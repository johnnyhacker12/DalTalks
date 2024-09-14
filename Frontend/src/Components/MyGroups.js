import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Navigation from './navigation.js';
import '../css/MyGroups.css';

const MyGroups = () => {
    const [groups, setGroups] = useState([]);
    const userId = localStorage.getItem('userId');

    useEffect(() => {
        const fetchGroups = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/group_members/user/${userId}`);
                setGroups(response.data.map(groupMember => groupMember.group));
            } catch (error) {
                console.error('Error fetching user groups', error);
            }
        };

        fetchGroups();
    }, [userId]);

    return (
        <div className="my-groups">
            <Navigation />
            <h2>My Groups</h2>
            <ul>
                {groups.length > 0 ? (
                    groups.map(group => (
                        <li key={group.id}>
                            <a href={`/groups/${group.id}`}>{group.groupName}</a>
                        </li>
                    ))
                ) : (
                    <p>You are not a member of any groups.</p>
                )}
            </ul>
        </div>
    );
};

export default MyGroups;