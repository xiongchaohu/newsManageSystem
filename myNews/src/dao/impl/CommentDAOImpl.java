package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import dao.CommentDAO;
import entity.Comment;
import entity.News;
import util.DBHelper;

public class CommentDAOImpl implements CommentDAO {

	public static final int PAGE_SIZE = 7; // 设置分页显示的每页数量
	private int allCount; // 所有的纪录总数
	private int allPageCount; // 总共多少页
	private int currentPage; // 当前页

	// 得到所有的纪录总数
	public int getAllCount() {
		return allCount;
	}

	// 返回总页数
	public int getAllPageCount() {
		return allPageCount;
	}

	// 返回当前页
	public int getCurrentPage() {
		return currentPage;
	}

	//根据新闻id获取该新闻下的评论
	@Override
	public ArrayList<Comment> getAllCommentByNewsId(int cPage, int news_id) {
		// TODO 自动生成的方法存根
		currentPage = cPage;
		ArrayList<Comment> list = new ArrayList<Comment>();

		// 若未指定查找某人，则默认全查
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 获取记录总数
			String sql1 = "select count(comment.id) as AllRecord from comment,news where comment.news_id=? and news.id=?";
			conn = DBHelper.getInstance().getConnection();
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, news_id);
			pstmt.setInt(2, news_id);
			rs = pstmt.executeQuery();

			if (rs.next())
				allCount = rs.getInt("AllRecord");

			rs.close();
			pstmt.close();

			// 记算总页数
			allPageCount = (allCount + PAGE_SIZE - 1) / PAGE_SIZE;

			// 如果当前页数大于总页数，则赋值为总页数
			if (allPageCount > 0 && currentPage > allPageCount)
				currentPage = allPageCount;

			// 获取第currentPage页数据
			String sql2 = "select comment.*,users.username from comment,users,news where comment.news_id=? and news.id=? and comment.user_id=users.id  order by id desc limit ?,?";
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, news_id);
			pstmt.setInt(2, news_id);
			pstmt.setInt(3, PAGE_SIZE * (currentPage - 1));
			pstmt.setInt(4, PAGE_SIZE);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setNews_id(rs.getInt("news_id"));
				comment.setContent(rs.getString("content"));
				comment.setUser_id(rs.getInt("user_id"));
				comment.setTime(rs.getTimestamp("time"));
				comment.setUsername(rs.getString("username"));

				// 将新闻信息插入列表
				list.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(rs, pstmt, conn);

		}
		return list;
	}

	//发表评论
	public boolean addComment(Comment comment) {
		boolean result = false;
		if (comment == null) {
			return result;
		}
		Connection con = DBHelper.getInstance().getConnection();
		PreparedStatement stmt = null;

		try {
			String sql = "insert into comment(news_id,content,user_id,time)values(?,?,?,?)";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, comment.getNews_id());
			stmt.setString(2, comment.getContent());
			stmt.setInt(3, comment.getUser_id());
			stmt.setTimestamp(4, new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));

			stmt.executeUpdate();
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(null, stmt, con);

		}
		return result;
	}
	
	
	//审核评论，更新flag
	/*public boolean updateFlag(int id,int flag){
		boolean result=false;
		
		Connection con=DBHelper.getInstance().getConnection();
		PreparedStatement pstmt=null;
		
		try{
			
			String sql="update comment set flag=? where id=?";
			pstmt=con.prepareStatement(sql);
			
			pstmt.setInt(1,flag);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
			
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBHelper.close(null, pstmt, con);
			
		}
		return result;
	}
	*/
	
	public ArrayList<Comment> getAllFlagComment(int cPage) {
		// TODO 自动生成的方法存根
		currentPage = cPage;
		ArrayList<Comment> list = new ArrayList<Comment>();

		// 若未指定查找某人，则默认全查
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 获取记录总数
			String sql1 = "select count(id) as AllRecord from comment";
			conn = DBHelper.getInstance().getConnection();
			pstmt = conn.prepareStatement(sql1);
			/*pstmt.setInt(1, news_id);
			pstmt.setInt(2, news_id);*/
			rs = pstmt.executeQuery();

			if (rs.next())
				allCount = rs.getInt("AllRecord");

			rs.close();
			pstmt.close();

			// 记算总页数
			allPageCount = (allCount + PAGE_SIZE - 1) / PAGE_SIZE;

			// 如果当前页数大于总页数，则赋值为总页数
			if (allPageCount > 0 && currentPage > allPageCount)
				currentPage = allPageCount;

			// 获取第currentPage页数据
			String sql2 = "select comment.*,users.username,news.title from comment,users,news where users.id=comment.user_id and news.id=comment.news_id order by id desc limit ?,?";
			pstmt = conn.prepareStatement(sql2);
			/*pstmt.setInt(1, news_id);
			pstmt.setInt(2, news_id);*/
			pstmt.setInt(1, PAGE_SIZE * (currentPage - 1));
			pstmt.setInt(2, PAGE_SIZE);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setNews_id(rs.getInt("news_id"));
				comment.setContent(rs.getString("content"));
				comment.setUser_id(rs.getInt("user_id"));
				comment.setTime(rs.getTimestamp("time"));
				comment.setUsername(rs.getString("username"));
				comment.setTitle(rs.getString("title"));

				// 将新闻信息插入列表
				list.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(rs, pstmt, conn);

		}
		return list;
	}
	
	public Comment getCommentById(int id){
		Connection con=DBHelper.getInstance().getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		Comment comment = new Comment();
		
		try {

			String sql = "select comment.*,users.username from comment,users where users.id=comment.user_id  and comment.id="+id;
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				
				comment.setId(rs.getInt("id"));
				comment.setContent(rs.getString("content"));
				comment.setNews_id(rs.getInt("news_id"));
				comment.setTime(rs.getTimestamp("time"));
				comment.setUsername(rs.getString("username"));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			
			DBHelper.close(rs, pstmt, con);
			
		}
		return comment;
	}
	
	public boolean deleteCommentById(int id){
		boolean result=false;
		if(id<=0){
			return result;
		}
		
		Connection con=DBHelper.getInstance().getConnection();
		PreparedStatement pstmt=null;
		
		try{
			
			String sql="delete from comment where id=?";
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
		return result;
	}
	/*public static void main(String args[]){
		CommentDAOImpl dao=new CommentDAOImpl();
		
		dao.updateFlag(2);
	}*/
}
