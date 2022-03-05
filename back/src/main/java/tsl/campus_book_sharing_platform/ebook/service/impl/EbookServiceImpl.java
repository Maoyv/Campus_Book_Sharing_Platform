package tsl.campus_book_sharing_platform.ebook.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tsl.campus_book_sharing_platform.ebook.dao.EbookDao;
import tsl.campus_book_sharing_platform.ebook.entity.Ebook;
import tsl.campus_book_sharing_platform.ebook.service.EbookService;
import org.springframework.stereotype.Service;
import tsl.campus_book_sharing_platform.ebookShelf.dao.EbookShelfDao;
import tsl.campus_book_sharing_platform.ebookShelf.entity.EbookShelf;
import tsl.campus_book_sharing_platform.ebookShelf.service.EbookShelfService;
import tsl.campus_book_sharing_platform.uitl.GlobalVariable;
import tsl.campus_book_sharing_platform.uitl.ResultFormat;
import tsl.campus_book_sharing_platform.uitl.ResultUtil;
import tsl.campus_book_sharing_platform.user.dao.UserDao;
import tsl.campus_book_sharing_platform.user.entity.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

/**
 * (Ebook)表服务实现类
 *
 * @author makejava
 * @since 2022-03-01 22:00:02
 */
@Service("ebookService")
/**
 * @author TSL
 * (User)表服务实现类
 * 表明该类（class）或方法（method）受事务控制
 * @param propagation  设置隔离级别
 * @param isolation 设置传播行为
 * @param rollbackFor 设置需要回滚的异常类，默认为RuntimeException
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class EbookServiceImpl implements EbookService {
    @Resource
    private EbookDao dao;

    @Resource
    private UserDao userDao;

    @Resource
    private EbookShelfDao ebookShelfDao;

    private int i = 10;

    @Value("${file.upload.url}")
    private String uploadFilePath;

    static final private String cancel="已取消";
    static final private String reject="已驳回";
    static final private String adopt="上传成功";

    @Override
    public ResultFormat uploadListQueryAll( String ebookState, String ebookCategory, String ebookName, HttpSession session) {
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
        return ResultUtil.success(this.dao.uploadListQueryAll(user.getUserId(), ebookState, ebookCategory, ebookName));
    }

    @Override
    public ResultFormat uploadListDelete(String ebookId, HttpSession session) {
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
        dao.uploadListDelete(ebookId);
        return ResultUtil.success("删除成功");
    }

    @Override
    public ResultFormat update(String ebookId,String ebookName, String state, HttpSession session) {
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
        if(cancel.equals(state)){
            if(GlobalVariable.user.equals(user.getRoleName()) == false){
                return ResultUtil.error(-1,"账户异常，请重新登录后操作");
            }
        }
        if(reject.equals(state) || adopt.equals(state)){
            if(GlobalVariable.admin.equals(user.getRoleName()) == false){
                return ResultUtil.error(-1,"账户异常，请重新登录后操作");
            }
        }
        dao.update(ebookId,state);
        if(cancel.equals(state) || reject.equals(state)){
            File dest = new File(uploadFilePath + '/' + ebookName);
            dest.delete();
        }
        return ResultUtil.success("修改成功");
    }

    @Override
    public ResultFormat uploadListInsert(String ebookName,String ebookCategory, String ebookSize,String ebookBriefInformation, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null || "".equals(userId)){
            File dest = new File(uploadFilePath + '/' + ebookName);
            dest.delete();
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = userDao.findPassword(userId);
        if(user == null){
            File dest = new File(uploadFilePath + '/' + ebookName);
            dest.delete();
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(GlobalVariable.tourists.equals(user.getRoleName())){
            File dest = new File(uploadFilePath + '/' + ebookName);
            dest.delete();
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(dao.uploadListSum(ebookName,user.getUserId()) != 0){
            File dest = new File(uploadFilePath + '/' + ebookName);
            dest.delete();
            return ResultUtil.error(-1,"该电子书在书库已存在或正在上传或已驳回");
        }
        Ebook ebook = new Ebook();
        ebook.setEbookId("DTS"+i);
        i++;
        ebook.setEbookName(ebookName);
        if(ebookCategory == null || "".equals(ebookCategory)) {
            ebookCategory = "无类别";
        }
        ebook.setEbookCategory(ebookCategory);
        ebook.setEbookBriefInformation(ebookBriefInformation);
        ebook.setDownloadTimes(0);
        ebook.setCollectionTimes(0);
        ebook.setDownloadUserId(user.getUserId());
        ebook.setDownloadUserName(user.getUserName());
        ebook.setEbookSize(ebookSize);
        if(GlobalVariable.admin.equals(user.getRoleName())){
            ebook.setEbookState("上传成功");
        }
        if(GlobalVariable.user.equals(user.getRoleName())){
            ebook.setEbookState("审核中");
        }
        ebook.setInformationState("未删除");
        dao.uploadListInsert(ebook);
        return ResultUtil.success("上传电子书成功");
    }

    @Override
    public ResultFormat auditListQueryAll(String ebookCategory, String ebookName, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null || "".equals(userId)){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = userDao.findPassword(userId);
        if(user == null){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(GlobalVariable.admin.equals(user.getRoleName()) == false){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        return ResultUtil.success(this.dao.auditListQueryAll(ebookCategory, ebookName));
    }

    @Override
    public ResultFormat libraryQueryAll(String ebookCategory, String ebookName, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null || "".equals(userId)){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = userDao.findPassword(userId);
        if(user == null){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        return ResultUtil.success(dao.libraryQueryAll(ebookCategory, ebookName));
    }

    @Override
    public void download(String ebookName) {
        Integer integer = dao.selectDownload(ebookName);
        dao.download((integer + 1),ebookName);
    }

    @Override
    public ResultFormat libraryCollection(String ebookName, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null || "".equals(userId)){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = userDao.findPassword(userId);
        if(user == null){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        File dest = new File(uploadFilePath + '/' + ebookName);
        if(GlobalVariable.tourists.equals(user.getRoleName())){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(ebookShelfDao.ebookShelfSelectSum(user.getUserId(),ebookName) != 0){
            return ResultUtil.error(-1,"该电子书已被收藏，请勿重复操作");
        }
        Integer integer = dao.selectCollection(ebookName);
        dao.collection((integer + 1),ebookName);
        Ebook ebook = dao.selectEbook(ebookName);
        EbookShelf ebookShelf = new EbookShelf();
        ebookShelf.setUserId(user.getUserId());
        ebookShelf.setEbookName(ebookName);
        ebookShelf.setEbookCategory(ebook.getEbookCategory());
        ebookShelf.setEbookSize(ebook.getEbookSize());
        ebookShelf.setEbookRead(0);
        ebookShelf.setEbookPage(Math.toIntExact(dest.length()/1200 + 1));
        ebookShelfDao.ebookShelfInsert(ebookShelf);
        return ResultUtil.success("收藏成功");
    }

    @Override
    public ResultFormat libraryUpdate(String ebookName,String ebookBriefInformation, String ebookCategory, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null || "".equals(userId)){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = userDao.findPassword(userId);
        if(user == null){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(GlobalVariable.admin.equals(user.getRoleName()) == false){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(ebookName == null || "".equals(ebookName)){
            return ResultUtil.error(-1,"参数错误");
        }
        dao.libraryUpdate(ebookName, ebookBriefInformation, ebookCategory);
        return ResultUtil.success("修改成功");
    }

    @Override
    public ResultFormat libraryDelete(String ebookName, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null || "".equals(userId)){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = userDao.findPassword(userId);
        if(user == null){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(GlobalVariable.admin.equals(user.getRoleName()) == false){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(ebookName == null || "".equals(ebookName)){
            return ResultUtil.error(-1,"参数错误");
        }
        File dest = new File(uploadFilePath + '/' + ebookName);
        dest.delete();
        dao.libraryDelete(ebookName);
        return ResultUtil.success("删除成功");
    }


}
