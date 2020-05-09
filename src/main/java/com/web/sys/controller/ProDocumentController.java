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

import java.util.Date;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import com.web.sys.bean.ProDocument;
import com.web.sys.service.ProDocumentService;
import org.springframework.stereotype.Controller;
//import com.web.common.annotation.Auth;
/**
 * 
 * @author zengtp
 *
 */
@Controller
public class ProDocumentController {
	
	@Resource
	private ProDocumentService proDocumentService;

	@Resource
	private SysUserService userService;
	/**
	 * 删除
	 * @param proDocument
	 * @return
	 */
	@RequestMapping("proDocument/deleteByPrimaryKey")
	@ResponseBody
	public DataRes deleteByPrimaryKey(ProDocument proDocument, HttpServletRequest request, HttpServletResponse response){
		return DataRes.success(proDocumentService.deleteByPrimaryKey(proDocument));
	}

    /**
	 * 保存 如果id存在则修改否则新增
	 * @param proDocument
	 * @return
	 */
	@RequestMapping("proDocument/save")
	@ResponseBody
	public DataRes save(ProDocument proDocument, HttpServletRequest request, HttpServletResponse response){
		if(proDocument.getId()==null){
			return DataRes.success(proDocumentService.insert(proDocument));
		}
		return DataRes.success(proDocumentService.update(proDocument));
	}

    /**
     * 根据主键查询
     * @param proDocument
     * @return
     */
	@RequestMapping("proDocument/selectByPrimaryKey")
	@ResponseBody
	public DataRes selectByPrimaryKey(ProDocument proDocument, HttpServletRequest request, HttpServletResponse response){
    	return DataRes.success(proDocumentService.selectByPrimaryKey(proDocument));
    }

    /**
	* 根据条件查询
	*/
	@RequestMapping("proDocument/queryProDocumentByCondition")
	@ResponseBody
	public DataRes queryByCondition(ProDocument proDocument, HttpServletRequest request, HttpServletResponse response){
    	return DataRes.success(proDocumentService.queryByCondition(proDocument));
    }

   /**
	* 分页查询
	* @param proDocument 参数
	* @return
	*/
   @RequestMapping("proDocument/selectAll")
   @ResponseBody
   public DataRes selectAll(ProDocument proDocument,HttpServletRequest request, HttpServletResponse response){
	   return DataRes.success(proDocumentService.selectAllByPaging(proDocument));
   }

	@RequestMapping("proDocument/selectMyAll")
	@ResponseBody
	public DataRes selectMyAll(ProDocument proDocument,HttpServletRequest request, HttpServletResponse response){
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		proDocument.setUserId(user.getId());
		return DataRes.success(proDocumentService.selectAllByPaging(proDocument));
	}

	/**
	* 导出数据
	* @param tests 参数
	* @return
	*/
	@RequestMapping("proDocument/export")
	public void export(ProDocument proDocument,HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ProDocument> list= proDocumentService.selectAll(proDocument);
		Map<String, String> header = new LinkedHashMap<>();
        header.put("id", "档案ID");
        header.put("userId", "用户ID");
        header.put("name", "名称");
        header.put("content", "内容");
		header.put("createtime_", "创建时间");
		ExcelUtils.exportExcel("档案ID",header,list,response,request);
    }

	/**
	* 跳转到列表页面
	* @return
	*/
	@RequestMapping("proDocument/gotoList")
	public String gotoList(ProDocument proDocument, HttpServletRequest request, HttpServletResponse response){
		return "sys/pro_document_list";
	}

	/**
	* 跳转到详情页面
	* @return
	*/
	@RequestMapping("proDocument/gotoDetail")
	//@Auth("proDocument/save")
	public String gotoDetail(ProDocument proDocument, HttpServletRequest request, HttpServletResponse response){
		if(proDocument.getId()!=null){
			proDocument.setCreatetime(new Date());
			request.setAttribute("pro_document",proDocumentService.selectByPrimaryKey(proDocument));
		}else {
			request.setAttribute("pro_document",proDocument);
		}
		SysUser t = new SysUser();
		t.setItype((short)0);
		request.setAttribute("users",userService.queryByCondition(t));
		return "sys/pro_document_detail";
	}

	@RequestMapping("proDocument/gotoDetailShow")
	//@Auth("proDocument/save")
	public String gotoDetailShow(ProDocument proDocument, HttpServletRequest request, HttpServletResponse response){
		if(proDocument.getId()!=null){
			request.setAttribute("pro_document",proDocumentService.selectByPrimaryKey(proDocument));
		}else {
			request.setAttribute("pro_document",proDocument);
		}
		SysUser t = new SysUser();
		t.setItype((short)0);
		request.setAttribute("users",userService.queryByCondition(t));
		return "sys/pro_document_detail_show";
	}
}
