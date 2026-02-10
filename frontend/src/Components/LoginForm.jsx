import React, { useEffect, useState } from "react";
import FloatingLabel from "react-bootstrap/FloatingLabel";
import Form from "react-bootstrap/Form";
import imageUrl from "./images/db1.jpg";
import "./ComponentCss/LoginForm.css"; // Import your custom CSS file
import Badge from "react-bootstrap/Badge";
import Button from "react-bootstrap/Button";
import axios from "axios";
import { Formik } from "formik";
import * as yup from "yup";
import { useNavigate } from "react-router-dom"; // Import useNavigate

export default function LoginForm(props) {
  const navigate = useNavigate(); // Initialize useNavigate hook

  useEffect(() => {
    // Set the background image of the body
    document.body.style.backgroundImage = `url(${imageUrl})`;
    document.body.style.backgroundSize = "cover";
    document.body.style.backgroundRepeat = "no-repeat";
    document.body.style.backgroundPosition = "center center";
    document.body.style.backgroundAttachment = "fixed";

    // Cleanup function to reset background image when the component unmounts
    return () => {
      document.body.style.backgroundImage = "";
      document.body.style.backgroundSize = "";
      document.body.style.backgroundRepeat = "";
      document.body.style.backgroundPosition = "";
    };
  }, []);

  const [isLoading, setLoading] = useState(false);

  const validationSchema = yup.object().shape({
    email: yup.string().email("Invalid email format").required("Email is required"),
    password: yup.string().required("Password is required"),
  });

  return (
    <div className="login-form-container d-flex justify-content-center align-items-center">
      <div className="login-form">
        <Badge bg="primary" className="mb-5 badgeLogin text-light">
          <h3>
            <b>{props.name}</b>
          </h3>
        </Badge>
        <Formik
          initialValues={{ email: "", password: "" }}
          validationSchema={validationSchema}
          onSubmit={(values, { setSubmitting }) => {
            setLoading(true);
            axios.post(props.link, values)
              .then(response => {
                alert(response.data.message);
                setLoading(false);
                setSubmitting(false);

                // Redirect to a page with user details and accessible features
                if (response.data.code === props.successCode) {
                  localStorage.setItem("token", response.data.token); // Store token
                  localStorage.setItem("user", JSON.stringify(response.data.data)); // Store user object
                  localStorage.setItem("role", props.role); // Store role
                  props.setAuthenticated(response.data.data); // Pass user data
                  navigate(props.forwardTo, { state: { user: response.data.data} });
                }
                
              })
              .catch(error => {
                alert("Error occurred: " + error.message);
                setLoading(false);
                setSubmitting(false);
              });
          }}
        >
          {({
            handleSubmit,
            handleChange,
            values,
            touched,
            errors,
          }) => (
            <Form noValidate onSubmit={handleSubmit}>
              <FloatingLabel
                controlId="floatingInput"
                label="Email address"
                className="mb-5"
              >
                <Form.Control
                  type="email"
                  placeholder="name@example.com"
                  name="email"
                  value={values.email}
                  onChange={handleChange}
                  isInvalid={touched.email && !!errors.email}
                />
                <Form.Control.Feedback type="invalid">
                  {errors.email}
                </Form.Control.Feedback>
              </FloatingLabel>
              <FloatingLabel controlId="floatingPassword" label="Password">
                <Form.Control
                  type="password"
                  placeholder="Password"
                  name="password"
                  value={values.password}
                  onChange={handleChange}
                  isInvalid={touched.password && !!errors.password}
                />
                <Form.Control.Feedback type="invalid">
                  {errors.password}
                </Form.Control.Feedback>
              </FloatingLabel>
              <div className="text-center mt-5">
                <Button
                  variant="primary"
                  type="submit"
                  disabled={isLoading}
                >
                  {isLoading ? "Checking…" : "Click to login"}
                </Button>
              </div>
            </Form>
          )}
        </Formik>
      </div>
    </div>
  );
}

