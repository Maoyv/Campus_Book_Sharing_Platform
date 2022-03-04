package tsl.campus_book_sharing_platform.userBook.controller;

import tsl.campus_book_sharing_platform.uitl.ResultFormat;
import tsl.campus_book_sharing_platform.userBook.entity.UserBook;
import tsl.campus_book_sharing_platform.userBook.service.UserBookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * (UserBook)表控制层
 *
 * @author makejava
 * @since 2022-01-04 12:14:06
 */
@RestController
@RequestMapping("userBook")
public class UserBookController {
    /**
     * 服务对象
     */
    @Resource
    private UserBookService service;

    /**
     * 预约借阅(新增)
     *
     * @param userBook 实体
     * @return 新增结果
     */
    @RequestMapping(value = "/addUserBook",method = RequestMethod.GET)
    public ResultFormat add(UserBook userBook, HttpSession session) {
        return this.service.insert(userBook,session);
    }

    /**
     * 取消预约借阅(删除)
     *
     * @param userBook 实体
     * @return 新增结果
     */
    @RequestMapping(value = "/deleteUserBook",method = RequestMethod.GET)
    public ResultFormat delete(UserBook userBook, HttpSession session) {
        return this.service.delete(userBook,session);
    }

    /**
     * 借阅图书(修改)
     *
     * @param userBook 实体
     * @return 新增结果
     */
    @RequestMapping(value = "/borrowingUserBook",method = RequestMethod.GET)
    public ResultFormat borrowing(UserBook userBook, HttpSession session) {
        return this.service.borrowing(userBook,session);
    }

    /**
     * 归还图书(修改)
     *
     * @param userBook 实体
     * @return 新增结果
     */
    @RequestMapping(value = "/restoreUserBook",method = RequestMethod.GET)
    public ResultFormat restore(UserBook userBook, HttpSession session) {
        return this.service.restore(userBook,session);
    }

    /**
     *  查询，管理员及用户可使用，管理员可自选是否传入用户ID，用户默认传入用户ID，
     *  管理员默认state为申请中，用户为申请中+已借阅
     *
     * @param userId 用户ID
     * @param state 状态
     * @return 查询到的结果
     */
    @RequestMapping(value = "/findAllUserBook",method = RequestMethod.GET)
    public ResultFormat findAll(String userId,String state, HttpSession session) {
        return this.service.findAll(userId, state, session);
    }
}

