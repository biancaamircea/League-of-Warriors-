import javax.swing.*;
import java.awt.*;
import java.util.*;

public class BattleScreen extends JPanel {
    private final Character player;
    private final Enemy enemy;
    private JLabel playerHealthLabel, enemyHealthLabel;
    private JProgressBar playerHealthBar, enemyHealthBar;
    private JPanel infoPanel;
    private JLabel cellTypeLabel;

    public BattleScreen(Character player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;

        setLayout(new BorderLayout());
        setBackground(new Color(75, 0, 130));

        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(4, 1));
        infoPanel.setBackground(new Color(75, 0, 130));
        add(infoPanel, BorderLayout.WEST);

        add(createStatsPanel(), BorderLayout.CENTER);
        add(createActionPanel(), BorderLayout.SOUTH);

        updateInfoPanel();
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.setBackground(new Color(75, 0, 130));

        playerHealthLabel = new JLabel("Player Health: " + player.getCurrentHealth());
        playerHealthLabel.setForeground(Color.WHITE);
        playerHealthBar = new JProgressBar(0, player.getMaxHealth());
        playerHealthBar.setValue(player.getCurrentHealth());

        enemyHealthLabel = new JLabel("Enemy Health: " + enemy.getCurrentHealth());
        enemyHealthLabel.setForeground(Color.WHITE);
        enemyHealthBar = new JProgressBar(0, enemy.getMaxHealth());
        enemyHealthBar.setValue(enemy.getCurrentHealth());

        panel.add(playerHealthLabel);
        panel.add(playerHealthBar);
        panel.add(enemyHealthLabel);
        panel.add(enemyHealthBar);

        return panel;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(75, 0, 130));

        JButton attackButton = new JButton("Attack");
        attackButton.addActionListener(e -> performAttack());

        JButton abilityButton = new JButton("Use Ability");
        abilityButton.addActionListener(e -> useAbility());

        panel.add(attackButton);
        panel.add(abilityButton);
        return panel;
    }

    private void updateInfoPanel() {
        infoPanel.removeAll();

        JLabel playerNameLabel = new JLabel("Character: " + player.getName());

        JLabel playerLevelLabel = new JLabel("Level: " + player.getLevel());

        JLabel enemyNameLabel = new JLabel("Enemy: " + enemy.getName() + " (" + enemy.getProfession() + ")");

        JLabel enemyAbilitiesLabel = new JLabel("Abilities: " + String.join(", ", enemy.getAbilities()));

        infoPanel.add(playerNameLabel);

        infoPanel.add(playerLevelLabel);
        infoPanel.add(enemyNameLabel);

        infoPanel.add(enemyAbilitiesLabel);

        infoPanel.revalidate();
        infoPanel.repaint();
    }

    private void performAttack() {
        int damage = player.attack(enemy);
        enemy.receiveDamage(damage);

        enemyHealthBar.setValue(enemy.getCurrentHealth());
        enemyHealthLabel.setText("Enemy Health: " + enemy.getCurrentHealth());

        System.out.println("Player attacked! Enemy takes " + damage + " damage.");

        if (enemy.getCurrentHealth() <= 0) {
            JOptionPane.showMessageDialog(this, "You won the battle!");
            return;
        }

        enemyTurn();
    }

    private void useAbility() {
        JDialog abilityDialog = new JDialog((Frame) null, "Select Ability", true);
        abilityDialog.setLayout(new FlowLayout());
        abilityDialog.setSize(300, 150);

        String[] abilities = generateRandomAbilities();
        JComboBox<String> abilityComboBox = new JComboBox<>(abilities);
        JButton confirmButton = new JButton("Confirm");

        confirmButton.addActionListener(e -> {
            int selectedIndex = abilityComboBox.getSelectedIndex();
            if (selectedIndex >= 0) {
                String selectedAbility = abilities[selectedIndex];
                int damage = useSelectedAbility(selectedAbility);

                enemy.receiveDamage(damage);

                enemyHealthBar.setValue(enemy.getCurrentHealth());
                enemyHealthLabel.setText("Enemy Health: " + enemy.getCurrentHealth());

                System.out.println("Ability used! Enemy takes " + damage + " damage.");

                if (enemy.getCurrentHealth() <= 0) {
                    JOptionPane.showMessageDialog(this, "You won the battle!");
                    return;
                }

                enemyTurn();
            }
            abilityDialog.dispose();
        });

        abilityDialog.add(abilityComboBox);
        abilityDialog.add(confirmButton);

        abilityDialog.setLocationRelativeTo(this);
        abilityDialog.setVisible(true);
    }

    private String[] generateRandomAbilities() {
        String[] allAbilities = {"Fire ", "Ice", "Earth", "Fire", "Ice", "Ice"};
        int numAbilities = 3 + new Random().nextInt(4);
        Set<String> selectedAbilities = new HashSet<>();

        while (selectedAbilities.size() < numAbilities) {
            String ability = allAbilities[new Random().nextInt(allAbilities.length)];
            selectedAbilities.add(ability);
        }

        return selectedAbilities.toArray(new String[0]);
    }

    private int useSelectedAbility(String ability) {
        int damage = 0;
        switch (ability) {
            case "Fire ":
                damage = 15;
                break;
            case "Ice ":
                damage = 20;
                break;
            case "Earth ":
                damage = 5;
                break;
            case "Ice":
                damage = 10;
                break;
            case "Fire":
                damage = 25;
                break;
            case "Earth":
                damage = 30;
                break;
        }
        return damage;
    }

    private void enemyTurn() {
        int damage = enemy.attack(player);
        player.receiveDamage(damage);

        playerHealthBar.setValue(player.getCurrentHealth());
        playerHealthLabel.setText("Player Health: " + player.getCurrentHealth());

        System.out.println("Enemy attacked! Player takes " + damage + " damage.");

        if (player.getCurrentHealth() <= 0) {
            JOptionPane.showMessageDialog(this, "You lost the battle.");
            return;
        }
    }
}
