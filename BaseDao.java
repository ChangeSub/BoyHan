package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDao {
	private static final String DRIVER="com.mysql.jdbc.Driver";
	private static final String URL="jdbc:mysql://127.0.0.1:3306/holly";
	private static final String PASSWORD="ok";
	private static final String USER="root";
	
	public static Connection conn=null;
	public static PreparedStatement pstm=null;
	public static ResultSet rs=null;
	
	static{
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("drivr fail");
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		try {
			conn=DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public ResultSet executeQuery(String sql,Object[]param){
		conn=this.getConnection();
		try {
			pstm=conn.prepareStatement(sql);
			if(param!=null){
				for (int i = 0; i < param.length; i++) {
					pstm.setObject(i+1, param[i]);
				}
			}
			rs=pstm.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public int executeUpdate(String sql,Object[]param){
		int num=0;
		conn=this.getConnection();
		try {
			pstm=conn.prepareStatement(sql);
			if(param!=null){
				for (int i = 0; i < param.length; i++) {
					pstm.setObject(i+1, param[i]);
				}
			}
			num=pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.closeAll(rs,pstm,conn);
		}
		
		return num;
	}

	public void closeAll(ResultSet rs, PreparedStatement pstm,
			Connection conn) {
		try {
			if(rs!=null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(pstm!=null){
				pstm.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
