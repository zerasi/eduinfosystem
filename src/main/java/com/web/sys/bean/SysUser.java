package com.web.sys.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.common.bean.Page;
import com.web.common.utils.DateUtils;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

/**
* 主键ID
* @author zengtp
*/
public class SysUser extends Page {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String truename;

    /**
     * 密码
     */
    private String password;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * {"name":"性别","0":"男","1":"女"}
     */
    private String sex;

    /**
     * 图片ID
     */
    private String photoid;

    /**
     * 出生日期
     */
    private Date brithday;

    /**
     * 创建时间
     */
    private Date createtime;

    private  Short itype;

    public Short getItype() {
        return itype;
    }

    public void setItype(Short itype) {
        this.itype = itype;
    }

    /**
     * 最后登录时间
     */
    private Date lastlogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename == null ? null : truename.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getPhotoid() {
        return photoid;
    }

    public void setPhotoid(String photoid) {
        this.photoid = photoid == null ? null : photoid.trim();
    }

    @JsonIgnore
    public Date getBrithday() {
        return brithday;
    }

    public void setBrithday(Date brithday) {
        this.brithday = brithday;
    }

    @JsonIgnore
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @JsonIgnore
    public Date getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getSex_() {
        if(StringUtils.isEmpty(sex)){
             return "";
        }else if(sex.equals(0)){
             return "男";
        }else if(sex.equals(1)){
             return "女";
        }
        return "";
    }

    public String getBrithday_() {
        return DateUtils.formatDate(brithday);
    }

    public String getCreatetime_() {
        return DateUtils.formatDateTime(createtime);
    }

    public String getLastlogin_() {
        return DateUtils.formatDateTime(lastlogin);
    }



    private List<SysUserRole> surs;

    public List<SysUserRole> getSurs() {
        return surs;
    }

    public void setSurs(List<SysUserRole> surs) {
        this.surs = surs;
    }

}