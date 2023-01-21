import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Block here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Block extends Actor {
    private final static int SIDE_LEN = 50;
    private final boolean breakable;
    private final boolean organic;

    /**
     * Constructor for objects of class Block.
     *
     * @param image     Image of the block.
     * @param breakable Whether it can be destroyed by an explosive.
     * @param organic   Whether it can be destroyed by fire.
     */
    public Block(GreenfootImage image, boolean breakable, boolean organic) {
        image.scale(SIDE_LEN, SIDE_LEN);
        setImage(image);
        this.breakable = breakable;
        this.organic = organic;
    }

    /**
     * Act - do whatever the Block wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {

    }

    /**
     * Remove the block from the world.
     */
    public void destroy() {
        int soundIndex = Greenfoot.getRandomNumber(4) + 1;
        Greenfoot.playSound("Grass_dig" + soundIndex + ".wav");
        getWorld().removeObject(this);
    }

    /**
     * @return Whether it can be destroyed by an explosive.
     */
    public boolean getBreakable() {
        return breakable;
    }

    /**
     * @return Whether it can be destroyed by fire.
     */
    public boolean getOrganic() {
        return organic;
    }

    /**
     * @return The side length of a block.
     */
    public static int getSideLen() {
        return SIDE_LEN;
    }

}
