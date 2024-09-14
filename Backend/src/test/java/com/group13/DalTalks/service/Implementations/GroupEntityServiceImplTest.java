package com.group13.DalTalks.service.Implementations;

import com.group13.DalTalks.model.GroupEntity;
import com.group13.DalTalks.repository.GroupEntityRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class GroupEntityServiceImplTest {

  @Mock
  private GroupEntityRepository groupRepository;

  @InjectMocks
  private GroupServiceImpl groupService;

  @Test
  public void createNewGroup() {
    GroupEntity group = new GroupEntity();

    when(groupRepository.save(group)).thenReturn(group);

    GroupEntity returned = groupService.createGroup(group);

    assertEquals(group, returned, "Group was not returned!");
  }

  @Test
  public void createNewGroup_testAttributes() {
    GroupEntity group = new GroupEntity();
    group.setGroupName("Group");
    group.setCreatorID(1);
    group.setPrivate(false);
    group.setCreation_date(new Date());

    when(groupRepository.save(group)).thenReturn(group);

    GroupEntity returned = groupService.createGroup(group);

    assertEquals(group.getGroupName(), returned.getGroupName(), "Names do not match");
    assertEquals(group.getCreatorID(), returned.getCreatorID(), "IDs do not match");
    assertEquals(group.isPrivate(), returned.isPrivate(), "Privacy levels do not match");
    assertEquals(group.getCreation_date(), returned.getCreation_date(), "Names do not match");
  }

  @Test
  public void getAllGroups() {
    GroupEntity group = new GroupEntity();

    List<GroupEntity> allGroups = new ArrayList<>();
    allGroups.add(group);

    when(groupRepository.findAll()).thenReturn(allGroups);

    List<GroupEntity> returnGroups = groupService.getAllGroups();

    assertEquals(returnGroups.size(), allGroups.size(), "returned list is the wrong size");
  }

  @Test
  public void getAllGroups_multipleEntries() {
    GroupEntity group = new GroupEntity();
    GroupEntity groupTwo = new GroupEntity();

    List<GroupEntity> allGroups = new ArrayList<>();
    allGroups.add(group);
    allGroups.add(groupTwo);

    when(groupRepository.findAll()).thenReturn(allGroups);

    List<GroupEntity> returnGroups = groupService.getAllGroups();

    assertEquals(returnGroups.size(), allGroups.size(), "returned list is the wrong size");
  }

  @Test
  public void getGroupInfo() {
    GroupEntity group = new GroupEntity();
    group.setId(1);

    when(groupRepository.findById(group.getId())).thenReturn(Optional.of(group));

    GroupEntity returned = groupService.getGroupById(group.getId());

    assertEquals(group, returned, "Group was not returned!");
  }
}