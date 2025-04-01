import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Routes} from "react-router";

import './App.css';

import  CookingBlog from "./components/CookingBlog";
import  AddRecipe from "./components/AddRecipe";
import  UpdateRecipe from "./components/UpdateRecipe";
import Charts from "./components/Chart";



export default function App() {
  const [recipes, setRecipes] = useState([]);

  useEffect(()=>{
    const fetchdata = async () => {
      try{
      const response=await fetch("http://localhost:8080");
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
  

  const handleAddRecipe = (newRecipes) => {
      setRecipes(newRecipes); // Update the state with the updated list of recipes
  };
  
  const handleUpdateRecipe = (updatedRecipes) => {
    setRecipes(updatedRecipes); // Update the state with the updated list of recipes
  };

  const handleDeleteRecipe = async (id) => {
    // if (window.confirm("Are you sure you want to delete this recipe?")) {
    //   setRecipes(recipes.filter((r) => r.id !== id));
    // }
    if (window.confirm("Are you sure you want to delete this recipe?")) {
        const response = await fetch(`http://localhost:8080/${id}`, {
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
    <>
      {recipes.length === 0 ? (
        <div>Loading...</div>
      ) : (
        <Router>
          <Routes>
            <Route path="/" element={<CookingBlog recipes={recipes} onDelete={handleDeleteRecipe} />} />
            <Route path="/add-recipe" element={<AddRecipe onSave={handleAddRecipe} />} />
            <Route path="/update-recipe/:id" element={<UpdateRecipe recipes={recipes} onUpdate={handleUpdateRecipe} />} />
            <Route path="/chart" element={<Charts recipes={recipes} />} />
          </Routes>
        </Router>
      )}
    </>
  );
  
}