import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Button, List, Avatar, Typography, Card, Spin } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import Navigation from './navigation.js';
import { useNavigate } from 'react-router-dom';
import '../css/friends.css';

const { Title, Text } = Typography;

const FriendsList = () => {
  const [friends, setFriends] = useState([]);
  const userId = localStorage.getItem('userId');
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchFriends = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/user/friends/${userId}`);
        setFriends(response.data);
      } catch (error) {
        console.error('Error fetching friends', error);
      }
    };

    fetchFriends();
  }, [userId]);

  const handleRemoveFriend = async (friendEmail) => {
    try {
      await axios.post(`http://localhost:8080/api/user/removeFriend?userID1=${friendEmail}&userID2=${userId}`);
      await axios.post(`http://localhost:8080/api/user/removeFriend?userID1=${userId}&userID2=${friendEmail}`);
      setMessage('Friend removed successfully.');
    } catch (error) {
      setMessage(error.response?.data?.message || 'Error removing friend');
    }
  };

  return (
    <div className="friend-list-container">
      <Navigation />
      <Title level={2}>Friends List</Title>
      <Card className="friends-list-card">
        {friends.length > 0 ? (
          <List
            itemLayout="horizontal"
            dataSource={friends}
            renderItem={friend => (
              <List.Item>
                <List.Item.Meta
                  avatar={<Avatar icon={<UserOutlined />} />}
                  title={
                    <Text className="account-name" onClick={() => navigate(`/profile/${friend.id}`)}>
                    <a>{friend.email.split('@')[0]}</a>
                  </Text>
                  }
                />
                <Button type="primary" danger onClick={() => handleRemoveFriend(friend.id)}>Remove</Button>
              </List.Item>
            )}
          />
        ) : (
          <div className="loading-container">
            <Spin size="large" />
          </div>
        )}
      </Card>
      {message && <Text type="danger">{message}</Text>}
    </div>
  );
};

export default FriendsList;
