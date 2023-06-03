package com.rabin.comment_service.controller;

import com.rabin.comment_service.dto.CommentDto;
import com.rabin.comment_service.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "CRUD REST APIs for Comment Resources")
@RestController
@RequestMapping("/api/v1")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "Create Comment REST API")
    @PostMapping("/blogs/{blogId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "blogId") long blogId,
                                                    @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(blogId, commentDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get all comments by post id REST API")
    @GetMapping("/blogs/{blogId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "blogId") Long blogId){
        return commentService.getCommentsByPostId(blogId);
    }

    @ApiOperation(value = "Get single comment by post Id and comment id REST API")
    @GetMapping("/blogs/{blogId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "blogId") Long blogId,
                                                     @PathVariable(value = "commentId") Long commentId) {
        CommentDto commentDto = commentService.getCommentById(blogId, commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Update single comment by post Id and comment id REST API")
    @PutMapping("/blogs/{blogId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "blogId") Long blogId,
                                                    @PathVariable(value = "commentId") Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto){
        CommentDto updatedComment = commentService.updateComment(blogId, commentId, commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete single comment by post Id and comment id REST API")
    @DeleteMapping("/blogs/{blogId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "blogId") Long blogId,
                                                @PathVariable(value = "commentId") Long commentId){
        commentService.deleteComment(blogId, commentId);
        return new ResponseEntity<>("Comment deleted successfully!", HttpStatus.OK);
    }
}