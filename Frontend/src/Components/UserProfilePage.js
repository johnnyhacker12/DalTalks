import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import { Card, Spin, Typography, Avatar, Row, Col } from 'antd';
import { UserOutlined, EditOutlined } from '@ant-design/icons';
import Navigation from './navigation.js';
import '../css/ProfilePage.css';
 
const { Title, Paragraph } = Typography;
 
const UserProfilePage = () => {
    var id = localStorage.getItem('userId');
    const [profile, setProfile] = useState(null);
    const [loading, setLoading] = useState(true);
 
    useEffect(() => {
      const fetchProfile = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/profiles/getByUserID/${id}`)
          console.log(response);
          setProfile(response.data);
        } catch (error) {
          console.error('Error fetching profile', error);
        }
      };
        fetchProfile();
    }, [id]);
   
 
    return (
        <div className="profile-container">
            <Navigation />
            <Card className="profile-card">
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
                    <div>
                        <Link to={`/editprofile/`}>
                            <EditOutlined style={{ fontSize: '24px', color: '#1890ff' }} />
                        </Link>
                    </div>
                </div>
                <div style={{ display: 'flex', justifyContent: 'center', marginBottom: '20px' }}>
                    <Avatar size={100} icon={<UserOutlined />} />
                </div>
                <Title level={1} className="profile-title">My Profile</Title>
                {profile ? (
                    <div className="profile-details">
                        <Row gutter={[16, 16]}>
                            <Col span={24}>
                                <Paragraph className="profile-text">
                                    <strong>Status:</strong> {profile.status}
                                </Paragraph>
                            </Col>
                            <Col span={24}>
                                <Paragraph className="profile-text">
                                    <strong>Interests:</strong> {profile.interests}
                                </Paragraph>
                            </Col>
                            <Col span={24}>
                                <Paragraph className="profile-text">
                                    <strong>Major:</strong> {profile.major}
                                </Paragraph>
                            </Col>
                            <Col span={24}>
                                <Paragraph className="profile-text">
                                    <strong>Location:</strong> {profile.location}
                                </Paragraph>
                            </Col>
                            <Col span={24}>
                                <Paragraph className="profile-text">
                                    <strong>Birthday:</strong> {profile.birthday}
                                </Paragraph>
                            </Col>
                        </Row>
                    </div>
                ) : (
                    <Paragraph>Profile not found.</Paragraph>
                )}
            </Card>
        </div>
    )
};
 
export default UserProfilePage;