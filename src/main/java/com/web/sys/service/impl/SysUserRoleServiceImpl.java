package com.web.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import java.util.List;
import com.web.common.utils.PagingUtils;
import com.web.sys.bean.SysUserRole;
import com.web.sys.service.SysUserRoleService;
import com.web.sys.dao.SysUserRoleDao;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysUserRoleServiceImpl implements SysUserRoleService {

	@Resource
	private SysUserRoleDao sysUserRoleDao;


    @Override
    public SysUserRoleDao initDao() {
        return sysUserRoleDao;
    }
}
