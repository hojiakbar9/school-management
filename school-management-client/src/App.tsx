import { useEffect, useState } from "react";

function App() {
  const [message, setMessage] = useState("");

  useEffect(() => {
    fetch("/api/students")
      .then((res) => res.json())
      .then((data) => setMessage(JSON.stringify(data))); // stringify if it's an object/array
  }, []);
  return <p className="text-3xl font-bold m-4">{message}</p>;
}

export default App;
