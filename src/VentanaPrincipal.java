import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame {
    private GestorCuentas gestorCuentas;
    private GestorPrestamos gestorPrestamos;
    private JDesktopPane escritorio;
    private JMenuBar menuBar;

    public VentanaPrincipal() {
        gestorCuentas = new GestorCuentas();
        gestorPrestamos = new GestorPrestamos();

        cargarDatos();
        initComponents();
        configurarVentana();
    }

    private void initComponents() {
        // Configurar escritorio
        escritorio = new JDesktopPane();
        escritorio.setBackground(new Color(240, 240, 240));
        add(escritorio, BorderLayout.CENTER);

        // Crear menú
        crearMenu();

        // Panel inferior con información
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(new Color(0, 0, 0));
        panelInferior.setPreferredSize(new Dimension(0, 30));
        JLabel lblInfo = new JLabel("Sistema Banco Azteca - Gestión de Cuentas y Préstamos");
        lblInfo.setForeground(Color.WHITE);
        lblInfo.setFont(new Font("Arial", Font.BOLD, 12));
        panelInferior.add(lblInfo);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void crearMenu() {
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(0, 0, 0));

        // Menú Gestión de Cuentas
        JMenu menuCuentas = crearMenu("Gestión de Cuentas");
        menuCuentas.add(crearMenuItem("Alta de Cuenta", e -> abrirAltaCuenta()));
        menuCuentas.add(crearMenuItem("Eliminar Cuenta", e -> abrirEliminarCuenta()));
        menuCuentas.add(crearMenuItem("Modificar Cuenta", e -> abrirModificarCuenta()));
        menuCuentas.add(crearMenuItem("Buscar Cuenta", e -> abrirBuscarCuenta()));
        menuCuentas.addSeparator();
        menuCuentas.add(crearMenuItem("Ver Todas las Cuentas", e -> abrirVerCuentas()));
        menuBar.add(menuCuentas);

        // Menú Movimientos
        JMenu menuMovimientos = crearMenu("Movimientos");
        menuMovimientos.add(crearMenuItem("Realizar Cargo", e -> abrirRealizarCargo()));
        menuMovimientos.add(crearMenuItem("Realizar Abono", e -> abrirRealizarAbono()));
        menuMovimientos.add(crearMenuItem("Consultar Saldo", e -> abrirConsultarSaldo()));
        menuBar.add(menuMovimientos);

        // Menú Préstamos
        JMenu menuPrestamos = crearMenu("Préstamos");
        menuPrestamos.add(crearMenuItem("Alta de Préstamo", e -> abrirAltaPrestamo()));
        menuPrestamos.add(crearMenuItem("Baja de Préstamo", e -> abrirBajaPrestamo()));
        menuPrestamos.add(crearMenuItem("Buscar Préstamo", e -> abrirBuscarPrestamo()));
        menuPrestamos.add(crearMenuItem("Abonar a Préstamo", e -> abrirAbonarPrestamo()));
        menuPrestamos.addSeparator();
        menuPrestamos.add(crearMenuItem("Ver Todos los Préstamos", e -> abrirVerPrestamos()));
        menuBar.add(menuPrestamos);

        // Menú Reportes
        JMenu menuReportes = crearMenu("Reportes");
        menuReportes.add(crearMenuItem("Reporte General de Cuentas", e -> abrirReporteGeneral()));
        menuReportes.add(crearMenuItem("Reporte por Categoría", e -> abrirReporteCategoria()));
        menuReportes.add(crearMenuItem("Préstamos por Cliente", e -> abrirPrestamosPorCliente()));
        menuReportes.add(crearMenuItem("Reporte General de Préstamos", e -> abrirReportePrestamos()));
        menuBar.add(menuReportes);

        // Menú Sistema
        JMenu menuSistema = crearMenu("Sistema");
        menuSistema.add(crearMenuItem("Guardar Datos", e -> guardarDatos()));
        menuSistema.add(crearMenuItem("Hacer Backup", e -> hacerBackup()));
        menuSistema.addSeparator();
        menuSistema.add(crearMenuItem("Salir", e -> salir()));
        menuBar.add(menuSistema);

        setJMenuBar(menuBar);
    }

    private JMenu crearMenu(String texto) {
        JMenu menu = new JMenu(texto);
        menu.setForeground(Color.BLACK);
        menu.setFont(new Font("Arial", Font.BOLD, 12));
        return menu;
    }

    private JMenuItem crearMenuItem(String texto, ActionListener listener) {
        JMenuItem item = new JMenuItem(texto);
        item.setFont(new Font("Arial", Font.PLAIN, 11));
        item.addActionListener(listener);
        return item;
    }

    private void configurarVentana() {
        setTitle("Banco Azteca - Sistema de Gestión Bancaria");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salir();
            }
        });

        try {
            setIconImage(Toolkit.getDefaultToolkit().createImage(""));
        } catch (Exception e) {
        }
    }

    // Métodos para abrir ventanas internas
    private void abrirAltaCuenta() {
        PanelGestionCuentas panel = new PanelGestionCuentas(gestorCuentas, "ALTA");
        agregarVentanaInterna(panel, "Alta de Cuenta");
    }

    private void abrirEliminarCuenta() {
        PanelGestionCuentas panel = new PanelGestionCuentas(gestorCuentas, "ELIMINAR");
        agregarVentanaInterna(panel, "Eliminar Cuenta");
    }

    private void abrirModificarCuenta() {
        PanelGestionCuentas panel = new PanelGestionCuentas(gestorCuentas, "MODIFICAR");
        agregarVentanaInterna(panel, "Modificar Cuenta");
    }

    private void abrirBuscarCuenta() {
        PanelGestionCuentas panel = new PanelGestionCuentas(gestorCuentas, "BUSCAR");
        agregarVentanaInterna(panel, "Buscar Cuenta");
    }

    private void abrirVerCuentas() {
        PanelGestionCuentas panel = new PanelGestionCuentas(gestorCuentas, "VER_TODAS");
        agregarVentanaInterna(panel, "Todas las Cuentas");
    }

    private void abrirRealizarCargo() {
        PanelMovimientos panel = new PanelMovimientos(gestorCuentas, "CARGO");
        agregarVentanaInterna(panel, "Realizar Cargo");
    }

    private void abrirRealizarAbono() {
        PanelMovimientos panel = new PanelMovimientos(gestorCuentas, "ABONO");
        agregarVentanaInterna(panel, "Realizar Abono");
    }

    private void abrirConsultarSaldo() {
        PanelMovimientos panel = new PanelMovimientos(gestorCuentas, "SALDO");
        agregarVentanaInterna(panel, "Consultar Saldo");
    }

    private void abrirAltaPrestamo() {
        PanelPrestamos panel = new PanelPrestamos(gestorPrestamos, gestorCuentas, "ALTA");
        agregarVentanaInterna(panel, "Alta de Préstamo");
    }

    private void abrirBajaPrestamo() {
        PanelPrestamos panel = new PanelPrestamos(gestorPrestamos, gestorCuentas, "BAJA");
        agregarVentanaInterna(panel, "Baja de Préstamo");
    }

    private void abrirBuscarPrestamo() {
        PanelPrestamos panel = new PanelPrestamos(gestorPrestamos, gestorCuentas, "BUSCAR");
        agregarVentanaInterna(panel, "Buscar Préstamo");
    }

    private void abrirAbonarPrestamo() {
        PanelPrestamos panel = new PanelPrestamos(gestorPrestamos, gestorCuentas, "ABONAR");
        agregarVentanaInterna(panel, "Abonar a Préstamo");
    }

    private void abrirVerPrestamos() {
        PanelPrestamos panel = new PanelPrestamos(gestorPrestamos, gestorCuentas, "VER_TODOS");
        agregarVentanaInterna(panel, "Todos los Préstamos");
    }

    private void abrirReporteGeneral() {
        PanelReportes panel = new PanelReportes(gestorCuentas, gestorPrestamos, "GENERAL");
        agregarVentanaInterna(panel, "Reporte General de Cuentas");
    }

    private void abrirReporteCategoria() {
        PanelReportes panel = new PanelReportes(gestorCuentas, gestorPrestamos, "CATEGORIA");
        agregarVentanaInterna(panel, "Reporte por Categoría");
    }

    private void abrirPrestamosPorCliente() {
        PanelReportes panel = new PanelReportes(gestorCuentas, gestorPrestamos, "PRESTAMOS_CLIENTE");
        agregarVentanaInterna(panel, "Préstamos por Cliente");
    }

    private void abrirReportePrestamos() {
        PanelReportes panel = new PanelReportes(gestorCuentas, gestorPrestamos, "GENERAL_PRESTAMOS");
        agregarVentanaInterna(panel, "Reporte General de Préstamos");
    }

    private void agregarVentanaInterna(JPanel panel, String titulo) {
        JInternalFrame frame = new JInternalFrame(titulo, true, true, true, true);
        frame.setContentPane(panel);
        frame.setSize(800, 500);
        frame.setVisible(true);
        escritorio.add(frame);

        try {
            frame.setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarDatos() {
        try {
            Object[] datosCuentas = GestorArchivos.cargarCuentas();
            gestorCuentas.setCuentas((Cuenta[]) datosCuentas[0],
                    (int) datosCuentas[1]);

            gestorPrestamos.setPrestamos(GestorArchivos.cargarPrestamos());

            System.out.println("Datos cargados correctamente");
        } catch (Exception e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void guardarDatos() {
        try {
            boolean exitoCuentas = GestorArchivos.guardarCuentas(
                    gestorCuentas.getCuentas(), gestorCuentas.getTotalCuentas());
            boolean exitoPrestamos = GestorArchivos.guardarPrestamos(
                    gestorPrestamos.getPrestamos());

            if (exitoCuentas && exitoPrestamos) {
                JOptionPane.showMessageDialog(this,
                        "Datos guardados correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al guardar algunos datos",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hacerBackup() {
        if (GestorArchivos.hacerBackup()) {
            JOptionPane.showMessageDialog(this,
                    "Backup realizado correctamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al hacer backup",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salir() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Desea guardar los cambios antes de salir?",
                "Confirmar salida",
                JOptionPane.YES_NO_CANCEL_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            guardarDatos();
            System.exit(0);
        } else if (opcion == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }
}