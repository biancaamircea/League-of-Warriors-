import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameGUI {
    private Grid grid;
    private JFrame gameFrame;
    private JPanel gamePanel;
    private JPanel infoPanel;
    private JPanel controlPanel;
    private int playerX;
    private int playerY;
    private Character player;

    public GameGUI(Grid grid, Character player) {
        this.grid = grid;
        this.player = player;

        this.gameFrame = new JFrame("Game Grid");
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameFrame.setSize(800, 600);
        this.gameFrame.setLocationRelativeTo(null);

        this.gamePanel = new JPanel();
        this.gamePanel.setLayout(new GridLayout(grid.size(), grid.get(0).size()));

        Random random = new Random();
        this.playerX = random.nextInt(grid.size());
        this.playerY = random.nextInt(grid.get(0).size());

        Cell startingCell = grid.get(playerX).get(playerY);
        startingCell.visit();

        this.infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setPreferredSize(new Dimension(200, 600));

        this.cellTypeLabel = new JLabel("You are now on a " + grid.get(playerX).get(playerY).getType().toString());

        this.controlPanel = new JPanel();
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGameOverTab();
            }
        });
        controlPanel.add(quitButton);

        gameFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> movePlayer(-1, 0);
                    case KeyEvent.VK_S -> movePlayer(1, 0);
                    case KeyEvent.VK_A -> movePlayer(0, -1);
                    case KeyEvent.VK_D -> movePlayer(0, 1);
                }
            }
        });
        gameFrame.setFocusable(true);
        gameFrame.requestFocusInWindow();

        gameFrame.add(infoPanel, BorderLayout.EAST);
        gameFrame.add(gamePanel, BorderLayout.CENTER);
        gameFrame.add(controlPanel, BorderLayout.SOUTH);

        upgradeGrid();

        gameFrame.setVisible(true);
    }

    private void showGameOverTab() {
        JFrame gameOverFrame = new JFrame("Game Over");
        gameOverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameOverFrame.setSize(1000, 800);
        gameOverFrame.setLocationRelativeTo(null);

        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS));
        gameOverPanel.setBackground(new Color(75, 0, 130));

        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 40));
        gameOverLabel.setForeground(Color.WHITE); // Text alb
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel playerDetails = new JLabel("Character: " + player.getName() + " | Health: " + player.getCurrentHealth() + " / " + player.getMaxHealth() +
                " | Level: " + player.getLevel());
        playerDetails.setFont(new Font("Arial", Font.PLAIN, 20));
        playerDetails.setForeground(Color.WHITE); // Text alb
        playerDetails.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 18));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(new Color(50, 0, 100));
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        gameOverPanel.add(Box.createVerticalStrut(50));
        gameOverPanel.add(gameOverLabel);
        gameOverPanel.add(Box.createVerticalStrut(30));
        gameOverPanel.add(playerDetails);
        gameOverPanel.add(Box.createVerticalStrut(50));
        gameOverPanel.add(closeButton);

        gameOverFrame.add(gameOverPanel);
        gameOverFrame.setVisible(true);

        gameFrame.setVisible(false);
    }

    private void movePlayer(int deltaX, int deltaY) {
        int newX = playerX + deltaX;
        int newY = playerY + deltaY;

        if (newX >= 0 && newX < grid.size() && newY >= 0 && newY < grid.get(0).size()) {
            Cell currentCell = grid.get(playerX).get(playerY);
            if (currentCell.getType() == CellEntityType.ENEMY || currentCell.getType() == CellEntityType.SANCTUARY) {
                currentCell.setType(CellEntityType.EMPTY);
                currentCell.visit();
            }

            Cell newCell = grid.get(newX).get(newY);
            if (newCell.getType() == CellEntityType.PORTAL) {
                JOptionPane.showMessageDialog(gameFrame, "You found a portal!");
                resetAllCellsToGrey();
                resetGame();
                randomizePlayerPosition();
            }

            playerX = newX;
            playerY = newY;

            String cellTypeMessage = "You are now on a " + newCell.getType().toString();
            cellTypeLabel.setText(cellTypeMessage);

            if (newCell.getType() == CellEntityType.ENEMY) {
                startBattle(new Enemy("Enemy", 50, 20, 10, 5, 3, "Enemy"));
            } else if (newCell.getType() == CellEntityType.SANCTUARY) {
                JOptionPane.showMessageDialog(gameFrame, "You found a sanctuary! Your health has been restored.");
                player.setCurrentHealth(player.getMaxHealth());
            }

            newCell.visit();
            upgradeGrid();
        }
    }

    private void resetAllCellsToGrey() {
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                grid.get(i).get(j).setVisited(false);
            }
        }
    }

    private void upgradeGrid() {
        gamePanel.removeAll();

        Color unvisitedColor = new Color(75, 0, 130);
        Color visitedColor = Color.black;
        Color playerColor = Color.white;
        Color portalColor = new Color(75, 0, 130);
        Color enemyColor = new Color(75, 0, 130);
        Color sanctuaryColor = new Color(75, 0, 130);

        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                JPanel cellPanel = new JPanel();
                Cell cell = grid.get(i).get(j);

                if (i == playerX && j == playerY) {
                    cellPanel.setBackground(playerColor);
                } else if (cell.getType() == CellEntityType.PORTAL) {
                    cellPanel.setBackground(portalColor);
                } else if (cell.getType() == CellEntityType.ENEMY) {
                    cellPanel.setBackground(enemyColor);
                } else if (cell.getType() == CellEntityType.SANCTUARY) {
                    cellPanel.setBackground(sanctuaryColor);
                } else if (cell.isVisited()) {
                    cellPanel.setBackground(visitedColor);
                } else {
                    cellPanel.setBackground(unvisitedColor);
                }

                gamePanel.add(cellPanel);
            }
        }

        updateInfoPanel();

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    private JLabel cellTypeLabel;

    private void updateInfoPanel() {
        infoPanel.removeAll();

        JLabel nameLabel = new JLabel("Character: " + player.getName());
        JLabel hpLabel = new JLabel("Health: " + player.getCurrentHealth() + " / " + player.getMaxHealth());
        JLabel levelLabel = new JLabel("Level: " + player.getLevel());

        infoPanel.add(nameLabel);
        infoPanel.add(hpLabel);
        infoPanel.add(levelLabel);

        infoPanel.add(cellTypeLabel);

        infoPanel.revalidate();
        infoPanel.repaint();
    }

    private void resetGame() {
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                grid.get(i).get(j).reset();
            }
        }

        randomizePlayerPosition();
        upgradeGrid();
    }

    private void randomizePlayerPosition() {
        Random random = new Random();

        while (true) {
            int newX = random.nextInt(grid.size());
            int newY = random.nextInt(grid.get(0).size());


            if (grid.get(newX).get(newY).getType() == CellEntityType.EMPTY) {
                playerX = newX;
                playerY = newY;
                break;
            }
        }
    }

    private void startBattle(Enemy enemy) {
        SwingUtilities.invokeLater(() -> {
            JFrame battleFrame = new JFrame("Battle Screen");
            BattleScreen battleScreen = new BattleScreen(player, enemy);
            battleFrame.add(battleScreen);
            battleFrame.setSize(800, 600);
            battleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            battleFrame.setVisible(true);
        });
    }

    public void showGame() {
        upgradeGrid();
        gameFrame.setVisible(true);
    }
}
