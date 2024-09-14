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
public class GroupMemberServiceImplCreateTest {
    @Mock
    private GroupMemberRepository groupMemberRepository;

    @InjectMocks
    private GroupMemberServiceImpl groupMemberService;

    @Test
    public void createNewGroupMembers() {
        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setUser(new User());
        groupMembers.setGroup(new GroupEntity());

        int signedInUserID = 1;

        when(groupMemberRepository.save(groupMembers)).thenReturn(groupMembers);

        GroupMembers returned = groupMemberService.saveGroupMember(groupMembers, signedInUserID);

        assertEquals(groupMembers, returned, "Membership was not returned correctly.");
    }

    @Test
    public void createGroupMember_privateGroup_GroupCreatorAdding() {
        GroupEntity group = new GroupEntity();
        group.setId(1);
        group.setPrivate(true);
        group.setCreatorID(1);
        User user = new User();
        user.setId(1);

        int signedInUserID = 1;

        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setGroup(group);
        groupMembers.setUser(user);

        when(groupMemberRepository.save(groupMembers)).thenReturn(groupMembers);

        try {
            GroupMembers returned = groupMemberService.saveGroupMember(groupMembers, signedInUserID);
            assertEquals(groupMembers, returned);
        } catch (RuntimeException e) {
            //no error should be passed -> the creator can add anyone to the group
            fail();
        }
    }

    @Test
    public void createGroupMember_privateGroup_GroupCreatorNotAdding() {
        GroupEntity group = new GroupEntity();
        group.setId(1);
        group.setPrivate(true);
        group.setCreatorID(1);
        User user = new User();
        user.setId(1);

        int signedInUserID = 5;

        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setGroup(group);
        groupMembers.setUser(user);

        try {
            GroupMembers returned = groupMemberService.saveGroupMember(groupMembers, signedInUserID);
            //this test should fail here, as an error must be thrown if the group creator is not the one adding to a private group
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void createGroupMember_privateGroup_GroupCreatorRemoving() {
        GroupEntity group = new GroupEntity();
        group.setId(1);
        group.setPrivate(true);
        group.setCreatorID(1);
        User user = new User();
        user.setId(1);

        int signedInUserID = 1;

        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setGroup(group);
        groupMembers.setUser(user);


        try {
            GroupMembers returned = groupMemberService.removeGroupMember(groupMembers, signedInUserID);
            assertEquals(groupMembers, returned);
        } catch (RuntimeException e) {
            //no error should be passed -> the creator can remove anyone to the group
            fail();
        }
    }


    @Test
    public void createGroupMember_privateGroup_NotGroupCreatorRemoving() {
        GroupEntity group = new GroupEntity();
        group.setId(1);
        group.setPrivate(true);
        group.setCreatorID(1);
        User user = new User();
        user.setId(1);

        int signedInUserID = 10;

        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setGroup(group);
        groupMembers.setUser(user);


        try {
            GroupMembers returned = groupMemberService.removeGroupMember(groupMembers, signedInUserID);
            //an exception should be thrown if it's not the group creator removing someone
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void createGroupMember_privateGroup_GroupCreatorActivating() {
        GroupEntity group = new GroupEntity();
        group.setId(1);
        group.setPrivate(true);
        group.setCreatorID(1);
        User user = new User();
        user.setId(1);

        int signedInUserID = 1;

        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setGroup(group);
        groupMembers.setUser(user);

        GroupMembers existingGroupMember = new GroupMembers();
        existingGroupMember.setGroup(group);
        existingGroupMember.setUser(user);

        when(groupMemberRepository.findByGroupIdAndUserId(group.getId(), user.getId())).thenReturn(existingGroupMember);

        try {
            GroupMembers returned = groupMemberService.activateGroupMember(groupMembers, signedInUserID);
            assertTrue(returned.isActive(), "User was not made active!");
        } catch (RuntimeException e) {
            //no error should be passed -> the creator can activate anyone in the group
            fail();
        }
    }

    @Test
    public void createGroupMember_privateGroup_NotGroupCreatorActivating() {
        GroupEntity group = new GroupEntity();
        group.setId(1);
        group.setPrivate(true);
        group.setCreatorID(1);
        User user = new User();
        user.setId(1);

        int signedInUserID = 10;

        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setGroup(group);
        groupMembers.setUser(user);

        GroupMembers existingGroupMember = new GroupMembers();
        existingGroupMember.setGroup(group);
        existingGroupMember.setUser(user);

        try {
            GroupMembers returned = groupMemberService.activateGroupMember(groupMembers, signedInUserID);
            //this should fail -> only the creator should be able to activate the group member
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }
    }
}
