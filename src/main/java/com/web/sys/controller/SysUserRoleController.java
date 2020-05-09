package com.web.sys.controller;

import javax.annotation.Resource;

import com.web.common.utils.ExcelUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.web.common.bean.DataRes;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import com.web.sys.bean.SysUserRole;
import com.web.sys.service.SysUserRoleService;
import org.springframework.stereotype.Controller;
/**
 * 
 * @author zengtp
 *
 */
@Controller
public class SysUserRoleController {
	
	@Resource
	private SysUserRoleService sysUserRoleService;

	/**
	 * 删除
	 * @param sysUserRole
	 * @return
	 */
	@RequestMapping("sysUserRole/deleteByPrimaryKey")
	@ResponseBody
	public DataRes deleteByPrimaryKey(SysUserRole sysUserRole, HttpServletRequest request, HttpServletResponse response){
		return DataRes.success(sysUserRoleService.deleteByPrimaryKey(sysUserRole));
	}

    /**
	 * 保存 如果id存在则修改否则新增
	 * @param sysUserRole
	 * @return
	 */
	@RequestMapping("sysUserRole/save")
	@ResponseBody
	public DataRes save(SysUserRole sysUserRole, HttpServletRequest request, HttpServletResponse response){
		if(sysUserRole.getId()==null){
			return DataRes.success(sysUserRoleService.insert(sysUserRole));
		}
		return DataRes.success(sysUserRoleService.update(sysUserRole));
	}

    /**
     * 根据主键查询
     * @param sysUserRole
     * @return
     */
	@RequestMapping("sysUserRole/selectByPrimaryKey")
	@ResponseBody
	public DataRes selectByPrimaryKey(SysUserRole sysUserRole, HttpServletRequest request, HttpServletResponse response){
    	return DataRes.success(sysUserRoleService.selectByPrimaryKey(sysUserRole));
    }

    /**
	* 根据条件查询
	*/
	@RequestMapping("sysUserRole/querySysUserRoleByCondition")
	@ResponseBody
	public DataRes queryByCondition(SysUserRole sysUserRole, HttpServletRequest request, HttpServletResponse response){
    	return DataRes.success(sysUserRoleService.queryByCondition(sysUserRole));
    }

   /**
	* 分页查询
	* @param sysUserRole 参数
	* @return
	*/
	@RequestMapping("sysUserRole/selectAll")
	@ResponseBody
	public DataRes selectAll(SysUserRole sysUserRole,HttpServletRequest request, HttpServletResponse response){
    	return DataRes.success(sysUserRoleService.selectAllByPaging(sysUserRole));
    }

	/**
	* 导出数据
	* @param tests 参数
	* @return
	*/
	@RequestMapping("sysUserRole/export")
	public void export(SysUserRole sysUserRole,HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SysUserRole> list= sysUserRoleService.selectAll(sysUserRole);
		Map<String, String> header = new LinkedHashMap<>();
        header.put("id", "");
        header.put("userId", "");
        header.put("roleId", "");
		ExcelUtils.exportExcel("",header,list,response,request);
    }

	/**
	* 跳转到列表页面
	* @return
	*/
	@RequestMapping("sysUserRole/gotoList")
	public String gotoList(SysUserRole sysUserRole, HttpServletRequest request, HttpServletResponse response){
		return "sys/sys_user_role_list";
	}

	/**
	* 跳转到详情页面
	* @return
	*/
	@RequestMapping("sysUserRole/gotoDetail")
	// @Auth("sysUserRole/save")
	public String gotoDetail(SysUserRole sysUserRole, HttpServletRequest request, HttpServletResponse response){
		if(sysUserRole.getId()!=null){
			request.setAttribute("sys_user_role",sysUserRoleService.selectByPrimaryKey(sysUserRole));
		}else {
			request.setAttribute("sys_user_role",sysUserRole);
		}
		return "sys/sys_user_role_detail";
	}
}
