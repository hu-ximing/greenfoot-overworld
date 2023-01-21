import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Fireball here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Fireball extends Actor {
    private final UncomplicatedAnimationHandler me;
    private final static int SPEED = 5;
    private int transparency = 255;

    /**
     * Constructor for objects of class Fireball.
     *
     * @param rotation Degrees to turn. 0 is right, 180 is left.
     * @param size     Size of the fireball.
     */
    public Fireball(int rotation, int size) {
        setRotation(rotation);
        size = 39 + size;

        me = new UncomplicatedAnimationHandler(this, 5);
        me.setImgArr(0, 4, "fireball/Fire", ".png");
        me.scale(size, size / 39 * 20);

        Greenfoot.playSound("Fire.fire.wav");
    }

    /**
     * Act - do whatever the Fireball wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        movement();
        me.animate();
        detectHit();
        fade(2);
    }

    /**
     * Move by speed.
     */
    public void movement() {
        move(SPEED);
    }

    /**
     * Detect targets to hit.
     * Enemy: decrease its life by 1 every moment they are in contact.
     * Log and Leaves: remove.
     */
    public void detectHit() {
        LivingThings target = (LivingThings) getOneIntersectingObject(Enemy.class);
        Block block = (Block) getOneIntersectingObject(Block.class);
        if (target != null) {
            target.modifyHealth(-1);
        }
        if (block != null) {
            if (block.getOrganic())
                block.destroy();
        }
    }

    /**
     * Become increasingly transparent.
     *
     * @param fadingSpeed The amount of transparency that is going to decrease every act.
     */
    public void fade(int fadingSpeed) {
        getImage().setTransparency(transparency);
        transparency -= fadingSpeed;
        if (transparency <= 0) {
            getWorld().removeObject(this);
        }
    }
}
