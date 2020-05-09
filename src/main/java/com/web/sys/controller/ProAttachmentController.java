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
import com.web.sys.bean.ProAttachment;
import com.web.sys.service.ProAttachmentService;
import org.springframework.stereotype.Controller;
// import com.web.common.annotation.Auth;
/**
 * 
 * @author zengtp
 *
 */
@Controller
public class ProAttachmentController {
	
	@Resource
	private ProAttachmentService proAttachmentService;

	/**
	 * 删除
	 * @param proAttachment
	 * @return
	 */
	@RequestMapping("proAttachment/deleteByPrimaryKey")
	@ResponseBody
	public DataRes deleteByPrimaryKey(ProAttachment proAttachment, HttpServletRequest request, HttpServletResponse response){
		return DataRes.success(proAttachmentService.deleteByPrimaryKey(proAttachment));
	}

    /**
	 * 保存 如果id存在则修改否则新增
	 * @param proAttachment
	 * @return
	 */
	@RequestMapping("proAttachment/save")
	@ResponseBody
	public DataRes save(ProAttachment proAttachment, HttpServletRequest request, HttpServletResponse response){
		if(proAttachment.getId()==null){
			return DataRes.success(proAttachmentService.insert(proAttachment));
		}
		return DataRes.success(proAttachmentService.update(proAttachment));
	}

    /**
     * 根据主键查询
     * @param proAttachment
     * @return
     */
	@RequestMapping("proAttachment/selectByPrimaryKey")
	@ResponseBody
	public DataRes selectByPrimaryKey(ProAttachment proAttachment, HttpServletRequest request, HttpServletResponse response){
    	return DataRes.success(proAttachmentService.selectByPrimaryKey(proAttachment));
    }

    /**
	* 根据条件查询
	*/
	@RequestMapping("proAttachment/queryProAttachmentByCondition")
	@ResponseBody
	public DataRes queryByCondition(ProAttachment proAttachment, HttpServletRequest request, HttpServletResponse response){
    	return DataRes.success(proAttachmentService.queryByCondition(proAttachment));
    }

   /**
	* 分页查询
	* @param proAttachment 参数
	* @return
	*/
	@RequestMapping("proAttachment/selectAll")
	@ResponseBody
	public DataRes selectAll(ProAttachment proAttachment,HttpServletRequest request, HttpServletResponse response){
    	return DataRes.success(proAttachmentService.selectAllByPaging(proAttachment));
    }

	/**
	* 导出数据
	* @param tests 参数
	* @return
	*/
	@RequestMapping("proAttachment/export")
	public void export(ProAttachment proAttachment,HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ProAttachment> list= proAttachmentService.selectAll(proAttachment);
		Map<String, String> header = new LinkedHashMap<>();
        header.put("id", "");
        header.put("filename", "");
		header.put("uploadtime_", "");
        header.put("isinuse", "");
        header.put("content", "");
		ExcelUtils.exportExcel("",header,list,response,request);
    }

	/**
	* 跳转到列表页面
	* @return
	*/
	@RequestMapping("proAttachment/gotoList")
	public String gotoList(ProAttachment proAttachment, HttpServletRequest request, HttpServletResponse response){
		return "sys/pro_attachment_list";
	}

	/**
	* 跳转到详情页面
	* @return
	*/
	@RequestMapping("proAttachment/gotoDetail")
	// @Auth("proAttachment/save")
	public String gotoDetail(ProAttachment proAttachment, HttpServletRequest request, HttpServletResponse response){
		if(proAttachment.getId()!=null){
			request.setAttribute("pro_attachment",proAttachmentService.selectByPrimaryKey(proAttachment));
		}else {
			request.setAttribute("pro_attachment",proAttachment);
		}
		return "sys/pro_attachment_detail";
	}
}
