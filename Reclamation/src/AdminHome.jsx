import Nav from "./Components/Nav";
import Reclamation from "./Components/Reclamation";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useEffect , useRef } from "react";
import Rejected from "./Components/Rejected";
import Approve from "./Components/Approve";
import SessionExpired from "./Components/SessionExpired";

function AdminHome() {
  const [reclamations, setReclamations] = useState([]);
  const navigate = useNavigate();

  const [isLoginFailVisible, setIsLoginFailVisible] = useState(false);
  const [isLoginSuccessVisible, setIsLoginSuccessVisible] = useState(false);
  const [isSessionExpired, SetisSessionExpired] = useState(false);
  const RejectedRef = useRef(null);
  const ApprovedRef = useRef(null);
  const SessionRef = useRef(null);

  const switchOn = (ref) => {
    console.log("test")
    if (ref === RejectedRef) {
      setIsLoginFailVisible(true);
    } else if (ref === ApprovedRef) {
      setIsLoginSuccessVisible(true);
    }
  };
  const switchOnSimple = () => {
    console.log("Session Expired")
      SetisSessionExpired(true);
  };

  const switchOff = () => {
    setIsLoginFailVisible(false);
    setIsLoginSuccessVisible(false);
  };

  useEffect(() => {
    const checkTokenValidity = async (token) => {
      try {
        const response = await fetch(
          `http://localhost:9090/checkToken?token=${token}`,
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
            },
          }
        );

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
        console.log(valid);
        if (valid) {
          // Token is valid, navigate to home
          //navigate("/home");
        } else {
          // Token is not valid, remove it and navigate to login
          switchOnSimple()
          const SessionTimer = setTimeout(() =>navigate("/Login") , 3500);
          localStorage.removeItem("token");

          
        }
      });
    } else {
      // Token not found in localStorage, navigate to login
      navigate("/Login");
    }
  }, []);

  //
  useEffect(() => {
    fetchDataFromApi();
  }, []);
  //Function to Fetch Data from My API
  const fetchDataFromApi = async () => {
    const token = localStorage.getItem("token");
    console.log(token);
    const response = await fetch("http://localhost:9090/demandes", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: token,
    });
    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.message || "Failed to fetch data");
    }
    console.log(data);
    setReclamations(data);
    return data;
  };
  //function to Reject Reclamation
  const rejectReclamation = async (id) => {
    console.log("test");
    try {
      const token = localStorage.getItem("token");
      console.log("id ===> " + id);
      const response = await fetch(
        `http://localhost:9090/reclamation/reject?id=${id}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: token, // Adjust as per your needs
        }
      );
      console.log(token);
      const data = await response.json();
      console.log("Response:", data);
      switchOn(RejectedRef) 
      const ApprovedTimer = setTimeout(() =>switchOff() , 3500); 

      // Perform any other actions based on the response, such as updating UI
    } catch (error) {
      console.error("Error:", error);
      // Handle error scenarios
    }
  };
  //function to Solve Reclamation
  const solveReclamation = async (id) => {
    console.log("test");
    try {
      const token = localStorage.getItem("token");
      console.log("id ===> " + id);
      const response = await fetch(
        `http://localhost:9090/reclamation/solve?id=${id}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: token, // Adjust as per your needs
        }
      );
      console.log(token);
      const data = await response.json();
      console.log("Response:", data);
      switchOn(ApprovedRef); 
      const ApprovedTimer = setTimeout(() =>switchOff() , 3500); 
      // Perform any other actions based on the response, such as updating UI
    } catch (error) {
      console.error("Error:", error);
      // Handle error scenarios
    }
  };

  //__________________________________________________________
  return (
    <>
      <Nav></Nav>
      <div
        className={`backdrop ${isSessionExpired ? "visible" : "hidden"}`}
        ref={SessionRef}
      >
        <div className="w-2/6 ml-96">
          <SessionExpired />
        </div>
      </div>
      <div
        className={`backdrop ${isLoginFailVisible ? "visible" : "hidden"}`}
        ref={RejectedRef}
      >
        <div className="w-2/6 ml-96">
          <Rejected />
        </div>
      </div>
      <div
        className={`backdrop ${isLoginSuccessVisible ? "visible" : "hidden"}`}
        ref={ApprovedRef}
      >
        <div className="w-2/6 ml-96">
          <Approve />
        </div>
      </div>
      <div className="mt-16 flex justify-center items-center w-screen h-full">
        <div className="rounded-xl mydiv grid grid-cols-4 gap-5 p-4 border-solid border-2 border-blue-900 mt-2 ml-2">
          {reclamations.map((reclamation) => (
            <Reclamation
              key={reclamation.idDemande}
              id={reclamation.idDemande}
              title={reclamation.title}
              date={reclamation.date}
              details={reclamation.sujet}
              status={reclamation.etat}
            >
              <div className="grid grid-cols-2 gap-5 w-full">
                <button
                  className="ring-1 bg-cyan-200 rounded-2xl self-end hover:ring-white"
                  onClick={() => solveReclamation(reclamation.idDemande)}
                >
                  Solve!
                </button>

                <button
                  className="ring-1 bg-red-400 rounded-2xl self-end hover:ring-white"
                  onClick={() => rejectReclamation(reclamation.idDemande)}
                >
                  Reject!
                </button>
              </div>
            </Reclamation>
          ))}
        </div>
      </div>
    </>
  );
}
export default AdminHome;
