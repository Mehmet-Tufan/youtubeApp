package com.mehmett.service;

import com.mehmett.dto.request.CommentRequestDTO;
import com.mehmett.dto.response.CommentResponseDTO;
import com.mehmett.entity.Comment;
import com.mehmett.entity.User;
import com.mehmett.repository.CommentRepository;
import com.mehmett.utility.ConsoleTextUtils;

import java.util.List;
import java.util.Optional;

public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private static CommentService instance;


    private CommentService() {
        this.commentRepository = CommentRepository.getInstance();
        this.userService = UserService.getInstance();
    }

    public static CommentService getInstance() {
        if (instance == null) {
            instance = new CommentService();
        }
        return instance;
    }




    public Optional<CommentResponseDTO> save(CommentRequestDTO commentRequestDTO) {
        Comment comment;
        CommentResponseDTO responseDTO = new CommentResponseDTO();
        try {
            Optional<User> userOpt =
                    userService.findByUsernameAndPassword(commentRequestDTO.getUsername(), commentRequestDTO.getPassword());
            if (userOpt.isPresent()) {
                comment = new Comment();
                comment.setComment(commentRequestDTO.getComment());
                comment.setUser_id(userOpt.get().getId());
                comment.setVideo_id(commentRequestDTO.getVideoId());
                commentRepository.save(comment);
                ConsoleTextUtils.printSuccessMessage(comment.getComment() + " yorumu eklendi.");
                responseDTO.setComment(comment.getComment());
                responseDTO.setUsername(userOpt.get().getUsername());
            }
        } catch (Exception e) {
            ConsoleTextUtils.printErrorMessage("Service : Yorum kaydedilirken hata oluştu : " + e.getMessage());
        }
        return Optional.of(responseDTO);
    }

    public void delete(Long id) {
        try {
            commentRepository.delete(id);
            ConsoleTextUtils.printSuccessMessage("Yorum silindi.");
        } catch (Exception e) {
            ConsoleTextUtils.printErrorMessage("CommentService: Yorum silinirken bir hata oluştu.");
        }
    }

    public void update(CommentRequestDTO commentRequestDTO) {
        Optional<Comment> comment = findById(commentRequestDTO.getId());
        if (comment.isPresent()) {
            comment.get().setComment(commentRequestDTO.getComment());
            commentRepository.update(comment.get());
            ConsoleTextUtils.printSuccessMessage("Yorum güncellendi.");
        }
    }


    public List<Comment> findAll() {
        return commentRepository.findAll();
    }


    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }
    
    public List<Comment> findCommentOfVideo(Long video_id){
        return commentRepository.findCommentOfVideo(video_id);
    }

    public Long countCommentsOfVideo(Long video_id){
        return commentRepository.countCommentsOfVideo(video_id);
    }
}