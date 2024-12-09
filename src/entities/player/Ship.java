package entity;

import java.awt.Color;
import java.io.File;
import java.util.Set;
import Enemy.PiercingBullet;
import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;
import inventory_develop.Bomb;
import Enemy.PiercingBulletPool;
import Sound_Operator.SoundManager; // Sound Operator
import Enemy.PlayerGrowth; // Import PlayerGrowth class
import inventory_develop.NumberOfBullet; // Import NumberOfBullet class
import inventory_develop.ItemBarrierAndHeart; // Import ShipStatus class
import inventory_develop.ShipStatus; // Tracks the ship's status and attributes.

public class Ship extends Entity {
	private Cooldown shootingCooldown; // Minimum time between shots.
	private Cooldown destructionCooldown; // Time spent inactive between hits
	private PlayerGrowth growth;  // Tracks and manages player stats
	private ShipStatus shipStatus;  // Holds ship configuration data.
	private ItemBarrierAndHeart item; // Handles barrier and health item functionality.
	private static SoundManager sm; // Plays sound effects.
	private NumberOfBullet numberOfBullet; // Manages bullet generation.

	/**
	 *  Initializes the ship with default properties and stats.
	 *
	 * @param positionX positionX Initial X position.
	 * @param positionY positionY Initial Y position.
	 */
	//Edit by Enemy
	public Ship(final int positionX, final int positionY, final Color color) {
		super(positionX, positionY - 50, 13 * 2, 8 * 2, color); // Set position, size, and color.
		this.spriteType = SpriteType.Ship;

		// Initialize player growth and load ship status.
		this.growth = new PlayerGrowth();  
		this.shipStatus = new ShipStatus();
		shipStatus.loadStatus();

		// Set cooldowns based on player stats.
		this.shootingCooldown = Core.getCooldown(growth.getShootingDelay());
		this.destructionCooldown = Core.getCooldown(1000);
		this.numberOfBullet = new NumberOfBullet();
	}

	/**
	 * Moves the ship speed uni ts right, or until the right screen border is
	 * reached.
	 */
	public final void moveRight() {
		this.positionX += growth.getMoveSpeed(); 
	} 


	/**
	 * Moves the ship speed units left, or until the left screen border is
	 * reached.
	 */
	public final void moveLeft() {
		this.positionX -= growth.getMoveSpeed(); // Use PlayerGrowth for movement speed
	}

	/**
	 * Shoots a bullet upwards.
	 *
	 * @param bullets
	 *            List of bullets on screen, to add the new bullet.
	 * @return Checks if the bullet was shot correctly.
	 *
	 * You can set Number of enemies the bullet can pierce at here.
	 */
	public final boolean shoot(final Set<PiercingBullet> bullets) {

		if (this.shootingCooldown.checkFinished()) {

			this.shootingCooldown.reset(); // Reset cooldown after shooting

			sm = SoundManager.getInstance(); // Sound Operator, Apply a Shooting sound
			sm.playES("My_Gun_Shot");

			// Use NumberOfBullet to generate bullets
			Set<PiercingBullet> newBullets = numberOfBullet.addBullet(
					positionX + this.width / 2,
					positionY,
					growth.getBulletSpeed(), 
					Bomb.getCanShoot()
			);

			Bomb.setCanShoot(false); // Prevent shooting bombs immediately.
			bullets.addAll(newBullets);

			return true;
		}
		return false;
	}


	/**
	 * Updates status of the ship.
	 */
	public final void update() {
		if (!this.destructionCooldown.checkFinished())
			this.spriteType = SpriteType.ShipDestroyed;
		else
			this.spriteType = SpriteType.Ship;
	}

	/**
	 * Switches the ship to its destroyed state.
	 */
	public final void destroy() {
		this.destructionCooldown.reset();
		sm = SoundManager.getInstance();
		sm.playES("ally_airship_damage");
	}

	/**
	 * Checks if the ship is destroyed.
	 *
	 * @return True if the ship is currently destroyed.
	 */
	public final boolean isDestroyed() {
		return !this.destructionCooldown.checkFinished();
	}
	/**
	 * Methods to increase stats (using PlayerGrowth)
	 */

	// Increases health
	public void increaseHealth(int increment) {
		growth.increaseHealth(increment);
	}

	//  Increases movement speed
	public void increaseMoveSpeed() {
		growth.increaseMoveSpeed(shipStatus.getSpeedIn());
	}

	// Increases bullet speed
	public void increaseBulletSpeed() {
		growth.increaseBulletSpeed(shipStatus.getBulletSpeedIn());
	}

	//  Decreases shooting delay
	public void decreaseShootingDelay() {
		growth.decreaseShootingDelay(shipStatus.getSuootingInIn());
		System.out.println(growth.getShootingDelay());
		this.shootingCooldown = Core.getCooldown(growth.getShootingDelay()); // Apply new shooting delay
	}

	/**
	 * Getter for the ship's speed.
	 *
	 * @return Speed of the ship.
	 */
	public final double getSpeed() {
		return growth.getMoveSpeed();
	}
	
	/**
	 * Calculates and returns the bullet speed in Pixels per frame.
	 *
	 * @return bullet speed (Pixels per frame).
	 */
	public final int getBulletSpeed() {
		int speed = growth.getBulletSpeed();
		return (speed >= 0) ? speed : -speed;
	}

	public PlayerGrowth getPlayerGrowth() {
		return growth;
	}	// Team Inventory(Item)


}