package com.group13.DalTalks.controller;

import com.group13.DalTalks.model.ProfilePage;
import com.group13.DalTalks.service.ProfilePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/profiles")
public class ProfilePageController {

  @Autowired
  private ProfilePageService profilePageService;

  @PostMapping("/save")
  public ProfilePage createProfilePage(@RequestBody ProfilePage profilePage) {
    return profilePageService.createProfile(profilePage);
  }

  @GetMapping("/getById/{id}")
  public ProfilePage getProfilePageById(@PathVariable int id) {
    return profilePageService.getProfilePageById(id);
  }

  @GetMapping("/getByUserID/{id}")
  public ProfilePage getProfilePageByUserID(@PathVariable int id) {
    return profilePageService.getProfilePageByUserID(id);
  }

  @PostMapping("/update/{userID}")
  public ProfilePage updateProfilePageByUserID(@PathVariable int userID, @RequestBody ProfilePage profilePage){
    return profilePageService.updateProfilePage(userID, profilePage);
  }

  @DeleteMapping("/delete/{id}")
  public ProfilePage deleteProfilePage(@PathVariable int id){
    return profilePageService.deleteProfilePage(id);
  }

  @GetMapping("/getAllProfiles")
  public List<ProfilePage> getAllProfiles() {
    return profilePageService.getAllProfiles();
  }
}
