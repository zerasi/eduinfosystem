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
import com.web.sys.bean.SysRole;
import com.web.sys.service.SysRoleService;
import org.springframework.stereotype.Controller;
// import com.web.common.annotation.Auth;
/**
 * 
 * @author zengtp
 *
 */
@Controller
public class SysRoleController {
	
	@Resource
	private SysRoleService sysRoleService;

	/**
	 * 删除
	 * @param sysRole
	 * @return
	 */
	@RequestMapping("sysRole/deleteByPrimaryKey")
	@ResponseBody
	public DataRes deleteByPrimaryKey(SysRole sysRole, HttpServletRequest request, HttpServletResponse response){
		return DataRes.success(sysRoleService.deleteByPrimaryKey(sysRole));
	}

    /**
	 * 保存 如果id存在则修改否则新增
	 * @param sysRole
	 * @return
	 */
	@RequestMapping("sysRole/save")
	@ResponseBody
	public DataRes save(SysRole sysRole, HttpServletRequest request, HttpServletResponse response){
		if(sysRole.getId()==null){
			return DataRes.success(sysRoleService.insert(sysRole));
		}
		return DataRes.success(sysRoleService.update(sysRole));
	}

    /**
     * 根据主键查询
     * @param sysRole
     * @return
     */
	@RequestMapping("sysRole/selectByPrimaryKey")
	@ResponseBody
	public DataRes selectByPrimaryKey(SysRole sysRole, HttpServletRequest request, HttpServletResponse response){
    	return DataRes.success(sysRoleService.selectByPrimaryKey(sysRole));
    }

    /**
	* 根据条件查询
	*/
	@RequestMapping("sysRole/querySysRoleByCondition")
	@ResponseBody
	public DataRes queryByCondition(SysRole sysRole, HttpServletRequest request, HttpServletResponse response){
    	return DataRes.success(sysRoleService.queryByCondition(sysRole));
    }

   /**
	* 分页查询
	* @param sysRole 参数
	* @return
	*/
	@RequestMapping("sysRole/selectAll")
	@ResponseBody
	public DataRes selectAll(SysRole sysRole,HttpServletRequest request, HttpServletResponse response){
    	return DataRes.success(sysRoleService.selectAllByPaging(sysRole));
    }

	/**
	* 导出数据
	* @param tests 参数
	* @return
	*/
	@RequestMapping("sysRole/export")
	public void export(SysRole sysRole,HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SysRole> list= sysRoleService.selectAll(sysRole);
		Map<String, String> header = new LinkedHashMap<>();
        header.put("id", "");
        header.put("name", "");
        header.put("num", "");
		ExcelUtils.exportExcel("",header,list,response,request);
    }

	/**
	* 跳转到列表页面
	* @return
	*/
	@RequestMapping("sysRole/gotoList")
	public String gotoList(SysRole sysRole, HttpServletRequest request, HttpServletResponse response){
		return "sys/sys_role_list";
	}

	/**
	* 跳转到详情页面
	* @return
	*/
	@RequestMapping("sysRole/gotoDetail")
	// @Auth("sysRole/save")
	public String gotoDetail(SysRole sysRole, HttpServletRequest request, HttpServletResponse response){
		if(sysRole.getId()!=null){
			request.setAttribute("sys_role",sysRoleService.selectByPrimaryKey(sysRole));
		}else {
			request.setAttribute("sys_role",sysRole);
		}
		return "sys/sys_role_detail";
	}
}
