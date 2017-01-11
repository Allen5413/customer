package com.zs.web.controller;

import com.zs.tools.PropertiesTools;
import com.zs.tools.UpLoadFileTools;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Allen on 2017/1/10.
 */
@Controller
@RequestMapping(value = "/uploadTempFile")
public class UploadTempFileController extends LoggerController {
    private static Logger log = Logger.getLogger(UploadTempFileController.class);

    @RequestMapping(value = "upload")
    @ResponseBody
    public JSONObject upload(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        try{
            MultipartRequest mulReu = (MultipartRequest) request;
            String uploadPath = new PropertiesTools("resource/commons.properties").getProperty("file.temp.url");

            //处理上传图片
            String filePath = UpLoadFileTools.uploadImg(request, mulReu.getFiles("img"), "jpg|png|jpeg", 10240, 10, uploadPath);
            json.put("filePath", filePath);
            json.put("state", 0);
        }catch (Exception e){
            String msg = super.outputException(request, e, log, "上传文件到临时目录");
            json.put("state", 1);
            json.put("msg", msg);
        }
        return json;
    }

    @RequestMapping(value = "delFile")
    @ResponseBody
    public JSONObject delFile(HttpServletRequest request, @RequestParam("filePath")String filePath) {
        JSONObject json = new JSONObject();
        try{
            UpLoadFileTools.delFile(request, filePath);
            json.put("state", 0);
        }catch (Exception e){
            String msg = super.outputException(request, e, log, "删除路径文件");
            json.put("state", 1);
            json.put("msg", msg);
        }
        return json;
    }
}
