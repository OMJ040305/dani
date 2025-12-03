import javax.swing.*;
import java.awt.*;

public class PanelMovimientos extends JPanel {
    private GestorCuentas gestor;
    private String operacion;
    private JTextField txtNumCuenta, txtMonto;
    private JTextArea txtInfo;

    public PanelMovimientos(GestorCuentas gestor, String operacion) {
        this.gestor = gestor;
        this.operacion = operacion;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel de entrada
        JPanel panelEntrada = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelEntrada.add(new JLabel("Número de Cuenta:"), gbc);
        gbc.gridx = 1;
        txtNumCuenta = new JTextField(20);
        panelEntrada.add(txtNumCuenta, gbc);

        if (!operacion.equals("SALDO")) {
            gbc.gridx = 0; gbc.gridy = 1;
            panelEntrada.add(new JLabel("Monto:"), gbc);
            gbc.gridx = 1;
            txtMonto = new JTextField(20);
            panelEntrada.add(txtMonto, gbc);
        }

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton btnEjecutar = new JButton(getTextoBoton());
        btnEjecutar.setBackground(getColorBoton());
        btnEjecutar.setForeground(Color.WHITE);
        btnEjecutar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEjecutar.addActionListener(e -> ejecutarOperacion());
        panelEntrada.add(btnEjecutar, gbc);

        add(panelEntrada, BorderLayout.NORTH);

        // Área de información
        txtInfo = new JTextArea(15, 40);
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(txtInfo);
        add(scroll, BorderLayout.CENTER);
    }

    private String getTextoBoton() {
        switch (operacion) {
            case "CARGO": return "Realizar Cargo";
            case "ABONO": return "Realizar Abono";
            case "SALDO": return "Consultar Saldo";
            default: return "Ejecutar";
        }
    }

    private Color getColorBoton() {
        switch (operacion) {
            case "CARGO": return new Color(204, 0, 0);
            case "ABONO": return new Color(0, 153, 0);
            case "SALDO": return new Color(0, 102, 204);
            default: return Color.GRAY;
        }
    }

    private void ejecutarOperacion() {
        try {
            String numCuenta = txtNumCuenta.getText().trim();

            if (numCuenta.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debe ingresar un número de cuenta",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cuenta cuenta = gestor.buscarCuentaPorNumero(numCuenta);
            if (cuenta == null) {
                JOptionPane.showMessageDialog(this,
                        "No se encontró la cuenta",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            switch (operacion) {
                case "CARGO":
                    realizarCargo(cuenta);
                    break;
                case "ABONO":
                    realizarAbono(cuenta);
                    break;
                case "SALDO":
                    consultarSaldo(cuenta);
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void realizarCargo(Cuenta cuenta) {
        try {
            double monto = Double.parseDouble(txtMonto.getText().trim());

            if (monto <= 0) {
                JOptionPane.showMessageDialog(this,
                        "El monto debe ser mayor a cero",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (gestor.realizarCargo(cuenta.getNumCuenta(), monto)) {
                StringBuilder sb = new StringBuilder();
                sb.append("CARGO REALIZADO EXITOSAMENTE\n");
                sb.append("===========================\n\n");
                sb.append("Cuenta: ").append(cuenta.getNumCuenta()).append("\n");
                sb.append("Cliente: ").append(cuenta.getNombreCliente()).append("\n");
                sb.append(String.format("Monto cargado: $%.2f\n", monto));
                sb.append(String.format("Saldo nuevo: $%.2f\n", cuenta.getSaldo()));

                txtInfo.setText(sb.toString());
                txtMonto.setText("");

                JOptionPane.showMessageDialog(this,
                        "Cargo realizado exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo realizar el cargo. Saldo insuficiente.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El monto debe ser un número válido",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarAbono(Cuenta cuenta) {
        try {
            double monto = Double.parseDouble(txtMonto.getText().trim());

            if (monto <= 0) {
                JOptionPane.showMessageDialog(this,
                        "El monto debe ser mayor a cero",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (gestor.realizarAbono(cuenta.getNumCuenta(), monto)) {
                StringBuilder sb = new StringBuilder();
                sb.append("ABONO REALIZADO EXITOSAMENTE\n");
                sb.append("============================\n\n");
                sb.append("Cuenta: ").append(cuenta.getNumCuenta()).append("\n");
                sb.append("Cliente: ").append(cuenta.getNombreCliente()).append("\n");
                sb.append(String.format("Monto abonado: $%.2f\n", monto));
                sb.append(String.format("Saldo nuevo: $%.2f\n", cuenta.getSaldo()));

                txtInfo.setText(sb.toString());
                txtMonto.setText("");

                JOptionPane.showMessageDialog(this,
                        "Abono realizado exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El monto debe ser un número válido",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarSaldo(Cuenta cuenta) {
        StringBuilder sb = new StringBuilder();
        sb.append("INFORMACIÓN DE LA CUENTA\n");
        sb.append("========================\n\n");
        sb.append("Número de Cuenta: ").append(cuenta.getNumCuenta()).append("\n");
        sb.append("Cliente: ").append(cuenta.getNombreCliente()).append("\n");
        sb.append("Tipo: ").append(cuenta.getTipoCuenta()).append("\n");
        sb.append(String.format("Saldo actual: $%.2f\n", cuenta.getSaldo()));

        txtInfo.setText(sb.toString());
    }
}