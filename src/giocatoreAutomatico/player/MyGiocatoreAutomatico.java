package giocatoreAutomatico.player;

import game2048.*;
import giocatoreAutomatico.*;
import java.util.Arrays;
import java.util.Set;

public class MyGiocatoreAutomatico implements GiocatoreAutomatico {
    
    final int MONOTONYWEIGHT=4;
    final int CONTIGUITYWEIGHT=2;
    final int FREECELLSWIEGHT=2;
    final int MERGEWEIGHT=1;
    final int UPPERSIDEWEIGHT=6;
    final int RIGHTSIDEWEIGHT=5;
    final int LEFTSIDEWEIGHT=4;
    final int DOWNSIDEWEIGHT=3;
    final int PREVISIONTREEDEPTH=1;
    
    private int[][] griglia = new int[4][4];

    /**
     * Select next move.
     * 
     * @param  g The game grid.
     * 
     * @return   0 == Direction.UP | 1 == Direction.RIGHT | 2 == Direction.DOWN | 3 == Direction.LEFT
     */
    public int prossimaMossa(Griglia g)
    {
        
        Set<Location> locations = g.keySet();

        for (Location a : locations) {

            this.griglia[a.getY()][a.getX()] = g.get(a).intValue();
        }
      
        return this.arbiter(this.griglia);
       
    }


    /**
     * Emulate a move.
     *
     * @param griglia The game grid.
     * @param dir The move to play.
     *
     * @return The grid with the played move.
     */
    public int[][] euristic(int [][] griglia,Direction dir) 
    {
       
        int [][] auxGrid=new int[4][4];
        boolean[] isMerged = new boolean[4]; // Check tiles merging.
        int i, j, k; // Indexes.

        /* Grid copy */
        for(i=0; i<4; i++){

            for (j=0; j<4; j++) {

                auxGrid[i][j]  = griglia[i][j];            
               
            }

        }

        ////////////LEFT
        if (dir==Direction.LEFT)
        {
            return prevediMossaSinistra(auxGrid);
        }


        ///////////////RIGHT
       else if (dir==Direction.RIGHT) 
        {
            return prevediMossaDestra(auxGrid);
        }

        //////////////UP
       else if (dir==Direction.UP) 
        {
            return prevediMossaSu(auxGrid);

        }
        
        //////////////DOWN
        else  
        {
            return prevediMossaGiu(auxGrid);
        }  
        
    }

    /**
     * Emulate a move to the right side.
     *
     * @param grid The game grid.
     *
     * @return The grid with the played move.
     */
    public int [][] prevediMossaDestra(int [][] grid ){
        
        int [][] auxGrid=new int [4][4];
        boolean[] isMerged = new boolean[4];
        int i,j,k;


        /* Grid copy */
        for (int x=0;x<4;x++) {

            for (int y=0;y<4;y++) {

                auxGrid[x][y]=grid[x][y];
                    
            }

        }
        
        for(i=0; i<4; i++) {

            for(k=0; k<4; k++)

                isMerged[k] = false;

                    for(j=3; j>=0; j--) {

                            if(auxGrid[i][j]!=-1) {

                                boolean merging=false;

                                for(k=j+1; k<4; k++) {

                                        if(auxGrid[i][k]!=-1) {

                                                if(auxGrid[i][k]==auxGrid[i][j])					 
                                                {

                                                        if (isMerged[k]==false)
                                                        {
                                                                auxGrid[i][k]*=2;
                                                                isMerged[k]=true;
                                                                merging=true;
                                                        }

                                                        else
                                                        {
                                                                break;
                                                        }

                                                }

                                                else{
                                                    break;
                                                }

                                        }
                                }

                                if(!merging){

                                        auxGrid[i][k-1]=auxGrid[i][j];

                                }

                                if(j!=k-1){

                                    auxGrid[i][j] = -1;

                                }

                            }

                    }

        }

        return auxGrid;

    }
    
    /**
     * Emulate a move to the left side.
     *
     * @param grid The game grid.
     *
     * @return The grid with the played move.
     */
    public int [][] prevediMossaSinistra(int [][] grid ) {

        int [][] auxGrid=new int [4][4];
        boolean[] isMerged = new boolean[4];
        int i,j,k;

        /* Copy grid */
        for (int x=0;x<4;x++) {

            for (int y=0;y<4;y++) {

                auxGrid[x][y]=grid[x][y];
                    
            }

        }
        
        
            for(i=0; i<4; i++) {

                    for(k=0; k<4; k++)
                        isMerged[k] = false;

                    for(j=0; j<4; j++) {

                            if(auxGrid[i][j]!=-1)
                            {
                                    boolean merging=false;

                                    for(k=j-1; k>=0; k--) {

                                            if(auxGrid[i][k]!=-1) {

                                                if(auxGrid[i][k]==auxGrid[i][j]) {

                                                        if (isMerged[k]==false)
                                                        {
                                                                auxGrid[i][k]*=2;
                                                                isMerged[k]=true;
                                                                merging=true;
                                                        }

                                                        else
                                                        {
                                                                break;
                                                        }
                                                }

                                                else
                                                    break;
                                            }

                                    }

                                    if(!merging)
                                        auxGrid[i][k+1]=auxGrid[i][j];

                                    if(j!=k+1)
                                        auxGrid[i][j] = -1;

                            }

                    }

            }

        return auxGrid;

    }
    
    /**
     * Emulate a move to the upper side.
     *
     * @param grid The game grid.
     *
     * @return The grid with the played move.
     */
    public int [][] prevediMossaSu(int [][] grid ) {
        int [][] auxGrid=new int [4][4];
        boolean[] isMerged = new boolean[4];
        int i,j,k;

        /* Copy grid */
        for (int x=0;x<4;x++) {

            for (int y=0;y<4;y++) {

                auxGrid[x][y]=grid[x][y];
                    
            }

        }
        

        
        for(j=0; j<4; j++) {

            for(k=0; k<4; k++)
                isMerged[k] = false;

            for(i=0; i<4; i++) {

                if(auxGrid[i][j]!=-1) {

                    boolean merging=false;

                    for(k=i-1; k>=0; k--) {

                        if(auxGrid[k][j]!=-1) {

                            if(auxGrid[k][j]==auxGrid[i][j]) {

                                if (isMerged[k]==false)
                                {
                                    auxGrid[k][j]*=2;
                                    isMerged[k]=true;
                                    merging=true;
                                }

                                else
                                {
                                    break;
                                }

                            }

                            else
                                break;
                        }

                    }

                    if(!merging)
                        auxGrid[k+1][j]=auxGrid[i][j];
                    if(i!=k+1)
                        auxGrid[i][j] = -1;
                }

            }

        }

        return auxGrid;
    
    }

        /**
     * Emulate a move to the bottom side.
     *
     * @param grid The game grid.
     *
     * @return The grid with the played move.
     */
    public int [][] prevediMossaGiu(int [][] grid ) {

        int [][] auxGrid=new int [4][4];
        boolean[] isMerged = new boolean[4];
        int i,j,k;

        /* Copy grid */
        for (int x=0;x<4;x++) {

            for (int y=0;y<4;y++) {

                auxGrid[x][y]=grid[x][y];
                    
            }
        }
        

        for(j=0; j<4; j++) {

            for(k=0; k<4; k++)
                isMerged[k] = false;

            for(i=3; i>=0; i--) {
                if(auxGrid[i][j]!=-1) {

                    boolean merging=false;

                    for(k=i+1; k<4; k++) {

                        if(auxGrid[k][j]!=-1)
                        {
                            if(auxGrid[k][j]==auxGrid[i][j])					 
                            {

                                if (isMerged[k]==false)
                                {
                                    auxGrid[k][j]*=2;
                                    isMerged[k]=true;
                                    merging=true;
                                }

                                else
                                {
                                    break;
                                }

                            }

                            else
                                break;
                        }

                    }

                    if(!merging)
                        auxGrid[k-1][j]=auxGrid[i][j];

                    if(i!=k-1)
                        auxGrid[i][j] = -1;
                }

            }

        } 

        return auxGrid;

    }

    
    /**
     * Return the number of free cells of the grid @griglia
     * 
     * @param  griglia The game grid.
     * 
     * @return Number of free cells
     */
    public int freeCells( int[][] griglia)
    {        
        int count = 0;

        for (int i = 0; i < 4; i++) {

                for (int j = 0; j < 4; j++) {

                    if (griglia[i][j]==-1) count++; 

                }
            
        }
        
        return count;        
        
    }


    /**
     * Calculate contiguity.
     * 
     * @param  auxGrid The game grid
     * @return 
     */
    public int contiguity(int[][] auxGrid)
    {

        int tmp;
        int count = 0;
        int i,j,k;

        for(i=0; i<4; i++)
        {

            for(j=0; j<4; j++)
            {

                tmp = auxGrid[i][j];
                k=j+1;

                while(k<4 && auxGrid[i][k]==-1)
                    k++;

                if(k<4)
                    if(tmp == auxGrid[i][k])
                    {
                        count++;
                        j=k;
                    }

            }

        }


        for(j=0; j<4; j++)
        {
            for(i=0; i<4; i++)
            {
                tmp = auxGrid[i][j];
                k=i+1;

                while(k<4 && auxGrid[k][j]==-1)
                    k++;

                if(k<4)
                    if(tmp == auxGrid[k][j])
                    {
                        count++;
                        i=k;
                    }            

            }
        }

        return count;
	
    } 


    /**
     * Calculate merge points.
     * 
     * @param auxGrid The game grid
     * @param dir The merge direction.
     * 
     * @return Merge points.
     */
    public int puntiMerge(int[][] auxGrid, Direction dir)
    {

        int i, j, k,tmp,count=0;

        if(dir==Direction.LEFT || dir==Direction.RIGHT)
        {
            for(i=0; i<4; i++)
            {
                for(j=0; j<4; j++)
                {
                    tmp = auxGrid[i][j];
                    k=j+1;
                    while(k<4 && auxGrid[i][k]==-1)
                        k++;

                    if(k<4)
                        if(tmp == auxGrid[i][k])
                        {
                            count+=auxGrid[i][k];
                            j=k;
                        }

                }
            }
        }


        if(dir==Direction.UP || dir==Direction.DOWN)
        {
            for(j=0; j<4; j++)
            {
                for(i=0; i<4; i++)
                {
                    tmp = auxGrid[i][j];
                    k=i+1;
                    while(k<4 && auxGrid[k][j]==-1)
                        k++;

                    if(k<4)
                        if(tmp == auxGrid[k][j])
                        {
                            count+=auxGrid[k][j];
                            i=k;
                        }

                }
            }
        }

        return count;

    }    


    /**
     * Calculate monotony.
     * 
     * @param auxGrid The game grid
     * 
     * @return Monotony points.
     */
    public int monotony(int [][] auxGrid) 
    {
        
        int i,j,k,tmp,count = 0;

        for(i=0; i<4; i++)
        {
            for(j=0; j<4; j++)
            {

                if(auxGrid[i][j]!=-1)
                {
                    tmp = auxGrid[i][j];
                    k = j + 1;

                    while(k<4 && auxGrid[i][k] == -1)
                        k++;

                    if(k<4)
                    {
                        if(auxGrid[i][k] > tmp)            
                            count++;
                        else if(auxGrid[i][k] < tmp)
                            count--;                
                    }
                }    

            }
        }

        count = Math.abs(count);

        for(j=0; j<4; j++)
        {
            for(i=0; i<4; i++)
            {

                if(auxGrid[i][j]!=-1)
                {
                    tmp = auxGrid[i][j];
                    k = i + 1;

                    while(k<4 && auxGrid[k][j] == -1)
                        k++;

                    if(k<4)
                    {
                        if(auxGrid[k][j] > tmp)            
                            count++;
                        else if(auxGrid[k][j] < tmp)
                            count--;
                    }    
                }

            }
        }

        count = Math.abs(count);
        
        return count;
    }


    /**
     * Check if @first and @second are the same grid.
     * 
     * @param first The first grid
     * @param second The second direction.
     * 
     * @return True if @first and @second are the same grid, false otherwise.
     */
    public boolean isNotSame(int [][] first,int [][] second) {
        
         for(int j=0; j<4; j++){
        
            for(int i=0; i<4; i++){
            
                if (first[i][j]!=second[i][j])
                    return true;

            }

        }

        return false;
    }

    /**
     * Calculate move points.
     * 
     * @param  grid       The game grid.
     * @param  dir        The move direction.
     * @param  ricorsioni Current tree level.
     * 
     * @return            The move points.
     */
    public int punteggioMossa(int[][] grid, Direction dir, int ricorsioni) 
    {

        int[] punteggio = new int[4];
        int [][] auxGrid=new int [4][4];
        int punti;

        /* Copy grid */
        for (int x=0;x<4;x++) {

            for (int y=0;y<4;y++) {

                auxGrid[x][y]=grid[x][y];
                    
            }

        }

        /* Emulate move */
        auxGrid=this.euristic(auxGrid, dir);
        
        
        if(ricorsioni>0)
        {
            ricorsioni--;

            punteggio[0] = punteggioMossa(this.euristic(auxGrid,Direction.UP ), Direction.UP, ricorsioni);
            punteggio[1]= punteggioMossa(this.euristic(auxGrid,Direction.RIGHT ),Direction.RIGHT, ricorsioni);
            punteggio[2]= punteggioMossa(this.euristic(auxGrid,Direction.DOWN ), Direction.DOWN, ricorsioni);
            punteggio[3]= punteggioMossa(this.euristic(auxGrid,Direction.LEFT ), Direction.LEFT, ricorsioni);

            Arrays.sort(punteggio);

            return punteggio[3]; 
        
        }      
        
        
        punti=this.puntiMerge(grid, dir)+this.contiguity(auxGrid)*CONTIGUITYWEIGHT+this.freeCells(auxGrid)*FREECELLSWIEGHT+this.monotony(auxGrid)*MONOTONYWEIGHT;
        
        if (dir==Direction.LEFT) return punti;
        else if (dir==Direction.UP) return punti;
        else if (dir==Direction.DOWN) return punti;
        else return punti;
        
    }

    /**
     * Choose the best move out of the 4 available.
     * 
     * @param  oldGriglia The game grid.
     * 
     * @return            The best move. At least he tries to.
     */
    public int arbiter(int [][] oldGriglia) {
        
        int punti = 0;
        int dir = 1;
    
        /* RIGHT POINTS*/
        if (this.isNotSame(oldGriglia, this.euristic(oldGriglia,Direction.RIGHT))) {
            
            punti = RIGHTSIDEWEIGHT*(punteggioMossa(oldGriglia,Direction.RIGHT, PREVISIONTREEDEPTH));
            
        }

        /* UP POINTS */
        if (this.isNotSame(oldGriglia, this.euristic(oldGriglia,Direction.UP))) {

            if (punti < UPPERSIDEWEIGHT*punteggioMossa(oldGriglia,Direction.UP, PREVISIONTREEDEPTH)) {

                punti = UPPERSIDEWEIGHT*punteggioMossa(oldGriglia,Direction.UP, PREVISIONTREEDEPTH);
                dir = 0;              

            }

        }

        /* LEFT POINTS */
        if (this.isNotSame(oldGriglia, this.euristic(oldGriglia,Direction.LEFT))) {

            if (punti < LEFTSIDEWEIGHT*punteggioMossa(oldGriglia,Direction.LEFT, PREVISIONTREEDEPTH)) {

                punti = LEFTSIDEWEIGHT*punteggioMossa(oldGriglia,Direction.LEFT, PREVISIONTREEDEPTH);
                dir = 3;

            }

        }

        /* DOWN POINTS */
        if (this.isNotSame(oldGriglia, this.euristic(oldGriglia,Direction.DOWN))) {

            if (punti < DOWNSIDEWEIGHT*punteggioMossa(oldGriglia,Direction.DOWN, PREVISIONTREEDEPTH)) {

                punti = DOWNSIDEWEIGHT*punteggioMossa(oldGriglia,Direction.DOWN, PREVISIONTREEDEPTH);
                dir = 2;

            }

        }

        return dir;

    }

}