import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import { Button } from "./button";
import { Card } from "./card";
import ReactPaginate from "react-paginate";



function CookingBlog({ recipes, onDelete }) {
    const [filteredCategory, setFilteredCategory] = useState("All");
    const [sortedRecipes, setSortedRecipes] = useState(recipes);
    const [maxTimeRecipe, setMaxTimeRecipe] = useState(null);
    const [minTimeRecipe, setMinTimeRecipe] = useState(null);
    const [avgTime, setAvgTime] = useState(null);
    const navigate = useNavigate();

    const [currentPage, setCurrentPage] = useState(0);
    const itemsPerPage = 3;


    const handlePageChange = ({ selected }) => {
      setCurrentPage(selected);
    };

    
    
    
  
    // useEffect(() => {
    //     setSortedRecipes(recipes);
    //   }, [recipes]);
  
    const handleFilter = (category) => {
      setFilteredCategory(category);
      setCurrentPage(0);
    };

    useEffect(() => {
        setSortedRecipes(recipes);
        const maxTime = Math.max(...recipes.map(r => r.totaltime));
        const minTime = Math.min(...recipes.map(r => r.totaltime));
        const avgTime = recipes.reduce((acc, r) => acc + r.totaltime, 0) / recipes.length;
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

    const displayedRecipes = filteredRecipes.slice(
      currentPage * itemsPerPage,
      (currentPage + 1) * itemsPerPage
    );

    const handleSortByTime = () => {
        const sorted = [...recipes].sort((a, b) => a.totaltime - b.totaltime);
        setSortedRecipes(sorted);
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
          {['All', 'Meat', 'Salads', 'Pasta', 'Deserts'].map((category) => (
            <Button key={category} onClick={() => handleFilter(category)} className="m-1">{category}</Button>
          ))}
        </div>
  
        { <Button onClick={handleSortByTime} className="mb-4">Sort by Total Time (Ascending)</Button> }
        { <Button onClick={chartsButtonHandler} className="mb-4">View Charts</Button> }

        <div className="grid">
          {displayedRecipes.map((recipe) => {
            
            let className = "";
            if (recipe.totaltime === maxTimeRecipe) {
                className += "max-time";
            } else if (recipe.totaltime === minTimeRecipe) {
                className += "min-time";
            } else if (Math.abs(recipe.totaltime - avgTime) < 5) {
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
                <p>{recipe.totaltime} minutes</p>
                <div className="button-group">
                  <Button className="ml-2 bg-red-500 hover:bg-red-700" onClick={() => onDelete(recipe.id)}>Delete</Button>
                  <Button className="ml-2 bg-yellow-500 hover:bg-yellow-700" onClick={() => navigate(`/update-recipe/${recipe.id}`)}>Update</Button>
                </div>
              </div>
            </Card>);
        })}
        </div>
        <ReactPaginate
          previousLabel={"← Previous"}
          nextLabel={"Next →"}
          pageCount={Math.ceil(filteredRecipes.length / itemsPerPage)}
          onPageChange={handlePageChange}
          containerClassName={"pagination"}
          activeClassName={"active"}
          
        />
      </div>
    );
}

export default CookingBlog;