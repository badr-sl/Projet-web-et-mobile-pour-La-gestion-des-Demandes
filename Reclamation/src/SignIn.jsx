import { useState } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

function SignIn() {
  // State variables for form inputs
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [telephone, setTelephone] = useState("");
  const [Error, setError] = useState("");
  const navigateTo = useNavigate();
  

  // Function to handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    // Prepare form data
    const formData = {
      username: username,
      email: email,
      password: password,
      tel: telephone,
    };
    console.log(formData);

    // Make POST request to API
    try {
      const response = await fetch("http://localhost:9090/user/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });
      const data = await response.json();
      console.log(data);
      if(data.response == "400"){
        setError("Username is being used, please consider choosing another ❤️")
      }
      else{
        setError("")
      }
      if(data.response =="200"){
        localStorage.setItem("token",data.token)
        navigateTo("/home")
      }
      // Handle success or display any feedback to the user
    } catch (error) {
      console.error("Error:", error);
      // Handle error or display error message to the user
    }
  };

  return (
    <div className="flex flex-col items-center justify-center w-screen h-screen bg-slate-800">
      <form className="w-1/3 ring-2 p-8" onSubmit={handleSubmit}>
        {/* Input fields */}
        {/* Username */}
        <div className="relative z-0 w-full mb-5 group">
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
            placeholder="Username"
            required
          />
        </div>
        {/* Email */}
        <div className="relative z-0 w-full mb-5 group">
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
            placeholder="Email address"
            required
          />
        </div>
        {/* Password */}
        <div className="relative z-0 w-full mb-5 group">
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
            placeholder="Password"
            required
          />
        </div>
        {/* Telephone */}
        <div className="relative z-0 w-full mb-5 group">
          <input
            type="tel"
            value={telephone}

            onChange={(e) => setTelephone(e.target.value)}
            className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
            placeholder="Phone number (123-456-7890)"
            required
          />
          <p className="font-serif text-xl mt-5 text-white">
            you already have an account{" "}
            <span className="text-blue-400 hover:text-pink-400 animate-pulse hover:transition duration-1000">
              <Link to="/Login">Log In</Link>
            </span>
          </p>
        </div>
        {/* Submit button */}
        <button
          type="submit"
          className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        >
          Submit
        </button>
        {Error && <div className="text-red-500 mt-5 animate-pulse duration-1000">{Error}</div>}
      </form>
    </div>
  );
}

export default SignIn;
