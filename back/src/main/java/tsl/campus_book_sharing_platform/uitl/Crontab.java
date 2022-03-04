package tsl.campus_book_sharing_platform.uitl;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tsl.campus_book_sharing_platform.userBook.service.UserBookService;

import javax.annotation.Resource;

/**
 * @author TSL
 * 定时任务
 */
@Component
@EnableAsync
public class Crontab {

    @Resource
    private UserBookService userBookService;
//    /**
//     *  定时任务1 每 1 秒执行一次
//     *
//     * @param
//     * @return
//     */
//    @Scheduled(fixedRate = 1000)
//    public void test() {
//
//    }

    /**
     * 定时任务2 ，每天晚上23点55分执行一次
     * 检查图书预约借阅中 申请中 的借阅信息的时间是否过期
     */
    @Scheduled(cron = "0 55 23 * * ?")
    public void test2() {
        userBookService.checkDate();
    }

}
