package com.gdkm.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
public class Resource {
    @Id
    @GeneratedValue
    private Integer resId;
    private Integer adminId;
    private Integer viewNum;
    private String resTitle;
    private String resContent;
    private String resUrl;
    private Date createtime;
    private Date updatetime;
    private Integer rtId;

}
