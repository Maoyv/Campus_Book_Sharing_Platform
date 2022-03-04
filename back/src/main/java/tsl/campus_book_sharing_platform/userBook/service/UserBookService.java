package tsl.campus_book_sharing_platform.userBook.service;

import tsl.campus_book_sharing_platform.uitl.ResultFormat;
import tsl.campus_book_sharing_platform.userBook.entity.UserBook;
import javax.servlet.http.HttpSession;

/**
 * (UserBook)表服务接口
 *
 * @author makejava
 * @since 2022-01-04 12:14:06
 */
public interface UserBookService {

    /**
     * 预约借阅(新增)
     *
     * @param userBook 实例对象
     * @return 实例对象
     */
    ResultFormat insert(UserBook userBook, HttpSession session);

    /**
     * 取消预约借阅(删除)
     *
     * @param userBook 实例对象
     * @return 实例对象
     */
    ResultFormat delete(UserBook userBook, HttpSession session);


    /**
     * 借阅图书(修改)
     *
     * @param userBook 实例对象
     * @return 实例对象
     */
    ResultFormat borrowing(UserBook userBook, HttpSession session);

    /**
     * 归还图书(修改)
     *
     * @param userBook 实例对象
     * @return 实例对象
     */
    ResultFormat restore(UserBook userBook, HttpSession session);
    /**
     *  查询所有，管理员及用户可使用，管理员可自选是否传入用户ID，用户默认传入用户ID，
     *  管理员默认state为申请中，用户为申请中+已借阅
     *
     * @param userId 用户ID
     * @param state 状态
     * @return 查询到的结果
     */
    ResultFormat findAll(String userId,String state, HttpSession session);

    /**
     *  检查当前天是否有预约过期
     *
     * @return 查询到的结果
     */
    void checkDate();
}
