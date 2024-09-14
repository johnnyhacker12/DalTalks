package com.group13.DalTalks.service.Implementations;

import com.group13.DalTalks.model.User;
import com.group13.DalTalks.repository.UserRepository;
import com.group13.DalTalks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import java.util.regex.Pattern;
import java.util.*;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public String createUser(User user) {
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
        checkDuplicateEmail(user.getEmail());

        userRepository.save(user);
        return "User created successfully";
    }

    private void validateEmail(String email) {
        String errorMessage = "The email address used is invalid. This application is only targeted for employees " +
                "and students who are currently enrolled in Dalhousie.";

        if (!email.endsWith("@dal.ca")) {
            throw new IllegalArgumentException(errorMessage);
        }
    }


    private void validatePassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$";
        String errorMessage = "Password must be at least 8 characters long, contain at least one uppercase letter, " +
                "one lowercase letter, one number, and one special character.";

        if (!Pattern.compile(regex).matcher(password).matches()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }


    private void checkDuplicateEmail(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }
    }

    @Override
    public User login(String email, String password) throws IllegalArgumentException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email address!"));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password!");
        }
        else if(!(user.getStatus() == null)){
            throw new IllegalArgumentException("User account has not been accepted!");
        }

        return user;
    }

    @Override
    public User forgotPassword(String email, String securityAnswer) throws IllegalArgumentException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email address!"));

        if (!user.getSecurityAnswer().equals(securityAnswer)) {
            throw new IllegalArgumentException("Security answer is incorrect!");
        }

        return user;
    }

    @Override
    public void resetPassword(String email, String newPassword) throws IllegalArgumentException {
        validatePassword(newPassword);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email address!"));
        user.setPassword(newPassword);
        userRepository.save(user);
    }
    
    @Override
    public List<User> getAllUserExcept(int userID) {
        List<User> getAllUsers = this.userRepository.findAll();
        Stream<User> StreamUsers = getAllUsers.stream();
        Stream<User> filterUsers = StreamUsers.filter(user -> user.getId() != userID);
        return filterUsers.collect(Collectors.toList());
    }

    @Override
    public List<User> getAllPendingUser() {
        List<User> getAllUsers = this.userRepository.findAll();
        Stream<User> StreamUsers = getAllUsers.stream();
        Stream<User> filterUsers = StreamUsers.filter(user -> "pending".equals(user.getStatus()));
        return filterUsers.collect(Collectors.toList());
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public List<User> getAllApprovedUser(){
        List<User> getAllUsers = this.userRepository.findAll();
        Stream<User> StreamUsers = getAllUsers.stream();
        Stream<User> filterUsers = StreamUsers.filter(user -> user.getStatus() == null);
        return filterUsers.collect(Collectors.toList());
    }

    @Override
    public String getEmailByUserID(Integer userID) {
        Optional<User> userOptional = userRepository.findById(userID);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getEmail();
        } else {
            return ("User not found with ID: " + userID);
        }
    }

    @Override
    public String acceptUser(int userID){
        Optional<User> userOptional = userRepository.findById(userID);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(null);
            userRepository.save(user);
            return "User successfully save";
        } else {
            return "Can not find user";
        }
    }

    @Override
    public void deleteUser(int userID){
        this.userRepository.deleteById(userID);
    }

    @Override
    public String getRole(int userID) {
        Optional<User> userOptional = userRepository.findById(userID);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getRole();
        } else {
            return ("User not found with ID: " + userID);
        }
    }

    @Override
    public String setRole(int userID){
        Optional<User> userOptional = userRepository.findById(userID);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setRole("BSB");
            userRepository.save(user);
            return "User successfully save";
        } else {
            return "Can not find user";
        }
    }

    @Override
    public String setRole2(int userID){
        Optional<User> userOptional = userRepository.findById(userID);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setRole("user");
            userRepository.save(user);
            return "User successfully save";
        } else {
            return "Can not find user";
        }
    }
}