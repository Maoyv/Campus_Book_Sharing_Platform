package tsl.campus_book_sharing_platform.user.service.impl;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tsl.campus_book_sharing_platform.permissions.service.PermissionsService;
import tsl.campus_book_sharing_platform.uitl.*;
import tsl.campus_book_sharing_platform.user.dao.UserDao;
import tsl.campus_book_sharing_platform.user.entity.User;
import tsl.campus_book_sharing_platform.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Service("userService")
/**
 * @author TSL
 * (User)表服务实现类
 * 表明该类（class）或方法（method）受事务控制
 * @param propagation  设置隔离级别
 * @param isolation 设置传播行为
 * @param rollbackFor 设置需要回滚的异常类，默认为RuntimeException
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao dao;

    @Resource
    private PermissionsService permissionsService;

    int min = 6;
    int max = 12;
    String book = "无类别";

    @Override
    public ResultFormat registered(String userId, String password){
        if(userId == null ||"".equals(userId)){
            return ResultUtil.error(-1,"账户不能为空");
        }else if(password == null || "".equals(password)){
            return ResultUtil.error(-1,"密码不能为空");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = dao.findPassword(userId);
        // 如果user不为null，则为该账户ID已存在
        if(user != null){
            // 返回登录失败
            return ResultUtil.error(-1,"该账户已注册");
        }
        List<String> passwords = Arrays.asList(password.split(""));
        if(passwords.size() < min || passwords.size()> max){
            return ResultUtil.error(-1,"密码应为6-12位");
        }
        User users = new User();
        users.setUserId(userId);
        users.setUserName(userId);
        users.setUserPassword(password);
        users.setRoleName("用户");
        users.setPermissionsId("2");
        // 原图： https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif
        users.setPortrait("https://www.qqtouxiang.com/d/file/nvsheng/2016-11-24/a337577e72aa1a676a4496cb120adcaf.jpg");
        dao.insert(users);

        // 返回注册成功
        return ResultUtil.success("注册成功");

    }

    @Override
    public ResultFormat login(String userId, String password, HttpSession session){
        if(userId == null ||"".equals(userId)){
            return ResultUtil.error(-1,"账户不能为空");
        }else if(password == null || "".equals(password)){
            return ResultUtil.error(-1,"密码不能为空");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = dao.findPassword(userId);
        // 将session中userId重置为null
        session.setAttribute("userId",null);
        // 如果user为null，则为查询到登录账户
        if(user == null){
            // 返回登录失败
            return ResultUtil.error(-1,"登录失败：该账户尚未注册");
        }
        // 判断账户密码是否正确
        if(password.equals(user.getUserPassword())){
            LoginSubmit loginSubmit = new LoginSubmit();
            loginSubmit.setUserId(userId);
            loginSubmit.setName(user.getUserName());
            loginSubmit.setAvatar(user.getPortrait());
            loginSubmit.setRole(user.getRoleName());
            loginSubmit.setBookCategory(dao.selectBookCategory());
            loginSubmit.setPermissions(permissionsService.queryByPermissionsId(user.getPermissionsId()));
            // 生成token
            String token = JwtUitls.createToken(userId);
            // 写入token
            session.setAttribute("token",token);
            loginSubmit.setToken(token);
            // 写入登录人ID
            session.setAttribute("userId",user.getUserId());
            // 写入登录人昵称
            session.setAttribute("username",loginSubmit.getName());
            // 写入登录人角色
            session.setAttribute("role",loginSubmit.getRole());
            // 写入登录人权限集
            session.setAttribute("permissions",loginSubmit.getPermissions());
            // 返回登录成功
            return ResultUtil.success(loginSubmit);
        }else {
            // 返回登录失败
            return ResultUtil.error(-1,"登录失败：账户/密码不正确");
        }
    }

    @Override
    public ResultFormat info(HttpSession session){
        String userId = (String) session.getAttribute("userId");
        // 根据登录账户ID获取登录账户账户信息
        User user = dao.findPassword(userId);
        // 将session中userId重置为null
        session.setAttribute("userId",null);
        // 如果user为null，则为查询到登录账户
        LoginSubmit loginSubmit = new LoginSubmit();
        loginSubmit.setUserId(userId);
        loginSubmit.setName(user.getUserName());
        loginSubmit.setAvatar(user.getPortrait());
        loginSubmit.setRole(user.getRoleName());
        loginSubmit.setBookCategory(dao.selectBookCategory());
        loginSubmit.setPermissions(permissionsService.queryByPermissionsId(user.getPermissionsId()));
        // 生成token
        String token = JwtUitls.createToken(userId);
        // 写入token
        session.setAttribute("token",token);
        loginSubmit.setToken(token);
        // 写入登录人ID
        session.setAttribute("userId",user.getUserId());
        // 写入登录人昵称
        session.setAttribute("username",loginSubmit.getName());
        // 写入登录人角色
        session.setAttribute("role",loginSubmit.getRole());
        // 写入登录人权限集
        session.setAttribute("permissions",loginSubmit.getPermissions());
        // 返回登录成功
        return ResultUtil.success(loginSubmit);
    }

    @Override
    public ResultFormat updateUserName(String userName, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        // 根据登录账户ID获取登录账户账户信息
        User user = dao.findPassword(userId);
        if(GlobalVariable.tourists.equals(user.getRoleName())){
            return ResultUtil.error(-1,"无权限操作");
        }
        if(userName == null || "".equals(userName)){
            return ResultUtil.error(-1,"用户名不能为空");
        }
        user.setUserName(userName);
        dao.update(user);
        return ResultUtil.success(userName);
    }

    @Override
    public ResultFormat updatePassword(String oldPassword, String newPassword, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        // 根据登录账户ID获取登录账户账户信息
        User user = dao.findPassword(userId);
        if(GlobalVariable.tourists.equals(user.getRoleName())){
            return ResultUtil.error(-1,"无权限操作");
        }
        if(oldPassword == null || "".equals(oldPassword)){
            return ResultUtil.error(-1,"原密码不能为空");
        }
        List<String> oldPasswords = Arrays.asList(oldPassword.split(""));
        if(oldPasswords.size() < min || oldPasswords.size()> max){
            return ResultUtil.error(-1,"原密码应为6-12位");
        }
        if(newPassword == null || "".equals(newPassword)){
            return ResultUtil.error(-1,"新密码不能为空");
        }
        List<String> newPasswords = Arrays.asList(newPassword.split(""));
        if(newPasswords.size() < min || newPasswords.size()> max){
            return ResultUtil.error(-1,"原密码应为6-12位");
        }
        if(oldPassword.equals(user.getUserPassword())){
            if(oldPassword.equals(newPassword)){
                return ResultUtil.error(-1,"原密码和新密码应为不同密码");
            }else {
                user.setUserPassword(newPassword);
                dao.update(user);
            }
        }else {
            return ResultUtil.error(-1,"原密码错误");
        }
        return ResultUtil.success("密码修改成功");
    }

    @Override
    public ResultFormat insertBookCategory(String bookCategory, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null || "".equals(userId)){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = dao.findPassword(userId);
        if(user == null){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(GlobalVariable.admin.equals(user.getRoleName())){
            if(bookCategory == null || "".equals(bookCategory)){
                return ResultUtil.error(-1,"图书类型不能为空");
            }else {
                List<String> newPasswords = Arrays.asList(bookCategory.split(""));
                if(newPasswords.size() > min ){
                    return ResultUtil.error(-1,"图书类型请勿超过6位");
                }
                if(dao.selectSumBookCategory(bookCategory) != 0){
                    return ResultUtil.error(-1,"图书类型已重复");
                }else {
                    dao.insertBookCategory(bookCategory);
                    return ResultUtil.success(dao.selectBookCategory());
                }
            }
        }else {
            return ResultUtil.error(-1,"无权限操作");
        }
    }

    @Override
    public ResultFormat deleteBookCategory(String bookCategory, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null || "".equals(userId)){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = dao.findPassword(userId);
        if(user == null){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(GlobalVariable.admin.equals(user.getRoleName())){
            if(bookCategory == null || "".equals(bookCategory)){
                return ResultUtil.error(-1,"图书类型不能为空");
            }else {
                if(book.equals(bookCategory)){
                    return ResultUtil.error(-1,"无类别无法删除");
                }
                if(dao.selectSumBookCategory(bookCategory) == 0){
                    return ResultUtil.error(-1,"未查询到需要删除的图书类型");
                }else {
                    dao.deleteBookCategory(bookCategory);
                    dao.deleteBookCategoryToUpdateBook(bookCategory);
                    dao.deleteBookCategoryToUpdateEbook(bookCategory);
                    dao.deleteBookCategoryToUpdateEbookShelf(bookCategory);
                    return ResultUtil.success(dao.selectBookCategory());
                }
            }
        }else {
            return ResultUtil.error(-1,"无权限操作");
        }
    }

    @Override
    public ResultFormat updateBookCategory(String oldBookCategory, String newBookCategory, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null || "".equals(userId)){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = dao.findPassword(userId);
        if(user == null){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(GlobalVariable.admin.equals(user.getRoleName())){
            if(oldBookCategory == null || "".equals(oldBookCategory)){
                return ResultUtil.error(-1,"原图书类型不能为空");
            }else {
                if(book.equals(oldBookCategory)){
                    return ResultUtil.error(-1,"无类别无法修改");
                }
                if(book.equals(newBookCategory)){
                    return ResultUtil.error(-1,"无法修改为无类别");
                }
                List<String> newPasswords1 = Arrays.asList(oldBookCategory.split(""));
                if(newPasswords1.size() > min ){
                    return ResultUtil.error(-1,"原图书类型请勿超过6位");
                }
                if(dao.selectSumBookCategory(oldBookCategory) == 0){
                    return ResultUtil.error(-1,"未查询到需要修改的图书类型");
                }else {
                    if(newBookCategory == null || "".equals(newBookCategory)){
                        return ResultUtil.error(-1,"修改后图书类型不能为空");
                    }else {
                        List<String> newPasswords = Arrays.asList(newBookCategory.split(""));
                        if(newPasswords.size() > min ){
                            return ResultUtil.error(-1,"修改后图书类型请勿超过6位");
                        }
                        if(dao.selectSumBookCategory(newBookCategory) != 0){
                            return ResultUtil.error(-1,"修改后的图书类型已存在");
                        }else {
                            dao.updateBookCategory(oldBookCategory, newBookCategory);
                            dao.updateBookCategoryToUpdateBook(oldBookCategory, newBookCategory);
                            dao.updateBookCategoryToUpdateEbook(oldBookCategory, newBookCategory);
                            dao.updateBookCategoryToUpdateEbookShelf(oldBookCategory,newBookCategory);
                            return ResultUtil.success(dao.selectBookCategory());
                        }
                    }
                }
            }
        }else {
            return ResultUtil.error(-1,"无权限操作");
        }
    }
}
