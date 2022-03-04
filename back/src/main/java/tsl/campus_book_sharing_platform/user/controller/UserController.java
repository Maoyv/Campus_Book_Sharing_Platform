package tsl.campus_book_sharing_platform.user.controller;

import tsl.campus_book_sharing_platform.uitl.ResultFormat;
import tsl.campus_book_sharing_platform.uitl.ResultUtil;
import tsl.campus_book_sharing_platform.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2021-12-01 16:46:50
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService service;

    /**
     * 注册
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/registered",method = RequestMethod.GET)
    public ResultFormat registered(String userId, String password){
        return service.registered(userId, password);
    }

    /**
     * 测试
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public ResultFormat test(){
        return ResultUtil.success("1");
    }

    /**
     * 登录
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ResultFormat login(String userId, String password, HttpSession session){
        return service.login(userId, password, session);
}

    /**
     * 重新获取信息
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public ResultFormat info(HttpSession session){
        return service.info(session);
    }

    /**
     * 退出登录
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/exit",method = RequestMethod.GET)
    public ResultFormat exit(HttpSession session){
        session.setAttribute("userId",null);
        return ResultUtil.success("退出成功");
    }

    /**
     * 修改用户名
     *
     */
    @RequestMapping(value = "/updateUserName",method = RequestMethod.GET)
    public ResultFormat updateUserName(String userName,HttpSession session){
        return service.updateUserName(userName, session);
    }

    /**
     * 修改密码
     *
     */
    @RequestMapping(value = "/updatePassword",method = RequestMethod.GET)
    public ResultFormat updatePassword(String oldPassword,String newPassword,HttpSession session){
        return service.updatePassword(oldPassword,newPassword, session);
    }

    /**
     * 添加图书类型
     *
     */
    @RequestMapping(value = "/insertBookCategory",method = RequestMethod.GET)
    public ResultFormat insertBookCategory(String bookCategory,HttpSession session){
        return service.insertBookCategory(bookCategory, session);
    }

    /**
     * 删除图书类型
     *
     */
    @RequestMapping(value = "/deleteBookCategory",method = RequestMethod.GET)
    public ResultFormat deleteBookCategory(String bookCategory,HttpSession session){
        return service.deleteBookCategory(bookCategory, session);
    }

    /**
     * 修改指定的图书类型
     *
     */
    @RequestMapping(value = "/updateBookCategory",method = RequestMethod.GET)
    public ResultFormat updateBookCategory(String oldBookCategory,String newBookCategory, HttpSession session){
        return service.updateBookCategory(oldBookCategory,newBookCategory, session);
    }

}

