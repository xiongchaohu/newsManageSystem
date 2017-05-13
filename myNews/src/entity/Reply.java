package entity;

import java.sql.Timestamp;

public class Reply {
	
	private int id;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private int comment_id;//评论的id；表示挂在哪个评论上
	private String content;
	private int user_id;
	private String username;
	private Timestamp time;
	public int getComment_id() {
		return comment_id;
	}
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	
	
}
