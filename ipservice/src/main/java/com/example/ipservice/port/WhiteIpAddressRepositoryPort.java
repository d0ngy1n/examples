package com.example.ipservice.port;

/**
 * Created by jacob on 2019/4/3.
 */
public interface WhiteIpAddressRepositoryPort {

    boolean addWhiteIpAddress(String ip);

    boolean isWhiteIpAddress(String ip);

}
