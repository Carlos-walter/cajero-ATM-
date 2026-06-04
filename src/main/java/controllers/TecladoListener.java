package controllers;

// Cualquier pantalla que use el teclado DEBE implementar esto
public interface TecladoListener {
    void onDigito(String digito);
    void onBorrar();
    void onEntrar();
}