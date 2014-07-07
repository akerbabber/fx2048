package giocatoreAutomatico.player;

import giocatoreAutomatico.*;
import game2048.*;
import java.util.Random;

public class MyGiocatoreAutomatico implements GiocatoreAutomatico{
	
    
	public int prossimaMossa(Griglia g){
		// tmp implementation with random move
		Random randomGenerator=new Random();

		return randomGenerator.nextInt(4);
	}
        
        
	
}