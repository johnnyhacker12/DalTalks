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
public class GroupMemberServiceImplGetTest {
    @Mock
    private GroupMemberRepository groupMemberRepository;

    @InjectMocks
    private GroupMemberServiceImpl groupMemberService;

    @Test
    public void getMembersFromGroup_oneMember() {
        GroupMembers member1 = new GroupMembers();
        GroupEntity group = new GroupEntity();
        group.setId(1);
        member1.setActive(true);
        member1.setGroup(group);

        List<GroupMembers> allMembers = new ArrayList<>();
        allMembers.add(member1);

        when(groupMemberRepository.findByGroupId(group.getId())).thenReturn(allMembers);

        List<GroupMembers> returned = groupMemberService.findAllGroupMembersByGroupId(group.getId());

        assertEquals(allMembers.size(), returned.size(), "Group members not returned correctly.");
    }


    @Test
    public void getMembersFromGroup_twoMembers() {
        GroupEntity group = new GroupEntity();
        group.setId(1);


        GroupMembers member1 = new GroupMembers();
        member1.setActive(true);
        member1.setGroup(group);
        GroupMembers member2 = new GroupMembers();
        member2.setActive(true);
        member2.setGroup(group);

        List<GroupMembers> allMembers = new ArrayList<>();
        allMembers.add(member1);
        allMembers.add(member2);

        when(groupMemberRepository.findByGroupId(group.getId())).thenReturn(allMembers);

        List<GroupMembers> returned = groupMemberService.findAllGroupMembersByGroupId(group.getId());

        assertEquals(allMembers.size(), returned.size(), "Group members not returned correctly.");
    }

    @Test
    public void getMembersFromGroup_noMembers() {
        GroupEntity group = new GroupEntity();
        group.setId(1);

        List<GroupMembers> allMembers = new ArrayList<>();

        when(groupMemberRepository.findByGroupId(group.getId())).thenReturn(allMembers);

        List<GroupMembers> returned = groupMemberService.findAllGroupMembersByGroupId(group.getId());

        assertTrue(allMembers.isEmpty(), "No members should have been returned.");
    }

    @Test
    public void getGroupsByUserId_userHasGroups() {
        int userId = 1;
        User user = new User();
        user.setId(userId);

        GroupEntity group1 = new GroupEntity();
        group1.setId(1);
        GroupEntity group2 = new GroupEntity();
        group2.setId(2);

        GroupMembers member1 = new GroupMembers();
        member1.setUser(user);
        member1.setGroup(group1);

        GroupMembers member2 = new GroupMembers();
        member2.setUser(user);
        member2.setGroup(group2);

        List<GroupMembers> groupMembersList = new ArrayList<>();
        groupMembersList.add(member1);
        groupMembersList.add(member2);

        when(groupMemberRepository.findByUserId(userId)).thenReturn(groupMembersList);

        List<GroupMembers> returnedGroups = groupMemberService.findGroupsByUserId(userId);

        assertEquals(2, returnedGroups.size(), "User should have 2 groups.");
        assertTrue(returnedGroups.stream().anyMatch(gm -> gm.getGroup().getId() == 1), "Returned groups should contain group1.");
        assertTrue(returnedGroups.stream().anyMatch(gm -> gm.getGroup().getId() == 2), "Returned groups should contain group2.");
    }

    @Test
    public void getGroupsByUserId_userHasNoGroups() {
        int userId = 1;
        when(groupMemberRepository.findByUserId(userId)).thenReturn(new ArrayList<>());
        List<GroupMembers> returnedGroups = groupMemberService.findGroupsByUserId(userId);
        assertTrue(returnedGroups.isEmpty(), "User should have no groups.");
    }

    @Test
    public void getGroupsByUserId_userIdNotFound() {
        int userId = 999;
        when(groupMemberRepository.findByUserId(userId)).thenReturn(new ArrayList<>());
        List<GroupMembers> returnedGroups = groupMemberService.findGroupsByUserId(userId);
        assertTrue(returnedGroups.isEmpty(), "Non-existent user ID should return an empty list.");
    }

    @Test
    public void getGroupsByUserId_nullUserId() {
        List<GroupMembers> returnedGroups = groupMemberService.findGroupsByUserId(0);
        assertTrue(returnedGroups.isEmpty(), "Null user ID should return an empty list.");
    }
}
