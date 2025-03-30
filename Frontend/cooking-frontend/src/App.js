import React, { useState } from "react";
import { BrowserRouter as Router, Route, Routes} from "react-router";

import './App.css';

import  CookingBlog from "./components/CookingBlog";
import  AddRecipe from "./components/AddRecipe";
import  UpdateRecipe from "./components/UpdateRecipe";
import Charts from "./components/Chart";


const initialRecipes = [
  { id: 1, title: "Creamy Ravioli", category: "Pasta", description: "Delicious ravioli with creamy sauce.",totaltime: 50, image: "https://i.pinimg.com/736x/06/0f/e5/060fe527e9aad9f7148b2378685d0e2a.jpg" },
  { id: 2, title: "Marry Me Chicken", category: "Meat", description: "Juicy chicken with flavorful sauce.",totaltime: 95, image: "http://i.pinimg.com/736x/cb/3f/51/cb3f512304bd0308d8f286c1a6be18d9.jpg" },
  { id: 3, title: "Lasagna", category: "Pasta", description: "Classic Italian lasagna.",totaltime: 150, image: "https://i.pinimg.com/736x/8f/0d/d4/8f0dd4d9f5381460cc6ae99c97960b23.jpg"},
  { id: 4, title: "Greek Salad", category: "Salads", description: "Healthy and delicious Greek salad.",totaltime: 20, image: "https://i.pinimg.com/736x/d1/e2/a9/d1e2a9c45ae8746c20b326c49414f663.jpg"},
  { id: 5, title: "Tiramisu", category: "Deserts", description: "Classic Italian dessert.",totaltime: 90, image: "https://i.pinimg.com/736x/48/5f/5e/485f5e8e40ce4996a24d2f5ea41ba367.jpg"},
  { id: 6, title: "Chicken Alfredo", category: "Pasta", description: "Creamy chicken alfredo pasta.",totaltime: 60, image: "https://i.pinimg.com/736x/f9/bb/a8/f9bba8fa2d1798b9d5d69afa7c163771.jpg"},
  { id: 7, title: "Caesar Salad", category: "Salads", description: "Classic Caesar salad.",totaltime: 30, image: "https://i.pinimg.com/736x/b2/bc/23/b2bc2347c177cd1d21b13b003c2d5ae9.jpg"},
  { id: 8, title: "Chocolate Cake", category: "Deserts", description: "Delicious chocolate cake.",totaltime: 150, image: "https://i.pinimg.com/736x/20/7d/26/207d2626197bc2edbc40d5165f726758.jpg"},
  { id: 9, title: "Fritto Misto", category: "Meat", description: "Delicious fried seafood.",totaltime: 120, image: "https://i.pinimg.com/736x/96/f7/93/96f7930dd034f17dd25759532038e549.jpg"},
  { id: 10, title: "Steak", category: "Meat", description: "Juicy steak with flavorful sauce.",totaltime: 90, image: "https://i.pinimg.com/736x/e0/24/b2/e024b26a7f553ad39331ed8414a3d910.jpg"}
];

export default function App() {
  const [recipes, setRecipes] = useState(initialRecipes);
  

  const handleAddRecipe = (newRecipe) => {
    setRecipes([...recipes, newRecipe]);
  };

  const handleUpdateRecipe = (updatedRecipe) => {
    setRecipes(recipes.map((r) => (r.id === updatedRecipe.id ? updatedRecipe : r)));
  };

  const handleDeleteRecipe = (id) => {
    if (window.confirm("Are you sure you want to delete this recipe?")) {
      setRecipes(recipes.filter((r) => r.id !== id));
    }
  };
  

  return (
    <Router>
      <Routes>
        <Route path="/" element={<CookingBlog recipes={recipes} onDelete={handleDeleteRecipe} />} />
        <Route path="/add-recipe" element={<AddRecipe onSave={handleAddRecipe} />} />
        <Route path="/update-recipe/:id" element={<UpdateRecipe recipes={recipes} onUpdate={handleUpdateRecipe} />} />
        <Route path="/chart" element={<Charts recipes={recipes} />} />
      </Routes>
    </Router>
  );
}