package com.web.sys.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.common.bean.Page;
import com.web.common.utils.DateUtils;
import java.util.Date;
import org.springframework.util.StringUtils;

/**
* 已发表文章ID
* @author wyb
*/
public class ProPaper extends Page {
    /**
     * 已发表文章ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 发表时间
     */
    private Date pubtime;

    /**
     * 附件ID
     */
    private Long attachid;

    /**
     * 指导老师
     */
    private String teachername;

    /**
     * 学生ID
     */
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @JsonIgnore
    public Date getPubtime() {
        return pubtime;
    }

    public void setPubtime(Date pubtime) {
        this.pubtime = pubtime;
    }

    public Long getAttachid() {
        return attachid;
    }

    public void setAttachid(Long attachid) {
        this.attachid = attachid;
    }

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername == null ? null : teachername.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPubtime_() {
        return DateUtils.formatDateTime(pubtime);
    }
}