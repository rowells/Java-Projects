/* Ocean.java */

/**
 *  The Ocean class defines an object that models an ocean full of sharks and
 *  fish.  Descriptions of the methods you must implement appear below.  They
 *  include a constructor of the form
 *
 *      public Ocean(int i, int j, int starveTime);
 *
 *  that creates an empty ocean having width i and height j, in which sharks
 *  starve after starveTime timesteps.
 *
 *  See the README file accompanying this project for additional details.
 */

public class Ocean {
	
	/**
	*  Do not rename these constants.  WARNING:  if you change the numbers, you
	*  will need to recompile Test4.java.  Failure to do so will give you a very
	*  hard-to-find bug.
	*/
	
	public final static int EMPTY = 0;
	public final static int SHARK = 1;
	public final static int FISH = 2;
	
	/**
	*  Define any variables associated with an Ocean object here.  These
	*  variables MUST be private.
	*/
	
	private int x;
	private int y;
	private int hungry;
	private int[] sharkHunger;
	private int[] seaCells;

	/**
	 *  The following methods are required for Part I.
	 */
	
	public int cellWrap(int x, int y) 
	{
	x = x % this.x; 
	y = y % this.y;
	if(x < 0) 
	{ 
	x = x+this.x;
	}
	if(y < 0) 
	{ 
	y = y+this.y;
	}
	int ind = this.x*y+x;
	return ind;
	}
	
		
	public int xSpot(int z) 
	{
	int i = z % x;
	return i; 
	}
  	
	public int ySpot(int z) 
	{
	int j = z/x;
	return j; 
	}
	
	/**
	 *  Ocean() is a constructor that creates an empty ocean having width i and
	 *  height j, in which sharks starve after starveTime timesteps.
	 *  @param i is the width of the ocean.
	 *  @param j is the height of the ocean.
	 *  @param starveTime is the number of timesteps sharks survive without food.
	 */
	
	public Ocean(int i, int j, int starveTime) 
	{
		hungry = starveTime;
		x = i;
		y = j;
		seaCells = new int[i*j];
		for (int k=0; k < seaCells.length; k++) 
		{ 
		seaCells[k] = EMPTY; 
		} 
		sharkHunger = new int[i*j];
		for (int l=0; l < sharkHunger.length; l++) 
		{ 
		sharkHunger[l] = 0; 
		} 
	}
	
	/**
	 *  width() returns the width of an Ocean object.
	 *  @return the width of the ocean.
	 */
	
	public int width() 
	{
		return x;
	}
	
	/**
	 *  height() returns the height of an Ocean object.
	 *  @return the height of the ocean.
	 */
	
	public int height() 
	{
		return y;
	}
	
	/**
	 *  starveTime() returns the number of timesteps sharks survive without food.
	 *  @return the number of timesteps sharks survive without food.
	 */
	
	public int starveTime() 
	{
	return hungry;
	}
	
	/**
	 *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
	 *  cell is already occupied, leave the cell as it is.
	 *  @param x is the x-coordinate of the cell to place a fish in.
	 *  @param y is the y-coordinate of the cell to place a fish in.
	 */
	
	public void addFish(int x, int y) {
		if (seaCells[this.cellWrap(x,y)] == EMPTY) 
		{
		seaCells[this.cellWrap(x,y)] = FISH;
		}
	}
	
	/**
	 *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
	 *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
	 *  just eaten.  If the cell is already occupied, leave the cell as it is.
	 *  @param x is the x-coordinate of the cell to place a shark in.
	 *  @param y is the y-coordinate of the cell to place a shark in.
	 */
	
	public void addShark(int x, int y) 
	{
	int zN = this.cellWrap(x,y);
	if (seaCells[zN] == EMPTY) 
	{
	seaCells[zN] = SHARK;
	sharkHunger[zN] = hungry;
	}
	}
	
	/**
	 *  cellContents() returns EMPTY if cell (x, y) is empty, FISH if it contains
	 *  a fish, and SHARK if it contains a shark.
	 *  @param x is the x-coordinate of the cell whose contents are queried.
	 *  @param y is the y-coordinate of the cell whose contents are queried.
	 */
	
	public int cellContents(int x, int y) 
	{
	return seaCells[this.cellWrap(x,y)]; 
	}
	
	/**
	 *  timeStep() performs a simulation timestep as described in README.
	 *  @return an ocean representing the elapse of one timestep.
	 */
	 
	public int[] neighborsAhoy(int z) 
	{
	int[] array = new int[8];
	int x = xSpot(z);
	int y = ySpot(z);
	array[0] = cellContents(x-1, y-1); 
	array[1] = cellContents(x, y-1); 
	array[2] = cellContents(x+1, y-1); 
	array[3] = cellContents(x+1, y); 
	array[4] = cellContents(x+1, y+1); 
	array[5] = cellContents(x, y+1); 
	array[6] = cellContents(x-1, y+1); 
	array[7] = cellContents(x-1, y); 
	return array; 
	}
	
	public int neighborCheck(int type, int z) 
	{
	int[] array = neighborsAhoy(z);
	int number = 0;
	for (int i=0; i<array.length; i++) 
	{
	if (array[i] == type) { number++; } }
	return number; 
	}
	
	public boolean omNom(int z) 
	{ 
	if (seaCells[z] == SHARK && neighborCheck(FISH, z)>0) 
	{
	return true;
	} 
	else 
	{ 
	return false; 
	} 
	}
	
	public boolean gettingEaten(int z) 
	{ 
	if (seaCells[z] == FISH && neighborCheck(SHARK, z)==1) 
	{
	return true;
	} 
	else 
	{ 
	return false; 
	} 
	}
   
	public boolean newShark(int z) 
	{
	if (seaCells[z] == FISH && neighborCheck(SHARK, z)>1) 
	{
	return true;
	} 
	else if (seaCells[z] == EMPTY && neighborCheck(FISH, z)>1 && neighborCheck(SHARK, z)>1) 
	{
	return true;
	} 
	else 
	{ 
	return false; 
	} 
	}
   	
	public boolean newFish(int z) 
	{ 
	if (seaCells[z] == EMPTY && neighborCheck(FISH, z)>1 && neighborCheck(SHARK, z)<2) 
	{
	return true;
	} 
	else 
	{ 
	return false; 
	} 
	}
	
	public Ocean timeStep() 
	{
	Ocean newocean = new Ocean(x, y, hungry); 
	for (int z=0; z < seaCells.length; z++) 
	{
		if (omNom(z)) 
		{ 
		newocean.seaCells[z] = SHARK;
		newocean.sharkHunger[z] = hungry; 
		}
		else if (omNom(z) != true && seaCells[z]==SHARK) 
		{
			if (sharkHunger[z] == 0) 
			{ 
			} 
			else 
			{ 
			newocean.seaCells[z] = SHARK;
			newocean.sharkHunger[z] = sharkHunger[z] - 1;
			}
		} 
		else if (gettingEaten(z)) 
		{ 
		} 
		else if (newShark(z)) 
		{  
		newocean.seaCells[z] = SHARK;
		newocean.sharkHunger[z] = hungry;
		} 
		else if (newFish(z)) 
		{ 
		newocean.seaCells[z] = FISH;  
		} 
		else 
		{ 
		newocean.seaCells[z] = seaCells[z]; 
		} 
	}
	return newocean; 
	}
	
/**
 *  The following method is required for Part II.
 */

/**
 *  addShark() (with three parameters) places a shark in cell (x, y) if the
 *  cell is empty.  The shark's hunger is represented by the third parameter.
 *  If the cell is already occupied, leave the cell as it is.  You will need
 *  this method to help convert run-length encodings to Oceans.
 *  @param x is the x-coordinate of the cell to place a shark in.
 *  @param y is the y-coordinate of the cell to place a shark in.
 *  @param feeding is an integer that indicates the shark's hunger.  You may
 *  		encode it any way you want; for instance, "feeding" may be the
 *  		last timestep the shark was fed, or the amount of time that has
 *  		passed since the shark was last fed, or the amount of time left
 * 			before the shark will starve.  It's up to you, but be consistent.
 */

	public void addShark(int x, int y, int feeding) 
	{
	int zN = this.cellWrap(x,y);
	if	(seaCells[zN] == EMPTY) 
	{
	seaCells[zN] = SHARK;
	sharkHunger[zN] = feeding;
	}
	}
					 
/**
 *  The following method is required for Part III.
 */

/**
 *  sharkFeeding() returns an integer that indicates the hunger of the shark
 *  in cell (x, y), using the same "feeding" representation as the parameter
 *  to addShark() described above.  If cell (x, y) does not contain a shark,
 *  then its return value is undefined--that is, anything you want.
 *  Normally, this method should not be called if cell (x, y) does not
 *  contain a shark.  You will need this method to help convert Oceans to
 *  run-length encodings.
 *  @param x is the x-coordinate of the cell whose contents are queried.
 *  @param y is the y-coordinate of the cell whose contents are queried.
 */

	public int sharkFeeding(int x, int y) 
	{
	return sharkHunger[this.cellWrap(x,y)];
	}
	
	
	public int sharkFeeding(int z) 
	{
	return sharkFeeding(xSpot(z), ySpot(z));
	}

}
