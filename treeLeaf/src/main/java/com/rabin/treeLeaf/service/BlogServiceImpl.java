package com.rabin.treeLeaf.service;

import com.rabin.treeLeaf.dto.BlogDTO;
import com.rabin.treeLeaf.entity.Blog;
import com.rabin.treeLeaf.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final BlogDTOMapper blogDTOMapper;

    public BlogServiceImpl(BlogRepository blogRepository, BlogDTOMapper blogDTOMapper) {
        this.blogRepository = blogRepository;
        this.blogDTOMapper = blogDTOMapper;
    }

    @Override
    public List<BlogDTO> findAll() {
         return blogRepository.findAll().
                 stream().
                 map(blogDTOMapper).
                 collect(Collectors.toList());
    }

    @Override
    public Optional<BlogDTO> findById(Long id) {
        return Optional.ofNullable(blogRepository.findById(id)
                .map(blogDTOMapper).orElseThrow(NoSuchElementException::new));
    }

    @Override
    public void save(Blog blog) {
        blogRepository.save(blog);
    }

    @Override
    public void update(BlogDTO blogDTO) {
              Optional<Blog> optionalBlog=blogRepository.findById(blogDTO.getId());
              if(optionalBlog.isPresent()){
                 Blog blog=optionalBlog.get();
                 blog.setTitle(blogDTO.getTitle());
                 blog.setContent(blogDTO.getContent());
                 blog.setAuthor(blogDTO.getAuthor());
                 blog.setUpdatedAt(blogDTO.getUpdatedAt());
                 blogRepository.save(blog);
              }
              else throw new RuntimeException();
    }

    @Override
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

}
