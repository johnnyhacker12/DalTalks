package com.group13.DalTalks;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import com.group13.DalTalks.model.User;
import com.group13.DalTalks.repository.UserRepository;
import com.group13.DalTalks.service.Implementations.UserServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserServiceImplJUnitTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService; // Use concrete implementation

    @Test
    void getAllUserExcept() {
        User user1 = new User("Alice@dal.ca", "Pending!123", "What is your mother's maiden name?", "Camille", null, "user");
        userRepository.save(user1);

        List<User> getAllUserExcept = userService.getAllUserExcept(user1.getId());
        List<User> allUser = userService.getAllUsers();

        assertEquals(allUser.size()-1, getAllUserExcept.size());
    }

    @Test
    void getAllApprovedUser() {
        User user1 = new User("Alice@dal.ca", "Pending!123", "What is your mother's maiden name?", "Camille", "pending", "user");
        userRepository.save(user1);

        List<User> getAllApprovedUser = userService.getAllApprovedUser();
        List<User> allUser = userService.getAllUsers();

        assertEquals(allUser.size()-1, getAllApprovedUser.size());
    }

    @Test
    void getAllPendingUser() {
        createPendingUser("Alice@dal.ca", "Pending!123", "What is your mother's maiden name?", "Camille");

        List<User> pendingUsers = userService.getAllPendingUser();

        assertEquals(1, pendingUsers.size());
    }

    private void createPendingUser(String email, String password, String securityQuestion, String securityAnswer) {
        User user = new User(email, password, securityQuestion, securityAnswer, "pending", "user");
        userService.createUser(user);
    }


    @Test
    void getAllUsers() {
        User user1 = new User("Alice@dal.ca", "pending!123", "What is your mother's maiden name?", "Camille", "pending", "user");
        userRepository.save(user1);
        List<User> getAllUser = userService.getAllUsers();

        assertFalse(getAllUser.isEmpty());
    }

    @Test
    void getEmailByUserID() {
        User user1 = new User("Alice@dal.ca", "pending!123", "What is your mother's maiden name?", "Camille", "pending", "user");
        userRepository.save(user1);

        String userEmail = userService.getEmailByUserID(user1.getId());

        assertEquals(user1.getEmail(), userEmail);
    }

    @Test
    void acceptUser() {
        User user = new User("Alice@dal.ca", "pending!123", "What is your mother's maiden name?", "Camille", "pending", "user");
        userRepository.save(user);
        String response = userService.acceptUser(user.getId());

        assertEquals("User successfully save", response);
    }

    @Test
    void SetRoleAdmin() {
        User user = new User("Alice@dal.ca", "Pending!123", "What is your mother's maiden name?", "Camille", "pending", "user");
        userRepository.save(user);
        userService.setRole(user.getId());
        assertEquals("BSB", user.getRole());
    }

    @Test
    void SetRoleUser() {
        User user = new User("Alice@dal.ca", "Pending!123", "What is your mother's maiden name?", "Camille", "pending", "user");
        userRepository.save(user);
        userService.setRole2(user.getId());
        assertEquals("user", user.getRole());
    }

    @Test
    void deleteUser(){
        User user = new User("Alice@dal.ca", "Pending!123", "What is your mother's maiden name?", "Camille", "pending", "user");
        userRepository.save(user);

        userService.deleteUser(user.getId());

        Optional<User> deletedUser = userRepository.findById(user.getId());
        assertEquals(Optional.empty(), deletedUser);
    }

    @Test
    void getRole() {
        User user = new User("Alice@dal.ca", "pending!123", "What is your mother's maiden name?", "Camille", "pending", "user");
        userRepository.save(user);
        assertEquals("user", user.getRole());
    }
}