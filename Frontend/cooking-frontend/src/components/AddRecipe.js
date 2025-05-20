import { useState } from "react";
import { useNavigate } from "react-router";
import { Button } from "./button";


function AddRecipe({ onSave }) {
    const [title, setTitle] = useState("");
    const [category, setCategory] = useState("Pasta");
    const [description, setDescription] = useState("");
    const [totalTime, setTotaltime] = useState(0);
    const [image, setImage] = useState("");
    const [file, setFile] = useState(null);
    const [ingredients, setIngredients] = useState([{ name: "", quantity: "", unit: "" }]);

    const navigate = useNavigate();
    
    
    const handleFileChange = (e) => {
      setFile(e.target.files[0]);
      
    }

    const handleIngredientChange = (index, field, value) => {
      const newIngredients = [...ingredients];
      newIngredients[index][field] = value;
      setIngredients(newIngredients);
    };
    
    const addIngredient = () => {
      setIngredients([...ingredients, { name: "", quantity: "" }]);
    };
    
    const removeIngredient = (index) => {
      const newIngredients = ingredients.filter((_, i) => i !== index);
      setIngredients(newIngredients);
    };
    
    // Handle form submission
    const handleSubmit = async () => {
      if (!title || !description || (!image && !file) || !totalTime) {
        alert("Please fill in all fields.");
        return;
      }
    
      if (isNaN(totalTime) || totalTime < 0) {
        alert("Please enter a valid number for the total time.");
        return;
      }
      let uploadedFileUrl = image;

    // If a file is selected, upload it
    if (file) {
      const formData = new FormData();
      formData.append("file", file);

      try {
        const uploadResponse = await fetch("http://16.171.44.125:8080/upload", {
          method: "POST",
          body: formData,
        });

        if (!uploadResponse.ok) {
          throw new Error(`Failed to upload file: ${uploadResponse.statusText}`);
        }

        const uploadData = await uploadResponse.json();
        uploadedFileUrl = uploadData.url; 
      } catch (error) {
        console.error("Error uploading file:", error);
        alert("There was an error uploading the file. Please try again.");
        return;
      }
    }

    
      // Create the new recipe object
      const newRecipe = {
        id: parseInt(Date.now()), // Use a timestamp as a temporary ID
        title,
        category,
        description,
        totalTime: parseInt(totalTime, 10),
        image: uploadedFileUrl, // Use the uploaded file's URL
        recipeIngredients: ingredients.map(i => ({
          ingredient: {
            name: i.name,
            unit: i.unit,
          },
          quantity: i.quantity.toString(),
        }))
      };
    
      try {
        const response = await fetch("http://16.171.44.125:8080", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",  
          },
          body: JSON.stringify(newRecipe), 
          withCredentials: true
        });
        console.log(newRecipe);
        console.log(response);
        if (!response.ok) {
          throw new Error(`Failed to add recipe submit: ${response.statusText}`);
        }
    
        // Get the updated list of recipes from the response
        const updatedRecipes = await response.json();
    
        // Update the state in the parent component
        onSave(updatedRecipes);
    
        // Redirect to the homepage
        navigate("/home");
    
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
          {['Meat', 'Salads', 'Pasta', 'Deserts', 'Soup'].map((cat) => (
            <option key={cat} value={cat}>{cat}</option>
          ))}
        </select>
        <br></br>
        <input className="w-full border p-2 my-2" placeholder="Description" value={description} onChange={(e) => setDescription(e.target.value)} />
        <br></br>
        <input className="w-full border p-2 my-2" placeholder="Total time" value={totalTime} onChange={(e) => setTotaltime(e.target.value)}></input>
        <input className="w-full border p-2 my-2" placeholder="Image URL" value={image} onChange={(e) => setImage(e.target.value)} />
        <br />
        <input
          type="file"
          className="w-full border p-2 my-2"
          onChange={handleFileChange}
        />
        <br />{file && <p>Selected file: {file.name}</p>}<br />
        <h3 className="font-semibold mt-4">Ingredients</h3>
        {ingredients.map((ing, index) => (
          <div key={index} className="flex gap-2 mb-2">
            <input
              className="flex-1 border p-2"
              placeholder="Name"
              value={ing.name}
              onChange={(e) =>
                handleIngredientChange(index, "name", e.target.value)
              }
            />
            <input
              className="w-24 border p-2"
              placeholder="Quantity"
              value={ing.quantity}
              onChange={(e) =>
                handleIngredientChange(index, "quantity", e.target.value)
              }
            />
            <input
              className="w-24 border p-2"
              placeholder="Unit"
              value={ing.unit}
              onChange={(e) => handleIngredientChange(index, "unit", e.target.value)}
            />
            <button onClick={() => removeIngredient(index)} className="text-red-500">✕</button>
          </div>
        ))}
        <Button onClick={addIngredient} className="mb-4">Add Ingredient</Button>

        <Button onClick={handleSubmit} className="mr-2">Save</Button>
        <Button onClick={() => navigate("/home")}>Cancel</Button>
    
      </div>
    );
  }

  export default AddRecipe;
  