package dao;

import java.util.ArrayList;

import entity.Reply;

public interface ReplyDAO {

	public boolean addReply(Reply reply);
	public ArrayList<Reply> getAllReplyByCommentId(int cPage,int comment_id);
	public int getAllCount(int comment_id);
	
}
