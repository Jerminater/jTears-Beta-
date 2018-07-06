package scripts.jTears;

import java.awt.Color;
import java.awt.Graphics;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Login;
import org.tribot.api2007.Login.STATE;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Ending;
import org.tribot.script.interfaces.MessageListening07;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;

import scripts.framework.Task;
import scripts.framework.TaskSet;
import scripts.jTears.actions.Bank;
import scripts.jTears.actions.CollectTears;
import scripts.jTears.actions.EnterTOG;
import scripts.jTears.actions.GoToTears;
import scripts.jTears.data.Vars;
import scripts.jTears.graphics.FXMLString;
import scripts.jTears.graphics.FluffeesPaint;
import scripts.jTears.graphics.GUI;
import scripts.jTears.graphics.PaintInfo;
import scripts.jTears.graphics.PaintInfoThread;
import scripts.jTears.utils.MessageUtility;

@ScriptManifest(authors = { "Jerminater" }, category = "Mini games", 
	name = "JTears", version = 1.00, 
	description = "Completes the Tears of Guthix minigame!", gameMode = 1)

public class JTears extends Script implements MessageListening07, Starting, Ending, Painting, PaintInfo {
	
	// Items
	public static final Integer[] GAMES_NECKLACE = {3867,3865,3863,3861,3859,3857,3855,3853};
	
	private final FluffeesPaint Paint = new FluffeesPaint(this, FluffeesPaint.PaintLocations.BOTTOM_RIGHT_PLAY_SCREEN,
			new Color[]{new Color(255, 251, 255)}, "Tahoma", new Color[]{new Color(255, 218, 185, 127)},
            new Color[]{new Color(139, 119, 101)}, 1, false, 5, 3, 0);
	private URL stylesheet;
	private GUI gui;
	private int index;
	private String skill = lowestSkill();
	private int startXP = xp(index);
	
	private boolean paintThreadStarted;
	
	public void onStart() {
		// Initializing GUI
		println("Opening GUI");
		try {
			stylesheet = new URL("https://raw.githubusercontent.com/Jerminater/jTears-Beta-/master/jTears/graphics/Styles.css");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		gui = new GUI(FXMLString.get, stylesheet);
		gui.show();
		while (gui.isOpen())
			sleep(500);
		
		// init vars
		Vars.get().homeTeleport = true;
		Vars.get().minigameTeleport = true;
		Vars.get().collected = false;
		
		//making sure we are logged in
		while (Login.getLoginState() != STATE.INGAME)
			General.sleep(300, 500);
	}
	
	// object ids: blue = 6665,6661       green = 6666,6662,     none = 6663, 6667,      wall = 6660
	//DynamicClicking.clickRSObject(Objects.find(12, 6666)[0], 1); //General.println(Objects.find(12, 6661));
	@Override
	public void run() {
		// Start paint data tracking thread
		new PaintInfoThread(this).start();
		paintThreadStarted = true;
		// initialize tasks and loop through them
		TaskSet tasks = new TaskSet(new GoToTears(), new CollectTears(), new Bank(), new EnterTOG());
	    while (!Vars.get().endCond) {
	    	sleep(100);
	       Task task = tasks.getValidTask();
	        if (task != null) {
	        	Vars.get().status = task.toString();
	            task.execute();
	        }
	    }	
	}
	
	@Override
	public void onEnd() {
		int xpGained = startXP - xp(index);
		println("Thanks for using Jerm's Tears collector!");
		println("Total Runtime: " + Timing.msToString(Vars.get().runTime));
		println("Tears Collected: " + Vars.get().tears);
		println("Exp gained in " + skill + ": " + xpGained);
		println("Exp gained per tear: " + xpGained/Integer.parseInt(Vars.get().tears));
	}
	
	@Override
    public void onPaint(Graphics g) {
		if (paintThreadStarted)
			Paint.paint(g);
	}
	
	@Override
    public String[] getPaintInfo() {
        return new String[] {"jTears v1.0", "Runtime: " + Timing.msToString(Vars.get().runTime),
        		"Status: " + Vars.get().status, "Tears Collected: " + Vars.get().tears};
    }
	
	private String lowestSkill() {
		Integer[] xp = {Skills.getXP(SKILLS.AGILITY),Skills.getXP(SKILLS.ATTACK),Skills.getXP(SKILLS.CONSTRUCTION),Skills.getXP(SKILLS.COOKING),
				Skills.getXP(SKILLS.CRAFTING),Skills.getXP(SKILLS.DEFENCE),Skills.getXP(SKILLS.FARMING),Skills.getXP(SKILLS.FIREMAKING),
				Skills.getXP(SKILLS.FISHING),Skills.getXP(SKILLS.FLETCHING),Skills.getXP(SKILLS.HERBLORE),Skills.getXP(SKILLS.HITPOINTS),
				Skills.getXP(SKILLS.HUNTER),Skills.getXP(SKILLS.MAGIC),Skills.getXP(SKILLS.MINING),Skills.getXP(SKILLS.PRAYER),
				Skills.getXP(SKILLS.RANGED),Skills.getXP(SKILLS.RUNECRAFTING),Skills.getXP(SKILLS.SLAYER),Skills.getXP(SKILLS.SMITHING),
				Skills.getXP(SKILLS.STRENGTH),Skills.getXP(SKILLS.THIEVING),Skills.getXP(SKILLS.WOODCUTTING)};
		String[] skillArray = {"Agility","Attack","Construction","Cooking","Crafting","Defence","Farming","Firemaking",
				"Fishing","Fletching","Herblore","Hitpoints","Hunter","Magic","Mining","Prayer",
				"Ranged","Runecrafting","Slayer","Smithing","Strength","Thieving","Woodcutting"};
		index = Arrays.asList(xp).indexOf(Collections.min(Arrays.asList(xp)));
		startXP = xp[index];
		return skillArray[index];
	}
	
	private int xp(int ind) {
		Integer[] xp = {Skills.getXP(SKILLS.AGILITY),Skills.getXP(SKILLS.ATTACK),Skills.getXP(SKILLS.CONSTRUCTION),Skills.getXP(SKILLS.COOKING),
				Skills.getXP(SKILLS.CRAFTING),Skills.getXP(SKILLS.DEFENCE),Skills.getXP(SKILLS.FARMING),Skills.getXP(SKILLS.FIREMAKING),
				Skills.getXP(SKILLS.FISHING),Skills.getXP(SKILLS.FLETCHING),Skills.getXP(SKILLS.HERBLORE),Skills.getXP(SKILLS.HITPOINTS),
				Skills.getXP(SKILLS.HUNTER),Skills.getXP(SKILLS.MAGIC),Skills.getXP(SKILLS.MINING),Skills.getXP(SKILLS.PRAYER),
				Skills.getXP(SKILLS.RANGED),Skills.getXP(SKILLS.RUNECRAFTING),Skills.getXP(SKILLS.SLAYER),Skills.getXP(SKILLS.SMITHING),
				Skills.getXP(SKILLS.STRENGTH),Skills.getXP(SKILLS.THIEVING),Skills.getXP(SKILLS.WOODCUTTING)};
		return xp[ind];
	}
	@Override
	public void serverMessageReceived(String message) {
		MessageUtility.ServerMessage(message);
	}

	@Override
	public void clanMessageReceived(String arg0, String arg1) {}

	@Override
	public void duelRequestReceived(String arg0, String arg1) {}

	@Override
	public void personalMessageReceived(String arg0, String arg1) {}

	@Override
	public void playerMessageReceived(String arg0, String arg1) {}

	@Override
	public void tradeRequestReceived(String arg0) {}
}