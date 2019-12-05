package com.gdkm.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gdkm.utils.Date2LongSerializer;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
public class LabelTitle {

    @Id
    @GeneratedValue
    private  Integer titleId;
    private  Integer labelId;
    private  String title;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updatatime;
}
