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
public class Question {
    @Id
    @GeneratedValue
    private Integer qId;
    private String title;
    private String description;
    private Date createtime;
    private Date updatetime;
    private Integer creator;
}
