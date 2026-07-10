package application.model;
import org.bson.Document;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Transaccion {
    private final TipoTransaccion tipoTransaccion;
    private final LocalDateTime fechaTransaccion;
    private final double monto;
    private final String cuentaOrigen;
    private final String cuentaDestino;
    private final String id;

    public Transaccion(TipoTransaccion tipoTransaccion, double monto, String cuentaOrigen, String cuentaDestino) {
        this.id = generarID();
        this.fechaTransaccion = LocalDateTime.now();
        this.tipoTransaccion = tipoTransaccion;
        this.monto = monto;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
    }

    public Transaccion(String id, TipoTransaccion tipoTransaccion, LocalDateTime fechaTransaccion, double monto, String cuentaOrigen, String cuentaDestino) {
        this.id = id;
        this.fechaTransaccion = fechaTransaccion;
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
        return String.format("[%s] %s - %s - S/ %.2f", getId(), getFechaTransaccion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), getTipoTransaccion(), getMonto());
    }

    public String generarVoucher() {
        StringBuilder voucher = new StringBuilder();
        voucher.append("-----------------------------------\n")
                .append("      COMPROBANTE DE OPERACIÓN\n")
                .append("----------------------------------\n")
                .append("ID: ").append(getId()).append("\n")
                .append("TIPO: ").append(getTipoTransaccion()).append("\n")
                .append("FECHA: ")
                .append(getFechaTransaccion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))).append("\n")
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

    public Document toDocument() {
        return new Document("_id", this.id)
                .append("tipoTransaccion", this.tipoTransaccion.toString())
                .append("fechaTransaccion", Date.from(this.fechaTransaccion.atZone(ZoneId.systemDefault()).toInstant()))
                .append("monto", this.monto)
                .append("cuentaOrigen", this.cuentaOrigen)
                .append("cuentaDestino", this.cuentaDestino != null ? this.cuentaDestino : "CAJERO");
    }
}
