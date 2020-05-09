package com.web.sys.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.common.bean.Page;
import com.web.common.utils.DateUtils;
import java.util.Date;
import org.springframework.util.StringUtils;

/**
* 
* @author zengtp
*/
public class ProAttachment extends Page {
    private Long id;

    private String filename;

    private Date uploadtime;

    private Short isinuse;

    private byte[] content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    @JsonIgnore
    public Date getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
    }

    public Short getIsinuse() {
        return isinuse;
    }

    public void setIsinuse(Short isinuse) {
        this.isinuse = isinuse;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getUploadtime_() {
        return DateUtils.formatDateTime(uploadtime);
    }
}