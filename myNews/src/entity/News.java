package entity;

import java.sql.Date;
import java.util.*;

public class News {

	private int id;
	private String title;
	private String content;
	private Date creat_time;
	private int news_type_id;
	private int news_count;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreat_time() {
		return creat_time;
	}

	public void setCreat_time(Date creat_time) {
		this.creat_time = creat_time;
	}

	public int getNews_type_id() {
		return news_type_id;
	}

	public void setNews_type_id(int news_type_id) {
		this.news_type_id = news_type_id;
	}
	
	public int getNews_count() {
		return news_count;
	}

	public void setNews_count(int news_count) {
		this.news_count = news_count;
	}


}
