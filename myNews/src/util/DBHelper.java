package util;

import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
//import com.mysql.jdbc.Connection;
/*import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;*/

public class DBHelper {

	private static DBHelper instance;
	private static ComboPooledDataSource ds;

	// 初始化,只执行一次
	static {
		ResourceBundle rb = ResourceBundle.getBundle("c3p0");
		ds = new ComboPooledDataSource();
		try {
			ds.setDriverClass(rb.getString("driver"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ds.setJdbcUrl(rb.getString("url"));
		ds.setUser(rb.getString("username"));
		ds.setPassword(rb.getString("password"));
	}

	/**
	 * 获取数据库实例
	 * 
	 * @return 连接对象ConnectionManager
	 */
	public synchronized static final DBHelper getInstance() {
		if (instance == null) {
			try {
				instance = new DBHelper();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return 数据库连接对象Connection
	 */
	public synchronized final Connection getConnection() {
		try {
			// 查看活动链接数
			// System.out.println("------->busy connections: " +
			// ds.getNumBusyConnections());
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @return void
	 */
	public static void close(ResultSet rs, Statement stmt, Connection con) {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 释放数据库资源
	 * 
	 * @return void
	 */
	@Override
	protected void finalize() throws Throwable {
		// 关闭datasource
		DataSources.destroy(ds);
		super.finalize();
	}

	/*
	 * private static final String driver = "com.mysql.jdbc.Driver";// 数据库驱动
	 * private static final String url =
	 * "jdbc:mysql://localhost:3306/news_db?useUnicode=true&characterEncoding";
	 * private static final String username = "root"; private static final
	 * String password = "xc9412"; private static Connection conn = null;//
	 * 数据库链接对象
	 * 
	 * //定义每页显示商品的数量 private static int span = 2; public static int getspan(){
	 * return span; }
	 * 
	 * public static void setSpan(int i){ span=i; } // 静态代码块负责加载驱动 static { try
	 * { Class.forName(driver); } catch (Exception ex) { ex.printStackTrace();
	 * 
	 * } }
	 * 
	 * // 单例模式返回数据库连接对象 public Connection getConnection() throws Exception { if
	 * (conn == null) {// 曾经没有建立连接 conn = (Connection)
	 * DriverManager.getConnection(url, username, password); return conn; }
	 * return conn; }
	 * 
	 * 
	 * 
	 * 执行查询
	 * 
	 * @param sql
	 * 
	 * @return ResultSet
	 * 
	 * public ResultSet executeQuery(String sql){
	 * 
	 * ResultSet rs=null; try{ Statement stmt = (Statement)
	 * conn.createStatement(); rs=(ResultSet) stmt.executeQuery(sql);
	 * }catch(SQLException e){ e.printStackTrace(); } return rs; }
	 * 
	 * 
	 * 执行更新的方法
	 * 
	 * @param sql
	 * 
	 * @return 更新的行数
	 * 
	 * 
	 * public int executeUpdate(String sql){ int result=0; try{ Statement st =
	 * (Statement) conn.createStatement(); result = st.executeUpdate(sql);
	 * st.close(); }catch(SQLException e){ e.printStackTrace(); } return result;
	 * }
	 * 
	 * 
	 * 关闭数据库连接
	 * 
	 * public void closeAll(Connection conn,Statement stmt,ResultSet rs){ try{
	 * if(rs!=null){ rs.close(); } if(stmt!=null){ stmt.close(); }
	 * if(conn!=null){ conn.close(); } }catch(SQLException e){
	 * e.printStackTrace(); } }
	 * 
	 * 
	 * 根据SQL语句获取数据的页数
	 * 
	 * 
	 * public static int getTotalPages(String sql) throws Exception{ int
	 * totalPage=1; DBHelper db=new DBHelper(); Connection con =
	 * db.getConnection(); Statement st=null; ResultSet rs=null; try{
	 * //执行语句的到结果集 st=(Statement) con.createStatement();
	 * 
	 * //获取结果集的元数据 rs=(ResultSet) st.executeQuery(sql); //执行语句得到结果集 rs.next();
	 * //得到总记录数 int rows=rs.getInt(1); totalPage=rows/span; if(rows%span!=0){
	 * totalPage++; } }catch(SQLException e){ e.printStackTrace(); }
	 * db.closeAll(con, st, rs); return totalPage ; }
	 * 
	 * 
	 * 
	 * 获取当前页面中的数据
	 * 
	 * @param page当前是第几页
	 * 
	 * @param sql
	 * 
	 * @return 页面内容
	 * 
	 * public static List<String[]>getPageContent(int page,String sql) throws
	 * Exception{ List<String[]> lists=new ArrayList<String[]>(); DBHelper
	 * db=new DBHelper(); Connection con=db.getConnection(); Statement st=null;
	 * ResultSet rs=null; try{ st=(Statement) con.createStatement(); //执行语句得到结果集
	 * rs=(ResultSet) st.executeQuery(sql); //获取结果集的元数据 ResultSetMetaData
	 * rsmt=rs.getMetaData(); //得到结果集的总列数 int count = rsmt.getColumnCount(); int
	 * start=(page-1)*span; if(start!=0){ rs.absolute(start); } int temp=0;
	 * while(rs.next()&&temp<span){ temp++; String[] str=new String[count];
	 * for(int i=0;i<str.length;i++){ str[i]=rs.getString(i+1); }
	 * lists.add(str); } db.closeAll(con, st, rs); }catch(SQLException e){
	 * e.printStackTrace(); } return lists; }
	 * 
	 * 
	 * 根据sql语句获取查询到的内容
	 * 
	 * @param sql
	 * 
	 * @return 数据列表
	 * 
	 * 
	 * public static List<String[]> getInfoArr(String sql){ List<String[]>
	 * vtemp=new ArrayList<String[]>(); try{ DBHelper db=new DBHelper(); //得到连接
	 * Connection con=db.getConnection(); //声明语句 Statement st=(Statement)
	 * con.createStatement(); ResultSet rs=(ResultSet) st.executeQuery(sql);
	 * ResultSetMetaData rsmt = rs.getMetaData(); int
	 * count=rsmt.getColumnCount(); while(rs.next()){ String [] str=new
	 * String[count]; for(int i=0;i<count;i++){ str[i]=rs.getString(i+1);
	 * 
	 * } vtemp.add(str); } db.closeAll(con, st, rs);
	 * 
	 * }catch(Exception e){ e.printStackTrace(); } return vtemp; }
	 * 
	 * public static List<String> getNews(String sql){ List<String> vtemp=new
	 * ArrayList<String>(); try{ DBHelper db=new DBHelper(); //得到连接 Connection
	 * con=db.getConnection(); //声明语句 Statement st=(Statement)
	 * con.createStatement(); ResultSet rs=(ResultSet) st.executeQuery(sql);
	 * 
	 * while(rs.next()){ String str=rs.getString(1); vtemp.add(str); }
	 * db.closeAll(con, st, rs);
	 * 
	 * }catch(Exception e){ e.printStackTrace(); } return vtemp; } public static
	 * void main(String[] args) { try { Connection conn =
	 * DBHelper.getConnection(); if (conn != null) {
	 * System.out.println("数据库连接正常"); } else { System.out.println("数据库连接异常"); }
	 * } catch (Exception ex) { ex.printStackTrace(); }
	 * 
	 * }
	 */
}
