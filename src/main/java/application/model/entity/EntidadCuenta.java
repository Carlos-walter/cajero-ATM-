package application.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "Cuenta")
public class EntidadCuenta {


    @Id
    @Column(name = "numero_cuenta")
    private String numero_Cuenta;


    @Column(name = "saldo")
    private double saldo;


    @Column(name = "tipo")
    private String tipo;


    @ManyToOne
    @JoinColumn(name = "dni_usuario")
    private EntidadUsuario propietario;



    public EntidadCuenta() {
    }



    public EntidadCuenta(String numero_Cuenta,
                         double saldo,
                         String tipo,
                         EntidadUsuario propietario) {

        this.numero_Cuenta = numero_Cuenta;
        this.saldo = saldo;
        this.tipo = tipo;
        this.propietario = propietario;
    }



    public String getNumero_Cuenta() {
        return numero_Cuenta;
    }


    public void setNumero_Cuenta(String numero_Cuenta) {
        this.numero_Cuenta = numero_Cuenta;
    }



    public double getSaldo() {
        return saldo;
    }


    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }



    public String getTipo() {
        return tipo;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }



    public EntidadUsuario getPropietario() {
        return propietario;
    }


    public void setPropietario(EntidadUsuario propietario) {
        this.propietario = propietario;
    }



    @Override
    public String toString() {

        return "EntidadCuenta{" +
                "numero_Cuenta='" + numero_Cuenta + '\'' +
                ", saldo=" + saldo +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}