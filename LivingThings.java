import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LivingThings here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LivingThings extends GravityBound {
    private final int defaultHealth;
    private int health;
    private int invulnerableCounter = 0;
    private int invulnerableTime = 2;

    /**
     * Constructor for objects of class LivingThings.
     *
     * @param health The default and maximum health it will have.
     */
    public LivingThings(int health) {
        this.health = health;
        defaultHealth = this.health;
    }

    /**
     * Act - do whatever the LivingThings wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        super.act();
        detectEdge();
        invulnerableCounter--;
    }

    /**
     * @param invulnerableTime The time period that it doesn't take damage.
     */
    public void setInvulnerableTime(int invulnerableTime) {
        this.invulnerableTime = invulnerableTime;
    }

    /**
     * Increase or decrease its health by an amount.
     * Increase: The final health will not exceed the default maximum health.
     * Decrease: The target's health will not be decreased again for some period.
     *
     * @param amount The amount of live or health.
     */
    public void modifyHealth(int amount) {
        if (amount >= 0) {
            health += amount;
            if (health >= defaultHealth) {
                health = defaultHealth;
            }
        } else {
            if (invulnerableCounter <= 0) {
                invulnerableCounter = invulnerableTime;
                health += amount;
                Greenfoot.playSound("NPC_Hit_1.wav");
            }
        }
    }

    /**
     * @return The amount of live or health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * @param health The amount of live or health.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @return Whether its health is larger than 0.
     */
    public boolean isAlive() {
        return health > 0;
    }
}
