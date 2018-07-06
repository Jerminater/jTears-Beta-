package scripts.jTears.utils;

import java.util.Arrays;

import org.tribot.api.General;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.Timing07.Timing07;
import scripts.aCamera.ACamera;
import scripts.jTears.JTears;
import scripts.jTears.data.Constants;

public class JTearsUtil {	
	
	ACamera aCamera = new ACamera();
	
	public static void WithdrawTeleport() {
		if (!Banking.withdraw(1, Constants.Objects.GAMES_NECKLACE.getIds()))
			General.println("Sorry, you don't have a games necklace!");
	}
	
	public static boolean inLumby() {
		return (Constants.Areas.LUMBRIDGE.getArea().contains(Player.getPosition()) || 
				Constants.Areas.LUMBY_BASEMENT.getArea().contains(Player.getPosition()));
	}
	
	public static boolean haveGames() {
		if ((Inventory.find(Filters.Items.nameContains("Games necklace")).length == 0))
		{
			RSItem ammy = Equipment.SLOTS.AMULET.getItem();
			if (ammy != null)
					if (!Arrays.asList(JTears.GAMES_NECKLACE).contains(ammy.getID()))
						return false;
					else
						return true;
			else
				return false;
		}
		return true;
	}
	
	public static boolean wearingGames() {
		if ((Inventory.find(Filters.Items.nameContains("Games necklace")).length == 0))
		{
			RSItem ammy = Equipment.SLOTS.AMULET.getItem();
			if (ammy != null) {
					if (Arrays.asList(JTears.GAMES_NECKLACE).contains(ammy.getID()))
						return true;
			}
		}
		return false;
	}
	
	public static boolean isWallGreen() {
		RSTile tearsPos = getCurrentTears().getPosition();
		Filter<RSObject> Obj = Filters.Objects.idEquals(Constants.Objects.GREENOREMPTY.getIds());
		RSObject[] cur = Objects.getAt(tearsPos, Obj);
		return (cur.length > 0);
	}
	
	public static boolean isCollecting() {
		return Player.getAnimation() != -1;
	}
	
	public static RSObject[] getAvailableTears(int[] ids) {
		return Objects.findNearest(30, ids);
	}
	
	public static RSObject getCurrentTears() {
		if (!isCollecting())
			return null;
		
		RSTile objectTile = Player.getPosition();
		
		switch (Player.getRSPlayer().getOrientation()) {
		case 1537: // East
			objectTile = objectTile.translate(1, 0);
			break;
		case 1023: // North
			objectTile = objectTile.translate(0, 1);
			break;
		case 0: // South
			objectTile = objectTile.translate(0, -1);
			break;
		case 511: // West
			objectTile = objectTile.translate(-1, 0);
			break;
		default:
			return null;
		}
		
		Filter<RSObject> filter = Filters.Objects.tileEquals(objectTile);
		
		RSObject[] foundObjects = Objects.findNearest(3, filter);
		
		RSObject tear = getObjectWithName(foundObjects, "Weeping wall");

		return tear;
	}
	
	public static boolean areTearsEqual(RSObject tear1, RSObject tear2) {
		if (tear1 == null || tear2 == null)
			return false;
		
		return tear1.getPosition().equals(tear2.getPosition());
	}
	
public static boolean isTearValid(RSObject tear) {
		
		if (tear == null)
			return false;
		
		for (int id : Constants.Objects.GREENOREMPTY.getIds())
			if (id == tear.getID())
				return false;
		return true;
	}

	//TY Einstein
	public static void waitToStartAnimating(Positionable target) {
		
		if (Player.getPosition().distanceTo(target) > 1)  // Wait to stop walking
			Timing07.waitCondition(() -> !Player.isMoving() || !isTearValid((RSObject)target), General.random(4000, 6000));
		//Wait to start animating
		Timing07.waitCondition(() -> isCollecting() || !isTearValid((RSObject)target), General.random(2000, 3000));
	}
	
	public static RSObject getObjectWithName(RSObject[] objects, String name) {
		try {
			for (RSObject object : objects) 
				if (object.getDefinition().getName().equals(name))
					return object;
		}
		catch (NullPointerException e) {
		}
		return null;
	}
}
