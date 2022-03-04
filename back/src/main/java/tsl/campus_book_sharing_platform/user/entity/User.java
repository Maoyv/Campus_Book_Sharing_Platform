package tsl.campus_book_sharing_platform.user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2021-12-01 16:46:50
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 265857679385025540L;
    /**
     * ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户密码
     */
    private String userPassword;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 权限ID
     */
    private String permissionsId;
    /**
     * 头像地址
     */
    private String portrait;
}

