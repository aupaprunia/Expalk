function login(){

var email = document.getElementById("input_email").value;
var password = document.getElementById("input_password").value;
console.log(email, password);

var url = 'http://127.0.0.1:5000/signin/';
var url2 = url.concat(email);
var url1=url2.concat("/");
var new_url=url1.concat(password);

fetch(new_url).then(response =>{
    return response.json();
}).then( result =>{
    if(result.status == 1){
        tokens = result.token;
        user_name = result.name;

        sessionStorage.setItem("name", user_name);
        sessionStorage.setItem("token", tokens);

        
        window.location.href = "dashboard.html";
    }
    else{
        document.getElementById("error_message").textContent = "Incorrect Details.";
    }
});

}
