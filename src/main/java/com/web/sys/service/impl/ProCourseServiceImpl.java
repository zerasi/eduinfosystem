package com.web.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import java.util.List;
import com.web.common.utils.PagingUtils;
import com.web.sys.bean.ProCourse;
import com.web.sys.service.ProCourseService;
import com.web.sys.dao.ProCourseDao;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProCourseServiceImpl implements ProCourseService {

	@Resource
	private ProCourseDao proCourseDao;


    @Override
    public ProCourseDao initDao() {
        return proCourseDao;
    }
}
