import React, { useEffect } from "react";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import Row from "react-bootstrap/Row";
import * as formik from "formik";
import * as yup from "yup";
import axios from "axios";
import imageUrl from "./images/db1.jpg";
import Button from "react-bootstrap/Button";
import Badge from "react-bootstrap/Badge";
import Stack from "react-bootstrap/Stack";

export default function RegisterForm(props) {
  useEffect(() => {
    // Set the background image of the body
    document.body.style.backgroundImage = `url(${imageUrl})`;
    document.body.style.backgroundSize = "cover"; // Ensures the image covers the viewport
    document.body.style.backgroundRepeat = "no-repeat"; // Ensures the image does not repeat
    document.body.style.backgroundPosition = "center center"; // Centers the image
    document.body.style.backgroundAttachment = "fixed"; // Prevents the background image from moving when the component unmounts

    // Cleanup function to reset background image when the component unmounts
    return () => {
      document.body.style.backgroundImage = "";
      document.body.style.backgroundSize = "";
      document.body.style.backgroundRepeat = "";
      document.body.style.backgroundPosition = "";
    };
  }, []);

  const { Formik } = formik;
  const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/; // Password must contain at least one digit, one lowercase letter, one uppercase letter, and at least 8 characters

  const schema = yup.object().shape({
    firstName: yup.string().required("First name is required"),
    lastName: yup.string().required("Last name is required"),
    email: yup
      .string()
      .email("Invalid email format")
      .required("Email is required"),
    status: yup.string().required("Status is required"),
    address: yup.string().required("Address is required"),
    gender: yup.string().required("Gender is required"),
    phone: yup.string().required("Phone is required"),
    simpleOverview: yup.string(),
    password: yup
      .string()
      .min(8, "Password must be at least 8 characters")
      .matches(
        passwordRegex,
        "Password must contain at least one digit, one lowercase letter, one uppercase letter, and at least 8 characters"
      )
      .required("Password is required"),
    confirmPassword: yup
      .string()
      .oneOf([yup.ref("password"), null], "Passwords must match")
      .required("Confirm password is required"),
    agree: yup.bool().oneOf([true], "You must agree before submitting."),
    
  });

  return (
    <div>
      <Stack className="mx-5 mb-5">
        <Badge pill bg="primary" className="text-light">
          <h4>{props.name}</h4>
        </Badge>
      </Stack>
      <Formik
        validationSchema={schema}
        onSubmit={(values, { resetForm }) => {
          axios
            .post(props.link, values)
            .then((response) => {
              alert(response.data.message);
              resetForm();
            })
            .catch((error) => {
              alert("There was an error! :- " + error);
            });
        }}
        initialValues={{
          firstName: "",
          lastName: "",
          email: "",
          status: "",
          address: "",
          gender: "",
          phone: "",
          simpleOverview: "",
          password: "",
          confirmPassword: "",
          agree: false,
          
        }}
      >
        {({ handleSubmit, handleChange, values, touched, errors }) => (
          <Form noValidate onSubmit={handleSubmit} className="mx-5">
            <Row className="mb-3">
              <Form.Group
                as={Col}
                md="4"
                controlId="validationFormik101"
                className="position-relative"
              >
                <Form.Label className="text-light">First name</Form.Label>
                <Form.Control
                  type="text"
                  name="firstName"
                  value={values.firstName}
                  onChange={handleChange}
                  isInvalid={!!errors.firstName}
                />
                <Form.Control.Feedback type="invalid" tooltip>
                  {errors.firstName}
                </Form.Control.Feedback>
              </Form.Group>
              <Form.Group
                as={Col}
                md="4"
                controlId="validationFormik102"
                className="position-relative"
              >
                <Form.Label className="text-light">Last name</Form.Label>
                <Form.Control
                  type="text"
                  name="lastName"
                  value={values.lastName}
                  onChange={handleChange}
                  isInvalid={!!errors.lastName}
                />
                <Form.Control.Feedback type="invalid" tooltip>
                  {errors.lastName}
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group as={Col} md="4" controlId="validationFormikUsername2">
                <Form.Label className="text-light">Email</Form.Label>
                <InputGroup hasValidation>
                  <InputGroup.Text id="inputGroupPrepend">@</InputGroup.Text>
                  <Form.Control
                    type="email"
                    placeholder="Email"
                    aria-describedby="inputGroupPrepend"
                    name="email"
                    value={values.email}
                    onChange={handleChange}
                    isInvalid={!!errors.email}
                  />
                  <Form.Control.Feedback type="invalid" tooltip>
                    {errors.email}
                  </Form.Control.Feedback>
                </InputGroup>
              </Form.Group>
            </Row>
            <Row className="mb-3">
              <Form.Group
                as={Col}
                md="3"
                controlId="validationFormik103"
                className="position-relative"
              >
                <Form.Label className="text-light">Status</Form.Label>
                <Form.Select
                  name="status"
                  value={values.status}
                  onChange={handleChange}
                  isInvalid={!!errors.status}
                >
                  <option value="">Select Status</option>
                  <option value="Married">Married</option>
                  <option value="Single">Single</option>
                  <option value="Divorced">Divorced</option>
                  <option value="Other">Other</option>
                </Form.Select>
                <Form.Control.Feedback type="invalid" tooltip>
                  {errors.status}
                </Form.Control.Feedback>
              </Form.Group>
              <Form.Group
                as={Col}
                md="3"
                controlId="validationFormik103"
                className="position-relative"
              >
                <Form.Label className="text-light">Address</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Address"
                  name="address"
                  value={values.address}
                  onChange={handleChange}
                  isInvalid={!!errors.address}
                />
                <Form.Control.Feedback type="invalid" tooltip>
                  {errors.address}
                </Form.Control.Feedback>
              </Form.Group>
              <Form.Group
                as={Col}
                md="3"
                controlId="validationFormik103"
                className="position-relative"
              >
                <Form.Label className="text-light">Phone</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Phone"
                  name="phone"
                  value={values.phone}
                  onChange={handleChange}
                  isInvalid={!!errors.phone}
                />
                <Form.Control.Feedback type="invalid" tooltip>
                  {errors.phone}
                </Form.Control.Feedback>
              </Form.Group>
              <Form.Group
                as={Col}
                md="3"
                controlId="validationFormik105"
                className="position-relative"
              >
                <Form.Label className="text-light">Gender</Form.Label>
                <Form.Select
                  name="gender"
                  value={values.gender}
                  onChange={handleChange}
                  isInvalid={!!errors.gender}
                >
                  <option value="">Select Gender</option>
                  <option value="male">Male</option>
                  <option value="female">Female</option>
                  <option value="other">Other</option>
                </Form.Select>
                <Form.Control.Feedback type="invalid" tooltip>
                  {errors.gender}
                </Form.Control.Feedback>
              </Form.Group>
            </Row>
            <Row className="mb-3">
              <Form.Group
                as={Col}
                md="12"
                controlId="validationFormik103"
                className="position-relative"
              >
                <Form.Label className="text-light">Simple Overview</Form.Label>
                <Form.Control
                  as="textarea" // Change this line to use the 'as' prop
                  name="simpleOverview"
                  value={values.simpleOverview}
                  onChange={handleChange}
                  isInvalid={!!errors.simpleOverview}
                />
                <Form.Control.Feedback type="invalid" tooltip>
                  {errors.simpleOverview}
                </Form.Control.Feedback>
              </Form.Group>
            </Row>
            <Row className="mb-3">
              <Form.Group
                as={Col}
                md="6"
                controlId="validationFormikPassword"
                className="position-relative"
              >
                <Form.Label className="text-light">Password</Form.Label>
                <Form.Control
                  type="password"
                  name="password"
                  value={values.password}
                  onChange={handleChange}
                  isInvalid={!!errors.password}
                />
                <Form.Control.Feedback type="invalid" tooltip>
                  {errors.password}
                </Form.Control.Feedback>
              </Form.Group>
              <Form.Group
                as={Col}
                md="6"
                controlId="validationFormikConfirmPassword"
                className="position-relative"
              >
                <Form.Label className="text-light">Confirm Password</Form.Label>
                <Form.Control
                  type="password"
                  name="confirmPassword"
                  value={values.confirmPassword}
                  onChange={handleChange}
                  isInvalid={!!errors.confirmPassword}
                />
                <Form.Control.Feedback type="invalid" tooltip>
                  {errors.confirmPassword}
                </Form.Control.Feedback>
              </Form.Group>
            </Row>
            <Form.Group className="mb-5 mt-5">
              <Form.Check
                className="text-warning"
                required
                name="agree"
                label="Agree to terms and conditions"
                onChange={handleChange}
                checked={values.agree}
                isInvalid={!!errors.agree}
                feedback={errors.agree}
                feedbackType="invalid"
              />
            </Form.Group>
            <Button type="submit">Submit form</Button>
          </Form>
        )}
      </Formik>
    </div>
  );
}

