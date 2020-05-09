package com.web.sys.controller;

import javax.annotation.Resource;

import com.web.common.utils.ExcelUtils;
import com.web.sys.bean.SysUser;
import com.web.sys.service.SysUserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.web.common.bean.DataRes;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import com.web.sys.bean.ProPaper;
import com.web.sys.service.ProPaperService;
import org.springframework.stereotype.Controller;
//import com.web.common.annotation.Auth;
/**
 * 
 * @author zengtp
 *
 */
@Controller
public class ProPaperController {
	
	@Resource
	private ProPaperService proPaperService;

	@Resource
	private SysUserService userService;
	/**
	 * 删除
	 * @param proPaper
	 * @return
	 */
	@RequestMapping("proPaper/deleteByPrimaryKey")
	@ResponseBody
	public DataRes deleteByPrimaryKey(ProPaper proPaper, HttpServletRequest request, HttpServletResponse response){
		return DataRes.success(proPaperService.deleteByPrimaryKey(proPaper));
	}

    /**
	 * 保存 如果id存在则修改否则新增
	 * @param proPaper
	 * @return
	 */
	@RequestMapping("proPaper/save")
	@ResponseBody
	public DataRes save(ProPaper proPaper, HttpServletRequest request, HttpServletResponse response){
		if(proPaper.getId()==null){
			return DataRes.success(proPaperService.insert(proPaper));
		}
		return DataRes.success(proPaperService.update(proPaper));
	}

    /**
     * 根据主键查询
     * @param proPaper
     * @return
     */
	@RequestMapping("proPaper/selectByPrimaryKey")
	@ResponseBody
	public DataRes selectByPrimaryKey(ProPaper proPaper, HttpServletRequest request, HttpServletResponse response){
    	return DataRes.success(proPaperService.selectByPrimaryKey(proPaper));
    }

    /**
	* 根据条件查询
	*/
	@RequestMapping("proPaper/queryProPaperByCondition")
	@ResponseBody
	public DataRes queryByCondition(ProPaper proPaper, HttpServletRequest request, HttpServletResponse response){
    	return DataRes.success(proPaperService.queryByCondition(proPaper));
    }

   /**
	* 分页查询
	* @param proPaper 参数
	* @return
	*/
   @RequestMapping("proPaper/selectAll")
   @ResponseBody
   public DataRes selectAll(ProPaper proPaper,HttpServletRequest request, HttpServletResponse response){
	   return DataRes.success(proPaperService.selectAllByPaging(proPaper));
   }

	@RequestMapping("proPaper/selectMyAll")
	@ResponseBody
	public DataRes selectMyAll(ProPaper proPaper,HttpServletRequest request, HttpServletResponse response){
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		proPaper.setUserId(user.getId());
		return DataRes.success(proPaperService.selectAllByPaging(proPaper));
	}

	/**
	* 导出数据
	* @param tests 参数
	* @return
	*/
	@RequestMapping("proPaper/export")
	public void export(ProPaper proPaper,HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ProPaper> list= proPaperService.selectAll(proPaper);
		Map<String, String> header = new LinkedHashMap<>();
        header.put("id", "已发表文章ID");
        header.put("name", "名称");
		header.put("pubtime_", "发表时间");
        header.put("attachid", "附件ID");
        header.put("teachername", "指导老师");
        header.put("userId", "学生ID");
		ExcelUtils.exportExcel("已发表文章ID",header,list,response,request);
    }

	/**
	* 跳转到列表页面
	* @return
	*/
	@RequestMapping("proPaper/gotoList")
	public String gotoList(ProPaper proPaper, HttpServletRequest request, HttpServletResponse response){
		return "sys/pro_paper_list";
	}

	/**
	* 跳转到详情页面
	* @return
	*/
	@RequestMapping("proPaper/gotoDetail")
	//@Auth("proPaper/save")
	public String gotoDetail(ProPaper proPaper, HttpServletRequest request, HttpServletResponse response){
		if(proPaper.getId()!=null){
			request.setAttribute("pro_paper",proPaperService.selectByPrimaryKey(proPaper));
		}else {
			request.setAttribute("pro_paper",proPaper);
		}
		SysUser t = new SysUser();
		t.setItype((short)0);
		request.setAttribute("users",userService.queryByCondition(t));
		return "sys/pro_paper_detail";
	}


	@RequestMapping("proPaper/gotoDetailShow")
	//@Auth("proPaper/save")
	public String gotoDetailShow(ProPaper proPaper, HttpServletRequest request, HttpServletResponse response){
		if(proPaper.getId()!=null){
			request.setAttribute("pro_paper",proPaperService.selectByPrimaryKey(proPaper));
		}else {
			request.setAttribute("pro_paper",proPaper);
		}
		SysUser t = new SysUser();
		t.setItype((short)0);
		request.setAttribute("users",userService.queryByCondition(t));
		return "sys/pro_paper_detail_show";
	}
}
