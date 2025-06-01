import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private static Game instance = null;
    private ArrayList<Account> accounts;
    private Grid gameGrid;


    private Game() {
        accounts = JsonInput.deserializeAccounts();

        if (accounts == null || accounts.isEmpty()) {
            System.out.println("Error: Accounts not loaded or the list is empty.");
        } else {
            System.out.println("Accounts loaded successfully.");
        }

        int gridWidth = (int) (Math.random() * 6) + 5;
        int gridHeight = (int) (Math.random() * 6) + 5;

        gameGrid = new Grid(gridWidth, gridHeight);
    }


    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }


    public void displayAccounts() {
        System.out.println("Available accounts:");
        if (accounts != null && !accounts.isEmpty()) {
            for (int i = 0; i < accounts.size(); i++) {
                Account account = accounts.get(i);
                System.out.println((i + 1) + ". " + account.getInformation().getName());
            }
        } else {
            System.out.println("No accounts available.");
        }
    }


    public Account authenticateUser() {
        Scanner scanner = new Scanner(System.in);

        if (accounts == null || accounts.isEmpty()) {
            System.out.println("No accounts available.");
            return null;
        }

        while (true) {
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            for (Account account : accounts) {
                Credentials credentials = account.getInformation().getCredentials();
                if (credentials.getEmail().equals(email) && credentials.getPassword().equals(password)) {
                    System.out.println("Authentication successful!");
                    displayAccountDetails(account);
                    return account;
                }
            }
            System.out.println("Invalid email or password. Please try again.");
        }
    }


    private void displayAccountDetails(Account account) {
        System.out.println("Account Details:");
        System.out.println("Name: " + account.getInformation().getName());
        System.out.println("Country: " + account.getInformation().getCountry());
        System.out.println("Favorite Games: " + String.join(", ", account.getInformation().getFavoriteGames()));
    }


    public Character selectCharacter(Account selectedAccount) {
        ArrayList<Character> characters = selectedAccount.getCharacters();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a character to play with:");

        for (int i = 0; i < characters.size(); i++) {
            Character character = characters.get(i);
            System.out.println((i + 1) + ". " + character.getName() + " (Level " + character.getLevel() + ")");
        }

        while (true) {
            System.out.print("Select a character by number: ");
            try {
                int characterIndex = Integer.parseInt(scanner.nextLine()) - 1;
                if (characterIndex >= 0 && characterIndex < characters.size()) {
                    Character selectedCharacter = characters.get(characterIndex);
                    System.out.println("\nCharacter Details:");
                    System.out.println("Name: " + selectedCharacter.getName());
                    System.out.println("Level: " + selectedCharacter.getLevel());
                    System.out.println("Current Health: " + selectedCharacter.getCurrentHealth() + "/" + selectedCharacter.getMaxHealth());
                    System.out.println("Mana: " + selectedCharacter.getCurrentMana() + "/" + selectedCharacter.getMaxMana());
                    System.out.println("Strength: " + selectedCharacter.getStrength());
                    System.out.println("Charisma: " + selectedCharacter.getCharisma());
                    System.out.println("Dexterity: " + selectedCharacter.getDexterity());
                    return selectedCharacter;
                }
                System.out.println("Invalid character number. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
