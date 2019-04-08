package com.example.ipservice.policy;

import com.example.ipservice.domain.IpRule;
import com.example.ipservice.policy.holder.IpRuleHolder;

import java.util.Set;

/**
 * Created by jacob on 2019/4/8.
 * ip配置规则和匹配策略
 */
public interface IpRulePolicy {

    String IP_SEPARATOR = "\\.";
    String MATCH_ALL    = "*";

    void validate(String rule);

    default IpRule buildIpRule(String rule) {
        validate(rule);
        return IpRule.builder().rule(rule).build();
    }

    default IpRule matchRule(Set<IpRule> rules, String ip) {
        final IpRuleHolder holder = new IpRuleHolder();
        rules.forEach((rule) -> {
            if (isMatch(rule, ip)) {
                holder.setRule(rule);
            }
        });

        return holder.getRule();
    }

    boolean isMatch(IpRule rule, String ip);

    boolean isMatched(Set<IpRule> rules, String ip);

}
