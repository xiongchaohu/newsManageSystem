package dao.impl;


import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;

import java.util.Scanner;

import dao.NewsDAO;

//import com.news.utils.WebUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import entity.News;
import entity.NewsType;
import util.DBHelper;

import java.sql.Connection;
public class NewsDAOImpl implements NewsDAO{

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

	    
	    /**根据新闻类别进行分页显示
	     * @param cPage
	     * @param news_type_id
	     * @return
	     */
	    @SuppressWarnings("finally")
	    public ArrayList<News> findNewsByPage(int cPage, int news_type_id)
	    {
	        currentPage = cPage;
	        ArrayList<News> list = new ArrayList<News>();

	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        try
	        {
	            // 计算新闻类别下总共的新闻数目
	            String sql1 = "select count(id) as AllRecord from news where news_type_id = ?";
	            conn = DBHelper.getInstance().getConnection();
	            pstmt = conn.prepareStatement(sql1);
	            pstmt.setInt(1,news_type_id);
	            rs = pstmt.executeQuery();

	            if(rs.next())
	                allCount = rs.getInt("AllRecord");

	            rs.close();
	            pstmt.close();

	            // 计算总页数
	            allPageCount = (allCount + PAGE_SIZE - 1) / PAGE_SIZE;

	            // 若当前页大于总页数，则置当前页为最后一页
	            if(allPageCount > 0 && currentPage > allPageCount)
	                currentPage = allPageCount;

	            // 进行分页查询
	            String sql2 = "select * from news where news_type_id=? order by news_count desc limit ?,?";
	            pstmt = conn.prepareStatement(sql2);
	            pstmt.setInt(1, news_type_id);
	            pstmt.setInt(2, PAGE_SIZE * (currentPage - 1));
	            pstmt.setInt(3, PAGE_SIZE);
	            rs = pstmt.executeQuery();
	            News news = null;
	            while(rs.next())
	            {
	            	news = new News();
	            	news.setId(rs.getInt("id"));
	            	news.setTitle(rs.getString("title"));
	            	news.setContent(rs.getString("content"));
	            	news.setCreat_time(rs.getDate("creat_time"));
	            	news.setNews_type_id(rs.getInt("news_type_id"));
	            	news.setNews_count(rs.getInt("news_count"));

	                //添加到list结果集中
	                list.add(news);
	            }
	        }
	        catch(SQLException e)
	        {
	            e.printStackTrace();
	        }
	        finally
	        {
	            DBHelper.close(rs, pstmt, conn);
	            return list;
	        }
	    }
	
	//查询所有新闻信息
	public ArrayList<News> getAllNews() {

		Connection conn = DBHelper.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<News> list = new ArrayList<News>();

		try {
			
			String sql = "select * from news";
			stmt = conn.prepareStatement(sql);
			rs =stmt.executeQuery();

			while (rs.next()) {
				News item = new News();
				item.setId(rs.getInt("id"));
				item.setTitle(rs.getString("title"));
				item.setContent(rs.getString("content"));
				item.setCreat_time(rs.getDate("creat_time"));
				item.setNews_type_id(rs.getInt("news_type_id"));
				item.setNews_count(rs.getInt("news_count"));
				list.add(item);
			}

			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			DBHelper.close(rs, stmt, conn);
						
		}
	}
	
	//分页查询获取所有新闻
	public ArrayList<News> getAllNewsByPage(int cPage){
		currentPage = cPage;
        ArrayList<News> list = new ArrayList<News>();

        // 若未指定查找某人，则默认全查
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            // 获取记录总数
            String sql1 = "select count(id) as AllRecord from news";
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
            String sql2 = "select * from news order by id desc limit ?,?";
            pstmt = conn.prepareStatement(sql2);
            pstmt.setInt(1, PAGE_SIZE * (currentPage - 1));
            pstmt.setInt(2, PAGE_SIZE);
            rs = pstmt.executeQuery();
            
            while(rs.next())
            {
                News news = new News();
                news.setId(rs.getInt("id"));
                news.setTitle(rs.getString("title"));
                news.setContent(rs.getString("content"));
                news.setCreat_time(rs.getDate("creat_time"));
                news.setNews_type_id(rs.getInt("news_type_id"));
                news.setNews_count(rs.getInt("news_count"));

                // 将新闻信息插入列表
                list.add(news);
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
	
	//获取所有的新闻类别
	public ArrayList<NewsType> getAllType(){
		Connection conn = DBHelper.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<NewsType> list = new ArrayList<NewsType>();//用来存放查询结果
		try {

			String sql = "select * from news_type";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				NewsType item = new NewsType();
				item.setNews_type_id(rs.getInt("news_type_id"));
				item.setNews_type_name(rs.getString("news_type_name"));
				list.add(item);
			}

			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			
			DBHelper.close(rs, stmt, conn);
			
		}
	}
	//根据新闻类别id查询新闻
	public ArrayList<News> getAllNewsByTypeId( int news_type_id) {

		//建立连接
		Connection conn = DBHelper.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<News> list = new ArrayList<News>();//用来存放查询结果
		try {

			String sql = "select * from news where news_type_id=? order by id desc";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, news_type_id);
			rs = stmt.executeQuery();

			while (rs.next()) {
				News item = new News();
				item.setId(rs.getInt("id"));
				item.setTitle(rs.getString("title"));
				item.setContent(rs.getString("content"));
				item.setCreat_time(rs.getDate("creat_time"));
				item.setNews_type_id(rs.getInt("news_type_id"));
				item.setNews_count(rs.getInt("news_count"));
				list.add(item);
			}

			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			
			DBHelper.close(rs, stmt, conn);
			
		}
	}
	
	//添加新闻
	@SuppressWarnings("finally")
	public boolean insertNews(News news){
		boolean result=false;
		if(news==null){
			return result;
		}
		Connection con =DBHelper.getInstance().getConnection();
		PreparedStatement stmt=null;
		
		try{
			String sql="insert into news(title,content,creat_time,news_type_id,news_count)values(?,?,?,?,?)";
			stmt=con.prepareStatement(sql);
			stmt.setString(1, news.getTitle());
			stmt.setString(2, news.getContent());
			stmt.setTimestamp(3, new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			stmt.setInt(4, news.getNews_type_id());
			stmt.setInt(5, news.getNews_count());
			
			stmt.executeUpdate();
			result=true;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			DBHelper.close(null, stmt, con);
			return result;
		}
	}
	
	//根据新闻id删除新闻
	@SuppressWarnings("finally")
	public boolean deleteNewsById(int news_id){
		boolean result=false;
		if(news_id<=0){
			return result;
		}
		
		Connection con=DBHelper.getInstance().getConnection();
		PreparedStatement pstmt=null;
		
		try{
			
			String sql="delete from news where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,news_id);
			pstmt.executeUpdate();
			
			result=true;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			DBHelper.close(null, pstmt, con);
			return result;
		}
	}
	
	//更新修改新闻
	@SuppressWarnings("finally")
	public boolean updateNews(News news){
		boolean result=false;
		if(news==null){
			result=false;
		}
		Connection con=DBHelper.getInstance().getConnection();
		PreparedStatement pstmt=null;
		
		try{
			
			String sql="update news set title=?,content=?,creat_time=?,news_type_id=? where id=?";
			pstmt=con.prepareStatement(sql);
			
			pstmt.setString(1,news.getTitle());
			pstmt.setString(2, news.getContent());
			pstmt.setTimestamp(3, new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			pstmt.setInt(4, news.getNews_type_id());
			
			pstmt.setInt(5, news.getId());
			pstmt.executeUpdate();
			/*pstmt.setInt(6, news.getId());*/
			
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBHelper.close(null, pstmt, con);
			return result;
		}
		
	}
	
	//获取所有的新闻类别名称
	public ArrayList<NewsType> getTypeName(){
		Connection conn = DBHelper.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<NewsType> list = new ArrayList<NewsType>();

		try {
			
			String sql = "select * from news_type";
			stmt = conn.prepareStatement(sql);
			rs =stmt.executeQuery();

			while (rs.next()) {
				NewsType item = new NewsType();
				item.setNews_type_id(rs.getInt(1));
				item.setNews_type_name(rs.getString(2));
				list.add(item);
			}

			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			DBHelper.close(rs, stmt, conn);
						
		}
	}
	
	//根据标题进行模糊搜索
	public ArrayList<News> searchByTitle(int cPage,String title){
		currentPage = cPage;
        ArrayList<News> list = new ArrayList<News>();

        // 若未指定查找某标题，则默认全查
        if(null == title || title.equals("null"))
        {
            title = "";
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            // 获取记录总数
            String sql1 = "select count(id) as AllRecord from news where title like ?";
            conn = DBHelper.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, "%" + title + "%");
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
            String sql2 = "select * from news where title like ? limit ?,?";
            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, "%" + title + "%");
            pstmt.setInt(2, PAGE_SIZE * (currentPage - 1));
            pstmt.setInt(3, PAGE_SIZE);
            rs = pstmt.executeQuery();
            
            while(rs.next())
            {
                News news = new News();
                news.setId(rs.getInt("id"));
                news.setTitle(rs.getString("title"));
                news.setContent(rs.getString("content"));
                news.setCreat_time(rs.getDate("creat_time"));
                news.setNews_type_id(rs.getInt("news_type_id"));
                news.setNews_count(rs.getInt("news_count"));

                // 将该用户信息插入列表
                list.add(news);
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
	
	//当点击查看详细新闻时更新数量
	public boolean updateCount(int count,int id){
		boolean result=false;
		
		Connection con=DBHelper.getInstance().getConnection();
		PreparedStatement pstmt=null;
		
		try{
			
			String sql="update news set news_count=? where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, count+1);
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
		
	//根据新闻id获取新闻内容
	public News getNewsById( int id) {

		Connection conn = DBHelper.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		News item = new News();
		
		try {

			String sql = "select * from news where id="+id;
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			if (rs.next()) {
				
				item.setId(rs.getInt("id"));
				item.setTitle(rs.getString("title"));
				item.setContent(rs.getString("content"));
				item.setCreat_time(rs.getDate("creat_time"));
				item.setNews_type_id(rs.getInt("news_type_id"));
				item.setNews_count(rs.getInt("news_count"));
			}

			return item;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			
			DBHelper.close(rs, stmt, conn);
			
		}
	}
	
	//根据新闻类别id获取新闻类别名字
	public String findTypeNameByType(int type_id){
		Connection conn = DBHelper.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		NewsType item = new NewsType();
		try {

			String sql = "select news_type_name from news_type where news_type_id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, type_id);
			rs = stmt.executeQuery();

			if (rs.next()) {
				
				item.setNews_type_id(type_id);
				item.setNews_type_name(rs.getString("news_type_name"));
				
			}

			return item.getNews_type_name();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			
			DBHelper.close(rs, stmt, conn);
			
		}
	}
	
	//根据新闻类别id查找类名
	public NewsType findTypeNameByTypeId(int type_id){
		Connection conn = DBHelper.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		NewsType item = new NewsType();
		try {

			String sql = "select news_type_name from news_type where news_type_id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, type_id);
			rs = stmt.executeQuery();

			if (rs.next()) {
				
				item.setNews_type_id(type_id);
				item.setNews_type_name(rs.getString("news_type_name"));
				
			}

			return item;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			
			DBHelper.close(rs, stmt, conn);
			
		}
	}
	
	//根据新闻类别对新闻类别进行删除
	public boolean deleteNewsTypeById(int news_type_id){
		boolean result=false;
		if(news_type_id<=0){
			return result;
		}
		
		Connection con=DBHelper.getInstance().getConnection();
		PreparedStatement pstmt=null;
		
		try{
			
			String sql="delete from news_type where news_type_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,news_type_id);
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
	
	//根据新闻类别名称查找新闻类别
	public boolean isExistType(String news_type_name){
		boolean result=false;
		if(news_type_name!=null){
			Connection con =DBHelper.getInstance().getConnection();
			PreparedStatement stmt=null;
			ResultSet rs=null;
			try{				
				String sql="select * from news_type where news_type_name='"+news_type_name+"'";
				stmt=con.prepareStatement(sql);
				rs=stmt.executeQuery();
				if(rs.next()){
					result=true;
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				DBHelper.close(null, stmt, con);
				
			}
		}
		return result;
	}
	//添加新闻类别
	public boolean addNewsType(NewsType newsType){
		boolean result=false;
		if(newsType==null){
			return result;
		}
		Connection con =DBHelper.getInstance().getConnection();
		PreparedStatement stmt=null;
		
		try{
			
			String sql="insert into news_type(news_type_name)values(?)";
			stmt=con.prepareStatement(sql);
			stmt.setString(1, newsType.getNews_type_name());
			
			stmt.executeUpdate();
			result=true;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			DBHelper.close(null, stmt, con);
			
		}
		return result; 
	}
	
	//更新新闻类型
	public boolean updateNewsType(NewsType newsType){
		boolean result=false;
		if(newsType==null){
			result=false;
		}
		Connection con=DBHelper.getInstance().getConnection();
		PreparedStatement pstmt=null;
		
		try{
			
			String sql="update news_type set news_type_name=? where news_type_id=?";
			pstmt=con.prepareStatement(sql);
			
			pstmt.setString(1,newsType.getNews_type_name());
			pstmt.setInt(2,newsType.getNews_type_id());
			pstmt.executeUpdate();
			
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBHelper.close(null, pstmt, con);
			
		}
		return result;
		
	}
	
	//根据点击量进行排序
	public ArrayList<News> getAllNewsByCount() {

		Connection conn = DBHelper.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<News> list = new ArrayList<News>();

		try {
			
			String sql = "select * from news order by news_count desc limit 0,10";
			stmt = conn.prepareStatement(sql);
			rs =stmt.executeQuery();

			while (rs.next()) {
				News item = new News();
				item.setId(rs.getInt("id"));
				item.setTitle(rs.getString("title"));
				item.setContent(rs.getString("content"));
				item.setCreat_time(rs.getDate("creat_time"));
				item.setNews_type_id(rs.getInt("news_type_id"));
				item.setNews_count(rs.getInt("news_count"));
				list.add(item);
			}

			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			DBHelper.close(rs, stmt, conn);
						
		}
	}
	
		/*public static void main(String[] args){
		int id=2;
		NewsType item=new NewsType();
		NewsDAOImpl dao = new NewsDAOImpl();
		//Scanner in=new Scanner(System.in);
		System.out.println(dao.updateCount(dao.getNewsById(id).getNews_count(), id));
	}*/
	
	/*public static void main(String[] args){
		NewsDAOImpl dao=new NewsDAOImpl();
		System.out.println(dao.getAllNewsByCount());
	}*/
}
