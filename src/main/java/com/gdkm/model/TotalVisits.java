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
public class TotalVisits {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer count;
    private Date createtime;

    public TotalVisits(Integer count, Date createtime) {
        this.count = count;
        this.createtime = createtime;
    }
}
