package com.sif.digestyc.Controller.Validacion;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.sif.digestyc.Entity.ValidationModule.Notificacion;

@Controller
public class NotificacionController {
	

    @MessageMapping("/notificacion.send/")
    @SendTo("/topic/notificacion")
    public Notificacion sendMessage(Notificacion notificacion) {
        return notificacion;
    }
        

}
