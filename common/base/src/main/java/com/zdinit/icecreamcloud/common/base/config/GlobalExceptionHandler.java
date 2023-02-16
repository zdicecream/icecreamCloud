package com.zdinit.icecreamcloud.common.base.config;

import com.zdinit.icecreamcloud.common.base.base.BaseResponse;
import com.zdinit.icecreamcloud.common.base.base.CheckException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局统一错误处理
 */
@Slf4j
@ControllerAdvice
@Component    // 保证此类被SpringBoot扫描
public class GlobalExceptionHandler {
//    非restAPI 类型的指向
//    public static final String DEFAULT_ERROR_VIEW ="error";
//    @ExceptionHandler(value = Exception.class)
//    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e){
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", e);
//        mav.addObject("url", req.getRequestURL());
//        mav.setViewName(DEFAULT_ERROR_VIEW);
//        return mav;
//    }

    /**
     * 统一的restAPI 500返回
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse<String> defaultErrorHandler(Exception e){
        log.info("系统错误",e);
        BaseResponse baseResponse = new BaseResponse(BaseResponse.errorCode,BaseResponse.errorMsg+"["+e.getMessage()+"]",null);
        return baseResponse;
    }
    /**
     * 统一的restAPI 参数检测错误返回
     * @param nle
     * @return
     */
    @ExceptionHandler(value = CheckException.class)
    @ResponseBody
    public BaseResponse<String> CheckExceptionhandler(CheckException nle){
        log.info("参数检测异常",nle);
        BaseResponse baseResponse = new BaseResponse(BaseResponse.falseCode,nle.getMessage(),null);
        return baseResponse;
    }
}
