package engine;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import CtrlS.EncryptionSupport;
import engine.DrawManager.SpriteType;

/**
 * Manages files used in the application.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public final class FileManager {

	/** Singleton instance of the class. */
	private static FileManager instance;
	/** Application logger. */
	private static Logger logger;
	/** Max number of high scores. */
	private static final int MAX_SCORES = 7;

	/**
	 * private constructor.
	 */
	private FileManager() {
		logger = Core.getLogger();
	}

	/**
	 * Returns shared instance of FileManager.
	 * 
	 * @return Shared instance of FileManager.
	 */
	protected static FileManager getInstance() {
		if (instance == null)
			instance = new FileManager();
		return instance;
	}

	/**
	 * Loads sprites from disk.
	 * 
	 * @param spriteMap
	 *            Mapping of sprite type and empty boolean matrix that will
	 *            contain the image.
	 * @throws IOException
	 *             In case of loading problems.
	 */
	public void loadSprite(final Map<SpriteType, boolean[][]> spriteMap)
			throws IOException {
		InputStream inputStream = null;

		try {
			inputStream = DrawManager.class.getClassLoader()
					.getResourceAsStream("graphics");
			char c;

			// Sprite loading.
			for (Map.Entry<SpriteType, boolean[][]> sprite : spriteMap
					.entrySet()) {
				for (int i = 0; i < sprite.getValue().length; i++)
					for (int j = 0; j < sprite.getValue()[i].length; j++) {
						do
							c = (char) inputStream.read();
						while (c != '0' && c != '1');

						if (c == '1')
							sprite.getValue()[i][j] = true;
						else
							sprite.getValue()[i][j] = false;
					}
				logger.fine("Sprite " + sprite.getKey() + " loaded.");
			}
			if (inputStream != null)
				inputStream.close();
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
	}

	/**
	 * Loads a font of a given size.
	 * 
	 * @param size
	 *            Point size of the font.
	 * @return New font.
	 * @throws IOException
	 *             In case of loading problems.
	 * @throws FontFormatException
	 *             In case of incorrect font format.
	 */
	public Font loadFont(final float size) throws IOException,
			FontFormatException {
		InputStream inputStream = null;
		Font font;

		try {
			inputStream = FileManager.class.getClassLoader()
					.getResourceAsStream("font.ttf");
			if (inputStream != null) {
				font = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(size);
			} else {
				// Set as default font, if inputStream is null
				System.out.println("Custom font not found, applying default font.");
				font = new Font("Serif", Font.PLAIN, (int) size); // Set as "Serif"
			}
		} finally {
			if (inputStream != null)
				inputStream.close();
		}

		return font;
	}

	/**
	 * Returns the application default scores if there is no user high scores
	 * file.
	 * 
	 * @return Default high scores.
	 * @throws IOException
	 *             In case of loading problems.
	 */
	private List<Score> loadDefaultHighScores() throws IOException {
		List<Score> highScores = new ArrayList<Score>();
		InputStream inputStream = null;
		BufferedReader reader = null;

		try {
			inputStream = FileManager.class.getClassLoader()
					.getResourceAsStream("scores");
			reader = new BufferedReader(new InputStreamReader(inputStream));

			Score highScore = null;
			String name = reader.readLine();
			String score = reader.readLine();

			while ((name != null) && (score != null)) {
				highScore = new Score(name, Integer.parseInt(score));
				highScores.add(highScore);
				name = reader.readLine();
				score = reader.readLine();
			}
		} finally {
			if (inputStream != null)
				inputStream.close();
		}

		return highScores;
	}

	/**
	 * Loads high scores from file, and returns a sorted list of pairs score -
	 * value.
	 * 
	 * @return Sorted list of scores - players.
	 * @throws IOException
	 *             In case of loading problems.
	 */
	public List<Score> loadHighScores() throws IOException {

		List<Score> highScores = new ArrayList<Score>();
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;

		try {
			String jarPath = FileManager.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			jarPath = URLDecoder.decode(jarPath, "UTF-8");

			String scoresPath = new File(jarPath).getParent();
			scoresPath += File.separator;
			scoresPath += "scores";

			File scoresFile = new File(scoresPath);
			inputStream = new FileInputStream(scoresFile);
			bufferedReader = new BufferedReader(new InputStreamReader(
					inputStream, Charset.forName("UTF-8")));

			logger.info("Loading user high scores.");

			Score highScore = null;
			String name = bufferedReader.readLine();
			String score = bufferedReader.readLine();

			while ((name != null) && (score != null)) {
				highScore = new Score(name, Integer.parseInt(score));
				highScores.add(highScore);
				name = bufferedReader.readLine();
				score = bufferedReader.readLine();
			}

		} catch (FileNotFoundException e) {
			// loads default if there's no user scores.
			logger.info("Loading default high scores.");
			highScores = loadDefaultHighScores();
		} finally {
			if (bufferedReader != null)
				bufferedReader.close();
		}

		Collections.sort(highScores);
		return highScores;
	}

	/**
	 * Saves user high scores to disk.
	 * 
	 * @param highScores
	 *            High scores to save.
	 * @throws IOException
	 *             In case of loading problems.
	 */
	public void saveHighScores(final List<Score> highScores)
			throws IOException {
		OutputStream outputStream = null;
		BufferedWriter bufferedWriter = null;

		try {
			String jarPath = FileManager.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			jarPath = URLDecoder.decode(jarPath, "UTF-8");

			String scoresPath = new File(jarPath).getParent();
			scoresPath += File.separator;
			scoresPath += "scores";

			File scoresFile = new File(scoresPath);

			if (!scoresFile.exists())
				scoresFile.createNewFile();

			outputStream = new FileOutputStream(scoresFile);
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(
					outputStream, Charset.forName("UTF-8")));

			logger.info("Saving user high scores.");

			// Saves 7 or less scores.
			int savedCount = 0;
			for (Score score : highScores) {
				if (savedCount >= MAX_SCORES)
					break;
				bufferedWriter.write(score.getName());
				bufferedWriter.newLine();
				bufferedWriter.write(Integer.toString(score.getScore()));
				bufferedWriter.newLine();
				savedCount++;
			}

		} finally {
			if (bufferedWriter != null)
				bufferedWriter.close();
		}
	}

	/**
	 * Saves user currency to disk.
	 *
	 * @param currency
	 *            amount of user currency to save.
	 * @throws IOException
	 *             In case of saving problems.
	 */

	public void saveCurrency(final int currency) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		BufferedWriter bufferedWriter = null;
		BufferedReader bufferedReader = null;

		try {
			String jarPath = FileManager.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			jarPath = URLDecoder.decode(jarPath, "UTF-8");

			//Choose File root
			String currencyPath = new File(jarPath).getParent();
			currencyPath += File.separator;
			currencyPath += "currency";

			File currencyFile = new File(currencyPath);
			//create File If there is no currencyFile
			if (!currencyFile.exists())
				currencyFile.createNewFile();

			List<String> lines = new ArrayList<>();
			inputStream = new FileInputStream(currencyFile);
			outputStream = new FileOutputStream(currencyFile);
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(
					outputStream, Charset.forName("UTF-8")));
			bufferedReader = new BufferedReader(new InputStreamReader(
					inputStream, Charset.forName("UTF-8")));

			// Read the file's current content
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				lines.add(line);
			}

			// Modify the first line (currency)
			if (!lines.isEmpty()) {
				lines.set(0, EncryptionSupport.encrypt(Integer.toString(currency)));
			} else {
				// If the file was empty, add the new currency as the first line and the new gem as the second line
				lines.add(EncryptionSupport.encrypt(Integer.toString(currency)));
				lines.add(EncryptionSupport.encrypt("0"));
			}

			// Write back the modified content
			for (String l : lines) {
				bufferedWriter.write(l);
				bufferedWriter.newLine();
			}

			logger.info("Saving user's currency.");

		} finally {
			if (bufferedReader != null)
				bufferedReader.close();

			if (bufferedWriter != null)
				bufferedWriter.close();
		}
	}

	/**
	 * Loads user currency from file, and returns current currency.
	 *
	 * @return amount of current currency.
	 * @throws IOException
	 *             In case of loading problems.
	 */
	public int loadCurrency() throws IOException {
		int currency;
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;

		try {
			String jarPath = FileManager.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			jarPath = URLDecoder.decode(jarPath, "UTF-8");

			String currencyPath = new File(jarPath).getParent();
			currencyPath += File.separator;
			currencyPath += "currency";

			File currencyFile = new File(currencyPath);
			inputStream = new FileInputStream(currencyFile);
			bufferedReader = new BufferedReader(new InputStreamReader(
					inputStream, Charset.forName("UTF-8")));

			logger.info("Loading user's currency.");

			String amount = bufferedReader.readLine();
			currency = Integer.parseInt(EncryptionSupport.decrypt(amount));
		} catch (FileNotFoundException e) {
			// loads default if there's no user currency.
			logger.info("Loading default currency.");
			currency = loadDefaultCurrency();
		} finally {
			if (bufferedReader != null)
				bufferedReader.close();
		}

		return currency;
	}

	/**
	 * Returns the application default currency if there is no user currency files.
	 *
	 * @return Default currency.
	 * @throws IOException
	 *             In case of loading problems.
	 */
	private int loadDefaultCurrency() throws IOException {
		int currency;
		InputStream inputStream = null;
		BufferedReader reader = null;

		try {
			inputStream = FileManager.class.getClassLoader()
					.getResourceAsStream("currency");
			reader = new BufferedReader(new InputStreamReader(inputStream));

			String amount = reader.readLine();
			currency = Integer.parseInt(amount);
		} finally {
			if (inputStream != null)
				inputStream.close();
		}

		return currency;
	}

	/**
	 * Saves user gem to disk.
	 *
	 * @param gem
	 *            amount of user gem to save.
	 * @throws IOException
	 * 			   In case of saving problems.
	 */
	// Team-Ctrl-S(Currency)
	public void saveGem(final int gem) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		BufferedWriter bufferedWriter = null;
		BufferedReader bufferedReader = null;

		try {
			String jarPath = FileManager.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			jarPath = URLDecoder.decode(jarPath, "UTF-8");

			//Choose File root
			String gemPath = new File(jarPath).getParent();
			gemPath += File.separator;
			gemPath += "currency";

			File gemFile = new File(gemPath);
			//create File If there is no gemFile
			if (!gemFile.exists())
				gemFile.createNewFile();

			List<String> lines = new ArrayList<>();
			inputStream = new FileInputStream(gemFile);
			outputStream = new FileOutputStream(gemFile);
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(
					outputStream, Charset.forName("UTF-8")));
			bufferedReader = new BufferedReader(new InputStreamReader(
					inputStream, Charset.forName("UTF-8")));

			// Read the file's current content
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				lines.add(line);
			}

			// Modify the second line (gem)
			if (!lines.isEmpty()) {
				lines.set(1, EncryptionSupport.encrypt(Integer.toString(gem)));
			} else {
				// If the file was empty, add the new currency as the first line and the new gem as the second line
				lines.add(EncryptionSupport.encrypt("0"));
				lines.add(EncryptionSupport.encrypt(Integer.toString(gem)));
			}

			// Write back the modified content
			for (String l : lines) {
				bufferedWriter.write(l);
				bufferedWriter.newLine();
			}

			logger.info("Saving user's gem.");

		} finally {
			if (bufferedReader != null)
				bufferedReader.close();

			if (bufferedWriter != null)
				bufferedWriter.close();
		}
	}

	/**
	 * Loads user gem from file, and returns current gem.
	 *
	 * @return amount of current gem.
	 * @throws IOException
	 * 			   In case of loading problems.
	 */
	// Team-Ctrl-S(Currency)
	public int loadGem() throws IOException {
		int gem;
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;

		try {
			String jarPath = FileManager.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			jarPath = URLDecoder.decode(jarPath, "UTF-8");

			String gemPath = new File(jarPath).getParent();
			gemPath += File.separator;
			gemPath += "currency";

			File gemFile = new File(gemPath);
			inputStream = new FileInputStream(gemFile);
			bufferedReader = new BufferedReader(new InputStreamReader(
					inputStream, Charset.forName("UTF-8")));

			logger.info("Loading user's gem.");

			bufferedReader.readLine(); // Ignore first(currency) line
			String amount = bufferedReader.readLine();
			gem = Integer.parseInt(EncryptionSupport.decrypt(amount));
		} catch (FileNotFoundException e) {
			// loads default if there's no user gem.
			logger.info("Loading default gem.");
			gem = loadDefaultGem();
		} finally {
			if (bufferedReader != null)
				bufferedReader.close();
		}

		return gem;
	}

	/**
	 * Returns the application default gem if there is no user gem files.
	 *
	 * @return Default gem.
	 * @throws IOException
	 * 			   In case of loading problems.
	 */
	// Team-Ctrl-S(Currency)
	private int loadDefaultGem() throws IOException {
		int gem;
		InputStream inputStream = null;
		BufferedReader reader = null;

		try {
			inputStream = FileManager.class.getClassLoader()
					.getResourceAsStream("currency");
			reader = new BufferedReader(new InputStreamReader(inputStream));

			reader.readLine(); //Ignore first(currency) line
			String amount = reader.readLine();
			gem = Integer.parseInt(amount);
		} finally {
			if (inputStream != null)
				inputStream.close();
		}

		return gem;
	}
}