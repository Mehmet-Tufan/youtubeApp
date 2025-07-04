package com.mehmett.controller;

import com.mehmett.dto.request.VideoSaveRequestDTO;
import com.mehmett.dto.request.VideoUpdateRequestDTO;
import com.mehmett.dto.response.VideoResponseDTO;
import com.mehmett.entity.User;
import com.mehmett.entity.Video;
import com.mehmett.service.VideoService;
import com.mehmett.utility.ConsoleTextUtils;

import java.util.List;
import java.util.Optional;

public class VideoController {
	private static VideoController instance;
	
	VideoService videoService;
	
	public static VideoController getInstance() {
		if(instance ==null) {
			instance =new VideoController();
		}
		return instance;
	}
	
	private VideoController() {
		this.videoService=VideoService.getInstance();
	}
	
	public Optional<VideoResponseDTO> save(VideoSaveRequestDTO videoSaveRequestDTO) {
		try {
			return videoService.save(videoSaveRequestDTO);
		}
		catch (Exception e){
			ConsoleTextUtils.printErrorMessage("Controller : Video kaydedilirken hata oluştu : "+e.getMessage());
		}
		return Optional.empty();
	}
	
	public void delete(Long id) {
			videoService.delete(id);
	}
	
	public void update(VideoUpdateRequestDTO videoUpdateRequestDTO) {
			videoService.update(videoUpdateRequestDTO);
	}
	
	public List<Video> findAll() {
		return videoService.findAll();
	}
	public Optional<Video> findById(Long id) {
		return videoService.findById(id);
	}
	public List<Video> getTrendVideos (){
		return videoService.getTrendVideos();
	}
	public List<Video> findVideosByTitle(String baslik){
		return videoService.findVideosByTitle(baslik);
	}
	public List<Video> findVideosOfUser(User user){
		return videoService.findVideosOfUser(user);
	}
	
	public void goruntulenmeArttir(Video video) {
		videoService.goruntulenmeArttir(video);
	}
}