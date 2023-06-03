package com.rabin.treeLeaf.service;

import com.rabin.treeLeaf.dto.BlogDTO;
import com.rabin.treeLeaf.entity.Blog;

import java.util.List;
import java.util.Optional;

public interface BlogService {

    List<BlogDTO> findAll();
    Optional<BlogDTO> findById(Long id);

    void save(Blog blog);
    void update(BlogDTO blogDTO);

    void delete(Long id);
}
