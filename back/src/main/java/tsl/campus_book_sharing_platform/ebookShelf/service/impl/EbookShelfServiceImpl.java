package tsl.campus_book_sharing_platform.ebookShelf.service.impl;

import tsl.campus_book_sharing_platform.ebookShelf.dao.EbookShelfDao;
import tsl.campus_book_sharing_platform.ebookShelf.service.EbookShelfService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (EbookShelf)表服务实现类
 *
 * @author makejava
 * @since 2022-03-01 22:00:18
 */
@Service("ebookShelfService")
public class EbookShelfServiceImpl implements EbookShelfService {
    @Resource
    private EbookShelfDao dao;


}
