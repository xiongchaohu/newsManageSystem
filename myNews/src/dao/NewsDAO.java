package dao;
import java.util.ArrayList;

import entity.News;
import entity.NewsType;
public interface NewsDAO {
	
	/*
	 * 插入一条新闻
	 * @param news
	 * @return boolean
	 * */
	public boolean insertNews(News news);
	
	/*
	 * 根据id删除新闻
	 * @param news_id 新闻id
	 * */
	public boolean deleteNewsById(int news_id);
	
	/*
	 * 修改新闻
	 * */
	public boolean updateNews(News news);
	
	/*
	 * 根据新闻类别查询
	 * */
	public ArrayList<News> getAllNewsByTypeId( int news_type_id);
	
	/*
	 * 获取所有新闻
	 * */
	public ArrayList<News> getAllNews();
	
	/**根据新闻标题进行搜索
	 * @param cPage
	 * @param title
	 * @return
	 */
	public ArrayList<News> searchByTitle(int cPage,String title);
	
	/**更新点击率
	 * @param count
	 * @param id
	 * @return
	 */
	public boolean updateCount(int count,int id);
	
	/**根据新闻类别分页显示新闻信息
	 * @param cPage
	 * @param news_type_id  新闻类别
	 * @return
	 */
	public ArrayList<News> findNewsByPage(int cPage, int news_type_id);
	
	/**分页显示所有新闻信息
	 * @param cPage
	 * @return
	 */
	public ArrayList<News> getAllNewsByPage(int cPage);
	
	/**获取所有的新闻类别
	 * @return
	 */
	public ArrayList<NewsType> getAllType();
	
	
	/**获取所有的新闻类别名称
	 * @return
	 */
	public ArrayList<NewsType> getTypeName();
	/**根据id查询某条新闻
	 * @param id
	 * @return
	 */
	public News getNewsById( int id);
	/**根据新闻类别id获取新闻类别名称
	 * @param type_id
	 * @return
	 */
	public String findTypeNameByType(int type_id);
	//根据新闻点击率进行排序，获取前10条新闻
	public ArrayList<News> getAllNewsByCount();
	/**修改新闻类别
	 * @param newsType
	 * @return
	 */
	public boolean updateNewsType(NewsType newsType);
	/**添加新闻类别
	 * @param newsType
	 * @return
	 */
	public boolean addNewsType(NewsType newsType);
	
	/**根据新闻类别id删除新闻类别
	 * @param news_type_id
	 * @return
	 */
	public boolean deleteNewsTypeById(int news_type_id);
}
