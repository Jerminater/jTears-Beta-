package scripts.jTears.antiban;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.tribot.util.Util;

/**
 * Allows certain script patterns to persist based on account username.
 * 
 * Currently only supports dropping pattern. 
 * Allows for easy implementation of other patterns in the future.
 * 
 * @author Nate
 *
 */
public class JTearsAntibanProfile {
	
	// Save/Load
	private String username;
	private File directory = new File(Util.getWorkingDirectory() + "\\jTearsAntibanProfile");
	private Properties prop = new Properties();
	
	// Patterns
	//private Inventory.DROPPING_PATTERN DROPPING_PATTERN;
	
	public JTearsAntibanProfile(String username) {
		this.username = username;
	}
	
	public void loadProfile() {
		
		loadFromFile();
		
		setPatterns();
		
	}
	
	private void loadFromFile() {
		// Load patterns from file to this object, or create a new pattern file
		try {
			prop.load(new FileInputStream(directory.getAbsolutePath() + "/" + this.username));
			
			//this.DROPPING_PATTERN = Inventory.DROPPING_PATTERN.valueOf(prop.getProperty("dropping"));
		}
		catch (IOException e) {
			generateNewProfile();
		}
		catch (NullPointerException e) {
			updateCurrentProfile();
		}
	}
	
	private void generateNewProfile() {
		
		//this.DROPPING_PATTERN = org.tribot.api2007.Inventory.DROPPING_PATTERN.values()[General.random(0, 2)];
		
		saveProfile();
		
	}
	
	private void saveProfile() {
		
		if (!directory.exists())
			directory.mkdirs();
		
		prop.clear();
		//prop.setProperty("NAME", SAVED_PROP.toString());
		
		try {
			prop.store(new FileOutputStream(directory + "/" + this.username), "Unique JTears Antiban Profile");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void updateCurrentProfile() {
		// Add in logic when more patterns are added in
	}
	
	private void setPatterns() {
		// Set patterns
		//Inventory.setDroppingPattern(this.DROPPING_PATTERN);
	}

}