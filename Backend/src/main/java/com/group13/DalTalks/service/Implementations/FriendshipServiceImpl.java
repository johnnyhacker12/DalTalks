package com.group13.DalTalks.service.Implementations;

import com.group13.DalTalks.model.Friendship;
import com.group13.DalTalks.model.User;
import com.group13.DalTalks.repository.FriendshipRepository;
import com.group13.DalTalks.repository.UserRepository;
import com.group13.DalTalks.service.FriendshipService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class FriendshipServiceImpl implements FriendshipService {
    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void sendFriendRequest(int senderId, int receiverId) {
        // if findByUser1IdAndUser2Id finds a non-empty entity in the repository, then the
        // users are either already friends or having a pending friend request -> no new request should be made
        if (!friendshipRepository.findByUser1IdAndUser2Id(senderId,receiverId).isEmpty()) {
            return;
        }

        // we need to check in the opposite order to prevent duplicate friend requests
        // -> ie A is friends with B, and B is friends with A.
        if (!friendshipRepository.findByUser1IdAndUser2Id(receiverId,senderId).isEmpty()) {
            return;
        }

        Friendship friendship = new Friendship();
        friendship.setUser1Id(senderId);
        friendship.setUser2Id(receiverId);
        friendship.setAccepted(false);

        friendshipRepository.save(friendship);
    }

    @Override
    @Transactional
    public void acceptFriendRequest(int requestId) {
        Optional<Friendship> optionalFriendship = friendshipRepository.findById(requestId);
        if (optionalFriendship.isPresent()) {
            Friendship friendship = optionalFriendship.get();
            friendship.setAccepted(true);
            friendshipRepository.save(friendship);
        }
    }

    @Override
    @Transactional
    public void rejectFriendRequest(int requestId) {
        friendshipRepository.deleteById(requestId);
    }

    @Override
    @Transactional
    public void removeFriend(int userID1, int userID2) {
        List<Friendship> results = friendshipRepository.findByUser1IdAndUser2Id(userID1, userID2);
        if (!results.isEmpty()) {
            friendshipRepository.deleteById(results.get(0).getId());
        }
    }

    @Override
    public List<Object> getFriendRequests(int userId) {
        List<Object> friendRequests = new ArrayList<>();
        List<Friendship> requests = friendshipRepository.findByUser2IdAndAccepted(userId, false);

        for (Friendship request : requests) {
            int senderId = (request.getUser2Id() == userId) ? request.getUser1Id() : request.getUser2Id();
            Optional<User> optionalUser = userRepository.findById(senderId);
            Properties userProperties = new Properties() {{
                put("senderId", String.valueOf(request.getUser1Id()));
                put("receiverId", String.valueOf(request.getUser2Id()));
                put("requestId", String.valueOf(request.getId()));
                put("senderEmail", String.valueOf(optionalUser.get().getEmail()));
            }};

            friendRequests.add(userProperties);
        }

        return friendRequests;
    }

    public List<User> getFriends(int userId) {  //method for getting all friends
        List<Friendship> friendships = friendshipRepository.findByUser1IdAndAccepted(userId, true);
        List<User> friends = new ArrayList<>();

        for (Friendship friendship : friendships) {
            Optional<User> friendOpt = userRepository.findById(friendship.getUser2Id());
            friendOpt.ifPresent(friends::add);
        }

        friendships = friendshipRepository.findByUser2IdAndAccepted(userId, true);
        for (Friendship friendship : friendships) {
            Optional<User> friendOpt = userRepository.findById(friendship.getUser1Id());
            friendOpt.ifPresent(friends::add);
        }

        return friends;
    }

}


