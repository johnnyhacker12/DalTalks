import React from 'react';
import { Button } from 'antd';
import { HomeOutlined, UserOutlined, LogoutOutlined, UserAddOutlined, TeamOutlined, PlusOutlined, GroupOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';

const Navigation = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.setItem('loggedIn', 'false');
    localStorage.removeItem('userId');
    navigate('/login');
  };

  return (
    <div style={{ cursor: 'default' }}>
      <h1>DalTalks</h1>
      <Button type="text">
        <a href="/main">
          <HomeOutlined /> Home
        </a>
      </Button>
      <Button type="text" onClick={() => navigate(`/profile`)}>
        <UserOutlined /> User
      </Button>
      <Button type="text" onClick={() => navigate(`/friend-requests`)}>
        <UserAddOutlined /> Friend Requests
      </Button>
      <Button type="text" onClick={() => navigate(`/my-friends`)}>
        <TeamOutlined /> Friends
      </Button>
      <Button type="text" onClick={() => navigate(`/create-group`)}>
        <PlusOutlined /> Create Group
      </Button>
      <Button type="text" onClick={() => navigate(`/groups`)}>
        <GroupOutlined /> All Groups
      </Button>
      <Button type="text" onClick={() => navigate(`/my-groups`)}>
        <GroupOutlined /> My Groups
      </Button>
      <Button type="text" onClick={handleLogout}>
        <LogoutOutlined /> Log out
      </Button>
    </div>
  );
};

export default Navigation;
