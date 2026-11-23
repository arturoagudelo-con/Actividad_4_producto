import javax.swing.*;
import java.awt.*;
public class ventana extends JFrame {
    private JTextField monto;
    private JTextField nombreProducto;
    private JComboBox<String> tipoProducto;
    private JButton calcular;
    private JTextArea mostrar;
    private JPanel panel;

    public ventana () {
        setTitle("Calculadora producto");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblTipo = new JLabel("Tipo:");
        tipoProducto = new JComboBox<>(new String[]{"Comida", "Bebida"}); // CAMBIO: Opciones de subclases
        JLabel nombre = new JLabel("Producto:");
        nombreProducto = new JTextField(12);
        JLabel etiqueta = new JLabel("Monto:");
        monto = new JTextField(12);
        calcular = new JButton("Calcular");
        mostrar = new JTextArea(10, 36);
        mostrar.setEditable(false);

        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));

        panel.add(lblTipo);
        panel.add(tipoProducto);
        panel.add(etiqueta);
        panel.add(monto);
        panel.add(nombre);
        panel.add(nombreProducto);
        panel.add(calcular);
        panel.add(mostrar);


        add(panel);

        ConexionBD db = new ConexionBD();
        String estadoBD = db.probarConexion("jdbc:sqlite:tienda.db", "", "");
        if (estadoBD.startsWith("OK")) {
            mostrar.setForeground(new Color(0, 128, 0));
        } else {
            mostrar.setForeground(Color.RED);
        }
        mostrar.setText(estadoBD);

        descuento calcularDescuento = precio -> (precio > 20000.0) ? precio * 0.15 : 0;

        calcular.addActionListener(e -> {
            try {
                String nombreTexto = nombreProducto.getText().trim();
                double precio = Double.parseDouble(monto.getText().trim());

                if (nombreTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Escribe el nombre del producto");
                    return;
                }
                if (precio <= 0) {
                    JOptionPane.showMessageDialog(this, "El monto debe ser mayor a 0");
                    return;
                }

                Producto productoSeleccionado;
                if (tipoProducto.getSelectedItem().equals("Comida")) {
                    productoSeleccionado = new comida(nombreTexto, precio);
                } else {
                    productoSeleccionado = new bebida(nombreTexto, precio);
                }

                double descuentoAplicado = calcularDescuento.aplicarDescuento(productoSeleccionado.getPrecio());
                double valorFinal = productoSeleccionado.getPrecio() - descuentoAplicado;

                mostrar.setForeground(Color.BLACK);
                mostrar.setText(
                        "Producto: " + productoSeleccionado.getNombre() + "\n" +
                                productoSeleccionado.obtenerDetalleTipo() + "\n" + // Llamada polimórfica
                                "Descuento: $" + String.format("%.2f", descuentoAplicado) + "\n" +
                                "Valor total: $" + String.format("%.2f", valorFinal)
                );

            } catch (NumberFormatException excep) {
                JOptionPane.showMessageDialog(this, "Ingrese un dato numérico válido en el monto");
            }
        });

    }

}
