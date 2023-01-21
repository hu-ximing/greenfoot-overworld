import greenfoot.Greenfoot;

/**
 * Write a description of class Explosives here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Explosives extends GravityBound {
    private final UncomplicatedAnimationHandler exploding;
    private boolean exploded = false;
    private final int radius;
    private final int damage;
    private int timeToExplode;
    private int speed = 0;

    /**
     * Constructor for objects of class Explosives.
     *
     * @param radius        Radius of the explosion and damage.
     * @param damage        Amounts of damage to apply to living things when it explodes.
     * @param timeToExplode Number of acts to wait to explode
     */
    public Explosives(int radius, int damage, int timeToExplode) {
        this.radius = radius;
        this.damage = damage;
        this.timeToExplode = timeToExplode;

        exploding = new UncomplicatedAnimationHandler(this, 3);
        exploding.setImgArr(0, 28, "explosion/Explosion", ".gif");
        exploding.scale(radius, radius);

        Greenfoot.playSound("Fuse.wav");
    }

    /**
     * Act - do whatever the Stone wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        super.act();
        if (exploded) {
            explode();
        } else {
            waitToExplode();
            gravityFall();
            inertiaMove();
            detectEdge();
        }
    }

    /**
     * Wait to explode.
     * When it is time to explode, destroy blocks, hurt living things and play a sound.
     */
    public void waitToExplode() {
        if (--timeToExplode < 0) {
            exploded = true;
            destroyBlocks();
            hurtLivingThings();
            playExplosionSound();
        }
    }

    /**
     * Destroy nearby blocks within radius if the block is breakable.
     */
    public void destroyBlocks() {
        for (Block block : getObjectsInRange(radius, Block.class)) {
            if (block.getBreakable()) {
                block.destroy();
            }
        }
    }

    /**
     * Decrease living things' health within radius.
     */
    public void hurtLivingThings() {
        for (LivingThings i : getObjectsInRange(radius, LivingThings.class)) {
            i.modifyHealth(-damage);
        }
    }

    /**
     * Play a random explosion sound.
     */
    public void playExplosionSound() {
        int soundIndex = Greenfoot.getRandomNumber(4) + 1;
        Greenfoot.playSound("Explosion" + soundIndex + ".wav");
    }

    /**
     * Animate the explosion and remove itself when the animation ends.
     */
    public void explode() {
        exploding.animate();
        if (exploding.isLastIndex()) {
            getWorld().removeObject(this);
        }
    }

    /**
     * Continue to move horizontally unless it is stopped by an enemy or a wall.
     */
    public void inertiaMove() {
        if (isTouchingWall() || isTouching(Enemy.class)) {
            speed = 0;
        } else {
            setRotation(getRotation() + speed * 2);
            setLocation(getX() + speed, getY());
        }
    }

    /**
     * @param speed The horizontal moving speed.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
