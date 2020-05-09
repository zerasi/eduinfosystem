package com.web.sys.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.web.sys.bean.SysUserRole;
import com.web.sys.service.SysUserRoleService;
import com.web.sys.utils.*;
import com.web.sys.bean.SysRole;
import com.web.sys.bean.SysUser;
import com.web.sys.service.SysRoleService;
import com.web.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/log")
public class LoginController {

    @Autowired
    SysUserService userService;

    @Autowired
    SysRoleService roleService;

    @Autowired
    SysUserRoleService user_roleService;

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public @ResponseBody Object toLogin(HttpServletRequest request,Model model) {
        SysUser user = (SysUser)request.getSession().getAttribute("user");
        if(user == null){
            return "";
        }else{
            return "";
        }
    }
    @RequestMapping(value = "/password", method = {RequestMethod.POST})
    public @ResponseBody Object password(HttpServletRequest request,Model model) {

        String op = request.getParameter("oldPassword");
        String np = request.getParameter("newPasswd");
        String rnp = request.getParameter("reNewPasswd");

        SysUser user = (SysUser)request.getSession().getAttribute("user");
        SysUser nSysUser = userService.selectByPrimaryKey(user);
        String msg = null;
        if(T.isNullOrWhite(op)){
            msg = "旧密码不能为空";
        }else if(T.isNullOrWhite(np)){
            msg = "新密码不能为空";
        }else if(T.isNullOrWhite(rnp)||!np.equals(rnp)){
            msg = "两次输入密码不一致";
        }else if(nSysUser == null){
            msg = "用户不存在";
        }else if(!nSysUser.getPassword().equals(op)){
            msg = "旧密码不正确";
        }else if(op.equals(np)){
            msg = "修改前后密码不能一样";
        }else{
            nSysUser.setPassword(np);
            int flag = userService.update(nSysUser);
            if(flag == 1){
                msg = "修改成功";
                return R.ret(R.OK,msg);
            }else{
                msg = "修改失败";
            }
        }
        return R.ret(R.ERR,msg);
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public @ResponseBody Object loginCheck(Integer typ, HttpServletRequest request,Model model){
        if(typ == null || (typ != 1 && typ != 2 && typ != 0)){
            return R.ret(R.ERR,"请选择登录角色");
        }
        HashMap<String, Object> res = new HashMap<String, Object>();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(username == null ||"".equals(username)){
            return  R.ret(R.ERR,"用户名不能为空");
        };
        if(password == null || "".equals(password)){
            return  R.ret(R.ERR,"密码不能为空");
        }

        SysUser su = new SysUser();
        su.setUsername(username);
        List<SysUser> list = userService.queryByCondition(su);
        if(list.size() < 1){
            return  R.ret(R.ERR,"用户名或密码错误");
        }
        SysUser user = list.get(0);
        if(user == null || ! password.equals(user.getPassword())){
            return  R.ret(R.ERR,"用户名或密码错误");
        }

        SysUserRole sur = new SysUserRole();
        sur.setUserId(user.getId());
        List<SysUserRole> surs = user_roleService.queryByCondition(sur);
        if(user.getItype() ==  1&& typ != 1){
            return R.ret(R.ERR,"教师不存在");
        }

        if(user.getItype() == 0 && typ != 0){
            return R.ret(R.ERR,"学生不存在");
        }

        if(user.getItype() == 2 && typ != 2){
            return R.ret(R.ERR,"管理员不存在");
        }

        user.setLastlogin(new Date());
        userService.update(user);
        request.getSession().setAttribute("user",user);
        request.getSession().setAttribute("isLogin",true);

        return  R.ret(R.OK,user);
    }

    @RequestMapping(value = "/reg", method = {RequestMethod.POST})
    public @ResponseBody Object reg(HttpServletRequest request,Model model){
        HashMap<String, Object> res = new HashMap<String, Object>();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
//        String vcode = request.getParameter("vcode");
        if(username == null ||"".equals(username)){
            return  R.ret(R.ERR,"用户名不能为空");
        };
        if(password == null || "".equals(password)){
            return  R.ret(R.ERR,"密码不能为空");
        }
        if(!password.equals(repassword)){
            return  R.ret(R.ERR,"两次输入密码不一致");
        }

        SysUser su = new SysUser();
        su.setUsername(username);
        List<SysUser> list = userService.queryByCondition(su);
        if(list.size() > 0){
            return  R.ret(R.ERR,"用户已存在");
        }

        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setCreatetime(new Date());
        user.setItype((short)0);

        int ret = userService.insert(user);
        if(ret < 1) return R.ret("注册失败");

        return  R.ret(R.OK,user);
    }

    @RequestMapping(value = "/vcode", method = {RequestMethod.GET})
    public void getVECode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String vcode = T.getRandomString(4);
        request.getSession().removeAttribute("vcode");
        request.getSession().removeAttribute("vcodeTime");
        request.getSession().setAttribute("vcode",vcode);
        request.getSession().setAttribute("vcodeTime", LocalDateTime.now());

        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        // 生成图片
        int w = 100, h = 40;
        OutputStream out = response.getOutputStream();
        I.draw(out,vcode,w,h);
    }



    @RequestMapping(value = "/logout", method = {RequestMethod.GET}, produces="application/json;charset=UTF-8")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/login";
    }

    @RequestMapping(value = {"/user"},method={RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object
    loginSysUser(HttpServletRequest request){
        SysUser user = (SysUser)(request.getSession().getAttribute("user"));
        if(user != null){
            SysUserRole sur = new SysUserRole();
            sur.setUserId(user.getId());
            List<SysUserRole> surs = user_roleService.queryByCondition(sur);
            if(surs.size() > 0){
                user.setSurs(surs);
            }
            return R.ret(R.OK,user);
        }
        return R.ret(R.OK,"未登录");
    }
    @RequestMapping(value = "/islogin", method = RequestMethod.GET)
    public @ResponseBody Object islogin(HttpServletRequest request){
        SysUser user = (SysUser)(request.getSession().getAttribute("user"));
        if(user != null){
            return R.ret(R.OK,user);
        }
        return R.ret(R.OK,"未登录");
    }
    //配置404页面
    @RequestMapping("*")
    public String notFind(){
        return "404";
    }


}