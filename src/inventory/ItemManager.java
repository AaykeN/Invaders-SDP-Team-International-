package Enemy;

import engine.Core;
import entity.EnemyShip;
import entity.Ship;
import inventory_develop.Bomb;
import inventory_develop.FeverTimeItem;
import screen.GameScreen;
import engine.DrawManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import inventory_develop.ItemBarrierAndHeart;
import inventory_develop.NumberOfBullet;
import inventory_develop.SpeedItem;
import CtrlS.CurrencyManager;
import Sound_Operator.SoundManager; // Sound Operator

/**
 * Manages all item-related logic, including item spawning, drawing, 
 * interaction with the player, and recycling items.
 */
public class ItemManager {

    public Set<Item> items; //Active items in the game.
    private int screenHeight; //Screen height for bounds checking.
    private DrawManager drawManager;
    private GameScreen gameScreen; // Reference to the main game screen.
    protected Logger logger = Core.getLogger(); //Logger for debug and info messages.
    private Set<Item> recyclableItems = new HashSet<>();
    private Set<EnemyShip> enemyShips; //Reference to current enemy ships in the game.
    private ItemBarrierAndHeart Item2;
    private NumberOfBullet numberOfBullet;
    private SpeedItem speedItem;
    private Ship ship;
    private PlayerGrowth growth;
    private FeverTimeItem feverTimeItem;
    private CurrencyManager currencyManager;
    // Sound Operator
    private static SoundManager sm;

    public ItemManager(int screenHeight, DrawManager drawManager, GameScreen gameScreen) {
        this.items = new HashSet<>();
        this.screenHeight = screenHeight;
        this.drawManager = drawManager;
        this.gameScreen = gameScreen;
        this.ship = gameScreen.getShip();
        this.growth = ship.getPlayerGrowth();
        this.Item2 = gameScreen.getItem();
        this.feverTimeItem = gameScreen.getFeverTimeItem();
        this.numberOfBullet = new NumberOfBullet();
        this.speedItem = gameScreen.getSpeedItem();
        this.enemyShips = new HashSet<>();
    }

    public void cleanItems() {
        Set<Item> recyclable = new HashSet<>();
        for (Item item : this.items) {
            item.update(); // Update item position or state.
            if (item.getPositionY() > screenHeight) {
                recyclable.add(item);
            }
        }
        this.items.removeAll(recyclable);
        ItemPool.recycle(recyclable);
    }

    public void initialize() {
        this.items = new HashSet<>();
    }

    /**
     * Renders all active items on the screen.
     */
    public void drawItems() {
        for (Item item : this.items) {
            drawManager.drawEntity(item, item.getPositionX(), item.getPositionY());
        }
    }

    public void dropItem(EnemyShip enemyShip, double probability, int enemyship_type) {
        if(Math.random() < probability) {
            Item item = ItemPool.getItem(enemyShip.getPositionX(), enemyShip.getPositionY(), 3, enemyship_type);
            this.items.add(item);
        }
    }

    public void setEnemyShips(Set<EnemyShip> enemyShips) {
        this.enemyShips = enemyShips;
    }

    public void OperateItem(Item item) {
        if(item!= null) {

            DrawManager.SpriteType whatItem = item.getSpriteType();

            switch (whatItem) {     // Operates according to the SpriteType of the item.
                case ItemBomb:
                    Bomb.setIsbomb(true);
                    Bomb.setCanShoot(true);
                    sm = SoundManager.getInstance();
                    sm.playES("get_item");
                    break;
                case ItemBarrier:
                    Item2.activatebarrier();
                    sm = SoundManager.getInstance();
                    sm.playES("get_item");
                    break;
                case ItemHeart:
                    Item2.activeheart(gameScreen);
                    sm = SoundManager.getInstance();
                    sm.playES("get_item");
                    break;
                case ItemFeverTime:
                    feverTimeItem.activate();
                    break;
                case ItemPierce:
                    numberOfBullet.pierceup();
                    ship.increaseBulletSpeed();
                    sm = SoundManager.getInstance();
                    sm.playES("get_item");
                    break;
                case ItemCoin:
                    this.logger.info("You get coin!");
                    break;
                case ItemSpeedUp:
                    speedItem.activate(true, enemyShips);
                    break;
                case ItemSpeedSlow:
                    speedItem.activate(false, enemyShips);
                    break;
            }

            addItemRecycle(item);
        }
    }

    public void addItemRecycle(Item item) {
        recyclableItems.add(item);
        String itemLog = item.getSpriteType().toString().toLowerCase().substring(4);
        if (itemLog.equals("coin")){
            sm = SoundManager.getInstance();
            sm.playES("item_coin");
        }

        if (!itemLog.equals("coin")) {
            this.logger.info("get " + itemLog + " item"); // Log item pickup
        }
    }

    public void removeAllReItems(){
        this.items.removeAll(recyclableItems);
        ItemPool.recycle(recyclableItems);
    }

}
