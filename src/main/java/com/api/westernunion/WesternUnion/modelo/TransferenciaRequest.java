
package com.api.westernunion.WesternUnion.modelo;


public class TransferenciaRequest {

    private Remitente remitente;
    private Destinatario destinatario;
    private double monto;

    // Getters y Setters para TransferenciaRequest
    public Remitente getRemitente() {
        return remitente;
    }

    public void setRemitente(Remitente remitente) {
        this.remitente = remitente;
    }

    public Destinatario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Destinatario destinatario) {
        this.destinatario = destinatario;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    // Clase Remitente
    public static class Remitente {
        private String nombre;
        private String email;
        private String telefono;
        private String direccion;

        // Getters y Setters para Remitente
        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }
    }

    // Clase Destinatario
    public static class Destinatario {
        private String nombre;
        private String pais;
        private String numeroCuenta;
        private String codigoPais;
        private String codigoPostal;
        private String descripcionDestino;

        // Getters y Setters para Destinatario
        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getPais() {
            return pais;
        }

        public void setPais(String pais) {
            this.pais = pais;
        }

        public String getNumeroCuenta() {
            return numeroCuenta;
        }

        public void setNumeroCuenta(String numeroCuenta) {
            this.numeroCuenta = numeroCuenta;
        }

        public String getCodigoPais() {
            return codigoPais;
        }

        public void setCodigoPais(String codigoPais) {
            this.codigoPais = codigoPais;
        }

        public String getCodigoPostal() {
            return codigoPostal;
        }

        public void setCodigoPostal(String codigoPostal) {
            this.codigoPostal = codigoPostal;
        }

        public String getDescripcionDestino() {
            return descripcionDestino;
        }

        public void setDescripcionDestino(String descripcionDestino) {
            this.descripcionDestino = descripcionDestino;
        }
    }
}