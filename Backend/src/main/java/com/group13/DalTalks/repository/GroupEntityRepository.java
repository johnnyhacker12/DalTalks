package com.group13.DalTalks.repository;

import com.group13.DalTalks.model.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupEntityRepository extends JpaRepository<GroupEntity, Integer> {
}
