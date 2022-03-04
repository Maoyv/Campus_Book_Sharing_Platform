package tsl.campus_book_sharing_platform.user.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import tsl.campus_book_sharing_platform.user.entity.BookCategory;
import tsl.campus_book_sharing_platform.user.entity.User;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2021-12-01 16:46:50
 */
public interface UserDao {

    /**
     * 通过用户ID查询用户信息
     *
     * @param userId
     * @return 用户信息
     */
    User findPassword(String userId);

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    User queryById(String userId);

    /**
     * 查询指定行数据
     *
     * @param user     查询条件
     * @param pageable 分页对象
     * @return 对象列表
     */
    List<User> queryAllByLimit(User user, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param user 查询条件
     * @return 总行数
     */
    long count(User user);

    /**
     * 注册
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int update(User user);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(String userId);

    /**
     * 添加图书类型
     *
     * @param  bookCategory 图书类型
     */
    void insertBookCategory(String bookCategory);

    /**
     * 查询指定图书类型的数量
     *
     * @param  bookCategory 图书类型
     * @return 总数量
     */
    int selectSumBookCategory(String bookCategory);

    /**
     * 查询图书所有类别
     *
     * @return 图书类别
     */
    List<BookCategory> selectBookCategory();

    /**
     * 删除书籍分类
     * @param  bookCategory 图书类型
     */
    void deleteBookCategory(String bookCategory);

    /**
     * 删除书籍分类后修改书籍中的分类
     *
     * @param  bookCategory 图书类型
     */
    void deleteBookCategoryToUpdateBook(String bookCategory);

    /**
     * 删除书籍分类后修改电子书中的分类
     *
     * @param  bookCategory 图书类型
     */
    void deleteBookCategoryToUpdateEbook(String bookCategory);

    /**
     * 删除书籍分类后修改电子书架中的分类
     *
     * @param  bookCategory 图书类型
     */
    void deleteBookCategoryToUpdateEbookShelf(String bookCategory);

    /**
     * 修改指定的图书类型
     *
     * @param oldBookCategory 旧图书类型
     * @param newBookCategory 新图书类型
     */
    void updateBookCategory(String oldBookCategory,String newBookCategory);

    /**
     * 修改指定的图书类型后修改图书类型
     *
     * @param oldBookCategory 旧图书类型
     * @param newBookCategory 新图书类型
     */
    void updateBookCategoryToUpdateBook(String oldBookCategory,String newBookCategory);

    /**
     * 修改指定的图书类型后修改电子书类型
     *
     * @param oldBookCategory 旧图书类型
     * @param newBookCategory 新图书类型
     */
    void updateBookCategoryToUpdateEbook(String oldBookCategory,String newBookCategory);

    /**
     * 修改指定的图书类型后修改电子书架类型
     *
     * @param oldBookCategory 旧图书类型
     * @param newBookCategory 新图书类型
     */
    void updateBookCategoryToUpdateEbookShelf(String oldBookCategory,String newBookCategory);
}

