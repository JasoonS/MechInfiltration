package jason.smythe.uct.csc2003.screens;

import jason.smythe.uct.csc2003.controllers.PlayController;
import jason.smythe.uct.csc2003.desktop.maps.MapsController;
import jason.smythe.uct.csc2003.path_finding.AStarNode;
import jason.smythe.uct.csc2003.states.GearState;
import jason.smythe.uct.csc2003.util.GameSoundController;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PathFindingSandbox implements Screen {

	MapsController maps;
	GearState gearState;
	GameSoundController sound;
	PlayController playController;
	SpriteBatch batch;
	Texture background;
	TextButton bread1, bread2, bread3, bread4, bread5, bread6, bread7, bread8, reset, stopMovement, everythingMoves, menu;
	TextButtonStyle textButtonStyle;
	Stage stage;
	InputMultiplexer inputMultiplexer;
	
	public PathFindingSandbox(GameSoundController sound, String mapToLoad, boolean specialDemo, TextButtonStyle textButtonStyle) {
		this.sound = sound;
		playController = new PlayController(sound, mapToLoad, specialDemo);
		batch = new SpriteBatch();
		background = new Texture("img/plainBackground.png");
		this.textButtonStyle = textButtonStyle;
		stage = new Stage();
		
		inputMultiplexer = new InputMultiplexer();
	}

	@Override
	public void show() {
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(playController.player);
		
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		maps = playController.maps;
		
		maps.setPlayerPositionVariable(playController.player.state.curSquare);
		
		playController.show();
		
		playController.calculateAllPaths();
		
		playController.sandboxMode = true;
		
		gearState = new GearState(17, 13, maps);
		
		playController.updateGearState(gearState);
		
		bread1 = new TextButton("set square breadcrumb to: 0", textButtonStyle);
		bread1.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				AStarNode.breadCrumbWeightIncrement = (float) 0;
			}
		});
		bread1.pad(15);
		stage.addActor(bread1);
		bread1.setPosition(880,50);
		
		bread2 = new TextButton("set square breadcrumb to: 0.1", textButtonStyle);
		bread2.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				AStarNode.breadCrumbWeightIncrement = (float) 0.1;
			}
		});
		bread2.pad(15);
		stage.addActor(bread2);
		bread2.setPosition(880,100);
		
		bread3 = new TextButton("set square breadcrumb to: 0.2", textButtonStyle);
		bread3.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				AStarNode.breadCrumbWeightIncrement = (float) 0.2;
			}
		});
		bread3.pad(15);
		stage.addActor(bread3);
		bread3.setPosition(880,150);
		
		bread4 = new TextButton("set square breadcrumb to: 0.3", textButtonStyle);
		bread4.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				AStarNode.breadCrumbWeightIncrement = (float) 0.3;
			}
		});
		bread4.pad(15);
		stage.addActor(bread4);
		bread4.setPosition(880,200);
		
		bread5 = new TextButton("set square breadcrumb to: 0.4", textButtonStyle);
		bread5.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				AStarNode.breadCrumbWeightIncrement = (float) 0.4;
			}
		});
		bread5.pad(15);
		stage.addActor(bread5);
		bread5.setPosition(880,250);
		
		bread6 = new TextButton("set square breadcrumb to: 0.5", textButtonStyle);
		bread6.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				AStarNode.breadCrumbWeightIncrement = (float) 0.5;
			}
		});
		bread6.pad(15);
		stage.addActor(bread6);
		bread6.setPosition(880,300);
		
		bread7 = new TextButton("set square breadcrumb to: 0.8", textButtonStyle);
		bread7.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				AStarNode.breadCrumbWeightIncrement = (float) 0.8;
			}
		});
		bread7.pad(15);
		stage.addActor(bread7);
		bread7.setPosition(880,350);
		
		bread8 = new TextButton("set square breadcrumb to: 1.1", textButtonStyle);
		bread8.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				AStarNode.breadCrumbWeightIncrement = (float) 1.1;
			}
		});
		bread8.pad(15);
		stage.addActor(bread8);
		bread8.setPosition(880,400);
		
		everythingMoves = new TextButton("Set everything into motion", textButtonStyle);
		everythingMoves.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				allowPlayerMovement();
			}
		});
		everythingMoves.pad(15);
		stage.addActor(everythingMoves);
		everythingMoves.setPosition(900,460);
		
		stopMovement = new TextButton("Stop All Movement", textButtonStyle);
		stopMovement.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stopPlayerMovement();
			}
		});
		stopMovement.pad(15);
		stage.addActor(stopMovement);
		stopMovement.setPosition(900,510);
		
		reset = new TextButton("Reset Everything", textButtonStyle);
		reset.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				playController.resetPlayer();
				playController.resetEnemies();
				
				inputMultiplexer.clear();
				
				inputMultiplexer.addProcessor(stage);
				inputMultiplexer.addProcessor(playController.player);
			}
		});
		reset.pad(15);
		stage.addActor(reset);
		reset.setPosition(900,560);
		
		menu = new TextButton("GO BACK TO MAIN MENU", textButtonStyle);
		menu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(sound));
			}
		});
		menu.pad(15);
		stage.addActor(menu);
		menu.setPosition(880,620);
	}

	protected void allowPlayerMovement() {
		playController.activateEnemies();
	}

	protected void stopPlayerMovement() {
		playController.stopEntities();
	}
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0);

		bread5.draw(batch, 1);
		bread6.draw(batch, 1);
		bread7.draw(batch, 1);
		bread8.draw(batch, 1);
		
		batch.end();
		
		stage.act(delta);
		stage.draw();
		playController.render();
		
		playController.calculateAllPaths();
		
		playController.drawEnemyLines();
	}

	@Override
	public void resize(int width, int height) {
		playController.resize(width, height);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
//		playController.dispose();
	}
}
