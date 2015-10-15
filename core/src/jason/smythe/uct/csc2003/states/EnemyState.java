package jason.smythe.uct.csc2003.states;

import jason.smythe.uct.csc2003.constants.Movement;
import jason.smythe.uct.csc2003.constants.SquareType;
import jason.smythe.uct.csc2003.desktop.maps.MapsController;
import jason.smythe.uct.csc2003.util.Square;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class EnemyState extends EntityState{
	public AStarSquareResultState[] path;
	
	public EnemyState(MapsController maps) {
		super(maps);
		
		path = new AStarSquareResultState[0];
	}
	
	public boolean movement(Movement direction) {
		setNextSquareAndPos(direction);
		updateMovement(direction);
		
		return true;
	}
}
