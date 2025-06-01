public class Warrior extends Character {

    public Warrior(String name, int experience, int level, int strength, int charisma, int dexterity) {

        super(name, 150 + level * 15, 50 + level * 2, strength, charisma, dexterity);
        setExperience(experience);
    }

    @Override
    public int calculateDamage() {
        return getStrength() * 3;
    }

    @Override
    public void levelUp() {
        setLevel(getLevel() + 1);
        setMaxHealth(getMaxHealth() + 15);
        setStrength(getStrength() + 3);
        setCharisma(getCharisma() + 1);
    }

    @Override
    public void useAbility(String ability, Entity enemy) {
        switch (ability.toLowerCase()) {
            case "earth":
                if (getCurrentMana() >= 10) {
                    int damage = calculateDamage() + 15;
                    System.out.println(getName() + " uses Earth spell for " + damage + " damage.");
                    enemy.receiveDamage(damage);
                    setCurrentMana(getCurrentMana() - 10);
                } else {
                    System.out.println("Not enough mana to cast Earth spell.");
                }
                break;
            default:
                System.out.println(getName() + " doesn't know the ability " + ability);
                break;
        }
    }
}
