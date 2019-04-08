package com.example.ipservice.policy;

import com.example.ipservice.domain.IpRule;
import com.example.ipservice.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by jacob on 2019/4/8.
 * ip配置规则：穷举的基础上支持ip段配置 如 192.168.0.* 10.10.*.* 等
 * ip校验：值相等或命中通配
 */
@Component("IpPatternRulePolicy")
@Slf4j
public class IpPatternRulePolicy implements IpRulePolicy {

    private Pattern pattern = Pattern.compile(
            "^(2(5[0-5]{1}|[0-4]\\d{1})|[0-1]?\\d{1,2}|\\*)(\\.(2(5[0-5]{1}|[0-4]\\d{1})|[0-1]?\\d{1,2}|\\*)){3}$");

    @Override
    public void validate(String rule) {
        if (!pattern.matcher(rule).matches()) {
            throw new BaseException("invalid rule " + rule);
        }
    }

    @Override
    public boolean isMatch(IpRule rule, String ip) {
        if (rule.getRule().equals(ip)) {
            return true;
        }

        String[] ruleParts = rule.getRule().split(IP_SEPARATOR);
        String[] ipParts = ip.split(IP_SEPARATOR);

        for (int i = 0; i < ruleParts.length; i++) {
            if (!ruleParts[i].equals(MATCH_ALL) && !ruleParts[i].equals(ipParts[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isMatched(Set<IpRule> rules, String ip) {
        log.info("isMatched {}" + ip);
        return this.matchRule(rules, ip) != null;
    }

}
