import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

const securityQuestions = [
    "What was the name of your first pet?",
    "What is your mother's maiden name?",
    "What was the name of your first school?",
    "What was the make and model of your first car?",
    "What was your favorite food as a child?"
];

const SignUp = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [selectedSecurityQuestion, setSelectedSecurityQuestion] = useState(securityQuestions[0]);
    const [securityAnswer, setSecurityAnswer] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!email || !password || !selectedSecurityQuestion || !securityAnswer) {
            alert('Please fill in all required fields');
            return;
        }

        const formData = {
            email,
            password,
            securityQuestion: selectedSecurityQuestion,
            securityAnswer,
            status: "pending",
            role: "user"
        };

        try {
            const response = await axios.post('http://localhost:8080/api/user/create', formData);
            console.log(formData);
            alert('Account created successfully');
            navigate('/login');
        } catch (error) {
            setErrorMessage(error.response?.data?.message || 'An error occurred. Please try again.');
        }
    };

    return (
        <div className="container">
            <div className="login-box">
                <h1 className="login-title">DalTalks</h1>
                <form onSubmit={handleSubmit}>
                    <div className="input-group">
                        <input
                            type="text"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="Email Address"
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
                    <div className="input-group">
                        <select
                            value={selectedSecurityQuestion}
                            onChange={(e) => setSelectedSecurityQuestion(e.target.value)}
                            required
                        >
                            {securityQuestions.map((question, index) => (
                                <option key={index} value={question}>
                                    {question}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="input-group">
                        <input
                            type="text"
                            value={securityAnswer}
                            onChange={(e) => setSecurityAnswer(e.target.value)}
                            placeholder="Security Answer"
                            required
                        />
                    </div>
                    <button type="submit" className="login-button">Sign Up</button>
                    <div className="error-message">{errorMessage}</div>
                    <div className="extra-links">
                        <p>Already have an account? <Link to="/login">Log in</Link></p>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default SignUp;
