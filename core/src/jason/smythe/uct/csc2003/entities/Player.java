package jason.smythe.uct.csc2003.entities;

import jason.smythe.uct.csc2003.constants.Movement;
import jason.smythe.uct.csc2003.desktop.maps.MapsController;
import jason.smythe.uct.csc2003.states.GearState;
import jason.smythe.uct.csc2003.util.Square;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity implements InputProcessor {
	
	GearState gearState;
	private boolean isOnBelt;
	private Vector2 beltMovement;
	public Vector2 goalPosition;
	public Vector2 midPointPos;
	private Sprite goalKey;
	
	public Player(Sprite sprite, MapsController maps, int startX, int startY, int goalX, int goalY) {
		super(sprite, maps, startX, startY);
		
		isOnBelt = false;
		
		state.nextPosition = state.nextSquare.squareToPos();
		
		goalPosition = (new Square(goalX, goalY)).squareToPos();
		goalKey = new Sprite(new Texture("img/key.png"));
		goalKey.setPosition(goalPosition.x, goalPosition.y);
		
		midPointPos = new Vector2();
	}
	
	public void draw(SpriteBatch batch){
		super.draw(batch);
		goalKey.draw(batch);
		update(Gdx.graphics.getDeltaTime());
	}
	
	//Update the players state, and move the player.
	protected void update(float deltaTime) {
		if( isOnBelt ) {
			if ( state.atNextBlock() ){
				if ( state.isOnGear() ) {
					isOnBelt = false;
					state.position = state.curSquare.squareToPos();
//					state.printState("No longer on belt");
				}
			} else { 
				moveOnBelt(deltaTime);
			}
			this.setPosition(state.position.x, state.position.y);
		} else {
			if ( state.atNextBlock() ){
				state.updateState();
				state.position = state.curSquare.squareToPos();
				if ( state.isOnGear() ) {
					state.moveState = Movement.STATIONARY;
					state.nextMoveState = Movement.STATIONARY;
				}
			} else { 
				state.move(speed, deltaTime);
			}
			this.setPosition(state.position.x, state.position.y);
		}
		
		updateMidPointPos();
	}
	
	private void updateMidPointPos() {
		midPointPos.x = (state.position.x + goalPosition.x) / (float) 2; 
		midPointPos.y = (state.position.y + goalPosition.y) / (float) 2;

	}

	public boolean playerHasGottenToKey() {
		return (state.position.dst2(goalPosition) < 46);
	}

	private void moveOnBelt(float deltaTime) {
		state.position.add(beltMovement.x * deltaTime, beltMovement.y * deltaTime);
	}

	// keyboard input controller
	@Override
	public boolean keyDown(int keycode) {
//		System.out.println("KEY PRESSED" + keycode);
		switch(keycode){
		case Keys.UP:
			if (isOnBelt) {
				state.moveState = Movement.UP;
			} else {
				this.state.movement(Movement.UP);
			}
			break;
		case Keys.DOWN:
			if (isOnBelt) {
				state.moveState = Movement.DOWN;
			} else {
				this.state.movement(Movement.DOWN);
			}
			break;
		case Keys.LEFT:
			if (isOnBelt) {
				state.moveState = Movement.LEFT;
			} else {
				this.state.movement(Movement.LEFT);
			}
			break;
		case Keys.RIGHT:
			if (isOnBelt) {
				state.moveState = Movement.RIGHT;
			} else {
				this.state.movement(Movement.RIGHT);
			}
			break;
		case Keys.SPACE:
//			System.out.println("pressing space!!");
			if (state.isOnGear()) {
//				state.printState("Before");
				System.out.println("Is juming on Belt");
				jumpOnBelt();
//				state.printState("After");
			}
			break;
		}
		return true;
	}
	
	// sets player in the appropriate motion according to where he tries to get onto the belt.
	private void jumpOnBelt() {
		System.out.println("THE STATE ON THE BELT IS: " + GearState.gearBelts[state.curSquare.x][state.curSquare.y]);
		int nextX = 0;
		int nextY = 0;
		switch(GearState.gearBelts[state.curSquare.x][state.curSquare.y]) {
		case START_R: 
			beltMovement = new Vector2(100, 100);
			nextX = 2;
			nextY = 2;
			break;
		case START_L: 
			beltMovement = new Vector2( -100, 100);
			nextX = -2;
			nextY = 2;
			break;
		case END_R: 
			beltMovement = new Vector2(-100, -100);
			nextX = -2;
			nextY = -2;
			break;
		case END_L:
			beltMovement = new Vector2(100, -100);
			nextX = 2;
			nextY = -2;
			break;
		default:
			break;
		}
		state.nextSquare.update(state.curSquare, nextX, nextY);
		state.curSquare.transferValues(state.nextSquare);
		state.nextPosition = state.nextSquare.squareToPos();
		state.moveState = Movement.STATIONARY;
		state.nextMoveState = Movement.STATIONARY;
		isOnBelt = true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
