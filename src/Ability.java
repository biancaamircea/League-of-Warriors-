public class Ability {
    private String name;
    private int damage;
    private int manaCost;

    public Ability(String name, int damage, int manaCost) {
        this.name = name;
        this.damage = damage;
        this.manaCost = manaCost;
    }

    @Override
    public String toString() {
        return name + " (Damage: " + damage + ", Mana Cost: " + manaCost + ")";
    }

    public String getName() {
        return name;
    }
}

