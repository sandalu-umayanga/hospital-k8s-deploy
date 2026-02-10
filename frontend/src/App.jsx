import React, { useState } from "react"; // Import useState for managing authentication
import "./App.css";
import Footer from "./Components/Footer";
import Home from "./Components/Home";
import MainInfo from "./Components/MainInfo";
import Navigation from "./Components/Navigation";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import LoginForm from "./Components/LoginForm";
import RegisterForm from "./Components/RegisterForm";
import PatientRegister from "./Components/PatientRegister";
import Profile from "./Components/Profile";
import SelfHome from "./Components/SelfHome";
import PatientHome from "./Components/PatientHome";
import PatientList from "./Components/PatientList";
import TreatmentManagement from "./Components/TreatmentManagement";
import Appointments from "./Components/Appointments";
import Notifications from "./Components/Notifications";
import Statistics from "./Components/Statistics";
import AdminDashboard from "./Components/AdminDashboard";
import About from "./Components/About";
import Contact from "./Components/Contact";
import StaffManagement from "./Components/StaffManagement";
import axios from "axios";

// Axios interceptor to add token
axios.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

function App() {
  const [isAuthenticated, setAuthenticated] = useState(false); // State for authentication
  const [user, setUser] = useState(null); // State for logged-in user

  const handleLoginSuccess = (userData) => {
    setAuthenticated(true);
    setUser(userData);
  };

  const handleLogout = () => {
    setAuthenticated(false);
    setUser(null);
    localStorage.removeItem("token"); // Remove token on logout
  };

  return (
    <div className="App">
      <BrowserRouter>
        <Navigation isAuthenticated={isAuthenticated} user={user} onLogout={handleLogout} /> {/* Pass the authentication state */}
        <MainInfo />
        <div className="main-content">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/doctorregister" element={<RegisterForm name="Doctor Registration" link="/api/v1/doctor/saveDoctor" />} />
            <Route path="/nurseregister" element={<RegisterForm name="Nurse Registration" link="/api/v1/nurse/saveNurse" />} />
            <Route path="/attendantregister" element={<RegisterForm name="Attendant Registration" link="/api/v1/attendant/saveAttendant" />} />
            
            <Route path="/doctorLogin" element={<LoginForm name="Doctor Login" link="/api/v1/doctor/loginDoctor" forwardTo="/doctorHome" setAuthenticated={handleLoginSuccess} successCode="10" role="DOCTOR" />} />
            <Route path="/nurseLogin" element={<LoginForm name="Nurse Login" link="/api/v1/nurse/loginNurse" forwardTo="/nurseHome" setAuthenticated={handleLoginSuccess} successCode="07" role="NURSE" />} />
            <Route path="/attendantLogin" element={<LoginForm name="Attendant Login" link="/api/v1/attendant/loginAttendant" forwardTo="/attendantHome" setAuthenticated={handleLoginSuccess} successCode="10" role="ATTENDANT" />} />
            <Route path="/patientLogin" element={<LoginForm name="Patient Login" link="/api/v1/patient/loginPatient" forwardTo="/patientHome" setAuthenticated={handleLoginSuccess} successCode="10" role="PATIENT" />} />
            <Route path="/adminLogin" element={<LoginForm name="Admin Login" link="/api/v1/admin/loginAdmin" forwardTo="/adminDashboard" setAuthenticated={handleLoginSuccess} successCode="10" role="ADMIN" />} />
            
            <Route path="/attendantHome" element={<SelfHome profileLink="/attendantProfile" pronoun="Attendant. " role="Attendant" getPatientsLink="/api/v1/attendant/getPatientList/" onLogout={handleLogout} />} />
            <Route path="/nurseHome" element={<SelfHome profileLink="/nurseProfile" pronoun="Nurse. " role="Nurse" getPatientsLink="/api/v1/nurse/getPatientList/" onLogout={handleLogout} />} />
            <Route path="/doctorHome" element={<SelfHome profileLink="/doctorProfile" pronoun="Dr. " role="Doctor" getPatientsLink="/api/v1/doctor/getPatientList/" onLogout={handleLogout} />} />
            <Route path="/patients" element={<PatientList />} />
            <Route path="/treatmentManagement" element={<TreatmentManagement />} />
            <Route path="/patientHome" element={<PatientHome />} />
            <Route path="/patientRegister" element={<PatientRegister />} />
            <Route path="/appointments" element={<Appointments />} />
            <Route path="/notifications" element={<Notifications />} />
            <Route path="/statistics" element={<Statistics />} />
            <Route path="/adminDashboard" element={<AdminDashboard onLogout={handleLogout} />} />
            <Route path="/staffManagement" element={<StaffManagement />} />
            <Route path="/about" element={<About />} />
            <Route path="/contact" element={<Contact />} />

            <Route path="/doctorProfile" element={<Profile 
            getPictureLink="/api/v1/doctor/getProfilePicture/" 
            userUpdateLink="/api/v1/doctor/updateDoctor" 
            updatePictureLink="/api/v1/doctor/updateProfilePicture/"
            pronoun="Dr. "/>} />

            <Route path="/nurseProfile" element={<Profile 
            getPictureLink="/api/v1/nurse/getProfilePicture/" 
            userUpdateLink="/api/v1/nurse/updateNurse" 
            updatePictureLink="/api/v1/nurse/updateProfilePicture/"
            pronoun="Nurse. "/>} />

            <Route path="/attendantProfile" element={<Profile 
            getPictureLink="/api/v1/attendant/getProfilePicture/" 
            userUpdateLink="/api/v1/attendant/updateAttendant" 
            updatePictureLink="/api/v1/attendant/updateProfilePicture/"
            pronoun="Attendant. "/>} />

          </Routes>
        </div>
        <Footer />
      </BrowserRouter>
    </div>
  );
}

export default App;

