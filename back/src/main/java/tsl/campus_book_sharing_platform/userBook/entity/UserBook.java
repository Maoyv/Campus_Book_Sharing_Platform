package tsl.campus_book_sharing_platform.userBook.entity;

import lombok.Data;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * (UserBook)实体类
 *
 * @author makejava
 * @since 2022-01-04 12:14:06
 */
@Data
public class UserBook implements Serializable {
    private static final long serialVersionUID = -59573053471912357L;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 图书ID
     */
    private String bookId;
    /**
     * 状态
     */
    private String state;
    /**
     * 预约时间
     */
    private LocalDate appointmentTime;
    /**
     * 预约过期时间
     */
    private LocalDate expirationTime;


}

