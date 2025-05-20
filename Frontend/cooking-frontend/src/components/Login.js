import React, { useState } from "react";
import { Button, Form } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";

function LogIn() {
	const [name, setName] = useState("");
	const [password, setPassword] = useState("");
	const history = useNavigate();

	const authenticate = async (e) => {
		e.preventDefault();

		if (name === "" || password === "") {
			alert("Invalid input");
			return;
		}

        const login_data = { username: name, password: password };
		try {
            const response = await axios.post("http://16.171.44.125:8080/users/login", login_data, {
				headers: { "Content-Type": "application/json" },
	
			});
        
            // Check if the response status is successful
            if (response.status !== 200) {
                throw new Error(response.data.error || "Login failed. Please check your credentials.");
            }
			console.log(response.data);
			if(response.data===1){//the user is admin
				alert("Admin login successful!");
				localStorage.setItem("isAdmin", true);
				history("/home");
			}
			else{
				alert("Login successful!");
				localStorage.setItem("isAdmin", false);
				localStorage.setItem("userId", response.data.id);
            // Handle successful login response (e.g., redirect, save token, etc.)
           		history("/home");
			}  // Example redirect
		} catch (error) {
			alert(error.response.data.error || "Login failed. Please check your credentials.");
			window.location.reload();
		}
	};

	return (
		<div>
			<Form className="d-grid gap-2" style={{ margin: "5rem" }}>
				<Form.Group className="mb-3" controlId="formBasicName" data-cy="formname">
					<Form.Control onChange={(e) => setName(e.target.value)} type="text" placeholder="Enter Name" required />
				</Form.Group>

				<Form.Group className="mb-3" controlId="formBasicPassword">
					<Form.Control onChange={(e) => setPassword(e.target.value)} type="password" placeholder="Password" required />
				</Form.Group>

				<Button onClick={authenticate} variant="primary" type="submit">
					Submit
				</Button>

				<Link className="d-grid gap-2" to="/signup">
					<Button variant="warning" size="lg">
						Sign Up!
					</Button>
				</Link>
			</Form>
		</div>
	);
}

export default LogIn;