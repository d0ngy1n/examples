package com.example.ipservice.service;

/**
 * Created by jacob on 2019/4/8.
 */
public interface WhiteIpAddressServiceProxy {

    boolean addWhiteIpAddress(String ip);

    boolean isWhiteIpAddress(String ip);

}
