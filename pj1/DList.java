/* DList.java */



public class DList 
{
	private DListNode head;
	int size;
	int dimx;
	int dimy;
	int starvetime;
	
	
	public DList() 
	{
	size = 0;
	head = new DListNode();
	head.next = head;
	head.prev = head;
	}
	
	
	public DList(int i, int j, int starve) 
	{
	size = 0;
	head = new DListNode();
	head.next = head;
	head.prev = head;
	dimx = i;
	dimy = j;
	starvetime = starve;
	}
	
	
	public boolean isEmpty() 
	{
	return size == 0;
	}
	
	
	public int length() 
	{
	return size;
	}
	
	public void insertFront(int type, int frequency, int hunger) 
	{
	DListNode temp = new DListNode(type);
	head.next.prev = temp;
	temp.next = head.next;
	temp.prev = head;
	head.next = temp;
	temp.foll = frequency;
	temp.hunger = hunger;
	size++;
	}
	
	
	public void insertEnd(int type, int frequency, int hunger) 
	{
	DListNode temp = new DListNode(type);
	head.prev.next = temp;
	temp.next = head;
	temp.prev = head.prev;
	head.prev = temp;
	temp.foll = frequency;
	temp.hunger = hunger;
	size++;
	}
	
	public void insertEnd(int type, int frequency) 
	{
	insertEnd(type, frequency, 0);
	}

	public void insertFront(int type, int frequency) 
	{
	insertFront(type, frequency, 0);
	}

	
	public DListNode nth(int position) 
	{
	DListNode currentNode;
		if ((position < 1) || (head.next == head)) 
		{
		return null;
		}
		else 
		{
		currentNode = head.next;
			while (position > 1) 
			{
			currentNode = currentNode.next;
				if (currentNode == head) 
				{
				return null;
				}
			position--;
			}
			return currentNode;
		}
	}
}
