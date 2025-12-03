public class MiMoto extends Prestamo {
    private static final long serialVersionUID = 1L;

    private double porcentajeEnganche;

    public MiMoto() {
        super();
    }

    public MiMoto(String numPrestamo, String cliente, double montoPrestamo, int plazoMeses, double porcentajeEnganche) {

        super(numPrestamo, cliente, montoPrestamo, 15.0, plazoMeses);

        // 2. Asignamos el atributo propio de esta clase
        this.porcentajeEnganche = porcentajeEnganche;

        // 3. Calculamos el saldo final
        calculaPrestamo();
    }

    public double getPorcentajeEnganche() {
        return porcentajeEnganche;
    }

    public void setPorcentajeEnganche(double porcentajeEnganche) {
        this.porcentajeEnganche = porcentajeEnganche;
    }

    @Override
    public void calculaPrestamo() {
        // Lógica de negocio para MiMoto:
        // El enganche se resta del monto original, y se cobran intereses sobre el resto.

        double montoEnganche = montoPrestamo * (porcentajeEnganche / 100.0);
        double montoAFinanciar = montoPrestamo - montoEnganche;

        // Cálculo de interés anual simple sobre lo que queda por pagar
        double interesTotal = montoAFinanciar * (tasaInteres / 100.0) * (plazoMeses / 12.0);

        // El saldo del préstamo es lo que falta pagar + los intereses
        this.saldoPrestamo = montoAFinanciar + interesTotal;
    }

    @Override
    public String getTipoPrestamo() {
        return "MiMoto";
    }

    @Override
    public String toString() {
        return String.format("%s | Tipo: MiMoto | Enganche: %.2f%% | Tasa: %.2f%% | Mensualidad: $%.2f",
                super.toString(), porcentajeEnganche, tasaInteres, calcularMensualidad());
    }
}