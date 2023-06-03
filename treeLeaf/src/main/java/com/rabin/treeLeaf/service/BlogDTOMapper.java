package com.rabin.treeLeaf.service;

import com.rabin.treeLeaf.dto.BlogDTO;
import com.rabin.treeLeaf.entity.Blog;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BlogDTOMapper implements Function<Blog, BlogDTO> {

    @Override
    public BlogDTO apply(Blog blog) {
        return new BlogDTO(
                blog.getId(),
                blog.getTitle(),
                blog.getContent(),
                blog.getAuthor(),
                blog.getCreatedAt(),
                blog.getUpdatedAt()
        );
    }
}
