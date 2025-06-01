
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class JsonInput {
    public static ArrayList<Account> deserializeAccounts() {
        String accountPath = "C:\\Users\\mirce\\Downloads\\temabianca\\temabianca\\src\\accounts.json";
        try {
            String content = new String(Files.readAllBytes(Paths.get(accountPath)));
            JSONObject obj = (JSONObject) new JSONParser().parse(content);
            JSONArray accountsArray = (JSONArray) obj.get("accounts");

            if (accountsArray == null) {
                System.out.println("No accounts found in the JSON.");
                return null;
            }

            ArrayList<Account> accounts = new ArrayList<>();
            for (int i = 0; i < accountsArray.size(); i++) {
                JSONObject accountJson = (JSONObject) accountsArray.get(i);

                String name = (String) accountJson.get("name");
                String country = (String) accountJson.get("country");
                int mapsCompleted = Integer.parseInt((String) accountJson.get("maps_completed"));

                Credentials credentials = null;
                try {
                    JSONObject credentialsJson = (JSONObject) accountJson.get("credentials");
                    String email = (String) credentialsJson.get("email");
                    String password = (String) credentialsJson.get("password");

                    credentials = new Credentials(email, password);
                } catch (Exception e) {
                    System.out.println("! This account doesn't have all credentials !");
                }

                SortedSet<String> favoriteGames = new TreeSet<>();
                try {
                    JSONArray games = (JSONArray) accountJson.get("favorite_games");
                    for (int j = 0; j < games.size(); j++) {
                        favoriteGames.add((String) games.get(j));
                    }
                } catch (Exception e) {
                    System.out.println("! This account doesn't have favorite games !");
                }

                ArrayList<Character> characters = new ArrayList<>();
                try {
                    JSONArray charactersListJson = (JSONArray) accountJson.get("characters");
                    for (int j = 0; j < charactersListJson.size(); j++) {
                        JSONObject charJson = (JSONObject) charactersListJson.get(j);
                        String cname = (String) charJson.get("name");
                        String profession = (String) charJson.get("profession");
                        int level = Integer.parseInt((String) charJson.get("level"));
                        int experience = ((Long) charJson.get("experience")).intValue();

                        Character newCharacter = null;
                        switch (profession) {
                            case "Warrior":
                                newCharacter = new Warrior(cname, experience, level, 10, 5, 8);
                                break;
                            case "Mage":
                                newCharacter = new Mage(cname, experience, level, 8, 10, 6);
                                break;
                            case "Rogue":
                                newCharacter = new Rogue(cname, experience, level, 7, 6, 10);
                                break;
                        }
                        characters.add(newCharacter);
                    }
                } catch (Exception e) {
                    System.out.println("! This account doesn't have characters !");
                }

                Account.Information information = new Account.Information(credentials, favoriteGames, name, country);
                Account account = new Account(characters, mapsCompleted, information);
                accounts.add(account);
            }

            return accounts;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}

