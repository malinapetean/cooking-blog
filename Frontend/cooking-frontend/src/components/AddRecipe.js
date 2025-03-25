import { useState } from "react";
import { useNavigate } from "react-router";
import { Button } from "./button";


function AddRecipe({ onSave }) {
    const [title, setTitle] = useState("");
    const [category, setCategory] = useState("Pasta");
    const [description, setDescription] = useState("");
    const [totaltime, setTotaltime] = useState(0);
    const [image, setImage] = useState("");
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
      onSave({ id: Date.now(), title, category, description, totaltime: parseInt(totaltime, 10), image });
      navigate("/");
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
        <input className="w-full border p-2 my-2" placeholder="Total time" value={totaltime} onChange={(e) => setTotaltime(e.target.value)}></input>
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
  