package tsl.campus_book_sharing_platform.book.controller;

import tsl.campus_book_sharing_platform.book.entity.Book;
import tsl.campus_book_sharing_platform.book.service.BookService;
import org.springframework.web.bind.annotation.*;
import tsl.campus_book_sharing_platform.uitl.ResultFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * (Book)表控制层
 *
 * @author makejava
 * @since 2022-01-04 12:13:50
 */
@RestController
@RequestMapping("book")
public class BookController {
    /**
     * 服务对象
     */
    @Resource
    private BookService service;

    /**
     * 条件查询
     *
     * @param book 筛选条件
     * @return 查询结果
     */
    @RequestMapping(value = "/queryAll",method = RequestMethod.GET)
    public ResultFormat queryAll(Book book, HttpSession session) {
        return this.service.queryAll(book,session);
    }


    /**
     * 新增数据
     *
     * @param book 实体
     * @return 新增结果
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public ResultFormat add(Book book,HttpSession session) {
        return this.service.insert(book,session);
    }

    /**
     * 编辑数据
     *
     * @param book 实体
     * @return 编辑结果
     */
    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public ResultFormat update(Book book,HttpSession session) {
        return this.service.update(book,session);
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @RequestMapping(value = "/deleteById",method = RequestMethod.GET)
    public ResultFormat deleteById(String id,HttpSession session) {
        return this.service.deleteById(id,session);
    }

}

