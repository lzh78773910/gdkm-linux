package com.gdkm.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@DynamicUpdate
@Data
public class ResourceType {

    @Id
    @GeneratedValue
    private Integer rtId;
    private String rtTitle;

}
