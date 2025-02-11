package com.api.westernunion.WesternUnion.servicio;

import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    public String enviarNotificacion(String metodo, String destinatario, String idTransaccion) {
        // Simular la lógica de envío según el método
        switch (metodo.toLowerCase()) {
            case "correo electrónico":
                return enviarCorreo(destinatario, idTransaccion);
            case "whatsapp":
                return enviarWhatsApp(destinatario, idTransaccion);
            case "sms":
                return enviarSMS(destinatario, idTransaccion);
            case "push":
                return enviarPush(destinatario, idTransaccion);
            default:
                throw new IllegalArgumentException("Método de notificación no soportado: " + metodo);
        }
    }

    private String enviarCorreo(String destinatario, String idTransaccion) {
        // Lógica para simular el envío de un correo
        return "Correo enviado a " + destinatario + " con ID de transacción " + idTransaccion;
    }

    private String enviarWhatsApp(String destinatario, String idTransaccion) {
        // Lógica para simular el envío de un mensaje de WhatsApp
        return "Mensaje de WhatsApp enviado a " + destinatario + " con ID de transacción " + idTransaccion;
    }

    private String enviarSMS(String destinatario, String idTransaccion) {
        // Lógica para simular el envío de un SMS
        return "SMS enviado a " + destinatario + " con ID de transacción " + idTransaccion;
    }

    private String enviarPush(String destinatario, String idTransaccion) {
        // Lógica para simular el envío de una notificación Push
        return "Notificación Push enviada a " + destinatario + " con ID de transacción " + idTransaccion;
    }
}
