public class Fire extends Spell {
    public Fire(String name, int manaCost, int baseDamage) {
        super(name, manaCost, baseDamage, "Fire");
    }

    @Override
    public int calculateDamage(Character caster) {

        return getBaseDamage() + (caster.getCharisma() / 3); // Example calculation
    }
}
