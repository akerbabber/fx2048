package giocatoreAutomatico.player;

import game2048.*;
import giocatoreAutomatico.*;
import java.util.Set;

public class MyGiocatoreAutomatico implements GiocatoreAutomatico {
    
    final int monotonyWeight=3;
    final int contiguityWeight=3;
    final int freeCellsWeight=2;
    final int upSideWeight=2;
    final int rightSideWeight=2;
    final int leftSideWeight=3;
    final int downSideWeight=3;
    private int[][] griglia = new int[4][4];

    public int prossimaMossa(Griglia g) {
        // tmp implementation with random moveù
           //   g.toString();
        Set<Location> locations = g.keySet();
        for (Location a : locations) {

            this.griglia[a.getY()][a.getX()] = g.get(a).intValue();
        }
      
       return this.arbiter();
       
    }

    public int[][] euristic(Direction dir) 
    {
     

        int[][] auxGrid = new int[4][4];     //girglia utilizzata per lo spostamento
       
        
        boolean[] isMerged = new boolean[4]; //vettore per il controllo delle tile mergiate
        int i, j, k; //indici
        
        
        for(i=0; i<4; i++)   //copio la griglia
            for (j=0; j<4; j++) {
                auxGrid[i][j]  = this.griglia[i][j];            
               
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
    public int [][] prevediMossaDestra(int [][] auxGrid ){
        
        int i,j,k;
        boolean[] isMerged = new boolean[4];
         for(i=0; i<4; i++)
            {
                    for(k=0; k<4; k++)
                            isMerged[k] = false;

                    for(j=3; j>=0; j--)
                    {
                            if(auxGrid[i][j]!=-1)
                            {
                                    boolean merging=false;
                                    for(k=j+1; k<4; k++)
                                    {
                                            if(auxGrid[i][k]!=-1)
                                            {
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
                                                    else
                                                            break;
                                            }
                                    }
                                    if(!merging)
                                            auxGrid[i][k-1]=auxGrid[i][j];
                                    if(j!=k-1)
                                            auxGrid[i][j] = -1;
                            }


                    }

            }

    return auxGrid;
    }
    
    public int [][] prevediMossaSinistra(int [][] auxGrid ) {
        
        int i,j,k;
        boolean[] isMerged = new boolean[4];
            for(i=0; i<4; i++)
            {
                    for(k=0; k<4; k++)
                            isMerged[k] = false;

                    for(j=0; j<4; j++)
                    {
                            if(auxGrid[i][j]!=-1)
                            {
                                    boolean merging=false;
                                    for(k=j-1; k>=0; k--)
                                    {
                                            if(auxGrid[i][k]!=-1)
                                            {
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
    
    public int [][] prevediMossaSu(int [][] auxGrid ) {
        
        int i,j,k;
        boolean[] isMerged = new boolean[4];
        
            for(j=0; j<4; j++)
            {
                    for(k=0; k<4; k++)
                            isMerged[k] = false;

                    for(i=0; i<4; i++)
                    {
                            if(auxGrid[i][j]!=-1)
                            {
                                    boolean merging=false;
                                    for(k=i-1; k>=0; k--)
                                    {
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
                                            auxGrid[k+1][j]=auxGrid[i][j];
                                    if(i!=k+1)
                                            auxGrid[i][j] = -1;
                            }


                    }

            }
        return auxGrid;
    
    }
    public int [][] prevediMossaGiu(int [][] auxGrid ) {
        
        int i,j,k;
        boolean[] isMerged = new boolean[4];
        for(j=0; j<4; j++)
            {
                    for(k=0; k<4; k++)
                            isMerged[k] = false;

                    for(i=3; i>=0; i--)
                    {
                            if(auxGrid[i][j]!=-1)
                            {
                                    boolean merging=false;
                                    for(k=i+1; k<4; k++)
                                    {
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

    

    public int freeCells( int[][] griglia)
    {
        
        
        int cont = 0;
        for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                if (griglia[i][j]==-1) cont++; 
                }
            
                }
        return cont;
        
        
    }
    public int contiguity(int[][] auxGrid)
    {
       ////contiguità


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

public boolean isNotSame(int [][] first,int [][] second) {
    
     for(int j=0; j<4; j++)
    
        for(int i=0; i<4; i++)
        
    if (first[i][j]!=second[i][j]) return true;
    return false;
}

public int punteggioMossa(Direction dir) {
    int punteggio=this.contiguity(this.euristic(dir))*contiguityWeight+this.freeCells(this.euristic(dir))*freeCellsWeight+this.monotony(this.euristic(dir))*monotonyWeight;
    if (dir==Direction.LEFT) return punteggio*leftSideWeight;
    else if (dir==Direction.UP) return punteggio*upSideWeight;
    else if (dir==Direction.DOWN) return punteggio*downSideWeight;
    else return punteggio*rightSideWeight;
}

    
    public int arbiter() {

        int punti = 0;
        int dir = 3;
        int[][] oldGriglia = this.griglia;

        if (this.isNotSame(oldGriglia, this.euristic(Direction.LEFT))) {
            punti = (punteggioMossa(Direction.LEFT));
        }
        if (this.isNotSame(oldGriglia, this.euristic(Direction.UP))) {
            if (punti < punteggioMossa(Direction.UP)) {
                punti = punteggioMossa(Direction.UP);
                dir = 0;

            }
        }
        if (this.isNotSame(oldGriglia, this.euristic(Direction.RIGHT))) {
            if (punti < punteggioMossa(Direction.RIGHT)) {
                punti = punteggioMossa(Direction.RIGHT);
                dir = 1;

            }
        }
        if (this.isNotSame(oldGriglia, this.euristic(Direction.DOWN))) {
            if (punti < punteggioMossa(Direction.DOWN)) {
                punti = punteggioMossa(Direction.DOWN);
                dir = 2;

            }
        }

        return dir;

    }

}