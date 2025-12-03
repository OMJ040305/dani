
public class Busqueda {

    /**
     * Búsqueda binaria por número de cuenta
     * El arreglo debe estar ordenado previamente
     */
    public static Cuenta busquedaBinariaPorNumCuenta(Cuenta[] cuentas, int n, String numCuenta) {
        int inicio = 0;
        int fin = n - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            int comparacion = cuentas[medio].getNumCuenta().compareTo(numCuenta);

            if (comparacion == 0) {
                return cuentas[medio]; // Encontrado
            }

            if (comparacion < 0) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }

        return null; // No encontrado
    }

    /**
     * Búsqueda binaria por nombre de cliente
     * El arreglo debe estar ordenado previamente
     */
    public static Cuenta busquedaBinariaPorNombre(Cuenta[] cuentas, int n, String nombre) {
        int inicio = 0;
        int fin = n - 1;
        String nombreBuscar = nombre.toUpperCase().trim();

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            String nombreMedio = cuentas[medio].getNombreCliente().toUpperCase().trim();
            int comparacion = nombreMedio.compareTo(nombreBuscar);

            if (comparacion == 0) {
                return cuentas[medio]; // Encontrado
            }

            if (comparacion < 0) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }

        return null; // No encontrado
    }

    /**
     * Búsqueda lineal por número de cuenta (alternativa si no está ordenado)
     */
    public static Cuenta busquedaLinealPorNumCuenta(Cuenta[] cuentas, int n, String numCuenta) {
        for (int i = 0; i < n; i++) {
            if (cuentas[i].getNumCuenta().equals(numCuenta)) {
                return cuentas[i];
            }
        }
        return null;
    }

    /**
     * Búsqueda lineal por nombre de cliente
     */
    public static Cuenta busquedaLinealPorNombre(Cuenta[] cuentas, int n, String nombre) {
        String nombreBuscar = nombre.toUpperCase().trim();
        for (int i = 0; i < n; i++) {
            if (cuentas[i].getNombreCliente().toUpperCase().trim().equals(nombreBuscar)) {
                return cuentas[i];
            }
        }
        return null;
    }

    /**
     * Búsqueda de préstamo por número
     */
    public static Prestamo buscarPrestamoPorNum(Prestamo[] prestamos, int n, String numPrestamo) {
        for (int i = 0; i < n; i++) {
            if (prestamos[i] != null && prestamos[i].getNumPrestamo().equals(numPrestamo)) {
                return prestamos[i];
            }
        }
        return null;
    }

    /**
     * Buscar todos los préstamos de un cliente
     */
    public static Prestamo[] buscarPrestamosPorCliente(Prestamo[] prestamos, int n, String cliente) {
        // Contar préstamos del cliente
        int contador = 0;
        for (int i = 0; i < n; i++) {
            if (prestamos[i] != null &&
                    prestamos[i].getCliente().toUpperCase().trim().equals(cliente.toUpperCase().trim())) {
                contador++;
            }
        }

        // Si no hay préstamos, retornar arreglo vacío
        if (contador == 0) {
            return new Prestamo[0];
        }

        // Crear arreglo con los préstamos encontrados
        Prestamo[] prestamosCliente = new Prestamo[contador];
        int index = 0;
        for (int i = 0; i < n; i++) {
            if (prestamos[i] != null &&
                    prestamos[i].getCliente().toUpperCase().trim().equals(cliente.toUpperCase().trim())) {
                prestamosCliente[index++] = prestamos[i];
            }
        }

        return prestamosCliente;
    }
}