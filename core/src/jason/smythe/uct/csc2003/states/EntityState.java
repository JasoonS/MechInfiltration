package jason.smythe.uct.csc2003.states;

import jason.smythe.uct.csc2003.constants.Movement;
import jason.smythe.uct.csc2003.constants.SquareType;
import jason.smythe.uct.csc2003.desktop.maps.MapsController;
import jason.smythe.uct.csc2003.util.Square;

import com.badlogic.gdx.math.Vector2;

public class EntityState {
	public Vector2 position;
	public Vector2 nextPosition = new Vector2();
	public Square curSquare;
	public Square nextSquare;
	public Movement moveState = Movement.STATIONARY;
	public Movement nextMoveState = Movement.STATIONARY;
	public MapsController maps;
	
	public EntityState(MapsController maps) {
		this.maps = maps; 
	}

	void getNextMovement() {
		moveState = nextMoveState;
		curSquare = nextSquare;
	}

	public boolean atNextBlock() {
		
		if (position.dst2(nextPosition) < 3) return true;
		
		else return false;
	}

	public boolean movement(Movement direction) {
		boolean wasPossible = false;
		
		// stationary check for enemy is unnecessary.
		if (moveState == Movement.STATIONARY) {
			if ( canMoveInDirection(direction, curSquare) ) {
//				System.out.println("CAN move in the " + direction + "direction. NOW MOVING.");
				setNextSquareAndPos(direction);
				updateMovement(direction);
				wasPossible = true;
			} else {
//				System.out.println("can NOT move in the " + direction + "direction.");
			}
		} else {
//			System.out.println("In motion change");
			if (isOppositeDirection(direction)){
				if (canMoveInDirection(direction, curSquare)){
					setNextSquareAndPos(direction);
					updateMovement(direction);
					wasPossible = true;
				} else {
					nextSquare.update(curSquare, 0, 0);
//					System.out.println("nextX: " + nextSquare.x + " nextY: " + nextSquare.y);
					nextPosition = nextSquare.squareToPos();
					updateMovement(direction);
				}
			} else {
//				System.out.println("the current square: "+ curSquare + " , and the next square: " + nextSquare);
				if (canMoveInDirection(direction, nextSquare)){
//					System.out.println("can move in direction, changing nextMoveState");
					nextMoveState = direction;
					wasPossible = true;
				} else {
					//resets next move state to default
					nextMoveState = moveState;
				}
			}
		}

		return wasPossible;
	}

	private boolean isOppositeDirection(Movement direction) {
//		System.out.println("isOppositeDirection?");
		switch(direction) {
		case UP:
			if (moveState == Movement.DOWN) return true;
			break;
		case DOWN:
			if (moveState == Movement.UP) return true;
			break;
		case LEFT:
			if (moveState == Movement.RIGHT) return true;
			break;
		case RIGHT:
			if (moveState == Movement.LEFT) return true;
			break;
		default:
			break;
		}
		return false;
	}

	public void setNextSquareAndPos(Movement direction) {
		int nextX = 0;
		int nextY = 0;
		switch(direction) {
		case UP:
			nextY = 1;
			break;
		case DOWN:
			nextY = -1;
			break;
		case LEFT:
			nextX = -1;
			break;
		case RIGHT:
			nextX = 1;
			break;
		default:
			break;
		}
		nextSquare.update(curSquare, nextX, nextY);
		nextPosition = nextSquare.squareToPos();
	}

	private boolean canMoveInDirection(Movement direction, Square curSquare) {
		
		switch(direction) {
		case UP:
			if (maps.gridSystem.mapGrid[curSquare.x][curSquare.y].squareType == SquareType.BLT 
				|| maps.gridSystem.mapGrid[curSquare.x][curSquare.y].squareType == SquareType.BLTR)
				return true;
			break;
		case DOWN:
			if ( (curSquare.y > 0) 
					&& (maps.gridSystem.mapGrid[curSquare.x][curSquare.y - 1].squareType == SquareType.BLT
						|| maps.gridSystem.mapGrid[curSquare.x][curSquare.y - 1].squareType == SquareType.BLTR))
				return true;
			break;
		case LEFT:
			if ( (curSquare.x > 0) 
					&& (maps.gridSystem.mapGrid[curSquare.x - 1][curSquare.y].squareType == SquareType.BLR
						|| maps.gridSystem.mapGrid[curSquare.x - 1][curSquare.y].squareType == SquareType.BLTR))
				return true;
			break;
		case RIGHT:
			if ( maps.gridSystem.mapGrid[curSquare.x][curSquare.y].squareType == SquareType.BLR
					|| maps.gridSystem.mapGrid[curSquare.x][curSquare.y].squareType == SquareType.BLTR)
				return true;
			break;
		default:
			break;
		}
		
		return false;
	}
	

//	private boolean hasSquareProp(int x, int y, String squareProp1, String squareProp2) {
//		return ((TiledMapTileLayer) maze.get("grid")).getCell(x, y).getTile().getProperties().containsKey(squareProp1) || ((TiledMapTileLayer) maze.get("grid")).getCell(x, y).getTile().getProperties().containsKey(squareProp2);
//	}

	public void move(float speed, float deltaTime) {
		switch(moveState){
		case STATIONARY:
			break;
		case UP:
			position.y += speed * deltaTime;
			break;
		case DOWN:
			position.y -= speed * deltaTime;
			break;
		case LEFT:
			position.x -= speed * deltaTime;
			break;
		case RIGHT:
			position.x += speed * deltaTime;
			break;
		default:
			break;
		}
	}
	
	public void updateState() {
//		printState("Before STATE UPDATE ");
		//Insert gear checks! Only for player.
		
		curSquare.transferValues(nextSquare);
		position = nextPosition;
		
		
		if ( canMoveInDirection(nextMoveState, curSquare) ) {
			setNextSquareAndPos(nextMoveState);
			updateMovement(nextMoveState);
		} else {
			updateMovement(Movement.STATIONARY);
		}
//		
//		printState("After STATE UPDATE ");
	}
	
//	protected void stop() {
//		updateMovement(Movement.STATIONARY);
//		inMotion = false;
//	}
	
	protected void updateMovement(Movement movement) {
		moveState = movement;
		nextMoveState = movement;
	}
	
	public void printState(String string) {
		System.out.println(string + "********Player***********");
		System.out.println("CurSquare: " + curSquare + "; NextSquare: " + nextSquare);
		System.out.println("CurPosition: " + position + "; NextPosition: " + nextPosition);
		System.out.println("CurState: " + moveState + "; NextState: " + nextMoveState);
	}

	public boolean isOnGear() {
//		printState("Checking if on gear: ");
		switch(GearState.gearBelts[curSquare.x][curSquare.y]) {
		case START_R: 
			return true;
		case START_L: 
			return true;
		case END_R: 
			return true;
		case END_L:
			return true;
		default:
			return false;
		}
	}
}
