package com.group13.DalTalks.service;

import com.group13.DalTalks.model.ProfilePage;

import java.util.List;

public interface ProfilePageService {
  ProfilePage createProfile(ProfilePage page);

  ProfilePage getProfilePageById(int id);

  ProfilePage getProfilePageByUserID(int userID);

  ProfilePage updateProfilePage(int userID, ProfilePage page);

  ProfilePage deleteProfilePage(int id);

  List<ProfilePage> getAllProfiles();
}
