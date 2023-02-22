import React, { useState } from "react";
import { fetchCustomerAdd, fetchCreditApplicationAdd } from "../../api";

function Form() {
  const [values, setValues] = useState({});
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [identityNumber, setIdentityNumber] = useState("");
  const [phone, setPhone] = useState("");
  const [monthlyIncome, setMonthlyIncome] = useState("");
  const [result, setResult] = useState({});
  const [click, setClick] = useState(false);
  const [applicationResult, setApplicationResult] = useState({});

  const handleSubmit = (event) => {
    setValues(event.target.values);
    setClick(true);
    fetchCustomerAdd({
      firstName,
      lastName,
      identityNumber,
      phone,
      monthlyIncome,
    })
      .then((res) => setResult(res.data))
      .then(() => {
        let userStr = `{"identityNumber":"${identityNumber}"}`;
        let userObj = JSON.parse(userStr);
        fetchCreditApplicationAdd(userObj).then((res) =>
          setApplicationResult(res)
        );
      });
    event.preventDefault();
  };

  const resetForm = () => {
    setValues({});
    setApplicationResult({});
    setResult({});
  };
  return click ? (
    <div>
      {applicationResult.data ? (
        <div
          className="card text-center mt-5 "
          style={{
            width: "600px",
            height: "700px",
            display: "flex",
            marginTop: "180px",
            margin: "auto",
            borderRadius:"25px",
            borderTopLeftRadius:"25px"
          }}
        >
          <div className="card-header" style={{ fontWeight: "bold", backgroundColor:"#5891ff",borderTopLeftRadius:"25px", borderTopRightRadius:"25px"}}>
            {applicationResult.data[0].status ? (
              <div style={{ color: "white",fontFamily:"serif" }}>APPROVAL</div>
            ) : (
              <div style={{ color: "white",fontFamily:"serif" }}> REJECTION</div>
            )}
          </div>
          <div className="card-body">
          <div  style={{ fontWeight: "bold" ,fontSize:"30px"}}>
            {applicationResult.data[0].status ? (
              <div style={{ color: "green" }}> ✅ </div>
            ) : (
              <div style={{ color: "red" }}>❌ </div>
            )}
          </div>
            <div style={{ marginTop: "30px", fontSize: "29px",fontFamily:"cursive" }}>
              Dear {result.firstName + " " + result.lastName} ,
            </div>
            <h5
              className="card-title "
              style={{ margin: "30px", fontSize: "17px",fontFamily:"serif" }}
            >
              Your Credit Application Has Been Made.{" "}
            </h5>
            <p
              className="card-text"
              style={{
                display: "flex",
                justifyContent: "center",
                flexDirection: "column",
              }}
            >
              <table className="table" style={{textAlign:"left", width:"350px",margin:"auto",fontFamily:"serif"}}>
                <tr>
                  <td>First Name</td>
                  <td>{result.firstName}</td>
                </tr>
                <tr>
                  <td>Last Name </td>
                  <td>{result.lastName}</td>
                </tr>
                <tr>
                  <td>Identity Number</td>
                  <td>{result.identityNumber}</td>
                </tr>
                <tr>
                  <td>Phone</td>
                  <td>{result.phone}</td>
                </tr>
                <tr>
                  <td>Monthly Income</td>
                  <td>{result.monthlyIncome}</td>
                </tr>
                <tr>
                  <td>Credit Limit</td>
                  <td>{applicationResult.data[0].creditLimit}</td>
                </tr>
              </table>
            
            </p>
            <a onClick={resetForm} className="btn" style={{backgroundColor:"#5891ff", color:"white",  width:"150px"}}>
              Cancel
            </a>
          </div>
        </div>
      ) : (
        <div className="registration-form">
          <form onSubmit={handleSubmit}>
            <div className="form-group" style={{marginLeft: "100px", fontSize: "25px",fontFamily:"serif",color:"#5891ff"}}> Credit Application Form </div>
            <div className="form-icon">
              <span>
                <i className="icon icon-user"></i>
              </span>
            </div>
            <div className="form-group">
              <input
                onChange={(e) => setFirstName(e.target.value)}
                type="text"
                className="form-control item"
                id="firstname"
                placeholder="First Name"
              />
            </div>
            <div className="form-group">
              <input
                onChange={(e) => setLastName(e.target.value)}
                type="text"
                className="form-control item"
                id="lastname"
                placeholder="Last Name"
              />
            </div>
            <div className="form-group">
              <input
                onChange={(e) => setIdentityNumber(e.target.value)}
                type="text"
                className="form-control item"
                id="identitynumber"
                placeholder="Identity Number"
              />
            </div>
            <div className="form-group">
              <input
                onChange={(e) => setPhone(e.target.value)}
                type="text"
                className="form-control item"
                id="phone"
                placeholder="Phone"
              />
            </div>
            <div className="form-group">
              <input
                onChange={(e) => setMonthlyIncome(e.target.value)}
                type="text"
                className="form-control item"
                id="monthlyincome"
                placeholder="Monthly Income"
              />
            </div>
            <div className="form-group">
              <button type="submit" className="btn btn-block create-account">
                Send
              </button>
            </div>
          </form>
        </div>
      )}
    </div>
  ) : (
    <div className="registration-form">
      <form onSubmit={handleSubmit}>
        <div className="form-icon">
          <span>
            <i className="icon icon-user"></i>
          </span>
        </div>
        <div className="form-group">
          <input
            onChange={(e) => setFirstName(e.target.value)}
            type="text"
            className="form-control item"
            id="firstname"
            placeholder="First Name"
          />
        </div>
        <div className="form-group">
          <input
            onChange={(e) => setLastName(e.target.value)}
            type="text"
            className="form-control item"
            id="lastname"
            placeholder="Last Name"
          />
        </div>
        <div className="form-group">
          <input
            onChange={(e) => setIdentityNumber(e.target.value)}
            type="text"
            className="form-control item"
            id="identitynumber"
            placeholder="Identity Number"
          />
        </div>
        <div className="form-group">
          <input
            onChange={(e) => setPhone(e.target.value)}
            type="text"
            className="form-control item"
            id="phone"
            placeholder="Phone"
          />
        </div>
        <div className="form-group">
          <input
            onChange={(e) => setMonthlyIncome(e.target.value)}
            type="text"
            className="form-control item"
            id="monthlyincome"
            placeholder="Monthly Income"
          />
        </div>
        <div className="form-group">
          <button type="submit" className="btn btn-block create-account">
            Send
          </button>
        </div>
      </form>
    </div>
  );
}

export default Form;
