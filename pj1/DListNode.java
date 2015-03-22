/* DListNode.java */


class DListNode {
	
	public DListNode next;
	public DListNode prev;

	int type;
	int hunger;
	int foll;
	
	
	DListNode(int type, int hunger, DListNode next) 
	{
	this.type = type;
	this.hunger = hunger;
	this.next = next;
	}
	
	DListNode(int type, DListNode next) 
	{
	this(type, 0, next);
	}
	
	DListNode(int type, int hunger)
	{
	this(type, hunger, null);
	}
		
	DListNode(int type) 
	{
	this(type, null);
	}

	DListNode() 
	{
	this(Ocean.EMPTY, null);
	}
}
