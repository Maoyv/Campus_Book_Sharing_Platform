package tsl.campus_book_sharing_platform.permissions.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Permissions)实体类
 *
 * @author makejava
 * @since 2021-12-28 13:05:42
 */
@Data
public class Permissions implements Serializable {
    private static final long serialVersionUID = 442307708091264499L;
    /**
     * 权限ID
     */
    private String permissionsId;
    /**
     * 权限名称
     */
    private String permissionsName;
    /**
     * 权限编码
     */
    private String permissionsCoding;

}

