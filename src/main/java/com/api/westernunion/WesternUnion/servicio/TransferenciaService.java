
package com.api.westernunion.WesternUnion.servicio;

import com.api.westernunion.WesternUnion.modelo.TransferenciaRequest;

import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class TransferenciaService {

    private static final Map<String, List<String>> requisitosPorPais = new HashMap<>();
    private static final Map<String, double[]> tarifas = new HashMap<>();

    static {
        // Inicialización de requisitos por país
        requisitosPorPais.put("Colombia", Arrays.asList("nombreRemitente", "nombreDestinatario", "email", "telefono", "numeroCuenta"));
        requisitosPorPais.put("Perú", Arrays.asList("nombreRemitente", "nombreDestinatario", "direccion", "numeroCuenta", "codigoPostal"));
        requisitosPorPais.put("Chile", Arrays.asList("nombreRemitente", "nombreDestinatario", "telefono", "numeroCuenta", "descripcionDestino"));
        requisitosPorPais.put("Argentina", Arrays.asList("nombreRemitente", "nombreDestinatario", "telefono", "codigoPais", "descripcionDestino"));
        requisitosPorPais.put("Paraguay", Arrays.asList("nombreRemitente", "nombreDestinatario", "direccion", "numeroCuenta"));
        requisitosPorPais.put("Uruguay", Arrays.asList("nombreRemitente", "nombreDestinatario", "email", "codigoPais", "codigoPostal"));
        requisitosPorPais.put("Brasil", Arrays.asList("nombreRemitente", "nombreDestinatario", "direccion", "telefono", "numeroCuenta", "codigoPostal"));

        // Inicialización de tarifas
        tarifas.put("Colombia", new double[]{5, 0.01}); // Comisión, Impuesto
        tarifas.put("Perú", new double[]{15, 0.20});
        tarifas.put("Chile", new double[]{50, 0.05});
        tarifas.put("Argentina", new double[]{100, 0.10});
        tarifas.put("Paraguay", new double[]{20, 0.03});
        tarifas.put("Uruguay", new double[]{10, 0.04});
        tarifas.put("Brasil", new double[]{200, 0.08});
    }

    // Método para calcular costos
    public Map<String, Object> calcularCostos(String paisDestino, double monto) {
        if (!tarifas.containsKey(paisDestino)) {
            throw new IllegalArgumentException("País no soportado");
        }

        double comision = tarifas.get(paisDestino)[0];
        double impuesto = monto * tarifas.get(paisDestino)[1];
        double total = monto + comision + impuesto;

        return Map.of(
                "montoOriginal", monto,
                "comision", comision,
                "impuesto", impuesto,
                "total", total
        );
    }

    // Método para validar datos
    public List<String> validarTransferencia(TransferenciaRequest transferencia) {
        String pais = transferencia.getDestinatario().getPais();
        if (!requisitosPorPais.containsKey(pais)) {
            throw new IllegalArgumentException("País no soportado");
        }

        List<String> requisitos = requisitosPorPais.get(pais);
        List<String> errores = new ArrayList<>();

        if (requisitos.contains("nombreRemitente") && (transferencia.getRemitente().getNombre() == null || transferencia.getRemitente().getNombre().isEmpty())) {
            errores.add("El nombre del remitente es obligatorio");
        }
        if (requisitos.contains("nombreDestinatario") && (transferencia.getDestinatario().getNombre() == null || transferencia.getDestinatario().getNombre().isEmpty())) {
            errores.add("El nombre del destinatario es obligatorio");
        }
        if (requisitos.contains("email") && (transferencia.getRemitente().getEmail() == null || transferencia.getRemitente().getEmail().isEmpty())) {
            errores.add("El email del remitente es obligatorio");
        }
        if (requisitos.contains("telefono") && (transferencia.getRemitente().getTelefono() == null || transferencia.getRemitente().getTelefono().isEmpty())) {
            errores.add("El teléfono del remitente es obligatorio");
        }
        if (requisitos.contains("direccion") && (transferencia.getRemitente().getDireccion() == null || transferencia.getRemitente().getDireccion().isEmpty())) {
            errores.add("La dirección del remitente es obligatoria");
        }
        if (requisitos.contains("numeroCuenta") && (transferencia.getDestinatario().getNumeroCuenta() == null || transferencia.getDestinatario().getNumeroCuenta().isEmpty())) {
            errores.add("El número de cuenta del destinatario es obligatorio");
        }
        if (requisitos.contains("codigoPais") && (transferencia.getDestinatario().getCodigoPais() == null || transferencia.getDestinatario().getCodigoPais().isEmpty())) {
            errores.add("El código del país del destinatario es obligatorio");
        }
        if (requisitos.contains("codigoPostal") && (transferencia.getDestinatario().getCodigoPostal() == null || transferencia.getDestinatario().getCodigoPostal().isEmpty())) {
            errores.add("El código postal del destinatario es obligatorio");
        }
        if (requisitos.contains("descripcionDestino") && (transferencia.getDestinatario().getDescripcionDestino() == null || transferencia.getDestinatario().getDescripcionDestino().isEmpty())) {
            errores.add("La descripción del destino es obligatoria");
        }

        return errores;
    }
}
