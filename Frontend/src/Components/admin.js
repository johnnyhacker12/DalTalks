import React, { useEffect, useState } from 'react';
import { Col, Flex, Row } from 'antd';
import { Avatar, Card, Spin } from 'antd';
import { Button, Form, Input } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { Typography } from 'antd';
import axios from 'axios';
import Navigation from './navigation';

const Admin = () => {
    const { Link } = Typography;
    const [pendingUsers, setPendingUsers] = useState([]);
    const [users, setUsers] = useState([]);
    const value = 'vertical';
    const navigate = useNavigate();

    const onFinish = (values) => {
        fixRole(values);
        console.log('Success:', values.userID);
        alert("User's role set successfully");
    };
    const onFinish2 = (values) => {
        fixRole2(values);
        console.log('Success:', values.userID);
        alert("User's role set successfully");
    };
      
    const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    const fixRole = async (values) => {
        await axios.put(`http://localhost:8080/api/user/fixRole/${values.userID}`)
    }
    const fixRole2 = async (values) => {
        await axios.put(`http://localhost:8080/api/user/fixRole2/${values.userID}`)
    }

    const fetchPendingUser = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/user/getAllPendingUser');
            const fetchedPendingUser = response.data;
            setPendingUsers(fetchedPendingUser);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

    const fetchUser = async () => {
        try{
            const response1 = await axios.get('http://localhost:8080/api/user/getAllApprovedUser');
            const fetchedUser = response1.data;
            setUsers(fetchedUser);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }

    useEffect(() => {
        fetchPendingUser();
        fetchUser();
    }, []);

    const removeUser = async (userID) => {
        try {
            await axios.delete(`http://localhost:8080/api/user/deleteUser/${userID}`);
            fetchUser();
            fetchPendingUser();
        } catch (error) {
            alert(error);
        }
    };
  
      const acceptUser = async (userID) => {
        try {
            await axios.put(`http://localhost:8080/api/user/acceptUser/${userID}`);
            fetchPendingUser();
            fetchUser();
        } catch (error) {
            alert(error);
        }
    };

    return (
        <div>
            <Navigation />
            <div>
                <Row justify="center" align="top">
                    <Col span={8}>
                        <Flex vertical={value === 'vertical'} align='center'>
                            <div>
                                <h1>Administrator</h1>
                            </div>
                            <Form
                                    name="basic"
                                    labelCol={{ span: 8 }}
                                    wrapperCol={{ span: 16 }}
                                    style={{ maxWidth: 600 }}
                                    initialValues={{ remember: true }}
                                    onFinish={onFinish}
                                    onFinishFailed={onFinishFailed}
                                    autoComplete="off"
                                >
                                    <Form.Item
                                    label="userID"
                                    name="userID"
                                    rules={[{ required: true, message: 'No empty field!' }]}
                                    >
                                    <Input />
                                    </Form.Item>

                                    <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                                    <Button type="primary" htmlType="submit">
                                        Submit
                                    </Button>
                                    </Form.Item>
                                </Form>

                            <div>
                                <h1>User</h1>
                            </div>
                                <Form
                                    name="basic"
                                    labelCol={{ span: 8 }}
                                    wrapperCol={{ span: 16 }}
                                    style={{ maxWidth: 600 }}
                                    initialValues={{ remember: true }}
                                    onFinish={onFinish2}
                                    onFinishFailed={onFinishFailed}
                                    autoComplete="off"
                                >
                                    <Form.Item
                                    label="userID"
                                    name="userID"
                                    rules={[{ required: true, message: 'No empty field!' }]}
                                    >
                                    <Input />
                                    </Form.Item>

                                    <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                                    <Button type="primary" htmlType="submit">
                                        Submit
                                    </Button>
                                    </Form.Item>
                                </Form>

                        </Flex>
                    </Col>
                    <Col span={8}>
                        <Flex vertical={value === 'vertical'} align='center'>
                            <div>
                                <h1>Pending</h1>
                            </div>
                            <div>
                                {pendingUsers ? (
                                    pendingUsers.map((user, index) => (
                                            <div class="user-profile">
                                            <Card style={{ width: 300 }}>
                                            <Avatar size={48} icon={<UserOutlined />} />
                                                <div class="user-info">
                                                <Link onClick={() => navigate(`/profile/${user.id}`)} target="_blank">
                                                    {user.email.split('@')[0]}
                                                </Link>
                                                <Button type="primary" className="follow" onClick={() => acceptUser(user.id)}>Accept</Button>
                                                <Button type="primary" danger onClick={() => removeUser(user.id)}>Reject</Button>
                                                </div>
                                            </Card> 
                                            </div>
                                    ))
                                    ) : (
                                    <div className="loading-container">
                                        <Spin size="large" />
                                    </div>
                                )}
                            </div>
                        </Flex>
                    </Col>
                    <Col span={8}>
                        <div>
                            <Flex vertical={value === 'vertical'} align='center'>
                                <div>
                                    <h1>Current</h1>
                                    
                                </div>
                                {users ? (
                                    users.map((user, index) => (
                                        <div class="user-profile">
                                            <Card style={{ width: 300 }}>
                                                <Avatar size={48} icon={<UserOutlined />} />
                                                <div class="user-info">
                                                <p class="account-name" onClick={() => navigate(`/profile/${user.id}`)}>
                                                    <a>{user.email.split('@')[0]}</a>
                                                </p>
                                                    <Button type="primary" danger onClick={() => removeUser(user.id)}>Remove</Button>
                                                </div>                                      
                                            </Card> 
                                        </div>             
                                    ))
                                    ) : (
                                    <div className="loading-container">
                                        <Spin size="large" />
                                    </div>
                                )}
                            </Flex>
                        </div>
                    </Col>
                </Row>
            </div>
        </div>
    );
};

export default Admin;