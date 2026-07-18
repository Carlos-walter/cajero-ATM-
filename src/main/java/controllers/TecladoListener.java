package controllers;

public interface TecladoListener {
    void onDigito(String digito);
    void onBorrar();
    void onEntrar();
}