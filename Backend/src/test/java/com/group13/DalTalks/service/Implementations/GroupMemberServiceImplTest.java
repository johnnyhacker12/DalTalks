package com.group13.DalTalks.service.Implementations;

import com.group13.DalTalks.model.GroupEntity;
import com.group13.DalTalks.model.GroupMembers;
import com.group13.DalTalks.model.User;
import com.group13.DalTalks.repository.GroupMemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class GroupMemberServiceImplTest {
  @Mock
  private GroupMemberRepository groupMemberRepository;

  @InjectMocks
  private GroupMemberServiceImpl groupMemberService;


  @Test
  public void saveGroupMember_nullGroup() {
    GroupMembers groupMembers = new GroupMembers();
    groupMembers.setGroup(null);

    int signedInUserID = 1;

    GroupMembers returned = groupMemberService.saveGroupMember(groupMembers, signedInUserID);

    assertNull(returned, "Group member with null group should return null.");
  }

  @Test
  public void saveGroupMember_nullUser() {
    GroupMembers groupMembers = new GroupMembers();
    groupMembers.setGroup(new GroupEntity());

    int signedInUserID = 1;

    GroupMembers returned = groupMemberService.saveGroupMember(groupMembers, signedInUserID);

    assertNull(returned, "Group member with null user should return null.");
  }

  @Test
  public void saveGroupMember_publicGroupActiveStatus() {
    GroupMembers groupMembers = new GroupMembers();
    groupMembers.setUser(new User());
    groupMembers.setGroup(new GroupEntity());
    groupMembers.getGroup().setPrivate(false);

    int signedInUserID = 1;

    when(groupMemberRepository.save(groupMembers)).thenReturn(groupMembers);

    GroupMembers returned = groupMemberService.saveGroupMember(groupMembers, signedInUserID);

    assertTrue(returned.isActive(), "This is a public group, the group member should be active!");
  }

  @Test
  public void removeGroupMembers() {
    GroupMembers groupMembers = new GroupMembers();
    groupMembers.setUser(new User());
    groupMembers.setGroup(new GroupEntity());

    int signedInUserID = 1;

    GroupMembers returned = groupMemberService.removeGroupMember(groupMembers, signedInUserID);

    assertEquals(groupMembers, returned, "Deleted group member not returned.");
  }

}
