package com.web.sys.config;

import com.web.sys.bean.ProAttachment;
import com.web.sys.service.ProAttachmentService;
import com.web.sys.utils.R;
import com.web.sys.utils.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/common")
public class CommonController {
    @Resource
    private ProAttachmentService proAttachmentService;


    @RequestMapping(value="/upload",method = RequestMethod.POST)
    public @ResponseBody Object count(@RequestParam("file") MultipartFile file) {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        byte[] fileByte = null;// file.getBytes();
        try {
            fileByte = file.getBytes();
//            if(fileByte.length > E.MAX_FILE_LENGTH) return R.ret(R.ERR,"�ļ����ܳ���"+E.MAX_FILE_SIZE +"MB");
        } catch (Exception e) {
            return R.ret(R.ERR,"上传失败");
        }

        ProAttachment attachment = new ProAttachment();
        attachment.setUploadtime(new Date());
        attachment.setFilename(fileName);
        attachment.setContent(fileByte);
        int r = proAttachmentService.insert(attachment);
        T.print("ret:",attachment.getId());
        if(r != 1) return R.ret(R.ERR,"上传失败");
        Map<String,Object> ret = new HashMap<>();

        ret.put("id",attachment.getId());
        ret.put("fileName",fileName);
        ret.put("contentType",contentType);
        return R.ret(R.OK,ret);
    }


    @RequestMapping(value="/download",method = RequestMethod.GET)
    public @ResponseBody void getfile(long id,HttpServletResponse response) {
        if(T.isNullOrWhite(id)){
            return ;//R.ret(R.ERR,"id 不能为空");
        }
        ProAttachment t = new ProAttachment();
        t.setId(id);
        ProAttachment attachment = proAttachmentService.selectByPrimaryKey(t);
        if(attachment== null){
            return;// R.ret(R.ERR,"图片不存在");
        }
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + T.getURLEncoderString(attachment.getFilename()));
        if(attachment.getContent()== null){
            return;// R.ret(R.ERR);
        }
    }

    @RequestMapping(value="/get",method = RequestMethod.GET)
    public @ResponseBody void get(long id,HttpServletResponse response) {
        if(T.isNullOrWhite(id)){
            return;// R.ret(R.ERR,"id 不能为空");
        }
        ProAttachment t = new ProAttachment();
        t.setId(id);
        ProAttachment attachment = proAttachmentService.selectByPrimaryKey(t);
        if(attachment== null){
            return;// R.ret(R.ERR,"图片不存在");
        }
        if(attachment.getContent()== null){
            return;// R.ret(R.ERR);
        }
        T.responseFile(attachment.getContent(),attachment.getFilename(),response);
    }

     @RequestMapping("*")
     public String notFind(){
        return "404";
     }


}
    