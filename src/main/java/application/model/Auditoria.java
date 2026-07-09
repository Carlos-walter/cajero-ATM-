package application.model;
import com.mongodb.client.model.Filters;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Auditoria {
    private final List<Transaccion> transacciones;
    private final List<String> eventos;
    private final MongoCollection<Document> coleccionTransacciones = MongoManager.getInstancia().getColeccion("transacciones");
    private final MongoCollection<Document> coleccionEventos = MongoManager.getInstancia().getColeccion("eventos_auditoria");

    public Auditoria() {
        transacciones = new ArrayList<>();
        eventos = new ArrayList<>();
    }

    public void registrar(Transaccion transaccion) {
        if (transaccion != null) {
            transacciones.add(transaccion);
            coleccionTransacciones.insertOne(transaccion.toDocument());
        }
    }

    public void registrarEvento(String evento) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaStr = LocalDateTime.now().format(formato);
        String registro = String.format("[%s] %s", fechaStr, evento);
        eventos.add(registro);

        Document documentoEvento = new Document("fecha", new Date())
                .append("descripcion", evento)
                .append("registroFormateado", registro);
        coleccionEventos.insertOne(documentoEvento);
    }

    public String generarReporteDiario() {
        List<Transaccion> transaccionesHoy = filtrarPorFecha(LocalDate.now());
        StringBuilder reporte = new StringBuilder();
        reporte.append("----- REPORTE DIARIO -----\n");
        reporte.append("Fecha: ").append(LocalDate.now()).append("\n\n");

        if (transaccionesHoy.isEmpty()) {
            reporte.append("No se registraron operaciones hoy");
            return reporte.toString();
        }
        for (Transaccion t : transaccionesHoy) {
            reporte.append(t).append("\n");
        }
        reporte.append("\nTotal operaciones hoy: ").append(transaccionesHoy.size());
        return reporte.toString();
    }

    public List<Transaccion> filtrarPorFecha(LocalDate fecha) {
        List<Transaccion> resultado = new ArrayList<>();

        LocalDateTime inicioDia = fecha.atStartOfDay();
        LocalDateTime finDia = fecha.atTime(LocalTime.MAX);

        Date dateInicio = Date.from(inicioDia.atZone(ZoneId.systemDefault()).toInstant());
        Date dateFin = Date.from(finDia.atZone(ZoneId.systemDefault()).toInstant());

        for (Document doc : coleccionTransacciones.find(Filters.and(
                Filters.gte("fechaTransaccion", dateInicio),
                Filters.lte("fechaTransaccion", dateFin)
        ))) {

            String id = doc.getString("_id");
            double monto = doc.getDouble("monto");
            String cuentaOrigen = doc.getString("cuentaOrigen");
            String cuentaDestino = doc.getString("cuentaDestino");
            TipoTransaccion tipo = TipoTransaccion.valueOf(doc.getString("tipoTransaccion"));

            Date dateMongo = doc.getDate("fechaTransaccion");
            LocalDateTime fechaTransaccion = LocalDateTime.ofInstant(dateMongo.toInstant(), ZoneId.systemDefault());

            Transaccion t = new Transaccion(id, tipo, fechaTransaccion, monto, cuentaOrigen, cuentaDestino);
            resultado.add(t);
        }

        return resultado;
    }

    public List<Transaccion> obtenerHistorialPorCuentas(List<String> numerosCuentas, int dias) {
        List<Transaccion> historial = new ArrayList<>();

        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(dias);
        Date dateLimiteBson = Date.from(fechaLimite.atZone(ZoneId.systemDefault()).toInstant());

        Bson filtroCuentas = Filters.or(
                Filters.in("cuentaOrigen", numerosCuentas),
                Filters.in("cuentaDestino", numerosCuentas));
        Bson filtroFecha = Filters.gte("fechaTransaccion", dateLimiteBson);
        Bson filtroFinal = Filters.and(filtroCuentas, filtroFecha);

        for (Document doc : coleccionTransacciones.find(filtroFinal)) {
            String id = doc.getString("_id");
            double monto = doc.getDouble("monto");
            String origen = doc.getString("cuentaOrigen");
            String destino = doc.getString("cuentaDestino");
            TipoTransaccion tipo = TipoTransaccion.valueOf(doc.getString("tipoTransaccion"));

            Date dateMongo = doc.getDate("fechaTransaccion");
            LocalDateTime fechaTransaccion = LocalDateTime.ofInstant(dateMongo.toInstant(), ZoneId.systemDefault());

            Transaccion t = new Transaccion(id, tipo, fechaTransaccion, monto, origen, destino);
            historial.add(t);
        }
        return historial;
    }
    public int totalOperaciones() {
        return transacciones.size();
    }
}