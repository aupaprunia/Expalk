//Mentor

url = 'http://localhost:5000/speaker/';

var channel;
function mood_value(x){
    sessionStorage.setItem("role","mentor")
    var new_url = url.concat(x);
    fetch(new_url).then(response =>{
        return response.json();
    }).then(status_json=>{
        status = status_json.status;
        if(status == 0){
            window.alert(status_json.message);
        }
        if(status == 1){
            channel = status_json.channel_name;
            var current_token = sessionStorage.getItem("token");
            sessionStorage.setItem("token", String(parseInt(current_token) + 5));
            sessionStorage.setItem("channel_name", channel);
            window.location.href = "index.html";
        }
    });
}
