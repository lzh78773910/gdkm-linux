package com.gdkm.utils;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class AjaxResult<T> {

	private boolean success ;
	
	private String message ;
	
	private T data ;

}