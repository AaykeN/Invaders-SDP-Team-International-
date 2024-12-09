package entities.base;

import java.awt.Color;

import core.Cooldown;
import core.Core;
import core.DrawManager.SpriteType;

/**
* Implements an obstacle, to be destroyed by the player.
*/
public class Obstacle extends Entity {
    
    private Cooldown movementCooldown; //Controls movement speed
    private Cooldown explosionCooldown; //Controls explosion duration
    private boolean isDestroyed; //Checks if the obstacle has been destroyed by a bullet
    
    /**
    * Constructor, establishes the obstacle's properties.
    * 
    * @param positionX Initial position of the obstacle in the X axis.
    * @param positionY Initial position of the obstacle in the Y axis.
    */
    public Obstacle(final int positionX, final int positionY) {
        super(positionX, positionY, 14 * 2, 7 * 2, new Color(161, 142, 108)); // Use different size/color than EnemyShip
        this.spriteType = SpriteType.Obstacle; // Ensure it's an obstacle shape sprite type
        this.isDestroyed = false;
        this.movementCooldown = Core.getCooldown(500); // Adjust the cooldown for obstacle movement
    }
    
    /**
    * Moves the obstacle a certain distance.
    * 
    * @param distanceX Distance to move in the X axis.
    * @param distanceY Distance to move in the Y axis.
    */
    public final void move(final int distanceX, final int distanceY) {
        this.positionX += distanceX;
        this.positionY += distanceY;
    }
    
    /**
    * Updates the obstacle's movement or other attributes.
    */
    public final void update(int level) {
        if (!this.isDestroyed) {
            int speed = 2 + (level / 2);  // Set speed based on the level
            this.positionY += speed; // Moves the obstacle down continuously
        } else {
            // If destroyed, check if the explosion animation should finish
            if (this.explosionCooldown != null && this.explosionCooldown.checkFinished()) {
                this.spriteType = null; // Mark this obstacle to be removed
            }
        }
    }
    
    /**
    * Destroys the obstacle, causing it to explode.
    */
    public final void destroy() {
        if (!this.isDestroyed) {
            this.isDestroyed = true;
            this.spriteType = SpriteType.Explosion;  
            this.explosionCooldown = Core.getCooldown(500);  // Explosion lasts for 500ms
            this.explosionCooldown.reset();
        }
    }
    
    /**
    * 
    * @return True if the obstacle has been destroyed.
    */
    public final boolean isDestroyed() {
        return this.isDestroyed;
    }
    
    /**
    * 
    * @return True if the obstacle can be removed.
    */
    public final boolean shouldBeRemoved() {
        return this.spriteType == null;  // Remove when spriteType is null after explosion
    }
}
