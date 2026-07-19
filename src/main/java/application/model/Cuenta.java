package application.model;

import application.model.dao.CuentaDAO;
import application.model.entity.EntidadCuenta;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Cuenta {

    private final String numeroCuenta;
    private double saldo;
    private final List<Transaccion> transacciones;
    private final Usuario propietario;
    private final String tipo;

    private final CuentaDAO cuentaDAO;


    public Cuenta(String numeroCuenta,
                  String tipo,
                  double saldo,
                  Usuario propietario) {

        this.numeroCuenta = numeroCuenta;
        this.tipo = tipo;
        this.saldo = saldo;
        this.propietario = propietario;
        this.transacciones = new ArrayList<>();

        this.cuentaDAO = new CuentaDAO();
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



    public String getTipo() {
        return tipo;
    }




    public Transaccion depositar(double monto) {


        if(monto <= 0){

            System.out.println("Monto inválido");

            return null;
        }


        saldo += monto;


        actualizarSaldoSQL();



        Transaccion transaccion =
                new Transaccion(
                        TipoTransaccion.deposito,
                        monto,
                        "CAJERO",
                        numeroCuenta
                );


        transacciones.add(transaccion);



        return transaccion;
    }





    public Transaccion retirar(double monto) {


        if(monto <= 0 || saldo < monto){

            System.out.println(
                    "Saldo insuficiente"
            );

            return null;
        }


        saldo -= monto;



        actualizarSaldoSQL();



        Transaccion transaccion =
                new Transaccion(
                        TipoTransaccion.retiro,
                        monto,
                        numeroCuenta,
                        "EXTERNO"
                );


        transacciones.add(transaccion);



        return transaccion;

    }





    protected void descontarSaldo(double monto){


        saldo -= monto;


        actualizarSaldoSQL();

    }




    protected void agregarSaldo(double monto){


        saldo += monto;


        actualizarSaldoSQL();

    }






    private void actualizarSaldoSQL(){


        try {


            EntidadCuenta entidad =
                    cuentaDAO.buscarPorNumero(numeroCuenta);



            if(entidad != null){


                entidad.setSaldo(saldo);


                cuentaDAO.actualizar(entidad);

            }


        }catch(Exception e){


            System.err.println(
                    "Error actualizando saldo SQL: "
                            + e.getMessage()
            );

        }

    }







    public void agregarTransaccion(
            Transaccion transaccion
    ){

        if(transaccion != null){

            transacciones.add(transaccion);

        }

    }







    public List<Transaccion> getTransaccionesRecientes(
            int dias
    ){

        LocalDateTime limite =
                LocalDateTime.now()
                        .minusDays(dias);



        return transacciones.stream()

                .filter(t ->
                        t.getFechaTransaccion()
                                .isAfter(limite))

                .collect(Collectors.toList());

    }





    @Override
    public String toString() {

        return "Cuenta{" +
                "numeroCuenta='" + numeroCuenta + '\'' +
                ", saldo=" + saldo +
                ", tipo='" + tipo + '\'' +
                '}';

    }

}