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
public class VideoComment {
    @Id
    @GeneratedValue
    private Integer vcId;
    private Integer parentId;
    private Integer commentator;
    private String vcContent;
    private Date createtime;
}
