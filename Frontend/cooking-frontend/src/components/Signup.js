import axios from "axios";
import React, { useState } from "react";
import { Button, Form } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";

function Authenticate() {
	const [name, setname] = useState("");
	const [password, setPassword] = useState("");

	let history = useNavigate();

	async function register(e) {
		e.preventDefault();

		if (name === "" || password === "") {
			alert("invalid input");
			setname("");
			setPassword("");
			window.location.reload();
			return;
		}

        const signup_data = { username: name, password: password };
		try {
            const response = await axios.post("http://localhost:8080/users/register", signup_data, {
                headers: { "Content-Type": "application/json" },
				withCredentials: true
            });
        
            // Access the data directly from response.data
            if (response.status !== 200) {
                // If response is not successful, throw an error
                throw new Error(response.data.error || "Registration failed. Please try again.");
            }

			history("/home");
		} catch (error) {
			alert(error.message);
			setname("");
			setPassword("");
			window.location.reload();
		}
	}

	return (
		<div>
			<Form className="d-grid gap-2" style={{ margin: "5rem" }}>
				<Form.Group className="mb-3" controlId="formBasicName">
					<Form.Control onChange={(e) => setname(e.target.value)} type="text" placeholder="Enter Name" required />
				</Form.Group>

				<Form.Group className="mb-3" controlId="formBasicPassword">
					<Form.Control onChange={(e) => setPassword(e.target.value)} type="password" placeholder="Password" required />
				</Form.Group>

				<Button onClick={register} variant="primary" type="submit">
					Submit
				</Button>

				<Link className="d-grid gap-2" to="/">
					<Button variant="info" size="lg">
						Log in
					</Button>
				</Link>
			</Form>
		</div>
	);
}

export default Authenticate;