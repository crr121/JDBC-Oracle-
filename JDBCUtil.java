# JDBC-Oracle-
JDBC实现Oracle数据库的增删改查
package com.zt.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class JDBCUtil {
   
	//加载驱动
	//利用属性解析配置文件,定义一个resourcebundle类型的变量获取属性配置文件
	//如果属性配置文件就在src下面，就直接写文件名，不加后缀名
	//这里的rb需要
	//加载驱动用静态代码块独立出来，只加载一次
	private static	ResourceBundle rb =ResourceBundle.getBundle("jdbc");
	//这里由于需要传递给下面的静态代码块，所以需要定义为static类型
	static{
		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
	        //将驱动的名称作为变量提取出来
			//这里的驱动名称利用rb来获取driver的字段
			Class.forName(rb.getString("driver"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//获取链接,返回con
	
	public Connection getconneConnection(){
		Connection con = null;
//		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		
		try {
			//			DriverManager.getConnection(url, "scott", "tiger");
			//这里的url,user,pwd同样的根据属性配置文件rb来获取相应的字段value
			//这里的url user pwd不是变量，所以需要加引号
			con = DriverManager.getConnection(rb.getString("url"), rb.getString("user"), rb.getString("pwd"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	//关闭链接
	public void closeAll(Connection con,PreparedStatement ps,ResultSet rs){
			try {
				if(con!=null){
					con.close();
				}
				if(ps!=null){
					ps.close();
				}
				if(rs!=null){
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}
