import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import '../Login.css';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            const response = await axios.post('http://localhost:8080/api/user/login', { email, password });
            const { id } = response.data;
            localStorage.setItem('loggedIn', 'true');
            localStorage.setItem('userId', id);
            window.location.href = '/main';
        } catch (error) {
            setErrorMessage(error.response.data.message || 'An error occurred. Please try again.');
        }
    };

    return (
        <div className="container">
            <div className="login-box">
                <h1 className="login-title">DalTalks</h1>
                <form onSubmit={handleSubmit}>
                    <div className="input-group">
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="Email"
                            required
                        />
                    </div>
                    <div className="input-group">
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Password"
                            required
                        />
                    </div>
                    <div className="forgot-password">
                        <p><Link to="/forgotpassword">Forgot password?</Link></p>
                    </div>
                    <button type="submit" className="login-button">Log In</button>
                    <div className="error-message">{errorMessage}</div>
                    <div className="extra-links">
                        <p>Don't have an account? <Link to="/signup">Sign up</Link></p>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default Login;