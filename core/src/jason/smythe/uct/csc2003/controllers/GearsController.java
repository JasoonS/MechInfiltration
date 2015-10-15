package jason.smythe.uct.csc2003.controllers;

import java.util.ArrayList;

import jason.smythe.uct.csc2003.constants.GearBelts;
import jason.smythe.uct.csc2003.screens.ConfirmationScreen;
import jason.smythe.uct.csc2003.states.GearState;
import jason.smythe.uct.csc2003.util.GameSoundController;
import jason.smythe.uct.csc2003.util.Square;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GearsController implements InputProcessor {
	
	GearState gearState;
	private Texture gear;
	private Texture gearSelected;
	public ArrayList<Square> highlighted;
	public final int totalNumOfBelts;
	public int numOfBelts;
	private GameSoundController sound;
	private PlayController playController;
	
	public GearsController (GearState gearState, int totalNumOfBelts, GameSoundController sound, PlayController playController) {
		this.sound = sound;
		this.gearState = gearState;
		this.playController = playController;
		
		gear = new Texture("img/gear.png");
		gearSelected = new Texture("img/highlightedGear.png");
		
		this.totalNumOfBelts = totalNumOfBelts;
		numOfBelts = totalNumOfBelts;
		
		highlighted = new ArrayList<Square>();
	}
	
	//user clicks the screen, and selects a gear.
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// out of bounds of play.
		if ( screenX > 850 || screenY < 50 ) return false;
		makeGearTouch(screenX, Gdx.graphics.getHeight() - screenY);
		return true;
	}
	
	//check if gear at that position, then check if another gear has been touch, then check if suitable to connect the two gears.
	public void makeGearTouch(int x, int y) {
		Square square = Square.getSquare(x, y);
		
		if( GearState.gearBelts[square.x][square.y] == GearBelts.GEAR ) {
			if (highlighted.size() > 0) {
				GearState.gearBelts[square.x][square.y] = GearBelts.GEAR_SELECTED;
				Square compare = highlighted.get(0);
				if ( Math.abs(square.x - compare.x) == 2 && Math.abs(square.y - compare.y) == 2 && square.x != compare.x && square.y != compare.y ) {
					highlighted = new ArrayList<Square>();
					
					gearState.setNewGearBelt(square, compare);
					
					if((--numOfBelts) < 1) {
						((Game) Gdx.app.getApplicationListener()).setScreen(new ConfirmationScreen(sound, playController));
					}
				} else {
					GearState.gearBelts[compare.x][compare.y] = GearBelts.GEAR;
					highlighted = new ArrayList<Square>();
					GearState.gearBelts[square.x][square.y] = GearBelts.GEAR_SELECTED;
					highlighted.add(square);
				}
			} else {
				GearState.gearBelts[square.x][square.y] = GearBelts.GEAR_SELECTED;
				highlighted.add(square);
			}
		} else {
			if (highlighted.size() > 0) GearState.gearBelts[highlighted.get(0).x][highlighted.get(0).y] = GearBelts.GEAR;
			highlighted = new ArrayList<Square>();
		}
	}
	
	// display the temporary gears on the screen so players can see them.
	public void printTempGears(SpriteBatch batch) {
		batch.begin();
		for (int i = 1; i < gearState.x; i += 2) {
			for (int j = 1; j < gearState.y; j += 2) {
				switch(GearState.gearBelts[i][j]){
					case GEAR:
						batch.draw(gear, i * 50 + 7, j * 50 + 7);
						break;
					case GEAR_SELECTED:
						batch.draw(gearSelected, i * 50 + 7, j * 50 + 7);
						break;
				default:
					break;
				}
			}
		}
		batch.end();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
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
