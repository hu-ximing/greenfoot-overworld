import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StartButton here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StartButton extends Actor {
    public final static int SIZE = 30;
    public final static Color COLOR_FOREGROUND = Color.WHITE;
    public final static Color COLOR_BACKGROUND = null;

    /**
     * Constructor for objects of class startButton.
     *
     * @param text The text to show on the button.
     */
    public StartButton(String text) {
        GreenfootImage img;
        img = new GreenfootImage(text, SIZE, COLOR_FOREGROUND, COLOR_BACKGROUND);
        setImage(img);
    }

    /**
     * Act - do whatever the StartButton wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        checkClick();
    }

    public void checkClick() {
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new MyWorld());
        }
    }
}
