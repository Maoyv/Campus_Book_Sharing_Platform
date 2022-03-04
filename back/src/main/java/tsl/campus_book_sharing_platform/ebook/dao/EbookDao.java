package tsl.campus_book_sharing_platform.ebook.dao;

import tsl.campus_book_sharing_platform.ebook.entity.Ebook;

import java.util.List;

/**
 * (Ebook)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-01 22:00:01
 */
public interface EbookDao {

    /**
     * 上传列表-条件查询
     *
     * @param downloadUserId   上传者ID
     * @param ebookState   电子书审核状态
     * @param ebookCategory   电子书分类
     * @param ebookName   电子书名称
     * @return 对象列表
     */
    List<Ebook> uploadListQueryAll(String downloadUserId,String ebookState,String ebookCategory,String ebookName);

    /**
     * 上传列表-删除信息
     *
     * @param ebookId   电子书ID
     */
    void uploadListDelete(String ebookId);

    /**
     * 修改电子书状态
     *
     * @param ebookId   电子书ID
     * @param state   电子书状态
     */
    void update(String ebookId,String state);

    /**
     * 上传列表-新增
     *
     * @param ebook   电子书信息
     */
    void uploadListInsert(Ebook ebook);

    /**
     * 上传列表-新增
     *
     * @param ebookName   电子书名称
     * @param downloadUserId   上传者ID
     * @return 总列数
     */
    int uploadListSum(String ebookName,String downloadUserId);

    /**
     * 审核列表-条件查询
     *
     * @param ebookCategory   电子书分类
     * @param ebookName   电子书名称
     * @return 对象列表
     */
    List<Ebook> auditListQueryAll(String ebookCategory,String ebookName);

}

