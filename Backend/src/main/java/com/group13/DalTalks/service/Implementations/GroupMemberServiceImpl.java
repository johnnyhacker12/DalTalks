package com.group13.DalTalks.service.Implementations;

import com.group13.DalTalks.model.GroupMembers;
import com.group13.DalTalks.repository.GroupMemberRepository;
import com.group13.DalTalks.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMemberServiceImpl implements GroupMemberService {
  //here we implement the interface methods

  @Autowired
  private GroupMemberRepository groupMemberRepository;

  @Override
  public GroupMembers saveGroupMember(GroupMembers groupMembers, int signedInUserID) {
    if (groupMembers.getGroup() == null || groupMembers.getUser() == null) {
      return null;
    }
    if (groupMembers.getGroup().isPrivate() && groupMembers.getGroup().getCreatorID() != signedInUserID) {
      throw new RuntimeException("You do not have authorization to add a group member!");
    }
    if (!groupMembers.getGroup().isPrivate()) {
      groupMembers.setActive(true);
    }
    return groupMemberRepository.save(groupMembers);
  }

  @Override
  public GroupMembers removeGroupMember(GroupMembers groupMembers, int signedInUserID) {
    if (groupMembers.getGroup().isPrivate() && groupMembers.getGroup().getCreatorID() != signedInUserID) {
      throw new RuntimeException("You do not have authorization to remove a group member!");
    }
    int groupID = groupMembers.getGroup().getId();
    int userID = (groupMembers.getUser().getId());
    groupMemberRepository.deleteGroupMembersByGroupIdAndUserId(groupID, userID);

    return groupMembers;
  }

  @Override
  public List<GroupMembers> findAllGroupMembersByGroupId(int id) {
    return groupMemberRepository.findByGroupId(id);
  }

  @Override
  public GroupMembers activateGroupMember(GroupMembers groupMembers, int signedInUserID) {
    if (groupMembers == null) {
      return null;
    }
    if (groupMembers.getGroup().isPrivate() && groupMembers.getGroup().getCreatorID() != signedInUserID) {
      throw new RuntimeException("You do not have authorization to activate a group member!");
    }
    int groupID = groupMembers.getGroup().getId();
    int userID = (groupMembers.getUser().getId());
    GroupMembers existingGroupMember = groupMemberRepository.findByGroupIdAndUserId(groupID, userID);
    if (existingGroupMember != null) {
      existingGroupMember.setActive(true);
      groupMemberRepository.save(existingGroupMember);
    }
    return existingGroupMember;

  }
  @Override
  public List<GroupMembers> findGroupsByUserId(int userId) {
    return groupMemberRepository.findByUserId(userId);
  }
}
