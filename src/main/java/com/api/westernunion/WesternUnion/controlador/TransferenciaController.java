package com.api.westernunion.WesternUnion.controlador;

import com.api.westernunion.WesternUnion.modelo.TransferenciaRequest;
import com.api.westernunion.WesternUnion.servicio.TransferenciaService;
import com.api.westernunion.WesternUnion.servicio.NotificacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transferencia")
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping("/banco")
    public ResponseEntity<Map<String, Object>> realizarTransferencia(@RequestBody Map<String, Object> request) {
        try {
            // Obtener datos de la solicitud
            TransferenciaRequest transferencia = convertToTransferenciaRequest(request.get("transferencia"));
            String metodoNotificacion = (String) request.get("metodoNotificacion");

            // Validar los datos de la transferencia
            List<String> errores = transferenciaService.validarTransferencia(transferencia);
            if (!errores.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("errores", errores));
        }

        // Calcular costos
        String paisDestino = transferencia.getDestinatario().getPais();
        double monto = transferencia.getMonto();
        Map<String, Object> costos = transferenciaService.calcularCostos(paisDestino, monto);

        // Simular la realización de la transferencia
        String idTransaccion = "TX" + System.currentTimeMillis();

        // Enviar notificación
        String destinatario = transferencia.getDestinatario().getNombre();
        String resultadoNotificacion = notificacionService.enviarNotificacion(metodoNotificacion, destinatario, idTransaccion);

        // Construir respuesta unificada
        Map<String, Object> respuesta = Map.of(
                "estado", "Éxito",
                "mensaje", "Transferencia realizada correctamente",
                "idTransaccion", idTransaccion,
                "costos", costos,
                "notificacion", resultadoNotificacion
        );

        return ResponseEntity.ok(respuesta);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Método para convertir TransferenciaRequest
    private TransferenciaRequest convertToTransferenciaRequest(Object data) {
        if (data instanceof Map) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(data, TransferenciaRequest.class);
        }
        throw new IllegalArgumentException("Transferencia inválida");
    }
}