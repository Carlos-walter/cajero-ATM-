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
    private final MongoCollection<Document> coleccionTransacciones;
    private final MongoCollection<Document> coleccionEventos;

    public Auditoria() {
        transacciones = new ArrayList<>();
        eventos = new ArrayList<>();
        this.coleccionTransacciones = MongoManager.getInstancia().getColeccion("transacciones");
        this.coleccionEventos = MongoManager.getInstancia().getColeccion("eventos_auditoria");
    }

    private Transaccion documentToTransaccion(Document doc) {
        String id = doc.getString("_id");
        double monto = doc.getDouble("monto");
        String origen = doc.getString("cuentaOrigen");
        String destino = doc.getString("cuentaDestino");
        TipoTransaccion tipo = TipoTransaccion.valueOf(doc.getString("tipoTransaccion"));

        Date dateMongo = doc.getDate("fechaTransaccion");

        LocalDateTime fechaTransaccion = LocalDateTime.ofInstant(dateMongo.toInstant(), ZoneId.systemDefault());

        return new Transaccion(id, tipo, fechaTransaccion, monto, origen, destino);
    }

    private Date toDate(LocalDate fecha) {
        return Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private Date toDate(LocalDateTime fechaHora) {
        return Date.from(fechaHora.atZone(ZoneId.systemDefault()).toInstant());
    }

    public void registrar(Transaccion transaccion) {
        if (transaccion != null) {
            transacciones.add(transaccion);
            try {
                coleccionTransacciones.insertOne(transaccion.toDocument());
            } catch (Exception e) {
                System.err.println("No se pudo respaldar la transacción en Mongo DB, detalle del error: " + e.getMessage());
            }
        }
    }

    public void registrarEvento(String evento) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaStr = LocalDateTime.now().format(formato);
        String registro = String.format("[%s] %s", fechaStr, evento);
        eventos.add(registro);
        try {
            Document documentoEvento = new Document("fecha", toDate(LocalDateTime.now()))
                    .append("descripcion", evento)
                    .append("registroFormateado", registro);
            coleccionEventos.insertOne(documentoEvento);
        } catch (Exception e) {
            System.err.println("Hubo un error al guardar el evento en la nube " + e.getMessage());
        }
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

        Date dateInicio = toDate(fecha);
        Date dateFin = toDate(fecha.atTime(LocalTime.MAX));

        try {
            for (Document doc : coleccionTransacciones.find(Filters.and(
                    Filters.gte("fechaTransaccion", dateInicio),
                    Filters.lte("fechaTransaccion", dateFin)
            ))) {
                resultado.add(documentToTransaccion(doc));
            }
        } catch (Exception e) {
            System.err.println("Hubo un error al filtrar " + e.getMessage());
        }

        return resultado;
    }

    public List<Transaccion> obtenerHistorialPorCuentas(List<String> numerosCuentas, int dias) {
        List<Transaccion> historial = new ArrayList<>();
        Date dateBson = toDate(LocalDate.now().minusDays(dias));

        try {
            Bson filtroCuentas = Filters.or(
                    Filters.in("cuentaOrigen", numerosCuentas),
                    Filters.in("cuentaDestino", numerosCuentas));
            Bson filtroFecha = Filters.gte("fechaTransaccion", dateBson);
            Bson filtroFinal = Filters.and(filtroCuentas, filtroFecha);

            for (Document doc : coleccionTransacciones.find(filtroFinal)) {
                historial.add(documentToTransaccion(doc));
            }

        } catch (Exception e) {
            System.err.println("Hubo un error al obtener el historial " + e.getMessage());
        }
        return historial;
    }

    public int totalOperaciones() {
        return transacciones.size();
    }
}