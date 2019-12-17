package com.gdkm.utils;

import com.gdkm.vo.ResultVO;


public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
    public static ResultVO error(String msg){
        ResultVO resultVO=new ResultVO();
        resultVO.setMsg(msg);
        return resultVO;
    }
}
