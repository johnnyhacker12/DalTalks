import React, { useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';
import { Spin } from 'antd';
import axios from 'axios';

const AdminRoute = ({ element: Component }) => {
  const isAuthenticated = localStorage.getItem('loggedIn') === 'true';
  const [isAdmin, setIsAdmin] = useState(false);
  const [loading, setLoading] = useState(true);
  const userID = localStorage.getItem('userId');

  const checkRole = async (userID) => {
    try {
      const response = await axios.get(`http://localhost:8080/api/user/getRole/${userID}`);
      if (response.data === "BSB") {
        setIsAdmin(true);
      }
    } catch {
      alert("Cannot get user ID");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (userID) {
      checkRole(userID);
    } else {
      setLoading(false);
    }
  }, [userID]);

  if (loading) {
    return <Spin size="large" />;
  }

  return isAuthenticated && isAdmin ? <Component /> : <Navigate to="/error" />;
};

export default AdminRoute;
