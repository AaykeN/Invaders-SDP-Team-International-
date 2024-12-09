package engine;

import clove.ScoreManager;

/**
 * Implements an object that stores the state of the game between levels.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class GameState {

	private int level; //Current game level
	public int score; //Current score
	public int livesRemaining; //Lives currently remaining
	public int livestwoRemaining; //Lives remaining for Player 2
	private int bulletsShot; //Bullets shot until now.
	public int shipsDestroyed; //Ships destroyed until now
	private int playTime; //Total time to play.
	private int coin; //Current coin
	private int gem; //Current gem
	private int hitCount; //Current hitCount
	public ScoreManager scoreManager;
	private int coinItemsCollected; //Current coinItemsCollected

	/**
	 * Constructor.
	 *
	 * @param level Current game level
	 * @param score Current score
	 * @param livesRemaining Lives currently remaining
  	 * @param livestwoRemaining Lives currently remaining for Player 2
	 * @param bulletsShot Bullets shot until now.
	 * @param shipsDestroyed Ships destroyed until now
	 * @param playTime Total time to play
	 * @param coin Current coin
	 * @param gem Current gem
	 * @param hitCount Current hitCount.
	 */
	public GameState(final int level, final int score,
					 final int livesRemaining, final int livestwoRemaining, final int bulletsShot,
					 final int shipsDestroyed, final int playTime, final int coin, final int gem, final int hitCount, final int coinItemsCollected) {
		this.level = level;
		this.score = score;
		this.livesRemaining = livesRemaining;
		this.livestwoRemaining = livestwoRemaining;
		this.bulletsShot = bulletsShot;
		this.shipsDestroyed = shipsDestroyed;
		this.playTime = playTime;
		this.coin = coin; 
		this.gem = gem; 
		this.hitCount = hitCount; 
		this.scoreManager = new ScoreManager(level, score);
		this.coinItemsCollected = coinItemsCollected; 
	}

	public int shipsDestroyed() {
		return shipsDestroyed;
	}

	/**
	 * Create Constructor for using in "engine.Score"
	 * @param bulletsShot Bullets shot until now.
	 * @param shipsDestroyed Ships destroyed until now
	 * @param level Current game level
	 */
	public GameState(int bulletsShot, int shipsDestroyed, int level) { 
		this.bulletsShot = bulletsShot;
		this.shipsDestroyed = shipsDestroyed;
		this.level = level;
	}

	public GameState(int livesRemaining) { 
		this.livesRemaining = livesRemaining;
	}

	/**
	 * @return the level
	 */
	public final int getLevel() {
		return level;
	}

	/**
	 * @return the score
	 */
	public final int getScore() {
		return score;
	}

	/**
	 * @return the livesRemaining
	 */
	public final int getLivesRemaining() {
		return livesRemaining;
	}
	/**
	 * @return the livestwoRemaining for Player 2
	 */
	public final int getLivesTwoRemaining() {
		return livestwoRemaining;
	}
	
	/**
	 * @return the bulletsShot
	 */
	public final int getBulletsShot() {
		return bulletsShot;
	}

	/**
	 * @return the shipsDestroyed
	 */
	public final int getShipsDestroyed() {
		return shipsDestroyed;
	}

	/**
	 * @return the playTime
	 */
	public final int getTime() { return playTime; }

	/**
	 * @return the coin
	 */
	public final int getCoin() {
		return coin;
	}

	/**
	 * @return the gem
	 */
	public final int getGem() {
		return gem;
	}

	/**
	 * @return the hitCount
	 */
	public final int getHitCount() { return hitCount; }

	/**
	 * @return the coinItemsCollected
	 */
	public int getCoinItemsCollected() { return coinItemsCollected; }
}
