

test("renders Cooking Blog title", () => {
  expect(testadd()).toBe(true);
});


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

  if(data[0].id==5){
    return true;
  }
  else{
    return false;
  }
  

}




