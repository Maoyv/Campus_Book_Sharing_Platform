package tsl.campus_book_sharing_platform.ebookShelf.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tsl.campus_book_sharing_platform.ebook.dao.EbookDao;
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
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

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

    private final String readStates = "阅读";

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

    @Override
    public ResultFormat ebookShelfRead(Integer ebookRead, String ebookName, String readState,HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null || "".equals(userId)){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = userDao.findPassword(userId);
        if(user == null){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(ebookRead < 1){
            return ResultUtil.error(-1,"已经是首页了，无法跳转！");
        }
        String result = "";
        try {
            result = read(ebookName,ebookRead);
            if("".equals(result)){
                return ResultUtil.error(-1,"已到达文本末页或不存在指定目标页！");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if(readStates.equals(readState)){
            dao.ebookShelfReadUpdate(ebookRead,user.getUserId(),ebookName);
        }
        return ResultUtil.success(result);
    }

    /**
     *  读取文件内容
     *
     * @param   fileName 文件名
     * @param   ebookRead  页码
     * @return   读取的内容
     */
    public String read(String fileName,Integer ebookRead) throws Exception{
        File dest = new File(uploadFilePath + '/' + fileName);
        InputStream in = new FileInputStream(dest);
        byte[] tempByte = new byte[1024];
        int read = 0 ;
        Integer i = 1;
        String result = "";
        while ((read = in.read(tempByte)) != -1 ) {
            if(i.equals(ebookRead)){
                if(read == 0){
                    return "";
                }
                result = new String(tempByte,"utf-8").replace("�","").replace("\u0000","");
                break;
            }
            i++;
        }
        in.close();
        return result;
    }
}
