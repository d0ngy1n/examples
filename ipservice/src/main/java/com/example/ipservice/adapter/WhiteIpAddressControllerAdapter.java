package com.example.ipservice.adapter;

import com.example.ipservice.port.WhiteIpAddressUIPort;
import com.example.ipservice.service.WhiteIpAddressService;
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
    private WhiteIpAddressService whiteIpAddressService;

    @Override
    public boolean addWhiteIpAddress(String ip) {
        return whiteIpAddressService.addWhiteIpAddress(ip);
    }

    @Override
    public boolean isWhiteIpAddress(String ip) {
        return whiteIpAddressService.isWhiteIpAddress(ip);
    }
}
