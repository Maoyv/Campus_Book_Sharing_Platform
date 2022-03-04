package tsl.campus_book_sharing_platform.ebook.controller;

import tsl.campus_book_sharing_platform.ebook.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    private EbookService ebookService;


}

