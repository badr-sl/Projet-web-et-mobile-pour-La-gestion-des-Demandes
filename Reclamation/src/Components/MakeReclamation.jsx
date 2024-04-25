import PropTypes from "prop-types"; // Import PropTypes
import { useState } from "react";

function MakeReclamation(props) {
  //const todayDate = new Date().toLocaleDateString("fr-FR");
  // States
  const [Details, SetDetails] = useState("Empty");
  const [Title, SetTitle] = useState("Title");
  const [Date, SetDate] = useState("todayDate");

  // Handle Changes
  function DetailsHandler(event) {
    SetDetails(event.target.value);
  }
  function TitleHandler(event) {
    SetTitle(event.target.value);
  }
  function DateHandler(event) {
    SetDate(event.target.value);
  }
  function SubmitHandler(event) {
    event.preventDefault();
    /*const NoteData ={
      date: Date,
      etat: "pending",
      idDemande: null,
      sujet: Details,
      title: Title,
      user:null,
    }*/
    const NoteData = {
      date: "2024-06-06", // Use an actual date string here
      etat: "pending",
      idDemande: null,
      sujet: "Details", // Use actual details here
      title: "Title", // Use an actual title here
    };

    // Pass data to parent component
    props.onAddNote(NoteData);
    props.onCancel();
  }

  return (
    <div className="  bg-gradient-to-r from-orange-200 via-stone-200 to-teal-100">
      <form
        className="w-96 flex flex-col items-center justify-center"
        action=""
        onSubmit={SubmitHandler}
      >
        <label htmlFor="title" className="text-2xl font-bold mb-2">
          Title
        </label>
        <input
          type="text"
          name="title"
          id="title"
          className="rounded-lg mb-2 text-center"
          value={Title}
          onChange={TitleHandler}
          required
        />
        <label htmlFor="date" className="mb-2 text-2xl font-bold">
          Date
        </label>
        <input
          type="date"
          name="date"
          id="date"
          className="rounded-lg mb-2 text-center"
          value={Date}
          onChange={DateHandler}
          required
        />
        <label htmlFor="details" className="text-2xl font-bold">
          Details
        </label>
        <input
          type="text"
          name="details"
          id="details"
          className="text-wrap rounded-lg h-40 w-64 mb-2 "
          value={Details}
          onChange={DetailsHandler}
          required
        />
        <button
          type="submit"
          className="SubmitButton text-gray-900 bg-gradient-to-r from-red-200 via-red-300 to-yellow-200 hover:bg-gradient-to-bl focus:ring-4 focus:outline-none focus:ring-red-100 dark:focus:ring-red-400 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
        >
          Add New Post
        </button>
      </form>
    </div>
  );
}

MakeReclamation.propTypes = {
  onCancel: PropTypes.func.isRequired,
  onAddNote: PropTypes.func.isRequired,
};

export default MakeReclamation;
