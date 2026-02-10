import React, { useEffect } from "react";
import imageUrl from "./images/back1.jpg";
import imageUrlshape1 from "./images/d1.png";
import imageUrlshape2 from "./images/d2.png";
import "./ComponentCss/Home.css";
import Notes from "./Notes";
import Image from 'react-bootstrap/Image';

export default function Home() {
   useEffect(() => {
    // Set the background image of the body
    document.body.style.backgroundImage = `url(${imageUrl})`;
    document.body.style.backgroundSize = 'cover'; // Ensures the image covers the viewport
    document.body.style.backgroundRepeat = 'no-repeat'; // Ensures the image does not repeat
    document.body.style.backgroundPosition = 'center center'; // Centers the image
    document.body.style.backgroundAttachment = 'fixed'; // Prevents the background image from moving when the

    // Cleanup function to reset background image when the component unmounts
    return () => {
      document.body.style.backgroundImage = '';
      document.body.style.backgroundSize = '';
      document.body.style.backgroundRepeat = '';
      document.body.style.backgroundPosition = '';
    };
  }, []);
  return (
    <div>
      <div className="container mt-4 mb-5">
        <div className="row">
          <div className="col-12 text-light">
            <h1>
              The Future Of the world's
              <br />
              Medical Website
            </h1>
          </div>
        </div>
      </div>
      <div className="container text-center mb-5">
        <div className="row justify-content-center">
          <div className="col-auto">
            <Image src={imageUrlshape1} fluid className="announceShape" alt="shape"/> 
          </div>
          <div className="col-12 md-col-6 lg-col-4 bg-primary rounded announce ">
            <div className="text-start">
              <h2>
                <b>Opening Hour</b>
              </h2>
              <h4 className="opacity-75">Medical Center</h4>
            </div>
            <div className="d-flex justify-content-between">
              <div className="text-start">
                <br />
                <p>
                  <b>Mon-Fri</b>
                </p>
                <br />
                <p>
                  <b>Sat</b>
                </p>
                <br />
                <p>
                  <b>Sun</b>
                </p>
              </div>
              <div className="text-end">
                <br />
                <p>
                  <b>5.00 - 17.00</b>
                </p>
                <br />
                <p>
                  <b>6.30 - 17.00</b>
                </p>
                <br />
                <p>
                  <b>6.30 - 17.00</b>
                </p>
              </div>
            </div>
          </div>
          <div className="col-auto">
            <Image src={imageUrlshape2} fluid className="announceShape" alt="shape"/> 
          </div>
        </div>
      </div>
      <div className="row mb-5"></div>
      <Notes/>
    </div>
  );
}

