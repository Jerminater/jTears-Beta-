package scripts.jTears.antiban;

import org.tribot.api.Clicking;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.types.RSObject;

import scripts.jTears.data.Vars;
import scripts.jTears.utils.JTearsUtil;

/**
 * Thanks to Einstein. I used his scripts as a reference.
 * @author Nate
 *
 */
public class JTearsAntiban {
	
	// Used for inner methods
	private boolean shouldHover, shouldOpenMenu;
	private boolean lastObjectHovered, lastObjectMenuOpened;
	
	private ABCUtil abcInstance = new ABCUtil();
	
	// Instance Manip
	private static JTearsAntiban antiban;
	
	private JTearsAntiban () {}

	public static JTearsAntiban get() {
		return antiban == null ? antiban = new JTearsAntiban() : antiban; 
	}
	
	public static void clear() {
		antiban = new JTearsAntiban();
	}
	// End Instance Manip
	
	// ABC2 Information
	public double reactionTimeScale = General.randomDouble(0.1, 0.3);
	
	public boolean shouldAbcSleep;
	
	public long lastWaitTime;
	public long averageWaitTime;
	public long totalWaitTime;
	public long startCollectingTime;
	public int totalCollectingInstances;
	
	
	// Pattern persistence
	public JTearsAntibanProfile antibanProfile;
	
	public void performTimedActions() {
		
		/*if (abcInstance.shouldCheckTabs())
			abcInstance.checkTabs();
		
		if (abcInstance.shouldCheckXP()) {
			abcInstance.checkXP();
			// Sleep after checking xp otherwise we instantly move mouse away from exp which looks very bot like
			General.sleep(General.randomSD(750, 1500, 1000, 150));
		}
		
		if (abcInstance.shouldLeaveGame())
			abcInstance.leaveGame();*/
		
		if (abcInstance.shouldExamineEntity())
			abcInstance.examineEntity();
		
		if (abcInstance.shouldMoveMouse())
			abcInstance.moveMouse();
		
		if (abcInstance.shouldPickupMouse())
			abcInstance.pickupMouse();
		
		if (abcInstance.shouldRightClick())
			abcInstance.rightClick();
		
		if (abcInstance.shouldRotateCamera())
			abcInstance.rotateCamera();
		
	}
	
	// Banking is handled by API methods
	// Walking conditions are unnecessary because dax walker is used
	// Tab Switch Preference is unnecessary because we are not switching tabs
	
	public RSObject getNextTarget(Positionable[] objects) {
		return (RSObject) abcInstance.selectNextTarget(objects);
	}
	
	// We don't eat so HP to eat at is useless
	// Running is handled by dax walker so no need to generate run activation
	
	// Could potentially add in moving to anticipated however someone told me he believes it is absolutely pointless
	
	// Could potentially add in resource switching upon high competition
	
	
	// TY Einstein/Naton
	/**
	 * Updates the variables.
	 * 
	 * Must be called upon starting an action. (example: clicking a tree)
	 */
	public void setHoverAndMenuOpenBooleans() {
		this.shouldHover = abcInstance.shouldHover();
		this.shouldOpenMenu= abcInstance.shouldOpenMenu();
	}
	
	/**
	 * Hovers over or opens the menu for target, if it should.
	 * 
	 * Will be called while performing an action. (example: while cutting a tree)
	 * 
	 * @param target to hover/open menu
	 */
	public void executeHoverOrMenuOpen(RSObject target) {
		if (Mouse.isInBounds() && this.shouldHover) {
			Clicking.hover(target);
			if (this.shouldOpenMenu)
				if (!ChooseOption.isOpen())
					DynamicClicking.clickRSObject(target, 3);
		}
	}
	
	public void setLastObjectHoverAndMenu() {
		this.lastObjectHovered = this.shouldHover;
		this.lastObjectMenuOpened = this.shouldOpenMenu;
	}
	
	private int generateReactionTime(int waitingTime) {

		long menuOpenOption = this.lastObjectMenuOpened ? ABCUtil.OPTION_MENU_OPEN : 0;
		long hoverOption = this.lastObjectHovered ? ABCUtil.OPTION_HOVERING : 0;
		
		return abcInstance.generateReactionTime(abcInstance.generateBitFlags(waitingTime, menuOpenOption, hoverOption));
	}
	
	public void generateSupportingTrackerInfo() {
		abcInstance.generateTrackers(this.averageWaitTime);	
	}
	
	public void sleepReactionTime(int waitingTime) {
		
		String priorStatus = Vars.get().status;
		
		Vars.get().status = "Sleeping";
		
		int reaction = generateReactionTime(waitingTime);
		
		int scaledReaction = (int) (reaction * this.reactionTimeScale);
		
		General.println("sleeping for " + scaledReaction + "ms");
		try {
			abcInstance.sleep(scaledReaction);
		} catch (InterruptedException e) {}
		
		Vars.get().status = priorStatus;
		
		this.shouldAbcSleep = false;
	}
	
	public boolean isHovering() {
		return this.shouldHover || this.shouldOpenMenu;
	}
	
	public void close() {
		abcInstance.close();
	}
	
	public void updateCollectionStatistics() {
		lastWaitTime = (int) (System.currentTimeMillis() - startCollectingTime);
		totalCollectingInstances++;
		totalWaitTime += lastWaitTime;
		averageWaitTime = totalWaitTime / totalCollectingInstances;
//		General.println("finished mining rock, updating reaction time statistics, average mining time: " + averageWaitTime / 1000.0 + " seconds");
	}
	
	public void abcSleep() { //average time waited from starting to collect to ending collect
		sleepReactionTime((int) averageWaitTime);
	}
	
	// This should only be called after calling updateCollectionStatistics. 
	public void abcSleepAfterCollecting() {
		sleepReactionTime((int) lastWaitTime);
	}
	
	public RSObject getNewTarget(RSObject currentTarget, int[] blueIds) {

		RSObject[] tears = JTearsUtil.getAvailableTears(blueIds);

		// If somehow there are no blue tears found, return null
		if (tears.length == 0)
			return null;

		if (tears.length > 0) {

			RSObject newTarget = getNextTarget(tears);

			while (JTearsUtil.areTearsEqual(currentTarget, newTarget) && JTearsUtil.isCollecting())
				newTarget = getNextTarget(tears);

			return newTarget;

		}

		return null;
	}
}






















