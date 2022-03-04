package tsl.campus_book_sharing_platform.user.service;

import tsl.campus_book_sharing_platform.uitl.ResultFormat;

import javax.servlet.http.HttpSession;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2021-12-01 16:46:50
 */
public interface UserService {

    /**
     * 注册
     */
    ResultFormat registered(String userId, String password);

    /**
     * 登录
     */
    ResultFormat login(String userId, String password, HttpSession session);

    /**
     * 重新获取信息
     */
    ResultFormat info(HttpSession session);

    /**
     * 修改用户名
     */
    ResultFormat updateUserName(String userName,HttpSession session);

    /**
     * 修改密码
     */
    ResultFormat updatePassword(String oldPassword,String newPassword,HttpSession session);

    /**
     * 添加图书类型
     */
    ResultFormat insertBookCategory(String bookCategory,HttpSession session);

    /**
     * 删除书籍分类
     */
    ResultFormat deleteBookCategory(String bookCategory,HttpSession session);

    /**
     * 修改指定的图书类型
     */
    ResultFormat updateBookCategory(String oldBookCategory,String newBookCategory, HttpSession session);


}
