package scripts.jTears.actions;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Magic;
import org.tribot.api2007.Player;

import scripts.Timing07.Timing07;
import scripts.framework.Priority;
import scripts.framework.Task;
import scripts.jTears.data.Constants;
import scripts.jTears.data.Vars;
import scripts.jTears.utils.JTearsUtil;
import scripts.minigame.MinigameTele;
import scripts.utilities.JBanking;
import scripts.webwalker_logic.WebWalker;

public class Bank implements Task {

	@Override
	public String toString() {
	        return "Gathering Items";
	    }
	
	@Override
	public Priority priority() {
		return Priority.LOW;
	}

	@Override
	public boolean validate() {
		return (!JTearsUtil.haveGames() && !Constants.Areas.TOG.getArea().contains(Player.getPosition()));
	}

	@Override
	public void execute() {
		if (JBanking.isInBank() || Constants.Areas.LUMBY_BASEMENT.getArea().contains(Player.getPosition())) 
		{
			if (Banking.isBankScreenOpen())
			{
				if (!Inventory.isFull())
				{
					if (!JTearsUtil.haveGames())
					{
						if (!Banking.withdraw(1, Constants.Objects.GAMES_NECKLACE.getIds())) 
						{
							General.println("Sorry, you don't have a games necklace!");
							Vars.get().endCond = true;
						}
						Banking.close();
						General.sleep(1000,2000);
					}
					else 
						Banking.close();
				}
				else
					Banking.depositAll();
			}
			else
				Banking.openBank();
		} else if (!JTearsUtil.inLumby())
		{
			if (Vars.get().homeTeleport) // message listening checks for server message
			{
				Magic.selectSpell("Lumbridge Home Teleport");
				General.sleep(3000); // takes a few seconds for animation to start
				Timing07.waitCondition(() -> (Player.getAnimation() == -1), General.random(10000, 15000));
				General.sleep(2000); // just to make sure screen loads
			}
			else if (Vars.get().minigameTeleport) // message listening checks for server message
			{
				if (MinigameTele.chooseMinigame("Castle Wars")) {
				     MinigameTele.teleport();
				     General.sleep(3000); // takes a few seconds for animation to start
				     Timing07.waitCondition(() -> (Player.getAnimation() == -1), General.random(10000, 15000));
				     General.sleep(2000);
				}
			} else
				WebWalker.walkToBank();
		}
		else 
			WebWalker.walkTo(Constants.Tiles.LUMBY_CHEST.getTiles()[0]);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
