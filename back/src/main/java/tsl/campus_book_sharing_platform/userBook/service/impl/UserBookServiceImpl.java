package tsl.campus_book_sharing_platform.userBook.service.impl;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tsl.campus_book_sharing_platform.book.dao.BookDao;
import tsl.campus_book_sharing_platform.book.entity.Book;
import tsl.campus_book_sharing_platform.uitl.GlobalVariable;
import tsl.campus_book_sharing_platform.uitl.ResultFormat;
import tsl.campus_book_sharing_platform.uitl.ResultUtil;
import tsl.campus_book_sharing_platform.user.dao.UserDao;
import tsl.campus_book_sharing_platform.user.entity.User;
import tsl.campus_book_sharing_platform.userBook.entity.UserBook;
import tsl.campus_book_sharing_platform.userBook.dao.UserBookDao;
import tsl.campus_book_sharing_platform.userBook.service.UserBookService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

/**
 * (UserBook)表服务实现类
 *
 * @author makejava
 * @since 2022-01-04 12:14:06
 */
@Service("userBookService")
/**
 * @author TSL
 * (User)表服务实现类
 * 表明该类（class）或方法（method）受事务控制
 * @param propagation  设置隔离级别
 * @param isolation 设置传播行为
 * @param rollbackFor 设置需要回滚的异常类，默认为RuntimeException
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class UserBookServiceImpl implements UserBookService {
    @Resource
    private UserBookDao dao;
    @Resource
    private UserDao userDao;
    @Resource
    private BookDao bookDao;

    private int max = 3;


    @Override
    public ResultFormat insert(UserBook userBook,HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null || "".equals(userId)){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = userDao.findPassword(userId);
        if(user == null){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(userId.equals(userBook.getUserId()) == false){
            return ResultUtil.error(-1,"信息异常，请重新登录后操作");
        }
        if(GlobalVariable.user.equals(user.getRoleName())){
            if(userBook.getUserId() == null || "".equals(userBook.getUserId())){
                return ResultUtil.error(-1,"无借阅人参数，请重新操作或退出登录后重试");
            }
            if(userBook.getBookId() == null || "".equals(userBook.getBookId())){
                return ResultUtil.error(-1,"无书籍参数，请重新操作或退出登录后重试");
            }
            if(bookDao.selectByBookIdForNumber(userBook.getBookId()) == 0){
                return ResultUtil.error(-1,"未查询到指定借阅书籍信息,请重新操作或退出登录后重试");
            }
            if(dao.selectStateByUserIdAndBookId(userBook.getUserId(),userBook.getBookId()) != 0){
                return ResultUtil.error(-1,"该书籍已借阅过，请勿重复借阅");
            }
            if(bookDao.selectByBookId(userBook.getBookId()) == 0){
                return ResultUtil.error(-1,"该书籍暂无可借阅数量，请勿借阅");
            }
            if(dao.selectStateByUserId(userBook.getUserId()) >= max){
                return ResultUtil.error(-1,"借阅数量已达上限3本");
            }
            userBook.setState("申请中");
            LocalDate appointmentTime = LocalDate.now();
            userBook.setAppointmentTime(appointmentTime);
            LocalDate expirationTime = LocalDate.now().plusDays(14);
            userBook.setExpirationTime(expirationTime);
            this.dao.insert(userBook);
            Book book = bookDao.queryById(userBook.getBookId());
            book.setBookAppointment(book.getBookAppointment() + 1);
            book.setBookAvailable(book.getBookAvailable() - 1);
            bookDao.update(book);
            return ResultUtil.success(userBook);
        }else {
            return ResultUtil.error(-1,"无权限操作");
        }
    }

    @Override
    public ResultFormat delete(UserBook userBook,HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if(userId == null || "".equals(userId)){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = userDao.findPassword(userId);
        if(user == null){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        if(userId.equals(userBook.getUserId()) == false){
            return ResultUtil.error(-1,"信息异常，请重新登录后操作");
        }
        if(GlobalVariable.user.equals(user.getRoleName())){
            if(userBook.getUserId() == null || "".equals(userBook.getUserId())){
                return ResultUtil.error(-1,"无借阅人参数，请重新操作或退出登录后重试");
            }
            if(userBook.getBookId() == null || "".equals(userBook.getBookId())){
                return ResultUtil.error(-1,"无书籍参数，请重新操作或退出登录后重试");
            }
            if(bookDao.selectByBookIdForNumber(userBook.getBookId()) == 0){
                return ResultUtil.error(-1,"未查询到指定借阅书籍信息,请重新操作或退出登录后重试");
            }
            if(dao.selectStateByUserIdAndBookIds(userBook.getUserId(),userBook.getBookId()) == 0){
                return ResultUtil.error(-1,"该书籍未预约借阅，请核实后操作");
            }
            this.dao.delete(userBook.getUserId(),userBook.getBookId());
            Book book = bookDao.queryById(userBook.getBookId());
            book.setBookAppointment(book.getBookAppointment() - 1);
            book.setBookAvailable(book.getBookAvailable() + 1);
            bookDao.update(book);
            return ResultUtil.success(userBook);
        }else {
            return ResultUtil.error(-1,"无权限操作");
        }
    }

    @Override
    public ResultFormat borrowing(UserBook userBook, HttpSession session) {
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
            if(userBook.getUserId() == null || "".equals(userBook.getUserId())){
                return ResultUtil.error(-1,"无借阅人参数，请重新操作或退出登录后重试");
            }
            if(userBook.getBookId() == null || "".equals(userBook.getBookId())){
                return ResultUtil.error(-1,"无书籍参数，请重新操作或退出登录后重试");
            }
            if(bookDao.selectByBookIdForNumber(userBook.getBookId()) == 0){
                return ResultUtil.error(-1,"未查询到指定借阅书籍信息,请重新操作或退出登录后重试");
            }
            if(dao.selectStateByUserIdAndBookIds(userBook.getUserId(),userBook.getBookId()) == 0){
                return ResultUtil.error(-1,"该书籍未预约借阅，请核实后操作");
            }
            this.dao.updateBorrowing(userBook.getUserId(),userBook.getBookId());
            Book book = bookDao.queryById(userBook.getBookId());
            book.setBookBorrowed(book.getBookBorrowed() +1 );
            book.setBookAppointment(book.getBookAppointment() - 1);
            bookDao.update(book);
            return ResultUtil.success(userBook);
        }else {
            return ResultUtil.error(-1,"无权限操作");
        }
    }

    @Override
    public ResultFormat restore(UserBook userBook, HttpSession session) {
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
            if(userBook.getUserId() == null || "".equals(userBook.getUserId())){
                return ResultUtil.error(-1,"无借阅人参数，请重新操作或退出登录后重试");
            }
            if(userBook.getBookId() == null || "".equals(userBook.getBookId())){
                return ResultUtil.error(-1,"无书籍参数，请重新操作或退出登录后重试");
            }
            if(bookDao.selectByBookIdForNumber(userBook.getBookId()) == 0){
                return ResultUtil.error(-1,"未查询到指定借阅书籍信息,请重新操作或退出登录后重试");
            }
            if(dao.selectStateByUserIdAndBookIdes(userBook.getUserId(),userBook.getBookId()) == 0){
                return ResultUtil.error(-1,"该书籍未借阅，请核实后操作");
            }
            this.dao.updateRestore(userBook.getUserId(),userBook.getBookId());
            Book book = bookDao.queryById(userBook.getBookId());
            book.setBookBorrowed(book.getBookBorrowed() -1 );
            book.setBookAvailable(book.getBookAvailable() + 1);
            bookDao.update(book);
            return ResultUtil.success(userBook);
        }else {
            return ResultUtil.error(-1,"无权限操作");
        }
    }


    @Override
    public ResultFormat findAll(String userId, String state, HttpSession session) {
        String userIds = (String) session.getAttribute("userId");
        if(userIds == null || "".equals(userIds)){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 根据登录账户ID获取登录账户账户信息
        User user = userDao.findPassword(userIds);
        if(user == null){
            return ResultUtil.error(-1,"账户异常，请重新登录后操作");
        }
        // 如果是游客
        if(GlobalVariable.tourists.equals(user.getRoleName())){
            return ResultUtil.error(-1,"无权限操作");
        }
        // 如果是用户
        if(GlobalVariable.user.equals(user.getRoleName())){
            if(userIds.equals(userId)){
                // 如果state为空，则默认查询申请中和已借阅
                return ResultUtil.success(dao.selectAllByUserIdAndState(userId,state));
            }else {
                return ResultUtil.error(-1,"账户异常，请重新登录后操作");
            }
        }else {
            if(state == null || "".equals(state)){
                state = "申请中";
            }
            return ResultUtil.success(dao.selectAllByUserIdAndState(userId,state));
        }
    }

    @Override
    public void checkDate() {
        LocalDate date = LocalDate.now();
        dao.checkDate(date);
    }


}
