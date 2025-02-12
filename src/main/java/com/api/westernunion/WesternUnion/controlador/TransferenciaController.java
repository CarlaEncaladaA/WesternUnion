package com.api.westernunion.WesternUnion.controlador;

import com.api.westernunion.WesternUnion.modelo.TransferenciaRequest;
import com.api.westernunion.WesternUnion.modelo.TransferenciaRequest.Destinatario;
import com.api.westernunion.WesternUnion.modelo.TransferenciaRequest.Remitente;
import com.api.westernunion.WesternUnion.servicio.TransferenciaService;
import com.api.westernunion.WesternUnion.servicio.NotificacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/transferencia")
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @Autowired
    private NotificacionService notificacionService;
    
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> listarTransferencias() {
        List<TransferenciaRequest> transferencias = new ArrayList<TransferenciaRequest>();
        
        // Lista de nombres de destinatarios para hacerlos diferentes
        List<String> nombresDestinatarios = Arrays.asList(
            "Juan Alpes", "Maria Gonzalez", "Carlos Rojas", "Luisa Fernandez", "Pedro Ramirez",
            "Sofia Morales", "Javier Peña", "Andrea Castro", "Fernando Diaz", "Gabriela Torres"
        );

        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            TransferenciaRequest transferencia = new TransferenciaRequest();

            // Crear y asignar un destinatario diferente
            Destinatario destinatario = new Destinatario();
            destinatario.setNombre(nombresDestinatarios.get(i)); // Diferentes nombres
            destinatario.setPais("USA");
            destinatario.setNumeroCuenta("12345678" + i);
            destinatario.setCodigoPais("US");
            destinatario.setCodigoPostal("1000" + i);
            destinatario.setDescripcionDestino("Cuenta bancaria en USA");

            // Crear y asignar el remitente (mismo remitente en todas)
            Remitente remitente = new Remitente();
            remitente.setNombre("Pepito Lopez");
            remitente.setEmail("pepito@example.com");
            remitente.setTelefono("+123456789");
            remitente.setDireccion("123 Calle Falsa, Springfield");

            // Asignar remitente y destinatario a la transferencia
            transferencia.setDestinatario(destinatario);
            transferencia.setRemitente(remitente);

            // Generar monto aleatorio entre 100 y 1000
            double montoAleatorio = 100 + (900 * random.nextDouble());
            transferencia.setMonto(montoAleatorio);

            transferencias.add(transferencia);
        }

        // Crear respuesta en formato JSON
        Map<String, Object> response = new HashMap<>();
        response.put("total", transferencias.size());
        response.put("transferencias", transferencias);

        return ResponseEntity.ok(response);
    }

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