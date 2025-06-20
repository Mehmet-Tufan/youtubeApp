package com.mehmett.controller;

import com.mehmett.dto.request.CommentRequestDTO;
import com.mehmett.dto.response.CommentResponseDTO;
import com.mehmett.entity.Comment;
import com.mehmett.service.CommentService;

import java.util.List;
import java.util.Optional;

public class CommentController {

    private static CommentController instance;
    private CommentService commentService;

    private CommentController(){
        commentService = CommentService.getInstance();
    }
    public static CommentController getInstance(){
        if(instance == null){
            instance = new CommentController();
        }
        return instance;
    }



    public Optional<CommentResponseDTO> save(CommentRequestDTO commentRequestDTO) {
        return commentService.save(commentRequestDTO);
    }


    public void delete(Long id) {
        commentService.delete(id);
    }


    public void update(CommentRequestDTO commentRequestDTO) {
        commentService.update(commentRequestDTO);
    }


    public List<Comment> findAll() {
        return commentService.findAll();
    }

    public Optional<Comment> findById(Long id) {
        return commentService.findById(id);
    }
    
    public List<Comment> findCommentOfVideo(Long video_id){
        return commentService.findCommentOfVideo(video_id);
    }

    public Long countCommentsOfVideo(Long video_id){
        return commentService.countCommentsOfVideo(video_id);
    }
}