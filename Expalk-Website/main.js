/**
 * @name handleFail
 * @param err - error thrown by any function
 * @description Helper function to handle errors
 */
 let handleFail = function(err){
    console.log("Error : ", err);
};

// Queries the container in which the remote feeds belong
let remoteContainer= document.getElementById("remote-container");
/**
 * @name addVideoStream
 * @param streamId
 * @description Helper function to add the video stream to "remote-container"
 */
function addVideoStream(streamId){
    let streamDiv=document.createElement("div"); // Create a new div for every stream
    streamDiv.id=streamId;                       // Assigning id to div
    streamDiv.style.transform="rotateY(180deg)"; // Takes care of lateral inversion (mirror image)
    remoteContainer.appendChild(streamDiv);      // Add new div to container
}
/**
 * @name removeVideoStream
 * @param evt - Remove event
 * @description Helper function to remove the video stream from "remote-container"
 */
function removeVideoStream (streamId) {
    let remDiv = document.getElementById(streamId);
    if(remDiv) remDiv.parentNode.removeChild(remDiv);
}

let client = AgoraRTC.createClient({
    mode: 'rtc',
    codec: "vp8"
});

client.init("8a74446508ae4a1e98ed9a66c1d607de",() => console.log("AgoraRTC client initialized") ,handleFail);

var channel = sessionStorage.getItem("channel_name");
console.log(channel);
sessionStorage.setItem("present","yes")

client.join(null,"channel",null, (uid)=>{  // client.join(token, channel_name, user_id) parameters

    let localStream = AgoraRTC.createStream({
        streamID: uid,
        audio: true,
        video: true,
        screen: false});

    localStream.init(function() {

        //Plays the localVideo
        localStream.play('me');

        //Publishes the stream to the channel
        client.publish(localStream, handleFail);

    },handleFail);

},handleFail);

client.on('stream-added', function (evt) {
    client.subscribe(evt.stream, handleFail);
});
//When you subscribe to a stream
client.on('stream-subscribed', function (evt) {
    let stream = evt.stream;
    let streamId = String(stream.getId());
    addVideoStream(streamId);
    stream.play(streamId);
});

//When a person is removed from the stream
client.on('stream-removed', (evt) => {
    let stream = evt.stream;
    let streamId = String(stream.getId());
    stream.close();
    removeVideoStream(streamId);
    
});
client.on('peer-leave', (evt) => {
    let stream = evt.stream;
    let streamId = String(stream.getId());
    stream.close();
    removeVideoStream(streamId);
    sessionStorage.setItem("present","no")
});

function func(){
    client.on('stream-subscribed', (evt)=>{
    let stream = evt.stream;
    stream.muteVideo();
});
}

var interval = setInterval(function(){
    if(sessionStorage.getItem("present") == "no"){
        window.alert("Meeting has ended. You will now be redirected to the dashbaord");
        window.location.href = "dashboard.html";
        clearInterval(interval);
    }
},1000)

if(sessionStorage.getItem("role") == "mentor"){
setTimeout(function(){alert("You have 1 minute left!")},24000);
setTimeout(function(){window.location.href = "dashboard.html"; alert("Your session has ended.")}, 30000);
setTimeout(function(){sessionStorage.removeItem("channel_name")}, 30000);
}