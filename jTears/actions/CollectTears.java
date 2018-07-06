package scripts.jTears.actions;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;

import scripts.aCamera.ACamera;
import scripts.framework.Priority;
import scripts.framework.Task;
import scripts.jTears.antiban.JTearsAntiban;
import scripts.jTears.data.Constants;
import scripts.jTears.data.Vars;
import scripts.jTears.utils.JTearsUtil;

public class CollectTears implements Task {
	
	ACamera aCamera = new ACamera();
	private RSObject target;
	private boolean changedTarget;
	
	
	@Override
	public String toString() {
	        return "Collecting Tears";
	    }
	 
	@Override
	public Priority priority() {
		return Priority.HIGH;
	}

	@Override
	public boolean validate() {
		if (Constants.Areas.COLLECTION.getArea().contains(Player.getPosition()))
			return true;
		return false;
	}

	@Override
	public void execute() 
	{
		Vars.get().collected = true;
		switch (getSubTask()) {
			case COLLECT_TEARS:
				if (JTearsUtil.isTearValid(target)) {
					General.println("hmm");
					if (clickTears(target, changedTarget)) {
						
						// Antiban stuff
						JTearsAntiban.get().setLastObjectHoverAndMenu();
						JTearsAntiban.get().setHoverAndMenuOpenBooleans();
						JTearsAntiban.get().generateSupportingTrackerInfo();
						JTearsAntiban.get().shouldAbcSleep = true;
						
						// Wait till we start Collecting
						General.println("leggo" + JTearsUtil.isCollecting());
						JTearsUtil.waitToStartAnimating(target);
					}
				}
				else {
					General.println("new targ");
					target = JTearsAntiban.get().getNewTarget(target, Constants.Objects.BLUE.getIds());
					changedTarget = true;
					General.sleep(250, 400);
				}
				
				break;
			case COLLECTING_TEARS:
				JTearsAntiban.get().startCollectingTime = Timing.currentTimeMillis();
				changedTarget = false;
				target = JTearsAntiban.get().getNewTarget(target, Constants.Objects.BLUE.getIds());
				if (target != null && Mouse.isInBounds())
					JTearsAntiban.get().executeHoverOrMenuOpen(target);
				
				while (JTearsUtil.isCollecting() && !JTearsUtil.isWallGreen()) {
					General.sleep(250, 400);
					// If we are not hovering then we can perform timed actions
					if (!JTearsAntiban.get().isHovering())
						JTearsAntiban.get().performTimedActions();
				}
				JTearsAntiban.get().updateCollectionStatistics();
				JTearsAntiban.get().abcSleepAfterCollecting();
				break;
		}		
}	

	enum SubTask {
		COLLECTING_TEARS,
		COLLECT_TEARS;
	}

	private SubTask getSubTask() 
	{
		if (JTearsUtil.isCollecting() && !JTearsUtil.isWallGreen())
			return SubTask.COLLECTING_TEARS;
		else
			return SubTask.COLLECT_TEARS;
	}
	
	private boolean clickTears(RSObject target, boolean changedTarget) {
		if (target == null)
			return false;
		if (ChooseOption.isOpen() && !changedTarget)
			return ChooseOption.select("Collect-from");
		if (!target.isOnScreen()) {
			if (Player.getPosition().distanceTo(target) < General.random(4, 6))
				aCamera.turnToTile(target);
			else
				Walking.blindWalkTo(target);
		}
		return target.click("Collect-from");
	}
}












