package giocatoreAutomatico;

import java.util.Set;
import game2048.*;

/**
 * A matrix implementation of Griglia.
 *
 * @see Griglia
 */
public class MatrixGriglia {

	private int[][] matrix; 
	

	/**
	 * Create an empty matrix.
     * 
	 * @return An empty matrix.
	 */
	public MatrixGriglia(){

		this.matrix=new int[4][4];

		for (int i=0;i<4;i++) {

			for (int j=0;j<4;j++) {
				
				this.matrix[i][j]=-1;

			}

		}

	}

	/**
	 * Create a MatrixGriglia from a GrigliaObject.
     * 
	 * @param  grid The game grid.
     * 
	 * @return      A MatrixGriglia implementation of a Griglia.
	 */
	public MatrixGriglia(Griglia grid){

		Set<Location> locations=grid.keySet();

		this.matrix=new int[4][4];		

        for (Location currentLocation : locations) {
            // In the original code X and Y axes are switched.
            this.matrix[currentLocation.getY()][currentLocation.getX()] = grid.get(currentLocation).intValue();

        }

	}

	@Override
	public boolean equals(Object obj){

		MatrixGriglia other;

		if(obj==null || !(obj instanceof MatrixGriglia))
			return false;

		other=(MatrixGriglia)obj;

		for(int j=0;j<4;j++){
        
            for(int i=0;i<4;i++){
            
                if (this.matrix[i][j]!=other.matrix[i][j])
                    return false;

            }

        }

        return true;		

	}

	/**
	 * Return the value in matrix[x][y].
	 * 
	 * @param  x Horizontal coordinate.
	 * @param  y Vertical coordinate.
	 * 
	 * @return The value in matrix[x][y].
	 */
	public int getValue(int x, int y){

		return this.matrix[x][y];

	}


	/**
	 * Put value @value in matrix[@x][@y].
	 * 
	 * @param x     The horizontal coordinate.
	 * @param y     The vertical coordinate. 
	 * @param value The value to put.
	 */
	public void putValue(int x, int y, int value){

		this.matrix[x][y]=value;

	}

	/**
	 * Copy a MatrixGriglia.
	 * 
	 * @return A copy of this MatrixGriglia.
	 */
	public MatrixGriglia copy(){

		MatrixGriglia copied=new MatrixGriglia();
		int i, j;

		for(i=0; i<4; i++){

            for (j=0; j<4; j++) {

                copied.matrix[i][j]=this.matrix[i][j];            
               
            }

        }

        return copied;
	}


	/**
     * Return the number of free cells.
     * 
     * @return Number of free cells
     */
    public int freeCells()
    {        
        int count = 0;

        for (int i=0;i<4;i++) {

                for (int j=0;j<4;j++) {

                    if (this.matrix[i][j]==-1)
                    	count++; 

                }
            
        }
        
        return count;        
        
    }


    /**
     * Calculate contiguity.
     * 
     * @return Contiguity.
     */
    public int contiguity()
    {

        int currentEntry;
        int count = 0;
        int i,j,k;

        for(i=0;i<4;i++)
        {

            for(j=0;j<4;j++)
            {

                currentEntry=this.matrix[i][j];
                k=j+1;

                while(k<4 && this.matrix[i][k]==-1)
                    k++;

                if(k<4)
                    if(currentEntry==this.matrix[i][k])
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
                currentEntry=this.matrix[i][j];
                k=i+1;

                while(k<4 && this.matrix[k][j]==-1)
                    k++;

                if(k<4)
                    if(currentEntry==this.matrix[k][j])
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
     * @param dir The merge direction.
     * 
     * @return Merge points.
     */
    public int puntiMerge(Direction dir)
    {

        int i, j, k,currentEntry,count=0;

        if(dir==Direction.LEFT || dir==Direction.RIGHT)
        {
            for(i=0;i<4;i++)
            {
                for(j=0;j<4;j++)
                {
                    currentEntry=this.matrix[i][j];
                    k=j+1;

                    while(k<4 && this.matrix[i][k]==-1)
                        k++;

                    if(k<4)
                        if(currentEntry==this.matrix[i][k])
                        {
                            count+=this.matrix[i][k];
                            j=k;
                        }
                }
            }
        }


        if(dir==Direction.UP || dir==Direction.DOWN)
        {
            for(j=0;j<4;j++)
            {
                for(i=0;i<4;i++)
                {
                    currentEntry = this.matrix[i][j];
                    k=i+1;

                    while(k<4 && this.matrix[k][j]==-1)
                        k++;

                    if(k<4)
                        if(currentEntry==this.matrix[k][j])
                        {
                            count+=this.matrix[k][j];
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
     * @return Monotony points.
     */
    public int monotony() 
    {
        
        int currentEntry;
        int count = 0;
        int i,j,k;

        for(i=0;i<4;i++)
        {
            for(j=0;j<4;j++)
            {

                if(this.matrix[i][j]!=-1)
                {
                    currentEntry = this.matrix[i][j];
                    k = j + 1;

                    while(k<4 && this.matrix[i][k] == -1)
                        k++;

                    if(k<4)
                    {
                        if(this.matrix[i][k] > currentEntry)            
                            count++;
                        else if(this.matrix[i][k] < currentEntry)
                            count--;                
                    }
                }    

            }
        }

        count = Math.abs(count);

        for(j=0;j<4;j++)
        {
            for(i=0;i<4;i++)
            {
                if(this.matrix[i][j]!=-1)
                {
                    currentEntry=this.matrix[i][j];
                    k=i+1;

                    while(k<4 && this.matrix[k][j] == -1)
                        k++;

                    if(k<4)
                    {
                        if(this.matrix[k][j] > currentEntry)            
                            count++;
                        else
                        	if(this.matrix[k][j] < currentEntry)
                            	count--;
                    }    
                }
            }
        }

        count = Math.abs(count);
        
        return count;
    }

    /**
     * Emulate a move.
     *
     * @param dir The move direction.
     *
     * @return The MatrixGriglia after the @dir move.
     */
    public MatrixGriglia emulateMove(Direction dir) 
    {
     	  
        MatrixGriglia newGrid=this.copy();
        boolean[] isMerged = new boolean[4]; // Check tiles merging.
        int i, j, k;

        if (dir==Direction.LEFT)
        {
            return newGrid.emulateLeft();
        }

        else if (dir==Direction.RIGHT) 
        {
            return newGrid.emulateRight();
        }

        else if (dir==Direction.UP) 
        {
            return newGrid.emulateUp();

        }
        
        else  
        {
            return newGrid.emulateDown();
        }  
        
    }

    /**
     * Emulate a move to the right side.
     * 
     * @return The grid after the played move.
     */
    private MatrixGriglia emulateRight(){
        
        boolean[] isMerged = new boolean[4];
        int i,j,k;

        
        for(i=0;i<4;i++) {

            for(k=0;k<4;k++)

                isMerged[k]=false;

                    for(j=3;j>=0;j--) {

                            if(this.matrix[i][j]!=-1) {

                                boolean merging=false;

                                for(k=j+1; k<4; k++) {

                                        if(this.matrix[i][k]!=-1) {

                                                if(this.matrix[i][k]==this.matrix[i][j])					 
                                                {

                                                        if (isMerged[k]==false)
                                                        {
                                                                this.matrix[i][k]*=2;
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

                                        this.matrix[i][k-1]=this.matrix[i][j];

                                }

                                if(j!=k-1){

                                    this.matrix[i][j] = -1;

                                }

                            }

                    }

        }

        return this;

    }

    /**
     * Emulate a move to the left side.
     * 
     * @return The grid after the played move.
     */
    private MatrixGriglia emulateLeft() {

        boolean[] isMerged = new boolean[4];
        int i,j,k;
        
        for(i=0; i<4; i++) {

                for(k=0; k<4; k++)
                    isMerged[k] = false;

                for(j=0; j<4; j++) {

                        if(this.matrix[i][j]!=-1)
                        {
                                boolean merging=false;

                                for(k=j-1; k>=0; k--) {

                                        if(this.matrix[i][k]!=-1) {

                                            if(this.matrix[i][k]==this.matrix[i][j]) {

                                                    if (isMerged[k]==false)
                                                    {
                                                            this.matrix[i][k]*=2;
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
                                    this.matrix[i][k+1]=this.matrix[i][j];

                                if(j!=k+1)
                                    this.matrix[i][j] = -1;

                        }

                }

        }

        return this;

    }


    /**
     * Emulate a move to the upper side.
     * 
     * @return The grid after the played move.
     */
    private MatrixGriglia emulateUp() {
        
        boolean[] isMerged = new boolean[4];
        int i,j,k;        

        
        for(j=0; j<4; j++) {

            for(k=0; k<4; k++)
                isMerged[k] = false;

            for(i=0; i<4; i++) {

                if(this.matrix[i][j]!=-1) {

                    boolean merging=false;

                    for(k=i-1; k>=0; k--) {

                        if(this.matrix[k][j]!=-1) {

                            if(this.matrix[k][j]==this.matrix[i][j]) {

                                if (isMerged[k]==false)
                                {
                                    this.matrix[k][j]*=2;
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
                        this.matrix[k+1][j]=this.matrix[i][j];
                    if(i!=k+1)
                        this.matrix[i][j] = -1;
                }

            }

        }

        return this;
    
    }


    /**
     * Emulate a move to the bottom side.
     * 
     * @return The grid after the played move.
     */
    private MatrixGriglia emulateDown() {

        boolean[] isMerged = new boolean[4];
        int i,j,k;


        for(j=0; j<4; j++) {

            for(k=0; k<4; k++)
                isMerged[k] = false;

            for(i=3; i>=0; i--) {
                if(this.matrix[i][j]!=-1) {

                    boolean merging=false;

                    for(k=i+1; k<4; k++) {

                        if(this.matrix[k][j]!=-1)
                        {
                            if(this.matrix[k][j]==this.matrix[i][j])					 
                            {

                                if (isMerged[k]==false)
                                {
                                    this.matrix[k][j]*=2;
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
                        this.matrix[k-1][j]=this.matrix[i][j];

                    if(i!=k-1)
                        this.matrix[i][j] = -1;
                }

            }

        } 

        return this;

    }
}