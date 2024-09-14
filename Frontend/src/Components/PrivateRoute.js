import React from 'react';
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ element: Component }) => {
  const isAuthenticated = localStorage.getItem('loggedIn') === 'true';
  console.log('PrivateRoute: isAuthenticated', isAuthenticated);

  return isAuthenticated ? <Component /> : <Navigate to="/error" />;
};

export default PrivateRoute;
