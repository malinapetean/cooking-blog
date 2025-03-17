import React, { useState } from "react";
import { BrowserRouter as Router, Route, Routes, useNavigate } from "react-router-dom";
import './App.css';

// Card Component
export function Card({ children, className }) {
  return <div className={`border rounded-lg p-4 shadow-md ${className}`}>{children}</div>;
}

// Button Component
export function Button({ children, onClick, className }) {
  return (
    <button
      onClick={onClick}
      className={`px-4 py-2 bg-blue-500 text-black rounded hover:bg-blue-700 ${className}`}
    >
      {children}
    </button>
  );
}

const initialRecipes = [
  { id: 1, title: "Creamy Ravioli", category: "Pasta", description: "Delicious ravioli with creamy sauce.", image: "https://i.pinimg.com/736x/06/0f/e5/060fe527e9aad9f7148b2378685d0e2a.jpg" },
  { id: 2, title: "Marry Me Chicken", category: "Meat", description: "Juicy chicken with flavorful sauce.", image: "http://i.pinimg.com/736x/cb/3f/51/cb3f512304bd0308d8f286c1a6be18d9.jpg" },
  { id: 3, title: "Lasagna", category: "Pasta", description: "Classic Italian lasagna.", image: "https://i.pinimg.com/736x/8f/0d/d4/8f0dd4d9f5381460cc6ae99c97960b23.jpg" },
  { id: 4, title: "Greek Salad", category: "Salads", description: "Healthy and delicious Greek salad.", image: "https://i.pinimg.com/736x/d1/e2/a9/d1e2a9c45ae8746c20b326c49414f663.jpg"},
  { id: 5, title: "Tiramisu", category: "Deserts", description: "Classic Italian dessert.", image: "https://i.pinimg.com/736x/48/5f/5e/485f5e8e40ce4996a24d2f5ea41ba367.jpg"},
  { id: 6, title: "Chicken Alfredo", category: "Pasta", description: "Creamy chicken alfredo pasta.", image: "https://i.pinimg.com/736x/5b/"},
  { id: 7, title: "Caesar Salad", category: "Salads", description: "Classic Caesar salad.", image: "https://i.pinimg.com/736x/5b/"},
  { id: 8, title: "Chocolate Cake", category: "Deserts", description: "Delicious chocolate cake.", image: "https://i.pinimg.com/736x/5b"},
  { id: 9, title: "Fritto Misto", category: "Meat", description: "Delicious fried seafood.", image: "https://i.pinimg.com/736x/5b"}
];

function CookingBlog({ recipes, onDelete }) {
  const [filteredCategory, setFilteredCategory] = useState("All");
  const navigate = useNavigate();

  const handleFilter = (category) => {
    setFilteredCategory(category);
  };

  const filteredRecipes =
    filteredCategory === "All"
      ? recipes
      : recipes.filter((r) => r.category === filteredCategory);

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold">Cooking Blog</h1>
      <h3 className="text-lg font-bold">Because love goes through the stomach!</h3>
      <p>Check out the latest recipes below.</p>
      <br></br>
      <img src="https://www.bokksu.com/cdn/shop/articles/shutterstock_1504638182.jpg?v=1651263197" alt="Cooking" className="cooking-image"></img>
      <br></br>
      <Button onClick={() => navigate("/add-recipe")} className="my-2">Add your own recipe</Button>
      
      <div className="my-4">
        {['All', 'Meat', 'Salads', 'Pasta', 'Deserts'].map((category) => (
          <Button key={category} onClick={() => handleFilter(category)} className="m-1">{category}</Button>
        ))}
      </div>

      <div className="grid grid-cols-3 gap-4">
        {filteredRecipes.map((recipe) => (
          <Card key={recipe.id} className="p-2">
            <img src={recipe.image} alt={recipe.title} className="recipe-image" />
            <div>
              <h2 className="text-lg font-bold">{recipe.title}</h2>
              <p>{recipe.description}</p>
              <Button className="ml-2 bg-red-500 hover:bg-red-700" onClick={() => onDelete(recipe.id)}>Delete</Button>
              
            </div>
          </Card>
        ))}
      </div>
    </div>
  );
}

function AddRecipe({ onSave }) {
  const [title, setTitle] = useState("");
  const [category, setCategory] = useState("Pasta");
  const [description, setDescription] = useState("");
  const [image, setImage] = useState("");
  const navigate = useNavigate();
  
  const handleSubmit = () => {
    if (!title || !description || !image) {
      alert("Please fill in all fields.");
      return;
    }
    onSave({ id: Date.now(), title, category, description, image });
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
      <input className="w-full border p-2 my-2" placeholder="Image URL" value={image} onChange={(e) => setImage(e.target.value)} />
      <br></br>
      <img src={image} alt={title} className="recipe-image" />
      <br></br>
      <Button onClick={handleSubmit} className="mr-2">Save</Button>
      <Button onClick={() => navigate("/")}>Cancel</Button>
    </div>
  );
}

export default function App() {
  const [recipes, setRecipes] = useState(initialRecipes);

  const handleAddRecipe = (newRecipe) => {
    setRecipes([...recipes, newRecipe]);
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

      </Routes>
    </Router>
  );
}