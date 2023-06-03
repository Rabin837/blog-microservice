package com.rabin.comment_service.service;

import com.rabin.comment_service.dto.CommentDto;
import com.rabin.comment_service.entity.Blog;
import com.rabin.comment_service.entity.Comment;
import com.rabin.comment_service.exception.BlogApiException;
import com.rabin.comment_service.exception.ResourceNotFoundException;
import com.rabin.comment_service.repository.BlogRepository;
import com.rabin.comment_service.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final BlogRepository blogRepository;

    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, BlogRepository blogRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.blogRepository = blogRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long blogId, CommentDto commentDto) {
        //convert Dto to entity
        Comment comment = mapToEntity(commentDto);
        Blog blog = blogRepository.findById(blogId).orElseThrow(()->new ResourceNotFoundException("Blog", "id", blogId));
        comment.setBlog(blog);

        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long blogId) {
        //retrieve comments by blogId
        List<Comment> comments = commentRepository.findByBlogId(blogId);

        // convert list of comment entities to list of dtos
        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long blogId, Long commentId) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(()->new ResourceNotFoundException("Blog", "id", blogId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getBlog().getId().equals(blog.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to blog");
        }

        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(Long blogId, long commentId, CommentDto commentDto) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(()->new ResourceNotFoundException("Blog", "id", blogId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getBlog().getId().equals(blog.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to blog");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);

    }

    @Override
    public void deleteComment(Long blogId, Long commentId) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(()->new ResourceNotFoundException("Blog", "id", blogId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getBlog().getId().equals(blog.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to blog");
        }

        commentRepository.delete(comment);
    }

    private CommentDto mapToDTO(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }
}
