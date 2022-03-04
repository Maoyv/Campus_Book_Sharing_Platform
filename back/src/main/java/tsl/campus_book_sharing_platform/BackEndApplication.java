package tsl.campus_book_sharing_platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author TSL
 * @data    :   2021/12/1
 * @module  :   启动类
 */
@MapperScan("tsl.campus_book_sharing_platform.**.dao")
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class BackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEndApplication.class, args);
    }

}
