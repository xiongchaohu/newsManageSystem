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
 * Servlet implementation class NewsServlet
 */
@WebServlet({ "/NewsServlet", "/servlet/NewsServlet" })
public class NewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewsServlet() {
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

		int currentPage = 1; // 当前页默认为第一页
		String strpage = request.getParameter("currentPage"); // 获取前台传入当前页
		if (strpage != null && !strpage.equals("")) {
			currentPage = Integer.parseInt(strpage) < 1 ? 1 : Integer.parseInt(strpage); // 将字符串转换成整型
		}
		String cid = request.getParameter("cid");
		

		if (cid != null) {
			int news_type_id = Integer.parseInt(cid);
			NewsDAOImpl dao = new NewsDAOImpl();
			// 从UserDAO中获取所有用户信息
			ArrayList<News> list = dao.findNewsByPage(currentPage, news_type_id);
			ArrayList<NewsType> typeLists = dao.getTypeName();
			ArrayList<News> listCount=dao.getAllNewsByCount();
			// 从UserDAO中获取总记录数
			int allCount = dao.getAllCount();
			// 从UserDAO中获取总页数
			int allPageCount = dao.getAllPageCount();
			// 从UserDAO中获取当前页
			currentPage = dao.getCurrentPage();

			// 存入request中
			request.setAttribute("typeLists", typeLists);
			request.setAttribute("allNews", list);
			request.setAttribute("allCount", allCount);
			request.setAttribute("allPageCount", allPageCount);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("news_type_id", cid);
			request.setAttribute("listCount", listCount);
		}

		try {
			request.getRequestDispatcher("/jsp/type_news.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void byPage(HttpServletRequest req, HttpServletResponse resp) {

	}
}
