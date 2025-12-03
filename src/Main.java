import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Configurar Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear y mostrar ventana principal en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);

            // Mensaje de bienvenida
            JOptionPane.showMessageDialog(ventana,
                    "Bienvenido al Sistema Banco Azteca\n" +
                            "Sistema de Gestión de Cuentas y Préstamos\n\n" +
                            "Seleccione una opción del menú para comenzar.",
                    "Bienvenida",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }
}