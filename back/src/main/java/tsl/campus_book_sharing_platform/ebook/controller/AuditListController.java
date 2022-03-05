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
@RequestMapping("auditList")
public class AuditListController {
    /**
     * 服务对象
     */
    @Resource
    private EbookService service;


    /**
     * 审核列表-条件查询
     *
     * @param ebookCategory   电子书分类
     * @param ebookName   电子书名称
     * @return 对象列表
     */
    @RequestMapping(value = "/queryAll",method = RequestMethod.GET)
    public ResultFormat queryAll(String ebookCategory, String ebookName, HttpSession session){
        return service.auditListQueryAll( ebookCategory, ebookName, session);
    }

    /**
     * 审核列表-上传成功
     *
     * @param ebookId   电子书ID
     * @return 对象列表
     */
    @RequestMapping(value = "/adopt",method = RequestMethod.GET)
    public ResultFormat adopt(String ebookId,String ebookName,HttpSession session){
        return service.update(ebookId,ebookName,"上传成功", session);
    }

    /**
     * 审核列表-驳回
     *
     * @param ebookId   电子书ID
     * @return 对象列表
     */
    @RequestMapping(value = "/reject",method = RequestMethod.GET)
    public ResultFormat reject(String ebookId,String ebookName,HttpSession session){
        return service.update(ebookId,ebookName,"已驳回", session);
    }
}

