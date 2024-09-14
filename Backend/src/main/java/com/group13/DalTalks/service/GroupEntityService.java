package com.group13.DalTalks.service;

import com.group13.DalTalks.model.GroupEntity;

import java.util.List;

public interface GroupEntityService {
  GroupEntity createGroup(GroupEntity group);

  List<GroupEntity> getAllGroups();

  GroupEntity getGroupById(int id);
}

