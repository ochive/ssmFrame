package com.hu.ssmFrame.core.exception;


import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hu.ssmFrame.core.vo.ResultForAjax;

/**
 * <pre>
 * 全局异常处理.
 * dao,service中不再处理异常,直接向上抛出到controller中捕获.
 * 控制层处理成自定义异常后抛出,在此统一记录处理.<br/>
 * 需要在spring mvc配置文件中注册.<br/>
 * </pre>
 * @author mudking
 *
 */
public class GlobalExceptionHandler implements HandlerExceptionResolver {
	Logger log=LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		/*
		CustomException customException=null;
		//可以分类处理异常
		if(ex instanceof CustomException){
			customException=(CustomException)ex;
		}*/
		
		//1.记录异常
		log.error(ex.getMessage(),ex);
		
		//2.处理异步请求(ajax)
		String header=request.getHeader("X-Requested-With");
		
		if (StringUtils.isNotBlank(header) && (header.equals("X-Requested-With") || header.equals("XMLHttpRequest"))) {
			response.setContentType("application/json;charset=UTF-8");
			ResultForAjax result = new ResultForAjax();
			result.setMessage(ex.getMessage());
			
			ServletOutputStream sos =null;
            try {
            	sos = response.getOutputStream();
            	
            	//使用jackson,将object转成json字符串,并输出
            	ObjectMapper mapper=new ObjectMapper();
    			mapper.writeValue(sos, result);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            	if(sos!=null){
            		try {
						sos.close();
					} catch (IOException e) {
						log.error("关闭response输出流失败!");
					}
            	}
            }
            
            return null;
		}else{
			//3.跳转错误页视图(非ajax异步请求时)
			ModelAndView mv = new ModelAndView("error/500");
			mv.addObject("error", ex);
			
			return mv;
		}
	}

}
