package tsl.campus_book_sharing_platform.book.service.impl;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tsl.campus_book_sharing_platform.book.entity.Book;
import tsl.campus_book_sharing_platform.book.dao.BookDao;
import tsl.campus_book_sharing_platform.book.service.BookService;
import org.springframework.stereotype.Service;
import tsl.campus_book_sharing_platform.uitl.GlobalVariable;
import tsl.campus_book_sharing_platform.uitl.ResultFormat;
import tsl.campus_book_sharing_platform.uitl.ResultUtil;
import tsl.campus_book_sharing_platform.user.dao.UserDao;
import tsl.campus_book_sharing_platform.user.entity.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * (Book)表服务实现类
 *
 * @author makejava
 * @since 2022-01-04 12:13:50
 */
@Service("bookService")
/**
 * @author TSL
 * (User)表服务实现类
 * 表明该类（class）或方法（method）受事务控制
 * @param propagation  设置隔离级别
 * @param isolation 设置传播行为
 * @param rollbackFor 设置需要回滚的异常类，默认为RuntimeException
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class BookServiceImpl implements BookService {
    @Resource
    private BookDao dao;

    @Resource
    private UserDao userDao;

    private int i = 10;

    /**
     * 条件查询
     *
     * @param book 筛选条件
     * @return 查询结果
     */
    @Override
    public ResultFormat queryAll(Book book, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null || "".equals(userId)){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = userDao.findPassword(userId);
        if(user == null){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        return ResultUtil.success(this.dao.queryAll(book));
    }

    /**
     * 新增数据
     *
     * @param book 实例对象
     * @return 实例对象
     */
    @Override
    public ResultFormat insert(Book book,HttpSession session) {

        String userId = (String) session.getAttribute("userId");
        if(userId == null || "".equals(userId)){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = userDao.findPassword(userId);
        if(user == null){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(GlobalVariable.admin.equals(user.getRoleName())){
            book.setBookId("TS"+i);
            if(book.getBookName() == null || "".equals(book.getBookName())){
                return ResultUtil.error(-1,"书籍名称不能为空");
            }
            if(book.getBookIntroduction() == null || "".equals(book.getBookIntroduction())){
                return ResultUtil.error(-1,"书籍简介不能为空");
            }
            if(book.getBookCategory() == null || "".equals(book.getBookCategory())){
                book.setBookCategory("无类别");
            }
            if(book.getBookNumber() == null || book.getBookNumber() == 0){
                return ResultUtil.error(-1,"书籍数量不能为空或0");
            }
            if(dao.selectByBookNameForNumber(book.getBookName()) != 0){
                return ResultUtil.error(-1,"书籍已存在");
            }
            book.setBookBorrowed(0);
            book.setBookAppointment(0);
            book.setBookAvailable(book.getBookNumber());
            this.dao.insert(book);
            i++;
            return ResultUtil.success(book);
        }else {
            return ResultUtil.error(-1,"无权限操作");
        }
    }

    /**
     * 修改数据
     *
     * @param book 实例对象
     * @return 实例对象
     */
    @Override
    public ResultFormat update(Book book,HttpSession session) {

        String userId = (String) session.getAttribute("userId");
        if(userId == null || "".equals(userId)){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = userDao.findPassword(userId);
        if(user == null){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(GlobalVariable.admin.equals(user.getRoleName())){
            if(book.getBookId() == null || "".equals(book.getBookId())){
                return ResultUtil.error(-1,"书籍ID不能为空");
            }
            if(book.getBookName() == null || "".equals(book.getBookName())){
                return ResultUtil.error(-1,"书籍名称不能为空");
            }
            if(book.getBookCategory() == null || "".equals(book.getBookCategory())){
                book.setBookCategory("无类别");
            }
            if(book.getBookIntroduction() == null || "".equals(book.getBookIntroduction())){
                return ResultUtil.error(-1,"书籍简介不能为空");
            }
            if(book.getBookNumber() == null || book.getBookNumber() == 0){
                return ResultUtil.error(-1,"书籍数量不能为空或0");
            }
            book.setBookAvailable(book.getBookNumber() - book.getBookBorrowed() - book.getBookAppointment());
            if(book.getBookAvailable() < 0 ){
                return ResultUtil.error(-1,"书籍数量少于已被借阅数量加预约借阅数量总和，请核对后再进行修改");
            }
            this.dao.deleteById(book.getBookId());
            if(dao.selectByBookNameForNumber(book.getBookName()) != 0){
                return ResultUtil.error(-1,"书籍已存在");
            }
            this.dao.insert(book);
            return ResultUtil.success(this.dao.queryById(book.getBookId()));
        }else {
            return ResultUtil.error(-1,"无权限操作");
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param bookId 主键
     * @return 是否成功
     */
    @Override
    public ResultFormat deleteById(String bookId,HttpSession session) {

        String userId = (String) session.getAttribute("userId");
        if(userId == null || "".equals(userId)){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = userDao.findPassword(userId);
        if(user == null){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(GlobalVariable.admin.equals(user.getRoleName())){
            if(bookId == null || "".equals(bookId)){
                return ResultUtil.error(-1,"无图书信息参数，请重新登录后操作");
            }
            if(dao.selectByBookIdForNumber(bookId) == 0){
                return ResultUtil.error(-1,"无要删除的指定图书");
            }
            dao.deleteById(bookId);

        }else {
            return ResultUtil.error(-1,"无权限操作");
        }
        return ResultUtil.success(this.dao.deleteById(bookId) > 0);
    }
}
