package com.group13.DalTalks.service;

import com.group13.DalTalks.model.Post;

import java.util.List;

public interface PostService{
    List<Post> getAllPost();

    Post getPostById(int id);
}
