package com.hu.ssmFrame.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hu.ssmFrame.pojo.Attachment;

public class AttachmentMapperTest {
	//ioc容器
	public ApplicationContext context;
	
	@Before
	public void initial(){
		context=new ClassPathXmlApplicationContext("/spring/spring-dao.xml");
	}
	
	@Test
	public void testUpdateFlag() {
		//fail("Not yet implemented");
	}

	@Test
	public void testSelectNotDownloaded() {
		//SqlSessionFactory fac = (SqlSessionFactory) context.getBean("sqlSessionFactory");
		//SqlSession session = fac.openSession(true);
		
		//AttachmentMapper mapper = session.getMapper(AttachmentMapper.class);
		//List<Attachment> list = mapper.selectNotDownloaded();
		
		//Assert.assertNotNull(list);
	}

}
