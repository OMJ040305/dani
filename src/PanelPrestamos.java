import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelPrestamos extends JPanel {
    private GestorPrestamos gestorPrestamos;
    private GestorCuentas gestorCuentas;
    private String operacion;

    public PanelPrestamos(GestorPrestamos gestorPrestamos, GestorCuentas gestorCuentas, String operacion) {
        this.gestorPrestamos = gestorPrestamos;
        this.gestorCuentas = gestorCuentas;
        this.operacion = operacion;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        switch (operacion) {
            case "ALTA": crearPanelAlta(); break;
            case "BAJA": crearPanelBaja(); break;
            case "BUSCAR": crearPanelBuscar(); break;
            case "ABONAR": crearPanelAbonar(); break;
            case "VER_TODOS": crearPanelVerTodos(); break;
        }
    }

    private void crearPanelAlta() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

        // Panel de tipo de préstamo
        JPanel panelTipo = new JPanel(new FlowLayout());
        panelTipo.add(new JLabel("Tipo de Préstamo:"));
        JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"Ordinario", "MiMoto", "Automotriz"});
        panelTipo.add(cmbTipo);
        panelPrincipal.add(panelTipo, BorderLayout.NORTH);

        // Panel de formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtNumPrestamo = new JTextField(15);
        JTextField txtCliente = new JTextField(15);
        JTextField txtMonto = new JTextField(15);
        JTextField txtPlazo = new JTextField(15);
        JTextField txtParam1 = new JTextField(15);
        JTextField txtParam2 = new JTextField(15);
        JLabel lblParam1 = new JLabel("ISR %:");
        JLabel lblParam2 = new JLabel();

        cmbTipo.addActionListener(e -> {
            String tipo = (String) cmbTipo.getSelectedItem();
            switch (tipo) {
                case "Ordinario":
                    lblParam1.setText("ISR %:");
                    lblParam2.setText("");
                    txtParam2.setEnabled(false);
                    break;
                case "MiMoto":
                    lblParam1.setText("Enganche %:");
                    lblParam2.setText("");
                    txtParam2.setEnabled(false);
                    break;
                case "Automotriz":
                    lblParam1.setText("Comisión %:");
                    lblParam2.setText("IVA %:");
                    txtParam2.setEnabled(true);
                    break;
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Núm. Préstamo:"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtNumPrestamo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtCliente, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Monto:"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtMonto, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(new JLabel("Plazo (meses):"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtPlazo, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panelForm.add(lblParam1, gbc);
        gbc.gridx = 1;
        panelForm.add(txtParam1, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panelForm.add(lblParam2, gbc);
        gbc.gridx = 1;
        txtParam2.setEnabled(false);
        panelForm.add(txtParam2, gbc);

        panelPrincipal.add(panelForm, BorderLayout.CENTER);

        // Botón
        JPanel panelBoton = new JPanel();
        JButton btnAlta = new JButton("Registrar Préstamo");
        btnAlta.setBackground(new Color(0, 102, 204));
        btnAlta.setForeground(Color.WHITE);
        btnAlta.setFont(new Font("Arial", Font.BOLD, 12));
        btnAlta.addActionListener(e -> {
            try {
                String tipo = (String) cmbTipo.getSelectedItem();
                String numPrestamo = txtNumPrestamo.getText().trim();
                String cliente = txtCliente.getText().trim();
                double monto = Double.parseDouble(txtMonto.getText().trim());
                int plazo = Integer.parseInt(txtPlazo.getText().trim());
                double param1 = Double.parseDouble(txtParam1.getText().trim());

                Prestamo prestamo;
                switch (tipo) {
                    case "Ordinario":
                        prestamo = new Ordinario(numPrestamo, cliente, monto, plazo, param1);
                        break;
                    case "MiMoto":
                        prestamo = new MiMoto(numPrestamo, cliente, monto, plazo, param1);
                        break;
                    case "Automotriz":
                        double param2 = Double.parseDouble(txtParam2.getText().trim());
                        prestamo = new Automotriz(numPrestamo, cliente, monto, plazo, param1, param2);
                        break;
                    default:
                        return;
                }

                if (gestorPrestamos.altaPrestamo(prestamo)) {
                    JOptionPane.showMessageDialog(this, "Préstamo registrado exitosamente",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    txtNumPrestamo.setText("");
                    txtCliente.setText("");
                    txtMonto.setText("");
                    txtPlazo.setText("");
                    txtParam1.setText("");
                    txtParam2.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Ya existe un préstamo con ese número",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panelBoton.add(btnAlta);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void crearPanelBaja() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Núm. Préstamo:"), gbc);
        gbc.gridx = 1;
        JTextField txtNum = new JTextField(20);
        panel.add(txtNum, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        JButton btnBaja = new JButton("Eliminar Préstamo");
        btnBaja.setBackground(new Color(204, 0, 0));
        btnBaja.setForeground(Color.WHITE);
        btnBaja.addActionListener(e -> {
            if (gestorPrestamos.bajaPrestamo(txtNum.getText().trim())) {
                JOptionPane.showMessageDialog(this, "Préstamo eliminado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                txtNum.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Préstamo no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(btnBaja, gbc);

        add(panel);
    }

    private void crearPanelBuscar() {
        JPanel panelBusq = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        panelBusq.add(new JLabel("Núm. Préstamo:"), gbc);
        gbc.gridx = 1;
        JTextField txtNum = new JTextField(20);
        panelBusq.add(txtNum, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(0, 102, 204));
        btnBuscar.setForeground(Color.WHITE);
        panelBusq.add(btnBuscar, gbc);

        add(panelBusq, BorderLayout.NORTH);

        JTextArea txtInfo = new JTextArea(15, 40);
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(txtInfo), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> {
            Prestamo p = gestorPrestamos.buscarPrestamoPorNumero(txtNum.getText().trim());
            if (p != null) {
                txtInfo.setText("PRÉSTAMO ENCONTRADO\n===================\n\n" + p.toString());
            } else {
                txtInfo.setText("Préstamo no encontrado");
            }
        });
    }

    private void crearPanelAbonar() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtNum = new JTextField(15);
        JTextField txtMonto = new JTextField(15);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Núm. Préstamo:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNum, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Monto a Abonar:"), gbc);
        gbc.gridx = 1;
        panel.add(txtMonto, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton btnAbonar = new JButton("Realizar Abono");
        btnAbonar.setBackground(new Color(0, 153, 0));
        btnAbonar.setForeground(Color.WHITE);
        btnAbonar.addActionListener(e -> {
            try {
                double monto = Double.parseDouble(txtMonto.getText().trim());
                if (gestorPrestamos.realizarAbonoPrestamo(txtNum.getText().trim(), monto)) {
                    JOptionPane.showMessageDialog(this, "Abono realizado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    txtNum.setText("");
                    txtMonto.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al abonar", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(btnAbonar, gbc);

        add(panel);
    }

    private void crearPanelVerTodos() {
        String[] columnas = {"Núm. Préstamo", "Cliente", "Tipo", "Saldo", "Plazo", "Tasa"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);

        List<Prestamo> prestamos = gestorPrestamos.getPrestamos();
        for (Prestamo p : prestamos) {
            modelo.addRow(new Object[]{
                    p.getNumPrestamo(), p.getCliente(), p.getTipoPrestamo(),
                    String.format("$%.2f", p.getSaldoPrestamo()),
                    p.getPlazoMeses(), p.getTasaInteres() + "%"
            });
        }

        add(new JScrollPane(tabla));
    }
}