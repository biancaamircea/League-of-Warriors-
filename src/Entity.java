import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Battle, Element<Entity> {
    protected String name;
    protected int currentHealth;
    protected int maxHealth;
    protected int currentMana;
    protected int maxMana;
    protected ArrayList<String> abilities;

    public Entity(String name, int maxHealth, int maxMana) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.maxMana = maxMana;
        this.currentMana = maxMana;
        this.abilities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public void addAbility(String ability) {
        if (!abilities.contains(ability)) {
            abilities.add(ability);
        }
    }

    @Override
    public void accept(Visitor<Entity> visitor) {
        visitor.visit(this);
    }

    public abstract void useAbility(String ability, Entity enemy);

    @Override
    public void receiveDamage(int damage) {
        this.currentHealth = Math.max(0, this.currentHealth - damage);
        System.out.println(name + " received " + damage + " damage. Remaining health: " + currentHealth);
    }
}
