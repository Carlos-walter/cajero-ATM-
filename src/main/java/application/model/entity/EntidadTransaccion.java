package application.model.entity;

import application.model.TipoTransaccion;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "Transaccion")
public class EntidadTransaccion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;



    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoTransaccion tipo;



    @Column(name = "fecha_transaccion")
    private LocalDateTime fecha;



    @Column(name = "monto")
    private double monto;



    @ManyToOne
    @JoinColumn(name = "cuenta_origen")
    private EntidadCuenta cuentaOrigen;



    @ManyToOne
    @JoinColumn(name = "cuenta_destino")
    private EntidadCuenta cuentaDestino;




    public EntidadTransaccion() {
    }



    public EntidadTransaccion(
            TipoTransaccion tipo,
            double monto,
            EntidadCuenta cuentaOrigen,
            EntidadCuenta cuentaDestino) {


        this.tipo = tipo;
        this.fecha = LocalDateTime.now();
        this.monto = monto;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
    }




    public Integer getId() {
        return id;
    }



    public TipoTransaccion getTipo() {
        return tipo;
    }


    public void setTipo(TipoTransaccion tipo) {
        this.tipo = tipo;
    }



    public LocalDateTime getFecha() {
        return fecha;
    }


    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }



    public double getMonto() {
        return monto;
    }


    public void setMonto(double monto) {
        this.monto = monto;
    }



    public EntidadCuenta getCuentaOrigen() {
        return cuentaOrigen;
    }


    public void setCuentaOrigen(EntidadCuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }



    public EntidadCuenta getCuentaDestino() {
        return cuentaDestino;
    }


    public void setCuentaDestino(EntidadCuenta cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }



    @Override
    public String toString() {

        return "EntidadTransaccion{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", fecha=" + fecha +
                ", monto=" + monto +
                '}';
    }
}