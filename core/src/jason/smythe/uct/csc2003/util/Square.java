package jason.smythe.uct.csc2003.util;

import com.badlogic.gdx.math.Vector2;

public class Square {
	public int x;
	public int y;
	public static int xOffset;
	public static int yOffset;
	
	public Square(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update(Square square, int i, int j) {
		this.x = square.x + i;
		this.y = square.y + j;
	}
	
	public Vector2 squareToPos() {		
		return new Vector2((float) (x * 50) + 5, (float) (y * 50) + 5);
	}
	
//	public Vector2 squareToCenterPos() {		
//		return new Vector2((float) (x * 50), (float) (y * 50));
//	}
	
	public String toString(){
		return "(x: " + x + " , y: " + y + " )";
	}

	public static Square getSquare(int xP, int yP) {
		return new Square((int) ((xP - xOffset) / 50), (int) ((yP - yOffset) / 50));
	}

	public static Square getSquare(float f, float g) {
		return new Square((int) ((f - xOffset) / 50), (int) ((g - yOffset) / 50));
	}
	
	public static void setOffset(int i, int j) {
		xOffset = i;
		yOffset = j;
	}

	public void transferValues(Square otherSquare) {
		this.x = otherSquare.x;
		this.y = otherSquare.y;
	}
}