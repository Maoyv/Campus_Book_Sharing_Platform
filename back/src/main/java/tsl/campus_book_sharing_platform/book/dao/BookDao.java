package tsl.campus_book_sharing_platform.book.dao;

import tsl.campus_book_sharing_platform.book.entity.Book;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (Book)表数据库访问层
 *
 * @author makejava
 * @since 2022-01-04 12:13:50
 */
public interface BookDao {

    /**
     * 通过ID查询单条数据
     *
     * @param bookId 主键
     * @return 实例对象
     */
    Book queryById(String bookId);

    /**
     * 条件查询
     *
     * @param book 查询条件
     * @return 对象列表
     */
    List<Book> queryAll(Book book);

    /**
     * 新增数据
     *
     * @param book 实例对象
     * @return 影响行数
     */
    int insert(Book book);

    /**
     * 修改数据
     *
     * @param book 实例对象
     * @return 影响行数
     */
    int update(Book book);

    /**
     * 通过主键删除数据
     *
     * @param bookId 主键
     * @return 影响行数
     */
    int deleteById(String bookId);

    /**
     * 查询书籍可借阅数量
     *
     * @param bookId 主键
     * @return 可借阅数量
     */
    int selectByBookId(String bookId);

    /**
     * 查询指定书籍数量
     *
     * @param bookId 主键
     * @return 书籍数量
     */
    int selectByBookIdForNumber(String bookId);

    /**
     * 查询指定名称书籍数量
     *
     * @param bookId 主键
     * @return 书籍数量
     */
    int selectByBookNameForNumber(String bookId);
}

