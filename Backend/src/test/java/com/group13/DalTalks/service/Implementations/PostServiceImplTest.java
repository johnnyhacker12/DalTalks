package com.group13.DalTalks.service.Implementations;

import com.group13.DalTalks.model.Post;
import com.group13.DalTalks.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class PostServiceImplTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostServiceImpl postService;

    int userID = 20;

    @Test
    void getAllPost() {
        Post post = new Post(userID, "Hello everybody", "Post title 1");
        postRepository.save(post);
        List<Post> getAllPost = postService.getAllPost();
        assertFalse(getAllPost.isEmpty());
    }

    @Test
    void getPostById() {
        Post post = new Post(userID, "Hello everybody", "Post title 1");
        postRepository.save(post);
        Post getPost = postService.getPostById(post.getPostId());
        assertEquals(getPost, post);
    }

    @Test
    void post_testAttributes() {
        Post post = new Post();
        post.setUserID(userID);
        post.setPostTitle("Hello everybody");
        post.setPostBodyContent("Post body content 1");

        postRepository.save(post);
        Post getPost = postService.getPostById(post.getPostId());

        assertEquals(getPost.getPostTitle(), "Hello everybody", "Incorrect title");
        assertEquals(getPost.getPostBodyContent(), "Post body content 1", "Incorrect body content");
        assertEquals(getPost.getPostId(), post.getPostId(), "Wrong id");
        assertEquals(getPost.getUserID(), userID, "Wrong id");
    }
}