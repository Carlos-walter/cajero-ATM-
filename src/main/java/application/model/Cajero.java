package application.model;

import java.util.*;

public class Cajero {
    private static Cajero instancia;
    private Usuario usuarioActual;
    private int intentos;
    private final List<Cuenta> cuentas;
    private final Map<Billetes, Integer> billetes;
    private final List<Usuario> usuarios;
    private final Auditoria auditoria;

    private Cajero(List<Usuario> usuarios) {
        auditoria = new Auditoria();
        this.usuarios = usuarios;
        this.cuentas = new ArrayList<>();
        for (Usuario u : usuarios) {
            this.cuentas.addAll(u.getCuentas());
        }
        this.usuarioActual = null;
        this.intentos = 0;

        billetes = new HashMap<>();
        for (Billetes b : Billetes.values()) {
            billetes.put(b, 250);
        }
    }

    public static void inicializar(List<Usuario> usuarios) {
        if (instancia == null) {
            instancia = new Cajero(usuarios);
        }
    }


    public static Cajero getInstancia() {
        if (instancia == null) {
            throw new IllegalStateException("Cajero no ha sido inicializado");
        }
        return instancia;
    }


    public boolean autenticarUsuario(String dni, String pin) {
        if (intentos >= 3) {
            String nombreUser = (usuarioActual != null) ? usuarioActual.getNombre() : "DNI: " + dni;
            auditoria.registrarEvento(nombreUser + " intento fallido de autentificacion");
            return false;
        }
        for (Usuario usuario : usuarios) {
            if (usuario.getDni().equals(dni) && usuario.validarPin(pin)) {
                this.usuarioActual = usuario;
                this.intentos = 0;
                auditoria.registrarEvento(usuarioActual.getNombre() + " autenticado correctamente");
                return true;
            }
        }
        intentos++;
        return false;
    }

    public double consultarSaldo(String numeroCuenta) {
        if (usuarioActual == null) {
            return -1;
        }

        for (Cuenta cuenta : usuarioActual.getCuentas()) {
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                auditoria.registrarEvento(usuarioActual.getNombre() + " consulto su saldo");
                return cuenta.getSaldo();
            }
        }
        return -1;
    }

    public boolean cambiarPin(String nuevoPin) {
        if (usuarioActual == null) return false;
        try {
            usuarioActual.setPin(nuevoPin);
            auditoria.registrarEvento(usuarioActual.getNombre() + " modifico su pin");
            return true;
        } catch (IllegalArgumentException e) {
            auditoria.registrarEvento(usuarioActual.getNombre() + " fallo al modificar su pin");
            return false;
        }
    }

    private double calcularMonto(Map<Billetes, Integer> billetesDepositados) {
        double monto = 0;
        for (Billetes billete : billetesDepositados.keySet()) {
            monto += billete.getValor() * billetesDepositados.get(billete);
        }
        return monto;
    }

    private Map<Billetes, Integer> calcularBilletes(int monto) {
        Map<Billetes, Integer> resultado = new HashMap<>();

        for (Billetes billete : Billetes.values()) {
            int valor = billete.getValor();
            int disponibles = billetes.get(billete);
            int necesarios = monto / valor;
            int entregar = Math.min(necesarios, disponibles);

            if (entregar > 0) {
                resultado.put(billete, entregar);
                monto -= entregar * valor;
            }
        }
        if (monto != 0) {
            return null;
        }

        return resultado;
    }

    private boolean hayBilletesSuficientes(int monto) {
        return calcularBilletes(monto) != null;
    }

    private void actualizarBilletes(Map<Billetes, Integer> movimiento, int signo) {
        for (Billetes billete : movimiento.keySet()) {
            int stockActual = billetes.get(billete);
            int cantidad = movimiento.get(billete);

            billetes.put(billete, stockActual + (cantidad * signo));
        }
    }

    public Transaccion retirar(double monto) {
        if (usuarioActual == null) return null;

        if (!hayBilletesSuficientes((int) monto)) {
            System.out.println("El cajero no dispone de billetes suficientes");
            return null;
        }
        Cuenta cuenta = usuarioActual.getCuentaSeleccionada();
        Transaccion transaccion = cuenta.retirar(monto);

        if (transaccion != null) {
            Map<Billetes, Integer> entregados = calcularBilletes((int) monto);
            actualizarBilletes(Objects.requireNonNull(entregados), -1);
            auditoria.registrar(transaccion);
            transaccion.generarVoucher();
        }
        return transaccion;
    }

    public Transaccion depositar(Map<Billetes, Integer> billetesDepositados) {
        if (usuarioActual == null) return null;
        double monto = calcularMonto(billetesDepositados);

        Cuenta cuenta = usuarioActual.getCuentaSeleccionada();
        Transaccion transaccion = cuenta.depositar(monto);
        if (transaccion != null) {
            actualizarBilletes(billetesDepositados, 1);
            auditoria.registrar(transaccion);
        }

        return transaccion;
    }

    public List<Transaccion> verHistorial(int dias) {
        if (usuarioActual == null) return new ArrayList<>();
        return usuarioActual.getHistorialReciente(this.auditoria, dias);
    }

    public Transaccion transferir(String numOrigen, String numDestino, double monto) {
        String nombreUser = (usuarioActual != null) ? usuarioActual.getNombre() : "Usuario Anónimo";
        Cuenta cuentaOrigen = null;
        if (usuarioActual != null) {
            for (Cuenta c : usuarioActual.getCuentas()) {
                if (c.getNumeroCuenta().equals(numOrigen)) {
                    cuentaOrigen = c;
                    break;
                }
            }
        }

        Cuenta cuentaDestino = null;
        for (Usuario u : usuarios) {
            for (Cuenta c : u.getCuentas()) {
                if (c.getNumeroCuenta().equals(numDestino)) {
                    cuentaDestino = c;
                    break;
                }
            }
        }

        if (cuentaOrigen == null || cuentaDestino == null) {
            System.out.println("Cuenta origen o destino no encontrada");
            auditoria.registrarEvento(nombreUser + " fallo al transferir (Cuenta no encontrada)");
            return null;
        }

        if (monto <= 0) {
            System.out.println("Monto inválido");
            auditoria.registrarEvento(nombreUser + " fallo al transferir (Monto inválido: $" + monto + ")");
            return null;
        }

        if (cuentaOrigen.getSaldo() < monto) {
            System.out.println("Fondos insuficientes");
            auditoria.registrarEvento(nombreUser + " fallo al transferir (Fondos insuficientes para transferir $" + monto + ")");
            return null;
        }

        cuentaOrigen.descontarSaldo(monto);
        cuentaDestino.agregarSaldo(monto);

        Transaccion transferencia = new Transaccion(TipoTransaccion.transferencia, monto, numOrigen, numDestino);

        cuentaOrigen.agregarTransaccion(transferencia);
        cuentaDestino.agregarTransaccion(transferencia);

        auditoria.registrar(transferencia);
        return transferencia;
    }

    public Usuario buscarUsuarioPorDNI(String dni) {
        String dniNormalizado = dni.trim();
        String nombreUser = (usuarioActual != null) ? usuarioActual.getNombre() : "Usuario Anónimo";
        for (Usuario usuario : this.usuarios) {
            if (usuario.getDni().equalsIgnoreCase(dniNormalizado)) {
                auditoria.registrarEvento(usuarioActual.getNombre() + " busco al usuario: " + usuario);
                return usuario;
            }
        }
        auditoria.registrarEvento(nombreUser + " fallo al buscar a un usuario");

        return null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public boolean estaAutenticado() {
        return usuarioActual != null;
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
        this.intentos = 0;
    }

    public int getIntentos() {
        return intentos;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}