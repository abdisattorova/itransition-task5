var stompClient = null;
var currentUser = null;
connect();

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
        $("#receiverId").show();

    } else {
        $("#conversation").hide();
        $("#receiverId").hide();

    }

}

async function getCurrentUser() {
    await fetch('api/users/me')
        .then(function (response) {
            response.json()
                .then(data => {
                    currentUser = data;
                    console.log(data)
                    authUser(data.name);
                })
        })
}

async function connect() {

    await getCurrentUser();
    getAllUsers();

    var socket = new SockJS('/gs-guide-websocket');

    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        stompClient.subscribe(
            '/user/' + currentUser.id + '/queue/messages',
            function (messageDto) {

                const data = JSON.parse(messageDto.body);
                console.log(data);
                if (data.senderId == currentUser.id || data.senderId == $("#receiverId").val()) {
                    // appendNewMsg(data);
                } else
                    alert("new msg : " + data.text + " from " + data.senderName)

                // if(data.senderId===$("#receiverId").val() ||
                //     data.senderId===currentUser.id
                // ) {
                //     console.log(data);
                //     appendNewMsg(data);
                // }else if(data.receiverId===currentUser.id || data.receiverId===$("#receiverId").val()){
                //     console.log(data);
                //     appendNewMsg(data);
                // }else{
                //     console.alert("new msg : " + data.text + " from " + data.senderName)
                // }
            }
        );
        stompClient.subscribe(
            '/user/' + currentUser.id + '/contact',
            function (){
                getAllUsers()
            }
        )
    });

}

function receiverChangeHandle(selectedObj) {
    let selectedReceiverId = selectedObj.value;
    getMessagesByReceiverId(selectedReceiverId)
}

function getAllUsers() {
    fetch("/api/users")
        .then(function (response) {
                if (response.ok) {
                    response.json().then(res => {
                        $("#receiverId").empty()
                        console.log(res);
                        res.map(user => {
                            let result = user.id === currentUser.id ? "Saved Messages" : user.name;
                            $("#receiverId").append(
                                "<option value=" + user.id + ">" + result  + "</option>"
                            );
                        })
                        let user1 = res[0];
                        getMessagesByReceiverId(user1.id)

                    })
                }
            }
        )
}

function getMessagesByReceiverId(receiverId) {
    fetch("/api/messages/get-by-receiver-and-sender-id/" + receiverId)
        .then(function (response) {
                if (response.ok) {
                    response.json().then(res => {
                        console.log(res);
                        $("#messages").empty()
                        res.map(value => {
                            appendNewMsg(value)
                        })
                    })
                }
            }
        )
}

function sendText() {

        let dataBodyStr = JSON.stringify(
            {
                'title': $("#title").val(),
                'text': $("#text").val(),
                'receiverId': $("#receiverId").val(),
                'receiverName': $("#receiverId").val(),
                'senderId': currentUser.id,
                'senderName': currentUser.name
            });
        stompClient.send(
            "/app/send-message",
            {},
            dataBodyStr
        )


        appendNewMsg(JSON.parse(dataBodyStr))
        document.getElementById("title").value = "";
        document.getElementById("text").value = "";

}

function appendNewMsg(message) {
    const tdAlign = message.senderId === currentUser.id ? 'right' : 'left';

    $("#messages").append("<tr>" +
        "<td align='" + tdAlign + "'>" +
        "<h6>" + message.senderName + "</h6>" +
        "<h3>" + message.title + " - " + message.text + "</h3>" +
        "</td>" +
        "</tr>");
}

function authUser(user) {

    $("#authUser").append( "Hello - "+ user);
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#send").click(function () {
        sendText();
    });
});
