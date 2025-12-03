
public class GestorCuentas {
    private static final int MAX_CUENTAS = 1000;
    private Cuenta[] cuentas;
    private int totalCuentas;

    public GestorCuentas() {
        cuentas = new Cuenta[MAX_CUENTAS];
        totalCuentas = 0;
    }

    public boolean altaCuenta(Cuenta cuenta) {
        try {
            if (totalCuentas >= MAX_CUENTAS) {
                return false;
            }

            // Verificar que no exista una cuenta con el mismo número
            if (buscarCuentaPorNumero(cuenta.getNumCuenta()) != null) {
                return false;
            }

            cuentas[totalCuentas] = cuenta;
            totalCuentas++;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Eliminar una cuenta por número
     */
    public boolean eliminarCuenta(String numCuenta) {
        try {
            for (int i = 0; i < totalCuentas; i++) {
                if (cuentas[i].getNumCuenta().equals(numCuenta)) {
                    // Desplazar elementos
                    for (int j = i; j < totalCuentas - 1; j++) {
                        cuentas[j] = cuentas[j + 1];
                    }
                    cuentas[totalCuentas - 1] = null;
                    totalCuentas--;
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Buscar cuenta por número
     */
    public Cuenta buscarCuentaPorNumero(String numCuenta) {
        return Busqueda.busquedaLinealPorNumCuenta(cuentas, totalCuentas, numCuenta);
    }

    /**
     * Buscar cuenta por nombre (búsqueda binaria)
     */
    public Cuenta buscarCuentaPorNombre(String nombre) {
        // Primero ordenar por nombre
        Cuenta[] copiaOrdenada = obtenerCuentasOrdenadas();
        Ordenamiento.quickSortPorNombre(copiaOrdenada, 0, totalCuentas - 1, true);
        return Busqueda.busquedaBinariaPorNombre(copiaOrdenada, totalCuentas, nombre);
    }

    /**
     * Modificar datos de una cuenta
     */
    public boolean modificarCuenta(String numCuenta, Cuenta nuevaCuenta) {
        try {
            Cuenta cuenta = buscarCuentaPorNumero(numCuenta);
            if (cuenta == null) {
                return false;
            }

            cuenta.setNombreCliente(nuevaCuenta.getNombreCliente());
            cuenta.setSaldo(nuevaCuenta.getSaldo());

            if (cuenta instanceof CuentaAhorro && nuevaCuenta instanceof CuentaAhorro) {
                ((CuentaAhorro) cuenta).setCuotaMantenimiento(
                        ((CuentaAhorro) nuevaCuenta).getCuotaMantenimiento());
            } else if (cuenta instanceof CuentaCorriente && nuevaCuenta instanceof CuentaCorriente) {
                ((CuentaCorriente) cuenta).setTransacciones(
                        ((CuentaCorriente) nuevaCuenta).getTransacciones());
                ((CuentaCorriente) cuenta).setImporteTransaccion(
                        ((CuentaCorriente) nuevaCuenta).getImporteTransaccion());
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Realizar un cargo a una cuenta
     */
    public boolean realizarCargo(String numCuenta, double monto) {
        try {
            Cuenta cuenta = buscarCuentaPorNumero(numCuenta);
            if (cuenta == null || monto <= 0 || cuenta.getSaldo() < monto) {
                return false;
            }

            cuenta.cargar(monto);

            // Incrementar transacciones si es cuenta corriente
            if (cuenta instanceof CuentaCorriente) {
                ((CuentaCorriente) cuenta).incrementarTransacciones();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Realizar un abono a una cuenta
     */
    public boolean realizarAbono(String numCuenta, double monto) {
        try {
            Cuenta cuenta = buscarCuentaPorNumero(numCuenta);
            if (cuenta == null || monto <= 0) {
                return false;
            }

            cuenta.abonar(monto);

            // Incrementar transacciones si es cuenta corriente
            if (cuenta instanceof CuentaCorriente) {
                ((CuentaCorriente) cuenta).incrementarTransacciones();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Aplicar comisiones a todas las cuentas
     */
    public void aplicarComisiones() {
        for (int i = 0; i < totalCuentas; i++) {
            cuentas[i].comisiones();
        }
    }

    /**
     * Aplicar intereses a todas las cuentas
     */
    public void aplicarIntereses() {
        for (int i = 0; i < totalCuentas; i++) {
            cuentas[i].intereses();
        }
    }

    /**
     * Obtener todas las cuentas
     */
    public Cuenta[] getCuentas() {
        Cuenta[] resultado = new Cuenta[totalCuentas];
        System.arraycopy(cuentas, 0, resultado, 0, totalCuentas);
        return resultado;
    }

    /**
     * Obtener copia ordenada de cuentas
     */
    private Cuenta[] obtenerCuentasOrdenadas() {
        Cuenta[] copia = new Cuenta[totalCuentas];
        System.arraycopy(cuentas, 0, copia, 0, totalCuentas);
        return copia;
    }

    /**
     * Ordenar cuentas por número (QuickSort)
     */
    public Cuenta[] obtenerCuentasOrdenadasPorNumero(boolean ascendente) {
        Cuenta[] ordenadas = obtenerCuentasOrdenadas();
        if (totalCuentas > 0) {
            Ordenamiento.quickSortPorNumCuenta(ordenadas, 0, totalCuentas - 1, ascendente);
        }
        return ordenadas;
    }

    /**
     * Ordenar cuentas por nombre (QuickSort)
     */
    public Cuenta[] obtenerCuentasOrdenadasPorNombre(boolean ascendente) {
        Cuenta[] ordenadas = obtenerCuentasOrdenadas();
        if (totalCuentas > 0) {
            Ordenamiento.quickSortPorNombre(ordenadas, 0, totalCuentas - 1, ascendente);
        }
        return ordenadas;
    }

    /**
     * Filtrar y ordenar por categoría (Inserción)
     */
    public Cuenta[] obtenerCuentasPorCategoria(String categoria, boolean ascendente) {
        Cuenta[] filtradas = Ordenamiento.filtrarPorCategoria(cuentas, totalCuentas, categoria);
        if (filtradas.length > 0) {
            Ordenamiento.insercionPorNumCuenta(filtradas, filtradas.length, ascendente);
        }
        return filtradas;
    }

    public int getTotalCuentas() {
        return totalCuentas;
    }

    public void setCuentas(Cuenta[] cuentas, int total) {
        this.cuentas = cuentas;
        this.totalCuentas = total;
    }
}