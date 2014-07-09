package giocatoreAutomatico.player;

import game2048.*;
import giocatoreAutomatico.*;
import java.util.Set;

public class MyGiocatoreAutomatico implements GiocatoreAutomatico {

    private int[][] griglia = new int[4][4];

    public int prossimaMossa(Griglia g) {
        // tmp implementation with random moveù
              g.toString();
        Set<Location> locations = g.keySet();
        for (Location a : locations) {

            this.griglia[a.getY()][a.getX()] = g.get(a).intValue();
        }
      
       return this.arbiter();
       
    }

    public int[][] euristic(Direction dir) 
    {
     

        int[][] auxGrid = new int[4][4];     
        int[][] oldGrid = new int[4][4];    
        
        boolean[] isMerged = new boolean[4];
        int i, j, k;
        
        
        for(i=0; i<4; i++)
            for (j=0; j<4; j++)
                auxGrid[i][j]  = this.griglia[i][j];
            for(i=0; i<4; i++)
            for (j=0; j<4; j++)
                oldGrid[i][j]  = this.griglia[i][j];

        ////////////LEFT
        if (dir==Direction.LEFT)
        {
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



        }


        ///////////////RIGHT
        if (dir==Direction.RIGHT) 
        {

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


        }

        //////////////UP
        if (dir==Direction.UP) 
        {


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

        }
        //////////////DOWN
        if (dir==Direction.DOWN) 
        {
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
    }
       
        for (i=0;i<4;i++) 
        {
            for (j=0;j<4;j++) 
            {
                System.out.print(auxGrid[i][j]+" ");
            }
            System.out.println();
        }
        
        System.out.println();
        System.out.println();
        System.out.println();
        ///inserire confronto aux e old grid
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
        
        return count*2;


	
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
    return count*2;
}

public boolean isSame(int [][] first,int [][] second) {
    
     for(int j=0; j<4; j++)
    
        for(int i=0; i<4; i++)
        
    if (first[i][j]!=second[i][j]) return true;
    return false;
}

    
    public int arbiter() 
{
    
    int tmp=0;
    int dir=3;
       int[][] oldGriglia = this.griglia;
      
       if (this.isSame(oldGriglia, this.euristic(Direction.LEFT)))
    tmp=this.contiguity(this.euristic(Direction.LEFT))+this.freeCells(this.euristic(Direction.LEFT))+this.monotony(this.euristic(Direction.LEFT));
    if (this.isSame(oldGriglia, this.euristic(Direction.UP)))
     if (tmp< this.contiguity(this.euristic(Direction.UP))+this.freeCells(this.euristic(Direction.UP))+this.monotony(this.euristic(Direction.UP))){
         tmp=this.contiguity(this.euristic(Direction.UP))+this.freeCells(this.euristic(Direction.UP))+this.monotony(this.euristic(Direction.UP));
         dir=0;
         
     }
    if (this.isSame(oldGriglia, this.euristic(Direction.RIGHT)))
     if (tmp< this.contiguity(this.euristic(Direction.RIGHT))+this.freeCells(this.euristic(Direction.RIGHT))+this.monotony(this.euristic(Direction.RIGHT))){
         tmp=this.contiguity(this.euristic(Direction.RIGHT))+this.freeCells(this.euristic(Direction.RIGHT))+this.monotony(this.euristic(Direction.RIGHT));
         dir=1;
         
     }
    if (this.isSame(oldGriglia, this.euristic(Direction.DOWN)))
     if (tmp< (this.contiguity(this.euristic(Direction.DOWN))+this.freeCells(this.euristic(Direction.DOWN))+this.monotony(this.euristic(Direction.DOWN)))/3){
         tmp=this.contiguity(this.euristic(Direction.DOWN))+this.freeCells(this.euristic(Direction.DOWN))+this.monotony(this.euristic(Direction.UP));
         dir=2;
         
     }
    
         
return dir;

}

}