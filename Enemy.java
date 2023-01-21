import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Enemy extends LivingThings {
    private final HealthLabel healthLabel;
    private boolean labelAdded = false;
    private final int damage;

    /**
     * Constructor for objects of class Enemy.
     *
     * @param damage Damage to apply when it attacks.
     */
    public Enemy(int health, int damage) {
        super(health);
        this.damage = damage;
        healthLabel = new HealthLabel(this);
    }

    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        super.act();
        if (!labelAdded) {
            getWorld().addObject(healthLabel, getX(), getY());
            labelAdded = true;
        }
    }

    /**
     * @return The label actor which indicates its health.
     */
    public HealthLabel getHealthLabel() {
        return healthLabel;
    }

    /**
     * @return The amount of damage it can apply.
     */
    public int getDamage() {
        return damage;
    }
}
