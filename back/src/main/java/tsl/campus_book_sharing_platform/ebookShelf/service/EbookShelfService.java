package tsl.campus_book_sharing_platform.ebookShelf.service;

import tsl.campus_book_sharing_platform.uitl.ResultFormat;

import javax.servlet.http.HttpSession;

/**
 * (EbookShelf)表服务接口
 *
 * @author makejava
 * @since 2022-03-01 22:00:18
 */
public interface EbookShelfService {

    /**
     * 书架-取消收藏
     *
     * @param ebookName   电子书名称
     * @return 对象列表
     */
    ResultFormat ebookShelfCancelCollection(String ebookName, HttpSession session);

    /**
     * 书架-分页查询
     *
     * @param ebookName   电子书名称
     * @param ebookCategory   电子书分类
     * @return 对象列表
     */
    ResultFormat ebookShelfQuarryAll(String ebookName,String ebookCategory, HttpSession session);

    /**
     * 读取电子书
     *
     * @param ebookName   电子书名称
     * @param ebookRead   进度
     * @param readState   状态（审核/阅读）
     * @return 对象列表
     */
    ResultFormat ebookShelfRead(Integer ebookRead,String ebookName,String readState,HttpSession session);
}
