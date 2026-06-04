package application.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaccion {
    private TipoTransaccion tipoTransaccion;
    private LocalDateTime fechaTransaccion;
    private double monto;
    private String cuentaOrigen;
    private String cuentaDestino;
    private String id;

    public Transaccion(TipoTransaccion tipoTransaccion, double monto, String cuentaOrigen, String cuentaDestino) {
        this.id = generarID();
        this.fechaTransaccion = LocalDateTime.now();
        this.tipoTransaccion = tipoTransaccion;
        this.monto = monto;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
    }

    private String generarID() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("ddMMyyHHmmss");
        return LocalDateTime.now().format(formato);
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getFechaTransaccion() {
        return fechaTransaccion;
    }

    public TipoTransaccion getTipoTransaccion() {
        return tipoTransaccion;
    }

    public double getMonto() {
        return monto;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format("[%s] %s - %s - S/ %.2f",getId(), getFechaTransaccion(), getTipoTransaccion(), getMonto());
    }

    public String generarVoucher() {

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        StringBuilder voucher = new StringBuilder();

        voucher.append("-----------------------------------\n")
                .append("      COMPROBANTE DE OPERACIÓN\n")
                .append("----------------------------------\n")
                .append("ID: ").append(getId()).append("\n")
                .append("TIPO: ").append(getTipoTransaccion()).append("\n")
                .append("FECHA: ")
                .append(getFechaTransaccion()).append("\n")
                .append("MONTO: S/ ")
                .append(String.format("%.2f", getMonto())).append("\n")
                .append("CUENTA ORIGEN: ")
                .append(getCuentaOrigen()).append("\n");

        if (cuentaDestino != null && !cuentaDestino.equals("CAJERO")) {
            voucher.append("CUENTA DESTINO: ").append(getCuentaDestino())
                    .append("\n");
        } else {
            voucher.append("DESTINO: CAJERO AUTOMÁTICO\n");
        }
        voucher.append("----------------------------------\n");
        return voucher.toString();
    }

}
