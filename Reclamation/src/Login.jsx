import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useEffect, useRef } from "react";
import { Link } from "react-router-dom";
import Succes from "./Components/Succes";
import Fail from "./Components/Fail";

function Login() {
  const errorRef = useRef(null);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigateTo = useNavigate();

  const [isLoginFailVisible, setIsLoginFailVisible] = useState(false);
  const [isLoginSuccessVisible, setIsLoginSuccessVisible] = useState(false);
  const loginFailRef = useRef(null);
  const loginSuccessRef = useRef(null);

  const switchOn = (ref) => {
    console.log("test")
    if (ref === loginFailRef) {
      setIsLoginFailVisible(true);
    } else if (ref === loginSuccessRef) {
      setIsLoginSuccessVisible(true);
    }
  };
  const switchOnSimple = () => {
    console.log("dkhel hna")
      setIsLoginFailVisible(true);
  };

  const switchOff = () => {
    setIsLoginFailVisible(false);
    setIsLoginSuccessVisible(false);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch("http://localhost:9090/users/authenticate", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });

      if (!response.ok) {
        throw new Error("Invalid credentials");
      }

      const data = await response.json();
      localStorage.setItem("token", data.token);
      setError("");
      console.log(data);
      if (data.role == "error") {
        console.log("503 error fail");
        switchOnSimple();
        setError("Invalid Credentials")

        const waitToTurnOff = setTimeout(() => switchOff(loginFailRef.current), 2000); //2s
        console.log("503 error");
      }
      if (data.role == "client") {
        console.log("succes");
        switchOn(loginSuccessRef);
        const waitToSwitch = setTimeout(() => navigateTo("/home"), 3500); //3.5s
      }
      if (data.role == "admin") {
        console.log("succes");
        switchOn(loginSuccessRef);
        const waitToSwitch = setTimeout(() => navigateTo("/AdminHome"), 3500); //3.5s
      }
    } catch (error) {
      setError(error.message);
    }
  };
    //switchOn(loginFailRef)
    console.log(isLoginFailVisible,"status")

  return (
    <div>
      <div
        className={`backdrop ${isLoginFailVisible ? "visible" : "hidden"}`}
        ref={loginFailRef}
      >
        <div className="w-2/6 ml-96">
          <Fail />
        </div>
      </div>
      <div
        className={`backdrop ${isLoginSuccessVisible ? "visible" : "hidden"}`}
        ref={loginSuccessRef}
      >
        <div className="w-2/6 ml-96">
          <Succes />
        </div>
      </div>
      <div className="bg-slate-800 w-screen h-screen flex justify-center items-center">
        <form
          className="max-w-sm mx-auto border-2 p-10 w-screen"
          onSubmit={handleSubmit}
        >
          <div className="mb-5">
            <label
              htmlFor="email"
              className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
            >
              Your email
            </label>
            <input
              type="email"
              id="email"
              className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="name@email.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="mb-5">
            <label
              htmlFor="password"
              className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
            >
              Your password
            </label>
            <input
              type="password"
              id="password"
              placeholder="**********"
              className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          {error && <div className="text-red-500 mb-3"></div>}
          <p className="font-serif text-l mt-5 text-white mb-4">
            This is your first time ?{" "}
            <span className="text-blue-400 hover:text-pink-400 animate-pulse hover:transition duration-1000">
              <Link to="/SignIn">Sign In</Link>
            </span>
          </p>
          <button
            type="submit"
            className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          >
            Submit
          </button>
          <div className="flex justify-center items-center mt-4 p-2">
            <p className="text-red-500 font-bold text-xl">{error}</p>
          </div>
        </form>
      </div>{" "}
    </div>
  );
}

export default Login;
