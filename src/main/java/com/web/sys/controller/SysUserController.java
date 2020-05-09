package com.web.sys.controller;

import javax.annotation.Resource;

import com.web.common.utils.ExcelUtils;
import com.web.sys.utils.T;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.web.common.bean.DataRes;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import com.web.sys.bean.SysUser;
import com.web.sys.service.SysUserService;
import org.springframework.stereotype.Controller;
//import com.web.common.annotation.Auth;
/**
 *
 * @author zengtp
 *
 */
@Controller
public class SysUserController {

	@Resource
	private SysUserService sysUserService;

	/**
	 * 删除
	 * @param sysUser
	 * @return
	 */
	@RequestMapping("sysUser/deleteByPrimaryKey")
	@ResponseBody
	public DataRes deleteByPrimaryKey(SysUser sysUser, HttpServletRequest request, HttpServletResponse response){
		return DataRes.success(sysUserService.deleteByPrimaryKey(sysUser));
	}

	/**
	 * 保存 如果id存在则修改否则新增
	 * @param sysUser
	 * @return
	 */
	@RequestMapping("sysUser/save")
	@ResponseBody
	public DataRes save(SysUser sysUser, HttpServletRequest request, HttpServletResponse response){
		if(sysUser.getId()==null){
			sysUser.setPassword("123");
			return DataRes.success(sysUserService.insert(sysUser));
		}
		return DataRes.success(sysUserService.update(sysUser));
	}

	@RequestMapping("sysUser/reset")
	@ResponseBody
	public DataRes reset(SysUser sysUser){
		sysUser.setPassword("123");
		return DataRes.success(sysUserService.update(sysUser));
	}

	@RequestMapping("sysUser/updatepassword")
	@ResponseBody
	public DataRes updatepassword(HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		String p1 = request.getParameter("password1");
		String p2 = request.getParameter("password2");
		String p3 = request.getParameter("password3");

		long idInt;
		try{
			idInt = Integer.parseInt(id);
		}catch (Exception ex){
			return DataRes.error();
		}
		SysUser t = new SysUser();
		t.setId(idInt);
		SysUser user = sysUserService.selectByPrimaryKey(t);
		if(!user.getPassword().equals(p1)){
			return DataRes.error("-1","旧密码不正确");
		}
		if(T.isNullOrWhite(p2)||T.isNullOrWhite(p3)||!p2.equals(p3)){
			return DataRes.error("-1","两次密码输入不一致");
		}
		user.setPassword(p2);

		return DataRes.success(sysUserService.update(user));
	}

	/**
	 * 根据主键查询
	 * @param sysUser
	 * @return
	 */
	@RequestMapping("sysUser/selectByPrimaryKey")
	@ResponseBody
	public DataRes selectByPrimaryKey(SysUser sysUser, HttpServletRequest request, HttpServletResponse response){
		return DataRes.success(sysUserService.selectByPrimaryKey(sysUser));
	}

	/**
	 * 根据条件查询
	 */
	@RequestMapping("sysUser/querySysUserByCondition")
	@ResponseBody
	public DataRes queryByCondition(SysUser sysUser, HttpServletRequest request, HttpServletResponse response){
		return DataRes.success(sysUserService.queryByCondition(sysUser));
	}

	/**
	 * 分页查询
	 * @param sysUser 参数
	 * @return
	 */
	@RequestMapping("sysUser/selectAll")
	@ResponseBody
	public DataRes selectAll(SysUser sysUser,HttpServletRequest request, HttpServletResponse response){
		return DataRes.success(sysUserService.selectAllByPaging(sysUser));
	}

	/**
	 * 分页查询
	 * @param sysUser 参数
	 * @return
	 */
	@RequestMapping("sysUser/selectAllStu")
	@ResponseBody
	public DataRes selectAllStu(SysUser sysUser,HttpServletRequest request, HttpServletResponse response){
		sysUser.setItype((short)0);
		return DataRes.success(sysUserService.selectAllByPaging(sysUser));
	}

	@RequestMapping("sysUser/selectAllTea")
	@ResponseBody
	public DataRes selectAllTea(SysUser sysUser,HttpServletRequest request, HttpServletResponse response){
		sysUser.setItype((short)1);
		return DataRes.success(sysUserService.selectAllByPaging(sysUser));
	}

	/**
	 * 导出数据
	 * @param tests 参数
	 * @return
	 */
	@RequestMapping("sysUser/export")
	public void export(SysUser sysUser,HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<SysUser> list= sysUserService.selectAll(sysUser);
		Map<String, String> header = new LinkedHashMap<>();
		header.put("id", "主键ID");
		header.put("username", "用户名");
		header.put("truename", "真实姓名");
		header.put("password", "密码");
		header.put("phone", "电话");
		header.put("email", "邮箱");
		header.put("address", "地址");
//		header.put("sex_", "{\"name\":\"性别\",\"0\":\"男\",\"1\":\"女\"}");
		header.put("sex", "性别");
		header.put("photoid", "图片ID");
		header.put("brithday_", "出生日期");
		header.put("createtime_", "创建时间");
		header.put("lastlogin_", "最后登录时间");
		ExcelUtils.exportExcel("用户表",header,list,response,request);
	}

	/**
	 * 跳转到列表页面
	 * @return
	 */
	@RequestMapping("sysUser/gotoList")
	public String gotoList(SysUser sysUser, HttpServletRequest request, HttpServletResponse response){
		return "sys/sys_user_list";
	}

	/**
	 * 跳转到详情页面
	 * @return
	 */
	@RequestMapping("sysUser/gotoDetail")
	//@Auth("sysUser/save")
	public String gotoDetail(SysUser sysUser, HttpServletRequest request, HttpServletResponse response){
		if(sysUser.getId()!=null){
			request.setAttribute("sys_user",sysUserService.selectByPrimaryKey(sysUser));
		}else {
			request.setAttribute("sys_user",sysUser);
		}
		return "sys/sys_user_detail";
	}
	@RequestMapping("sysUser/gotoTeaDetail")
	//@Auth("sysUser/save")
	public String gotoTeaDetail(SysUser sysUser, HttpServletRequest request, HttpServletResponse response){
		if(sysUser.getId()!=null){
			request.setAttribute("sys_user",sysUserService.selectByPrimaryKey(sysUser));
		}else {
			request.setAttribute("sys_user",sysUser);
		}
		return "sys/sys_user_tea_detail";
	}

	@RequestMapping("sysUser/gotoStuDetail")
	//@Auth("sysUser/save")
	public String gotoStuDetail(SysUser sysUser, HttpServletRequest request, HttpServletResponse response){
		if(sysUser.getId()!=null){
			request.setAttribute("sys_user",sysUserService.selectByPrimaryKey(sysUser));
		}else {
			request.setAttribute("sys_user",sysUser);
		}
		return "sys/sys_user_stu_detail";
	}

}
