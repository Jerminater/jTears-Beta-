package scripts.jTears.actions;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;

import scripts.Timing07.Timing07;
import scripts.aCamera.ACamera;
import scripts.framework.Priority;
import scripts.framework.Task;
import scripts.jTears.antiban.JTearsAntiban;
import scripts.jTears.data.Constants;
import scripts.jTears.utils.JTearsUtil;

public class GoToTears implements Task {
	
	@Override
	public String toString() {
	        return "Going to Tears of Guthix";
	    }
	 
	@Override
	public Priority priority() {
		return Priority.LOW;
	}

	@Override
	public boolean validate() {
		return (JTearsUtil.haveGames() && !Constants.Areas.JUNA.getArea().contains(Player.getPosition()));
	}

	ACamera aCamera = new ACamera();
	@Override
	public void execute() {
			if (JTearsUtil.wearingGames() && !Constants.Areas.TOG.getArea().contains(Player.getPosition())) {
				GameTab.open(GameTab.TABS.EQUIPMENT);
				RSItem neck = Equipment.SLOTS.AMULET.getItem();
				if (Clicking.click("Tears of Guthix", neck)) 
					Timing07.waitCondition(() -> Constants.Areas.TOG.getArea().contains(Player.getPosition()), General.random(5000, 7000));
			}
			else if (!JTearsUtil.wearingGames() && !Constants.Areas.TOG.getArea().contains(Player.getPosition())) {
				RSItem[] neck = Inventory.find(Filters.Items.nameContains("Games necklace"));
				if (Clicking.click("rub", neck))
				{
					JTearsAntiban.get().sleepReactionTime(1500); //generate reaction time
					Keyboard.holdKey('4',52, new Condition() {
						@Override
						public boolean active() {
							General.sleep(3000,4000);
							return true;
						}});
				}
				Timing07.waitCondition(() -> Constants.Areas.TOG.getArea().contains(Player.getPosition()), General.random(5000, 7000));
			} 
			else if (Constants.Areas.TOG.getArea().contains(Player.getPosition())) {
				PathFinding.aStarWalk(Constants.Tiles.JUNA.getTiles()[0]);
				General.sleep(1000,2000);
			}			
	}
}
