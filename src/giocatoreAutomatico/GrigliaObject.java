package giocatoreAutomatico;

import game2048.*;
import java.util.*;

public class GrigliaObject extends HashMap<Location, Integer> implements Griglia {
	
	public GrigliaObject(Map<Location, Tile> gameGrid){
<<<<<<< HEAD
		super(16);

		Set<Location> locations=gameGrid.keySet();

		for(Location currentLocation : locations){
			this.put(currentLocation,gameGrid.get(currentLocation)==null?new Integer(-1):new Integer(gameGrid.get(currentLocation).getValue()));
=======
                super(16);
		Set<Location> locations=gameGrid.keySet();

		for(Location currentLocation : locations){
			Tile auxTile = gameGrid.get(currentLocation);
                        if(gameGrid.get(currentLocation)==null) this.put(currentLocation,new Integer(-1));
                        else this.put(currentLocation,auxTile.getValue());
                        
                        
>>>>>>> 06dbce6233fd9acd9674f06f98cdab4457e4a97c
		}

	}

}