package scripts.jTears.graphics;

public class FXMLString {
	
	public static String get = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
			"\r\n" + 
			"<?import javafx.scene.control.Button?>\r\n" + 
			"<?import javafx.scene.control.Hyperlink?>\r\n" + 
			"<?import javafx.scene.layout.AnchorPane?>\r\n" + 
			"<?import javafx.scene.layout.BorderPane?>\r\n" + 
			"\r\n" + 
			"<BorderPane maxHeight=\"-Infinity\" maxWidth=\"-Infinity\" minHeight=\"-Infinity\" minWidth=\"-Infinity\" prefHeight=\"400.0\" prefWidth=\"600.0\" styleClass=\"background\" stylesheets=\"@Styles.css\" xmlns=\"http://javafx.com/javafx/10.0.1\" xmlns:fx=\"http://javafx.com/fxml/1\" fx:controller=\"scripts.jTears.graphics.GUIController\">\r\n" + 
			"   <center>\r\n" + 
			"      <AnchorPane prefHeight=\"200.0\" prefWidth=\"200.0\" styleClass=\"pic\" stylesheets=\"@Styles.css\" BorderPane.alignment=\"CENTER\">\r\n" + 
			"         <children>\r\n" + 
			"            <Button fx:id=\"startScriptButton\" layoutX=\"450.0\" layoutY=\"195.0\" mnemonicParsing=\"false\" onAction=\"#startScript\" stylesheets=\"@Styles.css\" text=\"Start Script\" underline=\"true\" />\r\n" + 
			"            <Hyperlink fx:id=\"MyGithub\" layoutX=\"381.0\" layoutY=\"225.0\" onAction=\"#goToGithub\" text=\"Check out my Github while I collect your tears!\" />\r\n" + 
			"         </children></AnchorPane>\r\n" + 
			"   </center>\r\n" + 
			"</BorderPane>\r\n" + 
			"";
}