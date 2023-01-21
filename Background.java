import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Background here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Background extends Actor {
    private final boolean autoScroll;

    /**
     * Constructor for objects of class Background.
     * Background will be a random image chosen from the image array.
     *
     * @param autoScroll Whether it automatically scrolls to the left.
     */
    public Background(boolean autoScroll) {
        this.autoScroll = autoScroll;
        GreenfootImage[] bgImg = new GreenfootImage[5];
        for (int i = 0; i < bgImg.length; ++i) {
            bgImg[i] = new GreenfootImage("background/Forest_background_" + (i + 1) + ".png");
        }
        int index = Greenfoot.getRandomNumber(bgImg.length);
        setImage(bgImg[index]);
    }

    /**
     * Act - do whatever the Background wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        if (autoScroll) {
            move(-2);
        }
        concatenateBg();
    }

    /**
     * Move itself to the other end of the screen if it passes
     * the left or right boarder and cannot be seen anymore.
     */
    public void concatenateBg() {
        int w = getWorld().getWidth();
        if (getX() < -w / 2) {
            // move to the right of the screen
            setLocation(w + w / 2 - 2, getY());
        }
        if (getX() > w + w / 2) {
            // move to the left of the screen
            setLocation(-w / 2 + 2, getY());
        }
    }
}
