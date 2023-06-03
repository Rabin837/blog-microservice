package com.rabin.treeLeaf.controller;

import com.rabin.treeLeaf.dto.BlogDTO;
import com.rabin.treeLeaf.entity.Blog;
import com.rabin.treeLeaf.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BlogController {
    BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

   @GetMapping("/blog")
   @PreAuthorize("isAuthenticated()")
   public ResponseEntity<List<BlogDTO>> getAll(){
        List<BlogDTO> blogs=blogService.findAll();
        return new ResponseEntity<>(blogs,HttpStatus.OK);
   }
   @GetMapping("/blog/{id}")
    public ResponseEntity<Optional<BlogDTO>> getById(@PathVariable Long id){
        Optional<BlogDTO> blogDTO=blogService.findById(id);
        return new ResponseEntity<>(blogDTO,HttpStatus.OK);
   }
    @PostMapping("/blog")
    public ResponseEntity<?> save(@RequestBody Blog blog){
        blogService.save(blog);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/blog")
    public ResponseEntity<?> update(@RequestBody BlogDTO blogDTO){
        blogService.update(blogDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

  @DeleteMapping("/blog/{id}")
    public ResponseEntity<Blog> delete(@PathVariable Long id){
        blogService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
  }


}
