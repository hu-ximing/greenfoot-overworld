import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HealthLabel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class HealthLabel extends Actor {
    private final LivingThings it;

    /**
     * Constructor for objects of class HealthLabel.
     *
     * @param it The living thing that the label is associated with.
     */
    public HealthLabel(LivingThings it) {
        this.it = it;
    }

    /**
     * Act - do whatever the HealthLabel wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        if (it == null) {
            getWorld().removeObject(this);
        } else {
            followActor();
            showActorHealth();
        }
    }

    /**
     * Set its position to the associated actor's position.
     */
    public void followActor() {
        setLocation(it.getX(), it.getY() - it.getImage().getHeight());
    }

    /**
     * Show the actor's health.
     */
    public void showActorHealth() {
        GreenfootImage img = new GreenfootImage(Integer.toString(it.getHealth()), 20, Color.BLACK, null);
        setImage(img);
    }
}
