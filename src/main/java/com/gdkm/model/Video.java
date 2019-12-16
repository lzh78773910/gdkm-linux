package com.gdkm.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
public class Video {

    @Id
    @GeneratedValue
    private Integer videoId;

    private Integer adminId;
    private  String  videoText;
    private  String videoTitle;
    private  String videoUrl;
    private Integer viewNum;

    private String videoImg;
    private String coursewareUrl;

    private Date createtime;
    private Date updatatime;

}
