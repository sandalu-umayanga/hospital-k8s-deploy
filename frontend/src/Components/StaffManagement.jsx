import React, { useEffect, useState } from "react";
import axios from "axios";
import { Container, Row, Col, Table, Button, Badge, Spinner, Alert, Tabs, Tab } from "react-bootstrap";
import imageUrl from "./images/db1.jpg";

export default function StaffManagement() {
  const [doctors, setDoctors] = useState([]);
  const [nurses, setNurses] = useState([]);
  const [attendants, setAttendants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    document.body.style.backgroundImage = `url(${imageUrl})`;
    document.body.style.backgroundSize = "cover";
    document.body.style.backgroundRepeat = "no-repeat";
    document.body.style.backgroundPosition = "center center";
    document.body.style.backgroundAttachment = "fixed";

    fetchStaff();

    return () => {
      document.body.style.backgroundImage = "";
    };
  }, []);

  const fetchStaff = async () => {
    setLoading(true);
    try {
      const [docRes, nurseRes, attRes] = await Promise.all([
        axios.get("/api/v1/doctor/getAllDoctors"),
        axios.get("/api/v1/nurse/getAllNurses"),
        axios.get("/api/v1/attendant/getAllAttendants")
      ]);

      if (docRes.data.code === "09") setDoctors(docRes.data.data);
      if (nurseRes.data.code === "05") setNurses(nurseRes.data.data);
      if (attRes.data.code === "05") setAttendants(attRes.data.data);
      
      setLoading(false);
    } catch (err) {
      console.error(err);
      setError("Failed to fetch staff list.");
      setLoading(false);
    }
  };

  const handleDelete = async (role, id) => {
    if (!window.confirm(`Are you sure you want to delete this ${role}?`)) return;

    let url = "";
    let payload = { id };
    if (role === "Doctor") url = "/api/v1/doctor/deleteDoctor";
    else if (role === "Nurse") url = "/api/v1/nurse/deleteNurse";
    else if (role === "Attendant") url = "/api/v1/attendant/deleteAttendant";

    try {
      const res = await axios.delete(url, { data: payload });
      alert(res.data.message);
      fetchStaff();
    } catch (err) {
      alert("Delete failed: " + err.message);
    }
  };

  const StaffTable = ({ data, role }) => (
    <Table striped bordered hover variant="dark" responsive className="mt-3 shadow" style={{ opacity: 0.9 }}>
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Email</th>
          <th>Phone</th>
          <th>Gender</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {data && data.length > 0 ? data.map((item) => (
          <tr key={item.id}>
            <td>{item.id}</td>
            <td>{item.firstName} {item.lastName}</td>
            <td>{item.email}</td>
            <td>{item.phone}</td>
            <td>{item.gender}</td>
            <td>
              <Button variant="danger" size="sm" onClick={() => handleDelete(role, item.id)}>Delete</Button>
            </td>
          </tr>
        )) : (
          <tr>
            <td colSpan="6" className="text-center">No {role}s found.</td>
          </tr>
        )}
      </tbody>
    </Table>
  );

  return (
    <Container className="mt-5">
      <h2 className="text-light mb-4">Staff Management</h2>
      {error && <Alert variant="danger">{error}</Alert>}
      {loading ? (
        <div className="text-center mt-5">
          <Spinner animation="border" variant="light" />
        </div>
      ) : (
        <Tabs defaultActiveKey="doctors" className="mb-3" fill>
          <Tab eventKey="doctors" title="Doctors">
            <StaffTable data={doctors} role="Doctor" />
          </Tab>
          <Tab eventKey="nurses" title="Nurses">
            <StaffTable data={nurses} role="Nurse" />
          </Tab>
          <Tab eventKey="attendants" title="Attendants">
            <StaffTable data={attendants} role="Attendant" />
          </Tab>
        </Tabs>
      )}
    </Container>
  );
}

