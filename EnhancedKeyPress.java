import greenfoot.*;

/**
 * Write a description of class EnhancedKeyPress here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class EnhancedKeyPress {
    private final int interval;
    private int counter = 0;
    private boolean ready = true;


    /**
     * Constructor for objects of class EnhancedKeyPress
     */
    public EnhancedKeyPress(int interval) {
        this.interval = interval;
    }

    /**
     * Use this method inside act() method.
     * It cools down until the key is ready to be pressed again.
     */
    public void coolDown() {
        if (counter < interval) {
            counter++;
        } else {
            ready = true;
        }
    }

    /**
     * Use this method after a key has been pressed.
     * The key needs to be cooled down again.
     */
    public void reset() {
        ready = false;
        counter = 0;
    }

    /**
     * @return Whether it is ready to process a key press.
     */
    public boolean isReady() {
        return ready;
    }
}
