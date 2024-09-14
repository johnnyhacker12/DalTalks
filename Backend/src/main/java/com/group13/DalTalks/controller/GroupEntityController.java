package com.group13.DalTalks.controller;

import com.group13.DalTalks.model.GroupEntity;
import com.group13.DalTalks.service.GroupEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/groups")
public class GroupEntityController {
  @Autowired
  private GroupEntityService groupService;

  @PostMapping("/create-group")
  public GroupEntity createGroup(@RequestBody GroupEntity group) {
    return groupService.createGroup(group);
  }

  @GetMapping("/get-all-groups")
  public List<GroupEntity> getAllGroups() {
    return groupService.getAllGroups();
  }

  @GetMapping("/{id}")
  public GroupEntity getGroupById(@PathVariable int id) {
    return groupService.getGroupById(id);
  }
}
