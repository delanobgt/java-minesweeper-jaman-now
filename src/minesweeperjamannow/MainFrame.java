package minesweeperjamannow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {
    
    private final int BOARD_WIDTH = MinesweeperModel.BOARD_WIDTH;
    private final int BOARD_HEIGHT = MinesweeperModel.BOARD_HEIGHT;
    private static final Map<Integer, Color> colorMap = new HashMap<>();   
    private JButton[][] buttons = 
            new JButton[BOARD_WIDTH][BOARD_HEIGHT];
    private MinesweeperModel gameModel = new MinesweeperModel();
    
    static {
        colorMap.put(-1, Color.RED);
        colorMap.put(0, Color.WHITE);
        colorMap.put(1, Color.BLUE);
        colorMap.put(2, Color.GREEN);
        colorMap.put(3, Color.MAGENTA);
        colorMap.put(4, Color.PINK);
        colorMap.put(5, Color.DARK_GRAY);
        colorMap.put(6, Color.ORANGE);
        colorMap.put(7, Color.YELLOW);
        colorMap.put(8, Color.BLACK);
    };
    
    public MainFrame() {
        super("Minesweeper Jaman Now");
        initUI();
    }
    
    private void initUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(BOARD_WIDTH*25+18, BOARD_HEIGHT*25+30+10);
        this.setLocationRelativeTo(null);
        initButtons();
    }
    
    private void initButtons() {
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                JButton btn = new JButton();
                add(btn);
                buttons[x][y] = btn;
                btn.setSize(25, 25);
                btn.setFocusable(false);
                btn.setFocusPainted(false);
                btn.setBounds(x*25, y*25, 25, 25);
                btn.setBackground(Color.LIGHT_GRAY);
                btn.setMargin(new Insets(0, 0, 0, 0));
                btn.setFont(new Font("Arial", Font.BOLD, 16));
                {
                    final int finalX = x;
                    final int finalY = y;
                    btn.addActionListener((e) -> {
                        if (gameModel.getMapVisibleAt(finalX, finalY)) return;
                        gameModel.openBlockAt(finalX, finalY);
                        updateButtons();
                        if (gameModel.checkLose(finalX, finalY)) {
                            JOptionPane.showMessageDialog(
                                    null, 
                                    "You step on a bomb", 
                                    "Game Over!", 
                                    JOptionPane.OK_OPTION
                            );
                            System.exit(0);
                        } else if (gameModel.checkWin()) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "You won the game!"
                            );
                            System.exit(0);
                        }
                    });
                }
            }
        }
    }
    
    private void updateButtons() {
        SwingUtilities.invokeLater(() -> {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                for (int y = 0; y < BOARD_HEIGHT; y++) {
                    JButton btn = buttons[x][y];
                    if (gameModel.getMapVisibleAt(x, y)) {
                        btn.setBackground(Color.WHITE);
                        int status = gameModel.getMapStatusAt(x, y);
                        if (status == -1) {
                            btn.setBackground(Color.RED);
                            ImageIcon icon = 
                                new ImageIcon(getClass().getResource("/res/unnamed.png"));
                            btn.setIcon(icon);
                        } else if (status > 0) {
                            btn.setText(Integer.toString(status));
                        }
                        btn.setForeground(colorMap.get(status));
                    }
                }
            }
        });
    }
    
}
