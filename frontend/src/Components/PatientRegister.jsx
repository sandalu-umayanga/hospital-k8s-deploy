import React, { useEffect, useState } from "react";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import Row from "react-bootstrap/Row";
import * as formik from "formik";
import * as yup from "yup";
import axios from "axios";
import imageUrl from "./images/loginback.jpg";
import Button from "react-bootstrap/Button";
import "./ComponentCss/PatientRegister.css";
import Badge from "react-bootstrap/Badge";
import Stack from "react-bootstrap/Stack";

export default function PatientRegister() {
  const [doctors, setDoctors] = useState([]);
  const [nurses, setNurses] = useState([]);
  const [attendants, setAttendants] = useState([]);

  useEffect(() => {
    // Set the background image of the body
    document.body.style.backgroundImage = `url(${imageUrl})`;
    document.body.style.backgroundSize = "cover";
    document.body.style.backgroundRepeat = "no-repeat";
    document.body.style.backgroundPosition = "center center";
    document.body.style.backgroundAttachment = "fixed";

    // Fetch lists
    axios.get("/api/v1/doctor/getAllDoctors").then(res => {
        if(res.data.code === "09") setDoctors(res.data.data);
    });
    axios.get("/api/v1/nurse/getAllNurses").then(res => {
        if(res.data.code === "05") setNurses(res.data.data);
    });
    axios.get("/api/v1/attendant/getAllAttendants").then(res => {
        if(res.data.code === "05") setAttendants(res.data.data);
    });

    return () => {
      document.body.style.backgroundImage = "";
      document.body.style.backgroundSize = "";
      document.body.style.backgroundRepeat = "";
      document.body.style.backgroundPosition = "";
    };
  }, []);

  const { Formik } = formik;

  const schema = yup.object().shape({
    firstName: yup.string().required("First name is required"),
    lastName: yup.string().required("Last name is required"),
    email: yup.string().email("Invalid email format").required("Email is required"),
    address: yup.string().required("Address is required"),
    phone: yup.string().required("Phone is required"),
    gender: yup.string().required("Gender is required"),
    dob: yup.date().required("Date of Birth is required"),
    password: yup.string().required("Password is required").min(8, "Password must be at least 8 characters"),
    confirmPassword: yup.string().oneOf([yup.ref('password'), null], 'Passwords must match').required('Confirm Password is required'),
    doctor: yup.string(),
    nurse: yup.string(),
    attendant: yup.string(),
  });

  const handleSubmit = async (values, { setSubmitting, resetForm }) => {
    try {
      const payload = {
        firstName: values.firstName,
        lastName: values.lastName,
        email: values.email,
        address: values.address, // mapped from address field (frontend city field removed/merged?) or let's keep address
        phone: values.phone,
        gender: values.gender,
        dob: values.dob,
        password: values.password,
        nurse: values.nurse ? { id: parseInt(values.nurse) } : null,
        attendant: values.attendant ? { id: parseInt(values.attendant) } : null,
        doctorsIDs: values.doctor ? [parseInt(values.doctor)] : null
      };

      const response = await axios.post(
        "/api/v1/patient/savePatient",
        payload
      );
      
      if (response.data.code === "01") {
          alert("Patient registered successfully!");
          resetForm();
      } else {
          alert("Error: " + response.data.message);
      }
    } catch (error) {
      console.error("There was an error submitting the form", error);
      alert("Registration failed. Please try again.");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div>
      <Stack className="mx-5 mb-5">
        <Badge pill bg="primary" className="text-light">
          <h4>Patient Registration</h4>
        </Badge>
      </Stack>
      <Formik
        validationSchema={schema}
        onSubmit={handleSubmit}
        initialValues={{
          firstName: "",
          lastName: "",
          email: "",
          address: "",
          phone: "",
          gender: "",
          dob: "",
          password: "",
          confirmPassword: "",
          doctor: "",
          nurse: "",
          attendant: "",
        }}
      >
        {({
          handleSubmit,
          handleChange,
          values,
          touched,
          errors,
          isSubmitting,
        }) => (
          <Form noValidate onSubmit={handleSubmit} className="mx-5">
            <Row className="mb-3">
              <Form.Group as={Col} md="4" controlId="firstName">
                <Form.Label className="text-light">First name</Form.Label>
                <Form.Control
                  type="text"
                  name="firstName"
                  value={values.firstName}
                  onChange={handleChange}
                  isInvalid={touched.firstName && !!errors.firstName}
                />
                <Form.Control.Feedback type="invalid">{errors.firstName}</Form.Control.Feedback>
              </Form.Group>
              <Form.Group as={Col} md="4" controlId="lastName">
                <Form.Label className="text-light">Last name</Form.Label>
                <Form.Control
                  type="text"
                  name="lastName"
                  value={values.lastName}
                  onChange={handleChange}
                  isInvalid={touched.lastName && !!errors.lastName}
                />
                <Form.Control.Feedback type="invalid">{errors.lastName}</Form.Control.Feedback>
              </Form.Group>
              <Form.Group as={Col} md="4" controlId="email">
                <Form.Label className="text-light">Email</Form.Label>
                <Form.Control
                  type="email"
                  name="email"
                  value={values.email}
                  onChange={handleChange}
                  isInvalid={touched.email && !!errors.email}
                />
                <Form.Control.Feedback type="invalid">{errors.email}</Form.Control.Feedback>
              </Form.Group>
            </Row>

            <Row className="mb-3">
              <Form.Group as={Col} md="4" controlId="dob">
                <Form.Label className="text-light">Date of Birth</Form.Label>
                <Form.Control
                  type="date"
                  name="dob"
                  value={values.dob}
                  onChange={handleChange}
                  isInvalid={touched.dob && !!errors.dob}
                />
                <Form.Control.Feedback type="invalid">{errors.dob}</Form.Control.Feedback>
              </Form.Group>
              <Form.Group as={Col} md="4" controlId="phone">
                <Form.Label className="text-light">Phone</Form.Label>
                <Form.Control
                  type="text"
                  name="phone"
                  value={values.phone}
                  onChange={handleChange}
                  isInvalid={touched.phone && !!errors.phone}
                />
                <Form.Control.Feedback type="invalid">{errors.phone}</Form.Control.Feedback>
              </Form.Group>
              <Form.Group as={Col} md="4" controlId="address">
                <Form.Label className="text-light">Address</Form.Label>
                <Form.Control
                  type="text"
                  name="address"
                  value={values.address}
                  onChange={handleChange}
                  isInvalid={touched.address && !!errors.address}
                />
                <Form.Control.Feedback type="invalid">{errors.address}</Form.Control.Feedback>
              </Form.Group>
            </Row>

            <Row className="mb-3">
              <Form.Group as={Col} md="4" controlId="gender">
                <Form.Label className="text-light">Gender</Form.Label>
                <Form.Select
                  name="gender"
                  value={values.gender}
                  onChange={handleChange}
                  isInvalid={touched.gender && !!errors.gender}
                >
                  <option value="">Select Gender</option>
                  <option value="Male">Male</option>
                  <option value="Female">Female</option>
                  <option value="Other">Other</option>
                </Form.Select>
                <Form.Control.Feedback type="invalid">{errors.gender}</Form.Control.Feedback>
              </Form.Group>
              <Form.Group as={Col} md="4" controlId="password">
                <Form.Label className="text-light">Password</Form.Label>
                <Form.Control
                  type="password"
                  name="password"
                  value={values.password}
                  onChange={handleChange}
                  isInvalid={touched.password && !!errors.password}
                />
                <Form.Control.Feedback type="invalid">{errors.password}</Form.Control.Feedback>
              </Form.Group>
              <Form.Group as={Col} md="4" controlId="confirmPassword">
                <Form.Label className="text-light">Confirm Password</Form.Label>
                <Form.Control
                  type="password"
                  name="confirmPassword"
                  value={values.confirmPassword}
                  onChange={handleChange}
                  isInvalid={touched.confirmPassword && !!errors.confirmPassword}
                />
                <Form.Control.Feedback type="invalid">{errors.confirmPassword}</Form.Control.Feedback>
              </Form.Group>
            </Row>

            <Row className="mb-3">
              <Form.Group as={Col} md="4" controlId="doctor">
                <Form.Label className="text-light">Assign Doctor</Form.Label>
                <Form.Select
                  name="doctor"
                  value={values.doctor}
                  onChange={handleChange}
                  isInvalid={touched.doctor && !!errors.doctor}
                >
                  <option value="">Select Doctor</option>
                  {doctors.map(d => <option key={d.id} value={d.id}>{d.firstName} {d.lastName}</option>)}
                </Form.Select>
              </Form.Group>
              <Form.Group as={Col} md="4" controlId="nurse">
                <Form.Label className="text-light">Assign Nurse</Form.Label>
                <Form.Select
                  name="nurse"
                  value={values.nurse}
                  onChange={handleChange}
                  isInvalid={touched.nurse && !!errors.nurse}
                >
                  <option value="">Select Nurse</option>
                  {nurses.map(n => <option key={n.id} value={n.id}>{n.firstName} {n.lastName}</option>)}
                </Form.Select>
              </Form.Group>
              <Form.Group as={Col} md="4" controlId="attendant">
                <Form.Label className="text-light">Assign Attendant</Form.Label>
                <Form.Select
                  name="attendant"
                  value={values.attendant}
                  onChange={handleChange}
                  isInvalid={touched.attendant && !!errors.attendant}
                >
                  <option value="">Select Attendant</option>
                  {attendants.map(a => <option key={a.id} value={a.id}>{a.firstName} {a.lastName}</option>)}
                </Form.Select>
              </Form.Group>
            </Row>

            <Button type="submit" disabled={isSubmitting}>
              {isSubmitting ? "Registering..." : "Register Patient"}
            </Button>
          </Form>
        )}
      </Formik>
    </div>
  );
}
