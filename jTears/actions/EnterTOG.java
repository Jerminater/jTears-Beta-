package scripts.jTears.actions;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;

import scripts.aCamera.ACamera;
import scripts.framework.Priority;
import scripts.framework.Task;
import scripts.jTears.data.Constants;
import scripts.jTears.data.Vars;

public class EnterTOG implements Task {

	ACamera aCamera = new ACamera();
	
	@Override
	public String toString() {
	        return "Talking to Juna";
	    }
	 
	@Override
	public Priority priority() {
		return Priority.MEDIUM;
	}

	@Override
	public boolean validate() {
		if (Constants.Areas.JUNA.getArea().contains(Player.getPosition()))
			return true;
		return false;
	}

	@Override
	public void execute() 
	{
		if (!Vars.get().collected) 
		{
			RSItem[] shield = Equipment.find(Equipment.SLOTS.SHIELD);
			RSItem[] weapon = Equipment.find(Equipment.SLOTS.WEAPON);
			if (shield != null)
				Equipment.remove(Equipment.SLOTS.SHIELD);
			if (weapon != null)
				Equipment.remove(Equipment.SLOTS.WEAPON);
			RSObject[] Juna = Objects.find(40, "Juna");
			if (Juna.length > 0 && Juna[0].isClickable()) 
			{
				Mouse.setSpeed(300);
				if (DynamicClicking.clickRSObject(Juna[0], "Story"));
				{
					Mouse.setSpeed(100);
					Timing.waitCondition(new Condition()
					{
						@Override
						public boolean active() 
						{
							General.sleep(20);
							RSInterfaceChild chat = NPCChat.getClickContinueInterface();
							return (Interfaces.isInterfaceSubstantiated(chat));
						}
					}, General.random(5000, 8000));
					RSInterfaceChild chat = NPCChat.getClickContinueInterface();
					while (Interfaces.isInterfaceSubstantiated(chat))
					{
						General.sleep(40);
						NPCChat.clickContinue(true);
					}
					aCamera.setCameraAngle(100);
					General.sleep(5000);
				}
			}
		} else
			Vars.get().endCond = true;
	}
}
