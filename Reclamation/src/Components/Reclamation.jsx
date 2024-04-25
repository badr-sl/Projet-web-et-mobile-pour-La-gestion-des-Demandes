import PropTypes from "prop-types"; // Import PropTypes

function Reclamation(props) {
  function formatDate(dateString) {
    const date = new Date(dateString);
    const day = date.getDate().toString().padStart(2, "0");
    const month = (date.getMonth() + 1).toString().padStart(2, "0"); // Months are zero-based
    const year = date.getFullYear();

    return `${day}/${month}/${year}`;
  }

  let backgroundColor;

  switch (props.status) {
    case "Pending":
      backgroundColor = "bg-orange-200";
      break;
    case "Solved":
      backgroundColor = "bg-green-200";
      break;
    case "Rejected":
      backgroundColor = "bg-red-200";
      break;
    case "pending":
      backgroundColor = "bg-orange-200";
      break;
    case "solved":
      backgroundColor = "bg-green-200";
      break;
    case "rejected":
      backgroundColor = "bg-red-200";
      break;
    default:
      backgroundColor = ""; // Default background color
  }

  return (
    <>
      <div
        className={`custom-scrollbar rounded-xl ${backgroundColor} font-serif p-2 w-60 border-orange-950 border`}
      >
        <div className="border-solid border-2 border-black">
          <h2 className="text-center font-bold font-serif">{props.title}</h2>
          <div className="text-center">{formatDate(props.date)}</div>
        </div>
        <span className="font-bold text-2xl">{props.status}</span>
        <div className="mt-auto flex justify-center">{props.children}</div>
        <p className="mt-3">{props.details}</p>
      </div>
    </>
  );
}

// Default props:
Reclamation.defaultProps = {
  title: "Default Title",
  date: "Default Date",
  status: "Pending",
  details:
    "Lorem ipsum dolor sit amet consectetur adipisicing elit. Fugiat dignissimos molestias eius nostrum voluptate veniam temporibus quisquam libero nisi veritatis, sequi nesciunt ipsa quam animi cumque obcaecati labore eos voluptas!",
};

Reclamation.propTypes = {
  title: PropTypes.string,
  date: PropTypes.string,
  details: PropTypes.string,
  status: PropTypes.oneOf(["Pending", "Solved", "Rejected"]),
};

export default Reclamation;
