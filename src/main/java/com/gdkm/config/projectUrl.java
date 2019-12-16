package com.gdkm.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "projectUrl")
@Component
public class projectUrl {

    /**
     * 项目上下文路径
     */
    public String linux;
    /**
     * 图片上传路径
     */
    public String imgUrl;
    /**
     * 视频上传路径
     */
    public String shipinUrl;

    /**
     * 课件上传路径
     */
    public String kejianUrl;
    /**
     * 映射图片上传路径
     */
    public String img;
    /**
     * 视映射频上传路径
     */
    public String shipin;

    /**
     * 映射课件上传路径
     */
    public String kejian;
}
