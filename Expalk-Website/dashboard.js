var user_name = sessionStorage.getItem("name");
var tokens = sessionStorage.getItem("token");

document.getElementById("token_count").textContent = tokens;
document.getElementById("name_user").textContent = user_name;

    
    