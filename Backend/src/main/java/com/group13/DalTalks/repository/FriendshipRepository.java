package com.group13.DalTalks.repository;

import com.group13.DalTalks.model.Friendship;
import com.group13.DalTalks.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {
    List<Friendship> findByUser1IdAndUser2Id(int user1Id, int user2Id);

    List<Friendship> findByUser2IdAndAccepted(int user2Id, boolean accepted);

    List<Friendship> findByUser1Id(int user1Id);

    List<Friendship> findByUser1IdAndAccepted(int user1Id, boolean accepted); //for getting all friends

}
