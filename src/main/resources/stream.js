var relativePath = "/socket";
var port = "8081";

var path = 
	"ws://" + 
	location.hostname + 
	":" + port + "" +
	relativePath;

var load = document.createElement('div');
load.classList.add("load");

function update(content) {
	document.body.innerHTML = "";
	document.body.appendChild(content);
}

update(
	load
);

var socket = null;

try {
	socket = new WebSocket(path);
}
catch(err) {
	window.location.reload();
}

socket.onerror = function(event) {
	update(
		load
	);
}

socket.onclose = function(event) {
	update(
		load
	);
	window.location.reload();
};

socket.onmessage = function(event) {
	var msg = event.data;
	var content = document.createElement('div');
	content.innerHTML = msg;
	update(
		content
	);
	const buttons = document.getElementsByTagName('button');
	for (const button of buttons) {
		button.addEventListener('click', function() {
			socket.send(button.id);
		});
	}
};
