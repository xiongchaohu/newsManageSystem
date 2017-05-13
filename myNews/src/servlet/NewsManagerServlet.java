package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.UpdatableResultSet;

import dao.NewsDAO;
import dao.impl.NewsDAOImpl;
import entity.News;
import entity.NewsType;

/**
 * Servlet implementation class NewsManagerServlet
 */
@WebServlet("/NewsManagerServlet")
public class NewsManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewsManagerServlet() {
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
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");

		String method = request.getParameter("method");

		if (method.equals("view")) {
			try {
				view(request, response);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		} else if (method.equals("add")) {
			add(request, response);
		} else if (method.equals("delete")) {
			delete(request, response);
		} else if (method.equals("edit")) {
			edit(request, response);

		} else if (method.equals("update")) {
			update(request, response);
		} else if (method.equals("search")) {
			search(request, response);
		}
	}

	private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		ArrayList<News> list = new ArrayList<News>();
		NewsDAOImpl dao = new NewsDAOImpl();

		int currentPage = 1; // 当前页默认为第一页
		String strpage = request.getParameter("currentPage"); // 获取前台传入当前页
		if (strpage != null && !strpage.equals("")) {
			currentPage = Integer.parseInt(strpage) < 1 ? 1 : Integer.parseInt(strpage); // 将字符串转换成整型
		}

		list = dao.getAllNewsByPage(currentPage);

		// 从NewsDAO中获取总记录数
		int allCount = dao.getAllCount();
		// 从UserDAO中获取总页数
		int allPageCount = dao.getAllPageCount();
		// 从UserDAO中获取当前页
		currentPage = dao.getCurrentPage();

		// 存入request中
		request.setAttribute("allNews", list);
		request.setAttribute("allCount", allCount);
		request.setAttribute("allPageCount", allPageCount);
		request.setAttribute("currentPage", currentPage);

		try {
			request.getRequestDispatcher("/jsp/admin/newsManager.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		ArrayList<News> list = new ArrayList<News>();

		String title = request.getParameter("title");
		String type = request.getParameter("type");
		String content = request.getParameter("content");

		if (title.equals("") || content.equals("") || type.equals("")) {
			out.println(
					"<script type='text/javascript'>alert('内容、标题、新闻类别为必填项，请检查是否输入!');JavaScript:history.back();</script>");
			return;
		}
		int type_id = Integer.parseInt(type);

		News news = new News();
		news.setTitle(title);
		news.setContent(content);
		news.setNews_type_id(type_id);
		news.setNews_count(0);

		NewsDAO dao = new NewsDAOImpl();
		boolean result = dao.insertNews(news);
		try {
			if (result) {

				request.setAttribute("result", "添加成功!");
				out.println("<script type='text/javascript'>alert('添加成功，请检查所有输入!');</script>");
				view(request, response);
			} else {
				request.setAttribute("result", "添加失败!");
				request.getRequestDispatcher("editNews.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NewsDAOImpl dao = new NewsDAOImpl();
		int id = Integer.parseInt(request.getParameter("id"));
		News news = new News();

		if (id > 0) {
			news = dao.getNewsById(id);
		}

		request.setAttribute("news", news);

		request.getRequestDispatcher("jsp/admin/updateNews.jsp").forward(request, response);
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		boolean result = false;
		int id = Integer.parseInt(request.getParameter("id"));
		if (id > 0) {
			NewsDAOImpl dao = new NewsDAOImpl();
			result = dao.deleteNewsById(id);
			if (result) {
				out.println(
						"<script type='text/javascript'>alert('删除成功!');window.location.href='./NewsManagerServlet?method=view'</script>");
			} else {
				out.println("<script type='text/javascript'>alert('删除失败!');JavaScript:history.back();");
			}

		}

	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String type = request.getParameter("type");

		if (title.equals("") || content.equals("") || type.equals("")) {
			out.println(
					"<script type='text/javascript'>alert('内容、标题、新闻类别为必填项，请检查是否输入!');JavaScript:history.back();</script>");
			return;
		}

		NewsDAOImpl dao = new NewsDAOImpl();
		News news = new News();

		news.setId(id);
		news.setContent(content);
		news.setNews_type_id(Integer.parseInt(type));
		news.setTitle(title);
		news.setNews_count(0);
		boolean result = dao.updateNews(news);
		try {
			if (result) {
				out.println(
						"<script type='text/javascript'>alert('更新成功!');window.location.href='./NewsManagerServlet?method=view'</script>");
			} else {
				out.println(
						"<script type='text/javascript'>alert('更新失败，请重新检查输入!');JavaScript:history.back();</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int currentPage = 1; // 当前页默认为第一页
		String strpage = request.getParameter("currentPage"); // 获取前台传入当前页
		if (strpage != null && !strpage.equals("")) {
			currentPage = Integer.parseInt(strpage) < 1 ? 1 : Integer.parseInt(strpage); // 将字符串转换成整型
		}
		String title = request.getParameter("title");

		NewsDAOImpl dao = new NewsDAOImpl();
		// 从UserDAO中获取所有用户信息
		ArrayList<News> list = dao.searchByTitle(currentPage, title);
		// 从UserDAO中获取总记录数
		int allCount = dao.getAllCount();
		// 从UserDAO中获取总页数
		int allPageCount = dao.getAllPageCount();
		// 从UserDAO中获取当前页
		currentPage = dao.getCurrentPage();

		// 存入request中
		request.setAttribute("allNews", list);
		request.setAttribute("allCount", allCount);
		request.setAttribute("allPageCount", allPageCount);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("searchTitle", title);

		try {
			request.getRequestDispatcher("/jsp/admin/newsManager.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
