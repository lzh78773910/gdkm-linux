package com.gdkm.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@DynamicUpdate
@Data
public class VideoItem {
    @Id
    @GeneratedValue
    private Integer viId;
    private Integer videoId;
    private  String  viTitle;
    private  String viUrl;
}
