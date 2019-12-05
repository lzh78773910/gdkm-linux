package com.gdkm.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@DynamicUpdate
@Data
public class HomeLabel {

    @Id
    @GeneratedValue
    private  Integer LabelId;
    private  String homeLabel;
    private  String labelImg;
}
