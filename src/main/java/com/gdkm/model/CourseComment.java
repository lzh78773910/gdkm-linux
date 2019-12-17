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
public class CourseComment {
    @Id
    @GeneratedValue
    private Integer ccId;
    private Integer userId;
    private String userContent;
    private Integer adminId;
    private String adminContent;
    private Integer grade;
    private Date createtime;
    private Date updatatime;
}
