import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class GameApp {
    private static ArrayList<Account> accounts;
    private static Account authenticatedAccount;

    public static void main(String[] args) {
        accounts = JsonInput.deserializeAccounts();
        if (accounts == null) {
            System.out.println("Nu s-au putut încărca conturile.");
            return;
        }


        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(500, 400);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);

        JPanel panel = new BackgroundPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);


        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 22));
        emailLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        panel.add(emailLabel, gbc);

        JTextField emailField = new JTextField();
        emailField.setBackground(Color.BLACK);
        emailField.setForeground(Color.WHITE);
        emailField.setPreferredSize(new Dimension(250, 30));
        gbc.gridy = 1;
        panel.add(emailField, gbc);


        JLabel passwordLabel = new JLabel("Parolă:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 22));
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBackground(Color.BLACK);
        passwordField.setForeground(Color.WHITE);
        passwordField.setPreferredSize(new Dimension(250, 30));
        gbc.gridy = 3;
        panel.add(passwordField, gbc);


        JButton loginButton = new JButton("Autentificare");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(75, 0, 130));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(250, 40));
        gbc.gridy = 4;
        panel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                authenticatedAccount = authenticateUser(email, password);
                if (authenticatedAccount != null) {
                    loginFrame.dispose();
                    showCharacterSelectionScreen();
                } else {
                    showErrorMessage("Email sau parolă incorecte.");
                }
            }
        });

        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }

    private static Account authenticateUser(String email, String password) {
        for (Account account : accounts) {
            if (account.getInformation() != null &&
                    account.getInformation().getCredentials() != null &&
                    account.getInformation().getCredentials().getEmail().equals(email) &&
                    account.getInformation().getCredentials().getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    private static void showCharacterSelectionScreen() {
        if (authenticatedAccount.getCharacters().isEmpty()) {
            showErrorMessage("Nu ai niciun personaj disponibil.");
            return;
        }


        JFrame characterSelectionFrame = new JFrame("Alege un Personaj");
        characterSelectionFrame.setSize(500, 500);
        characterSelectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        characterSelectionFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(75, 0, 130));

        JComboBox<String> characterComboBox = new JComboBox<>();
        characterComboBox.setFont(new Font("Arial", Font.PLAIN, 18));
        characterComboBox.setBackground(Color.WHITE);
        characterComboBox.setForeground(Color.BLACK);


        for (Character character : authenticatedAccount.getCharacters()) {
            String characterInfo = character.getName() + " (Nivel " + character.getLevel()
                    + ", Exp " + character.getExperience()
                    + ", Viață: " + character.getCurrentHealth() + "/" + character.getMaxHealth()
                    + ", Mana: " + character.getCurrentMana() + "/" + character.getMaxMana() + ")";
            characterComboBox.addItem(characterInfo);
        }

        JLabel characterDetailsLabel = new JLabel("Selectează un personaj pentru detalii");
        characterDetailsLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        characterDetailsLabel.setForeground(Color.WHITE);

        characterComboBox.addActionListener(e -> {
            int selectedIndex = characterComboBox.getSelectedIndex();
            if (selectedIndex >= 0) {
                Character selectedCharacter = authenticatedAccount.getCharacters().get(selectedIndex);
                String details = "<html><b>Nume:</b> " + selectedCharacter.getName() + "<br>"
                        + "<b>Nivel:</b> " + selectedCharacter.getLevel() + "<br>"
                        + "<b>Experiență:</b> " + selectedCharacter.getExperience() + "<br>"
                        + "<b>Viață:</b> " + selectedCharacter.getCurrentHealth() + "/" + selectedCharacter.getMaxHealth() + "<br>"
                        + "<b>Mana:</b> " + selectedCharacter.getCurrentMana() + "/" + selectedCharacter.getMaxMana() + "</html>";
                characterDetailsLabel.setText(details);
            }
        });

        JButton selectButton = new JButton("Selectează Personaj");
        selectButton.setFont(new Font("Arial", Font.BOLD, 18));
        selectButton.setForeground(Color.WHITE);
        selectButton.setBackground(new Color(50, 0, 100));

        selectButton.addActionListener(e -> {
            String selectedCharacter = (String) characterComboBox.getSelectedItem();
            if (selectedCharacter != null) {
                Character selectedCharacterObject = authenticatedAccount.getCharacters().get(characterComboBox.getSelectedIndex());
                showGameGrid(selectedCharacterObject);
                characterSelectionFrame.dispose();
            }
        });

        panel.add(characterComboBox);
        panel.add(Box.createVerticalStrut(10));
        panel.add(characterDetailsLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(selectButton);

        characterSelectionFrame.add(panel);
        characterSelectionFrame.setVisible(true);
    }

    private static void showGameGrid(Character selectedCharacter) {
        Random random = new Random();
        int rows = 5 + random.nextInt(6);
        int cols = 5 + random.nextInt(6);

        Grid grid = new Grid(rows, cols);
        GameGUI gameGUI = new GameGUI(grid, selectedCharacter);
        gameGUI.showGame();
    }

    private static void showErrorMessage(String message) {
        JPanel errorPanel = new BackgroundPanel();
        errorPanel.setLayout(new BorderLayout());

        JLabel errorLabel = new JLabel(message, SwingConstants.CENTER);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 16));
        errorLabel.setForeground(Color.WHITE);
        errorPanel.add(errorLabel, BorderLayout.CENTER);

        JOptionPane optionPane = new JOptionPane(errorPanel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);

        JDialog errorDialog = optionPane.createDialog("Eroare");

        errorDialog.setSize(250, 120);
        errorDialog.setLocationRelativeTo(null);
        errorDialog.setVisible(true);
    }


    static class BackgroundPanel extends JPanel {
        public BackgroundPanel() {
            setPreferredSize(new Dimension(500, 400));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            Color startColor = new Color(75, 0, 130);
            Color endColor = Color.BLACK;
            GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
