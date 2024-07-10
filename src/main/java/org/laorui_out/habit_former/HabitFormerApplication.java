package org.laorui_out.habit_former;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan("org.laorui_out.habit_former.mapper")
@SpringBootApplication
public class HabitFormerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HabitFormerApplication.class, args);
    }
}

class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(HabitFormerApplication.class);
    }
}
