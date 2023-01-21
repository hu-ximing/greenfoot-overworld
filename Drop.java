import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Drop here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Drop extends GravityBound {
    String thing;

    /**
     * Constructor for objects of class Drop.
     *
     * @param thing The name of item it represents.
     */
    public Drop(String thing) {
        this.thing = thing;
    }

    /**
     * Act - do whatever the Drop wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        gravityFall();
    }

    /**
     * @return The string of the item it represents.
     */
    public String getThing() {
        return thing;
    }
}
