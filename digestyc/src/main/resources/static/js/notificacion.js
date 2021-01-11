'use strict';

var stompClient = null;

function connect(event) {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    event.preventDefault();
}


function onConnected() {
	//se suscribe al canal de notificaciones
    stompClient.subscribe('/topic/notificacion', onMessageReceived);
}


function onError(error) {
	console.log("no se puede conectar, probablemente mala conexion");
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    $('#notificationAlert').append(
    		`<li>
                <a href="${message.url}">
                    <div>
                        <i class="fa fa-dot-circle-o"></i> ${message.titulo}
                        <span style="color: blue;" class="pull-right text-muted small">${message.tabla}</span>
                    </div>
                </a>
            </li>
            <li class="divider"></li>`);
    actualizarContador();
}

function actualizarContador(){
    var url = "/notificacion_rest/countNotification";
    $.get(url,function(data,status){
    	var contador = document.getElementById('contador');
    	contador.innerHTML = data;
    });
}

