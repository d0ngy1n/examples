package com.example.ipservice.adapter;

import com.example.ipservice.aspect.anno.SysLog;
import com.example.ipservice.domain.IpRule;
import com.example.ipservice.port.WhiteIpAddressRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jacob on 2019/4/3.
 */
@Service
@Slf4j
public class WhiteIpAddressServiceAdapter implements WhiteIpAddressRepositoryPort {

    private final static Set<IpRule> rules = new HashSet<IpRule>();

    @Override
    @SysLog
    public boolean addWhiteIpAddress(IpRule rule) {
        boolean result;
        synchronized (rules) {
            result = rules.add(rule);
        }
        return result;
    }


    @Override
    @SysLog
    public Set<IpRule> queryRules() {
        return Collections.unmodifiableSet(rules);
    }
}
