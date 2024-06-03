import { useState } from "react";
import "../index.css";
import axios from "axios";

function MakeAction(props) {
  const [motifstate, setmotif] = useState("");

  const handleMotifChange = (event) => {
    setmotif(event.target.value);
  };
  const motifobj = {
    motif: { motifstate },
  };

  const handleSolveAxios = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("token");
      const response = await axios.post(
        "http://localhost:9090/reclamation/newReject",
        {
          token,
          id: props.id,
          motif: motifobj, // Assuming motif is an object with a 'motif' property
        }
      );
      console.log(response.data); // Handle the response as needed
    } catch (error) {
      console.error("Error:", error);
    }
  };
  
  const handleSolve = async () => {
    try {
      const token = localStorage.getItem("token");
      const id = props.id; // Assuming id is a parameter
      const obj = {
        motif: motifstate,
      };
  
      const response = await fetch(
        `http://localhost:9090/reclamation/newSolve?token=${token}&id=${id}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(obj), // Convert obj to JSON string
        }
      );
  
      console.log(token);
      const data = await response.json();
      console.log("Response:", data);
      props.fetchDataFromApi()
      // Perform any other actions based on the response, such as updating UI
    } catch (error) {
      console.error("Error:", error);
      // Handle error scenarios
    }
  };
  const handleReject = async () => {
    try {
      const token = localStorage.getItem("token");
      const id = props.id; // Assuming id is a parameter
      const obj = {
        motif: motifstate,
      };
  
      const response = await fetch(
        `http://localhost:9090/reclamation/newReject?token=${token}&id=${id}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(obj), // Convert obj to JSON string
        }
      );
  
      console.log(token);
      const data = await response.json();
      console.log("Response:", data);
      props.fetchDataFromApi()
      // Perform any other actions based on the response, such as updating UI
    } catch (error) {
      console.error("Error:", error);
      // Handle error scenarios
    }
  };
  
  return (
    <>
      <div className="container rounded-2xl ">
        <p className="font-mono text-2 p-4">
          make action that seems correct for you{" "}
        </p>
        <div className="">
          <p className="font-mono text-2 p-4">
            you can Solve or Reject after justificating here😊 :{" "}
          </p>
          <textarea
            name=""
            id=""
            cols="30"
            rows="10"
            className="border-2 border-black rounded-2xl w-7/12"
            value={motifstate}
            onChange={handleMotifChange}
          ></textarea>

          <div className="flex gap-5 justify-center">
            <button
              className="border-2 border-white p-4 rounded-2xl bg-green-500 text-white font-bold  hover:text-green-500 hover:bg-white duration-1000 hover:border-green-500"
              onClick={handleSolve}
            >
              Solve !
            </button>
            <button className="border-2 p-4 rounded-2xl bg-red-500 text-white font-bold  hover:text-red-500 hover:bg-white duration-1000 hover:border-red-500 "
            onClick={handleReject}>
              Reject !
            </button>
          </div>
        </div>

        <p></p>
      </div>
    </>
  );
}

export default MakeAction;
