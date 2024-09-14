package com.group13.DalTalks;

import com.group13.DalTalks.model.Friendship;
import com.group13.DalTalks.model.User;
import com.group13.DalTalks.repository.FriendshipRepository;
import com.group13.DalTalks.repository.UserRepository;
import com.group13.DalTalks.service.Implementations.FriendshipServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
class FriendshipServiceImplTest {

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FriendshipServiceImpl friendshipService;

    private User user1;
    private User user2;
    private Friendship friendship;

    private final int userID_1 = 1;

    private final int userID_2 = 2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new User();
        user1.setId(userID_1);
        user1.setEmail("user1@example.com");

        user2 = new User();
        user2.setId(userID_2);
        user2.setEmail("user2@example.com");

        friendship = new Friendship();
        friendship.setId(1);
        friendship.setUser1Id(user1.getId());
        friendship.setUser2Id(user2.getId());
        friendship.setAccepted(false);
    }

    @Test
    void testSendFriendRequest_NewRequest() {
        when(friendshipRepository.findByUser1IdAndUser2Id(user1.getId(), user2.getId())).thenReturn(new ArrayList<>());
        when(friendshipRepository.findByUser1IdAndUser2Id(user2.getId(), user1.getId())).thenReturn(new ArrayList<>());

        friendshipService.sendFriendRequest(user1.getId(), user2.getId());

        verify(friendshipRepository, times(1)).save(any(Friendship.class));
    }

    @Test
    void testSendFriendRequest_AlreadyFriends() {
        when(friendshipRepository.findByUser1IdAndUser2Id(user1.getId(), user2.getId())).thenReturn(List.of(friendship));

        friendshipService.sendFriendRequest(user1.getId(), user2.getId());

        verify(friendshipRepository, times(0)).save(any(Friendship.class));
    }

    @Test
    void testSendFriendRequest_ExistingRequest() {
        when(friendshipRepository.findByUser1IdAndUser2Id(user1.getId(), user2.getId())).thenReturn(new ArrayList<>());
        when(friendshipRepository.findByUser1IdAndUser2Id(user2.getId(), user1.getId())).thenReturn(List.of(friendship));

        friendshipService.sendFriendRequest(user1.getId(), user2.getId());

        verify(friendshipRepository, times(0)).save(any(Friendship.class));
    }

    @Test
    void testAcceptFriendRequest() {
        when(friendshipRepository.findById(friendship.getId())).thenReturn(Optional.of(friendship));

        friendshipService.acceptFriendRequest(friendship.getId());

        verify(friendshipRepository, times(1)).save(friendship);
        assertTrue(friendship.isAccepted());
    }

    @Test
    void testAcceptFriendRequest_NotFound() {
        when(friendshipRepository.findById(friendship.getId())).thenReturn(Optional.empty());

        friendshipService.acceptFriendRequest(friendship.getId());

        verify(friendshipRepository, times(0)).save(any(Friendship.class));
    }

    @Test
    void testRejectFriendRequest() {
        when(friendshipRepository.findById(friendship.getId())).thenReturn(Optional.of(friendship));

        friendshipService.rejectFriendRequest(friendship.getId());

        verify(friendshipRepository, times(1)).deleteById(friendship.getId());
    }

    @Test
    void testRemoveFriend() {
        when(friendshipRepository.findByUser1IdAndUser2Id(user1.getId(), user2.getId())).thenReturn(List.of(friendship));

        friendshipService.removeFriend(user1.getId(), user2.getId());

        verify(friendshipRepository, times(1)).deleteById(friendship.getId());
    }

    @Test
    void testGetFriendRequests() {
        List<Friendship> requests = List.of(friendship);
        when(friendshipRepository.findByUser2IdAndAccepted(user2.getId(), false)).thenReturn(requests);
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));

        List<Object> friendRequests = friendshipService.getFriendRequests(user2.getId());

        assertEquals(1, friendRequests.size());
        Properties userProperties = (Properties) friendRequests.get(0);
        assertEquals("1", userProperties.getProperty("senderId"));
        assertEquals("2", userProperties.getProperty("receiverId"));
        assertEquals("1", userProperties.getProperty("requestId"));
        assertEquals("user1@example.com", userProperties.getProperty("senderEmail"));
    }

    @Test
    void testGetFriends() {
        List<Friendship> friendships1 = List.of(friendship);
        when(friendshipRepository.findByUser1IdAndAccepted(user1.getId(), true)).thenReturn(friendships1);
        when(friendshipRepository.findByUser2IdAndAccepted(user1.getId(), true)).thenReturn(new ArrayList<>());
        when(userRepository.findById(user2.getId())).thenReturn(Optional.of(user2));

        List<User> friends = friendshipService.getFriends(user1.getId());

        assertEquals(1, friends.size());
        assertEquals(user2, friends.get(0));
    }

    @Test
    void testGetFriends_NoFriends() {
        when(friendshipRepository.findByUser1IdAndAccepted(user1.getId(), true)).thenReturn(new ArrayList<>());
        when(friendshipRepository.findByUser2IdAndAccepted(user1.getId(), true)).thenReturn(new ArrayList<>());

        List<User> friends = friendshipService.getFriends(user1.getId());

        assertTrue(friends.isEmpty());
    }

    @Test
    void testGetFriends_UserNotFound() {
        List<Friendship> friendships = List.of(friendship);
        when(friendshipRepository.findByUser1IdAndAccepted(user1.getId(), true)).thenReturn(friendships);
        when(friendshipRepository.findByUser2IdAndAccepted(user1.getId(), true)).thenReturn(new ArrayList<>());
        when(userRepository.findById(user2.getId())).thenReturn(Optional.empty());

        List<User> friends = friendshipService.getFriends(user1.getId());

        assertTrue(friends.isEmpty());
    }
}