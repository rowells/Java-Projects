 /* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes an
 *  Ocean object.  Descriptions of the methods you must implement appear below.
 *  They include constructors of the form
 *
 *      public RunLengthEncoding(int i, int j, int starveTime);
 *      public RunLengthEncoding(int i, int j, int starveTime,
 *                               int[] runTypes, int[] runLengths) {
 *      public RunLengthEncoding(Ocean ocean) {
 *
 *  that create a run-length encoding of an Ocean having width i and height j,
 *  in which sharks starve after starveTime timesteps.
 *
 *  The first constructor creates a run-length encoding of an Ocean in which
 *  every cell is empty.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts an Ocean object into a run-length encoding of that object.
 *
 *  See the README file accompanying this project for additional details.
 **/

public class RunLengthEncoding {
	
	/**
	 *  Define any variables associated with a RunLengthEncoding object here.
	 *  These variables MUST be private.
	 **/
	
	private int countingRun;
	private DList temp2;

	
	/**
	 *  The following methods are required for Part II.
	 **/
	
	/**
	 *  RunLengthEncoding() (with three parameters) is a constructor that creates
	 *  a run-length encoding of an empty ocean having width i and height j,
	 *  in which sharks starve after starveTime timesteps.
	 *  @param i is the width of the ocean.
	 *  @param j is the height of the ocean.
	 *  @param starveTime is the number of timesteps sharks survive without food.
	 **/
	
	public RunLengthEncoding(int i, int j, int starveTime) 
	{
	temp2 = new DList(i, j, starveTime);
	temp2.insertFront(Ocean.EMPTY, i * j);
	countingRun = 1;
	}
	
	/**
	 *  RunLengthEncoding() (with five parameters) is a constructor that creates
	 *  a run-length encoding of an ocean having width i and height j, in which
	 *  sharks starve after starveTime timesteps.  The runs of the run-length
	 *  encoding are taken from two input arrays.  Run i has length runLengths[i]
	 *  and species runTypes[i].
	 *  @param i is the width of the ocean.
	 *  @param j is the height of the ocean.
	 *  @param starveTime is the number of timesteps sharks survive without food.
	 *  @param runTypes is an array that represents the species represented by
	 *         each run.  Each element of runTypes is Ocean.EMPTY, Ocean.FISH,
	 *         or Ocean.SHARK.  Any run of sharks is treated as a run of newborn
	 *         sharks (which are equivalent to sharks that have just eaten).
	 *  @param runLengths is an array that represents the length of each run.
	 *         The sum of all elements of the runLengths array should be i * j.
	 */
	
	public RunLengthEncoding(int i, int j, int starveTime, int[] runTypes, int[] runLengths) 
	{
		temp2 = new DList(i, j, starveTime);
		if (runTypes.length != runLengths.length) 
		{ 
		System.out.println("Check array insertions");
		} 
		else 
		{
			for (int ii = runTypes.length-1; ii>=0; ii--) 
			{
				if (runTypes[ii] == Ocean.SHARK) 
				{
				temp2.insertFront(runTypes[ii], runLengths[ii], starveTime);
				} 
				else 
				{
				temp2.insertFront(runTypes[ii], runLengths[ii]);
				}
			}
		countingRun = runTypes.length;
		}
	}
	
	/**
	 *  restartRuns() and nextRun() are two methods that work together to return
	 *  all the runs in the run-length encoding, one by one.  Each time
	 *  nextRun() is invoked, it returns a different run (represented as a
	 *  TypeAndSize object), until every run has been returned.  The first time
	 *  nextRun() is invoked, it returns the first run in the encoding, which
	 *  contains cell (0, 0).  After every run has been returned, nextRun()
	 *  returns null, which lets the calling program know that there are no more
	 *  runs in the encoding.
	 *
	 *  The restartRuns() method resets the enumeration, so that nextRun() will
	 *  once again enumerate all the runs as if nextRun() were being invoked for
	 *  the first time.
	 *
	 *  (Note:  Don't worry about what might happen if nextRun() is interleaved
	 *  with addFish() or addShark(); it won't happen.)
	 */
	
	/**
	 *  restartRuns() resets the enumeration as described above, so that
	 *  nextRun() will enumerate all the runs from the beginning.
	 */
	
	public void restartRuns() 
	{
	countingRun = temp2.length();
	}
	
	/**
	 *  nextRun() returns the next run in the enumeration, as described above.
	 *  If the runs have been exhausted, it returns null.  The return value is
	 *  a TypeAndSize object, which is nothing more than a way to return two
	 *  integers at once.
	 *  @return the next run in the enumeration, represented by a TypeAndSize
	 * 		  object.
	 */
	
	public TypeAndSize nextRun() 
	{
		if (countingRun == 0) 
		{ 
		return null; 
		}
		else 
		{
			int curindex = temp2.length()-countingRun+1;
			DListNode curnode = temp2.nth(curindex);
			int tip = curnode.type;
			int freq = curnode.foll;
			countingRun--;
			return new TypeAndSize(tip, freq); 
		}
	}
	
	/**
	 *  toOcean() converts a run-length encoding of an ocean into an Ocean
	 *  object.  You will need to implement the three-parameter addShark method
	 *  in the Ocean class for this method's use.
	 *  @return the Ocean represented by a run-length encoding.
	 */
	
	public Ocean toOcean() 
	{
	int index = 0;
	Ocean newocean = new Ocean(temp2.dimx, temp2.dimy, temp2.starvetime);
		for (int il = 1; il<=temp2.length(); il++) 
		{ 
		DListNode curnode = temp2.nth(il);
		int tip = curnode.type;
		int freq = curnode.foll;
		int hunger = curnode.hunger;
		int ia = index;
			for (; ia < index+freq; ia++) 
			{
				if (tip == Ocean.SHARK) 
				{
					newocean.addShark(newocean.xSpot(ia), newocean.ySpot(ia), hunger);
				}
				else if (tip == Ocean.FISH) 
				{
				newocean.addFish(newocean.xSpot(ia), newocean.ySpot(ia));
				} 
				else 
				{ 
				} 
			}
		index = ia;
		}
	return newocean;
	}
	
	/**
	 *  The following method is required for Part III.
	 */
	
	/**
	 *  RunLengthEncoding() (with one parameter) is a constructor that creates
	 *  a run-length encoding of an input Ocean.  You will need to implement
	 *  the sharkFeeding method in the Ocean class for this constructor's use.
	 *  @param sea is the ocean to encode.
	 */
	
	public RunLengthEncoding(Ocean sea) 
	{
	int is=0;
	int ir=1;
	int seasize = sea.width()*sea.height();
	temp2 = new DList(sea.width(), sea.height(), sea.starveTime());
		while (is<seasize) 
		{
		int tip = sea.cellContents(sea.xSpot(is), sea.ySpot(is));
		int hunger = sea.sharkFeeding(is);
		temp2.insertEnd(tip, 1, hunger);
		is++;
			for (; sea.cellContents(sea.xSpot(is), sea.ySpot(is)) == tip && sea.sharkFeeding(is) == hunger && is<seasize; is++) 
			{
			temp2.nth(ir).type = tip;
			temp2.nth(ir).foll++;
			temp2.nth(ir).hunger = hunger;
			}
		ir++;
		}
	countingRun = ir-1;
	check();
	}
	
	/**
	 *  The following methods are required for Part IV.
	 */
	
	/**
	 *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
	 *  cell is already occupied, leave the cell as it is.  The final run-length
	 *  encoding should be compressed as much as possible; there should not be
	 *  two consecutive runs of sharks with the same degree of hunger.
	 *  @param x is the x-coordinate of the cell to place a fish in.
	 *  @param y is the y-coordinate of the cell to place a fish in.
	 */
	
	public int itempos(int x, int y) 
	{
	x = x % temp2.dimx;
	y = y % temp2.dimy;
		if (x < 0) 
		{ 
		x = x + temp2.dimx; 
		}
		if (y < 0) 
		{ 
		y = y + temp2.dimy; 
		}
		int ind = (temp2.dimx*y+x) + 1;
		return ind;
	}

	public void addFish(int x, int y) 
	{
	int index = itempos(x, y);
	int temp2Spot = 1;
	int i = 1;
		for (; i < temp2.length(); i++) 
		{
		temp2Spot = temp2Spot + temp2.nth(i).foll;
			if (temp2Spot > index) 
			{ 
			temp2Spot = temp2Spot - temp2.nth(i).foll - 1;
			break; 
			}
		}
		if (i>=temp2.length()) 
		{ 
		System.out.println("Error detected: Self-destruct in 3...2...1"); 
		}
		else if (temp2.nth(i).type != Ocean.EMPTY) 
		{ 
		System.out.println("Cell Occupied"); 
		}
		else actualinsertion: 
		{ 
		DListNode removednode = temp2.nth(i);
		int after = removednode.foll - (index - temp2Spot);
		int before = removednode.foll - after - 1;
		DListNode insertionnode = new DListNode(Ocean.FISH);
		insertionnode.foll = 1;
		DListNode beforenode;
		DListNode afternode;
		boolean nodeisgone = false;
			if (after==0 && removednode.next.type==Ocean.FISH && removednode.prev.type!=Ocean.FISH) 
			{ 
			removednode.next.foll++;
			nodeisgone = true;
			}  
			if (before==0 && removednode.prev.type==Ocean.FISH && removednode.next.type!=Ocean.FISH) 
			{
			removednode.prev.foll++;
			nodeisgone = true;
			} 
			if (after==0 && before==0 && removednode.prev.type!=Ocean.FISH && removednode.next.type!=Ocean.FISH) 
			{ 
			insertionnode.next = removednode.next;
			insertionnode.next.prev = insertionnode;
			insertionnode.prev = removednode.prev;
			insertionnode.prev.next = insertionnode;
			break actualinsertion;
			} 
			if (removednode.next.type==Ocean.FISH && removednode.prev.type==Ocean.FISH) 
			{ 
			int combination = removednode.next.foll + removednode.prev.foll + 1;
			DListNode overhaul = new DListNode(Ocean.FISH);
			overhaul.foll = combination;
			overhaul.next = removednode.next.next;
			overhaul.next.prev = overhaul;
			overhaul.prev = removednode.prev.prev;
			overhaul.prev.next = overhaul;
			temp2.size = temp2.size -2;
			countingRun = temp2.length();
			break actualinsertion;
			} 
			if (nodeisgone) 
			{ 
			int combined = after + before;
				if (combined > 0) 
				{
				DListNode superduper = new DListNode(Ocean.EMPTY);
				superduper.foll = combined;
				superduper.next = removednode.next;
				superduper.prev = removednode.prev;
				superduper.prev.next = superduper;
				superduper.next.prev = superduper;
				} 
				else 
				{
				removednode.prev.next = removednode.next;
				removednode.next.prev = removednode.prev;
				temp2.size--;
				}
			}  
			else if (before>0 || after>0) 
			{
				if (before > 0) 
				{
				beforenode = new DListNode(Ocean.EMPTY);
				beforenode.foll = before;
				beforenode.prev = removednode.prev;
				removednode.prev.next = beforenode;
				beforenode.next = insertionnode;
				insertionnode.prev = beforenode;
					if (after == 0) 
					{
					insertionnode.next = removednode.next;
					insertionnode.next.prev = insertionnode; 
					}
				temp2.size++;
				countingRun = temp2.length();
				} 
				if (after > 0) 
				{
				afternode = new DListNode(Ocean.EMPTY);
				afternode.foll = after;
				afternode.next = removednode.next;
				afternode.next.prev = afternode;
				insertionnode.next = afternode;
					if (before == 0) 
					{
					insertionnode.prev = removednode.prev;
					insertionnode.prev.next = insertionnode; 
					}
				afternode.prev = insertionnode;
				temp2.size++;
				countingRun = temp2.length();
				}
			}
		}
	check();
	}
	
	/**
	 *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
	 *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
	 *  just eaten.  If the cell is already occupied, leave the cell as it is.
	 *  The final run-length encoding should be compressed as much as possible;
	 *  there should not be two consecutive runs of sharks with the same degree
	 *  of hunger.
	 *  @param x is the x-coordinate of the cell to place a shark in.
	 *  @param y is the y-coordinate of the cell to place a shark in.
	 */
	
	public void addShark(int x, int y) 
	{
	int index = itempos(x, y);
	int temp2Spot = 1;
	int i = 1;
		for (; i < temp2.length(); i++) 
		{
		temp2Spot = temp2Spot + temp2.nth(i).foll;
			if (temp2Spot > index) 
			{ 
			temp2Spot = temp2Spot - temp2.nth(i).foll - 1;
			break; 
			}
		}
		if (i >= temp2.length()) 
		{ 
		System.out.println("Error detected: explosion imminent"); 
		}
		else if (temp2.nth(i).type != Ocean.EMPTY) 
		{ 
		System.out.println("Occupied cell"); 
		}
		else actualinsertion: 
		{ 
		DListNode removednode = temp2.nth(i);
		int destinationhunger = temp2.starvetime;
		int after = removednode.foll - (index - temp2Spot);
		int before = removednode.foll - after - 1;
		DListNode insertionnode = new DListNode(Ocean.SHARK, destinationhunger);
		insertionnode.foll = 1;
		DListNode beforenode;
		DListNode afternode;
		boolean nodeisgone = false;
			if (after==0 && removednode.next.type==Ocean.SHARK && removednode.next.hunger==destinationhunger && removednode.prev.hunger!=destinationhunger) 
			{ 
			removednode.next.foll++;
			nodeisgone = true;
			}  
			if (before==0 && removednode.prev.type==Ocean.SHARK && removednode.prev.hunger==destinationhunger && removednode.next.hunger!=destinationhunger) 
			{
			removednode.prev.foll++;
			nodeisgone = true;
			} 
			if (after==0 && before==0 && removednode.prev.hunger!=destinationhunger && removednode.next.hunger!=destinationhunger) 
			{ 
			insertionnode.next = removednode.next;
			insertionnode.next.prev = insertionnode;
			insertionnode.prev = removednode.prev;
			insertionnode.prev.next = insertionnode;
			break actualinsertion;
			} 
			if (after==0 && before==0 && removednode.next.type==Ocean.SHARK && removednode.prev.type==Ocean.SHARK && removednode.next.hunger==destinationhunger && removednode.prev.hunger==destinationhunger) 
			{ 
			int combination = removednode.next.foll + removednode.prev.foll + 1;
			DListNode overhaul = new DListNode(Ocean.SHARK, destinationhunger);
			overhaul.foll = combination;
			overhaul.next = removednode.next.next;
			overhaul.next.prev = overhaul;
			overhaul.prev = removednode.prev.prev;
			overhaul.prev.next = overhaul;
			temp2.size = temp2.size -2;
			countingRun = temp2.length();
			break actualinsertion;
			} 
			if (nodeisgone) 
			{ 
			int combined = after + before;
				if (combined > 0) 
				{
				DListNode superduper = new DListNode(Ocean.EMPTY);
				superduper.foll = combined;
				superduper.next = removednode.next;
				superduper.prev = removednode.prev;
				superduper.prev.next = superduper;
				superduper.next.prev = superduper;
				} 
				else 
				{
				removednode.prev.next = removednode.next;
				removednode.next.prev = removednode.prev;
				temp2.size--;
				}
			} 
			else if (before>0 || after>0) 
			{
				if (before > 0) 
				{ 
				beforenode = new DListNode(Ocean.EMPTY);
				beforenode.foll = before;
				beforenode.prev = removednode.prev;
				removednode.prev.next = beforenode;
				beforenode.next = insertionnode;
				insertionnode.prev = beforenode;
					if (after == 0) 
					{
					insertionnode.next = removednode.next;
					insertionnode.next.prev = insertionnode; 
					}
				temp2.size++;
				countingRun = temp2.length();
				} 
				if (after > 0) 
				{ 
				afternode = new DListNode(Ocean.EMPTY);
				afternode.foll = after;
				afternode.next = removednode.next;
				afternode.next.prev = afternode;
				insertionnode.next = afternode;
					if (before == 0) 
					{
					insertionnode.prev = removednode.prev;
					insertionnode.prev.next = insertionnode; }
					afternode.prev = insertionnode;
					temp2.size++;
					countingRun = temp2.length();
				}
			} 
		}
	check();
	}
	
	/**
	 *  check() walks through the run-length encoding and prints an error message
	 *  if two consecutive runs have the same contents, or if the sum of all run
	 *  lengths does not equal the number of cells in the ocean.
	 */
	
	public void check()
	{
	int ti = 0;
		for (int i=1; i<temp2.length(); i++) 
		{
			if (temp2.nth(i).type == temp2.nth(i+1).type && temp2.nth(i).hunger == temp2.nth(i+1).hunger) 
			{
			System.out.print("Error detected, fix problem NOW, you have 15 seconds to comply");
			}
		}
		for (int ii=1; ii <= temp2.length(); ii++) 
		{
		ti = ti + temp2.nth(ii).foll;
		}	
		if (ti != temp2.dimx * temp2.dimy) 
		{
		System.out.print("Fatal Error Detected: EXECTUTING PROTOCOL ExterminateAllHumans.exe");
		}
	}
	
}

