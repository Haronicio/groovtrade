let setToken=(token)=>{
    localStorage.setItem("token",token);
}
let logout=()=>{
    localStorage.removeItem("token");
}
let islogged=()=>{
    let token = localStorage.getItem("token");
    return !!token;
}
let getToken=()=>{
    
    return localStorage.getItem("token");
}
export const authtificationService = {
    setToken,logout,islogged, getToken
}