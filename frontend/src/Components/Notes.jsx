import React from 'react'
import "./ComponentCss/Notes.css";
import NoteItem from './NoteItem';


export default function Notes() {
  return (
    <div className="noteMain">
      <div className="container     mx-0 mx-md-5 text">
        <div className="row d-flex align-content-center">
            <div className="col-4 notes ms-5 ms-md-0">
                <h1>Notes</h1>
            </div>
        </div>
        <div className="row sm-text-center">
            <div className="col d-flex align-content-center md-align-content-start justify-content-around flex-wrap notesSpace">
                <NoteItem/>
                <NoteItem/>
                <NoteItem/>
                <NoteItem/>
                <NoteItem/>
                <NoteItem/>
                

            </div>
        </div>
      </div>
    </div>
  )
}

