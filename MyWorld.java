import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 *
 * @author Ximing Hu
 * @version 2022-01-13
 */
public class MyWorld extends World {
    private final static double GRAVITY = 0.4;
    private Itachi player;
    private final static int LEN = Block.getSideLen();

    /**
     * Constructor for objects of class MyWorld.
     */
    public MyWorld() {
        // Create a new world with 900x600 cells with a cell size of 1x1 pixels.
        super(900, 600, 1, false);
        prepare();
        GreenfootSound bgm = new GreenfootSound
                ("sounds/Scott Lloyd Shelly - Terraria Soundtrack - 01 Overworld Day.mp3");
        bgm.playLoop();
    }

    /**
     * Act - do whatever the MyWorld wants to do.
     */
    public void act() {
        balanceEnemyNum();
    }

    /**
     * There is a chance to add enemies to the world.
     * There is a better chance when there are few enemies.
     */
    public void balanceEnemyNum() {
        int enemyNum = getObjects(Enemy.class).size();
        if (enemyNum < 1) {
            if (Greenfoot.getRandomNumber(200) < 1) {
                addSlime();
            }
        } else if (enemyNum < 3) {
            if (Greenfoot.getRandomNumber(1400) < 1) {
                addSlime();
            }
        }
    }

    /**
     * Add a slime at the top of the world,
     * at a random x position around the middle of the world.
     */
    public void addSlime() {
        Slime slime = new Slime();
        int x = Greenfoot.getRandomNumber(getWidth() / 2) + getWidth() / 4;
        int y = -100;
        addObject(slime, x, y);
    }

    /**
     * Adds backgrounds, characters, blocks
     */
    private void prepare() {
        // Add 2 backgrounds
        Background bg1 = new Background(false);
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        addObject(bg1, x, y);

        Background bg2 = new Background(false);
        bg2.setImage(bg1.getImage());
        x += getWidth();
        addObject(bg2, x, y);

        // Add itachi
        player = new Itachi();
        x = getWidth() / 2;
        y = getHeight() / 2;
        addObject(player, x, y);
        player.showEnergy();
        player.showShuriken();
        player.showBomb();
        player.showDynamite();
        player.showHealth();

        // Add slime
        addSlime();

        // fill the ground
        generateTerrain();

        // grow tree
        int treeSize = Greenfoot.getRandomNumber(4) + 2;
        generateTree(treeSize, 250, 350);
    }

    /**
     * Generate a flat ground of blocks.
     * Block allocation (from lowest to highest):
     * 1. Bedrock
     * 2. Stone
     * 3. Dirt
     * 4. Grass
     */
    private void generateTerrain() {
        int x, y;
        int row = 0;
        for (y = getHeight(); y >= getHeight() / 3 * 2; y -= LEN) {
            for (x = 0; x <= getWidth(); x += LEN) {
                Block b;
                if (row == 0) b = new Bedrock();
                else if (row <= 2) b = new Stone();
                else if (row <= 3) b = new Dirt();
                else b = new Grass();
                addObject(b, x, y);
            }
            row++;
        }
    }

    /**
     * Generate a tree.
     *
     * @param size Number of logs the tree has.
     * @param x    X-axis of the tree.
     * @param y    Y-axis of the tree.
     */
    public void generateTree(int size, int x, int y) {
        // log
        for (int i = 0; i < size; ++i) {
            addObject(new Log(), x, y - i * LEN);
        }

        // first row of leaves
        for (int i = 1; i <= size; ++i) {
            addObject(new Leaves(), x + i * LEN, y - (size - 1) * LEN);
            addObject(new Leaves(), x - i * LEN, y - (size - 1) * LEN);
        }

        // other rows of leaves
        int startX = x - (size - 1) * LEN;
        int blockNum = size * 2 - 1;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < blockNum; ++j) {
                addObject(new Leaves(), startX + j * LEN, y - size * LEN - i * LEN);
            }
            startX += LEN;
            blockNum -= 2;
        }
    }

    /**
     * @return player (instance of Itachi)
     */
    public Itachi getPlayer() {
        return player;
    }

    /**
     * @return Gravity of the world.
     */
    public static double getGravity() {
        return GRAVITY;
    }
}
