package com.zdinit.icecreamcloud.common.base.util;


import com.zdinit.icecreamcloud.common.base.base.BaseResponse;

public class ResponseUtil {
    public static BaseResponse sucess(Object object){
        BaseResponse baseResponse= new BaseResponse();
        baseResponse.setCode(BaseResponse.successCode);
        baseResponse.setMsg(BaseResponse.successMsg);
        baseResponse.setData(object);
        return baseResponse;
    }

    public static BaseResponse sucess(){
        return sucess(null);
    }

    public static BaseResponse error(Integer code,String msg){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(code);
        baseResponse.setMsg(msg);
        return baseResponse;
    }

    public static BaseResponse error(){
        return error(BaseResponse.errorCode,BaseResponse.errorMsg);
    }

    public static BaseResponse error(String msg){
        return error(BaseResponse.errorCode,msg);
    }
}
