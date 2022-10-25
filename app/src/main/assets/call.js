//const Peer from = require("peerjs");

//var localVideo = document.getElementById('video'),vendorUrl = window.URL || window.webkitURL;

//localVideo.style.opacity = 0

//localVideo.onplaying = () => { localVideo.style.opacity = 1 }

var peer
var videoStream
function init(userId) {
  user = userId
  peer = new Peer(userId, {
      host: '192.168.242.91',
      port: 4001
  })
if (navigator.mediaDevices.getUserMedia) {
     navigator.mediaDevices.getUserMedia({audio:true,video:true })
         .then(function (stream) {
                video.srcObject = stream
                videoStream = stream
         }).catch(function (error) {
             console.log("Something went wrong!"+error);
         });
         }
         else{
         console.log("Error")
         }
//  listen()
}

//function listen() {
//   peer.on('call', (call) => {
//           call.answer(video.srcObject)
//           console.log("Hi from reciver")
//           call.on('stream', (remoteStream) => {
//               video.srcObject = remoteStream
//               console.log("Hi for reciver")
//           })
//       })
//   }


function startCall(otherUserId) {
   const call = peer.call(otherUserId, videoStream)
                   console.log("Hi from sender")
                   call.on('stream', userVideoStream => {
                     console.log("Hi for sender")
                   })
}
function toggleVideo(b) {
   if (b == "true") {
       localStream.getVideoTracks()[0].enabled = true
       video.srcObject = localStream
   } else {
       localStream.getVideoTracks()[0].enabled = false
       video.srcObject = null
   }
}

function toggleAudio(b) {
   if (b == "true") {
       videoStream.getAudioTracks()[0].enabled = true
   } else {
       videoStream.getAudioTracks()[0].enabled = false
   }
}

// peerjs --port 4001 --key peerjs --path /videocallapp