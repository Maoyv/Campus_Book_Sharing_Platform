package tsl.campus_book_sharing_platform.permissions.service.impl;

import tsl.campus_book_sharing_platform.permissions.dao.PermissionsDao;
import tsl.campus_book_sharing_platform.permissions.service.PermissionsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Permissions)表服务实现类
 *
 * @author makejava
 * @since 2021-12-28 13:05:42
 */
@Service("permissionsService")
public class PermissionsServiceImpl implements PermissionsService {
    @Resource
    private PermissionsDao permissionsDao;

    @Override
    public List<String> queryByPermissionsId(String permissionsId) {
        return permissionsDao.queryByPermissionsId(permissionsId);
    }
}
