package tsl.campus_book_sharing_platform.book.service;

import tsl.campus_book_sharing_platform.book.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tsl.campus_book_sharing_platform.uitl.ResultFormat;

import javax.servlet.http.HttpSession;

/**
 * (Book)表服务接口
 *
 * @author makejava
 * @since 2022-01-04 12:13:50
 */
public interface BookService {

    /**
     * 条件查询
     *
     * @param book 筛选条件
     * @return 查询结果
     */
    ResultFormat queryAll(Book book, HttpSession session);

    /**
     * 新增数据
     *
     * @param book 实例对象
     * @return 实例对象
     */
    ResultFormat insert(Book book,HttpSession session);

    /**
     * 修改数据
     *
     * @param book 实例对象
     * @return 实例对象
     */
    ResultFormat update(Book book,HttpSession session);

    /**
     * 通过主键删除数据
     *
     * @param bookId 主键
     * @return 是否成功
     */
    ResultFormat deleteById(String bookId,HttpSession session);

}
