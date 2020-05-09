package com.web.sys.service.impl;

import javax.annotation.Resource;
import javax.management.relation.Role;

import com.web.sys.bean.SysUser;
import org.springframework.stereotype.Service;
import java.util.List;
import com.web.common.utils.PagingUtils;
import com.web.sys.bean.SysRole;
import com.web.sys.service.SysRoleService;
import com.web.sys.dao.SysRoleDao;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

	@Resource
	private SysRoleDao sysRoleDao;


    @Override
    public SysRoleDao initDao() {
        return sysRoleDao;
    }

}
