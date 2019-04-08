package com.example.ipservice.adapter;

import com.example.ipservice.port.WhiteIpAddressUIPort;
import com.example.ipservice.service.WhiteIpAddressServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jacob on 2019/4/3.
 */
@RestController
@RequestMapping("/ip")
public class WhiteIpAddressControllerAdapter implements WhiteIpAddressUIPort {

    @Autowired
    private WhiteIpAddressServiceProxy whiteIpAddressServiceProxy;

    @Override
    public boolean addWhiteIpAddress(String ip) {
        return whiteIpAddressServiceProxy.addWhiteIpAddress(ip);
    }

    @Override
    public boolean isWhiteIpAddress(String ip) {
        return whiteIpAddressServiceProxy.isWhiteIpAddress(ip);
    }
}
