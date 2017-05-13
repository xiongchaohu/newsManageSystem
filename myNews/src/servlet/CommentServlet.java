package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.CommentDAOImpl;
import dao.impl.NewsDAOImpl;
import dao.impl.UserDAOImpl;
import entity.Comment;
import entity.News;
import entity.NewsType;
import entity.User;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/html;charset=utf-8");

		String method = request.getParameter("method");

		if (method.equals("view")) {
			view(request, response);
		} else if (method.equals("add")) {
			add(request, response);
		} else if (method.equals("reviewAll")) {
			reviewAll(request, response);
		}else if(method.equals("pass")){
			/*pass(request,response);*/
		}else if(method.equals("noPass")){
			/*nopass(request,response);*/
		}else if(method.equals("del")){
			delete(request,response);
		}

	}

	private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int currentPage = 1; // 当前页默认为第一页
		String strpage = request.getParameter("currentPage"); // 获取前台传入当前页
		if (strpage != null && !strpage.equals("")) {
			currentPage = Integer.parseInt(strpage) < 1 ? 1 : Integer.parseInt(strpage); // 将字符串转换成整型
		}
		String news_id = request.getParameter("news_id");

		if (news_id != null) {
			int id = Integer.parseInt(news_id);
			NewsDAOImpl newsDao = new NewsDAOImpl();
			News news = newsDao.getNewsById(id);
			CommentDAOImpl dao = new CommentDAOImpl();
			// 从UserDAO中获取所有用户信息
			ArrayList<Comment> list = dao.getAllCommentByNewsId(currentPage, id);
			
			// 从UserDAO中获取总记录数
			int allCount = dao.getAllCount();
			// 从UserDAO中获取总页数
			int allPageCount = dao.getAllPageCount();
			// 从UserDAO中获取当前页
			currentPage = dao.getCurrentPage();

			// 存入request中
			request.setAttribute("allComments", list);
			request.setAttribute("allCount", allCount);
			request.setAttribute("allPageCount", allPageCount);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("news_id", id);
			request.setAttribute("title1", news.getTitle());
		}

		try {
			request.getRequestDispatcher("/jsp/comment.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		int news_id = Integer.parseInt(request.getParameter("news_id"));
		String username = request.getParameter("username");

		UserDAOImpl userDAO = new UserDAOImpl();
		User user = userDAO.findUserByName(username);
		String content = request.getParameter("commentContent");
		Comment comment = new Comment();

		comment.setNews_id(news_id);
		comment.setContent(content);
		comment.setUser_id(user.getId());
		CommentDAOImpl dao = new CommentDAOImpl();

		boolean result = dao.addComment(comment);
		if (result) {
			out.println(
					"<script type='text/javascript'>alert('发表成功!');window.location.href='./CommentServlet?method=view&news_id="
							+ news_id + "';</script>");
		}

	}

	private void reviewAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int currentPage = 1; // 当前页默认为第一页
		String strpage = request.getParameter("currentPage"); // 获取前台传入当前页
		if (strpage != null && !strpage.equals("")) {
			currentPage = Integer.parseInt(strpage) < 1 ? 1 : Integer.parseInt(strpage); // 将字符串转换成整型
		}

		
		CommentDAOImpl dao = new CommentDAOImpl();
		// 从UserDAO中获取所有用户信息
		ArrayList<Comment> list = dao.getAllFlagComment(currentPage);
		
		// 从UserDAO中获取总记录数
		int allCount = dao.getAllCount();
		// 从UserDAO中获取总页数
		int allPageCount = dao.getAllPageCount();
		// 从UserDAO中获取当前页
		currentPage = dao.getCurrentPage();

		// 存入request中
		request.setAttribute("allComments", list);
		request.setAttribute("allCount", allCount);
		request.setAttribute("allPageCount", allPageCount);
		request.setAttribute("currentPage", currentPage);

		try {
			request.getRequestDispatcher("/jsp/admin/commentManager.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*private void pass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();
		if(request.getParameter("id")!=null){
			int id=Integer.parseInt(request.getParameter("id"));
			
			CommentDAOImpl dao=new CommentDAOImpl();
			
			if(dao.updateFlag(id,1)){
				out.println(
						"<script type='text/javascript'>alert('审核成功!');window.location.href='./CommentServlet?method=reviewAll'</script>");
			}
		}
	}*/
	
	/*private void nopass(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		PrintWriter out=response.getWriter();
		if(request.getParameter("id")!=null){
			int id=Integer.parseInt(request.getParameter("id"));
			
			CommentDAOImpl dao=new CommentDAOImpl();
			
			if(dao.updateFlag(id,2)){
				out.println(
						"<script type='text/javascript'>alert('审核成功!');window.location.href='./CommentServlet?method=reviewAll'</script>");
			}
		}
	}*/
	
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		boolean result = false;
		int id = Integer.parseInt(request.getParameter("id"));
		if (id > 0) {
			CommentDAOImpl dao = new CommentDAOImpl();
			result = dao.deleteCommentById(id);
			if (result) {
				out.println(
						"<script type='text/javascript'>alert('删除成功!');window.location.href='./CommentServlet?method=reviewAll'</script>");
			} else {
				out.println("<script type='text/javascript'>alert('删除失败!');JavaScript:history.back();");
			}

		}

	}


}
