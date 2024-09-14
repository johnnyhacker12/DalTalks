import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { List, Button, Typography, Card } from 'antd';
import axios from 'axios';
import Navigation from './navigation.js';
import '../css/friends.css';

const { Title, Text } = Typography;

const FriendRequests = () => {
  const [requests, setRequests] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchFriendRequests();
  }, []);

  const fetchFriendRequests = async () => {
    try {
      const savedUserId = localStorage.getItem('userId');
      if (savedUserId) {
        const response = await axios.get(`http://localhost:8080/api/user/getFriendRequests?userID=${savedUserId}`);
        setRequests(response.data);
      }
    } catch (error) {
      console.error('Error fetching friend requests:', error);
    }
  };

  const handleAcceptRequest = async (requestId) => {
    try {
      await axios.post(`http://localhost:8080/api/user/acceptFriendRequest?requestId=${requestId}`);
      fetchFriendRequests();
    } catch (error) {
      console.error('Error accepting friend request:', error);
    }
  };

  const handleRejectRequest = async (requestId) => {
    try {
      await axios.post(`http://localhost:8080/api/user/rejectFriendRequest?requestId=${requestId}`);
      fetchFriendRequests();
    } catch (error) {
      console.error('Error rejecting friend request:', error);
    }
  };

  const handleProfileNavigation = (senderId) => {
    navigate(`/profile/${senderId}`);
  };

  return (
    <div className="friend-requests-container">
      <Navigation />
      <Title level={2}>Friend Requests</Title>
      <Card className="friend-requests-card">
        {requests.length > 0 ? (
          <List
            itemLayout="horizontal"
            dataSource={requests}
            renderItem={(request) => (
              <List.Item>
                <List.Item.Meta
                  title={
                    <Text className="account-name" onClick={() => handleProfileNavigation(request.senderId)}>
                      {request.senderEmail}
                    </Text>
                  }
                />
                <div className="friend-request-buttons">
                  <Button type="primary" onClick={() => handleAcceptRequest(request.requestId)}>
                    Accept
                  </Button>
                  <Button type="danger" onClick={() => handleRejectRequest(request.requestId)}>
                    Reject
                  </Button>
                </div>
              </List.Item>
            )}
          />
        ) : (
          <Text className="no-requests">No friend requests.</Text>
        )}
      </Card>
    </div>
  );
};

export default FriendRequests;
