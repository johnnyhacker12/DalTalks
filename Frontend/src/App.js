import React from 'react';
import './App.css';
import Post from './Components/post';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Login from './Components/Login';
import SignUp from './Components/signUp';
import ForgotPassword from './Components/forgotPassword';
import ProfilePage from './Components/ProfilePage';
import ProfilePageForm from './Components/ProfilePageForm';
import ErrorPage from './Components/ErrorPage';
import PrivateRoute from './Components/PrivateRoute';
import UserProfilePage from './Components/UserProfilePage';
import FriendRequests from './Components/FriendRequests';
import Friend from './Components/friends';
import FriendsList from './Components/FriendsList';  // Import the FriendsList component
import Admin from './Components/admin';
import AdminRoute from './Components/adminRoute';
import CreateGroup from './Components/CreateGroup'; // Import CreateGroup component
import GroupList from './Components/GroupList'; // Import GroupList component
import GroupDetails from './Components/GroupDetails';
import MyGroups from './Components/MyGroups';

const App = () => {
  return (
    <div className="App">
      <Router>
        {/* Routing list */}
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/forgotPassword" element={<ForgotPassword />} />
          <Route path="/error" element={<ErrorPage />} />
          <Route path="/profile" element={<PrivateRoute element={UserProfilePage} />} />
          <Route path="/admin" element={<AdminRoute element={Admin} />} />
          <Route path="/profile/:id" element={<PrivateRoute element={ProfilePage} />} />
          <Route path="/editprofile" element={<PrivateRoute element={ProfilePageForm} />} />
          <Route path="/main" element={<PrivateRoute element={Post} />} />
          <Route path="/" element={<Navigate to="/login" />} />
          <Route path="/friend" element={<Friend />} />
          <Route path="/friend-requests" element={<FriendRequests />} />
          <Route path="/My-friends" element={<FriendsList />} />
          <Route path="/create-group" element={<PrivateRoute element={CreateGroup} />} />
          <Route path="/groups" element={<PrivateRoute element={GroupList} />} />
          <Route path="/groups/:id" element={<PrivateRoute element={GroupDetails} />} />
          <Route path="/my-groups" element={<PrivateRoute element={MyGroups} />} />
        </Routes>
      </Router>
    </div>
  );
};

export default App;
