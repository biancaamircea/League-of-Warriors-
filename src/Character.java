import java.util.*;
public abstract class Character extends Entity {
    private String name;
    private int experience;
    private int level;
    private int strength;
    private int charisma;
    private int dexterity;
    private int currentHealth;
    private int maxHealth;
    private int currentMana;
    private int maxMana;
    private ArrayList<Spell> spells = new ArrayList<>();
    private String profession;


    public Character(String name, int maxHealth, int maxMana, int strength, int charisma, int dexterity) {
        super(name, maxHealth, maxMana);
        this.name = name;
        this.profession = profession;
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;
        this.strength = strength;
        this.charisma = charisma;
        this.dexterity = dexterity;
        this.currentHealth = maxHealth;
        this.currentMana = maxMana;
        this.level = Math.max(level, 1);
    }


    public String getProfession() {
        return profession;
    }

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public ArrayList<Spell> getSpells() {
        return spells;
    }

    public void addSpell(Spell spell) {
        spells.add(spell);
    }

    public void regenerateHealth() {
        setCurrentHealth(getMaxHealth());
        System.out.println(getName() + " fully regenerated health.");
    }

    public void regenerateMana() {
        setCurrentMana(getMaxMana());
        System.out.println(getName() + " fully regenerated mana.");
    }


    public void displayCharacterInfo() {
        System.out.println("Name: " + name);
        System.out.println("Profession: " + profession);  // Afișăm profesia
        System.out.println("Level: " + level);
        System.out.println("Experience: " + experience);
        System.out.println("Health: " + currentHealth + "/" + maxHealth);
        System.out.println("Mana: " + currentMana + "/" + maxMana);
        System.out.println("Strength: " + strength);
        System.out.println("Charisma: " + charisma);
        System.out.println("Dexterity: " + dexterity);
    }


    public void chooseAction(Enemy enemy) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You encountered an enemy: " + enemy.getName());
        System.out.println("Choose your action:");
        System.out.println("1. Normal Attack");
        System.out.println("2. Use Ability");

        while (true) {
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    attack(enemy);
                    return;
                case "2":
                    if (!spells.isEmpty()) {
                        System.out.println("Choose a spell to use:");
                        for (int i = 0; i < spells.size(); i++) {
                            System.out.println((i + 1) + ". " + spells.get(i).getName());
                        }

                        int spellChoice = scanner.nextInt() - 1;
                        if (spellChoice >= 0 && spellChoice < spells.size()) {
                            Spell spell = spells.get(spellChoice);
                            spell.cast(this, enemy);
                            return;
                        } else {
                            System.out.println("Invalid spell choice.");
                        }
                    } else {
                        System.out.println("No spells available.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }





    public void useAbility(int spellIndex, Enemy enemy) {
        if (spellIndex < 0 || spellIndex >= spells.size()) {
            System.out.println("Invalid spell choice.");
            return;
        }

        Spell selectedSpell = spells.get(spellIndex);
        if (getCurrentMana() < selectedSpell.getManaCost()) {
            System.out.println("Not enough mana to cast " + selectedSpell.getName());
            return;
        }
        int damage = selectedSpell.getDamage(this);
        setCurrentMana(getCurrentMana() - selectedSpell.getManaCost());
        System.out.println(getName() + " used " + selectedSpell.getName() + " on " + enemy.getName() + " for " + damage + " damage.");
        enemy.receiveDamage(damage);
    }

    public int attack(Enemy enemy) {
        int damage = calculateDamage();
        System.out.println(getName() + " attacks " + enemy.getName() + " for " + damage + " damage.");
        enemy.receiveDamage(damage);
        return damage;
    }

    @Override
    public int calculateDamage() {

        return strength + dexterity;
    }


    @Override
    public void receiveDamage(int damage) {
        setCurrentHealth(Math.max(0, getCurrentHealth() - damage));
        System.out.println(getName() + " took " + damage + " damage. Remaining health: " + getCurrentHealth());
    }





    public void levelUp() {
        this.level++;
        System.out.println(this.name + " has leveled up! New Level: " + this.level);
    }

    public Character selectCharacter(Account selectedAccount) {
        ArrayList<Character> characters = selectedAccount.getCharacters();
        Scanner scanner = new Scanner(System.in);
        CharacterFactory characterFactory = new CharacterFactory();
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
                    System.out.println("Level: " + selectedCharacter.getLevel());;
                    return selectedCharacter;
                }
                System.out.println("Invalid character number. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }


}