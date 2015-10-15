package jason.smythe.uct.csc2003.entities;

import jason.smythe.uct.csc2003.path_finding.AStarNode;
import jason.smythe.uct.csc2003.states.AStarSquareResultState;
import jason.smythe.uct.csc2003.states.EnemyState;
import jason.smythe.uct.csc2003.states.EntityState;
import jason.smythe.uct.csc2003.states.GearState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity {
	
	GearState gearState;
	protected EntityState playerState; //reference to the players state for the sake of collision deteciton.
	private boolean updater;
	public EnemyState state;
//	private final int enemyNumber;
//	private int counter;
	
	
	//TODO: remove enemyNumber
	//TODO: add offset to the enemyNodes!
	public Enemy(Sprite sprite, EnemyState enemyState, int startX, int startY, EntityState playerState, boolean b) {
		super(sprite, enemyState, startX, startY);
		state = enemyState;
		this.playerState = playerState; 
		this.updater = b;
	}

	public void draw(SpriteBatch batch){
		//if the update fails, ie if the game ends and the enemy captures the player
		super.draw(batch);
		
		update(Gdx.graphics.getDeltaTime());
	}

	public Vector2 drawPath(ShapeRenderer shapeRenderer, int i, boolean drawEnd) {
		Vector2 previousCentre = state.position;
		for (AStarSquareResultState pathNode: state.path) {
			shapeRenderer.rectLine(previousCentre, pathNode.centre, i);
			previousCentre = pathNode.centre;
		}
		
		if (drawEnd) shapeRenderer.rectLine(previousCentre, playerState.position, i);
		
		return previousCentre;
	}
	
	protected void update(float deltaTime) {
		if ( state.atNextBlock() ){
			changeCalculatePathAndChangeStates();
		} else { 
			state.move(speed, deltaTime);
		}
		this.setPosition(state.position.x, state.position.y);
	}

	public void changeCalculatePathAndChangeStates() {
		state.printState("Before: ");
		state.updateState();
		calculatePath();
		state.printState("updateState: ");
		state.position = state.curSquare.squareToPos();
		setEnemyInMotion();
		state.printState("setEnemyInMotion(End): ");
	}

	public boolean hasEnemyCaughtMech() {
		// this provides a good enough approximation as to whether the player has been caught.
		return (state.position.dst2(playerState.position) < 48 
				|| state.position.dst2(playerState.nextPosition) < 48
				|| state.nextPosition.dst2(playerState.position) < 48
				|| state.nextPosition.dst2(playerState.nextPosition) < 48);
	}

	public void setEnemyInMotion() {
			if (state.path.length == 0) return;
			
			if (state.path[0].movement == state.moveState) return;
			//test to see if a move is possible then makes a move.
			state.movement(state.path[0].movement);
	}
	
	public void calculatePath() {
		if (updater) maps.gridSystem.clearAllBreadCrumbs();
		AStarNode enemy = getThisEnemyNode();
		AStarNode player = getTargetNode();
//		System.out.println("THE ENEMY IS: " + enemy);
		state.path = maps.gridSystem.findShortestPath(enemy, player);
	}

	private AStarNode getThisEnemyNode() {
		return maps.gridSystem.mapGrid[state.curSquare.x][state.curSquare.y];
	}

	protected AStarNode getTargetNode() {
		return maps.gridSystem.mapGrid[playerState.nextSquare.x][playerState.nextSquare.y];

	}
}
