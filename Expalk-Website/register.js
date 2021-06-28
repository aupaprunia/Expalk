function register(){

    var name = document.getElementById("name").value;
    var email = document.getElementById("input_email").value;
    var password = document.getElementById("input_password").value;
    var confirm = document.getElementById("input_confirm").value;
    
    if (confirm != password){
        document.getElementById("error_message").textContent = "Passwords do not match.";
    }
    else{    
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var raw = JSON.stringify({
    "name": name,
    "email": email,
    "password": password
    });

    var requestOptions = {
    method: 'POST',
    headers: myHeaders,
    body: raw,
    redirect: 'follow'
    };

    fetch("http://localhost:5000/signup", requestOptions)
    .then(response =>{
        return response.json();
    }).then(result =>{
        if(result.status == 1){
            window.alert("Registration Successful. Please Log In.");
            window.location.href = "login.html";
        }
    })
    .catch(error => console.log('error', error));
    }
}