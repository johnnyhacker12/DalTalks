import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../css/CreateGroup.css';
import Navigation from './navigation.js';
import { Checkbox } from 'antd';

const CreateGroup = () => {
  const [groupName, setName] = useState('');
  const [isPrivate, setPrivate] = useState(false);
  const [creatorID, setCreatedBy] = useState(localStorage.getItem('userId'));
  const creation_date = new Date();
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    console.log(groupName)
    console.log(isPrivate)
    console.log(creatorID)
    console.log(creation_date)

    event.preventDefault();
    const groupData = { groupName, private: isPrivate, creatorID, creation_date };

    try {
      const response = await axios.post('http://localhost:8080/groups/create-group', groupData);
      alert('Group created successfully!');
      navigate('/groups');
    } catch (error) {
      console.error('Error creating group', error);
      alert('Failed to create group');
    }
  };

  return (
    <div>
      <Navigation />
    <div className="create-group-form">
      <h2>Create a New Group</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Group Name"
          value={groupName}
          onChange={(e) => setName(e.target.value)}
          required
        />
        <div>
        <Checkbox name="privateGroupCheck" checked = {isPrivate} onChange={(e) => setPrivate(!isPrivate)} />
        <label for="privateGroupCheck">Is this a private group?</label>
        </div>
        <button type="submit">Create Group</button>
      </form>
    </div>
    </div>
  );
};

export default CreateGroup;

