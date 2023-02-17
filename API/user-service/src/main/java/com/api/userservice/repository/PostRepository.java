package com.api.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.userservice.DTO.PostDTO;
import com.api.userservice.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT new com.api.userservice.DTO.PostDTO(p.id, p.name) FROM Post p")
    List<PostDTO> getAllPost();
}
