import { useState } from "react";
import { useNavigate, useParams } from "react-router";
import { Button } from "./button";


function UpdateRecipe({ recipes, onUpdate }) {
    const { id } = useParams();
    const recipe = recipes.find((r) => r.id === parseInt(id));
    const [title, setTitle] = useState(recipe.title);
    const [category, setCategory] = useState(recipe.category);
    const [description, setDescription] = useState(recipe.description);
    const [totaltime, setTotaltime] = useState(recipe.totaltime);
    const [image, setImage] = useState(recipe.image);
    const navigate = useNavigate();
  
    const handleSubmit = () => {
      if (!title || !description || !image || !totaltime) {
        alert("Please fill in all fields.");
        return;
      }
      if(isNaN(totaltime) || totaltime < 0){
        alert("Please enter a number for the total time.");
        return;
      }
      onUpdate({ id: recipe.id, title, category, description, totaltime, image });
      navigate("/");
    };
  
    return (
      <div className="p-4">
        <h2 className="text-xl font-bold">Update Recipe</h2>
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
        <input className="w-full border p-2 my-2" placeholder="Total time" value={totaltime} onChange={(e) => setTotaltime(e.target.value)}></input>
        <input className="w-full border p-2 my-2" placeholder="Image URL" value={image} onChange={(e) => setImage(e.target.value)} />
        <br></br>
        <img src={image} alt={title} className="recipe-image" />
        <br></br>
        <Button onClick={handleSubmit} className="mr-2">Update</Button>
        <Button onClick={() => navigate("/")}>Cancel</Button>
      </div>
    );
  }

    export default UpdateRecipe;