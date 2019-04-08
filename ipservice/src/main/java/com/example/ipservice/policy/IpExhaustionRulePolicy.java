package com.example.ipservice.policy;

import com.example.ipservice.domain.IpRule;
import com.example.ipservice.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sun.net.util.IPAddressUtil;

import java.util.Set;

/**
 * Created by jacob on 2019/4/8.
 * ip配置规则：穷举
 * ip校验：值相等
 */
@Component("IpExhaustionRulePolicy")
@Slf4j
public class IpExhaustionRulePolicy implements IpRulePolicy {

    @Override
    public void validate(String rule) {
        if (!IPAddressUtil.isIPv4LiteralAddress(rule)) {
            throw new BaseException("invalid rule" + rule);
        }
    }

    @Override
    public boolean isMatch(IpRule rule, String ip) {
        return rule.getRule().equals(ip);
    }

    @Override
    public boolean isMatched(Set<IpRule> rules, String ip) {
        log.info("isMatched {}" + ip);
        return this.matchRule(rules, ip) != null;
    }
}
