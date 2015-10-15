package jason.smythe.uct.csc2003.entities;

import jason.smythe.uct.csc2003.path_finding.AStarNode;
import jason.smythe.uct.csc2003.states.EnemyState;
import jason.smythe.uct.csc2003.states.EntityState;
import jason.smythe.uct.csc2003.util.Square;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class EnemyGuard extends Enemy{

	Player player;
	boolean useAlturnateTarget;
	private EnemyState state;
	
	public EnemyGuard(Sprite sprite, EnemyState enemyState, int startX, int startY, EntityState playerState, boolean b, Player player) {
		super(sprite, enemyState, startX, startY, playerState, b);
		
		state = enemyState;
		useAlturnateTarget = false;
		
		this.player = player;
	}
	
	public void draw(SpriteBatch batch){
		super.draw(batch);
	}
	
	public Vector2 drawPath(ShapeRenderer shapeRenderer, int i, boolean draw) {
		Vector2 end = super.drawPath(shapeRenderer, i, false);
		if (useAlturnateTarget) {
			shapeRenderer.setAutoShapeType(true);
			shapeRenderer.set(ShapeType.Line);
			shapeRenderer.setColor(Color.BLUE);
			shapeRenderer.circle(end.x, end.y, 4);
			shapeRenderer.circle(end.x, end.y, 6);
			shapeRenderer.circle(end.x, end.y, 8);
			shapeRenderer.circle(end.x, end.y, 10);
			shapeRenderer.circle(end.x, end.y, 12);
			shapeRenderer.circle(end.x, end.y, 14);
			shapeRenderer.circle(end.x, end.y, 16);
			shapeRenderer.setColor(Color.WHITE);
			shapeRenderer.circle(player.midPointPos.x, player.midPointPos.y, 4);
			shapeRenderer.circle(player.midPointPos.x, player.midPointPos.y, 6);
			shapeRenderer.circle(player.midPointPos.x, player.midPointPos.y, 8);
			shapeRenderer.circle(player.midPointPos.x, player.midPointPos.y, 10);
			shapeRenderer.circle(player.midPointPos.x, player.midPointPos.y, 12);
			shapeRenderer.circle(player.midPointPos.x, player.midPointPos.y, 14);
			shapeRenderer.circle(player.midPointPos.x, player.midPointPos.y, 16);
			shapeRenderer.line(player.goalPosition, playerState.position);
			
		} else {
			shapeRenderer.rectLine(end, playerState.position, i);
		}
		
		return null;
	}
	
//	// a modified getTargetNode class, this 
	protected AStarNode getTargetNode() {
		if (playerState.position.dst(state.position) < 250 && playerState.position.dst(state.position) > 150) {
//		if (true){
			useAlturnateTarget = true;
			Square midPoint = Square.getSquare(player.midPointPos.x + Square.xOffset, player.midPointPos.y + Square.yOffset);
			return maps.gridSystem.mapGrid[midPoint.x][midPoint.y];
		} else {
			useAlturnateTarget = false;
			return maps.gridSystem.mapGrid[playerState.nextSquare.x][playerState.nextSquare.y];
		}
	}
}
