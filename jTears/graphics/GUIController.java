package scripts.jTears.graphics;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import org.tribot.api.General;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import scripts.jTears.graphics.GUI;

public class GUIController implements Initializable {

	private GUI gui;

	// Inits
	@FXML private Button startScriptButton;
	
	@FXML private Hyperlink MyGithub;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//Put anything that you need the GUI to do when it is loaded.
	}
	
	// All events go here
	@FXML public void goToGithub() {
		 Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(new URI("https://github.com/Jerminater/jTears-Beta-"));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	@FXML public void startScript() {
		General.println("Enjoy Jerm's TOG!");
		gui.close();
	}
	
	public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public GUI getGUI() {
        return this.gui;
    }
}
