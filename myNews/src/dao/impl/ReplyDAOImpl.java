package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import dao.ReplyDAO;
import entity.Comment;
import entity.Reply;
import util.DBHelper;

public class ReplyDAOImpl implements ReplyDAO {

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
	
	public boolean addReply(Reply reply){
		boolean result = false;
		if (reply == null) {
			return result;
		}
		Connection con = DBHelper.getInstance().getConnection();
		PreparedStatement stmt = null;

		try {
			String sql = "insert into reply(comment_id,content,user_id,time)values(?,?,?,?)";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, reply.getComment_id());
			
			stmt.setString(2, reply.getContent());
			stmt.setInt(3,reply.getUser_id());
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

	public int getAllCount(int comment_id){
		Connection con = DBHelper.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int allCount=0;
		try {
			String sql = "select count(comment_id) as AllCount from reply where comment_id=?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, comment_id);
			

			rs=stmt.executeQuery();
			if (rs.next()){
				allCount = rs.getInt("AllCount");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(null, stmt, con);

		}
		return allCount;
	}

	@Override
	public ArrayList<Reply> getAllReplyByCommentId(int cPage, int comment_id) {
		// TODO 自动生成的方法存根
		currentPage = cPage;
		ArrayList<Reply> list = new ArrayList<Reply>();

		// 若未指定查找某人，则默认全查
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 获取记录总数
			String sql1 = "select count(id) as AllRecord from reply where comment_id=?";
			conn = DBHelper.getInstance().getConnection();
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, comment_id);
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
			String sql2 = "select reply.*,users.username from reply,users where reply.comment_id=? and reply.user_id=users.id order by id desc limit ?,?";
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, comment_id);
			pstmt.setInt(2, PAGE_SIZE * (currentPage - 1));
			pstmt.setInt(3, PAGE_SIZE);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Reply reply = new Reply();
				reply.setId(rs.getInt("id"));
				reply.setComment_id(rs.getInt("comment_id"));
				reply.setContent(rs.getString("content"));
				reply.setUser_id(rs.getInt("user_id"));
				reply.setTime(rs.getTimestamp("time"));
				reply.setUsername(rs.getString("username"));

				// 将新闻信息插入列表
				list.add(reply);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(rs, pstmt, conn);

		}
		return list;
	}
}