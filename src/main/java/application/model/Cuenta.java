package application.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cuenta {
    private String numeroCuenta;
    private double saldo;
    private List<Transaccion> transacciones;
    private Usuario propietario;
    private String tipo;

    public Cuenta(String numeroCuenta, String tipo, double saldo, Usuario propietario) {
        this.numeroCuenta = numeroCuenta;
        this.tipo = tipo;
        this.saldo = saldo;
        this.propietario = propietario;
        this.transacciones = new ArrayList<>();
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public List<Transaccion> getTransacciones() {
        return new ArrayList<>(transacciones);
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public Transaccion depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
            Transaccion deposito = new Transaccion(TipoTransaccion.deposito, monto, "EXTERNO", numeroCuenta);
            transacciones.add(deposito);
            System.out.println("Depósito exitoso: $" + monto);
            return deposito;
        } else {
            System.out.println("Monto insuficiente");
            return null;
        }
    }

    public Transaccion retirar(double monto) {
        if (monto > 0 && saldo >= monto) {
            saldo -= monto;
            Transaccion retiro = new Transaccion(TipoTransaccion.retiro, monto, numeroCuenta, "CAJERO");
            transacciones.add(retiro);
            System.out.println("Retiro exitoso: $" + monto);
            return retiro;
        } else {
            System.out.println("Fondos insuficientes o monto inválido");
            return null;
        }
    }

    protected void descontarSaldo(double monto) {
        saldo -= monto;
    }

    protected void agregarSaldo(double monto) {
        saldo += monto;
    }

    public List<Transaccion> getTransaccionesRecientes(int dias) {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(dias);
        return transacciones.stream().filter(t -> t.getFechaTransaccion().isAfter(fechaLimite)).collect(Collectors.toList());
    }

    public void agregarTransaccion(Transaccion transaccion) {
        transacciones.add(transaccion);
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Cuenta: " + numeroCuenta + " - Saldo: $" + saldo +
                " - Transacciones: " + transacciones.size();
    }
}