package com.mehmett.service;

import com.mehmett.entity.CommentGarbage;
import com.mehmett.repository.CommentGarbageRepository;

import java.util.List;
import java.util.Optional;

public class CommentGarbageService {
	CommentGarbageRepository commentGarbageRepository;
	private static CommentGarbageService instance;
	
	private CommentGarbageService() {
		commentGarbageRepository=CommentGarbageRepository.getInstance();
	}

	public static CommentGarbageService getInstance() {
		if (instance == null) {
			instance = new CommentGarbageService();
		}
		return instance;
	}
	
	public Optional<CommentGarbage> save(CommentGarbage commentGarbage) {
		return 	commentGarbageRepository.save(commentGarbage);
	}
	
	public List<CommentGarbage> findAllOldComments(Long comment_id){
		return commentGarbageRepository.findAllOldComments(comment_id);
	}
}