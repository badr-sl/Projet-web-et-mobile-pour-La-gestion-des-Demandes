import Logo from "../assets/A43.png";
import PropTypes from "prop-types";
import "../index.css"; // Import your CSS file

function Nav(props) {
  return (
    <div className="absolute flex justify-between w-screen top-0 h-16 font-bold bg-gradient-to-r from-orange-200 via-stone-200 to-teal-100">
      <img src={Logo} alt="Logo" className="h-full" />{" "}
      <div  className="flex justify-center items-center text-4xl" style={{ fontFamily: '"Ojuju", sans-serif', fontSize: "512" }}>
        Reclamation Manager
      </div>
      <button
        className="flex justify-center items-center fmr-4 w-1/6 rounded-xl border-4 border-solid border-orange-200"
        onClick={props.onShow}
      >
        Add New !
      </button>
    </div>
  );
}

Nav.propTypes = {
  onShow: PropTypes.func.isRequired,
};

export default Nav