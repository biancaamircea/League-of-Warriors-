public class Earth extends Spell {
    public Earth(String name, int manaCost, int baseDamage) {
        super(name, manaCost, baseDamage, "Earth");
    }

    @Override
    public int calculateDamage(Character caster) {
        return getBaseDamage() + caster.getStrength();
    }
}
