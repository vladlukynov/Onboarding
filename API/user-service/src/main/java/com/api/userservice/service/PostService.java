package com.api.userservice.service;

import java.util.List;

import com.api.userservice.DTO.PostDTO;

public interface PostService {
    List<PostDTO> getAll();
}
