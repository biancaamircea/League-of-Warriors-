public class CharacterFactory {
    public static Character createCharacter(String type, String name, int level, int experience) {

        switch (type) {
            case "Warrior":
                return new Warrior(name, experience, level, 10 + level, 5 + level, 8 + level);
            case "Mage":
                return new Mage(name, experience, level, 8 + level, 10 + level, 6 + level);
            case "Rogue":
                return new Rogue(name, experience, level, 7 + level, 6 + level, 10 + level);
            default:
                throw new IllegalArgumentException("Invalid character type: " + type);
        }
    }
}
