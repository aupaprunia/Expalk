//Student, Mentee

url = 'http://localhost:5000/listner/';
uid = "";
var current_token = sessionStorage.getItem("token");

var channel;
function mood_value(x){
    if (current_token == 0){
        window.alert("You don't have enough tokens for this interaction!");
    }

    else{
    sessionStorage.setItem("role","mentee")
    var new_url = url.concat(x);
    console.log(new_url)
    fetch(new_url).then(response =>{
        return response.json();
    }).then(message_info=>{
        success_msg = message_info.message;
        window.alert(success_msg);
        sessionStorage.setItem("token", current_token -1);
        sessionStorage.setItem("channel_name", message_info.uid);
        window.location.href = "index.html"
    });
}
}
