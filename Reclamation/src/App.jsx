import React, { useState, useEffect } from "react";
import "./App.css";
import Modal from "./Components/Modal";
import MakeReclamation from "./Components/MakeReclamation";
import Nav from "./Components/Nav";
import Reclamation from "./Components/Reclamation";
import { useNavigate } from "react-router-dom";
function App() {
  const [visibility, setVisibility] = useState(false);
  const [reclamations, setReclamations] = useState([]);
  const navigate = useNavigate()

  useEffect(() => {
    const checkTokenValidity = async (token) => {
      try {
        const response = await fetch(`http://localhost:9090/checkToken?token=${token}`, {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        });

        if (!response.ok) {
          throw new Error("Network response was not ok");
        }

        const data = await response.json();
        console.log("Token validity response:", data);

        return data; // This will be a boolean value
      } catch (error) {
        console.error("There was a problem checking token validity:", error);
        return false;
      }
    };

    const token = localStorage.getItem("token");
    if (token) {
      checkTokenValidity(token).then((valid) => {
        console.log(valid)
        if (valid) {
          // Token is valid, navigate to home
          //navigate("/home");
        } else {
          // Token is not valid, remove it and navigate to login
          localStorage.removeItem("token");
          navigate("/Login");
        }
      });
    } else {
      // Token not found in localStorage, navigate to login
      navigate("/Login");
    }
  }, []);

  useEffect(() => {
    fetchDataFromApi();
  }, []);

  const fetchDataFromApi = async () => {
    try {
      // Get the token from localStorage
      const token = localStorage.getItem("token");
      console.log(token)
      // Check if token exists
      if (!token) {
        throw new Error("Token not found in localStorage");
      }
      console.log("bug")
  
      const response = await fetch("http://localhost:9090/reclamation", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          // Include the token in the Authorization header
          Authorization: `Bearer ${token}`,
        },
        body: token,
      });
  
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
  
      const data = await response.json();
      setReclamations(data);
      console.log("Data from API:", data);
      return data;
    } catch (error) {
      console.error("There was a problem fetching data from the API:", error);
      return null;
    }
  };
  
  const addNoteHandle = async (noteData) => {
    const token = localStorage.getItem("token"); // Assuming token is stored locally after login
    const requestOptions = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(noteData), // Send noteData as the request body
    };
  
    try {
      const response = await fetch(
        `http://localhost:9090/demandes/add?token=${token}`, // Include the token as a URL query parameter
        requestOptions
      );
  
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
  
      const data = await response.json();
      console.log("Note added successfully:", data);
      fetchDataFromApi();
    } catch (error) {
      console.error("There was a problem adding the note:", error);
    }
  };
  
  
  

  const switchVisibilityOn = () => {
    setVisibility(true);
  };

  const switchVisibilityOff = () => {
    setVisibility(false);
  };

  return (
    <div>
      <Nav onShow={switchVisibilityOn} />
      {visibility && (
        <Modal onClose={switchVisibilityOff}>
          <MakeReclamation
            onCancel={switchVisibilityOff}
            onAddNote={addNoteHandle}
          />
        </Modal>
      )}
      <div className="mt-16 flex justify-center items-center w-screen h-full">
        <div className="rounded-xl mydiv grid grid-cols-4 gap-5 p-4 border-solid border-2 border-blue-900 mt-2 ml-2">
          {reclamations.map((reclamation) => (
            <Reclamation
              key={reclamation.idDemande}
              title={reclamation.title}
              date={reclamation.date}
              details={reclamation.sujet}
              status={reclamation.etat}
            />
          ))}
        </div>
      </div>
    </div>
  );
}

export default App;
