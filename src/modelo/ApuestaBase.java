package modelo;

public abstract class ApuestaBase {
    protected int monto;
    protected String seleccion;

    public ApuestaBase(int monto, String seleccion) {
        this.monto = monto;
        this.seleccion = seleccion;
    }

    public int getMonto() { return monto; }
    public String getSeleccion() { return seleccion; }

    // --- MÉTODOS POLIMÓRFICOS ---
    // Cada hija decidirá cómo se gana y cuánto paga
    public abstract boolean esGanadora(int numeroGanador, String colorGanador);
    public abstract int calcularPremio();
    public abstract String getNombreTipo();
}