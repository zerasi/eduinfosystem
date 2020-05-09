package com.web.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import java.util.List;
import com.web.common.utils.PagingUtils;
import com.web.sys.bean.ProPaper;
import com.web.sys.service.ProPaperService;
import com.web.sys.dao.ProPaperDao;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProPaperServiceImpl implements ProPaperService {

	@Resource
	private ProPaperDao proPaperDao;


    @Override
    public ProPaperDao initDao() {
        return proPaperDao;
    }
}
