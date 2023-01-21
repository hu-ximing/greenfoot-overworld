import greenfoot.*;

import java.util.Arrays;

/**
 * Write a description of class UncomplicatedAnimationHandler here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class UncomplicatedAnimationHandler {
    private GreenfootImage[] imgArr;
    private final Actor actor;
    private int counter = 0;
    private int index = 0;
    private final int interval;

    /**
     * Constructor for objects of class UncomplicatedAnimationHandler
     *
     * @param actor    The actor to associate with.
     * @param interval The number of acts to change to the next image index.
     */
    public UncomplicatedAnimationHandler(Actor actor, int interval) {
        this.actor = actor;
        this.interval = interval;
    }

    /**
     * Copy constructor for objects of class UncomplicatedAnimationHandler
     *
     * @param another The object to copy.
     */
    public UncomplicatedAnimationHandler(UncomplicatedAnimationHandler another) {
        this.actor = another.actor;
        this.interval = another.interval;
        this.imgArr = new GreenfootImage[another.imgArr.length];
        Arrays.setAll(this.imgArr, i -> new GreenfootImage(another.imgArr[i]));
    }

    /**
     * Use inside the act method.
     */
    public void animate() {
        increaseCounter();
    }

    /**
     * Increase the counter.
     * When it reaches the interval, show the current image and increase image index.
     */
    private void increaseCounter() {
        counter++;
        if (counter >= interval) {
            counter = 0;
            showImage();
            increaseIndex();
        }
    }

    /**
     * Increase image index.
     * If the index reaches the array length, set it to 0.
     */
    private void increaseIndex() {
        index++;
        if (index >= imgArr.length) {
            index = 0;
        }
    }

    /**
     * Set the actor's image to the current image.
     */
    public void showImage() {
        actor.setImage(imgArr[index]);
    }

    /**
     * It can be used to determine if the animation has been played once.
     *
     * @return Whether the image array index is the last one.
     */
    public boolean isLastIndex() {
        return index == imgArr.length - 1;
    }

    /**
     * Set the counter and index to 0.
     */
    public void reset() {
        counter = 0;
        index = 0;
    }

    /**
     * Set the image array.
     *
     * @param start The start number of the image.
     * @param end   The end number of the image.
     * @param part1 The part of filename before the number.
     * @param part2 The part of filename after the number.
     */
    public void setImgArr(int start, int end, String part1, String part2) {
        imgArr = new GreenfootImage[end - start + 1];
        for (int i = 0; i < imgArr.length; ++i) {
            GreenfootImage img = new GreenfootImage(part1 + (start + i) + part2);
            imgArr[i] = img;
        }
    }

    /**
     * Set the image array.
     *
     * @param imgArr The image array to be set.
     */
    public void setImgArr(GreenfootImage[] imgArr) {
        this.imgArr = new GreenfootImage[imgArr.length];
        Arrays.setAll(this.imgArr, i -> new GreenfootImage(imgArr[i]));
    }

    /**
     * Scale every image in the image array.
     *
     * @param width  Target width.
     * @param length Target length.
     */
    public void scale(int width, int length) {
        for (GreenfootImage img : imgArr) {
            img.scale(width, length);
        }
    }

    /**
     * Mirror every image in the image array horizontally.
     */
    public void mirrorHorizontally() {
        for (GreenfootImage img : imgArr) {
            img.mirrorHorizontally();
        }
    }
}
