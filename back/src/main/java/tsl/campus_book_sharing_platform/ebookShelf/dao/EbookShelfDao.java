package tsl.campus_book_sharing_platform.ebookShelf.dao;

import tsl.campus_book_sharing_platform.ebookShelf.entity.EbookShelf;

import java.util.List;

/**
 * (EbookShelf)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-01 22:00:17
 */
public interface EbookShelfDao {

    /**
     * 根据电子书名和用户名删除收藏信息
     *
     * @param ebookName   电子书名称
     * @param userId   用户名称
     * @return 对象列表
     */
    void ebookShelfDelete(String ebookName,String userId);

    /**
     * 书架——新增
     *
     * @param ebookShelf   收藏信息
     * @return 对象列表
     */
    void ebookShelfInsert(EbookShelf ebookShelf);

    /**
     * 查询是否已收藏
     *
     * @param ebookName   电子书名称
     * @param userId   用户名称
     * @return 对象列表
     */
    Integer ebookShelfSelectSum(String userId,String ebookName);

    /**
     * 书架-分页查询
     *
     * @param ebookName   电子书名称
     * @param userId   用户名称
     * @param ebookCategory   电子书分类
     * @return 对象列表
     */
    List<EbookShelf> ebookShelfQuarryAll(String userId,String ebookName,String ebookCategory);
}

