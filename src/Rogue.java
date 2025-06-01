public class Rogue extends Character {

    public Rogue(String name, int experience, int level, int strength, int charisma, int dexterity) {
        super(name, 120 + level * 8, 70 + level * 3, strength, charisma, dexterity);
        setExperience(experience);
    }

    @Override
    public int calculateDamage() {
        return getDexterity() * 3;
    }

    @Override
    public void levelUp() {
        setLevel(getLevel() + 1);
        setMaxHealth(getMaxHealth() + 8);
        setDexterity(getDexterity() + 3);
        setCharisma(getCharisma() + 2);
    }

    @Override
    public void useAbility(String ability, Entity enemy) {
        switch (ability.toLowerCase()) {
            case "ice":
                if (getCurrentMana() >= 12) {
                    int damage = calculateDamage() + 12;
                    System.out.println(getName() + " casts Ice spell for " + damage + " damage.");
                    enemy.receiveDamage(damage);
                    setCurrentMana(getCurrentMana() - 12);
                } else {
                    System.out.println("Not enough mana to cast Ice spell.");
                }
                break;
            default:
                System.out.println(getName() + " doesn't know the ability " + ability);
                break;
        }
    }
}
