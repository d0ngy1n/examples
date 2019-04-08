package com.example.ipservice.port;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by jacob on 2019/4/3.
 */
public interface WhiteIpAddressUIPort {

    @PostMapping("/white/add")
    boolean addWhiteIpAddress(@RequestBody String ip);

    @GetMapping("/white/check")
    boolean isWhiteIpAddress(@RequestParam("ip") String ip);

}
