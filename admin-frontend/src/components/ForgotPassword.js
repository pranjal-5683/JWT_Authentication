import { useState } from "react";
import { request } from "../services/axios_helper";
import {
  Container,
  CssBaseline,
  Avatar,
  Typography,
  TextField,
  Button,
  Box,
  Grid,
  Link,
} from "@mui/material";
import LockResetIcon from "@mui/icons-material/LockReset";
import { createTheme, ThemeProvider } from "@mui/material/styles";

const defaultTheme = createTheme();

export default function ForgotPassword() {
  const [email, setEmail] = useState("");
  const [message, setMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await request("POST", "/forgot-password", {
        email: email,
      });
      if (response.status === 200) {
        setMessage("Please check your email for the reset password link.");
      } else {
        setMessage("Something went wrong. Please try again.");
      }
    } catch (error) {
      console.error("Forgot Password error:", error);
      setMessage("Error sending reset password email. Please try again.");
    }
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <LockResetIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Forgot Password
          </Typography>
          <Box component="form" onSubmit={handleSubmit} sx={{ mt: 1 }}>
            <br/>
            {message && (
              <Typography
                color={message.startsWith("Please") ? "primary" : "error"}
                textAlign="center"
              >
                {message}
              </Typography>
            )}
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email Address"
              name="email"
              autoComplete="email"
              autoFocus
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Send Reset Link
            </Button>
            <Grid container>
              <Grid item xs>
                <Link href="/" variant="body2" underline="none">
                  Remember your password? Sign in
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
