package com.springboot.springboot.Config;

import com.springboot.springboot.Services.*;
import com.springboot.springboot.database.Database;
import com.springboot.springboot.repositories.ProductRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

// @Configuration gom tất cả các @bean vào chung 1 file, thay vì phải tạo các @bean ở mỗi class đơn lẻ, Nó được chạy trước main để gom các bean chuyển đến ApplicationContext
// được gọi ngay khi ứng dụng chạy
@Configuration
public class EngineConfig {
    // use engine factory
    @Bean
    public EngineFactory engineFactory()
    {
       return new EngineFactory();
    }



    @Bean

    public EngineService chineEngine(EngineFactory engineFactory)
    {
       return  engineFactory.createEngineServiceFactory("chineEngine");
    }
    @Bean

    public EngineService vnEngine(EngineFactory engineFactory )
    {
        return engineFactory.createEngineServiceFactory("vnEngine");
    }

}
