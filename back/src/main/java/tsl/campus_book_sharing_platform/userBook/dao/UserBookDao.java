package tsl.campus_book_sharing_platform.userBook.dao;

import tsl.campus_book_sharing_platform.userBook.entity.UserBook;

import java.time.LocalDate;
import java.util.List;

/**
 * (UserBook)表数据库访问层
 *
 * @author makejava
 * @since 2022-01-04 12:14:06
 */
public interface UserBookDao {

    /**
     * 新增数据
     *
     * @param userBook 实例对象
     * @return 影响行数
     */
    int insert(UserBook userBook);

    /**
     * 删除数据
     *
     * @param userId 用户ID
     * @param bookId 书籍ID
     * @return 影响行数
     */
    void delete(String userId,String bookId);

    /**
     * 借阅图书(修改)
     *
     * @param userId 用户ID
     * @param bookId 书籍ID
     * @return 影响行数
     */
    void updateBorrowing(String userId,String bookId);

    /**
     * 归还图书(修改)
     *
     * @param userId 用户ID
     * @param bookId 书籍ID
     * @return 影响行数
     */
    void updateRestore(String userId,String bookId);

    /**
     * 查询指定人申请中或已借阅的书籍数量
     *
     * @param userId 用户ID
     * @return 书籍数量
     */
    int selectStateByUserId(String userId);


    /**
     * 查询指定人指定状态的书籍
     *
     * @param userId 用户ID
     * @param state 状态
     * @return 书籍数量
     */
    List<UserBook> selectAllByUserIdAndState(String userId,String state);

    /**
     * 查询指定人指定书籍借阅数量
     *
     * @param userId 用户ID
     * @param bookId 书籍ID
     * @return 书籍数量
     */
    int selectStateByUserIdAndBookId(String userId,String bookId);

    /**
     * 查询指定人指定书籍申请中借阅数量
     *
     * @param userId 用户ID
     * @param bookId 书籍ID
     * @return 书籍数量
     */
    int selectStateByUserIdAndBookIds(String userId,String bookId);

    /**
     * 查询指定人指定书籍已借阅借阅数量
     *
     * @param userId 用户ID
     * @param bookId 书籍ID
     * @return 书籍数量
     */
    int selectStateByUserIdAndBookIdes(String userId,String bookId);

    /**
     * 查询指定人指定书籍已借阅借阅数量
     *
     * @param date 当前天数
     * @return
     */
    void checkDate(LocalDate date);
}

