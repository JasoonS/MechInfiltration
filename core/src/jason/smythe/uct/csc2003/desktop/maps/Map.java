package jason.smythe.uct.csc2003.desktop.maps;

import jason.smythe.uct.csc2003.constants.SquareType;
import jason.smythe.uct.csc2003.util.Square;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Map {

	public TiledMap map;
	public MapLayers layers;
	
	public Map(TiledMap map) {
		this.map = map;
		layers = this.map.getLayers();
	}
	
	public SquareType getSquareType(Square square) {
		
		switch(((TiledMapTileLayer) layers.get(0)).getCell(square.x, square.y).getTile().getId()) {
		case 1:
			return SquareType.BLR;
		case 2:
			return SquareType.BLT;
		case 3:
			return SquareType.BLTR;
		case 4:
			return SquareType.BL;	
		}
		return null;
	}

	public void dispose() {
		map.dispose();
	}

	public boolean hasGear() {
		
		return false;
	}
}
