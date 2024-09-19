package com.mehmett.entity;

public class Like {
	private Long id;
	private Long user_id;
	private Long video_id;
	private int status; // -1 dislike 1 like 2 dislike to nötr 3 dislike to like 4 like to nötr 5 like to dislike
	public Like() {
	}
	
	public Like(Long user_id, Long video_id, int status) {
		this.user_id = user_id;
		this.video_id = video_id;
		this.status = status;
	}
	
	public Like(Long id, Long user_id, Long video_id, int status) {
		this.id = id;
		this.user_id = user_id;
		this.video_id = video_id;
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUser_id() {
		return user_id;
	}
	
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	
	public Long getVideo_id() {
		return video_id;
	}
	
	public void setVideo_id(Long video_id) {
		this.video_id = video_id;
	}
	
}