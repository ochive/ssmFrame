package com.hu.ssmFrame.activiti;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestActiviti {
	public static ProcessEngine engine;
	
	@Before//使用流程引擎配置器创建流程引擎.
	public void testInitialProcessEngine (){
		/*
		ProcessEngineConfiguration cfg= new StandaloneProcessEngineConfiguration()
				.setJdbcUrl("jdbc:mysql://localhost:3306/activiti?useUnicode=true&characterEncoding=UTF-8&useSSL=true")
				.setJdbcUsername("root")
				.setJdbcPassword("root")
				.setJdbcDriver("com.mysql.jdbc.Driver")
				.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);//如果activiti的表存在,则不会插入.
		engine=cfg.buildProcessEngine();
		*/
		//改用spring来创建流程引擎
		ApplicationContext context=new ClassPathXmlApplicationContext("/spring/spring-context.xml");
		engine = (ProcessEngine) context.getBean("processEngine");
		
		String engineName = engine.getName();
		String version = ProcessEngine.VERSION;
		
		System.out.println("流程引擎:"+engineName+" 的版本是:"+version);
	}

	//测试仓库服务repositoryService:部署流程
	@Test
	public void testRepositoryService() throws FileNotFoundException{
		RepositoryService repoService = engine.getRepositoryService();
		//创建一个部署定义构建器
		DeploymentBuilder builder = repoService.createDeployment();
		//给部署起个名字
		builder.name("请假流程");
		//将画好的流程图和图片打包后,传入.
		builder.addZipInputStream(new ZipInputStream(new FileInputStream("F:\\SimpleProcess.zip")));
		//部署到数据库
		builder.deploy();
		
		//查看所有的部署
		DeploymentQuery deployQuery = repoService.createDeploymentQuery();
		List<Deployment> deployList = deployQuery.list();
		if(deployList !=null && deployList.size()>0){
			for(Deployment item:deployList){
				System.out.println(item.getId()+":"+item.getName());
			}
		}
		
		//查看所有流程定义
		ProcessDefinitionQuery proDefiQuery = repoService.createProcessDefinitionQuery();
		List<ProcessDefinition> procDefiList = proDefiQuery.list();
		Iterator<ProcessDefinition> iterator = procDefiList.iterator();
		while(iterator.hasNext()){
			ProcessDefinition next = iterator.next();
			System.out.printf("流程id:%s,流程key:%s,流程名:%s,流程定义版本:%s,流程定义的资源文件名:%s \n",
					next.getId(),next.getKey(),next.getName(),
					next.getVersion(),next.getResourceName());
		}
	}
	
	
}
