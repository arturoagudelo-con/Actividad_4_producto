import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            ventana objeto = new ventana();
            objeto.setVisible(true);
        });
    }
}
