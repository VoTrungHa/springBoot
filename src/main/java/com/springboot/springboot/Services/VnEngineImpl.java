package com.springboot.springboot.Services;

import org.springframework.stereotype.Component;

@Component("b")
public class VnEngineImpl implements EngineService {
    @Override
    public void run() {
        System.out.println("VN engine");
    }
}
