public class Spell implements Visitor<Entity>{
    private String name;
    private int baseDamage;
    private int manaCost;
    private String type;

    public Spell(String name, int manaCost, int baseDamage, String type) {
        this.name = name;
        this.manaCost = manaCost;
        this.baseDamage = baseDamage;
        this.type = type;
    }


    public String getName() {
        return name;
    }


    public int getBaseDamage() {
        return baseDamage;
    }
    public int getDamage(Character caster) {
        return calculateDamage(caster);
    }


    public int getManaCost() {
        return manaCost;
    }


    public String getType() {
        return type;
    }
    public int calculateDamage(Character caster) {

        return baseDamage;
    }

    public void cast(Character caster, Enemy enemy) {
        if (caster.getCurrentMana() >= manaCost) {

            caster.setCurrentMana(caster.getCurrentMana() - manaCost);


            int damage = calculateDamage(caster);


            enemy.receiveDamage(damage);

            System.out.println(caster.getName() + " casts " + name + " for " + damage + " damage.");
        } else {
            System.out.println("Not enough mana to cast " + name);
        }
    }

    @Override
    public void visit(Entity entity) {

    }
}
