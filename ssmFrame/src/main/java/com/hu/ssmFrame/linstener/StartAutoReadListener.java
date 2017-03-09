package com.hu.ssmFrame.linstener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hu.ssmFrame.constrant.Const;
import com.hu.ssmFrame.service.AutoReadAndDownloadAttach;

/**
 * 启动定期下载附件
 * 在监听器启动之前得先加载spring bean，所以自动注入的注解在监听器里是不能用的。
 * 但可以使用从servletContext对象中创建applicationContext的方法获取ioc容器
 */
//@WebListener
public class StartAutoReadListener implements ServletContextListener {
	private Logger logger=LoggerFactory.getLogger(StartAutoReadListener.class);

    public StartAutoReadListener() {
    }

    public void contextDestroyed(ServletContextEvent arg0)  { 
    }

    public void contextInitialized(ServletContextEvent context)  { 
    	if(logger.isInfoEnabled()){
    		logger.info("启动定期下载附件...");
		}
    	//从web应用上下文对象中获取ioc容器
    	WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(context.getServletContext());
    	AutoReadAndDownloadAttach downAttach = webApplicationContext.getBean(AutoReadAndDownloadAttach.class);
		/**
		 * ScheduledExecutorService是从Java SE5的java.util.concurrent里，做为并发工具类被引进的，这是最理想的定时任务实现方式。  
		 * 相比于上两个方法，它有以下好处： 
		 * 1>相比于Timer的单线程，它是通过线程池的方式来执行任务的  
		 * 2>可以很灵活的去设定第一次执行任务delay时间 
		 * 3>提供了良好的约定，以便设定执行的时间间隔 
		 *  
		 * 下面是实现代码，我们通过ScheduledExecutorService#scheduleAtFixedRate展示这个例子，通过代码里参数的控制，首次执行加了delay时间。 
		 */
		ScheduledExecutorService service=Executors.newSingleThreadScheduledExecutor();
		//启动后,10分钟执行一次,之后每30分钟执行一次.
		//第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
		service.scheduleAtFixedRate(downAttach, Const.START_TIME, Const.INTERVAL, TimeUnit.SECONDS);
    }
	
}
