package dao;
import entity.*;
import java.util.ArrayList;;
public interface CommentDAO {

	/**根据新闻id分页查询该条新闻下所有评论信息
	 * @param cPage  当前页
	 * @param news_id 新闻id
	 * @return ArrayList<Comment>
	 */
	public ArrayList<Comment> getAllCommentByNewsId(int cPage,int news_id);
	
	/**添加评论即发布评论
	 * @param comment 所要发布的评论信息
	 * @return  若发布成功，则返回true，否则，返回false
	 */
	public boolean addComment(Comment comment);
	
	/**分页显示评论信息（管理员管理评论调用）
	 * @param cPage 当前页
	 * @return ArrayList<Comment>评论信息列表
	 */
	public ArrayList<Comment> getAllFlagComment(int cPage);
	
	/**根据评论id得到该条评论信息
	 * @param id 评论id
	 * @return comment 该条评论信息
	 */
	public Comment getCommentById(int id);
	
	/**根据评论id删除某条评论
	 * @param id  评论id
	 * @return 若删除成功，则返回true，否则返回false
	 */
	public boolean deleteCommentById(int id);
	
}
