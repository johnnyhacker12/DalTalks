import React, { useState } from 'react';
import axios from 'axios';


//this form sets up a profile with 5 entities 
const ProfilePageForm = () => {
    // need to get user ID -> stored in local storage 
    var id = localStorage.getItem('userId');
    console.log(id);

    const [userid, setUserid] = useState(id);
    const [interests, setInterests] = useState('');
    const [status, setStatus] = useState('');
    const [birthday, setBirthday] = useState('');
    const [major, setMajor] = useState('');
    const [location, setLocation] = useState('');
    
    const handleSubmit = async (event) => {
        event.preventDefault();
      
        const formData = {
            interests,
            status,
            birthday,
            major,
            location,
            userid
        };
      
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
      <div>
        <h2 style={{ marginTop: '10px', marginBottom: '10px' }} >Enter profile details: </h2> 
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', backgroundColor: '#f0f0f0' }}>
            <form onSubmit={handleSubmit} >
              
              <input type="text" name="interests" placeholder="Enter Interests" onChange={(e) => setInterests(e.target.value)} style={{ padding: '10px', height: '100px', width: '1000px' }} /><br></br><br></br>
              <input type="text" name="status" placeholder="Enter Status" onChange={(e) => setStatus(e.target.value)}  style={{ padding: '10px', height: '100px', width: '1000px' }} /><br></br><br></br>
              <input type="text" name="birthday" placeholder="Enter Birthday" onChange={(e) => setBirthday(e.target.value)} style={{ padding: '10px', height: '100px', width: '1000px' }} /><br></br><br></br>
              <input type="text" name="major" placeholder="Enter Major" onChange={(e) => setMajor(e.target.value)} style={{ padding: '10px', height: '100px', width: '1000px' }} /><br></br><br></br>
              <input type="text" name="location" placeholder="Enter Location" onChange={(e) => setLocation(e.target.value)} style={{ padding: '10px', height: '100px', width: '1000px' }} /><br></br><br></br>
              
              <button type="submit">Submit Changes</button>
            </form>
          </div>
        </div>
      );
    };

export default ProfilePageForm;
