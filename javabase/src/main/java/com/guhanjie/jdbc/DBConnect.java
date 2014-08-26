package com.guhanjie.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnect {

	//FIXME youwenti 
	public void test() {
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//驱动程序名
		String driver = "com.mysql.jdbc.Driver";
		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://127.0.0.1:3306/test";
		// MySQL配置时的用户名
		String user = "root";
		// Java连接MySQL配置时的密码
		String password = "123";
		try {
			// 加载驱动程序
			Class.forName(driver);
			// 连续数据库
			Connection conn = DriverManager.getConnection(url, user, password);
			if(!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			// statement用来执行SQL语句
			Statement statement = conn.createStatement();
			// 要执行的SQL语句
			String sql = "select * from user";
			ResultSet rs = statement.executeQuery(sql);  
			while(rs.next()) {  
				System.out.println(rs.getString("name") + "\t" + rs.getString("name"));  
			}
			rs.close();  
			statement.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
