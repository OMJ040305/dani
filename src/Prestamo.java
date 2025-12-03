import java.io.Serializable;

/**
 * Clase base para todos los préstamos
 */
public abstract class Prestamo implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String numPrestamo;
    protected String cliente;
    protected double saldoPrestamo;
    protected double tasaInteres;
    protected int plazoMeses;
    protected double montoPrestamo; // Monto original

    public Prestamo() {
    }

    public Prestamo(String numPrestamo, String cliente, double montoPrestamo,
                    double tasaInteres, int plazoMeses) {
        this.numPrestamo = numPrestamo;
        this.cliente = cliente;
        this.montoPrestamo = montoPrestamo;
        this.tasaInteres = tasaInteres;
        this.plazoMeses = plazoMeses;
    }

    // Getters y Setters
    public String getNumPrestamo() {
        return numPrestamo;
    }

    public void setNumPrestamo(String numPrestamo) {
        this.numPrestamo = numPrestamo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public double getSaldoPrestamo() {
        return saldoPrestamo;
    }

    public void setSaldoPrestamo(double saldoPrestamo) {
        this.saldoPrestamo = saldoPrestamo;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public double getMontoPrestamo() {
        return montoPrestamo;
    }

    public void setMontoPrestamo(double montoPrestamo) {
        this.montoPrestamo = montoPrestamo;
    }

    public void abonar(double cantidad) {
        if (cantidad > 0 && cantidad <= saldoPrestamo) {
            this.saldoPrestamo -= cantidad;
        }
    }

    public abstract void calculaPrestamo();

    public abstract String getTipoPrestamo();

    public double calcularMensualidad() {
        if (plazoMeses > 0) {
            return saldoPrestamo / plazoMeses;
        }
        return 0;
    }

    @Override
    public String toString() {
        return String.format("Préstamo: %s | Cliente: %s | Saldo: $%.2f | Plazo: %d meses",
                numPrestamo, cliente, saldoPrestamo, plazoMeses);
    }
}