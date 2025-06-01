import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends Entity {
    private final List<String> abilities;

    public Enemy(String name, int maxHealth, int maxMana, int strength, int charisma, int dexterity, String profession) {
        super(name, maxHealth, maxMana);
        this.profession = profession;
        this.abilities = new ArrayList<>();
        generateRandomAbilities();
    }


    private void generateRandomAbilities() {
        String[] allAbilities = {"Fire", "Earth", "Ice"};
        Random random = new Random();
        int abilityCount = random.nextInt(3) + 3;

        for (int i = 0; i < abilityCount; i++) {
            String ability = allAbilities[random.nextInt(allAbilities.length)];
            if (!abilities.contains(ability)) {
                abilities.add(ability);
            }
        }
    }
    private final String profession;
    public String getProfession() {
        return profession;
    }
    private void generateProfessionAbilities() {
        Random random = new Random();

        // Profession-based abilities
        switch (profession.toLowerCase()) {
            case "Rogue":
                abilities.add("earth");
                break;
            case "Mage":
                abilities.add("ice");

                break;
            case "Warrior":
                abilities.add("fire");
                break;
            default:
                abilities.add("Basic Attack");
        }
    }

    @Override
    public void useAbility(String ability, Entity target) {
        if (!abilities.contains(ability)) {
            System.out.println("Ability not available.");
            return;
        }

        System.out.println(this.getName() + " used " + ability + " on " + target.getName());
        int damage = calculateDamage();
        System.out.println("It dealt " + damage + " damage!");
        target.receiveDamage(damage);
    }
    private void generateAbilitiesBasedOnProfession() {
        Random random = new Random();
        // Profession-based abilities
        switch (profession.toLowerCase()) {
            case "rogue":
                abilities.add("Earth");
                break;
            case "mage":
                abilities.add("Ice");
                break;
            case "warrior":
                abilities.add("Fire");
                break;
            default:
                abilities.add("Basic Attack");
        }


        String[] allAbilities = {"Fire", "Earth", "Ice"};
        int abilityCount = random.nextInt(3) + 3;

        for (int i = 0; i < abilityCount; i++) {
            String ability = allAbilities[random.nextInt(allAbilities.length)];
            if (!abilities.contains(ability)) {
                abilities.add(ability);
            }
        }
    }

    public int attack(Character player) {
        int damage = calculateDamage();
        System.out.println("Enemy attacked " + player.getName() + " for " + damage + " damage.");
        player.receiveDamage(damage);
        return damage;
    }

    @Override
    public int calculateDamage() {
        Random random = new Random();
        return random.nextInt(10) + 5;
    }

    public List<String> getAbilities() {
        return abilities;
    }
}
