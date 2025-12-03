import java.time.LocalDate;


public class CuentaAhorro extends Cuenta {
    private static final long serialVersionUID = 1L;

    private double cuotaMantenimiento;

    public CuentaAhorro() {
        super();
    }

    public CuentaAhorro(String numCuenta, String nombreCliente, double saldo, double cuotaMantenimiento) {
        super(numCuenta, nombreCliente, saldo);
        this.cuotaMantenimiento = cuotaMantenimiento;
    }

    public double getCuotaMantenimiento() {
        return cuotaMantenimiento;
    }

    public void setCuotaMantenimiento(double cuotaMantenimiento) {
        this.cuotaMantenimiento = cuotaMantenimiento;
    }

    @Override
    public void comisiones() {
        // Se aplican si el día es primero del mes
        LocalDate hoy = LocalDate.now();
        if (hoy.getDayOfMonth() == 1) {
            cargar(cuotaMantenimiento);
        }
    }

    @Override
    public void intereses() {
        // Se aplica si el día es primero del mes
        LocalDate hoy = LocalDate.now();
        if (hoy.getDayOfMonth() == 1) {
            double interes = saldo * 0.15; // 15% de interés
            abonar(interes);
        }
    }

    @Override
    public String getTipoCuenta() {
        return "Ahorro";
    }

    @Override
    public String toString() {
        return String.format("%s | Tipo: Ahorro | Cuota Mant: $%.2f",
                super.toString(), cuotaMantenimiento);
    }
}