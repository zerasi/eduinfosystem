package com.web.sys.pcontroller;

//import com.book.domain.Admin;
//import com.book.domain.ReaderCard;
//import com.book.service.LoginService;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.web.common.bean.DataRes;
import com.web.sys.bean.ProDocument;
import com.web.sys.bean.ProPaper;
import com.web.sys.bean.SysUserRole;
import com.web.sys.service.ProDocumentService;
import com.web.sys.service.ProPaperService;
import com.web.sys.service.SysUserRoleService;
import com.web.sys.utils.R;
import com.web.sys.bean.SysUser;
import com.web.sys.service.SysUserService;
import com.web.sys.utils.T;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//标注为一个Spring mvc的Controller
@Controller
public class SystemController {


    @Autowired
    SysUserService userService;



    @Autowired
    SysUserRoleService user_roleService;
    //负责处理login.html请求
    @ApiOperation(notes="请求首页",value = "request index")
    @RequestMapping(value = {"/index","/"},method=RequestMethod.GET)
    public String toLogin(HttpServletRequest request, Model model){

        return "admin/index";
    }


    @RequestMapping(value = {"/reg"},method=RequestMethod.GET)
    public String reg(HttpServletRequest request, Model model){

        return "admin/reg";
    }

    @Autowired
    ProDocumentService documentService;
    @Autowired
    ProPaperService paperService;

    @RequestMapping(value = {"/dataInfo"},method=RequestMethod.GET)
    @ResponseBody
    public Object dataInfo(HttpServletRequest request, Model model){
        Map<String,Object> ret = new HashMap<>();
        SysUser user = new SysUser();
        user.setItype((short)0);
        ret.put("stn",userService.selectAll(user).size());
        user.setItype((short)1);
        ret.put("ten",userService.selectAll(user).size());
        ret.put("pan",paperService.selectAll(new ProPaper()).size());
        ret.put("don",documentService.selectAll(new ProDocument()).size());
        return DataRes.success(ret);
    }

    @RequestMapping(value = {"/login"},method=RequestMethod.GET)
    public String login(HttpServletRequest request, Model model){
        String msg = request.getParameter("m");
        model.addAttribute("msg",msg);
        SysUser user = (SysUser)request.getSession().getAttribute("user");

        return "admin/login";
    }

    @RequestMapping(value = {"/pg/{path}/{name}"},method=RequestMethod.GET)
    public String personal(HttpServletRequest request, Model model,@PathVariable String path,@PathVariable String name){
        return path + "/" + name;
    }

    //配置404页面
    @RequestMapping("*")
    public String notFind() {
        return "404";
    }
}