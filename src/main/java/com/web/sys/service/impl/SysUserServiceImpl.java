package com.web.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import java.util.List;
import com.web.common.utils.PagingUtils;
import com.web.sys.bean.SysUser;
import com.web.sys.service.SysUserService;
import com.web.sys.dao.SysUserDao;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {

	@Resource
	private SysUserDao sysUserDao;


    @Override
    public SysUserDao initDao() {
        return sysUserDao;
    }
}
