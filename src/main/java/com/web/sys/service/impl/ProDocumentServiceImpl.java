package com.web.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import java.util.List;
import com.web.common.utils.PagingUtils;
import com.web.sys.bean.ProDocument;
import com.web.sys.service.ProDocumentService;
import com.web.sys.dao.ProDocumentDao;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProDocumentServiceImpl implements ProDocumentService {

	@Resource
	private ProDocumentDao proDocumentDao;


    @Override
    public ProDocumentDao initDao() {
        return proDocumentDao;
    }
}
