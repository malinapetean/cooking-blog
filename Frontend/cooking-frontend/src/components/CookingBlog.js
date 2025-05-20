import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import { Button } from "./button";
import { Card } from "./card";




function CookingBlog({ recipes, onDelete }) {
    const [filteredCategory, setFilteredCategory] = useState("All");
    const [sortedRecipes, setSortedRecipes] = useState(recipes);
    const [maxTimeRecipe, setMaxTimeRecipe] = useState(null);
    const [minTimeRecipe, setMinTimeRecipe] = useState(null);
    const [avgTime, setAvgTime] = useState(null);
    const navigate = useNavigate();

    const [visibleCount, setVisibleCount] = useState(10);
    const incrementCount = 3; 
    
    

    useEffect(() => {
      const handleScroll = () => {
        const nearBottom = window.innerHeight + window.scrollY >= document.body.offsetHeight - 100;
    
        if (nearBottom && visibleCount < recipes.length) {
          setVisibleCount(prev => Math.min(prev + incrementCount, recipes.length));
        }
      };
    
      window.addEventListener('scroll', handleScroll);
      return () => window.removeEventListener('scroll', handleScroll);
    }, [visibleCount, recipes.length]);


    
    const handleFilter = async (category) => {
      try {
        const response = await fetch(`http://16.171.44.125:8080/recipes?category=${category}`, {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        });

        if (!response.ok) {
          throw new Error(`Failed to fetch recipes for category ${category}: ${response.statusText}`);
        }

        const filteredRecipes = await response.json();
        setSortedRecipes(filteredRecipes);
        setFilteredCategory(category);
      } catch (error) {
        console.error("Error fetching recipes by category:", error);
      }
    };
    useEffect(() => {
      setSortedRecipes([...recipes]); // Reset sortedRecipes on refresh
    }, [recipes]);

    useEffect(() => {
        setSortedRecipes(recipes);
        const maxTime = Math.max(...recipes.map(r => r.totalTime));
        const minTime = Math.min(...recipes.map(r => r.totalTime));
        const avgTime = recipes.reduce((acc, r) => acc + r.totalTime, 0) / recipes.length;
        setMaxTimeRecipe(maxTime);
        setMinTimeRecipe(minTime);
        setAvgTime(avgTime);
        console.log(minTime);
        console.log(maxTime);
        console.log(avgTime);
      }, [recipes]);
  
      const filteredRecipes =
      filteredCategory === "All"
         ? sortedRecipes
         : sortedRecipes.filter((r) => r.category === filteredCategory);

      const displayedRecipes = filteredRecipes.slice(0, visibleCount);

    const handleSortByTime = async () => {
        const response = await fetch("http://16.171.44.125:8080/sortByTime", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        });
        if (!response.ok) {
          throw new Error(`Failed to sort recipes: ${response.statusText}`);
        }
        const sortedRec = await response.json();
        console.log(sortedRec);

        setSortedRecipes(sortedRec);
        setFilteredCategory("All");
    }


    const chartsButtonHandler = () => {
      navigate("/chart");
    };
  
  
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
          <h2 className="text-lg font-bold">Categories</h2>
        </div>
        <div className="my-4">
          {['All', 'Meat', 'Salads', 'Pasta', 'Deserts', 'Soup'].map((category) => (
            <Button key={category} onClick={() => handleFilter(category)} className="m-1">{category}</Button>
          ))}
        </div>
        {localStorage.getItem("isAdmin") === "true" && (
            <Button
              className="ml-2 bg-yellow-500 hover:bg-yellow-700"
              onClick={() => navigate("/admin/monitored-users")}
            >
              See monitored users
            </Button>
          )
          }
        { <Button onClick={handleSortByTime} className="mb-4">Sort by Total Time (Ascending)</Button> }
        { <Button onClick={chartsButtonHandler} className="mb-4">View Charts</Button> }

        <div className="grid">
          {displayedRecipes.map((recipe) => {
            
            let className = "";
            if (recipe.totalTime === maxTimeRecipe) {
                className += "max-time";
            } else if (recipe.totalTime === minTimeRecipe) {
                className += "min-time";
            } else if (Math.abs(recipe.totalTime - avgTime) < 5) {
                className += "avg-time";
            }
            else
            {
                className += "card";
            }
            return (
            <Card key={recipe.id} className={className}>
              <img src={recipe.image} alt={recipe.title} className="recipe-image" />
              <div>
                <h2 className="text-lg font-bold">{recipe.title}</h2>
                <p>{recipe.description}</p>
                <p>{recipe.totalTime} minutes</p>
                <div className="button-group">
                  <Button className="ml-2 bg-red-500 hover:bg-red-700" onClick={() => onDelete(recipe.id)}>Delete</Button>
                  <Button className="ml-2 bg-yellow-500 hover:bg-yellow-700" onClick={() => navigate(`/update-recipe/${recipe.id}`)}>Update</Button>
                </div>
              </div>
            </Card>);
        })}
        </div>
        
      </div>
    );
}

export default CookingBlog;