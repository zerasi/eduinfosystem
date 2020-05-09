package com.web.sys.controller;

import javax.annotation.Resource;

import com.web.common.utils.ExcelUtils;
import com.web.sys.bean.SysUser;
import com.web.sys.service.SysUserService;
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
import com.web.sys.bean.ProCourse;
import com.web.sys.service.ProCourseService;
import org.springframework.stereotype.Controller;
//import com.web.common.annotation.Auth;
/**
 * 
 * @author zengtp
 *
 */
@Controller
public class ProCourseController {
	
	@Resource
	private ProCourseService proCourseService;

	@Resource
	private SysUserService userService;
	/**
	 * 删除
	 * @param proCourse
	 * @return
	 */
	@RequestMapping("proCourse/deleteByPrimaryKey")
	@ResponseBody
	public DataRes deleteByPrimaryKey(ProCourse proCourse, HttpServletRequest request, HttpServletResponse response){
		return DataRes.success(proCourseService.deleteByPrimaryKey(proCourse));
	}

    /**
	 * 保存 如果id存在则修改否则新增
	 * @param proCourse
	 * @return
	 */
	@RequestMapping("proCourse/save")
	@ResponseBody
	public DataRes save(ProCourse proCourse, HttpServletRequest request, HttpServletResponse response){
		if(proCourse.getId()==null){
			return DataRes.success(proCourseService.insert(proCourse));
		}
		return DataRes.success(proCourseService.update(proCourse));
	}

    /**
     * 根据主键查询
     * @param proCourse
     * @return
     */
	@RequestMapping("proCourse/selectByPrimaryKey")
	@ResponseBody
	public DataRes selectByPrimaryKey(ProCourse proCourse, HttpServletRequest request, HttpServletResponse response){
    	return DataRes.success(proCourseService.selectByPrimaryKey(proCourse));
    }

    /**
	* 根据条件查询
	*/
	@RequestMapping("proCourse/queryProCourseByCondition")
	@ResponseBody
	public DataRes queryByCondition(ProCourse proCourse, HttpServletRequest request, HttpServletResponse response){
    	return DataRes.success(proCourseService.queryByCondition(proCourse));
    }

   /**
	* 分页查询
	* @param proCourse 参数
	* @return
	*/
   @RequestMapping("proCourse/selectAll")
   @ResponseBody
   public DataRes selectAll(ProCourse proCourse,Integer me, HttpServletRequest request, HttpServletResponse response){
   		if(!T.isNullOrWhite(proCourse.getUsername())){
   			proCourse.setUsername("%" + proCourse.getUsername() + "%");
		}
		SysUser user = T.getUser(request);
   		if(me != null && me == 1){
   			proCourse.setMeUser(user.getId());
		}
	   return DataRes.success(proCourseService.selectAllByPaging(proCourse));
   }

	@RequestMapping("proCourse/selectMyAll")
	@ResponseBody
	public DataRes selectMyAll(ProCourse proCourse,HttpServletRequest request, HttpServletResponse response){
   		SysUser user = (SysUser) request.getSession().getAttribute("user");
   		proCourse.setUserId(user.getId());
		return DataRes.success(proCourseService.selectAllByPaging(proCourse));
	}

	/**
	* 导出数据
	* @param tests 参数
	* @return
	*/
	@RequestMapping("proCourse/export")
	public void export(ProCourse proCourse,HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ProCourse> list= proCourseService.selectAll(proCourse);
		Map<String, String> header = new LinkedHashMap<>();
        header.put("id", "课程ID");
        header.put("name", "课程名称");
        header.put("score", "学分");
		header.put("gettime_", "获得时间");
        header.put("userId", "学生ID");
		ExcelUtils.exportExcel("课程ID",header,list,response,request);
    }

	/**
	* 跳转到列表页面
	* @return
	*/
	@RequestMapping("proCourse/gotoList")
	public String gotoList(ProCourse proCourse, HttpServletRequest request, HttpServletResponse response){
		return "sys/pro_course_list";
	}

	/**
	* 跳转到详情页面
	* @return
	*/
	@RequestMapping("proCourse/gotoDetail")
	//@Auth("proCourse/save")
	public String gotoDetail(ProCourse proCourse, HttpServletRequest request, HttpServletResponse response) {
		if (proCourse.getId() != null) {
			request.setAttribute("pro_course", proCourseService.selectByPrimaryKey(proCourse));
		} else {
			request.setAttribute("pro_course", proCourse);
		}
		SysUser t = new SysUser();
		t.setItype((short) 0);
		request.setAttribute("users", userService.queryByCondition(t));
		return "sys/pro_course_detail";

	}
		@RequestMapping("proCourse/gotoDetailShow")
		//@Auth("proCourse/save")
		public String gotoDetailShow(ProCourse proCourse, HttpServletRequest request, HttpServletResponse response){
			if(proCourse.getId()!=null){
				request.setAttribute("pro_course",proCourseService.selectByPrimaryKey(proCourse));
			}else {
				request.setAttribute("pro_course",proCourse);
			}
			SysUser t = new SysUser();
			t.setItype((short)0);
			request.setAttribute("users",userService.queryByCondition(t));
			return "sys/pro_course_detail_show";
		}
}
