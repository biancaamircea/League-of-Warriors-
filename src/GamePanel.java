import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private JComboBox<String> characterComboBox;
    private JLabel characterDetailsLabel;
    private JPanel infoPanel;
    private Account authenticatedAccount;

    public GamePanel() {
        characterComboBox = new JComboBox<>();
        characterDetailsLabel = new JLabel();
        infoPanel = new JPanel();

        this.setBackground(new Color(50, 0, 50));
        characterDetailsLabel.setForeground(Color.WHITE);
        infoPanel.setBackground(new Color(50, 0, 50));

        Font font = new Font("Arial", Font.PLAIN, 14);
        characterDetailsLabel.setFont(font);

        this.setLayout(new BorderLayout());
        this.add(characterComboBox, BorderLayout.NORTH);
        this.add(characterDetailsLabel, BorderLayout.CENTER);
        this.add(infoPanel, BorderLayout.EAST);

        if (authenticatedAccount != null) {
            for (Character character : authenticatedAccount.getCharacters()) {
                characterComboBox.addItem(character.getName());
            }
        }

        characterComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = characterComboBox.getSelectedIndex();
                if (selectedIndex >= 0) {
                    Character selectedCharacter = authenticatedAccount.getCharacters().get(selectedIndex);
                    String details = "<html>"
                            + "Nume: " + selectedCharacter.getName() + "<br>"
                            + "Profesie: " + selectedCharacter.getProfession() + "<br>"
                            + "Nivel: " + selectedCharacter.getLevel() + "<br>"
                            + "Experiență: " + selectedCharacter.getExperience() + "<br>"
                            + "Sănătate: " + selectedCharacter.getCurrentHealth() + "<br>"
                            + "Mana: " + selectedCharacter.getCurrentMana()
                            + "</html>";
                    characterDetailsLabel.setText(details);
                }
            }
        });
    }

    public void updateInfoPanel() {
        infoPanel.removeAll();

        Character activeCharacter = authenticatedAccount.getCharacters().get(0);

        JLabel characterNameLabel = new JLabel("Nume: " + activeCharacter.getName());
        JLabel characterProfessionLabel = new JLabel("Profesie: " + activeCharacter.getProfession());
        JLabel characterLevelLabel = new JLabel("Nivel: " + activeCharacter.getLevel());
        JLabel characterExperienceLabel = new JLabel("Experiență: " + activeCharacter.getExperience());

        characterNameLabel.setForeground(Color.WHITE);
        characterProfessionLabel.setForeground(Color.WHITE);
        characterLevelLabel.setForeground(Color.WHITE);
        characterExperienceLabel.setForeground(Color.WHITE);

        infoPanel.add(characterNameLabel);
        infoPanel.add(characterProfessionLabel);
        infoPanel.add(characterLevelLabel);
        infoPanel.add(characterExperienceLabel);

        infoPanel.revalidate();
        infoPanel.repaint();
    }

    public void setCurrentAccount(Account account) {
        this.authenticatedAccount = account;
        updateInfoPanel();
    }
}
