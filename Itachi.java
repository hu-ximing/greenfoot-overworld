import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * Write a description of class Itachi here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Itachi extends LivingThings {
    /* animation */
    private final UncomplicatedAnimationHandler runningRight;
    private final UncomplicatedAnimationHandler runningLeft;
    private final UncomplicatedAnimationHandler standingRight;
    private final UncomplicatedAnimationHandler standingLeft;
    private final static int INTERVAL = 10;
    private final static int RUNNING_LEFT = 1;
    private final static int RUNNING_RIGHT = 2;
    private final static int STANDING_LEFT = 3;
    private final static int STANDING_RIGHT = 4;
    private int status = STANDING_RIGHT;

    /* movement */
    private final static int SPEED = 4;
    private final static int JUMP_STRENGTH = 12;
    private final static int MAX_JUMP_TIMES = 2;
    private boolean jumpPressing = false;
    private int jumpCount = 0;

    /* attack */
    // fireball
    private final static int MAX_ENERGY = 20;
    private int energyNum = MAX_ENERGY;
    private final static int ENERGY_RESTORE_TIME = 20;
    private int energyCounter = 0;
    private boolean fireballPressing = false;
    // shuriken
    private final static int MAX_SHURIKEN = 20;
    private int shurikenNum = MAX_SHURIKEN;
    private final static int SHURIKEN_INTERVAL = 15;
    EnhancedKeyPress shurikenKeyPress = new EnhancedKeyPress(SHURIKEN_INTERVAL);
    // bomb
    private final static int MAX_BOMB = 20;
    private int bombNum = 3;
    private final static int BOMB_INTERVAL = 30;
    EnhancedKeyPress bombKeyPress = new EnhancedKeyPress(BOMB_INTERVAL);
    // dynamite
    private final static int MAX_DYNAMITE = 20;
    private int dynamiteNum = 1;
    private final static int DYNAMITE_INTERVAL = 60;
    EnhancedKeyPress dynamiteKeyPress = new EnhancedKeyPress(DYNAMITE_INTERVAL);

    /* others */
    private final static int MAX_HEALTH = 100;

    /* key arrangement */
    private final static String KEY_LEFT = "a";
    private final static String KEY_RIGHT = "d";
    private final static String KEY_JUMP = "k";
    private final static String KEY_FIREBALL = "i";
    private final static String KEY_SHURIKEN = "u";
    // key combination
    private final static String KEY_SECOND = "s";
    private final static String KEY_BOMB = "u";
    private final static String KEY_DYNAMITE = "i";

    /**
     * Constructor for objects of class Itachi.
     */
    public Itachi() {
        super(MAX_HEALTH);
        setInvulnerableTime(20);

        // Image array for moving
        runningRight = new UncomplicatedAnimationHandler(this, INTERVAL);
        runningRight.setImgArr(1, 6, "itachi/running-right-itachi-", ".png");

        runningLeft = new UncomplicatedAnimationHandler(runningRight);
        runningLeft.mirrorHorizontally();

        // Image array for still
        standingRight = new UncomplicatedAnimationHandler(this, INTERVAL);
        standingRight.setImgArr(1, 7, "itachi/standing-still-right-itachi-", ".png");

        standingLeft = new UncomplicatedAnimationHandler(standingRight);
        standingLeft.mirrorHorizontally();

        // Set initial image
        standingRight.showImage();
    }

    /**
     * Act - do whatever the Itachi wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        super.act();
        checkKeyPress();
        refreshJumpState();
        gravityFall();
        restoreEnergy();
        shurikenKeyPress.coolDown();
        bombKeyPress.coolDown();
        dynamiteKeyPress.coolDown();
        detectEdge();
        showAllData();
        pickupDrop();
        detectHurt();
        die();
    }

    /**
     * Check key press events.
     * For example, move to left or right, jump, shoot projectiles.
     */
    public void checkKeyPress() {
        boolean left = Greenfoot.isKeyDown(KEY_LEFT);
        boolean right = Greenfoot.isKeyDown(KEY_RIGHT);

        // move
        if (left ^ right) {
            if (left) {
                setLocation(getX() - SPEED, getY());
                status = RUNNING_LEFT;
                runningLeft.animate();
            } else {
                setLocation(getX() + SPEED, getY());
                status = RUNNING_RIGHT;
                runningRight.animate();
            }
        }
        // still
        else {
            if (isFacingLeft()) {
                standingLeft.animate();
                status = STANDING_LEFT;
            } else {
                standingRight.animate();
                status = STANDING_RIGHT;
            }
        }

        // jump
        if (Greenfoot.isKeyDown(KEY_JUMP)) {
            if (!jumpPressing)
                jump();
            jumpPressing = true;
        } else {
            jumpPressing = false;
        }

        // bomb, dynamite, fireball, shuriken
        if (Greenfoot.isKeyDown(KEY_SECOND)) {
            // shoot bomb
            if (Greenfoot.isKeyDown(KEY_BOMB)) {
                shootBomb();
            }
            // shoot dynamite
            if (Greenfoot.isKeyDown(KEY_DYNAMITE)) {
                shootDynamite();
            }
        } else {
            // shoot fireball
            if (Greenfoot.isKeyDown(KEY_FIREBALL)) {
                if (!fireballPressing)
                    shootFireball();
                fireballPressing = true;
            } else {
                fireballPressing = false;
            }
            // shoot shuriken
            if (Greenfoot.isKeyDown(KEY_SHURIKEN)) {
                shootShuriken();
            }
        }

    }

    /**
     * Set jumpCount to 0 if it is landed.
     */
    public void refreshJumpState() {
        if (isLanded()) jumpCount = 0;
    }

    /**
     * Jump if you can jump
     */
    public void jump() {
        jumpCount++;
        if (jumpCount <= MAX_JUMP_TIMES) {
            setVSpeed(-JUMP_STRENGTH);
            int x = getX();
            int y = getY() + (int) getVSpeed();
            setLocation(x, y);
        }
    }

    /**
     * Shoot a fireball towards the direction facing.
     */
    public void shootFireball() {
        int rotation;
        if (isFacingLeft()) {
            rotation = Greenfoot.getRandomNumber(30) + 165;
        } else {
            rotation = Greenfoot.getRandomNumber(30) - 15;
        }
        int x = getX();
        int y = getY() - getImage().getHeight() / 3;
        getWorld().addObject(new Fireball(rotation, energyNum * 10), x, y);
        energyNum = 0;
        showEnergy();
    }

    /**
     * Automatically increase energy.
     */
    public void restoreEnergy() {
        if (++energyCounter >= ENERGY_RESTORE_TIME) {
            energyCounter = 0;
            if (energyNum < MAX_ENERGY) {
                energyNum++;
            }
            showEnergy();
        }
    }

    /**
     * Shoot a shuriken towards the direction facing.
     */
    public void shootShuriken() {
        if (!shurikenKeyPress.isReady() || shurikenNum <= 0) {
            return;
        }
        int rotation;
        if (isFacingLeft()) {
            rotation = 180;
        } else {
            rotation = 0;
        }
        int x = getX();
        int y = getY() - getImage().getHeight() / 6;
        getWorld().addObject(new Shuriken(rotation), x, y);
        shurikenNum--;
        shurikenKeyPress.reset();
        showShuriken();
    }

    /**
     * Shoot a bomb towards the direction facing.
     * The bomb will have the same starting vertical speed of yours.
     */
    public void shootBomb() {
        if (!bombKeyPress.isReady() || bombNum <= 0) {
            return;
        }
        int x = getX();
        int y = getY() - getImage().getHeight() / 6;
        Bomb bomb = new Bomb();
        if (isFacingLeft()) {
            bomb.setSpeed(-SPEED);
        } else {
            bomb.setSpeed(SPEED);
        }
        bomb.setVSpeed(getVSpeed());
        getWorld().addObject(bomb, x, y);
        bombNum--;
        bombKeyPress.reset();
        showBomb();
    }

    /**
     * Shoot a dynamite towards the direction facing.
     * The dynamite will have the same starting vertical speed of yours.
     */
    public void shootDynamite() {
        if (!dynamiteKeyPress.isReady() || dynamiteNum <= 0) {
            return;
        }
        int x = getX();
        int y = getY() - getImage().getHeight() / 6;
        Dynamite dynamite = new Dynamite();
        if (isFacingLeft()) {
            dynamite.setSpeed(-SPEED);
        } else {
            dynamite.setSpeed(SPEED);
        }
        dynamite.setVSpeed(getVSpeed());
        getWorld().addObject(dynamite, x, y);
        dynamiteNum--;
        dynamiteKeyPress.reset();
        showDynamite();
    }

    /**
     * Show a data bar.
     *
     * @param name     The name describing the variable to be displayed on the screen.
     * @param value    The current value it has.
     * @param maxValue The maximum value it can have.
     * @param x        X coordinate of the bar.
     * @param y        Y coordinate of the bar.
     */
    public void showDataBar(String name, int value, int maxValue, int x, int y) {
        StringBuilder text = new StringBuilder(name + ": ");
        for (int i = 0; i < 20; ++i) {
            if (i < (double) value / maxValue * 20) {
                text.append("=");
            } else {
                text.append(". ");
            }
        }
        getWorld().showText(text.toString(), x, y);
    }

    /**
     * Show energy bar.
     * Amount of energy you have.
     */
    public void showEnergy() {
        showDataBar("Energy", energyNum, MAX_ENERGY, 240, 50);
    }

    /**
     * Show shuriken bar.
     * Number of shuriken you have.
     */
    public void showShuriken() {
        showDataBar("Shuriken", shurikenNum, MAX_SHURIKEN, 600, 50);
    }

    /**
     * Show bomb bar.
     * Number of bombs you have.
     */
    public void showBomb() {
        showDataBar("Bomb", bombNum, MAX_BOMB, 240, 100);
    }

    /**
     * Show dynamite bar.
     * Number of dynamites you have.
     */
    public void showDynamite() {
        showDataBar("Dynamite", dynamiteNum, MAX_DYNAMITE, 600, 100);
    }

    /**
     * Show health bar.
     * Amount of health you have.
     */
    public void showHealth() {
        int x = getWorld().getWidth() / 2;
        int y = getWorld().getHeight() / 10 * 9;
        showDataBar("Health", getHealth(), MAX_HEALTH, x, y);
    }

    /**
     * Show all the data bars you have.
     */
    public void showAllData() {
        showEnergy();
        showShuriken();
        showBomb();
        showDynamite();
        showHealth();
    }

    /**
     * Collect drop items by intersecting with it.
     */
    public void pickupDrop() {
        Drop drop = (Drop) getOneIntersectingObject(Drop.class);
        if (drop != null) {
            String thing = drop.getThing();
            switch (thing) {
                case "shuriken":
                    shurikenNum += 10;
                    if (shurikenNum > MAX_SHURIKEN) shurikenNum = MAX_SHURIKEN;
                    break;
                case "bomb":
                    if (bombNum < MAX_BOMB) bombNum++;
                    break;
                case "dynamite":
                    if (dynamiteNum < MAX_DYNAMITE) dynamiteNum++;
                    break;
            }
            getWorld().removeObject(drop);
        }
    }

    /**
     * When intersecting with enemies, decrease health by the largest damage that can apply.
     */
    public void detectHurt() {
        int maxDamage = 0;
        for (Enemy enemy : getIntersectingObjects(Enemy.class)) {
            maxDamage = Math.max(maxDamage, enemy.getDamage());
        }
        modifyHealth(-maxDamage);
    }

    /**
     * @return Whether the character is facing left.
     */
    public boolean isFacingLeft() {
        return status == RUNNING_LEFT || status == STANDING_LEFT;
    }

    /**
     * Die when its health value is less than 0.
     * It will also play a death sound, show a game over text and stop the game.
     */
    public void die() {
        if (!isAlive()) {
            Greenfoot.playSound("NPC_Killed_1.wav");
            int x = getWorld().getWidth() / 2;
            int y = getWorld().getHeight() / 2;
            getWorld().showText("Game over.", x, y);
            Greenfoot.stop();
        }
    }
}
