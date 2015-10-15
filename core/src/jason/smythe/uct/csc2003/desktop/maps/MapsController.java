package jason.smythe.uct.csc2003.desktop.maps;

import jason.smythe.uct.csc2003.util.Square;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class MapsController {
	public Map currentMap;
	public MapGrid gridSystem;
	
	// this class will be elaborated when many maps and levels are used.
	
	public MapsController(String mapToLoad){
		
		TmxMapLoader loader = new TmxMapLoader();
		
		TiledMap map = loader.load(mapToLoad);
		
		currentMap = new Map(map);

		gridSystem = new MapGrid(currentMap);
		
//		gridSystem.printMapGrid();
	}

//	public Map getCurrentMap() {
//		return currentMap;
//	}
//	
//	public MapGrid getCurrentGrid() {
//		return gridSystem;
//	}
	

//	public boolean hasSquareProp(int x, int y, String squareProp1, String squareProp2) {
////		System.out.println("Has square prop... " + x + " , " + y);
//		return ((TiledMapTileLayer) currentMap.layers.get(0)).getCell(x, y).getTile().getProperties().containsKey(squareProp1) 
//				|| ((TiledMapTileLayer) currentMap.layers.get(0)).getCell(x, y).getTile().getProperties().containsKey(squareProp2);
//	}

	public void dispose() {
		currentMap.dispose();
	}

	public void setPlayerPositionVariable(Square playerPos) {
		gridSystem.playerPosition = playerPos;
//		System.out.println("Setting player position!" + playerPos);
//		System.out.println("Player position:" + gridSystem.playerPosition);
	}
}
