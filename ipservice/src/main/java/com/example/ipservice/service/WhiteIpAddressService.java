package com.example.ipservice.service;

import com.example.ipservice.port.WhiteIpAddressRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jacob on 2019/4/3.
 */
@Service
public class WhiteIpAddressService {

    @Autowired
    private WhiteIpAddressRepositoryPort whiteIpAddressRepositoryPort;

    public boolean addWhiteIpAddress(String ip) {
        return whiteIpAddressRepositoryPort.addWhiteIpAddress(ip);
    }

    public boolean isWhiteIpAddress(String ip) {
        return whiteIpAddressRepositoryPort.isWhiteIpAddress(ip);
    }

}
