public class Wall {
	protected int x;
	protected int y;
	protected int kind;
	
	public final static int V = 0;
	public final static int H = 1;
	
	public Wall(int i, int j, int kin) {
		x = i;
		y = j;
		kind = kin;
	}
}
