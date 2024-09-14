package com.group13.DalTalks.controller;

import com.group13.DalTalks.model.Post;
import com.group13.DalTalks.model.User;
import com.group13.DalTalks.service.PostService;
import com.group13.DalTalks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @GetMapping("/getAllPost")
    public List<Post> getAllBooks(){
        return postService.getAllPost();
    }

    @GetMapping("/getPostById/{ID}")
    public Post getPostID(@PathVariable int ID){
        return postService.getPostById(ID);
    }

    @PostMapping("/create")
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @GetMapping("/getEmailByUserID/{id}")
    public String getEmailByUserID(@PathVariable int id){
        return userService.getEmailByUserID(id);
    }
}
