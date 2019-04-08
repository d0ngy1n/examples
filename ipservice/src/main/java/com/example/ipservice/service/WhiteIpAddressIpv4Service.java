package com.example.ipservice.service;

import com.example.ipservice.domain.IpRule;
import com.example.ipservice.policy.IpRulePolicy;
import com.example.ipservice.port.WhiteIpAddressRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by jacob on 2019/4/3.
 */
@Service
@Slf4j
public class WhiteIpAddressIpv4Service implements WhiteIpAddressServiceProxy {

    @Autowired
    private WhiteIpAddressRepositoryPort whiteIpAddressRepositoryPort;

    @Resource(name = "${policy.name:IpPatternRulePolicy}")
    private IpRulePolicy ipRulePolicy;

    public boolean addWhiteIpAddress(String ip) {

        IpRule ipRule = ipRulePolicy.buildIpRule(ip);

        return whiteIpAddressRepositoryPort.addWhiteIpAddress(ipRule);
    }

    public boolean isWhiteIpAddress(String ip) {

        Set<IpRule> rules = whiteIpAddressRepositoryPort.queryRules();

        return ipRulePolicy.isMatched(rules, ip);
    }

    @PostConstruct
    public void afterProperties() {
        log.info("policy is {}", ipRulePolicy.getClass().getSimpleName());
    }

}
