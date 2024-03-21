import react, { useEffect, useState } from "react";
import { request, setAuthHeader } from "../services/axios_helper";
import Button from "@mui/material/Button";
import { useNavigate } from "react-router-dom";

export default function HomePage() {
  const nav = useNavigate();

  const [message, setMessage] = useState("");

  useEffect(() => {
    request("GET", "/message", {})
      .then((response) => {
        setMessage(response.data);
      })
      .catch((err) => {
        if (err.response.status === 401) {
          setAuthHeader(null);
        } else {
          setMessage(err.response.code);
        }
      });
  }, []);

  const handleLogout = () => {
    nav("/");
    setAuthHeader(null);
  };

  return (
    <div>
      <h1>Hello Admin</h1>
      <p>{message}</p>

      <Button
        onClick={handleLogout}
        variant="contained"
        color="error"
        sx={{ mt: 3, mb: 2 }}
      >
        Logout
      </Button>
    </div>
  );
}
