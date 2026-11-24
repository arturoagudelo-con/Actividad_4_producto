import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            Ventana objeto = new Ventana();
            objeto.setVisible(true);
        });
    }
}
