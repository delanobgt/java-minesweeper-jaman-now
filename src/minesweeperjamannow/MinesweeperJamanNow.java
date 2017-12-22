package minesweeperjamannow;

import javax.swing.SwingUtilities;

public class MinesweeperJamanNow {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
    
}
