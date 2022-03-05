package tsl.campus_book_sharing_platform.ebookShelf.controller;

import tsl.campus_book_sharing_platform.ebookShelf.service.EbookShelfService;
import org.springframework.web.bind.annotation.*;
import tsl.campus_book_sharing_platform.uitl.ResultFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * (EbookShelf)表控制层
 *
 * @author makejava
 * @since 2022-03-01 22:00:17
 */
@RestController
@RequestMapping("/ebookShelf")
public class EbookShelfController {
    /**
     * 服务对象
     */
    @Resource
    private EbookShelfService service;

    /**
     * 书架-取消收藏
     *
     * @param ebookName   电子书名称
     * @return 对象列表
     */
    @RequestMapping(value = "cancelCollection",method = RequestMethod.GET)
    public ResultFormat ebookShelfCancelCollection(String ebookName, HttpSession session){
        return service.ebookShelfCancelCollection(ebookName, session);
    }

    /**
     * 书架-分页查询
     *
     * @param ebookName   电子书名称
     * @param ebookCategory   电子书分类
     * @return 对象列表
     */
    @RequestMapping(value = "quarryAll",method = RequestMethod.GET)
    public ResultFormat ebookShelfQuarryAll(String ebookName,String ebookCategory, HttpSession session){
        return service.ebookShelfQuarryAll(ebookName,ebookCategory, session);
    }

}

