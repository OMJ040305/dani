
public class Ordenamiento {

    public static void quickSortPorNumCuenta(Cuenta[] cuentas, int inicio, int fin, boolean ascendente) {
        if (inicio < fin) {
            int pivot = particionNumCuenta(cuentas, inicio, fin, ascendente);
            quickSortPorNumCuenta(cuentas, inicio, pivot - 1, ascendente);
            quickSortPorNumCuenta(cuentas, pivot + 1, fin, ascendente);
        }
    }

    private static int particionNumCuenta(Cuenta[] cuentas, int inicio, int fin, boolean ascendente) {
        String pivot = cuentas[fin].getNumCuenta();
        int i = inicio - 1;

        for (int j = inicio; j < fin; j++) {
            boolean condicion = ascendente ?
                    cuentas[j].getNumCuenta().compareTo(pivot) <= 0 :
                    cuentas[j].getNumCuenta().compareTo(pivot) >= 0;

            if (condicion) {
                i++;
                intercambiar(cuentas, i, j);
            }
        }
        intercambiar(cuentas, i + 1, fin);
        return i + 1;
    }


    public static void quickSortPorNombre(Cuenta[] cuentas, int inicio, int fin, boolean ascendente) {
        if (inicio < fin) {
            int pivot = particionNombre(cuentas, inicio, fin, ascendente);
            quickSortPorNombre(cuentas, inicio, pivot - 1, ascendente);
            quickSortPorNombre(cuentas, pivot + 1, fin, ascendente);
        }
    }

    private static int particionNombre(Cuenta[] cuentas, int inicio, int fin, boolean ascendente) {
        String pivot = cuentas[fin].getNombreCliente().toUpperCase();
        int i = inicio - 1;

        for (int j = inicio; j < fin; j++) {
            String nombreActual = cuentas[j].getNombreCliente().toUpperCase();
            boolean condicion = ascendente ?
                    nombreActual.compareTo(pivot) <= 0 :
                    nombreActual.compareTo(pivot) >= 0;

            if (condicion) {
                i++;
                intercambiar(cuentas, i, j);
            }
        }
        intercambiar(cuentas, i + 1, fin);
        return i + 1;
    }

    // ============ INSERCIÓN ============

    /**
     * Ordenamiento por Inserción por número de cuenta
     */
    public static void insercionPorNumCuenta(Cuenta[] cuentas, int n, boolean ascendente) {
        for (int i = 1; i < n; i++) {
            Cuenta key = cuentas[i];
            int j = i - 1;

            while (j >= 0 && compararNumCuenta(cuentas[j], key, ascendente)) {
                cuentas[j + 1] = cuentas[j];
                j--;
            }
            cuentas[j + 1] = key;
        }
    }

    private static boolean compararNumCuenta(Cuenta a, Cuenta b, boolean ascendente) {
        if (ascendente) {
            return a.getNumCuenta().compareTo(b.getNumCuenta()) > 0;
        } else {
            return a.getNumCuenta().compareTo(b.getNumCuenta()) < 0;
        }
    }

    /**
     * Filtrar cuentas por categoría
     */
    public static Cuenta[] filtrarPorCategoria(Cuenta[] cuentas, int total, String categoria) {
        int contador = 0;

        // Contar cuántas cuentas de esa categoría hay
        for (int i = 0; i < total; i++) {
            if (cuentas[i].getTipoCuenta().equalsIgnoreCase(categoria)) {
                contador++;
            }
        }

        // Crear arreglo filtrado
        Cuenta[] filtradas = new Cuenta[contador];
        int index = 0;
        for (int i = 0; i < total; i++) {
            if (cuentas[i].getTipoCuenta().equalsIgnoreCase(categoria)) {
                filtradas[index++] = cuentas[i];
            }
        }

        return filtradas;
    }

    // Método auxiliar para intercambiar elementos
    private static void intercambiar(Cuenta[] cuentas, int i, int j) {
        Cuenta temp = cuentas[i];
        cuentas[i] = cuentas[j];
        cuentas[j] = temp;
    }
}