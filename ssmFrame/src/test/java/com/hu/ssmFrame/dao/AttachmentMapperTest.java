package com.hu.ssmFrame.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


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
