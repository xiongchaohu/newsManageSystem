package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NewsDAO;
import dao.UserDAO;
import dao.impl.NewsDAOImpl;
import dao.impl.UserDAOImpl;
import entity.News;
import entity.User;

/**
 * Servlet implementation class UserManagerServlet
 */
@WebServlet("/UserManagerServlet")
public class UserManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserManagerServlet() {
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
		} else if (method.equals("search")) {
			search(request, response);
		} else if (method.equals("delete")) {
			delete(request, response);
		} else if (method.equals("edit")) {
			edit(request, response);
		} else if (method.equals("update")) {
			update(request, response);
		} else if (method.equals("add")) {
			add(request, response);
		}
	}

	private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		ArrayList<User> list = new ArrayList<User>();
		UserDAOImpl dao = new UserDAOImpl();

		int currentPage = 1; // 当前页默认为第一页
		String strpage = request.getParameter("currentPage"); // 获取前台传入当前页
		if (strpage != null && !strpage.equals("")) {
			currentPage = Integer.parseInt(strpage) < 1 ? 1 : Integer.parseInt(strpage); // 将字符串转换成整型
		}

		list = dao.getAllUserByPage(currentPage);

		// 从UserDAO中获取总记录数
		int allCount = dao.getAllCount();
		// 从UserDAO中获取总页数
		int allPageCount = dao.getAllPageCount();
		// 从UserDAO中获取当前页
		currentPage = dao.getCurrentPage();

		// 存入request中
		request.setAttribute("allUsers", list);
		request.setAttribute("allCount", allCount);
		request.setAttribute("allPageCount", allPageCount);
		request.setAttribute("currentPage", currentPage);

		try {
			request.getRequestDispatcher("/jsp/admin/userManager.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username");
		if (username == "") {
			view(request, response);
		} else {
			UserDAOImpl dao = new UserDAOImpl();
			// 从UserDAO中获取所有用户信息
			User user = dao.findUserByName(username);
			// 存入request中
			request.setAttribute("user", user);
			request.setAttribute("searchUsername", username);

			try {
				request.getRequestDispatcher("/jsp/admin/userManager.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		boolean result = false;
		int id = Integer.parseInt(request.getParameter("id"));
		if (id > 0) {
			UserDAOImpl dao = new UserDAOImpl();
			result = dao.deleteUserById(id);
			if (result) {
				out.println(
						"<script type='text/javascript'>alert('删除成功!');window.location.href='./UserManagerServlet?method=view'</script>");
			} else {
				out.println("<script type='text/javascript'>alert('删除失败!');JavaScript:history.back();");
			}

		}

	}

	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAOImpl dao = new UserDAOImpl();
		int id = Integer.parseInt(request.getParameter("id"));
		User user = new User();

		if (id > 0) {
			user = dao.findUserById(id);
		}

		request.setAttribute("user", user);

		request.getRequestDispatcher("jsp/admin/editUser.jsp").forward(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		int id = Integer.parseInt(request.getParameter("id"));
		String email = request.getParameter("email");
		String username = request.getParameter("username");

		if (email.equals("") || username.equals("")) {
			out.println(
					"<script type='text/javascript'>alert('邮箱、用户名为必填项，请检查是否输入!');JavaScript:history.back();</script>");
			return;
		}

		UserDAOImpl dao = new UserDAOImpl();
		User user = new User();

		user.setId(id);
		user.setEmail(email);
		user.setUsername(username);

		boolean result = dao.updateUser(user);
		try {
			if (result) {
				out.println(
						"<script type='text/javascript'>alert('更新成功!');window.location.href='./UserManagerServlet?method=view'</script>");
			} else {
				out.println(
						"<script type='text/javascript'>alert('更新失败，请重新检查输入!');JavaScript:history.back();</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (email.equals("") || username.equals("") || password.equals("")) {
			out.println(
					"<script type='text/javascript'>alert('用户名、密码、邮箱为必填项，请检查是否输入!');JavaScript:history.back();</script>");
			return;
		}
		/* int type_id = Integer.parseInt(type); */

		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(password);

		UserDAO dao = new UserDAOImpl();
		boolean result = dao.addUser(user);
		try {
			if (result) {

				request.setAttribute("result", "添加成功!");
				out.println(
						"<script type='text/javascript'>alert('添加成功!');window.location.href='./UserManagerServlet?method=view'</script>");
			} else {
				request.setAttribute("result", "添加失败!");
				/*
				 * request.getRequestDispatcher("editNews.jsp").forward(request,
				 * response);
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
