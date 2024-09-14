package com.group13.DalTalks.service;

import com.group13.DalTalks.model.GroupMembers;

import java.util.List;

public interface GroupMemberService {
  //here we can define the methods that the service needs
  //-> adding/removing group members, obtaining group members for a particular group

  GroupMembers saveGroupMember(GroupMembers groupMembers, int signedInUserID);

  GroupMembers removeGroupMember(GroupMembers groupMembers, int signedInUserID);

  List<GroupMembers> findAllGroupMembersByGroupId(int id);

  GroupMembers activateGroupMember(GroupMembers groupMembers, int signedInUserID);

  List<GroupMembers> findGroupsByUserId(int userId);
}


