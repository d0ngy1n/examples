package com.example.ipservice.port;

import com.example.ipservice.domain.IpRule;

import java.util.Set;

/**
 * Created by jacob on 2019/4/3.
 */
public interface WhiteIpAddressRepositoryPort {

    boolean addWhiteIpAddress(IpRule ip);

    Set<IpRule> queryRules();

}
