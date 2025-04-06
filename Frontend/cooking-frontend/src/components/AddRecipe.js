import { useState } from "react";
import { useNavigate } from "react-router";
import { Button } from "./button";


function AddRecipe({ onSave }) {
    const [title, setTitle] = useState("");
    const [category, setCategory] = useState("Pasta");
    const [description, setDescription] = useState("");
    const [totalTime, setTotaltime] = useState(0);
    const [image, setImage] = useState("");
    const navigate = useNavigate();
    
    const handleSubmit = async () => {
      if (!title || !description || !image || !totalTime) {
        alert("Please fill in all fields.");
        return;
      }
    
      if (isNaN(totalTime) || totalTime < 0) {
        alert("Please enter a valid number for the total time.");
        return;
      }
    
      // Create the new recipe object
      const newRecipe = {
        id: Date.now(), // Use current timestamp as a unique ID
        title,
        category,
        description,
        totalTime: parseInt(totalTime, 10),
        image,
      };
    
      try {
        const response = await fetch("http://localhost:8080", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",  // Make sure this is correct
          },
          body: JSON.stringify(newRecipe), // Make sure you're sending an object, not an array
        });
    
        if (!response.ok) {
          throw new Error(`Failed to add recipe submit: ${response.statusText}`);
        }
    
        // Get the updated list of recipes from the response
        const updatedRecipes = await response.json();
    
        // Update the state in the parent component
        onSave(updatedRecipes);
    
        // Redirect to the homepage
        navigate("/");
    
      } catch (error) {
        console.error("Error adding recipe:", error);
        alert("There was an error adding the recipe. Please try again.");
      }
    };
    
    
  
    return (
      <div className="p-4">
        <h2 className="text-xl font-bold">Add Recipe</h2>
        <input className="w-full border p-2 my-2" placeholder="Title" value={title} onChange={(e) => setTitle(e.target.value)} />
        <br></br>
        <select className="w-full border p-2 my-2" value={category} onChange={(e) => setCategory(e.target.value)}>
          {['Meat', 'Salads', 'Pasta', 'Deserts'].map((cat) => (
            <option key={cat} value={cat}>{cat}</option>
          ))}
        </select>
        <br></br>
        <input className="w-full border p-2 my-2" placeholder="Description" value={description} onChange={(e) => setDescription(e.target.value)} />
        <br></br>
        <input className="w-full border p-2 my-2" placeholder="Total time" value={totalTime} onChange={(e) => setTotaltime(e.target.value)}></input>
        <input className="w-full border p-2 my-2" placeholder="Image URL" value={image} onChange={(e) => setImage(e.target.value)} />
        <br></br>
        <img src={image} alt={title} className="recipe-image" />
        <br></br>
        <Button onClick={handleSubmit} className="mr-2">Save</Button>
        <Button onClick={() => navigate("/")}>Cancel</Button>
      </div>
    );
  }

  export default AddRecipe;
  