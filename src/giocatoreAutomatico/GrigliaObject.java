package giocatoreAutomatico;

import game2048.*;
import java.util.*;

public class GrigliaObject extends HashMap<Location, Integer> implements Griglia {
	
	public GrigliaObject(Map<Location, Tile> gameGrid){
		super(16);

		Set<Location> locations=gameGrid.keySet();

		for(Location currentLocation : locations){
			this.put(currentLocation,gameGrid.get(currentLocation)==null?new Integer(-1):new Integer(gameGrid.get(currentLocation).getValue()));
		}

	}

}