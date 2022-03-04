package tsl.campus_book_sharing_platform.uitl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tsl.campus_book_sharing_platform.user.entity.BookCategory;

import java.util.List;

/**
 * @author TSL
 */
@Getter
@Setter
@ToString
public class LoginSubmit {

    /**
     * 昵称
     */
    private String userId;

    /**
     * 昵称
     */
    private String name;

    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 角色
     */
    private String role;

    /**
     * 权限
     */
    private List<String> permissions;

    /**
     * Token
     */
    private String token;

    /**
     * 书籍类别
     */
    private List<BookCategory> bookCategory;
}
