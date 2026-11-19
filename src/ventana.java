import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class ventana extends JFrame {
    private JTextField monto;
    private JTextField nombreProducto;
    private JButton calcular;
    private JTextArea mostar;
    private JPanel panel;

    public ventana () {
        setTitle("Calculadora producto");
        setSize(500,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel nombre = new JLabel("Producto");
        nombreProducto = new JTextField(12);
        JLabel etiqueta = new JLabel("monto");
        monto = new JTextField(12);
        calcular = new JButton("Calcular");
        mostar = new JTextArea(10,36);
        mostar.setEditable(false);

        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));

        panel.add(etiqueta);
        panel.add(monto);
        panel.add(nombre);
        panel.add(nombreProducto);
        panel.add(calcular);
        panel.add(mostar);


        add(panel);

        probarConexionBD("jdbc:sqlite:tienda.db", "", "");

        descuento calcularDescuento = precio -> (precio > 20000.0 ) ? precio * 0.15 : 0;


        calcular.addActionListener(e ->{
            try{
            String producto = nombreProducto.getText().trim();
            double precio = Double.parseDouble(monto.getText().trim());

            if(producto.isEmpty()){
                JOptionPane.showMessageDialog(this, "Escribe el nombre del producto");
                return;
            }

            Producto productos = new Producto(producto, precio);
            double descuentoPorcentaje = calcularDescuento.aplicarDescuento(productos.getPrecio());
            double valor =  productos.getPrecio() - descuentoPorcentaje ;

                mostar.setForeground(Color.BLACK);
                mostar.setText(
                    "Producto: "+ productos.getNombre() +"\n"+
                    "Descuento: "+ String.format("%.2f", descuentoPorcentaje) + "\n" +
                    "Valor total: $" + String.format("%.2f", valor)
            );


            } catch (NumberFormatException excep){
                JOptionPane.showMessageDialog(this, "ingrese un dato real");
            }

        });


    }

    private void probarConexionBD(String url, String usuario, String contrasenia) {
        try {
            if (url.contains("sqlite")) {
                Class.forName("org.sqlite.JDBC");
            } else if (url.contains("mysql")) {
                Class.forName("com.mysql.cj.jdbc.Driver");
            }

            // try-with-resources cierra automáticamente Connection y Statement
            try (Connection conn = DriverManager.getConnection(url, usuario, contrasenia);
                Statement stmt = conn.createStatement()) {

                if (conn != null && !conn.isClosed()) {
                    // Simulación de consulta simple ("SELECT 1")
                    stmt.executeQuery("SELECT 1");

                    mostar.setForeground(new Color(0, 128, 0)); // Verde oscuro (más legible que Color.GREEN)
                    mostar.setText("Conexión exitosa a la base de datos local.");
                }
            }
        } catch (ClassNotFoundException ex) {
            mostar.setForeground(Color.RED);
            mostar.setText("Fallo de conexión: No se encontró el Driver de la BD.\nDetalle: " + ex.getMessage());
        } catch (SQLException ex) {
            mostar.setForeground(Color.RED);
            mostar.setText("Fallo de conexión con la base de datos.\nError SQL: " + ex.getMessage());
        }
    }

}
