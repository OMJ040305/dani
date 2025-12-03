import java.util.ArrayList;
import java.util.List;

public class GestorPrestamos {
    private List<Prestamo> prestamos;

    public GestorPrestamos() {
        prestamos = new ArrayList<>();
    }


    public boolean altaPrestamo(Prestamo prestamo) {
        try {
            // Verificar que no exista un préstamo con el mismo número
            if (buscarPrestamoPorNumero(prestamo.getNumPrestamo()) != null) {
                return false;
            }

            prestamos.add(prestamo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Dar de baja un préstamo
     */
    public boolean bajaPrestamo(String numPrestamo) {
        try {
            Prestamo prestamo = buscarPrestamoPorNumero(numPrestamo);
            if (prestamo == null) {
                return false;
            }

            prestamos.remove(prestamo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Buscar préstamo por número
     */
    public Prestamo buscarPrestamoPorNumero(String numPrestamo) {
        for (Prestamo p : prestamos) {
            if (p.getNumPrestamo().equals(numPrestamo)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Buscar todos los préstamos de un cliente
     */
    public List<Prestamo> buscarPrestamosPorCliente(String cliente) {
        List<Prestamo> prestamosCliente = new ArrayList<>();
        String clienteBuscar = cliente.toUpperCase().trim();

        for (Prestamo p : prestamos) {
            if (p.getCliente().toUpperCase().trim().equals(clienteBuscar)) {
                prestamosCliente.add(p);
            }
        }

        return prestamosCliente;
    }

    /**
     * Realizar un abono a un préstamo
     */
    public boolean realizarAbonoPrestamo(String numPrestamo, double monto) {
        try {
            Prestamo prestamo = buscarPrestamoPorNumero(numPrestamo);
            if (prestamo == null || monto <= 0 || monto > prestamo.getSaldoPrestamo()) {
                return false;
            }

            prestamo.abonar(monto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtener todos los préstamos
     */
    public List<Prestamo> getPrestamos() {
        return new ArrayList<>(prestamos);
    }

    /**
     * Obtener préstamos por tipo
     */
    public List<Prestamo> getPrestamosPorTipo(String tipo) {
        List<Prestamo> resultado = new ArrayList<>();

        for (Prestamo p : prestamos) {
            if (p.getTipoPrestamo().equalsIgnoreCase(tipo)) {
                resultado.add(p);
            }
        }

        return resultado;
    }

    /**
     * Obtener estadísticas de préstamos
     */
    public String obtenerEstadisticas() {
        if (prestamos.isEmpty()) {
            return "No hay préstamos registrados";
        }

        int totalOrdinarios = 0;
        int totalMiMoto = 0;
        int totalAutomotriz = 0;
        double montoTotal = 0;

        for (Prestamo p : prestamos) {
            montoTotal += p.getSaldoPrestamo();

            if (p instanceof Ordinario) {
                totalOrdinarios++;
            } else if (p instanceof MiMoto) {
                totalMiMoto++;
            } else if (p instanceof Automotriz) {
                totalAutomotriz++;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("ESTADÍSTICAS DE PRÉSTAMOS\n");
        sb.append("=========================\n");
        sb.append(String.format("Total de préstamos: %d\n", prestamos.size()));
        sb.append(String.format("Ordinarios: %d\n", totalOrdinarios));
        sb.append(String.format("MiMoto: %d\n", totalMiMoto));
        sb.append(String.format("Automotriz: %d\n", totalAutomotriz));
        sb.append(String.format("Monto total en préstamos: $%.2f\n", montoTotal));

        return sb.toString();
    }

    public int getTotalPrestamos() {
        return prestamos.size();
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = new ArrayList<>(prestamos);
    }
}