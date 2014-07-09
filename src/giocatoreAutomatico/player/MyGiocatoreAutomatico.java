package giocatoreAutomatico.player;

import game2048.*;
import giocatoreAutomatico.*;
import java.util.Arrays;
import java.util.Set;

public class MyGiocatoreAutomatico implements GiocatoreAutomatico {
    
    final int monotonyWeight=1;
    final int contiguityWeight=1;
    final int freeCellsWeight=1;
    final int mergeWeight=1;
    final int upSideWeight=1;
    final int rightSideWeight=1;
    final int leftSideWeight=1;
    final int downSideWeight=1;
    final int previsionTreeDepth=1;
    
    private int[][] griglia = new int[4][4];

    public int prossimaMossa(Griglia g) {
        // tmp implementation with random moveù
           //   g.toString();
        Set<Location> locations = g.keySet();
        for (Location a : locations) {

            this.griglia[a.getY()][a.getX()] = g.get(a).intValue();
        }
     this.stampa(this.euristic(griglia, Direction.UP), Direction.UP, monotonyWeight);
     this.stampa(this.euristic(griglia, Direction.DOWN), Direction.DOWN, monotonyWeight);
      
       return this.arbiter(this.griglia);
       
    }
    
    public void stampa(int [][] grid,Direction dir,int profondita) {
        System.out.println(dir.name()+"profondita"+profondita);
        for (int i=0;i<4;i++) { for (int j=0;j<4;j++) {
            System.out.print(grid[i][j]+" ");
        }
        System.out.println();
    }
    }

    public int[][] euristic(int [][] griglia,Direction dir) 
    {
     

        
       
        int [][] auxGrid=new int[4][4];
        boolean[] isMerged = new boolean[4]; //vettore per il controllo delle tile mergiate
        int i, j, k; //indici
        
        
        for(i=0; i<4; i++)   //copio la griglia
            for (j=0; j<4; j++) {
                auxGrid[i][j]  = griglia[i][j];            
               
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
    public int [][] prevediMossaDestra(int [][] grid ){
        
        int [][] auxGrid=new int [4][4];
        for (int x=0;x<4;x++) {
        for (int y=0;y<4;y++) {
            auxGrid[x][y]=grid[x][y];
                
            }
        }
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
    
    public int [][] prevediMossaSinistra(int [][] grid ) {
        int [][] auxGrid=new int [4][4];
        for (int x=0;x<4;x++) {
        for (int y=0;y<4;y++) {
            auxGrid[x][y]=grid[x][y];
                
            }
        }
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
    
    public int [][] prevediMossaSu(int [][] grid ) {
        int [][] auxGrid=new int [4][4];
        for (int x=0;x<4;x++) {
        for (int y=0;y<4;y++) {
            auxGrid[x][y]=grid[x][y];
                
            }
        }
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
    public int [][] prevediMossaGiu(int [][] grid ) {
        int [][] auxGrid=new int [4][4];
        for (int x=0;x<4;x++) {
        for (int y=0;y<4;y++) {
            auxGrid[x][y]=grid[x][y];
                
            }
        }
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
        System.out.println("free "+cont);
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
        System.out.println("cont "+count);
        return count;


	
} 
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



System.out.println("merge "+count);
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
    System.out.println("monotony "+count);
      System.out.println();
    return count;
}

public boolean isNotSame(int [][] first,int [][] second) {
    
     for(int j=0; j<4; j++)
    
        for(int i=0; i<4; i++)
        
    if (first[i][j]!=second[i][j]) return true;
    return false;
}

public int punteggioMossa(int[][] grid, Direction dir, int ricorsioni/*, int tot*/) 
{
    int[] punteggio = new int[4];
     int [][] auxGrid=new int [4][4];
    int punti;
    for (int x=0;x<4;x++) {
        for (int y=0;y<4;y++) {
            auxGrid[x][y]=grid[x][y];
                
            }
        }
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
    
    
    punti=this.puntiMerge(grid, dir)+this.contiguity(auxGrid)*contiguityWeight+this.freeCells(auxGrid)*freeCellsWeight+this.monotony(auxGrid)*monotonyWeight;
    //this.stampa(auxGrid, dir, ricorsioni);
    if (dir==Direction.LEFT) return punti;
    else if (dir==Direction.UP) return punti;
    else if (dir==Direction.DOWN) return punti;
    else return punti;
    
}

    
    public int arbiter(int [][] oldGriglia) {
        
        int punti = 0;
        int dir = 1;
  
        if (this.isNotSame(oldGriglia, this.euristic(oldGriglia,Direction.RIGHT))) {
            
            punti = rightSideWeight*(punteggioMossa(oldGriglia,Direction.RIGHT, previsionTreeDepth));
            
        }
        if (this.isNotSame(oldGriglia, this.euristic(oldGriglia,Direction.UP))) {
            if (punti < upSideWeight*punteggioMossa(oldGriglia,Direction.UP, previsionTreeDepth)) {
                punti = upSideWeight*punteggioMossa(oldGriglia,Direction.UP, previsionTreeDepth);
                dir = 0;
               

            }
        }
        if (this.isNotSame(oldGriglia, this.euristic(oldGriglia,Direction.LEFT))) {
            if (punti < leftSideWeight*punteggioMossa(oldGriglia,Direction.LEFT, previsionTreeDepth)) {
                punti = leftSideWeight*punteggioMossa(oldGriglia,Direction.LEFT, previsionTreeDepth);
                dir = 3;
               

            }
        }
        if (this.isNotSame(oldGriglia, this.euristic(oldGriglia,Direction.DOWN))) {
            if (punti < downSideWeight*punteggioMossa(oldGriglia,Direction.DOWN, previsionTreeDepth)) {
                punti = downSideWeight*punteggioMossa(oldGriglia,Direction.DOWN, previsionTreeDepth);
                dir = 2;

            }
        }
       System.out.println(punteggioMossa(oldGriglia,Direction.UP, previsionTreeDepth));
        System.out.println();
          System.out.println(punteggioMossa(oldGriglia,Direction.DOWN, previsionTreeDepth));
            System.out.println();
           System.out.println(punteggioMossa(oldGriglia,Direction.LEFT, previsionTreeDepth));
             System.out.println(); 
           System.out.println(punteggioMossa(oldGriglia,Direction.RIGHT, previsionTreeDepth));
             System.out.println();
        return dir;

    }

}