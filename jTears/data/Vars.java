package scripts.jTears.data;

import java.util.Properties;

public class Vars {
	
	// instance stuff
	
		private static Vars vars;
		
		public static Vars get() {
			return vars == null? vars = new Vars() : vars;
		}
		
		public static void reset() {
			vars = new Vars();
		}
		
		// other data
		public boolean homeTeleport, minigameTeleport;
		
		// Properties for loading/saving GUI settings
		public Properties prop = new Properties();
		
		// Script information
		public boolean endCond, collected = false;
		public String status = "Initializing";
		public long runTime;
		public String tears;
		
		// Antiban
		//public Antiban ab = new Antiban();
		//public boolean afkMode, shouldGenerateActionTime;
		
		// Paint
		//public Tabs tab = Paint.Tabs.INFO;
		//public int attXp, strXp, defXp, mageXp, rangeXp, hpXp;
		//public boolean hidePaint = false;
		
		// Combat Variables
		//public int eatAtHP;
		//public boolean shouldReset, drankPotion, ateFood;
		//public PotionHandler potionHandler;
		//public FoodHandler foodHandler;
		//public PositionHandler positionHandler;
		
		// Banking
		//public boolean checkBank = true;

	

}