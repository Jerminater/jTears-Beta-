package scripts.jTears.data;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public class Constants {

public enum Tiles {
		
		PLAYER_POSITION(new RSTile[] {
			    new RSTile(3257, 9519, 2), new RSTile(3258, 9519, 2), 
			    new RSTile(3259, 9519, 2), new RSTile(3260, 9518, 2), 
			    new RSTile(3260, 9517, 2), new RSTile(3260, 9516, 2), 
			    new RSTile(3259, 9515, 2), new RSTile(3258, 9515, 2), 
			    new RSTile(3257, 9515, 2)}),
		TEARS(new RSTile[] {
			    new RSTile(3257, 9520, 2), new RSTile(3258, 9520, 2), 
			    new RSTile(3259, 9520, 2), new RSTile(3261, 9518, 2), 
			    new RSTile(3261, 9517, 2), new RSTile(3261, 9516, 2), 
			    new RSTile(3259, 9514, 2), new RSTile(3258, 9514, 2), 
			    new RSTile(3257, 9514, 2)}),
		LUMBY_CHEST(new RSTile[] { new RSTile(3218,9623,0)}),
		LUMBY_HOLE(new RSTile[] {new RSTile(3219,9618,0)}),
		JUNA(new RSTile[] {new RSTile(3251, 9517, 2)});
	
		private final RSTile[] tiles;
		
		private Tiles(RSTile[] tiles) {
			this.tiles = tiles;
		}
		
		public RSTile[] getTiles() {
			return tiles;
		}
	}
	
public enum Areas {
		
		LUMBY_HOLE(new RSArea(new RSTile [] { new RSTile(3220, 9619, 0),
				new RSTile(3216, 9619, 0),new RSTile(3215, 9616, 0),
				new RSTile(3218, 9616, 0) })),
		COLLECTION(new RSArea(new RSTile [] { new RSTile(3257, 9520, 2),
				new RSTile(3259, 9520, 2),new RSTile(3261, 9518, 2),new RSTile(3261, 9516, 2),
				new RSTile(3260, 9513, 2),new RSTile(3257, 9513, 2),new RSTile(3252, 9516, 2),
				new RSTile(3252, 9517, 2) })),
		LUMBY_BASEMENT(new RSArea(new RSTile [] { new RSTile(3223, 9628, 0),
				new RSTile(3224, 9611, 0),new RSTile(3205, 9612, 0),new RSTile(3205, 9626, 0) })),
		LUMBRIDGE(new RSArea(new RSTile [] { new RSTile(3228, 3237, 0),
				new RSTile(3199, 3238, 0),new RSTile(3200, 3200, 0),new RSTile(3229, 3205, 0),
				new RSTile(3240, 3219, 0) })),
		TOG(new RSArea(new RSTile [] { new RSTile(3240, 9493, 2),
				new RSTile(3255, 9495, 2),new RSTile(3263, 9512, 2),
				new RSTile(3262, 9523, 2),new RSTile(3249, 9533, 2),
				new RSTile(3241, 9528, 2) })),
		JUNA(new RSArea(new RSTile [] { new RSTile(3252, 9519, 2),
				new RSTile(3246, 9519, 2),new RSTile(3247, 9513, 2),
				new RSTile(3252, 9513, 2),new RSTile(3253, 9513, 2),
				new RSTile(3252, 9515, 2),new RSTile(3252, 9516, 2),
				new RSTile(3252, 9517, 2),new RSTile(3253, 9518, 2) })),
		LUMBY_CHEST(new RSArea(new RSTile [] { new RSTile(3220, 9624, 0),
				new RSTile(3215, 9624, 0),new RSTile(3215, 9620, 0),
				new RSTile(3220, 9620, 0) }));

		private final RSArea areas;
		
		private Areas(RSArea areas) {
			this.areas = areas;
		}
		
		public RSArea getArea() {
			return areas;
		}
	}
	
	public enum Objects {
		
		GREENOREMPTY(new int[] {6666, 6662, 6667, 6663}),
		BLUE(new int[] {6665, 6661}),
		EARTH_RUNE(new int[] {557}),
		AIR_RUNE(new int[] {556}),
		LAW_RUNE(new int[] {563}),
		LUMBY_HOLE_ID(new int[] {6898, 6899, 6912}),
		GAMES_NECKLACE(new int[] {3867,3865,3863,3861,3859,3857,3855,3853}),
		UNLIT_LANTERN(new int[] {4701,4548}),
		LIT_LANTERN(new int[] {4702,9065}),
		TINDERBOX(new int[] {590});
		//juna id here
		
		private final int[] ids;
		
		private Objects(int[] ids) {
			this.ids = ids;
		}
		
		public int[] getIds() {
			return ids;
		}
	}
}