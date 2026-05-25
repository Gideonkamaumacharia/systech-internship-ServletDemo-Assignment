<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Audit Feed</title>
    <style>
        body { font-family: sans-serif; padding: 20px; background: #f4f4f4; }
        h2 { margin-bottom: 10px; }
        #status { font-size: 13px; color: #666; margin-bottom: 10px; }
        #feedList { list-style: none; padding: 0; }
        #feedList li {
            background: #fff;
            border-left: 4px solid #378ADD;
            padding: 8px 12px;
            margin-bottom: 6px;
            border-radius: 4px;
            font-size: 14px;
        }
        #feedList li .time { font-size: 11px; color: #999; float: right; }
        button { padding: 5px 12px; cursor: pointer; }
    </style>
</head>
<body>

<h2>Audit Log Feed</h2>
<div id="status">Connecting...</div>
<button onclick="clearFeed()">Clear</button>
<ul id="feedList"></ul>

<script>
    var socket;
    var feedList = document.getElementById("feedList");
    var status   = document.getElementById("status");

    function connect() {
        //var proto = location.protocol === "https:" ? "wss" : "ws";
        //var ctx   = location.pathname.replace(/\/[^/]*$/, "");
        socket    = new WebSocket("ws://localhost:8080/showroom/audit_feeds");

        socket.onopen = function () {
            console.log("Connected!!")
           status.textContent = "Connected";
        };

        socket.onmessage = function (event) {
            var li   = document.createElement("li");
            var time = new Date().toLocaleTimeString();
            li.innerHTML = event.data + '<span class="time">' + time + '</span>';
            feedList.insertBefore(li, feedList.firstChild);
        };

        socket.onclose = function () {
            status.textContent = "Disconnected. Retrying...";
            setTimeout(connect, 3000);
        };

        socket.onerror = function () {
            status.textContent = "Connection error.";
        };
    }

    function clearFeed() {
        feedList.innerHTML = "";
    }

    connect();
</script>

</body>
</html>