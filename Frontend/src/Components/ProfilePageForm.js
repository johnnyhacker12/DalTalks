import React, { useState } from 'react';
import axios from 'axios';
import { Button, Input, Form } from 'antd';
import Navigation from './navigation.js';

const ProfilePageForm = () => {
    const id = localStorage.getItem('userId');

    const [interests, setInterests] = useState('');
    const [status, setStatus] = useState('');
    const [birthday, setBirthday] = useState('');
    const [major, setMajor] = useState('');
    const [location, setLocation] = useState('');

    const handleSubmit = async () => {
        const formData = { interests, status, birthday, major, location };

        try {
            const response = await axios.post(`http://localhost:8080/profiles/update/${id}`, formData);
            console.log(response.data);
            alert('Profile was saved!');
        } catch (error) {
            console.error(error);
            alert('An error occurred. Please try again later');
        }
    };

    return (
        <div style={{ padding: '20px', maxWidth: '600px', margin: 'auto' }}>
            <Navigation />
            <div style={{ backgroundColor: '#fff', borderRadius: '10px', boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)', padding: '20px' }}>
                <h2 style={{ textAlign: 'center', marginBottom: '20px' }}>My Profile Details</h2>
                <Form layout="vertical" onFinish={handleSubmit}>
                    <Form.Item label="Interests">
                        <Input.TextArea
                            value={interests}
                            onChange={(e) => setInterests(e.target.value)}
                            placeholder="Enter Interests"
                            rows={4}
                        />
                    </Form.Item>
                    <Form.Item label="Status">
                        <Input
                            value={status}
                            onChange={(e) => setStatus(e.target.value)}
                            placeholder="Enter Status"
                        />
                    </Form.Item>
                    <Form.Item label="Birthday">
                        <Input
                            value={birthday}
                            onChange={(e) => setBirthday(e.target.value)}
                            placeholder="Enter Birthday"
                        />
                    </Form.Item>
                    <Form.Item label="Major">
                        <Input
                            value={major}
                            onChange={(e) => setMajor(e.target.value)}
                            placeholder="Enter Major"
                        />
                    </Form.Item>
                    <Form.Item label="Location">
                        <Input
                            value={location}
                            onChange={(e) => setLocation(e.target.value)}
                            placeholder="Enter Location"
                        />
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit" block>
                            Submit Changes
                        </Button>
                    </Form.Item>
                </Form>
            </div>
        </div>
    );
};

export default ProfilePageForm;
