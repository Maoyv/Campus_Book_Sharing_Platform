package tsl.campus_book_sharing_platform.ebook.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tsl.campus_book_sharing_platform.ebook.service.EbookService;
import tsl.campus_book_sharing_platform.uitl.ResultFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * (Ebook)表控制层
 *
 * @author makejava
 * @since 2022-03-01 22:00:01
 */
@RestController
@RequestMapping("uploadList")
public class UploadListController {
    /**
     * 服务对象
     */
    @Resource
    private EbookService service;

    /**
     * 上传列表-条件查询
     *
     * @param ebookState   电子书审核状态
     * @param ebookCategory   电子书分类
     * @param ebookName   电子书名称
     * @return 对象列表
     */
    @RequestMapping(value = "/queryAll",method = RequestMethod.GET)
    public ResultFormat queryAll(String ebookState, String ebookCategory, String ebookName, HttpSession session){
        return service.uploadListQueryAll(ebookState, ebookCategory, ebookName, session);
    }

    /**
     * 上传列表-删除电子书信息
     *
     * @param ebookId   电子书ID
     * @return 对象列表
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResultFormat delete(String ebookId,HttpSession session){
        return service.uploadListDelete(ebookId, session);
    }

    /**
     * 上传列表-取消上传
     *
     * @param ebookId   电子书ID
     * @return 对象列表
     */
    @RequestMapping(value = "/cancel",method = RequestMethod.GET)
    public ResultFormat cancel(String ebookId,String ebookName,HttpSession session){
        return service.update(ebookId,ebookName,"已取消", session);
    }

    /**
     * 上传列表-新增电子书
     *
     * @param ebookName   电子书名称
     * @param ebookCategory   电子书分类
     * @param ebookSize   电子书大小
     * @param ebookBriefInformation   电子书简介
     * @return 对象列表
     */
    @RequestMapping(value = "/insert",method = RequestMethod.GET)
    public ResultFormat insert(String ebookName,String ebookCategory,String ebookSize,String ebookBriefInformation,HttpSession session){
        return service.uploadListInsert(ebookName, ebookCategory, ebookSize, ebookBriefInformation, session);
    }
}

