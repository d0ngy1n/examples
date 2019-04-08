package com.example.ipservice.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by jacob on 2019/4/8.
 */
@EqualsAndHashCode(of = { "rule" })
@Data
@Builder
public class IpRule {

    private Long    id;
    private String  rule;
    private boolean isIpv6;
    private boolean isRange;


//    public static void main(String[] args) {
//        IpRule rule1 = IpRule.builder().rule("rule").id(1L).isRange(true).build();
//        IpRule rule2 = IpRule.builder().rule("rule").id(2L).build();
//        System.out.println(rule1.equals(rule2));
//    }

}
