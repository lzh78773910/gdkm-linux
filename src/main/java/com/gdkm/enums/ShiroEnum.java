package com.gdkm.enums;

public enum ShiroEnum {


   USER("user"),
   ADMIN("admin");

    private String message;

    ShiroEnum( String message) {

        this.message = message;
    }
}
