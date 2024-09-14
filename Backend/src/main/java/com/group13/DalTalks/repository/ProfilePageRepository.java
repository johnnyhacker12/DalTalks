package com.group13.DalTalks.repository;

import com.group13.DalTalks.model.ProfilePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilePageRepository extends JpaRepository<ProfilePage, Integer> {
  ProfilePage findByUserID(int userID);
}
