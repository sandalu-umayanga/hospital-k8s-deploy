import React from 'react'
import "./ComponentCss/NoteItem.css";
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import imageUrlNoteIcon from './images/noteIcon.png';

export default function NoteItem() {
  return (
      <Card style={{ width: "18rem" }} className="noteItem m-0 p-0 mb-3 mt-3">
        <Card.Img variant="top" src={imageUrlNoteIcon} className='noteIcon mt-2 ms-2'/>
        <Card.Body>
          <Card.Title className="text-light"><b>Card Title</b></Card.Title>
          <Card.Text className="text-warning">
            Some quick example text to build on the card title and make up the
            bulk of the card's content.
          </Card.Text>
          <Button variant="primary">Go somewhere</Button>
        </Card.Body>
      </Card>
  );
}

