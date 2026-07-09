package application.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// POO hola profe

public class Auditoria {
    private final List<Transaccion> transacciones;
    private List<String> eventos;

    public Auditoria() {
       transacciones = new ArrayList<>();
       eventos = new ArrayList<>();
    }

    public void registrar(Transaccion transaccion) {
        if (transaccion != null) {
            transacciones.add(transaccion);
        }
    }

    public void registrarEvento(String evento) {
        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String registro = String.format(
                "[%s] %s", LocalDateTime.now().format(formato), evento
        );

        eventos.add(registro);
    }

    public String generarReporteDiario() {

        List<Transaccion> transaccionesHoy = filtrarPorFecha(LocalDate.now());

        StringBuilder reporte = new StringBuilder();
        reporte.append("----- REPORTE DIARIO -----\n");
        reporte.append("Fecha: ").append(LocalDate.now())
                .append("\n\n");
        if (transaccionesHoy.isEmpty()) {
            reporte.append("No se registraron operaciones hoy");
            return reporte.toString();
        }

        for (Transaccion t : transaccionesHoy) {
            reporte.append(t).append("\n");
        }
        reporte.append("\nTotal operaciones: ").append(transaccionesHoy.size());

        return reporte.toString();
    }

    public List<Transaccion> filtrarPorFecha(LocalDate fecha) {
        List<Transaccion> resultado = new ArrayList<>();
        for (Transaccion t : transacciones) {
            if (t.getFechaTransaccion().toLocalDate().equals(fecha)) {
                resultado.add(t);
            }
        }
        return resultado;
    }

    public int totalOperaciones() {
        return transacciones.size();
    }
}
