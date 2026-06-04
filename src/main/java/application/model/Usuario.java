package application.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nombre;
    private String pin;
    private String dni;
    private List<Cuenta> cuentas;
    private Cuenta cuentaSeleccionada;

    public Usuario(String nombre, String pin, String dni) {
        if (pin == null || !pin.matches("\\d{4}")) {
            throw new IllegalArgumentException("El PIN debe tener exactamente 4 dígitos numéricos");
        }

        if (dni == null || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI no puede estar vacío");
        }
        this.nombre = nombre;
        this.pin = pin;
        this.dni = dni;
        this.cuentas = new ArrayList<>();
    }


    public String getDni() {
        return dni;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public String getNombre() {
        return nombre;
    }

    public Cuenta getCuentaSeleccionada() {
        if (cuentaSeleccionada == null && !cuentas.isEmpty()) {
            cuentaSeleccionada = cuentas.get(0);
        }
        return cuentaSeleccionada;
    }

    public void setCuentaSeleccionada(Cuenta cuenta) {
        if (cuentas.contains(cuenta)) {
            this.cuentaSeleccionada = cuenta;
        }
    }

    public boolean setCuentaSeleccionada(String numeroCuenta) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                this.cuentaSeleccionada = cuenta;
                return true;
            }
        }
        return false;
    }

    public boolean validarPin(String pinIngresado){
        return pinIngresado.equals(pin);
    }

    protected void setPin(String nuevoPin) {
        if (nuevoPin == null || !nuevoPin.matches("\\d{4}")) {
            throw new IllegalArgumentException("El PIN debe tener exactamente 4 dígitos numéricos");
        }
        this.pin = nuevoPin;
    }

    public void agregarCuenta(Cuenta cuenta) {
        if (cuenta != null) {
            cuentas.add(cuenta);
        }
    }

    public List<Transaccion> getHistorialReciente(int dias) {
        List<Transaccion> historial = new ArrayList<>();
        for (Cuenta cuenta : cuentas) {
            historial.addAll(cuenta.getTransaccionesRecientes(dias));
        }
        return historial;
    }

    @Override
    public String toString() {
        return "Usuario: " + nombre +
                " | DNI: " + dni +
                " | Cuentas: " + cuentas.size();
    }
}