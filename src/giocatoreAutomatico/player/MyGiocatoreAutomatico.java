package giocatoreAutomatico.player;

import game2048.*;
import giocatoreAutomatico.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MyGiocatoreAutomatico implements GiocatoreAutomatico{
	
        public int[][] griglia= new int[4][4];
    
	public int prossimaMossa(Griglia g){
		// tmp implementation with random move
            Set<Location> locations=g.keySet();
            for(Location a:locations)
            {
               
            griglia[a.getX()][a.getY()]=g.get(a).intValue();
                System.out.println(griglia[a.getX()][a.getY()]);
            }
		Random randomGenerator=new Random();

		return randomGenerator.nextInt(4);
	}
        private int isMovable() {
            
        }
        
       
        
	
}