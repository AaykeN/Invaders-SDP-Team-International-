package entity;

import java.awt.Color;

import engine.DrawManager;
import engine.DrawManager.SpriteType;
import inventory_develop.Bomb;

/**
 * Implements a bullet that moves vertically up or down.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Bullet extends Entity {

	private int speed; //Bullet speed, positive for downward movement 
	protected boolean checkCount; // Tracks if the bullet is counted after hitting a target.
	protected int fire_id;  // Unique identifier for the bullet.
	
	/**
	 * Constructor, establishes the bullet's properties.
	 * 
	 * @param positionX Initial position of the bullet in the X axis.
	 * @param positionY Initial position of the bullet in the Y axis.          
	 * @param speed Speed of the bullet; positive is downward.      
	 */

	public Bullet(final int positionX, final int positionY, final int speed) {
		super(positionX, positionY, 3 * 2, 5 * 2, Color.WHITE);
		this.checkCount = true;
		this.speed = speed;
		setSprite();
	}


	/**
	 * Sets correct sprite for the bullet, based on speed.
	 */
	public final void setSprite() {

		if (speed < 0) {
			if(Bomb.getIsBomb() && Bomb.getCanShoot())
				this.spriteType = SpriteType.ItemBomb; 
			else
				this.spriteType = SpriteType.Bullet; // Standard player bullet
		}
		else
			this.spriteType = SpriteType.EnemyBullet; // Enemy bullet
	}

	/**
	 * Updates the bullet's position.
	 */
	public final void update() {
		this.positionY += this.speed;
	}

	/**
	 * Set the speed of the bullet
	 * 
	 * @param speed New speed of the bullet.
	 */
	public final void setSpeed(final int speed) {
		this.speed = speed;
	}

	/**
	 * Get the speed of the bullet
	 * 
	 * @return Current bullet speed
	 */
	public final int getSpeed() {
		return this.speed;
	}

	/**
	 * Checks if the bullet is counted after hitting a target.
	 *
	 * @return checkCount of the bullet.
	 */
	public final boolean isCheckCount() { return this.checkCount; }

	/**
	 * Sets the bullet's counted status
	 *
	 *  @param checkCount New counted status
	 * 	          
	 */
	public final void setCheckCount(final boolean checkCount) { this.checkCount = checkCount; }

	/**
	 * Getter for the fire_id of the bullet
	 *
	 * @return Get fire_id of the bullet.
	 */
	public final int getFire_id() { return this.fire_id; }
	/**
	 * Setter for the fire_id of the bullet.
	 *
	 *  @param id  New fire_id of the bullet.       
	 */
	public final void setFire_id(final int id) { this.fire_id = id; }
}
