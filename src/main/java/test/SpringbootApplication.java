package test;

import com.springboot.springboot.Controllers.Car;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.springboot.springboot"})
public class SpringbootApplication {

    public static void main(String[] args) {
      ApplicationContext ctx=  SpringApplication.run(SpringbootApplication.class, args);
      Car c=ctx.getBean(Car.class);
      c.getMessage();
        System.out.println(ctx.getBeanDefinitionCount());

    }

}
