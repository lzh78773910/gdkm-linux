package com.gdkm.controller;

import com.gdkm.config.projectUrl;
import com.gdkm.dto.FileDTO;
import com.gdkm.utils.UCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ApiIgnore
@Controller
public class ByAdminFileController {

    @Autowired
    public projectUrl projectUrl;

    @Autowired
    private UCloudProvider uCloudProvider;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(value = "editormd-image-file", required = false) MultipartFile file) throws IOException {

        String fileName = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename(), projectUrl.getImgUcloud());
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setMessage("success");
        fileDTO.setUrl(fileName);
        return fileDTO;

    }

}
