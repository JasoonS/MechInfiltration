package jason.smythe.uct.csc2003.states;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import jason.smythe.uct.csc2003.constants.GearBelts;
import jason.smythe.uct.csc2003.desktop.maps.MapsController;
import jason.smythe.uct.csc2003.util.Square;

public class GearState {

	
	// TODO: make static and non-static components consistent.
	MapsController maps;
	static public GearBelts[][] gearBelts;
	public int x;
	public int y;
	public ArrayList<Square> highlighted;
	private Texture rightBelt;
	private Texture leftBelt;
	
//	public GearState(MapsCollection maps) {
//		this.maps = maps;
//	}

	public GearState(int x, int y, MapsController maps) {
		this.maps = maps;
		
		this.x = x;
		this.y = y;
		
		gearBelts = new GearBelts[x][y];
		
		rightBelt = new Texture("img/gearBeltRight.png");
		leftBelt = new Texture("img/gearBeltLeft.png");
		
		resetAllSelections(true);
	}

	public void resetAllSelections(boolean overWrite) {
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				// TODO: Overwrite will become necessary in future versions of my code.
//				if(overWrite || gearBelts[i][j] == GearBelts.GEAR || gearBelts[i][j] == GearBelts.GEAR_SELECTED){
					if ((i%2) == 1 && (j%2) == 1) {
						gearBelts[i][j] = GearBelts.GEAR;
					} else {
						gearBelts[i][j] = GearBelts.NOTHING;
					}
//				}
			}
		}
	}

	public void printGearBelts(SpriteBatch batch) {
		for (int i = 1; i < x; i += 2) {
			for (int j = 1; j < y; j += 2) {
				switch(gearBelts[i][j]){
					case START_R:
						batch.draw(rightBelt, i * 50 - 3, j * 50 - 3);
						break;
					case END_L:
						batch.draw(leftBelt, i * 50 - 3, (j - 2) * 50 - 3);
						break;
				default:
					break;
				}
			}
		}
	}

	public void setNewGearBelt(Square square, Square compare) {
		if (square.x > compare.x){
			if (square.y > compare.y){
				gearBelts[square.x][square.y] = GearBelts.END_R;
				gearBelts[compare.x][compare.y] = GearBelts.START_R;
			} else {
				gearBelts[square.x][square.y] = GearBelts.START_L;
				gearBelts[compare.x][compare.y] = GearBelts.END_L;
			}
		} else {
			if (square.y > compare.y){
				gearBelts[square.x][square.y] = GearBelts.END_L;
				gearBelts[compare.x][compare.y] = GearBelts.START_L;
			} else {
				gearBelts[square.x][square.y] = GearBelts.START_R;
				gearBelts[compare.x][compare.y] = GearBelts.END_R;
			}
		}
	}
}
