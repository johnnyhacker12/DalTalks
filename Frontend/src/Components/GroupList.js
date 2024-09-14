import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import Navigation from './navigation.js';
import '../css/GroupList.css';

const GroupList = () => {
  const [groups, setGroups] = useState([]);

  useEffect(() => {
    const fetchGroups = async () => {
      try {
        const response = await axios.get('http://localhost:8080/groups/get-all-groups');
        setGroups(response.data);
      } catch (error) {
        console.error('Error fetching groups', error);
      }
    };

    fetchGroups();
  }, []);

  return (
    <div className="group-list">
      <Navigation />
      <h2>Groups</h2>
      <ul>
        {groups.map(group => (
          <li key={group.id}>
            <Link to={`/groups/${group.id}`}>{group.groupName}</Link>
          </li>
        ))}
      </ul>
    </div>
  );

};

export default GroupList;
