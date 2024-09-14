package com.group13.DalTalks.service;
import com.group13.DalTalks.model.Friendship;
import com.group13.DalTalks.model.User;
import java.util.List;

public interface FriendshipService {
    void sendFriendRequest(int senderId, int receiverId);
    void acceptFriendRequest(int requestId);
    void rejectFriendRequest(int requestId);
    List<Object> getFriendRequests(int userId);
    void removeFriend(int userID1, int userID2);

    List<User> getFriends(int userId);

}
