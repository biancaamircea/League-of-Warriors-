public interface Battle {
    void receiveDamage(int damage);
    int calculateDamage();
    void useAbility(String ability, Entity enemy);
}
