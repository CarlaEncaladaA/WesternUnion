package com.api.westernunion.WesternUnion;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class WesternUnionApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String API_TRANSFERENCIA = "/transferencia/banco";
    private final String API_COMISIONES = "/api/comisiones";

    @Test
    public void testValidarIntegracionAPI() throws Exception {
        String requestBody = """
            {
              "transferencia": {
                "remitente": {
                  "nombre": "Juan Pérez",
                  "email": "juan.perez@example.com",
                  "telefono": "123456789",
                  "direccion": "Av. Principal 123"
                },
                "destinatario": {
                  "nombre": "María López",
                  "pais": "Colombia",
                  "numeroCuenta": "987654321",
                  "codigoPais": "CO",
                  "codigoPostal": "110111",
                  "descripcionDestino": "Pago de servicios"
                },
                "monto": 500.0
              },
              "metodoNotificacion": "correo electrónico"
            }
        """;

        mockMvc.perform(post(API_TRANSFERENCIA)
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Transferencia realizada con éxito"));
    }

    @Test
    public void testManejoErrorAPI() throws Exception {
        String requestBody = """
            {
              "transferencia": {
                "remitente": {
                  "nombre": "",
                  "email": "",
                  "telefono": "",
                  "direccion": ""
                },
                "destinatario": {
                  "nombre": "",
                  "pais": "",
                  "numeroCuenta": "",
                  "codigoPais": "",
                  "codigoPostal": "",
                  "descripcionDestino": ""
                },
                "monto": -10.0
              },
              "metodoNotificacion": ""
            }
        """;

        mockMvc.perform(post(API_TRANSFERENCIA)
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    public void testCalculoComisiones() throws Exception {
        String requestBody = """
            {
              "pais": "Ecuador",
              "monto": 100.0
            }
        """;

        mockMvc.perform(post(API_COMISIONES)
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comision").exists());
    }
}
