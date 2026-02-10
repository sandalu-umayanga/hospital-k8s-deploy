import React, { useEffect, useState } from "react";
import { Container, Alert, Spinner, Button } from "react-bootstrap";
import axios from "axios";
import imageUrl from "./images/db1.jpg";

export default function Notifications() {
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(true);
  const user = JSON.parse(localStorage.getItem("user"));
  const role = localStorage.getItem("role");

  useEffect(() => {
    document.body.style.backgroundImage = `url(${imageUrl})`;
    document.body.style.backgroundSize = "cover";
    document.body.style.backgroundRepeat = "no-repeat";
    document.body.style.backgroundPosition = "center center";
    document.body.style.backgroundAttachment = "fixed";

    if (user && user.id && role) {
      fetchNotifications();
    } else {
      setLoading(false);
    }

    return () => {
      document.body.style.backgroundImage = "";
    };
  }, []);

  const fetchNotifications = () => {
    axios.get(`/api/v1/notification/${user.id}/${role}`)
      .then(res => {
        if (res.data.code === "05") setNotifications(res.data.data);
        setLoading(false);
      })
      .catch(err => {
        console.error(err);
        setLoading(false);
      });
  };

  const markAsRead = (id) => {
    axios.put(`/api/v1/notification/markAsRead/${id}`)
      .then(() => fetchNotifications())
      .catch(err => console.error(err));
  };

  if (!user) return <Container className="mt-5"><Alert variant="danger">Please login to view notifications.</Alert></Container>;

  return (
    <Container className="mt-5">
      <h2 className="mb-4 text-light">Notifications</h2>
      {loading ? <Spinner animation="border" variant="light" /> : (
        notifications.length > 0 ? notifications.map((n) => (
          <Alert key={n.id} variant={n.isRead ? "secondary" : "info"} className="shadow-sm d-flex justify-content-between align-items-center" style={{ opacity: 0.9 }}>
            <div>
                <Alert.Heading className="small">{new Date(n.timestamp).toLocaleString()}</Alert.Heading>
                <p className="mb-0">{n.message}</p>
            </div>
            {!n.isRead && <Button variant="outline-primary" size="sm" onClick={() => markAsRead(n.id)}>Mark Read</Button>}
          </Alert>
        )) : <Alert variant="light">No notifications.</Alert>
      )}
    </Container>
  );
}

