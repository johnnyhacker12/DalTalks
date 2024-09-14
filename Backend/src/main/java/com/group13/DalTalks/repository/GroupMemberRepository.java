package com.group13.DalTalks.repository;

import com.group13.DalTalks.model.GroupMembers;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMembers, Integer> {
  List<GroupMembers> findByGroupId(int id);

  @Transactional
  void deleteGroupMembersByGroupIdAndUserId(int groupId, int userId);

  GroupMembers findByGroupIdAndUserId(int groupId, int userId);

  List<GroupMembers> findByUserId(int userId);


  //this allows us to interact with the group members table
  //-> ie we could define a method to search for a particular group, inactive/active members, etc..
}
