public class Mage extends Character {

    public Mage(String name, int experience, int level, int strength, int charisma, int dexterity) {

        super(name, 100 + level * 10, 150 + level * 5, strength, charisma, dexterity);
        setExperience(experience);
    }

    @Override
    public int calculateDamage() {
        return getDexterity() * 2 + getCharisma();
    }

    @Override
    public void levelUp() {
        setLevel(getLevel() + 1);
        setMaxHealth(getMaxHealth() + 10);
        setMaxMana(getMaxMana() + 5);
        setDexterity(getDexterity() + 2);
        setCharisma(getCharisma() + 3);
    }

    @Override
    public void useAbility(String ability, Entity enemy) {
        switch (ability.toLowerCase()) {
            case "fire":
                if (getCurrentMana() >= 15) {
                    int damage = calculateDamage() + 10;
                    System.out.println(getName() + " casts Fire spell for " + damage + " damage.");
                    enemy.receiveDamage(damage);
                    setCurrentMana(getCurrentMana() - 15);
                } else {
                    System.out.println("Not enough mana to cast Fire spell.");
                }
                break;
            default:
                System.out.println(getName() + " doesn't know the ability " + ability);
                break;
        }
    }
}
