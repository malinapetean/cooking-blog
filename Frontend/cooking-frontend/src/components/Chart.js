import React, { useEffect, useState } from "react";
import { BarChart, Bar, XAxis, YAxis, Tooltip, PieChart, Pie, Cell, LineChart, Line } from "recharts";
import { useNavigate } from "react-router";

const Charts = ({ recipes }) => {
  const navigate = useNavigate();

  const [timeData, setTimeData] = useState([]);
  const [categoryData, setCategoryData] = useState([]);

  useEffect(() => {
    const categoryTimeMap = recipes.reduce((acc, recipe) => {
      if (!acc[recipe.category]) {
          acc[recipe.category] = { total: 0, count: 0 };
      }
      acc[recipe.category].total += recipe.totaltime;
      acc[recipe.category].count += 1;
      return acc;
    }, {});
  
    // Convert to array with averages
    const timeStats = Object.keys(categoryTimeMap).map(category => ({
        category,
        avgTime: categoryTimeMap[category].total / categoryTimeMap[category].count
    }));
    
    setTimeData(timeStats);
  

    const categoryCounts = recipes.reduce((acc, recipe) => {
      acc[recipe.category] = (acc[recipe.category] || 0) + 1;
      return acc;
    }, {});
    setCategoryData(Object.entries(categoryCounts).map(([name, value]) => ({ name, value })));

    
  }, [recipes]);

  return (
    <div>
      <button className="buttonBack" onClick={() => navigate("/")}>Go Back Home</button>
      <div style={{display: "flex", flexDirection: "row" }}>
          <div>
              <h2>Recipe time distribution</h2>
              <BarChart width={400} height={300} data={timeData}>
                  <XAxis dataKey="category" />
                  <YAxis />
                  <Tooltip />
                  <Bar dataKey="avgTime" fill="#8884d8" />
              </BarChart>
          </div>
          <div>
              <h2>Recipes by Category</h2>
              <PieChart width={400} height={300}>
                  <Pie data={categoryData} dataKey="value" nameKey="name" cx="50%" cy="50%" outerRadius={100}>
                  {categoryData.map((entry, index) => (
                      <Cell key={`cell-${index}`} fill={['#0088FE', '#00C49F', '#FFBB28', '#FF8042'][index % 4]} />
                  ))}
                  </Pie>
                  <Tooltip />
              </PieChart>
          </div>
          <div>
          <h2>Category Counts</h2>
              <LineChart width={400} height={300} data={categoryData}>
                  <XAxis dataKey="name" />
                  <YAxis />
                  <Tooltip />
                  <Line type="monotone" dataKey="value" stroke="#82ca9d" />
              </LineChart>
          </div>
        
      </div>
    </div>
  );
};

export default Charts;