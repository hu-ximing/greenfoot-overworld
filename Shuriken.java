import greenfoot.Greenfoot;

/**
 * Write a description of class Shuriken here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Shuriken extends GravityBound {
    private final static int FLYING_SPEED = 12;
    private final static int ROTATION_SPEED = 2;
    private final static int DAMAGE = 7;
    private boolean hitSomething = false;

    /**
     * Constructor for objects of class
     *
     * @param rotation Degrees to turn. 180 is left, 0 is right.
     */
    public Shuriken(int rotation) {
        setImage("Shuriken.png");
        setRotation(rotation);
        Greenfoot.playSound("Random.bow.wav");
    }

    /**
     * Act - do whatever the Shuriken wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        super.act();
        movement();
        rotate();
        gravityFall();
        detectHit();
        removeMe();
    }

    /**
     * Fly like a shuriken.
     * But it will still fly after touching the ground, as intended.
     */
    public void movement() {
        move(FLYING_SPEED);
    }

    /**
     * Rotate like a shuriken.
     */
    public void rotate() {
        getImage().rotate(ROTATION_SPEED);
    }

    /**
     * Decrease Enemy's live upon contact.
     */
    public void detectHit() {
        LivingThings target = (LivingThings) getOneIntersectingObject(Enemy.class);
        if (target != null) {
            target.modifyHealth(-DAMAGE);
            hitSomething = true;
        }
    }

    /**
     * Remove it if it is going to be removed.
     */
    public void removeMe() {
        if (hitSomething || detectEdge() || isTouchingWall()) {
            getWorld().removeObject(this);
        }
    }
}
