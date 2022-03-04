package tsl.campus_book_sharing_platform.ebookShelf.controller;

import tsl.campus_book_sharing_platform.ebookShelf.service.EbookShelfService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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


}

