package Enemy;
import engine.Core;
import java.io.IOException;

public class PlayerGrowth {
    //Player's base stats
    private int health;          //Health
    private static double moveSpeed = 1.5; //Movement speed
    private static int bulletSpeed = -4; //Bullet speed
    private static int shootingDelay = 750; //Shooting delay

    //Constructor to set initial values
    public PlayerGrowth() {//  Base shooting delay is 750ms
        try {
            moveSpeed = Core.getUpgradeManager().getMovementSpeed();
            shootingDelay = Core.getUpgradeManager().getAttackSpeed();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void increaseHealth(int increment) {
        this.health += increment; //Increases health
    }

    public void increaseMoveSpeed(double increment) {
        this.moveSpeed += increment; //Increases movement speed
    }

    public void increaseBulletSpeed(int increment) {
        this.bulletSpeed -= increment; //Increases bullet speed (makes bullets faster)
    }

    public void decreaseShootingDelay(int decrement) {
        this.shootingDelay -= decrement; //  Decrease delay for faster shooting
        if (this.shootingDelay < 100) {
            this.shootingDelay = 100; // Minimum shooting delay is 100ms
        }
    }
    
    public void ResetBulletSpeed(){
        bulletSpeed = -4; //reset bullet speed
    }
    
    public int getHealth() {
        return this.health; //Returns current health
    }

    
    public double getMoveSpeed() {
        return this.moveSpeed; // Returns current movement speed
    }

    public int getBulletSpeed() {
        return this.bulletSpeed; //Returns current bullet speed
    }
    
    public int getShootingDelay() {
        return this.shootingDelay; //Returns current shooting delay
    }

    // Prints player stats (for debugging)
    public void printStats() {
        System.out.println("Health: " + this.health);
        System.out.println("MoveSpeed: " + this.moveSpeed);
        System.out.println("BulletSpeed: " + this.bulletSpeed);
        System.out.println("ShootingDelay: " + this.shootingDelay + "ms");
    }
}
