import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GravityBound here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GravityBound extends Actor {
    private double vSpeed = 0;

    /**
     * Constructor for objects of class GravityBound.
     */
    public GravityBound() {

    }

    /**
     * Act - do whatever the GravityBound wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {

    }

    /**
     * Accelerate to the ground determined by the gravity of the world.
     * However, it can also go up if vSpeed is modified.
     * It will stop when it is landed.
     */
    public void gravityFall() {
        double gravity = MyWorld.getGravity();
        vSpeed += gravity;
        int x = getX();
        int y = getY() + (int) vSpeed;
        if (isLanded()) {
            vSpeed = 0;
        } else {
            setLocation(x, y);
        }
    }

    /**
     * @return Whether the object is standing on a block.
     */
    public boolean isLanded() {
        int dy = getImage().getHeight() / 2 + (int) vSpeed;
        return getOneObjectAtOffset(0, dy, Block.class) != null;
    }

    /**
     * @return Whether it is touching the side of a block.
     */
    public boolean isTouchingWall() {
        int dx = getImage().getWidth() / 2;
        return getOneObjectAtOffset(dx, 0, Block.class) != null &&
                getOneObjectAtOffset(-dx, 0, Block.class) != null;
    }

    /**
     * Same as gravityFall, but will bounce off the ground.
     */
    public void bounce() {
        double gravity = MyWorld.getGravity();
        vSpeed += gravity;
        int x = getX();
        int y = getY() + (int) vSpeed;
        if (isLanded()) {
            if (vSpeed > 0) {
                vSpeed /= -2;
            }
        } else {
            setLocation(x, y);
        }
    }

    /**
     * Determine if it drops too far from the world.
     * If it is a living thing, set its health to 0.
     *
     * @return Return true if it is going to be deleted or died.
     */
    public boolean detectEdge() {
        if (getY() > getWorld().getHeight() * 2) {
            if (LivingThings.class.isAssignableFrom(this.getClass())) {
                ((LivingThings) this).modifyHealth(-20);
            }
            return true;
        }
        return false;
    }

    /**
     * @return vertical speed of object
     */
    public double getVSpeed() {
        return vSpeed;
    }

    /**
     * @param vSpeed vertical speed of object
     */
    public void setVSpeed(double vSpeed) {
        this.vSpeed = vSpeed;
    }

}
