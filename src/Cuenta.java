import java.io.Serializable;
import java.time.LocalDate;

public abstract class Cuenta implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String numCuenta;
    protected String nombreCliente;
    protected double saldo;

    public Cuenta() {
    }

    public Cuenta(String numCuenta, String nombreCliente, double saldo) {
        this.numCuenta = numCuenta;
        this.nombreCliente = nombreCliente;
        this.saldo = saldo;
    }

    // Getters y Setters
    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    // Método para abonar al saldo
    public void abonar(double cantidad) {
        if (cantidad > 0) {
            this.saldo += cantidad;
        }
    }

    // Método para cargar (restar) al saldo
    public void cargar(double cantidad) {
        if (cantidad > 0 && this.saldo >= cantidad) {
            this.saldo -= cantidad;
        }
    }

    // Métodos abstractos que deben implementarse en las subclases
    public abstract void comisiones();
    public abstract void intereses();

    // Método para obtener el tipo de cuenta
    public abstract String getTipoCuenta();

    @Override
    public String toString() {
        return String.format("Cuenta: %s | Cliente: %s | Saldo: $%.2f",
                numCuenta, nombreCliente, saldo);
    }
}