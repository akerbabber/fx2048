package giocatoreAutomatico;

import game2048.*;
import java.util.*;

public class GrigliaObject extends HashMap<Location, Integer> implements Griglia {
	
	public GrigliaObject(Map<Location, Tile> gameGrid){
                super(16);
		Set<Location> locations=gameGrid.keySet();

		for(Location currentLocation : locations){
			Tile auxTile = gameGrid.get(currentLocation);
                        if (auxTile==null) this.put(currentLocation,new Integer(-1));
                        else this.put(currentLocation,auxTile.getValue());
                        
                        
		}

	}

}