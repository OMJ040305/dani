import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelGestionCuentas extends JPanel {
    private GestorCuentas gestor;
    private String operacion;
    private JTextField txtNumCuenta, txtNombre, txtSaldo, txtCuota, txtTransacciones, txtImporte;
    private JComboBox<String> cmbTipoCuenta;
    private JButton btnEjecutar, btnLimpiar;
    private JTable tablaCuentas;
    private DefaultTableModel modeloTabla;
    private JPanel panelCampos;

    public PanelGestionCuentas(GestorCuentas gestor, String operacion) {
        this.gestor = gestor;
        this.operacion = operacion;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        switch (operacion) {
            case "ALTA":
                crearPanelAlta();
                break;
            case "ELIMINAR":
                crearPanelEliminar();
                break;
            case "MODIFICAR":
                crearPanelModificar();
                break;
            case "BUSCAR":
                crearPanelBuscar();
                break;
            case "VER_TODAS":
                crearPanelVerTodas();
                break;
        }
    }

    private void crearPanelAlta() {
        // Panel superior con formulario
        panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Número de cuenta
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCampos.add(new JLabel("Número de Cuenta:"), gbc);
        gbc.gridx = 1;
        txtNumCuenta = new JTextField(15);
        panelCampos.add(txtNumCuenta, gbc);

        // Nombre del cliente
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCampos.add(new JLabel("Nombre del Cliente:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(15);
        panelCampos.add(txtNombre, gbc);

        // Saldo inicial
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelCampos.add(new JLabel("Saldo Inicial:"), gbc);
        gbc.gridx = 1;
        txtSaldo = new JTextField(15);
        panelCampos.add(txtSaldo, gbc);

        // Tipo de cuenta
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelCampos.add(new JLabel("Tipo de Cuenta:"), gbc);
        gbc.gridx = 1;
        cmbTipoCuenta = new JComboBox<>(new String[]{"Ahorro", "Corriente"});
        cmbTipoCuenta.addActionListener(e -> actualizarCamposSegunTipo());
        panelCampos.add(cmbTipoCuenta, gbc);

        // Cuota de mantenimiento (Ahorro)
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelCampos.add(new JLabel("Cuota de Mantenimiento:"), gbc);
        gbc.gridx = 1;
        txtCuota = new JTextField(15);
        panelCampos.add(txtCuota, gbc);

        // Transacciones (Corriente)
        gbc.gridx = 0;
        gbc.gridy = 5;
        panelCampos.add(new JLabel("Transacciones:"), gbc);
        gbc.gridx = 1;
        txtTransacciones = new JTextField(15);
        txtTransacciones.setEnabled(false);
        panelCampos.add(txtTransacciones, gbc);

        // Importe por transacción (Corriente)
        gbc.gridx = 0;
        gbc.gridy = 6;
        panelCampos.add(new JLabel("Importe por Transacción:"), gbc);
        gbc.gridx = 1;
        txtImporte = new JTextField(15);
        txtImporte.setEnabled(false);
        panelCampos.add(txtImporte, gbc);

        add(panelCampos, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnEjecutar = new JButton("Dar de Alta");
        btnEjecutar.setBackground(new Color(0, 102, 204));
        btnEjecutar.setForeground(Color.WHITE);
        btnEjecutar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEjecutar.addActionListener(e -> ejecutarAlta());

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarCampos());

        panelBotones.add(btnEjecutar);
        panelBotones.add(btnLimpiar);
        add(panelBotones, BorderLayout.CENTER);
    }

    private void crearPanelEliminar() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Número de Cuenta a Eliminar:"), gbc);

        gbc.gridx = 1;
        txtNumCuenta = new JTextField(20);
        panel.add(txtNumCuenta, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        btnEjecutar = new JButton("Eliminar Cuenta");
        btnEjecutar.setBackground(new Color(204, 0, 0));
        btnEjecutar.setForeground(Color.WHITE);
        btnEjecutar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEjecutar.addActionListener(e -> ejecutarEliminar());
        panel.add(btnEjecutar, gbc);

        add(panel, BorderLayout.CENTER);
    }

    private void crearPanelModificar() {
        // Similar al panel de alta pero con botón de buscar primero
        panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Búsqueda
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCampos.add(new JLabel("Número de Cuenta:"), gbc);
        gbc.gridx = 1;
        txtNumCuenta = new JTextField(15);
        panelCampos.add(txtNumCuenta, gbc);
        gbc.gridx = 2;
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarParaModificar());
        panelCampos.add(btnBuscar, gbc);

        // Resto de campos (deshabilitados inicialmente)
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCampos.add(new JLabel("Nombre del Cliente:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        txtNombre = new JTextField(15);
        txtNombre.setEnabled(false);
        panelCampos.add(txtNombre, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelCampos.add(new JLabel("Saldo:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        txtSaldo = new JTextField(15);
        txtSaldo.setEnabled(false);
        panelCampos.add(txtSaldo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelCampos.add(new JLabel("Cuota/Transacciones:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        txtCuota = new JTextField(15);
        txtCuota.setEnabled(false);
        panelCampos.add(txtCuota, gbc);

        add(panelCampos, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout());
        btnEjecutar = new JButton("Modificar");
        btnEjecutar.setEnabled(false);
        btnEjecutar.setBackground(new Color(0, 153, 0));
        btnEjecutar.setForeground(Color.WHITE);
        btnEjecutar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEjecutar.addActionListener(e -> ejecutarModificar());
        panelBotones.add(btnEjecutar);

        add(panelBotones, BorderLayout.CENTER);
    }

    private void crearPanelBuscar() {
        JPanel panelBusqueda = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelBusqueda.add(new JLabel("Buscar por:"), gbc);

        gbc.gridx = 1;
        JComboBox<String> cmbTipoBusqueda = new JComboBox<>(new String[]{"Número de Cuenta", "Nombre"});
        panelBusqueda.add(cmbTipoBusqueda, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelBusqueda.add(new JLabel("Valor:"), gbc);

        gbc.gridx = 1;
        JTextField txtBusqueda = new JTextField(20);
        panelBusqueda.add(txtBusqueda, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(0, 102, 204));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.addActionListener(e -> {
            String tipo = (String) cmbTipoBusqueda.getSelectedItem();
            buscarCuenta(tipo, txtBusqueda.getText());
        });
        panelBusqueda.add(btnBuscar, gbc);

        add(panelBusqueda, BorderLayout.NORTH);

        // Área de resultados
        JTextArea txtResultado = new JTextArea(15, 50);
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(txtResultado);
        add(scroll, BorderLayout.CENTER);

        // Guardar referencia para usar en la búsqueda
        txtResultado.setName("txtResultado");
    }

    private void crearPanelVerTodas() {
        // Crear tabla
        String[] columnas = {"Num. Cuenta", "Cliente", "Tipo", "Saldo", "Detalles"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCuentas = new JTable(modeloTabla);
        tablaCuentas.getTableHeader().setBackground(new Color(0, 51, 102));
        tablaCuentas.getTableHeader().setForeground(Color.WHITE);
        tablaCuentas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaCuentas.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tablaCuentas);
        add(scroll, BorderLayout.CENTER);

        // Panel de botones para refrescar
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> cargarTablaCuentas());
        panelBotones.add(btnRefrescar);

        add(panelBotones, BorderLayout.SOUTH);

        // Cargar datos iniciales
        cargarTablaCuentas();
    }

// Continuación de PanelGestionCuentas.java
// Agregar estos métodos a la clase

    private void actualizarCamposSegunTipo() {
        String tipo = (String) cmbTipoCuenta.getSelectedItem();
        if ("Ahorro".equals(tipo)) {
            txtCuota.setEnabled(true);
            txtTransacciones.setEnabled(false);
            txtImporte.setEnabled(false);
            txtTransacciones.setText("");
            txtImporte.setText("");
        } else {
            txtCuota.setEnabled(false);
            txtTransacciones.setEnabled(true);
            txtImporte.setEnabled(true);
            txtCuota.setText("");
        }
    }

    private void ejecutarAlta() {
        try {
            // Validar campos
            if (txtNumCuenta.getText().trim().isEmpty() ||
                    txtNombre.getText().trim().isEmpty() ||
                    txtSaldo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos obligatorios deben estar llenos",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String numCuenta = txtNumCuenta.getText().trim();
            String nombre = txtNombre.getText().trim();
            double saldo = Double.parseDouble(txtSaldo.getText().trim());
            String tipo = (String) cmbTipoCuenta.getSelectedItem();

            Cuenta cuenta;
            if ("Ahorro".equals(tipo)) {
                double cuota = Double.parseDouble(txtCuota.getText().trim());
                cuenta = new CuentaAhorro(numCuenta, nombre, saldo, cuota);
            } else {
                int trans = Integer.parseInt(txtTransacciones.getText().trim());
                double importe = Double.parseDouble(txtImporte.getText().trim());
                cuenta = new CuentaCorriente(numCuenta, nombre, saldo, trans, importe);
            }

            if (gestor.altaCuenta(cuenta)) {
                JOptionPane.showMessageDialog(this,
                        "Cuenta registrada exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error: Ya existe una cuenta con ese número",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Error en formato de números: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void ejecutarEliminar() {
        try {
            String numCuenta = txtNumCuenta.getText().trim();

            if (numCuenta.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debe ingresar un número de cuenta",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar la cuenta " + numCuenta + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (gestor.eliminarCuenta(numCuenta)) {
                    JOptionPane.showMessageDialog(this,
                            "Cuenta eliminada exitosamente",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    txtNumCuenta.setText("");
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No se encontró la cuenta",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarParaModificar() {
        try {
            String numCuenta = txtNumCuenta.getText().trim();
            Cuenta cuenta = gestor.buscarCuentaPorNumero(numCuenta);

            if (cuenta != null) {
                txtNombre.setEnabled(true);
                txtSaldo.setEnabled(true);
                txtCuota.setEnabled(true);
                btnEjecutar.setEnabled(true);

                txtNombre.setText(cuenta.getNombreCliente());
                txtSaldo.setText(String.valueOf(cuenta.getSaldo()));

                if (cuenta instanceof CuentaAhorro) {
                    txtCuota.setText(String.valueOf(((CuentaAhorro) cuenta).getCuotaMantenimiento()));
                } else if (cuenta instanceof CuentaCorriente) {
                    txtCuota.setText(String.valueOf(((CuentaCorriente) cuenta).getImporteTransaccion()));
                }

                JOptionPane.showMessageDialog(this,
                        "Cuenta encontrada. Puede modificar los datos.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se encontró la cuenta",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ejecutarModificar() {
        try {
            String numCuenta = txtNumCuenta.getText().trim();
            Cuenta cuentaOriginal = gestor.buscarCuentaPorNumero(numCuenta);

            if (cuentaOriginal != null) {
                cuentaOriginal.setNombreCliente(txtNombre.getText().trim());
                cuentaOriginal.setSaldo(Double.parseDouble(txtSaldo.getText().trim()));

                if (cuentaOriginal instanceof CuentaAhorro) {
                    ((CuentaAhorro) cuentaOriginal).setCuotaMantenimiento(
                            Double.parseDouble(txtCuota.getText().trim()));
                } else if (cuentaOriginal instanceof CuentaCorriente) {
                    ((CuentaCorriente) cuentaOriginal).setImporteTransaccion(
                            Double.parseDouble(txtCuota.getText().trim()));
                }

                JOptionPane.showMessageDialog(this,
                        "Cuenta modificada exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarCuenta(String tipoBusqueda, String valor) {
        try {
            if (valor.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debe ingresar un valor de búsqueda",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cuenta cuenta = null;
            if ("Número de Cuenta".equals(tipoBusqueda)) {
                cuenta = gestor.buscarCuentaPorNumero(valor);
            } else {
                cuenta = gestor.buscarCuentaPorNombre(valor);
            }

            // Buscar el JTextArea
            Component[] components = getComponents();
            JTextArea txtResultado = null;
            for (Component c : components) {
                if (c instanceof JScrollPane) {
                    JViewport viewport = ((JScrollPane) c).getViewport();
                    Component view = viewport.getView();
                    if (view instanceof JTextArea) {
                        txtResultado = (JTextArea) view;
                        break;
                    }
                }
            }

            if (txtResultado != null) {
                if (cuenta != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("CUENTA ENCONTRADA\n");
                    sb.append("=================\n\n");
                    sb.append("Número de Cuenta: ").append(cuenta.getNumCuenta()).append("\n");
                    sb.append("Cliente: ").append(cuenta.getNombreCliente()).append("\n");
                    sb.append("Tipo: ").append(cuenta.getTipoCuenta()).append("\n");
                    sb.append(String.format("Saldo: $%.2f\n", cuenta.getSaldo()));

                    if (cuenta instanceof CuentaAhorro) {
                        CuentaAhorro ca = (CuentaAhorro) cuenta;
                        sb.append(String.format("Cuota Mantenimiento: $%.2f\n", ca.getCuotaMantenimiento()));
                    } else if (cuenta instanceof CuentaCorriente) {
                        CuentaCorriente cc = (CuentaCorriente) cuenta;
                        sb.append(String.format("Transacciones: %d\n", cc.getTransacciones()));
                        sb.append(String.format("Importe por Transacción: $%.2f\n", cc.getImporteTransaccion()));
                    }

                    txtResultado.setText(sb.toString());
                } else {
                    txtResultado.setText("No se encontró ninguna cuenta con ese criterio de búsqueda.");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error en la búsqueda: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTablaCuentas() {
        try {
            modeloTabla.setRowCount(0);
            Cuenta[] cuentas = gestor.getCuentas();

            for (Cuenta c : cuentas) {
                if (c != null) {
                    String detalles = "";
                    if (c instanceof CuentaAhorro) {
                        detalles = String.format("Cuota: $%.2f", ((CuentaAhorro) c).getCuotaMantenimiento());
                    } else if (c instanceof CuentaCorriente) {
                        CuentaCorriente cc = (CuentaCorriente) c;
                        detalles = String.format("Trans: %d, Imp: $%.2f",
                                cc.getTransacciones(), cc.getImporteTransaccion());
                    }

                    modeloTabla.addRow(new Object[]{
                            c.getNumCuenta(),
                            c.getNombreCliente(),
                            c.getTipoCuenta(),
                            String.format("$%.2f", c.getSaldo()),
                            detalles
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar cuentas: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtNumCuenta.setText("");
        txtNombre.setText("");
        txtSaldo.setText("");
        if (txtCuota != null) txtCuota.setText("");
        if (txtTransacciones != null) txtTransacciones.setText("");
        if (txtImporte != null) txtImporte.setText("");

        if (txtNombre != null) txtNombre.setEnabled(false);
        if (txtSaldo != null) txtSaldo.setEnabled(false);
        if (txtCuota != null) txtCuota.setEnabled(false);
        if (btnEjecutar != null) btnEjecutar.setEnabled(false);
    }
}