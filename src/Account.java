import java.io.*;
import java.util.*;

public class Account {
    private Information information;
    private ArrayList<Character> characters;
    private int gamesPlayed;

    public Account(Information information) {
        this.information = information;
        this.characters = new ArrayList<>();
        this.gamesPlayed = 0;
    }

    public Account(ArrayList<Character> characters, int gamesPlayed, Information information) {
        this.characters = characters;
        this.gamesPlayed = gamesPlayed;
        this.information = information;
    }

    public Account(String accountName, String country, int gamesPlayed, Credentials credentials, Set<String> favoriteGames, List<Character> characters) {
        this.information = new Information(credentials, new TreeSet<>(favoriteGames), accountName, country);
        this.characters = new ArrayList<>(characters);
        this.gamesPlayed = gamesPlayed;
    }




    public Information getInformation() {
        return this.information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public ArrayList<Character> getCharacters() {
        return this.characters;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public int getGamesPlayed() {
        return this.gamesPlayed;
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    public void addCharacter(Character character) {
        this.characters.add(character);
    }

    public static class Information {
        private Credentials credentials;
        private SortedSet<String> favoriteGames;
        private String name;
        private String country;

        public Information(Credentials credentials, SortedSet<String> favoriteGames, String name, String country) {
            this.credentials = credentials;
            this.favoriteGames = favoriteGames;
            this.name = name;
            this.country = country;
        }

        public String getName() {
            return this.name;
        }

        public String getCountry() {
            return this.country;
        }

        public Credentials getCredentials() {
            return this.credentials;
        }

        public SortedSet<String> getFavoriteGames() {
            return this.favoriteGames;
        }
    }

    public void updateCharacter(Character updatedCharacter) {
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i).getName().equals(updatedCharacter.getName())) {
                characters.set(i, updatedCharacter);  // Înlocuim personajul cu cel actualizat
                break;
            }
        }
    }

    public Character selectCharacter(Account userAccount) {
        Scanner scanner = new Scanner(System.in);

        // Afișăm lista de caractere disponibile și nivelurile lor
        System.out.println("Select a character to play with:");

        for (int i = 0; i < userAccount.getCharacters().size(); i++) {
            Character character = userAccount.getCharacters().get(i);
            System.out.println((i + 1) + ". " + character.getName() + " (Level " + character.getLevel() + ")");
        }

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt() - 1;

        // Validăm alegerea și returnăm personajul corespunzător
        if (choice >= 0 && choice < userAccount.getCharacters().size()) {
            return userAccount.getCharacters().get(choice);  // Returnăm personajul selectat
        } else {
            System.out.println("Invalid choice.");
            return null;
        }
    }


    public int getCharacterLevel(Character character) {
        for (Character c : characters) {
            if (c.getName().equals(character.getName())) {
                return c.getLevel();
            }
        }
        return 1;  // Returnăm nivelul 1 dacă nu găsim personajul (de exemplu, dacă nu există)
    }

    public static class InformationBuilder {
        private Credentials credentials;
        private SortedSet<String> favoriteGames = new TreeSet<>();
        private String name;
        private String country;

        public InformationBuilder setCredentials(Credentials credentials) {
            this.credentials = credentials;
            return this;
        }

        public InformationBuilder setFavoriteGames(Set<String> games) {
            this.favoriteGames = new TreeSet<>(games);
            return this;
        }

        public InformationBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public InformationBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Information build() {
            return new Information(credentials, favoriteGames, name, country);
        }
    }
}