package com.gdkm.exception;

import com.gdkm.enums.ResultEnum;

public class LinuxException extends RuntimeException {

    private Integer code;

    public LinuxException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public LinuxException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
