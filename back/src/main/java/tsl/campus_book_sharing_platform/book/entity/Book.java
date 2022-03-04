package tsl.campus_book_sharing_platform.book.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Book)实体类
 *
 * @author makejava
 * @since 2022-01-04 12:13:50
 */
@Data
public class Book implements Serializable {
    private static final long serialVersionUID = 622851563991129412L;
    /**
     * 书籍ID
     */
    private String bookId;
    /**
     * 书籍名称
     */
    private String bookName;
    /**
     * 书籍分类
     */
    private String bookCategory;
    /**
     * 书籍简介(0-124位)
     */
    private String bookIntroduction;
    /**
     * 书籍数量
     */
    private Integer bookNumber;
    /**
     * 已被借阅数量
     */
    private Integer bookBorrowed;
    /**
     * 预约借阅数量
     */
    private Integer bookAppointment;
    /**
     * 可预约借阅数量
     */
    private Integer bookAvailable;

}

