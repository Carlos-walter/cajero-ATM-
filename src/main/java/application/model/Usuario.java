package application.model;

import application.model.entity.EntidadUsuario;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private final String nombre;
    private String pin;
    private final String dni;
    private final List<Cuenta> cuentas;
    private Cuenta cuentaSeleccionada;

    // Constructor actual
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

    // Constructor para cargar desde SQL (JPA)
    public Usuario(EntidadUsuario entidad) {

        this.nombre = entidad.getNombre();
        this.pin = entidad.getPin();
        this.dni = entidad.getDni();

        this.cuentas = new ArrayList<>();

        if (entidad.getCuentas() != null) {

            for (var cuentaEntidad : entidad.getCuentas()) {

                Cuenta cuenta = new Cuenta(
                        cuentaEntidad.getNumero_Cuenta(),
                        cuentaEntidad.getTipo(),
                        cuentaEntidad.getSaldo(),
                        this
                );

                this.cuentas.add(cuenta);
            }
        }

        // Seleccionar primera cuenta automáticamente
        if (!cuentas.isEmpty()) {
            this.cuentaSeleccionada = cuentas.get(0);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getPin() {
        return pin;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public Cuenta getCuentaSeleccionada() {
        if (cuentaSeleccionada == null && !cuentas.isEmpty()) {
            cuentaSeleccionada = cuentas.getFirst();
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

    public boolean validarPin(String pinIngresado) {
        return pin.equals(pinIngresado);
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

    public List<Transaccion> getHistorialReciente(Auditoria auditoria, int dias) {

        List<String> numerosCuentas = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            numerosCuentas.add(cuenta.getNumeroCuenta());
        }

        if (numerosCuentas.isEmpty()) {
            return new ArrayList<>();
        }

        return auditoria.obtenerHistorialPorCuentas(numerosCuentas, dias);
    }

    @Override
    public String toString() {
        return "Usuario: " + nombre +
                " | DNI: " + dni +
                " | Cuentas: " + cuentas.size();
    }
}