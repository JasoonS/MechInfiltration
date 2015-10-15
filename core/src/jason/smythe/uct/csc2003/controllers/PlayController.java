package jason.smythe.uct.csc2003.controllers;

import jason.smythe.uct.csc2003.constants.Movement;
import jason.smythe.uct.csc2003.desktop.maps.MapsController;
import jason.smythe.uct.csc2003.entities.Enemy;
import jason.smythe.uct.csc2003.entities.EnemyCautiousAttack;
import jason.smythe.uct.csc2003.entities.EnemyGuard;
import jason.smythe.uct.csc2003.entities.Player;
import jason.smythe.uct.csc2003.screens.YouLoseScreen;
import jason.smythe.uct.csc2003.screens.YouWinScreen;
import jason.smythe.uct.csc2003.states.EnemyState;
import jason.smythe.uct.csc2003.states.GearState;
import jason.smythe.uct.csc2003.util.GameSoundController;
import jason.smythe.uct.csc2003.util.Square;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class PlayController {
	public MapsController maps;
	public String mapName;
	public OrthogonalTiledMapRenderer renderer;
	OrthographicCamera camera;
	GearState gearState;
	GameSoundController sound;
	public boolean isSetup;
	public Player player;
	public Enemy[] enemies;
	private int frameCounter;
	ShapeRenderer shapeRenderer;
	public boolean specialDemo;
	public boolean sandboxMode;
	
	public PlayController(GameSoundController sound, String mapToLoad, boolean specialDemo) {
		mapName = mapToLoad;
		this.specialDemo = specialDemo; 
		
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        Square.setOffset(35, 29);
        
		this.sound = sound;
		
		maps = new MapsController(mapToLoad);
        
		renderer = new OrthogonalTiledMapRenderer(maps.currentMap.map);
		
		camera = new OrthographicCamera();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w - (Square.xOffset * 2), h - (Square.yOffset * 2));
        camera.update();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.translate(60, 50, 0);
        
        resetPlayer();
	}

	//TODO: REMOVE
	public void show() {
        resetEnemies();
	}
	
	public void calculateAllPaths() {
		for(Enemy enemy: enemies) {
			enemy.calculatePath();
		}
	}
	
	public void resetPlayer() {
		if (specialDemo) {
			//I pass in the PlayController and sound for the sake of the handling of the win condition.
			player = new Player(new Sprite(new Texture("img/player.png")), maps, 0, 6, 16, 6);
		} else {
			//I pass in the PlayController and sound for the sake of the handling of the win condition.
			player = new Player(new Sprite(new Texture("img/player.png")), maps, 8, 0, 8, 12);
		}
	}
	
	public void resetEnemies() {	
		if (specialDemo) {
			//I pass in the PlayController and sound for the sake of the handling of the lose condition.
			enemies = new Enemy[4];
			enemies[0] = new Enemy(new Sprite(new Texture("img/microchip.png")), new EnemyState(maps), 16, 5, player.state, true);
			enemies[1] = new Enemy(new Sprite(new Texture("img/microchip.png")), new EnemyState(maps), 16, 7, player.state, false);
			enemies[2] = new Enemy(new Sprite(new Texture("img/microchip.png")), new EnemyState(maps), 16, 8, player.state,	false);
			enemies[3] = new Enemy(new Sprite(new Texture("img/microchip.png")), new EnemyState(maps), 16, 4, player.state, false);
		} else {
			//I pass in the PlayController and sound for the sake of the handling of the lose condition.
			enemies = new Enemy[4];
			enemies[0] = new Enemy(new Sprite(new Texture("img/microchip.png")), new EnemyState(maps), 0, 0, player.state, true);
			enemies[1] = new Enemy(new Sprite(new Texture("img/microchip.png")), new EnemyState(maps), 16, 0, player.state, false);
			enemies[2] = new EnemyGuard(new Sprite(new Texture("img/shield.png")), new EnemyState(maps), 16, 7, player.state, false, player);
			enemies[3] = new EnemyCautiousAttack(new Sprite(new Texture("img/crossEnemy.png")), new EnemyState(maps), 0, 7, player.state, false, player);
		}
	}

	public boolean render() {
		frameCounter++;
		
		//check victory or loss condition every ten render cycles.
		if(frameCounter > 10) {
//			System.out.println("Player position:" + maps.gridSystem.playerPosition);
			for(Enemy enemy: enemies) {
				if ( enemy.hasEnemyCaughtMech() ) {
					if (sandboxMode) {
						resetEnemies();
						resetPlayer();
						return false;
					}
					((Game) Gdx.app.getApplicationListener()).setScreen(new YouLoseScreen(sound, this));
					return false;
				} else if ( player.playerHasGottenToKey() ) {
					if (sandboxMode) {
						resetEnemies();
						resetPlayer();
						return false;
					}
					((Game) Gdx.app.getApplicationListener()).setScreen(new YouWinScreen(sound, this));
					return false;
				}
			}
			
			frameCounter = 0;
		}
		
		//display the grid and moving characters.
		renderer.setView(camera);
		renderer.render();
		
		renderer.getBatch().begin();
		
		gearState.printGearBelts((SpriteBatch) renderer.getBatch());
		
		player.draw((SpriteBatch) renderer.getBatch());
		for(Enemy enemy: enemies) {
			enemy.draw((SpriteBatch) renderer.getBatch());
		}
		renderer.getBatch().end();
		return false;
	}

	public void disposeMe() {
		maps.dispose();
		renderer.dispose();
	}

	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
	}

	public void activateEnemies() {
		for(Enemy enemy: enemies) {
			enemy.changeCalculatePathAndChangeStates();
		}
	}

	public void updateGearState(GearState gearState) {
		this.gearState = gearState;
	}

	public void drawEnemyLines() {
		shapeRenderer.begin(ShapeType.Filled);
		float increment = (float) 1 /(float) enemies.length;
		float r = 1;
		float g = 0;
		float b;
		for(int i = 0; i < enemies.length; i++) {
			//this somewhat uniformly sets the colours.
			if(i%2 == 1){
				b = (float) 0.75;
				r += increment;
				g -= increment;
			}
			else {
				b = (float) 0.25;
				r -= 2*increment;
				g += 2*increment;
			}
			
			shapeRenderer.setColor(r ,g , b, 1);
			enemies[i].drawPath(shapeRenderer, 15 - 4*i, true);
		}
		shapeRenderer.end();
	}

	public void stopEntities() {
		player.state.moveState = Movement.STATIONARY;
		player.state.nextMoveState = Movement.STATIONARY;
		
		for(Enemy enemy: enemies) {
			enemy.state.moveState = Movement.STATIONARY;
			enemy.state.nextMoveState = Movement.STATIONARY;
		}
	}

}
