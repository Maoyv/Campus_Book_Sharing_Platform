package tsl.campus_book_sharing_platform.ebookShelf.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (EbookShelf)实体类
 *
 * @author makejava
 * @since 2022-03-01 22:00:17
 */
@Data
public class EbookShelf implements Serializable {
    private static final long serialVersionUID = 220593102378893063L;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 电子书ID
     */
    private String ebookId;
    /**
     * 电子书分类
     */
    private String ebookCategory;
    /**
     * 电子书大小
     */
    private String ebookSize;
    /**
     * 阅读进度
     */
    private Integer ebookRead;

}

