package com.springboot.springboot.Services;

import org.springframework.stereotype.Component;

@Component("a")
public class ChineEngineImpl implements EngineService {

    @Override
    public void run() {
        System.out.println("engine china");
    }
}
