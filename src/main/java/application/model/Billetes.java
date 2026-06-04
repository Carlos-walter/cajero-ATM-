package application.model;

public enum Billetes {
    B10(10),
    B20(20),
    B50(50),
    B100(100),
    B200(200);

    private final int valor;

    Billetes(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
