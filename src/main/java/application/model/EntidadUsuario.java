package application.model;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Usuario")
public class EntidadUsuario {
    @Id
    @Column(name = "dni")
    private String dni;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "pin")
    private String pin;

    @OneToMany(mappedBy = "propietario")
    private List<EntidadCuenta> cuentas;

    public EntidadUsuario() {this(null,null,null,null);
    }

    public EntidadUsuario(String dni, String nombre, String pin, List<EntidadCuenta> cuentas) {
        this.dni = dni;
        this.nombre = nombre;
        this.pin = pin;
        this.cuentas = cuentas;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public List<EntidadCuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<EntidadCuenta> cuentas) {
        this.cuentas = cuentas;
    }

    @Override
    public String toString() {
        return "EntidadUsuario{" +
                "DNI='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", pin='" + pin + '\'' +
                ", cuentas=" + cuentas +
                ", cuentas=" + cuentas +
                '}';
    }
}