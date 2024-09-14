import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Navigation from './navigation.js';
import '../css/GroupDetails.css';

const GroupDetails = () => {
  const { id } = useParams();
  const [group, setGroup] = useState(null);
  const [members, setMembers] = useState([]);
  const [groupCreator, setGroupCreator] = useState(null);
  const signedInUserId = localStorage.getItem('userId');

  useEffect(() => {
    const fetchGroupInfo = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/groups/${id}`);
        setGroup(response.data);
      } catch (error) {
        console.error('Error fetching group info', error);
      }
    };

    const fetchGroupMembers = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/group_members/${id}`);
        setMembers(response.data);
      } catch (error) {
        console.error('Error fetching group members', error);
      }
    };

    fetchGroupInfo();
    fetchGroupMembers();
  }, [id]);

  const fetchGroupCreator = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/getEmailByUserID/${group.creatorID}`);
        setGroupCreator(response.data);
      } catch (error) {
        console.error('Error fetching group creator', error);
      }
    };

    fetchGroupCreator();
  ;

  const handleAddMember = async (userId) => {
      try {
        await axios.post(`http://localhost:8080/group_members/add-membership/${signedInUserId}`, {
          group: group,
          user: { id: userId },
          isActive: true
        });
        alert('Member added successfully');
        const response = await axios.get(`http://localhost:8080/group_members/${id}`);
        setMembers(response.data);
      } catch (error) {
        console.error('Error adding member', error);
        alert('Failed to add member');
      }
    };

  const handleRemoveMember = async (userId) => {
    try {
      await axios.delete(`http://localhost:8080/group_members/remove-membership/${signedInUserId}`, {
        data: {
          group: group,
          user: { id: userId }
        }
      });
      alert('Member removed successfully');
      const response = await axios.get(`http://localhost:8080/group_members/${id}`);
      setMembers(response.data);
    } catch (error) {
      console.error('Error removing member', error);
      alert('Failed to remove member');
    }
  };

  const handleActivateMember = async (userId) => {
      try {
        const response = await axios.post(`http://localhost:8080/group_members/activate-membership/${signedInUserId}`, {
          group: group,
          user: { id: userId }
        });
        alert('Member activated successfully');
        const responseMembers = await axios.get(`http://localhost:8080/group_members/${id}`);
        setMembers(responseMembers.data);
      } catch (error) {
        console.error('Error activating member', error);
        alert('Failed to activate member');
      }
    };

  return (
    <div>
      <Navigation />
    <div className="group-details">
      {group ? (
        <>
          <h2>{group.groupName}</h2>
          <p>Private: {group.private.toString()}</p>
          <p>Created by: {groupCreator}</p>
          <h3>Members</h3>
          <ul>
            {members.map(member => (
              <li key={member.user_id}>
                {member.user.email} - {member.active ? 'Active' : 'Inactive'}
                <button onClick={() => handleRemoveMember(member.user.id)}>Remove</button>
                <button onClick={() => handleActivateMember(member.user.id)}>Make Active</button>
              </li>
            ))}
          </ul>
          <button onClick={() => handleAddMember(prompt('Enter user ID to add'))}>Add Member</button>
        </>
      ) : (
        <p>Loading group details...</p>
      )}
    </div>
    </div>
  );
};

export default GroupDetails;


