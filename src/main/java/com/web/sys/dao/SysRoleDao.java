package com.web.sys.dao;

import com.web.sys.bean.SysRole;
import com.web.common.dao.BaseDao;
import com.web.sys.bean.SysUser;

public interface SysRoleDao extends BaseDao<SysRole> {
    public SysRole selectByUserID(SysUser user);
}