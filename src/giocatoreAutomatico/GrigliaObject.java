package giocatoreAutomatico;

import game2048.*;
import java.util.*;

public class GrigliaObject extends HashMap<Location, Integer> implements Griglia {
	
	public GrigliaObject(HashMap<Location, Tile> gameGrid){

		Collection<Tile> grid=gameGrid.values();

		for(Tile currentTile : grid){
			this.put(currentTile.getLocation(),currentTile.getValue()==0?new Integer(-1):currentTile.getValue());
		}

	}

}