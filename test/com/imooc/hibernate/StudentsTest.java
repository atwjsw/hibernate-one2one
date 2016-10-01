package com.imooc.hibernate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StudentsTest {

	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;

	@Before
	public void init() {
		//创建配置对象
		//Configuration config = new Configuration();
		//创建服务注册对象
		//ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties())
		//		.buildServiceRegistry();
		//创建会话工厂对象
		//sessionFactory = config.buildSessionFactory(serviceRegistry);
		//获取会话对象
		//session = sessionFactory.openSession();
		//开启事务
		//transaction = session.beginTransaction();
		Configuration config = new Configuration().configure();

		sessionFactory = config.buildSessionFactory();

		session = sessionFactory.openSession();

		transaction = session.beginTransaction();
		
	};

	@After
	public void destroy() {
		transaction.commit(); //提交事务
		session.close(); //关闭会话
		sessionFactory.close(); //关闭会话工厂
	};

	@Test
	public void testSaveStudents() {
		//生成学生对象
		Students s = new Students(4, "张四丰", "男", new Date(), "武当山");
		//保存对象进入数据库
		Address address = new Address("510080", "02087655447","广州市");
		s.setAddress(address);
		session.save(s);
		
		//手工打开JDBC Connection的事务控制
		/*session.doWork(new Work() {

			@Override
			public void execute(Connection conn) throws SQLException {
				conn.setAutoCommit(true);				
			}			
		});
		
		session.flush();		*/
	};
	
	@Test
	public void testSaveStudentsWithKey() {
		//生成学生对象
		Students s = new Students();
		s.setSid(1);
		s.setSname("张四丰");
		s.setGender("男");
		s.setBirthday(new Date());
		//s.setAddress("武当山");
		//保存对象进入数据库
		session.save(s);		
	};
	
	/*
	 * 测试写入Blob（照片）
	 */
	@Test
	public void testWriteBlob() throws Exception {
		//生成学生对象
		Students s = new Students();
		s.setSid(1);
		s.setSname("张四丰");
		s.setGender("男");
		s.setBirthday(new Date());
		//s.setAddress("武当山");
		File file = new File("C:\\Users\\ewendia\\myself.jpg");
		InputStream input = new FileInputStream(file);
		Blob image = Hibernate.getLobCreator(session).createBlob(input, input.available());
		s.setPicture(image);
		//保存对象进入数据库
		session.save(s);		
	};
	
	/*
	 * 测试读取Blob（照片）
	 */	
	@Test
	public void testReadBlob() throws Exception {
		//生成学生对象
		Students s = (Students)session.get(Students.class,1);
		//获取Blob对象的Ref
		Blob image = s.getPicture();
		InputStream input = image.getBinaryStream();
		File file = new File("C:\\Users\\ewendia\\copy.jpg");
		OutputStream output = new FileOutputStream(file);
		byte[] buff = new byte[input.available()];
		input.read(buff);
		output.write(buff);
		input.close();
		output.close();	
	};
	
	@Test
	public void testGet() {		
		Students s = (Students)session.get(Students.class,4);
		System.out.println(s);
		System.out.println(s.getClass().getName());
	};
	
	@Test
	public void testLoad() {		
		Students s = (Students)session.load(Students.class,4);
		System.out.println(s);
		System.out.println(s.getClass().getName());
	};
	
	@Test
	public void testUpdate() {		
		Students s = (Students)session.get(Students.class,1);
		s.setGender("女");
		session.update(s);	
	};
	
	@Test
	public void testDelete() {		
		Students s = (Students)session.get(Students.class,1);
		session.delete(s);	
	};
}
