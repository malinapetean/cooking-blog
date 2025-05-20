import { useState , useEffect} from "react";
import { useNavigate, useParams } from "react-router";
import { Button } from "./button";


function UpdateRecipe({ recipes, onUpdate }) {
    const { id } = useParams();
    const recipe = recipes.find((r) => r.id === parseInt(id));
    const [title, setTitle] = useState(recipe.title);
    const [category, setCategory] = useState(recipe.category);
    const [description, setDescription] = useState(recipe.description);
    const [totaltime, setTotaltime] = useState(recipe.totalTime);
    const [image, setImage] = useState(recipe.image);
    const [file, setFile] = useState(null);
    const navigate = useNavigate();

    const [ingredients, setIngredients] = useState([]);

    useEffect(() => {
      if (recipe && recipe.recipeIngredients) {
        setIngredients(
          recipe.recipeIngredients.map((ri) => ({
            name: ri.ingredient.name,
            unit: ri.ingredient.unit,
            quantity: ri.quantity,
          }))
        );
      }
    }, [recipe]);
    
    const handleIngredientChange = (index, field, value) => {
      const newIngredients = [...ingredients];
      newIngredients[index][field] = value;
      setIngredients(newIngredients);
    };
    
    const addIngredient = () => {
      setIngredients([...ingredients, { name: "", unit: "", quantity: "" }]);
    };
    
    const removeIngredient = (index) => {
      const newIngredients = ingredients.filter((_, i) => i !== index);
      setIngredients(newIngredients);
    };
    

    const handleFileChange = (e) => {
      setFile(e.target.files[0]); // Store the selected file
    };
  
    
    const handleSubmit =  async () => {
      if (!title || !description || (!image && !file) || !totaltime) {
        alert("Please fill in all fields.");
        return;
      }
      if(isNaN(totaltime) || totaltime < 0){
        alert("Please enter a number for the total time.");
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
          withCredentials: true
        });

        if (!uploadResponse.ok) {
          throw new Error(`Failed to upload file: ${uploadResponse.statusText}`);
        }

        const uploadData = await uploadResponse.json();
        uploadedFileUrl = uploadData.url; // Get the uploaded file's URL
      } catch (error) {
        console.error("Error uploading file:", error);
        alert("There was an error uploading the file. Please try again.");
        return;
      }
    }
    const updatedRecipe = {
      id: parseInt(id),
      title,
      category,
      description,
      totalTime: parseInt(totaltime, 10),
      image: uploadedFileUrl, // Use the uploaded file's URL
      recipeIngredients: ingredients.map((ing) => ({
        quantity: ing.quantity,
        ingredient: {
          name: ing.name,
          unit: ing.unit,
        },
      })),
    };

    console.log(updatedRecipe);

    try {
      const response = await fetch(`http://16.171.44.125:8080/${id}`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(updatedRecipe),
        withCredentials: true
      });

      if (!response.ok) {
        throw new Error(`Failed to update recipe: ${response.statusText}`);
      }

      const updatedRecipes = await response.json();
      onUpdate(updatedRecipes);
      navigate("/home");
    } catch (error) {
      console.error("Error updating recipe:", error);
      alert("There was an error updating the recipe. Please try again.");
    }
  };
  
    return (
      <div className="p-4">
        <h2 className="text-xl font-bold">Update Recipe</h2>
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
        <input className="w-full border p-2 my-2" placeholder="Total time" value={totaltime} onChange={(e) => setTotaltime(e.target.value)}></input>
        <input className="w-full border p-2 my-2" placeholder="Image URL" value={image} onChange={(e) => setImage(e.target.value)} />
        <br />
      <input
        type="file"
        className="w-full border p-2 my-2"
        onChange={handleFileChange}
      />
      <br />
      {file && <p>Selected file: {file.name}</p>}
      <br />
      <img src={image} alt={title} className="recipe-image" />
      <br />
      <h3 className="text-lg font-bold mt-4">Ingredients</h3>
      {ingredients.map((ingredient, index) => (
        <div key={index} className="flex gap-2 my-2">
          <input
            className="flex-1 border p-2"
            placeholder="Name"
            value={ingredient.name}
            onChange={(e) => handleIngredientChange(index, "name", e.target.value)}
          />
          <input
            className="w-24 border p-2"
            placeholder="Quantity"
            value={ingredient.quantity}
            onChange={(e) => handleIngredientChange(index, "quantity", e.target.value)}
          />
          <input
            className="w-24 border p-2"
            placeholder="Unit"
            value={ingredient.unit}
            onChange={(e) => handleIngredientChange(index, "unit", e.target.value)}
          />
          <Button onClick={() => removeIngredient(index)}>Remove</Button>
        </div>
      ))}
      <Button onClick={addIngredient} className="mb-4">Add Ingredient</Button>
      <br />
      <Button onClick={handleSubmit} className="mr-2">Update</Button>
        <Button onClick={() => navigate("/home")}>Cancel</Button>
      </div>
    );
  }

    export default UpdateRecipe;