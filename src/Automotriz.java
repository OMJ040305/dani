
public class Automotriz extends Prestamo {
    private static final long serialVersionUID = 1L;

    private double porcentajeComision;
    private double porcentajeIVA;

    public Automotriz() {
        super();
    }

    public Automotriz(String numPrestamo, String cliente, double montoPrestamo,
                      int plazoMeses, double porcentajeComision, double porcentajeIVA) {
        super(numPrestamo, cliente, montoPrestamo, 12.0, plazoMeses);
        this.porcentajeComision = porcentajeComision;
        this.porcentajeIVA = porcentajeIVA;
        calculaPrestamo();
    }

    public double getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(double porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    public double getPorcentajeIVA() {
        return porcentajeIVA;
    }

    public void setPorcentajeIVA(double porcentajeIVA) {
        this.porcentajeIVA = porcentajeIVA;
    }

    @Override
    public void calculaPrestamo() {
        // Calcular comisión por apertura
        double comision = montoPrestamo * (porcentajeComision / 100.0);

        // Calcular IVA sobre la comisión
        double iva = comision * (porcentajeIVA / 100.0);

        // Calcular interés anual
        double interesTotal = montoPrestamo * (tasaInteres / 100.0) * (plazoMeses / 12.0);

        // Saldo total = Monto + Intereses + Comisión + IVA
        saldoPrestamo = montoPrestamo + interesTotal + comision + iva;
    }

    @Override
    public String getTipoPrestamo() {
        return "Automotriz";
    }

    @Override
    public String toString() {
        return String.format("%s | Tipo: Automotriz | Tasa: %.2f%% | Comisión: %.2f%% | IVA: %.2f%% | Mensualidad: $%.2f",
                super.toString(), tasaInteres, porcentajeComision, porcentajeIVA, calcularMensualidad());
    }
}