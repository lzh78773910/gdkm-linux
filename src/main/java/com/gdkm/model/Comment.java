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
public class Comment {
    @Id
    @GeneratedValue
    private Integer cId;
    private Integer parentId;
    private Integer commentator;
    private String content;
    private Date createtime;
    private Date updatetime;
}
