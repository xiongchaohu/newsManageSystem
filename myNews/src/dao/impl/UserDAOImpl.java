package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dao.UserDAO;
import entity.User;
import util.DBHelper;

public class UserDAOImpl implements UserDAO {

	
	
	public static final int PAGE_SIZE = 7; // 设置分页显示的每页数量
    private int allCount; // 所有的纪录总数
    private int allPageCount; // 总共多少页
    private int currentPage; // 当前页

    //得到所有的纪录总数
    public int getAllCount()
    {
        return allCount;
    }

    //返回总页数
    public int getAllPageCount()
    {
        return allPageCount;
    }

    //返回当前页
    public int getCurrentPage()
    {
        return currentPage;
    }

    
    //分页查询所有用户信息
    public ArrayList<User> getAllUserByPage(int cPage){
		currentPage = cPage;
        ArrayList<User> list = new ArrayList<User>();

        // 若未指定查找某人，则默认全查
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            // 获取记录总数
            String sql1 = "select count(id) as AllRecord from users";
            conn = DBHelper.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql1);
            rs = pstmt.executeQuery();

            if(rs.next())
                allCount = rs.getInt("AllRecord");

            rs.close();
            pstmt.close();

            // 记算总页数
            allPageCount = (allCount + PAGE_SIZE - 1) / PAGE_SIZE;

            // 如果当前页数大于总页数，则赋值为总页数
            if(allPageCount > 0 && currentPage > allPageCount)
                currentPage = allPageCount;

            // 获取第currentPage页数据
            String sql2 = "select * from users order by id desc limit ?,?";
            pstmt = conn.prepareStatement(sql2);
            pstmt.setInt(1, PAGE_SIZE * (currentPage - 1));
            pstmt.setInt(2, PAGE_SIZE);
            rs = pstmt.executeQuery();
            
            while(rs.next())
            {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));

                // 将新闻信息插入列表
                list.add(user);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            DBHelper.close(rs, pstmt, conn);
            
        }
        return list;
	}
	/**根据用户名进行查找
	 * @param name 用户名
	 * @param pwd  密码
	 * @return
	 */
	
	public User findUserByName(String name){
		User user=new User();
		Connection conn = DBHelper.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			
			String sql = "select * from users where username='"+name+"'";
			stmt = conn.prepareStatement(sql);
			rs =stmt.executeQuery();

			
			if (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
			}	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBHelper.close(rs, stmt, conn);
						
		}
		return user;
	}
	
	/* （非 Javadoc）根据用户名和密码进行查找用户
	 * @see dao.UserDAO#findUser(java.lang.String, java.lang.String)
	 */
	public boolean findUser(String name,String pwd){
		
		boolean result=false;
		Connection conn = DBHelper.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			String sql = "select * from users where username='"+name+"'and password='"+pwd+"'";
			stmt = conn.prepareStatement(sql);
			rs =stmt.executeQuery();

			if (rs.next()) {
				int id=rs.getInt(1);
				String email=rs.getString(2);
				String username=rs.getString(3);
				String password=rs.getString(4);
			
				User user = new User(id,username,password,email);	
				result=true;
			}

			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBHelper.close(rs, stmt, conn);
						
		}
		return result;
	}
	
	/**添加用户
	 * @param user
	 * @return
	 */
	public boolean addUser(User user){
		boolean result=false;
		if(user!=null){
			Connection con =DBHelper.getInstance().getConnection();
			PreparedStatement stmt=null;
	
			try{
				String sql="insert into users(email,username,password)values(?,?,?)";
				stmt=con.prepareStatement(sql);
				stmt.setString(1, user.getEmail());
				stmt.setString(2, user.getUsername());
				
				stmt.setString(3, user.getPassword());
		
				stmt.executeUpdate();
				result=true;
		
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				DBHelper.close(null, stmt, con);				
			}
		}
		return result;
	}
	
	//根据id删除用户信息
	public boolean deleteUserById(int id){
		boolean result=false;
		if(id>0){
		
			Connection con=DBHelper.getInstance().getConnection();
			PreparedStatement pstmt=null;
		
			try{
				
				String sql="delete from users where id=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1,id);
				pstmt.executeUpdate();
			
				result=true;
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				DBHelper.close(null, pstmt, con);
			
			}
		
		}
		return result;
	}
	
	//更新用户信息
	public boolean updateUser(User user){
		
		boolean result=false;
		if(user!=null){
			Connection con=DBHelper.getInstance().getConnection();
			PreparedStatement pstmt=null;
		
			try{
			
				String sql="update users set email=?,username=? where id=?";
				pstmt=con.prepareStatement(sql);
			
				pstmt.setString(1,user.getEmail());
				pstmt.setString(2, user.getUsername());
				/*pstmt.setString(3, user.getPassword());*/
				pstmt.setInt(3, user.getId());
				pstmt.executeUpdate();
				
				result=true;
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				DBHelper.close(null, pstmt, con);
				
			}
	
		}
		return result;
	}

	//根据邮箱查找用户
	public boolean findUserByEmail(String email){
		
		Connection conn = DBHelper.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			
			String sql = "select email from users where email='"+email+"'";
			stmt = conn.prepareStatement(sql);
			rs =stmt.executeQuery();

			if (rs.next()) {
				return true;
				
			}	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBHelper.close(rs, stmt, conn);
						
		}
		return false;
	}
	
	//根据id查找用户，返回user类型
	public User findUserById(int id){
		Connection conn = DBHelper.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User user = new User();
		
		try {

			String sql = "select * from users where id="+id;
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			if (rs.next()) {
				
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
			}

			return user;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			
			DBHelper.close(rs, stmt, conn);
			
		}
	}
	
	
}