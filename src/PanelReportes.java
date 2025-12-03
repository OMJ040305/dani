import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelReportes extends JPanel {
    private GestorCuentas gestorCuentas;
    private GestorPrestamos gestorPrestamos;
    private String tipoReporte;

    public PanelReportes(GestorCuentas gestorCuentas, GestorPrestamos gestorPrestamos, String tipoReporte) {
        this.gestorCuentas = gestorCuentas;
        this.gestorPrestamos = gestorPrestamos;
        this.tipoReporte = tipoReporte;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        switch (tipoReporte) {
            case "GENERAL": crearReporteGeneral(); break;
            case "CATEGORIA": crearReporteCategoria(); break;
            case "PRESTAMOS_CLIENTE": crearPrestamosPorCliente(); break;
            case "GENERAL_PRESTAMOS": crearReporteGeneralPrestamos(); break;
        }
    }

    private void crearReporteGeneral() {
        // Panel de controles
        JPanel panelControl = new JPanel(new FlowLayout());
        panelControl.add(new JLabel("Ordenar por:"));

        JComboBox<String> cmbCriterio = new JComboBox<>(new String[]{"Número de Cuenta", "Nombre de Cliente"});
        panelControl.add(cmbCriterio);

        JComboBox<String> cmbOrden = new JComboBox<>(new String[]{"Ascendente", "Descendente"});
        panelControl.add(cmbOrden);

        JButton btnGenerar = new JButton("Generar Reporte");
        btnGenerar.setBackground(new Color(0, 102, 204));
        btnGenerar.setForeground(Color.WHITE);
        panelControl.add(btnGenerar);

        add(panelControl, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"Núm. Cuenta", "Cliente", "Tipo", "Saldo", "Info Adicional"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabla = new JTable(modelo);
        tabla.getTableHeader().setBackground(new Color(0, 51, 102));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // Acción del botón
        btnGenerar.addActionListener(e -> {
            modelo.setRowCount(0);
            String criterio = (String) cmbCriterio.getSelectedItem();
            boolean ascendente = cmbOrden.getSelectedIndex() == 0;

            Cuenta[] cuentas;
            if ("Número de Cuenta".equals(criterio)) {
                cuentas = gestorCuentas.obtenerCuentasOrdenadasPorNumero(ascendente);
            } else {
                cuentas = gestorCuentas.obtenerCuentasOrdenadasPorNombre(ascendente);
            }

            for (Cuenta c : cuentas) {
                String info = c.toString();
                modelo.addRow(new Object[]{
                        c.getNumCuenta(),
                        c.getNombreCliente(),
                        c.getTipoCuenta(),
                        String.format("$%.2f", c.getSaldo()),
                        c.getTipoCuenta()
                });
            }
        });

        // Generar reporte inicial
        btnGenerar.doClick();
    }

    private void crearReporteCategoria() {
        // Panel de controles
        JPanel panelControl = new JPanel(new FlowLayout());
        panelControl.add(new JLabel("Categoría:"));

        JComboBox<String> cmbCategoria = new JComboBox<>(new String[]{"Ahorro", "Corriente"});
        panelControl.add(cmbCategoria);

        JComboBox<String> cmbOrden = new JComboBox<>(new String[]{"Ascendente", "Descendente"});
        panelControl.add(cmbOrden);

        JButton btnGenerar = new JButton("Generar Reporte");
        btnGenerar.setBackground(new Color(0, 102, 204));
        btnGenerar.setForeground(Color.WHITE);
        panelControl.add(btnGenerar);

        add(panelControl, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"Núm. Cuenta", "Cliente", "Saldo", "Detalles"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabla = new JTable(modelo);
        tabla.getTableHeader().setBackground(new Color(0, 51, 102));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setRowHeight(25);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Acción del botón
        btnGenerar.addActionListener(e -> {
            modelo.setRowCount(0);
            String categoria = (String) cmbCategoria.getSelectedItem();
            boolean ascendente = cmbOrden.getSelectedIndex() == 0;

            Cuenta[] cuentas = gestorCuentas.obtenerCuentasPorCategoria(categoria, ascendente);

            for (Cuenta c : cuentas) {
                modelo.addRow(new Object[]{
                        c.getNumCuenta(),
                        c.getNombreCliente(),
                        String.format("$%.2f", c.getSaldo()),
                        c.toString()
                });
            }

            JOptionPane.showMessageDialog(this,
                    String.format("Total de cuentas %s: %d", categoria, cuentas.length),
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void crearPrestamosPorCliente() {
        JPanel panelBusqueda = new JPanel(new FlowLayout());
        panelBusqueda.add(new JLabel("Nombre del Cliente:"));

        JTextField txtCliente = new JTextField(25);
        panelBusqueda.add(txtCliente);

        JButton btnBuscar = new JButton("Buscar Préstamos");
        btnBuscar.setBackground(new Color(0, 102, 204));
        btnBuscar.setForeground(Color.WHITE);
        panelBusqueda.add(btnBuscar);

        add(panelBusqueda, BorderLayout.NORTH);

        // Panel de resultados
        JPanel panelResultados = new JPanel(new BorderLayout());

        JTextArea txtInfo = new JTextArea(10, 50);
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panelResultados.add(new JScrollPane(txtInfo), BorderLayout.CENTER);

        add(panelResultados, BorderLayout.CENTER);

        // Acción del botón
        btnBuscar.addActionListener(e -> {
            String cliente = txtCliente.getText().trim();
            if (cliente.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Ingrese el nombre del cliente",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Prestamo> prestamos = gestorPrestamos.buscarPrestamosPorCliente(cliente);

            if (prestamos.isEmpty()) {
                txtInfo.setText("No se encontraron préstamos para el cliente: " + cliente);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("PRÉSTAMOS DEL CLIENTE: ").append(cliente).append("\n");
                sb.append("=".repeat(60)).append("\n\n");

                int ordinarios = 0, mimotos = 0, automotrices = 0;

                for (Prestamo p : prestamos) {
                    sb.append(p.toString()).append("\n\n");

                    String tipo = p.getTipoPrestamo();
                    if ("Ordinario".equals(tipo)) ordinarios++;
                    else if ("MiMoto".equals(tipo)) mimotos++;
                    else if ("Automotriz".equals(tipo)) automotrices++;
                }

                sb.append("\nRESUMEN:\n");
                sb.append(String.format("Préstamos Ordinarios: %d\n", ordinarios));
                sb.append(String.format("Préstamos MiMoto: %d\n", mimotos));
                sb.append(String.format("Préstamos Automotriz: %d\n", automotrices));
                sb.append(String.format("Total de préstamos: %d\n", prestamos.size()));

                txtInfo.setText(sb.toString());
            }
        });
    }

    private void crearReporteGeneralPrestamos() {
        // Panel con tabla de todas las cuentas y sus préstamos
        String[] columnas = {"Cliente", "Núm. Cuenta", "Total Préstamos", "Monto Total"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabla = new JTable(modelo);
        tabla.getTableHeader().setBackground(new Color(0, 51, 102));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setRowHeight(25);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Botón para generar
        JPanel panelBoton = new JPanel();
        JButton btnGenerar = new JButton("Generar Reporte General");
        btnGenerar.setBackground(new Color(0, 102, 204));
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setFont(new Font("Arial", Font.BOLD, 12));
        panelBoton.add(btnGenerar);

        add(panelBoton, BorderLayout.NORTH);

        // Panel de estadísticas
        JTextArea txtEstadisticas = new JTextArea(8, 50);
        txtEstadisticas.setEditable(false);
        txtEstadisticas.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtEstadisticas.setBackground(new Color(240, 240, 240));
        add(new JScrollPane(txtEstadisticas), BorderLayout.SOUTH);

        btnGenerar.addActionListener(e -> {
            modelo.setRowCount(0);

            Cuenta[] cuentas = gestorCuentas.getCuentas();
            List<Prestamo> prestamos = gestorPrestamos.getPrestamos();

            for (Cuenta c : cuentas) {
                List<Prestamo> prestamosCliente = gestorPrestamos.buscarPrestamosPorCliente(c.getNombreCliente());
                double montoTotal = 0;

                for (Prestamo p : prestamosCliente) {
                    montoTotal += p.getSaldoPrestamo();
                }

                modelo.addRow(new Object[]{
                        c.getNombreCliente(),
                        c.getNumCuenta(),
                        prestamosCliente.size(),
                        String.format("$%.2f", montoTotal)
                });
            }

            // Mostrar estadísticas
            txtEstadisticas.setText(gestorPrestamos.obtenerEstadisticas());
        });

        // Generar automáticamente
        btnGenerar.doClick();
    }
}