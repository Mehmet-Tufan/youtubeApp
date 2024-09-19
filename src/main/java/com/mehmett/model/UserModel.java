package com.mehmett.model;

import com.mehmett.controller.CommentController;
import com.mehmett.controller.LikeController;
import com.mehmett.controller.UserController;
import com.mehmett.controller.VideoController;
import com.mehmett.entity.User;
import com.mehmett.entity.Video;

import java.util.List;

public class UserModel {
	
	public CommentController commentController = CommentController.getInstance();
	public UserController userController = UserController.getInstance();
	public LikeController likeController = LikeController.getInstance();
	public VideoController videoController = VideoController.getInstance();
	
	
	public String name;
	public String surname;
	public String email;
	public String username;
	public List<Video> videoList;
	
	public UserModel(User user) {
		this.name=user.getName();
		this.surname=user.getSurname();
		this.email=user.getEmail();
		this.username=user.getUsername();
		this.videoList=videoController.findVideosOfUser(user);
	}
	
	
		public void displayUser() {
			System.out.println("--------------------------------------------------");
			System.out.println("Name           : " + this.name + " " + this.surname);
			System.out.println("Email          : " + this.email);
			System.out.println("Username       : " + this.username);
			System.out.println("--------------------------------------------------");
			System.out.println(username+" ADLI KULLANICININ VİDEOLARI");
			videoList.stream().sorted((v1, v2) -> v2.getViews().compareTo(v1.getViews())).limit(5).forEach(video -> System.out.println("📺 : "+video.getViews()+" "+video.getTitle()));
			System.out.println("--------------------------------------------------");
		}
}