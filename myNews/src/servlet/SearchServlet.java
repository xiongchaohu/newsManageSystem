package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.NewsDAOImpl;
import entity.News;
import entity.NewsType;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet() {
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
		/*
		 * response.getWriter().append("Served at: ").append(request.
		 * getContextPath());
		 */
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");

		int currentPage = 1; // 当前页默认为第一页
		String strpage = request.getParameter("currentPage"); // 获取前台传入当前页
		String title = request.getParameter("title");// 获取title
		if (strpage != null && !strpage.equals("")) {
			currentPage = Integer.parseInt(strpage) < 1 ? 1 : Integer.parseInt(strpage); // 将字符串转换成整型
		}

		if (title != null) {

			NewsDAOImpl dao = new NewsDAOImpl();
			// 从UserDAO中获取所有用户信息
			ArrayList<News> list = dao.searchByTitle(currentPage, title);
			// ArrayList<NewsType> typeLists=dao.getTypeName();
			// 从UserDAO中获取总记录数
			int allCount = dao.getAllCount();
			// 从UserDAO中获取总页数
			int allPageCount = dao.getAllPageCount();
			// 从UserDAO中获取当前页
			currentPage = dao.getCurrentPage();

			// 存入request中
			// request.setAttribute("typeLists", typeLists);
			request.setAttribute("allNewsByTitle", list);
			request.setAttribute("allCount", allCount);
			request.setAttribute("allPageCount", allPageCount);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("title", title);
		}

		try {
			request.getRequestDispatcher("/jsp/searchNews.jsp").forward(request, response);
			/*System.out.println("跳转被触发");*/
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*if (title != null) {
			NewsDAOImpl dao = new NewsDAOImpl();

		}*/
	}

}
