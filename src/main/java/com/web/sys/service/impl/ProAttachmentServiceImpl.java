package com.web.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import java.util.List;
import com.web.common.utils.PagingUtils;
import com.web.sys.bean.ProAttachment;
import com.web.sys.service.ProAttachmentService;
import com.web.sys.dao.ProAttachmentDao;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProAttachmentServiceImpl implements ProAttachmentService {

	@Resource
	private ProAttachmentDao proAttachmentDao;


    @Override
    public ProAttachmentDao initDao() {
        return proAttachmentDao;
    }
}
