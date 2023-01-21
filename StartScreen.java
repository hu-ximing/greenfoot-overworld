import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StartScreen here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StartScreen extends World {

    /**
     * Constructor for objects of class StartScreen.
     */
    public StartScreen() {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(900, 600, 1, false);
        prepare();
    }

    /**
     * Add backgrounds and buttons.
     */
    public void prepare() {
        // Add 2 backgrounds
        Background bg1 = new Background(true);
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        addObject(bg1, x, y);

        Background bg2 = new Background(true);
        bg2.setImage(bg1.getImage());
        x += getWidth();
        addObject(bg2, x, y);

        // Add start button
        StartButton startButton = new StartButton("Start Game");
        x = getWidth() / 2;
        y = getHeight() / 2;
        addObject(startButton, x, y);
    }
}
