package com.hu.ssmFrame.core.utils.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 后面要想办法弄成类似dbutils那样的.
 * @author mudking
 *
 * @since  2017年4月27日
 */
public class DBHelper {
	private static String driverClassName;
	private static String url;
	private static String username;
	private static String password;
	private static ThreadLocal<Connection> localConn=new ThreadLocal<Connection>();
	
	//初始化
	static{
		Properties prop=new Properties();
		InputStream is=null;
		
		try {
			is=DBHelper.class.getResourceAsStream("./jdbc.properties");
			prop.load(is);
			driverClassName=prop.getProperty("jdbc.driverClassName");
			url=prop.getProperty("jdbc.url");
			username=prop.getProperty("jdbc.username");
			password=prop.getProperty("jdbc.password");
			
			//装载时,Driver类中的静态代码会进行驱动的注册.
			Class.forName(driverClassName);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取一个数据库连接对象
	 * @return 数据库连接对象,如果创建失败,则为null.
	 */
	public static Connection getConnection(){
		Connection conn=null;
		
		//如果线程局部变量中没有该线程的数据库连接,则新建一个.
		if((conn=localConn.get())==null){
			try {
				conn=DriverManager.getConnection(url, username, password);
				
				localConn.set(conn);
			}  catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return conn;
	}
	
	/**
	 * 关闭数据库连接对象,释放与之相关的资源.
	 * @param conn
	 */
	public static void closeQuietly(Connection conn){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
