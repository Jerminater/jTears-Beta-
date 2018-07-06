package scripts.jTears.utils;

import scripts.jTears.data.Vars;

public class MessageUtility {

	public static void ServerMessage(String message) {
		if (message.contains("to wait another"))
			Vars.get().homeTeleport = false;
		if (message.contains("must wait another"))
			Vars.get().minigameTeleport = false;
	}	
}
