import greenfoot.*;

/**
 * Write a description of class Slime here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Slime extends Enemy {
    private final UncomplicatedAnimationHandler alive;
    private final UncomplicatedAnimationHandler dead;
    private final String colour;
    private final static String GREEN = "slimeGreen";
    private final static String PINK = "slimePink";
    private int counter = 0;
    private final static int JUMP_DURATION = 200;
    private int speed = 0;
    private boolean itemDropped = false;
    private final static int DAMAGE = 5;

    /**
     * Constructor for objects of class Slime.
     */
    public Slime() {
        super(Greenfoot.getRandomNumber(20) + 10, DAMAGE);

        if (Greenfoot.getRandomNumber(100) < 75) {
            colour = GREEN;
        } else {
            colour = PINK;
        }

        alive = new UncomplicatedAnimationHandler(this, 10);
        alive.setImgArr(0, 3, "slime/" + colour + "/alive_down_", ".png");

        dead = new UncomplicatedAnimationHandler(this, 10);
        dead.setImgArr(0, 3, "slime/" + colour + "/dead_down_", ".png");
    }

    /**
     * Act - do whatever the Slime wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        super.act();
        if (isAlive()) {
            jump();
            alive.animate();
            setLocation(getX() + speed, getY());
            bounce();
            attack();
            moveUpGround();
            resetSpeed();
        } else {
            die();
        }
    }

    /**
     * Jump regularly when it is on the ground.
     */
    public void jump() {
        if (counter++ >= JUMP_DURATION && isLanded()) {
            counter = 0;
            setVSpeed(-12);
            decideSpeed();
        }
    }

    /**
     * Set speed to positive if the player is on the right,
     * negative if player is on the left.
     */
    public void decideSpeed() {
        int dstX = getWorldOfType(MyWorld.class).getPlayer().getX();
        if (getX() > dstX) {
            speed = -2;
        } else {
            speed = 2;
        }
    }

    /**
     * Set the speed to 0 when hit the ground.
     */
    public void resetSpeed() {
        if (isLanded()) {
            speed = 0;
        }
    }

    /**
     * Drop a random item.
     * Shuriken: 1/2
     * Bomb: 3/8
     * Dynamite: 1/8
     */
    public void dropItem() {
        Drop drop;
        if (Greenfoot.getRandomNumber(100) < 40) {
            drop = new Drop("shuriken");
            drop.setImage("Shuriken.png");
        } else {
            if (Greenfoot.getRandomNumber(100) < 75) {
                drop = new Drop("bomb");
                drop.setImage("Bomb.png");
            } else {
                drop = new Drop("dynamite");
                drop.setImage("Dynamite.png");
            }
        }
        getWorld().addObject(drop, getX(), getY());
    }

    /**
     * Generate a tree at its location.
     */
    public void dropTree() {
        int treeSize = Greenfoot.getRandomNumber(5) + 1;
        int len = Block.getSideLen();
        int x = getX() / len * len;
        int y = getY() / len * len;
        getWorldOfType(MyWorld.class).generateTree(treeSize, x, y);
    }

    /**
     * Decrease player's life.
     */
    public void attack() {
        for (Itachi itachi : getIntersectingObjects(Itachi.class)) {
            itachi.modifyHealth(-DAMAGE);
        }
    }

    /**
     * Move up if it is stuck in the ground.
     */
    public void moveUpGround() {
        if (isLanded()) setLocation(getX(), getY() - 1);
    }

    /**
     * If it is not alive, play the dead animation and remove it.
     * Drop a random item if it is a green slime.
     * Heal the player and become a tree if it is a pink slime.
     */
    public void die() {
        if (!isAlive()) {
            if (!itemDropped) {
                Greenfoot.playSound("NPC_Killed_1.wav");
                getWorld().removeObject(getHealthLabel());
                if (colour.equals(GREEN))
                    dropItem();
                itemDropped = true;
            }
            dead.animate();
        }
        if (dead.isLastIndex()) {
            if (colour.equals(PINK)) {
                Itachi player = getWorldOfType(MyWorld.class).getPlayer();
                player.modifyHealth(50);
                dropTree();
            }
            getWorld().removeObject(this);
        }
    }
}
