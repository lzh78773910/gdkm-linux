package com.gdkm.controller;

import com.gdkm.config.projectUrl;
import com.gdkm.dto.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@ApiIgnore
@Controller
public class ByAdminFileController {

    @Autowired
    public projectUrl projectUrl;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(value = "editormd-image-file", required = false) MultipartFile file) {

        String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
        File filePath = new File(projectUrl.getImgUrl() + fileName);    //拼接文件名并存放指定的目录
        System.out.println(filePath.toString());    //打印文件目录
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            file.transferTo(filePath);    //将文件内容读取到缓冲区 事半功倍 堪比传统的IO流
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setMessage("success");
        String url = projectUrl.getLinux() + projectUrl.getImg() + fileName;
        fileDTO.setUrl(url);
        System.out.println(url);
        return fileDTO;
    }

}
