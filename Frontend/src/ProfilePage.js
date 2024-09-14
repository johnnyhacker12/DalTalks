import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import './ProfilePage.css' 

//this displays the profile information by getting it from the database
const ProfilePage = () => {

  //profile ID is obtained as a parameter? 
  const {id} = useParams();

  const [profile, setProfile] = useState(null);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/profiles/getById/${id}`)
        console.log(response);
        setProfile(response.data);
      } catch (error) {
        console.error('Error fetching profile', error);
      }
    };

    fetchProfile();
  }, [id]);

  return (
    //need to limit vew to friends -> check if logged in user is friends with ID entered?
    <div className="container">
      <h2 className="title">My Profile</h2>
      {profile != null ? (
          <div className="profile">
            <p><strong>Status:</strong> {profile.status}</p>
            <p><strong>Interests:</strong> {profile.interests}</p>
            <p><strong>Major:</strong> {profile.major}</p>
            <p><strong>Location:</strong> {profile.location}</p>
            <p><strong>Birthday:</strong> {profile.birthday}</p>
          </div>
      ) : (
        <p>Loading...</p>
      )}
    </div>

  );
};

export default ProfilePage;
