package cn.meshed.cloud.rd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * <h1>启动类</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@MapperScan("cn.meshed.cloud.rd.*.gatewayimpl.database.mapper")
@SpringBootApplication(scanBasePackages = {"cn.meshed.cloud", "com.alibaba.cola"})
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }

    @Bean
    public Supplier<Date> source1() {
        return () -> new Date();
    }

    @Bean
    public Consumer<Date> sink1() {
        return System.out::println;
    }

    @Bean
    public Consumer<Date> sink2() {
        return System.out::println;
    }
}
