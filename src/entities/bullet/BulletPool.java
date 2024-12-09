package entities.bullet;

import java.util.HashSet;
import java.util.Set;

/**
 * Implements a pool of recyclable bullets.
 * 
 */
public final class BulletPool {

	/** Stores bullets available for reuse. */
	private static Set<Bullet> pool = new HashSet<Bullet>();

    /**
     * Private constructor to prevent instantiation.
     */
	private BulletPool() {

	}

	/**
	 * Returns a bullet from the pool if one is available, a new one if there
	 * isn't.
	 * 
	 * @param positionX
	 *            Requested position of the bullet in the X axis.
	 * @param positionY
	 *            Requested position of the bullet in the Y axis.
	 * @param speed
	 *            Requested speed of the bullet, positive or negative depending
	 *            on direction - positive is down.
	 * @return Requested bullet.
	 */

	public static Bullet getBullet(final int positionX,
			final int positionY, final int speed) {
		Bullet bullet;
		if (!pool.isEmpty()) {
			bullet = pool.iterator().next(); // Retrieve a bullet from the pool.
			pool.remove(bullet);
			bullet.setPositionX(positionX - bullet.getWidth() / 2);
			bullet.setPositionY(positionY);
			bullet.setSpeed(speed);
			bullet.setSprite(); // Reset its appearance for reuse.
		} else {
			bullet = new Bullet(positionX, positionY, speed); // Create a new bullet.
			bullet.setPositionX(positionX - bullet.getWidth() / 2);
		}
		return bullet;
	}

	/**
	 * Adds one or more bullets to the list of available ones.
	 * 
	 * @param bullet
	 *            Bullets to recycle.
	 */
	public static void recycle(final Set<Bullet> bullet) {
		pool.addAll(bullet);
	}
}
