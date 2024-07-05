package org.laorui_out.habit_former;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("org.laorui_out.habit_former.mapper")
@SpringBootApplication
public class HabitFormerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HabitFormerApplication.class, args);
    }
}
