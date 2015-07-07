package giocatoreAutomatico.player;

import game2048.*;
import giocatoreAutomatico.*;
import java.util.Set;

public class MyGiocatoreAutomatico implements GiocatoreAutomatico {
    
    final int MONOTONYWEIGHT=8;
    final int CONTIGUITYWEIGHT=1;
    final int FREECELLSWIEGHT=20;
    final int MERGEWEIGHT=10;
    final int UPPERSIDEWEIGHT=10;
    final int RIGHTSIDEWEIGHT=9;
    final int LEFTSIDEWEIGHT=9;
    final int DOWNSIDEWEIGHT=4;
    final int PREVISIONTREEDEPTH=2;

    /**
     * Select next move.
     * 
     * @param  gameGrid The game grid.
     * 
     * @return   0 == Direction.UP | 1 == Direction.RIGHT | 2 == Direction.DOWN | 3 == Direction.LEFT
     */
    public int prossimaMossa(Griglia gameGrid)
    {
      
        return this.arbiter(new MatrixGriglia(gameGrid));
       
    }  

    /**
     * Calculate move points.
     * 
     * @param  grid       The game grid.
     * @param  dir        The move direction.
     * @param  ricorsioni Current tree level.
     * @param  tot        Depth and width sum in the prevision tree.
     * 
     * @return            The move points.
     */
    public int pointsMove(MatrixGriglia grid, Direction dir, int ricorsioni, int tot) 
    {

        MatrixGriglia auxGrid=grid.copy();
        int punti;

        /* Emulate move */
        auxGrid=auxGrid.emulateMove(dir);
        
        
        if(ricorsioni>0)
        {
            ricorsioni--;

            tot+= pointsMove(auxGrid.emulateMove(Direction.UP), Direction.UP, ricorsioni, tot);
            tot+= pointsMove(auxGrid.emulateMove(Direction.RIGHT),Direction.RIGHT, ricorsioni, tot);
            tot+= pointsMove(auxGrid.emulateMove(Direction.DOWN), Direction.DOWN, ricorsioni, tot);
            tot+= pointsMove(auxGrid.emulateMove(Direction.LEFT), Direction.LEFT, ricorsioni, tot);

            return tot; 
        
        }   
        
        punti=grid.puntiMerge(dir)*MERGEWEIGHT+auxGrid.contiguity()*CONTIGUITYWEIGHT+auxGrid.freeCells()*FREECELLSWIEGHT+auxGrid.monotony()*MONOTONYWEIGHT;
        
        return punti;
        
    }


    /**
     * Choose the best move out of the 4 available.
     * 
     * @param  oldGriglia The game grid.
     * 
     * @return            The best move. ( 0 == Direction.UP | 1 == Direction.RIGHT | 2 == Direction.DOWN | 3 == Direction.LEFT )
     */
    public int arbiter(MatrixGriglia oldGriglia) {
        
        int punti = 0;
        int dir = 1;
    
        /* Right move points*/
        if (!(oldGriglia.equals(oldGriglia.emulateMove(Direction.RIGHT)))) {
            
            punti = RIGHTSIDEWEIGHT*(pointsMove(oldGriglia,Direction.RIGHT, PREVISIONTREEDEPTH, 0));
            
        }

        /* Up move points */
        if (!(oldGriglia.equals(oldGriglia.emulateMove(Direction.UP)))) {

            if (punti < UPPERSIDEWEIGHT*(pointsMove(oldGriglia,Direction.UP, PREVISIONTREEDEPTH, 0))) {

                punti = UPPERSIDEWEIGHT*(pointsMove(oldGriglia,Direction.UP, PREVISIONTREEDEPTH, 0));
                dir = 0;              

            }

        }

        /* Left move points */
        if (!(oldGriglia.equals(oldGriglia.emulateMove(Direction.LEFT)))) {

            if (punti < LEFTSIDEWEIGHT*(pointsMove(oldGriglia,Direction.LEFT, PREVISIONTREEDEPTH, 0))) {

                punti = LEFTSIDEWEIGHT*(pointsMove(oldGriglia,Direction.LEFT, PREVISIONTREEDEPTH, 0));
                dir = 3;

            }

        }

        /* Down points */
        if (!(oldGriglia.equals(oldGriglia.emulateMove(Direction.DOWN)))) {

            if (punti < DOWNSIDEWEIGHT*(pointsMove(oldGriglia,Direction.DOWN, PREVISIONTREEDEPTH, 0))) {

                dir = 2;

            }

        }

        return dir;

    }

}