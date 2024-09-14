import React, { useEffect, useState } from 'react';
import { Avatar, Space, Card, Button, Typography, Spin } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../css/friends.css';

const { Text } = Typography;

const Friend = () => {
  const [User, setUser] = useState(null);
  const [message, setMessage] = useState('');
  const navigate = useNavigate();
  const currentID = localStorage.getItem('userId');

  useEffect(() => {
    fetchUser();
  }, [currentID]);

  const fetchUser = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/user/getAllUserExceptCurrent/${currentID}`);
      setUser(response.data);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  const handleRemoveFriend = async (friendEmail) => {
    try {
      const response = await axios.post(`http://localhost:8080/api/user/removeFriend?userID1=${friendEmail}&userID2=${currentID}`);
      setMessage(response.data);
    } catch (error) {
      setMessage(error.response.data.message);
    }

    try {
      const response = await axios.post(`http://localhost:8080/api/user/removeFriend?userID1=${currentID}&userID2=${friendEmail}`);
      setMessage(response.data);
    } catch (error) {
      setMessage(error.response.data.message);
    }
  };

  const handleAddFriend = async (friendEmail) => {
    try {
      const response = await axios.post(`http://localhost:8080/api/user/sendFriendRequest?senderId=${currentID}&receiverId=${friendEmail}`);
      setMessage(response.data);
      if (response.status === 200) {
        navigate('/friend-requests');
      }
    } catch (error) {
      setMessage(error.response?.data?.message || 'Error sending friend request');
    }
  };

  return (
    <div className="friend-container">
      {User ? (
        User.map((user) => (
          <div className="user-profile" key={user.id}>
            <Card className="user-card">
              <Space align="center">
                <Avatar size={48} icon={<UserOutlined />} />
                <div className="user-info">
                  <Text className="account-name" onClick={() => navigate(`/profile/${user.id}`)}>
                    <a>{user.email.split('@')[0]}</a>
                  </Text>
                  <div className="button-group">
                    <Button type="primary" className="follow-button" onClick={() => handleAddFriend(user.id)}>Follow</Button>
                    <Button type="primary" danger onClick={() => handleRemoveFriend(user.id)}>Remove</Button>
                  </div>
                </div>
              </Space>
            </Card>
          </div>
        ))
      ) : (
        <div className="loading-container">
          <Spin size="large" />
        </div>
      )}
      {message && <Text type="danger">{message}</Text>}
    </div>
  );
};

export default Friend;
