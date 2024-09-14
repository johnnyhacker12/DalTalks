import React from 'react';
import { Link } from 'react-router-dom';

const ErrorPage = () => {
    return (
        <div className="error-page">
            <h1>Access Denied</h1>
            <p>You need to log in to access this page.</p>
            <Link to="/login">Go to Login</Link>
        </div>
    );
};

export default ErrorPage;