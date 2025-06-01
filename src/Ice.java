public class Ice extends Spell {
    public Ice(String name, int manaCost, int baseDamage) {
        super(name, manaCost, baseDamage, "Ice");
    }

    @Override
    public int calculateDamage(Character caster) {
        return getBaseDamage() + (caster.getDexterity() / 2);
    }
}
