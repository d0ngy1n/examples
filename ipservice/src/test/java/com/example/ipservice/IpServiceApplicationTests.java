package com.example.ipservice;

import com.example.ipservice.adapter.WhiteIpAddressControllerAdapter;
import com.example.ipservice.service.WhiteIpAddressService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class IpServiceApplicationTests {

    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final Condition     STOP = LOCK.newCondition();
    @Autowired
    private AbstractApplicationContext applicationContext;
    @Autowired
    private WhiteIpAddressService      whiteIpAddressService;
    private MockMvc                    mvc;
    @Autowired
    private WhiteIpAddressControllerAdapter whiteIpAddressControllerAdapter;

    static String generateIp() {
        Random r = new Random();
        return Strings.concat(r.nextInt(255), ".", r.nextInt(255), ".", r.nextInt(255), ".", r.nextInt(255));
    }

    private static void addHook(AbstractApplicationContext applicationContext) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            try {

                applicationContext.stop();
            } catch (Exception e) {
                log.error("StartMain stop exception ", e);
            }

            log.info("jvm exit, all service stopped.");
            try {
                LOCK.lock();
                STOP.signal();
            } finally {
                LOCK.unlock();
            }
        }, "StartMain-shutdown-hook"));
    }

    ThreadFactory getThreadFactory(final String threadName) {
        final AtomicInteger threadNumber = new AtomicInteger(1);
        ThreadFactory threadFactory = (r) -> {
            SecurityManager s = System.getSecurityManager();
            ThreadGroup group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();

            Thread t = new Thread(group, r, threadName + threadNumber.getAndIncrement(), 0);
            t.setDaemon(false);
            return t;
        };

        return threadFactory;
    }

    @Test
    public void testIsWhiteIpAddress() {
        Runnable isWhiteIp = () -> {
            String ip = generateIp();

            boolean result = whiteIpAddressService.isWhiteIpAddress(ip);
            log.info("ip {} is {} in white ip address list", ip, (result ? "" : "not"));
        };

        ExecutorService executorService2 = Executors.newScheduledThreadPool(20, getThreadFactory("check-ip-"));

        while (true) {
            executorService2.execute(isWhiteIp);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testAddWhiteIpAddress() throws InterruptedException {
        Runnable addWhiteIp = () -> {
            String ip = generateIp();

            boolean result = whiteIpAddressService.addWhiteIpAddress(ip);
            log.info("add ip {} {}", ip, (result ? "success" : "failed"));
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10, getThreadFactory("add-ip-"));
        for (int i = 0; i < 10; i++) {
            executor.schedule(addWhiteIp, 100, TimeUnit.MILLISECONDS);
        }

        addHook(applicationContext);
        lock();
    }

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(whiteIpAddressControllerAdapter).build();
    }

    @Test
    public void testMock() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(20, getThreadFactory("mock-ip-"));

        Runnable addWhiteIp = () -> {
            String ip = generateIp();
            try {
                mvc.perform(MockMvcRequestBuilders.post("/ip/white/add").content(ip).contentType(
                        MediaType.TEXT_PLAIN).accept(MediaType.ALL))
                   .andExpect(new ResultMatcher() {

                       @Override
                       public void match(MvcResult mvcResult) throws Exception {
                           assert mvcResult.getResponse().getStatus() == 200;
                       }
                   });
                Thread.sleep(100L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Runnable checkWhiteIp = () -> {
            String ip = generateIp();
            try {
                mvc.perform(MockMvcRequestBuilders.get("/ip/white/check").param("ip", ip).contentType(
                        MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.ALL))
                   .andExpect(new ResultMatcher() {

                       @Override
                       public void match(MvcResult mvcResult) throws Exception {
                           assert mvcResult.getResponse().getStatus() == 200;
                       }
                   });
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        List<Runnable> list = Arrays.asList(addWhiteIp, checkWhiteIp);

        for (; ; ) {
            Runnable runnable = list.get((int) (System.currentTimeMillis() % 2));

            executor.schedule(runnable, 0, TimeUnit.MILLISECONDS);
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    void lock() {
        //主线程阻塞等待，守护线程释放锁后退出
        try {
            LOCK.lock();
            STOP.await();
        } catch (InterruptedException e) {
            log.warn(" service  stopped, interrupted by other thread!", e);
        } finally {
            LOCK.unlock();
        }
    }

}
