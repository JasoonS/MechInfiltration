package jason.smythe.uct.csc2003.states;

import com.badlogic.gdx.math.Vector2;

import jason.smythe.uct.csc2003.constants.Movement;
import jason.smythe.uct.csc2003.util.Square;

public class AStarSquareResultState {
	public Square square;
	public Movement movement; //direction required to get to the square from the previous, not the direction you are moving from square to next.
	public Vector2 centre;
	
	public AStarSquareResultState(Square square, Movement movement){
		this.square = square;
		this.movement = movement;
		centre = square.squareToPos();
	}
	
	public String toString() {
		return square + ", Moved to in " + movement + " direction.";
	}
}
