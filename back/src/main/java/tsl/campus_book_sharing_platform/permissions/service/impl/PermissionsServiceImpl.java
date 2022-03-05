package tsl.campus_book_sharing_platform.permissions.service.impl;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
/**
 * @author TSL
 * (User)表服务实现类
 * 表明该类（class）或方法（method）受事务控制
 * @param propagation  设置隔离级别
 * @param isolation 设置传播行为
 * @param rollbackFor 设置需要回滚的异常类，默认为RuntimeException
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class PermissionsServiceImpl implements PermissionsService {
    @Resource
    private PermissionsDao permissionsDao;

    @Override
    public List<String> queryByPermissionsId(String permissionsId) {
        return permissionsDao.queryByPermissionsId(permissionsId);
    }
}
