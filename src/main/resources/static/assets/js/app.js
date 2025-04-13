const stompClient = new StompJs.Client({
    webSocketFactory: () => new SockJS('http://127.0.0.1:8080/gs-guide-websocket'),
    reconnectDelay: 5000,
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings', (greeting) => {
        const message = greeting.body;
        const formattedMessage = `ip:${message.ip}, Name:${message.name}, Content:${message.content}, time:${message.time}`;

        showGreeting(JSON.parse(formattedMessage).content);
        saveMessageToFile(message);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.publish({
        destination: "/app/hello",
        body: JSON.stringify({ 'name': $("#name").val(), 'Content:': $("input-label").val() })
    });
}

function saveMessageToFile(message) {
    // Bạn có thể lưu dữ liệu vào một đối tượng mảng JavaScript trước khi lưu vào file
    const messagesArray = JSON.parse(localStorage.getItem('messages') || "[]");
    messagesArray.push(message);
    localStorage.setItem('messages', JSON.stringify(messagesArray)); // Lưu vào LocalStorage (hoặc có thể gửi ra backend)

    // (Giả sử bạn muốn lưu vào file - cần sự hỗ trợ của backend hoặc sử dụng các API frontend như Blob hoặc FileSaver)
    const blob = new Blob([message], { type: "text/plain;charset=utf-8" });
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = "chatMessages.txt";
    link.click();
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function() {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendName());
});