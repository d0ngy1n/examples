package com.example.ipservice.adapter;

import com.example.ipservice.aspect.anno.SysLog;
import com.example.ipservice.port.WhiteIpAddressRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sun.net.util.IPAddressUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jacob on 2019/4/3.
 */
@Service
@Slf4j
public class WhiteIpAddressServiceAdapter implements WhiteIpAddressRepositoryPort {

    private final static Set<String> ips = new HashSet<String>();

    @Override
    @SysLog
    public boolean addWhiteIpAddress(String ip) {
        ip = ip.trim();
        if (!IPAddressUtil.isIPv4LiteralAddress(ip) && !IPAddressUtil.isIPv6LiteralAddress(ip)) {
            return false;
        }
        boolean result = false;
        synchronized (ips) {
            result = ips.add(ip);
        }
//        if (result) {
//            log.info("added white ip address {}", ip);
//        }

        return result;
    }

    @Override
    @SysLog
    public boolean isWhiteIpAddress(String ip) {
        boolean result = ips.contains(ip);
//        log.info("ip {} is in white list? {}", ip, result);
        return result;
    }
}
