package com.imooc.hibernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.junit.Test;

public class SessionTest {
	
	@Test
	public void testOpenSession() {
		
		Configuration config = new Configuration().configure();
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session1 = sessionFactory.openSession();
		Session session2 = sessionFactory.openSession();
		
		System.out.println(session1==session2); //false
		
		
		/*if (session!=null) {
			System.out.println("session创建成功");
		} else {
			System.out.println("session创建失败");
		}*/
		
	}
	
	@Test
	public void testGetCurrentSession() {
		
		Configuration config = new Configuration().configure();
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session1 = sessionFactory.getCurrentSession();
		Session session2 = sessionFactory.getCurrentSession();
		
		System.out.println(session1==session2); //true
		
		/*if (session!=null) {
			System.out.println("session创建成功");
		} else {
			System.out.println("session创建失败");
		}*/
	}
	
	/*
	 * 测试openSession与getCurrentSession在资源释放方面的差异
	 */	
	@Test
	public void testSaveStudentsWithOpenSession() {
		
		Configuration config = new Configuration().configure();
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session1 = sessionFactory.openSession();
		Transaction transaction1 = session1.beginTransaction();
		session1.doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				System.out.println("Session1 connection hashCode: " + conn.hashCode());				
			}			
		});
		Students s1 = new Students(1, "张三", "男", new Date(), "北京");
		session1.save(s1);
		transaction1.commit();
		//session1.close();
		
		Session session2 = sessionFactory.openSession();
		Transaction transaction2 = session2.beginTransaction();
		//如果session1关闭了，则session2会取到session1释放的Connection对象；否则session2会创建一个新的Connection对象，说明session1未释放资源。		
		session2.doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				System.out.println("Session2 connection hashCode: " + conn.hashCode());				
			}			
		});
		Students s2 = new Students(2, "李四", "男", new Date(), "北京");
		session2.save(s2);
		transaction2.commit();
		session2.close();		
	}	
	
	/*
	 * 测试openSession与getCurrentSession在资源释放方面的差异
	 */	
	@Test
	public void testSaveStudentsWithGetCurrentSession() {
		
		Configuration config = new Configuration().configure();
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session1 = sessionFactory.getCurrentSession();
		Transaction transaction1 = session1.beginTransaction();
		session1.doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				System.out.println("Session1 connection hashCode: " + conn.hashCode());				
			}			
		});
		Students s1 = new Students(1, "张三", "男", new Date(), "北京");
		session1.save(s1);
		transaction1.commit();
		//session1.close();
		
		Session session2 = sessionFactory.getCurrentSession();
		Transaction transaction2 = session2.beginTransaction();
		//session1自动关闭，session2会获得同一Connection对象，说明session1已释放资源。		
		session2.doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				System.out.println("Session2 connection hashCode: " + conn.hashCode());				
			}			
		});
		Students s2 = new Students(2, "李四", "男", new Date(), "北京");
		session2.save(s2);
		transaction2.commit();		
	}	
}
