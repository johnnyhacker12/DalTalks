import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import { Card, Spin, Typography, Avatar, Row, Col } from 'antd';
import { UserOutlined, EditOutlined } from '@ant-design/icons';
import Navigation from './navigation.js';
import '../css/ProfilePage.css';
 
const { Title, Paragraph } = Typography;
 
const ProfilePage = () => {
    const { id } = useParams();
    const [profile, setProfile] = useState(null);
    const [email, setEmail] = useState(null);
 
    useEffect(() => {
      const fetchProfile = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/profiles/getByUserID/${id}`);
          const email = await axios.get(`http://localhost:8080/api/getEmailByUserID/${id}`);
          setProfile(response.data);
          setEmail(email.data);
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
                <div style={{ display: 'flex', justifyContent: 'center', marginBottom: '20px' }}>
                    <Avatar size={100} icon={<UserOutlined />} />
                </div>
                <Title level={1} className="profile-title">{email}'s Profile</Title>
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
 
export default ProfilePage;