package com.mehmett.controller;

import com.mehmett.dto.request.LikeRequestDTO;
import com.mehmett.entity.Like;
import com.mehmett.service.LikeService;

import java.util.List;
import java.util.Optional;

public class LikeController {
	private static LikeController instance;
	LikeService likeService;
	
	
	public static LikeController getInstance() {
		if(instance==null){
			instance = new LikeController();
		}
		return instance;
	}
	
	private LikeController() {
		this.likeService = LikeService.getInstance();
	}
	
	public Optional<Like> save(LikeRequestDTO likeRequestDTO) {
		return likeService.save(likeRequestDTO);
	}
	
	public Optional<Like> update(LikeRequestDTO likeRequestDTO) {
		return likeService.update(likeRequestDTO);
	}
	
	
	public List<Like> findAll() {
		return likeService.findAll();
	}
	
	public Optional<Like> findById(Long id) {
		return likeService.findById(id);
	}
	
	public boolean isLikeExist(Long user_id,Long video_id) {
		return likeService.isLikeExist(user_id,video_id);
	}
	
	public Like findByUserIdAndVideoId(Long user_id,Long video_id) {
		return likeService.findByUserIdAndVideoId(user_id,video_id);
	}
	public Long countLikes(Long videoId){
		return likeService.countLikes(videoId);
	}

	public Long countDislikes(Long videoId){
		return likeService.countDislikes(videoId);
	}
}