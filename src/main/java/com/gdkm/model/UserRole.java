package com.gdkm.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@DynamicUpdate
@Data
public class UserRole  implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private Integer roleId;
}
