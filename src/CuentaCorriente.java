import java.time.LocalDate;

public class CuentaCorriente extends Cuenta {
    private static final long serialVersionUID = 1L;

    private int transacciones;
    private double importeTransaccion;

    public CuentaCorriente() {
        super();
    }

    public CuentaCorriente(String numCuenta, String nombreCliente, double saldo,
                           int transacciones, double importeTransaccion) {
        super(numCuenta, nombreCliente, saldo);
        this.transacciones = transacciones;
        this.importeTransaccion = importeTransaccion;
    }

    public int getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(int transacciones) {
        this.transacciones = transacciones;
    }

    public double getImporteTransaccion() {
        return importeTransaccion;
    }

    public void setImporteTransaccion(double importeTransaccion) {
        this.importeTransaccion = importeTransaccion;
    }

    public void incrementarTransacciones() {
        this.transacciones++;
    }

    @Override
    public void comisiones() {
        // Se aplican si el día es primero del mes
        LocalDate hoy = LocalDate.now();
        if (hoy.getDayOfMonth() == 1) {
            double comision = transacciones * importeTransaccion;
            cargar(comision);
            transacciones = 0; // Resetear transacciones del mes
        }
    }

    @Override
    public void intereses() {
        // Se aplica si el día es primero del mes
        LocalDate hoy = LocalDate.now();
        if (hoy.getDayOfMonth() == 1) {
            double interes = 0;

            if (saldo > 20000.00) {
                interes = saldo * 0.10; // 10%
            } else if (saldo >= 5000.00 && saldo <= 10000.00) {
                interes = saldo * 0.05; // 5%
            }

            if (interes > 0) {
                abonar(interes);
            }
        }
    }

    @Override
    public String getTipoCuenta() {
        return "Corriente";
    }

    @Override
    public String toString() {
        return String.format("%s | Tipo: Corriente | Transacciones: %d | Importe Trans: $%.2f",
                super.toString(), transacciones, importeTransaccion);
    }
}