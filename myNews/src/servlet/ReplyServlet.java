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
import dao.impl.ReplyDAOImpl;
import dao.impl.UserDAOImpl;
import entity.Comment;
import entity.News;
import entity.Reply;
import entity.User;

/**
 * Servlet implementation class ReplyServlet
 */
@WebServlet("/ReplyServlet")
public class ReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReplyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		String method = request.getParameter("method");
		if(method.equals("add")){
			
			add(request,response);
		}else if(method.equals("view")){
			view(request,response);
		}
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();
		int comment_id = Integer.parseInt(request.getParameter("comment_id"));
		int news_id = Integer.parseInt(request.getParameter("news_id"));
		
		String username = request.getParameter("username");
		
	
		UserDAOImpl userDAO = new UserDAOImpl();
		User user = userDAO.findUserByName(username);
		String content = request.getParameter("replyContent");
	
		Reply reply = new Reply();

		reply.setComment_id(comment_id);
		reply.setContent(content);
		reply.setUser_id(user.getId());
		reply.setUsername(username);
		ReplyDAOImpl dao = new ReplyDAOImpl();

		boolean result = dao.addReply(reply);
	
		if (result) {
			out.println(
					"<script type='text/javascript'>alert('回复成功!');window.location.href='./CommentServlet?method=view&news_id="
							+ news_id + "';</script>");
		}else{
			out.println("<script type='text/javascript'>alert('回复失败!');history.back();</script>");
		}

	}
	
	private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int currentPage = 1; // 当前页默认为第一页
		String strpage = request.getParameter("currentPage"); // 获取前台传入当前页
		if (strpage != null && !strpage.equals("")) {
			currentPage = Integer.parseInt(strpage) < 1 ? 1 : Integer.parseInt(strpage); // 将字符串转换成整型
		}
		String comment_id = request.getParameter("comment_id");

		if (comment_id != null) {
			int id = Integer.parseInt(comment_id);
			CommentDAOImpl commentDao=new CommentDAOImpl();
			Comment comment=commentDao.getCommentById(id);
			/*CommentDAOImpl dao = new CommentDAOImpl();*/
			// 从UserDAO中获取所有用户信息
			ReplyDAOImpl replyDao=new ReplyDAOImpl();
			ArrayList<Reply> list = replyDao.getAllReplyByCommentId(currentPage, id);
			
			// 从UserDAO中获取总记录数
			int allCount = replyDao.getAllCount();
			// 从UserDAO中获取总页数
			int allPageCount = replyDao.getAllPageCount();
			// 从UserDAO中获取当前页
			currentPage = replyDao.getCurrentPage();

			// 存入request中
			request.setAttribute("allReplys", list);
			request.setAttribute("allCount", allCount);
			request.setAttribute("allPageCount", allPageCount);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("comment_id", id);
			request.setAttribute("comment",comment);
		}

		try {
			request.getRequestDispatcher("/jsp/replyView.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
