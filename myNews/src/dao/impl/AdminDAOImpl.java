package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.AdminDAO;
import entity.Admin;
import entity.User;
import util.DBHelper;

public class AdminDAOImpl implements AdminDAO {
	public boolean isAdmin(String name,String password){
		
		boolean result=false;
		Connection conn = DBHelper.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			String sql = "select * from admin where name='"+name+"'and password='"+password+"'";
			stmt = conn.prepareStatement(sql);
			rs =stmt.executeQuery();

			if (rs.next()) {
				result=true;
			}

			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBHelper.close(rs, stmt, conn);
						
		}
		return result;

	}
	
	public boolean updatePassword(int id,String password){
		boolean result=false;
		/*if(admin!=null){*/
			Connection con=DBHelper.getInstance().getConnection();
			PreparedStatement pstmt=null;
		
			try{
			
				String sql="update admin set password=? where id=?";
				pstmt=con.prepareStatement(sql);
			
				pstmt.setString(1,password);
				pstmt.setInt(2, id);
				pstmt.executeUpdate();
				
				result=true;
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				DBHelper.close(null, pstmt, con);
				
			}
	
		/*}*/
		return result;
	}
	
	public Admin findAdminByName(String name){
		Admin admin=new Admin();
		Connection conn = DBHelper.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			
			String sql = "select * from admin where name='"+name+"'";
			stmt = conn.prepareStatement(sql);
			rs =stmt.executeQuery();

			
			if (rs.next()) {
				admin.setId(rs.getInt("id"));
				admin.setName(rs.getString("name"));
				admin.setPassword(rs.getString("password"));
			}	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBHelper.close(rs, stmt, conn);
						
		}
		
		return admin;
	}
}
