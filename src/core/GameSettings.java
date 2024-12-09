package core;

/**
 * Implements an object that stores a single game's difficulty settings.
 * 
 */
public class GameSettings {

	private int formationWidth; //Width of the level's enemy formation.
	private int formationHeight; //Height of the level's enemy formation
	private int baseSpeed; //Speed of the enemies, function of the remaining number
	private int shootingFrecuency; //Frequency of enemy shootings, +/- 30%.
	private int wavesNumber; //Number of enemy ships waves during the level

	/**
	 * Constructor.
	 * 
	 * @param formationWidth Width of the level's enemy formation.
	 * @param formationHeight Height of the level's enemy formation.
	 * @param baseSpeed Speed of the enemies
	 * @param shootingFrecuency Frecuency of enemy shootings, +/- 30%
	 * @param wavesNumber Number of waves in the level (Added by the Level Design team)
	 * 				
	 */
	public GameSettings(final int formationWidth, final int formationHeight,
			final int baseSpeed, final int shootingFrecuency, final int wavesNumber) {
		this.formationWidth = formationWidth;
		this.formationHeight = formationHeight;
		this.baseSpeed = baseSpeed;
		this.shootingFrecuency = shootingFrecuency;
		this.wavesNumber = wavesNumber;
	}

	/**
	 * @return the formationWidth
	 */
	public final int getFormationWidth() {
		return formationWidth;
	}

	/**
	 * @return the formationHeight
	 */
	public final int getFormationHeight() {
		return formationHeight;
	}

	/**
	 * @return the baseSpeed
	 */
	public final int getBaseSpeed() {
		return baseSpeed;
	}

	/**
	 * @return the shootingFrecuency
	 */
	public final int getShootingFrecuency() {
		return shootingFrecuency;
	}

	/**
	 * Added by the Level Design team
	 * @return the wavesNumber
	 */
	public final int getWavesNumber() {
		return wavesNumber;
	}

}
