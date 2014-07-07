package giocatoreAutomatico.player;

import giocatoreAutomatico.*;
import game2048.*;
import java.util.Random;

public class MyGiocatoreAutomatico implements GiocatoreAutomatico{

	private Griglia oldGrid;
	private GameManager gameManager=new GameManager();
	
	public int prossimaMossa(Griglia g){

		Random randomGenerator=new Random();

		return randomGenerator.nextInt(4);
	}
	
}