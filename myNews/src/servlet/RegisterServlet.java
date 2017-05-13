package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import entity.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
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
		response.setContentType("text/html");
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");

		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String pwd = request.getParameter("password");
		String confirmPwd = request.getParameter("confirmPwd");

		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(pwd);

		PrintWriter out = response.getWriter();

		UserDAO dao = new UserDAOImpl();
		if (email.equals("") || username.equals("") || pwd.equals("") || confirmPwd.equals("")) {
			out.println("<script type='text/javascript'>alert('注册失败，请检查所有输入!');JavaScript:history.back();</script>");
		} else if (dao.findUserByName(username).getUsername()!=null) {
			System.out.println("+++++");
			out.println(
					"<script type='text/javascript'>alert('注册失败，此用户已经被别人注册，请重新填过!');JavaScript:history.back();</script>");
		} else if (dao.findUserByEmail(email)) {
			System.out.println("-----");
			out.println(
					"<script type='text/javascript'>alert('注册失败，此邮箱已经被别人注册，请重新填过!');JavaScript:history.back();</script>");
		} else if (pwd.equals(confirmPwd)) {
			if (dao.addUser(user)) {
				// 最后要记得清空缓存区，并且关闭。
				out.println(
						"<script type='text/javascript'>alert('注册成功，请跳至首页进行登录!');JavaScript:window.location.href='./index1.jsp';</script>");

			}
		} else {
			out.println("<script>alert('注册失败，两次输入密码不一致,请重新输入');JavaScript:history.back();</script>");
		}
		out.flush();
		out.close();
	}
}
