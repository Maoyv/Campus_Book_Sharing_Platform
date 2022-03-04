package tsl.campus_book_sharing_platform.user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TSL
 */
@Data
public class BookCategory implements Serializable {

    /**
     * 图书类别
     */
    private String bookCategory;
}
