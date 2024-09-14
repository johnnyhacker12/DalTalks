package com.group13.DalTalks.service.Implementations;

import com.group13.DalTalks.model.ProfilePage;
import com.group13.DalTalks.repository.ProfilePageRepository;
import com.group13.DalTalks.service.ProfilePageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class ProfilePageServiceImplTest {

  @Mock
  ProfilePageRepository profilePageRepository;

  @InjectMocks
  ProfilePageServiceImpl profilePageServiceImpl;

  private final int userID = 10;

  @BeforeEach
  public void setUp() {
  }

  @Test
  public void createProfilePage_success() {
    ProfilePage profilePage = new ProfilePage();

    when(profilePageRepository.save(profilePage)).thenReturn(profilePage);

    ProfilePage saved = profilePageServiceImpl.createProfile(profilePage);

    assertEquals(profilePage, saved, "Profile pages do not match");
  }

  @Test
  public void createProfilePage_nullProfile() {
    ProfilePage profilePage = null;

    ProfilePage result = profilePageServiceImpl.createProfile(profilePage);

    assertNull(result, "Result should be null");
  }

  @Test
  public void getProfileByID_profilePresent() {
    ProfilePage profilePage = new ProfilePage();
    profilePage.setId(1);

    when(profilePageRepository.findById(profilePage.getId())).thenReturn(Optional.of(profilePage));

    ProfilePage result = profilePageServiceImpl.getProfilePageById(profilePage.getId());

    assertEquals(profilePage, result, "Profile was not found correctly by ID");
  }

  @Test
  public void getProfileByID_notFound() {

    when(profilePageRepository.findById(userID)).thenReturn(Optional.empty());

    ProfilePage result = profilePageServiceImpl.getProfilePageById(userID);

    assertNull(result, "Profile should not have been found");
  }

  @Test
  public void getProfilePageByUserID_profilePresent() {
    ProfilePage profilePage = new ProfilePage();
    profilePage.setUserID(1);

    when(profilePageRepository.findByUserID(profilePage.getUserID())).thenReturn(profilePage);

    ProfilePage result = profilePageServiceImpl.getProfilePageByUserID(profilePage.getUserID());

    assertEquals(profilePage, result, "Profile page not found by user ID");
  }

  @Test
  public void getProfilePageByUserID_profileNotPresent() {

    when(profilePageRepository.findByUserID(userID)).thenReturn(null);

    ProfilePage result = profilePageServiceImpl.getProfilePageByUserID(userID);

    assertNull(result, "Profile page should not have been found!");
  }

  @Test
  public void updateProfilePage_present() {
    //create a profile page
    ProfilePage profilePage = new ProfilePage();
    profilePage.setId(1);
    profilePage.setMajor("Math");
    profilePage.setLocation("Halifax");
    profilePage.setBirthday("March 17th");
    profilePage.setInterests("Hiking");
    profilePage.setUserID(1);

    //create a profile page to update it with
    ProfilePage newProfileInformation = new ProfilePage();
    newProfileInformation.setId(1);
    newProfileInformation.setMajor("Math");
    newProfileInformation.setLocation("Truro");
    newProfileInformation.setBirthday("March 17th");
    newProfileInformation.setInterests("Hiking");
    newProfileInformation.setUserID(1);

    when(profilePageRepository.save(profilePage)).thenReturn(profilePage);
    when(profilePageRepository.findByUserID(profilePage.getUserID())).thenReturn(profilePage);

    ProfilePage updated = profilePageServiceImpl.updateProfilePage(profilePage.getUserID(), newProfileInformation);

    assertEquals(updated.getLocation(), newProfileInformation.getLocation(), "Location was not updated correctly");
  }

  @Test
  public void updateProfilePage_notFound() {
    ProfilePage profilePage = new ProfilePage();
    profilePage.setId(1);
    profilePage.setMajor("Math");
    profilePage.setLocation("Halifax");
    profilePage.setBirthday("March 17th");
    profilePage.setInterests("Hiking");
    profilePage.setUserID(1);

    when(profilePageRepository.save(profilePage)).thenReturn(profilePage);
    when(profilePageRepository.findByUserID(profilePage.getUserID())).thenReturn(null);

    ProfilePage updated = profilePageServiceImpl.updateProfilePage(profilePage.getUserID(), profilePage);

    assertEquals(profilePage, updated, "New profile should have been created");
  }

  @Test
  public void deleteProfilePage_present() {
    ProfilePage profilePage = new ProfilePage();
    profilePage.setId(1);
    profilePage.setMajor("Math");
    profilePage.setLocation("Halifax");
    profilePage.setBirthday("March 17th");
    profilePage.setInterests("Hiking");
    profilePage.setUserID(1);

    when(profilePageRepository.findById(profilePage.getId())).thenReturn(Optional.of(profilePage));

    ProfilePage returned = profilePageServiceImpl.deleteProfilePage(profilePage.getId());

    assertEquals(profilePage, returned, "Profile page was not returned after being deleted");
  }

  @Test
  public void deleteProfilePage_notFound() {

    when(profilePageRepository.findById(userID)).thenReturn(Optional.empty());

    ProfilePage returned = profilePageServiceImpl.deleteProfilePage(userID);

    assertNull(returned, "Null should have been returned");
  }
}