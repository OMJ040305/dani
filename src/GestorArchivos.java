import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivos {
    private static final String ARCHIVO_CUENTAS = "datos/cuentas.dat";
    private static final String ARCHIVO_PRESTAMOS = "datos/prestamos.dat";

    /**
     * Guardar cuentas en archivo
     */
    public static boolean guardarCuentas(Cuenta[] cuentas, int total) {
        ObjectOutputStream oos = null;
        try {
            // Crear directorio si no existe
            File dir = new File("datos");
            if (!dir.exists()) {
                dir.mkdir();
            }

            oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_CUENTAS));
            oos.writeInt(total); // Guardar cantidad de cuentas

            for (int i = 0; i < total; i++) {
                oos.writeObject(cuentas[i]);
            }

            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar cuentas: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Cargar cuentas desde archivo
     */
    public static Object[] cargarCuentas() {
        ObjectInputStream ois = null;
        try {
            File archivo = new File(ARCHIVO_CUENTAS);
            if (!archivo.exists()) {
                return new Object[]{new Cuenta[1000], 0};
            }

            ois = new ObjectInputStream(new FileInputStream(archivo));
            int total = ois.readInt();
            Cuenta[] cuentas = new Cuenta[1000];

            for (int i = 0; i < total; i++) {
                cuentas[i] = (Cuenta) ois.readObject();
            }

            return new Object[]{cuentas, total};
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar cuentas: " + e.getMessage());
            e.printStackTrace();
            return new Object[]{new Cuenta[1000], 0};
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Guardar préstamos en archivo
     */
    public static boolean guardarPrestamos(List<Prestamo> prestamos) {
        ObjectOutputStream oos = null;
        try {
            File dir = new File("datos");
            if (!dir.exists()) {
                dir.mkdir();
            }

            oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_PRESTAMOS));
            oos.writeInt(prestamos.size());

            for (Prestamo p : prestamos) {
                oos.writeObject(p);
            }

            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar préstamos: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Cargar préstamos desde archivo
     */
    public static List<Prestamo> cargarPrestamos() {
        ObjectInputStream ois = null;
        try {
            File archivo = new File(ARCHIVO_PRESTAMOS);
            if (!archivo.exists()) {
                return new ArrayList<>();
            }

            ois = new ObjectInputStream(new FileInputStream(archivo));
            int total = ois.readInt();
            List<Prestamo> prestamos = new ArrayList<>();

            for (int i = 0; i < total; i++) {
                prestamos.add((Prestamo) ois.readObject());
            }

            return prestamos;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar préstamos: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Hacer backup de archivos
     */
    public static boolean hacerBackup() {
        try {
            File archivoC = new File(ARCHIVO_CUENTAS);
            File archivoP = new File(ARCHIVO_PRESTAMOS);

            if (archivoC.exists()) {
                copiarArchivo(archivoC, new File("datos/cuentas_backup.dat"));
            }

            if (archivoP.exists()) {
                copiarArchivo(archivoP, new File("datos/prestamos_backup.dat"));
            }

            return true;
        } catch (Exception e) {
            System.err.println("Error al hacer backup: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static void copiarArchivo(File origen, File destino) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(origen);
            fos = new FileOutputStream(destino);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        } finally {
            if (fis != null) fis.close();
            if (fos != null) fos.close();
        }
    }
}