package tsl.campus_book_sharing_platform.ebook.service;

import tsl.campus_book_sharing_platform.uitl.ResultFormat;

import javax.servlet.http.HttpSession;

/**
 * (Ebook)表服务接口
 *
 * @author makejava
 * @since 2022-03-01 22:00:02
 */
public interface EbookService {

    /**
     * 上传列表-条件查询
     *
     * @param ebookState   电子书审核状态
     * @param ebookCategory   电子书分类
     * @param ebookName   电子书名称
     * @return 对象列表
     */
    ResultFormat uploadListQueryAll(String ebookState, String ebookCategory, String ebookName, HttpSession session);

    /**
     * 上传列表-删除信息
     *
     * @param ebookId   电子书ID
     */
    ResultFormat uploadListDelete(String ebookId,HttpSession session);

    /**
     * 修改电子书状态
     *
     * @param ebookId   电子书ID
     * @param state   电子书状态
     */
    ResultFormat update(String ebookId,String ebookName,String state,HttpSession session);

    /**
     * 上传列表-新增
     *
     * @param ebookName   电子书名称
     * @param ebookCategory   电子书分类
     * @param ebookSize   电子书大小
     * @param ebookBriefInformation   电子书简介
     */
    ResultFormat uploadListInsert(String ebookName,String ebookCategory,String ebookSize,String ebookBriefInformation,HttpSession session);

    /**
     * 审核列表-条件查询
     *
     * @param ebookCategory   电子书分类
     * @param ebookName   电子书名称
     * @return 对象列表
     */
    ResultFormat auditListQueryAll(String ebookCategory,String ebookName,HttpSession session);

}
