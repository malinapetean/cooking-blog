import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Routes} from "react-router";
import './App.css';


import CookingBlog from "./components/CookingBlog";
import AddRecipe from "./components/AddRecipe";
import UpdateRecipe from "./components/UpdateRecipe";
import Charts from "./components/Chart";
import StatusBanner from "./components/StatusBanner";
import { useNetworkStatus } from "./ServerChecking";
import LogIn from "./components/Login";
import Authenticate from "./components/Signup";
import MonitoredUsersPage from "./components/Admin"; 




export default function App() {
  const [recipes, setRecipes] = useState([]);
  
  const { isOnline, isServerUp } = useNetworkStatus("http://16.171.44.125:8080/ping");
  
  
  useEffect(()=>{
    const fetchdata = async () => {
      try{
      const response=await fetch("http://16.171.44.125:8080/all-recipes");
      const data= await response.json();
      setRecipes(data);
      console.log(data);

      }
      catch(error){
        console.log(error);
      }
    }
    fetchdata();
  },[])

  useEffect(() => {
    const socket = new WebSocket("ws://16.171.44.125:8080/ws/recipes");
  
    socket.onopen = () => {
      console.log("Connected to WebSocket");
    };
  
    socket.onmessage = (event) => {
      const newRecipe = JSON.parse(event.data);
      console.log("Received recipe:", newRecipe);
      // Update your state here
      // For example, if you want to add the new recipe to the list:
      setRecipes((prevRecipes) => [...prevRecipes, newRecipe]);
    };
  
    socket.onclose = () => {
      console.log("WebSocket disconnected");
    };
  
    return () => {
      socket.close();
    };
  }, []);
  
  

  const handleAddRecipe = (newRecipes) => {
      setRecipes(newRecipes); // Update the state with the updated list of recipes
  };
  
  const handleUpdateRecipe = (updatedRecipes) => {
    setRecipes(updatedRecipes); // Update the state with the updated list of recipes
  };

  const handleDeleteRecipe = async (id) => {
    if (window.confirm("Are you sure you want to delete this recipe?")) {
        const response = await fetch(`http://16.171.44.125:8080/${id}`, {
          method: "DELETE",
        });
        if (response.ok) {
          const updatedRecipes = await response.json(); // Get the updated list of recipes from the response
          setRecipes(updatedRecipes); // Update the state with the updated list of recipes
          
        } else {
          alert("There was an error deleting the recipe. Please try again.");
        }
    }
  };

  return (
  
        <Router>
          <StatusBanner isOnline={isOnline} isServerUp={isServerUp}  />
          <Routes>
            <Route path="" element={<LogIn />} />
            <Route path="/signup" element={<Authenticate />} />
            <Route path="/home" element={<CookingBlog recipes={recipes} onDelete={handleDeleteRecipe} />} />
            <Route path="/add-recipe" element={<AddRecipe onSave={handleAddRecipe} />} />
            <Route path="/update-recipe/:id" element={<UpdateRecipe recipes={recipes} onUpdate={handleUpdateRecipe} />} />
            <Route path="/chart" element={<Charts recipes={recipes} />} />
            <Route path="/admin/monitored-users" element={<MonitoredUsersPage />} />
          </Routes>
        </Router>
      
  );
}
   
  
  
