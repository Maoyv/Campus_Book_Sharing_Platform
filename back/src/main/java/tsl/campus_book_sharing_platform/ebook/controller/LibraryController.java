package tsl.campus_book_sharing_platform.ebook.controller;

import tsl.campus_book_sharing_platform.ebook.service.EbookService;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("library")
public class LibraryController {
    /**
     * 服务对象
     */
    @Resource
    private EbookService service;

    /**
     * 书库-条件查询
     *
     * @param ebookCategory   电子书分类
     * @param ebookName   电子书名称
     * @return 对象列表
     */
    @RequestMapping(value = "/queryAll",method = RequestMethod.GET)
    public ResultFormat queryAll(String ebookCategory, String ebookName, HttpSession session){
        return service.libraryQueryAll(ebookCategory, ebookName, session);
    }

    /**
     * 书库-收藏
     *
     * @param ebookName   电子书名称
     * @return 对象列表
     */
    @RequestMapping(value = "/collection",method = RequestMethod.GET)
    public ResultFormat libraryCollection(String ebookName, HttpSession session){
        return service.libraryCollection(ebookName, session);
    }

    /**
     * 书库-修改
     *
     * @param ebookBriefInformation   电子书简介
     * @param ebookCategory   电子书分类
     * @param ebookName   电子书名称
     * @return 对象列表
     */
    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public ResultFormat libraryUpdate(String ebookName,String ebookBriefInformation,String ebookCategory, HttpSession session){
        return service.libraryUpdate(ebookName, ebookBriefInformation, ebookCategory, session);
    }

    /**
     * 书库-删除
     *
     * @param ebookName   电子书名称
     * @return 对象列表
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResultFormat libraryDelete(String ebookName, HttpSession session){
        return service.libraryDelete(ebookName, session);
    }
}

