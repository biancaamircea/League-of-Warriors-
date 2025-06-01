import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CharacterSelectionGUI {
    private static ArrayList<Account> accounts;
    private static Account currentAccount;

    public static void main(String[] args) {
        accounts = JsonInput.deserializeAccounts();

        JFrame frame = new JFrame("Character Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JComboBox<String> characterComboBox = new JComboBox<>();
        JTextArea characterDetails = new JTextArea(5, 30);
        characterDetails.setEditable(false);

        currentAccount = accounts.get(0);

        for (Character character : currentAccount.getCharacters()) {
            characterComboBox.addItem(character.getName());
        }

        characterComboBox.addActionListener(e -> {
            String selectedCharacterName = (String) characterComboBox.getSelectedItem();
            Character selectedCharacter = getCharacterByName(selectedCharacterName);
            if (selectedCharacter != null) {
                String details = "Name: " + selectedCharacter.getName() + "\n" +
                        "Profession: " + selectedCharacter.getProfession() + "\n" +
                        "Level: " + selectedCharacter.getLevel() + "\n" +
                        "Experience: " + selectedCharacter.getExperience();
                characterDetails.setText(details);
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel("Select a Character:"), BorderLayout.NORTH);
        panel.add(characterComboBox, BorderLayout.CENTER);
        panel.add(new JScrollPane(characterDetails), BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static Character getCharacterByName(String name) {
        for (Character character : currentAccount.getCharacters()) {
            if (character.getName().equals(name)) {
                return character;
            }
        }
        return null;
    }
}
