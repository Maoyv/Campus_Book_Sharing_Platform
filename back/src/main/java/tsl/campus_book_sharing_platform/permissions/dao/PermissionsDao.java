package tsl.campus_book_sharing_platform.permissions.dao;

import tsl.campus_book_sharing_platform.permissions.entity.Permissions;

import java.util.List;

/**
 * (Permissions)表数据库访问层
 *
 * @author makejava
 * @since 2021-12-28 13:05:42
 */
public interface PermissionsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param permissionsId
     * @return 实例对象
     */
    List<String> queryByPermissionsId(String permissionsId);

}

