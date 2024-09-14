import React, { useEffect, useState, useCallback } from 'react';
import { Col, Row, Card, Avatar, Input, Space, Spin, Button, AutoComplete, Select } from 'antd';
import { UserOutlined, LikeOutlined, CommentOutlined, SendOutlined, TeamOutlined } from '@ant-design/icons';
import Friend from './friends';
import axios from 'axios';
import Navigation from './navigation';
import '../css/post.css';
import { useNavigate } from 'react-router-dom';
import debounce from 'lodash/debounce'; // Import lodash debounce

const { Option } = Select;

const Post = () => {
    const [posts, setPosts] = useState([]);
    const [friends, setFriends] = useState([]);
    const [groups, setGroups] = useState([]);
    const [loading, setLoading] = useState(true);
    const [searchQuery, setSearchQuery] = useState('');
    const [searchResults, setSearchResults] = useState([]);
    const [filter, setFilter] = useState('people');
    const navigate = useNavigate();

    const currentID = localStorage.getItem('userId');

    // Fetch posts, friends, and groups
    useEffect(() => {
        const fetchPostsFriendsAndGroups = async () => {
            try {
                const postResponse = await axios.get('http://localhost:8080/api/getAllPost');
                setPosts(postResponse.data);

                const friendResponse = await axios.get('http://localhost:8080/api/user/getAllUser');
                setFriends(friendResponse.data);

                const groupResponse = await axios.get('http://localhost:8080/groups/get-all-groups');
                setGroups(groupResponse.data);

                setLoading(false);
            } catch (error) {
                console.error('Error fetching data:', error);
                setLoading(false);
            }
        };

        fetchPostsFriendsAndGroups();
    }, [currentID]);

    // Debounced search handler
    const handleSearch = useCallback(
        debounce((query) => {
            if (query) {
                let results = [];
                if (filter === 'people') {
                    results = friends
                        .filter(({ email }) =>
                            email && email.split('@')[0].toLowerCase().includes(query.toLowerCase())
                        )
                        .map(({ email, id }) => ({
                            type: 'friend',
                            value: email.split('@')[0],
                            label: (
                                <div>
                                    <UserOutlined /> {email.split('@')[0]}
                                </div>
                            ),
                            id: id,
                        }));
                } else if (filter === 'groups') {
                    results = groups
                        .filter(({ groupName }) =>
                            groupName && groupName.toLowerCase().includes(query.toLowerCase())
                        )
                        .map(({ groupName, id }) => ({
                            type: 'group',
                            value: groupName,
                            label: (
                                <div>
                                    <TeamOutlined /> {groupName}
                                </div>
                            ),
                            id: id,
                        }));
                }
                setSearchResults(results);
            } else {
                setSearchResults([]);
            }
        }, 300), // Debounce time (in milliseconds)
        [friends, groups, filter]
    );

    // Handle search input change
    const handleSearchInputChange = (e) => {
        const query = e.target.value;
        setSearchQuery(query);
        handleSearch(query);
    };

    // Handle selection of a search result
    const handleSelect = (value, option) => {
        if (option.type === 'friend') {
            navigate(`/profile/${option.id}`);
        } else if (option.type === 'group') {
            navigate(`/groups/${option.id}`);
        }
    };

    return (
        <div className="main">
            <Row justify="center" align="top">
                <Col span={4}>
                    <Navigation />
                </Col>
                <Col span={16} className="navigation-col">
                    <div className="filter-container">
                        <Select
                            defaultValue="people"
                            onChange={(value) => {
                                setFilter(value);
                                handleSearch(searchQuery); // Refetch results when filter changes
                            }}
                            className="filter-dropdown"
                        >
                            <Option value="people">People</Option>
                            <Option value="groups">Groups</Option>
                        </Select>
                        <AutoComplete
                            options={searchResults}
                            onSelect={handleSelect}
                            style={{ width: '100%' }}
                        >
                            <Input.Search
                                placeholder="Search friends or groups"
                                value={searchQuery}
                                onChange={handleSearchInputChange}
                                className="search-bar"
                            />
                        </AutoComplete>
                    </div>
                    {loading ? (
                        <div className="loading-container">
                            <Spin size="large" />
                        </div>
                    ) : (
                        posts.map((post) => (
                            <div className="post" key={post.id}>
                                <div className="post-header">
                                    <Card style={{ width: 500 }}>
                                        <div className="profile">
                                            <Space align="start">
                                                <div className="profile-picture">
                                                    <Avatar size={48} icon={<UserOutlined />} />
                                                </div>
                                                <div className="username" onClick={() => navigate(`/profile/${post.userID}`)}>
                                                    <div>
                                                        {friends.find((friend) => friend.id === post.userID)?.email.split('@')[0]
                                                            ? <a>{friends.find((friend) => friend.id === post.userID)?.email.split('@')[0]}</a>
                                                            : <a onClick={() => navigate(`/profile/${post.userID}`)}>User not found with id: {post.userID}</a>}
                                                    </div>
                                                </div>
                                            </Space>
                                        </div>
                                        <div>
                                            <h4>Post Title: {post.postTitle}</h4>
                                            <p>Post content: {post.postBodyContent}</p>
                                        </div>
                                        <div className="post-actions">
                                            <Button type="text" className="like-button"><LikeOutlined /></Button>
                                            <Button type="text" className="comment-button"><CommentOutlined /></Button>
                                            <Button type="text" className="share-button"><SendOutlined /></Button>
                                        </div>
                                    </Card>
                                </div>
                            </div>
                        ))
                    )}
                </Col>
                <Col span={4} className="friends-col">
                    <Friend />
                </Col>
            </Row>
        </div>
    );
};

export default Post;
