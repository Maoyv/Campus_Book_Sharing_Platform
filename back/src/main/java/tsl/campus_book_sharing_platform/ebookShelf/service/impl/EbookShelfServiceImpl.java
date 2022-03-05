package tsl.campus_book_sharing_platform.ebookShelf.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tsl.campus_book_sharing_platform.ebook.dao.EbookDao;
import tsl.campus_book_sharing_platform.ebook.service.EbookService;
import tsl.campus_book_sharing_platform.ebookShelf.dao.EbookShelfDao;
import tsl.campus_book_sharing_platform.ebookShelf.service.EbookShelfService;
import org.springframework.stereotype.Service;
import tsl.campus_book_sharing_platform.uitl.GlobalVariable;
import tsl.campus_book_sharing_platform.uitl.ResultFormat;
import tsl.campus_book_sharing_platform.uitl.ResultUtil;
import tsl.campus_book_sharing_platform.user.dao.UserDao;
import tsl.campus_book_sharing_platform.user.entity.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;

/**
 * (EbookShelf)表服务实现类
 *
 * @author makejava
 * @since 2022-03-01 22:00:18
 */
@Service("ebookShelfService")
/**
 * @author TSL
 * (User)表服务实现类
 * 表明该类（class）或方法（method）受事务控制
 * @param propagation  设置隔离级别
 * @param isolation 设置传播行为
 * @param rollbackFor 设置需要回滚的异常类，默认为RuntimeException
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class EbookShelfServiceImpl implements EbookShelfService {
    @Resource
    private EbookShelfDao dao;

    @Resource
    private UserDao userDao;

    @Resource
    private EbookDao ebookDao;

    @Value("${file.upload.url}")
    private String uploadFilePath;

    @Override
    public ResultFormat ebookShelfCancelCollection(String ebookName, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null || "".equals(userId)){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = userDao.findPassword(userId);
        if(user == null){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(GlobalVariable.tourists.equals(user.getRoleName())){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        Integer integer = ebookDao.selectCollection(ebookName);
        ebookDao.cancelCollection((integer-1),ebookName);
        dao.ebookShelfDelete(ebookName,user.getUserId());
        return ResultUtil.success("取消收藏成功");
    }

    @Override
    public ResultFormat ebookShelfQuarryAll(String ebookName, String ebookCategory, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null || "".equals(userId)){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = userDao.findPassword(userId);
        if(user == null){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(GlobalVariable.tourists.equals(user.getRoleName())){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        return ResultUtil.success(dao.ebookShelfQuarryAll(user.getUserId(),ebookName,ebookCategory));
    }
}
