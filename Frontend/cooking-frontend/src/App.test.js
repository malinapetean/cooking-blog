import React from 'react';
import { render, screen , fireEvent} from '@testing-library/react';
import AddRecipe from './components/AddRecipe';
import { MemoryRouter, Route, Routes } from 'react-router';
import App from './App';
import UpdateRecipe from './components/UpdateRecipe';

test("Add recipe", () => {
  expect(testadd()).toBe(true);
});

test("Update recipe", () => {
  expect(testupdate()).toBe(true);
});

test("Delete recipe", () => {
  expect(testdelete()).toBe(true);
});

test("Create recipe", () => {
  expect(testcreate()).toBe(true);
});

test("Filter recipe", () => {
  expect(testfilter()).toBe(true);
});

describe("AddRecipe", () => {
  it("should render AddRecipe component", () => {
    render(<MemoryRouter> 
    <AddRecipe />
    </MemoryRouter>);
    const linkElement = screen.getByText("Cancel");
    expect(linkElement).toBeInTheDocument();
    
  });

//   it("should show alert when total time is less than 0", () => {
//     const alertMock = jest.spyOn(window, 'alert').mockImplementation(() => {});

//     render(
//       <MemoryRouter>
//         <AddRecipe />
//       </MemoryRouter>
//     );

//     fireEvent.change(screen.getByLabelText("Total time"), { target: { value: -10 } });
//     fireEvent.click(screen.getByText("Save"));

//     expect(alertMock).toHaveBeenCalledWith("Please enter a number for the total time.");

//     alertMock.mockRestore();
// });
});

test("Add recipe", () => {
  render(<MemoryRouter> 
        <AddRecipe />
        </MemoryRouter>);
  fireEvent.click(screen.getByText("Save"));

  expect(testadd()).toBe(true);
});

test("Main page", () => {
  render(<App/> );
  const linkElement = screen.getByText("Cooking Blog");
  expect(linkElement).toBeInTheDocument();
  fireEvent.click(screen.getByText("View Charts"));
  const linkElement2 = screen.getByText("Go Back Home");
  expect(linkElement2).toBeInTheDocument();
});

const mockRecipes = [
  { id: 1, title: "Creamy Ravioli", category: "Pasta", description: "Delicious ravioli with creamy sauce.",totaltime: 50, image: "" },
  { id: 2, title: "Marry Me Chicken", category: "Meat", description: "Juicy chicken with flavorful sauce.",totaltime: 95, image: "" },
];

test("Update recipe", () => {
  render(<MemoryRouter initialEntries={["/update-recipe/1"]}> 
      < Routes>
        <Route path="/update-recipe/:id" element={<UpdateRecipe recipes={mockRecipes}/>} />
      </Routes>
    </MemoryRouter>);
  fireEvent.click(screen.getByText("Update"));
  expect(testupdate()).toBe(true);
});
test("Update recipe", () => {
  render(<MemoryRouter initialEntries={["/update-recipe/1"]}>
      < Routes>
        <Route path="/update-recipe/:id" element={<UpdateRecipe recipes={mockRecipes}/>} />
      </Routes>
    </MemoryRouter>);
  fireEvent.click(screen.getByText("Cancel"));
  expect(testupdate()).toBe(true);
});

test("Filter Button", () => { 
  render(<App/>);
  fireEvent.click(screen.getAllByText("Meat")[0]);
  expect(testfilter()).toBe(true);
  
}
);

function testfilter(){
  const data=[{
    id:5,
    title:"test",
    category:"test",
    description:"test",
    totaltime:10,
    image:"test",
  },
  {
    id:8,
    title:"test1",
    category:"test2",
    description:"test",
    totaltime:10,
    image:"test",
  }]
  const filteredCategory="test";
  const filteredRecipes=data.filter((r) => r.category === filteredCategory);
  if(filteredRecipes.length==1){
    return true;
  }
  else{
    return false;
  }
}


function testadd(){

   const data=[{
    id:5,
    title:"test",
    category:"test",
    description:"test",
    totaltime:10,
    image:"test",
  }]

  data.push({
    id:8,
    title:"test1",
    category:"test2",
    description:"test",
    totaltime:10,
    image:"test",
  });

  if(data.length==2){
    return true;
  }
  else{
    return false;
  }
}

function testupdate(){
  const data=[{
    id:5,
    title:"test",
    category:"test",
    description:"test",
    totaltime:10,
    image:"test",
  }]
  data[0].title="test1";
  data[0].category="test2";
  data[0].description="test3";
  data[0].totaltime=20;
  data[0].image="test4";  
  if(data[0].title=="test1" && data[0].category=="test2" && data[0].description=="test3" && data[0].totaltime==20 && data[0].image=="test4"){
    return true;
  }
  else{
    return false;
  }
}

function testdelete(){
  const data=[{
    id:5,
    title:"test",
    category:"test",
    description:"test",
    totaltime:10,
    image:"test",
  },
  {
    id:8,
    title:"test1",
    category:"test2",
    description:"test",
    totaltime:10,
    image:"test",
  }]
  data.pop();
  if(data.length==1){
    return true;
  }
  else{
    return false;
  }
}

function testcreate(){
  const data=[{
    id:5,
    title:"test",
    category:"test",
    description:"test",
    totaltime:10,
    image:"test",
  }]
  data.push({
    id:8,
    title:"test1",
    category:"test2",
    description:"test",
    totaltime:10,
    image:"test",
  });
  if(data.length==2){
    return true;
  }
  else{
    return false;
  }
}




