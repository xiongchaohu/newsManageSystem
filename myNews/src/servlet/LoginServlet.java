package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AdminDAO;
import dao.UserDAO;
import dao.impl.AdminDAOImpl;
import dao.impl.UserDAOImpl;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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
		//设置响应字符集
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		//获取登录用户类型
		String type = request.getParameter("type");
		//获取登录用户的用户名和密码
		String username = request.getParameter("userName");
		String password = request.getParameter("password");
		UserDAO dao = (UserDAOImpl) new UserDAOImpl();
		AdminDAO adminDAO = new AdminDAOImpl();
		//若登录用户为注册用户
		if (type.equals("user")) {
			//用户名或密码不存在时
			if (username.equals("") || password.equals("")) {
				out.println("<script>alert('用户名或密码不能为空，请检查输入！！');JavaScript:history.back();</script>");
			} else if (dao.findUser(username, password)) {//所输入的用户名和密码正确
				//设置session，将username设置成用户名
				request.getSession().setAttribute("username", username);
				//获取登录之前的网页url
				String url=(String)request.getSession().getAttribute("url");
				
				//获取到url后需将session中的url清空，以便下次使用
				request.getSession().removeAttribute("url");
				response.sendRedirect(url);
			} else {
				out.println("<script>alert('用户名或密码错误，请重新输入');JavaScript:history.back();</script>");
			}
		} else if (type.equals("admin")) {//若登录类型为管理员
			if (adminDAO.isAdmin(username, password)) {//管理员用户名和密码正确

				request.getSession().setAttribute("adminname", username);
				response.sendRedirect("./jsp/admin/index.jsp");
			}else{
				out.println("<script>alert('用户名或密码错误，请重新输入');JavaScript:history.back();</script>");
			}
		}
	}

}
