import { useState } from 'react'

import './App.css'
import {useTable,useGlobalFilter,useSortBy,usePagination} from "react-table";

import * as React from "react";
import axios from "axios";

function App() {
  const [users, setUsers] = useState([]);

  const columns = React.useMemo(() => [
    { Header: "Id", accessor: "id" },
    { Header: "UserName", accessor: "username" },
    { Header: "Age", accessor: "age" },
    { Header: "Email", accessor: "email" },
    { Header: "Address", accessor: "address" },
    { Header: "Edit",id:"Edit" ,accessor: "edit", 
      Cell:props=><button className='editBtn' onClick={()=>handleUpdate(props.cell.row.original)}>Edit</button>
    },
    { Header: "Delete",id:"Delete" ,accessor: "delete", 
      Cell:props=><button className='deleteBtn' onClick={()=>handleDelete(props.cell.row.original)}>Delete</button>
    }
  ], []);

  const data = React.useMemo(() => users,[]);
  const[userData,setUserData]=useState({username:"",age:"",email:"",address:""});
  const[showCancel,setShowCancel]=useState(false);
  const[errMsg,setErrMsg]=useState("");


  // Fix typo: it should be useTable not userTable
  const { getTableProps, getTableBodyProps, headerGroups, page, prepareRow,state,setGlobalFilter,pageCount,nextPage,previousPage,canPreviousPage,canNextPage,gotoPage} = useTable({ columns, data:users,initialState:{pageSize:5}},useGlobalFilter,useSortBy,usePagination);
  const{globalFilter,pageIndex}=state;

  const getAllUser = () => {
    axios.get("http://localhost:8182/api/getAllCustomer").then((res) => {
      console.log(res.data);
      setUsers(res.data);
    });
  }

  const handleUpdate=(cust)=>{
    setUserData(cust);
    setShowCancel(true);
  }


  const clearAll=()=>{
    setUserData({username:"",age:"",email:"",address:""});
    getAllUser();
  }

  const handleDelete=async(cust)=>{
    const isconfirmed=window.confirm("Are you sure you want to delete");
    if(isconfirmed){
    await axios.delete(`http://localhost:8182/api/deleteUserById/${cust.id}`).then((res) => {
      console.log(res.data); // Handle the response data here
      setUserData(res.data);
    });
  }
  window.location.reload();

  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    let errmsg="";
    if(!userData.username || !userData.address || !userData.age || !userData.email){
      errmsg="All fields are required";
      setErrMsg(errmsg);
    }
   if((errmsg.length===0)&& userData.id){
    await axios.put(`http://localhost:8182/api/${userData.id}`, userData).then((res) => {
      console.log(res.data); // Handle the response data here
    });
   }else if(errmsg.length===0){
     await axios.post("http://localhost:8182/api/saveCustomerData", userData).then((res) => {
        console.log(res.data); // Handle the response data here
     });
    }
     clearAll();
}


  const handleCancel=()=>{
    setUserData({username:"",age:"",email:"",address:""});
    setShowCancel(false);
  }



    
  const handleChange=(e)=>{
    setUserData({...userData,[e.target.name]:e.target.value});
    setErrMsg("")
  }

  React.useEffect(()=>{
    getAllUser();
  },[]);

  return (
    <>
      <div className='main-container'>
      <h3>Full Stack Application Using React js,Spring Boot & MySQL</h3>
      {errMsg && <span className='error'>{errMsg}</span>}
          <div className='add-panel'>
          <div className='addpaneldiv'>
          <label htmlFor="username">Name</label><br></br>
          <input className='addpanelinput' value={userData.username} onChange={handleChange} type="text" name="username" id="username" />
        </div>
        
          <div className='addpaneldiv'>
          <label htmlFor="address">Address</label><br></br>
          <input className='addpanelinput' value={userData.address} onChange={handleChange} type="text" name="address" id="address" />
        </div>

          <div className='addpaneldiv'>
          <label htmlFor="age">Age</label><br></br>
          <input className='addpanelinput' value={userData.age} onChange={handleChange} type="text" name="age" id="age" />
        </div>
       
          <div className='addpaneldiv'>
          <label htmlFor="email">Email</label><br></br>
          <input className='addpanelinput' value={userData.email} onChange={handleChange} type="text" name="email" id="email" />
        </div>
    
        <button className='addBtn' onClick={handleSubmit}>{userData.id?"Update":"Add"}</button>
        <button className='cancelBtn' disabled={!showCancel} onClick={handleCancel}>Cancel</button>
      </div>
      <input className='searchinput'  value={globalFilter || ""}
  onChange={(e) => setGlobalFilter(e.target.value)}  type="search" name="inputsearch" id="inputsearch" placeholder="Search User Data"/>
    </div>
    <table className='table' {...getTableProps()}>
        <thead>
          {headerGroups.map((headerGroup) => (
            <tr {...headerGroup.getHeaderGroupProps()} key={headerGroup.id}>
              {headerGroup.headers.map((column) => (
                <th {...column.getHeaderProps(column.getSortByToggleProps())} key={column.id}>{column.render("Header")}
                {column.isSorted && <span>{column.isSortedDesc?"⬆️":"⬇️"}</span>}
                </th>
              ))}
            </tr>
          ))}
        </thead>
        <tbody {...getTableBodyProps()}>
          {page.map((row) => {
            prepareRow(row);
            return (
              <tr {...row.getRowProps()} key={row.id}>
                {row.cells.map((cell) => (
                  <td {...cell.getCellProps()} key={cell.column.id}>{cell.render("Cell")}</td>
                ))}
              </tr>
            );
          })}
        </tbody>
      </table>
      <div className='pagediv'>
          <button disabled={!canPreviousPage} className='pageBtn' onClick={()=>gotoPage(0)}>First</button>
          <button disabled={!canPreviousPage} className='pageBtn' onClick={previousPage}>Pre</button>
          <span className='index'>{pageIndex + 1} of {pageCount}</span>
          <button disabled={!canNextPage} className='pageBtn' onClick={nextPage}>Next</button>
          <button disabled={!canNextPage} className='pageBtn' onClick={()=>gotoPage(pageCount-1)}>Last</button>
</div>

    </>
  )
}

export default App
