import React, { useEffect, useState } from "react";
import axios from "axios";

function MonitoredUsersPage() {
	const [monitoredUsers, setMonitoredUsers] = useState([]);
	const [error, setError] = useState(null);

	useEffect(() => {
		axios.get("http://localhost:8080/admin/monitored-users", {
			withCredentials: true,
		})
		.then(res => setMonitoredUsers(res.data))
		.catch(err => {
			setError("❌ You are not authorized to view this page.");
			console.error(err);
		});
	}, []);

	if (error) {
		return <div>{error}</div>;
	}

	if (monitoredUsers.length === 0) {
		return <div>No suspicious users detected ✅</div>;
	}

	return (
		<div>
			<h2>🚨 Monitored Users</h2>
			<table border="1">
				<thead>
					<tr>
						<th>User ID</th>
						<th>Detected At</th>
					</tr>
				</thead>
				<tbody>
					{monitoredUsers.map(mu => (
						<tr key={mu.userId}>
							<td>{mu.userId}</td>
							<td>{mu.detectedAt}</td>
						</tr>
					))}
				</tbody>
			</table>
		</div>
	);
}

export default MonitoredUsersPage;
