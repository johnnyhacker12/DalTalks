package com.group13.DalTalks.service.Implementations;

import com.group13.DalTalks.model.GroupEntity;
import com.group13.DalTalks.model.GroupMembers;
import com.group13.DalTalks.model.User;
import com.group13.DalTalks.repository.GroupMemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GroupMemberServiceImplActivateTest {
    @Mock
    private GroupMemberRepository groupMemberRepository;

    @InjectMocks
    private GroupMemberServiceImpl groupMemberService;

    @Test
    public void activateGroupMember() {
        GroupEntity group = new GroupEntity();
        group.setId(1);
        User user = new User();
        user.setId(1);

        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setGroup(group);
        groupMembers.setUser(user);

        GroupMembers existingGroupMember = new GroupMembers();
        existingGroupMember.setGroup(group);
        existingGroupMember.setUser(user);

        int signedInUserID = 1;

        when(groupMemberRepository.findByGroupIdAndUserId(group.getId(), user.getId())).thenReturn(existingGroupMember);

        GroupMembers returned = groupMemberService.activateGroupMember(groupMembers, signedInUserID);

        assertTrue(returned.isActive(), "Group member should be activated.");
    }

    @Test
    public void activateGroupMember_nonExistingMember() {
        GroupEntity group = new GroupEntity();
        group.setId(1);
        User user = new User();
        user.setId(1);

        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setGroup(group);
        groupMembers.setUser(user);

        int signedInUserID = 1;

        when(groupMemberRepository.findByGroupIdAndUserId(group.getId(), user.getId())).thenReturn(null);

        GroupMembers returned = groupMemberService.activateGroupMember(groupMembers, signedInUserID);

        assertNull(returned, "Group member that doesn't exist returns null.");
    }

    @Test
    public void activateGroupMember_nullMember() {
        int signedInUserID = 1;
        GroupMembers groupMembers = null;

        GroupMembers returned = groupMemberService.activateGroupMember(groupMembers, signedInUserID);

        assertNull(returned, "Group member should be null if no existing member is found.");
    }
}
