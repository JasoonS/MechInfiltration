package jason.smythe.uct.csc2003.screens;

import jason.smythe.uct.csc2003.controllers.GearsController;
import jason.smythe.uct.csc2003.controllers.PlayController;
import jason.smythe.uct.csc2003.desktop.maps.MapsController;
import jason.smythe.uct.csc2003.states.GearState;
import jason.smythe.uct.csc2003.util.GameSoundController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GearBeltSetup implements Screen {

	MapsController maps;
	GearState gearState;
	GearsController gearsController;
	GameSoundController sound;
	PlayController playController;
	SpriteBatch batch;
	Texture background;
	
	public GearBeltSetup(GameSoundController sound, String mapToLoad, boolean specialDemo) {
		this.sound = sound;
		playController = new PlayController(sound, mapToLoad, specialDemo);
		batch = new SpriteBatch();
		background = new Texture("img/gearSelectBackground.png");
	}
	
	public GearBeltSetup(GameSoundController sound, PlayController playController) {
		this.sound = sound;
		this.playController = playController;
		batch = new SpriteBatch();
		background = new Texture("img/gearSelectBackground.png");
	}

	@Override
	public void show() {
		maps = playController.maps;
		
		maps.setPlayerPositionVariable(playController.player.state.curSquare);
		
		playController.show();
		
		gearState = new GearState(17, 13, maps);
		gearsController = new GearsController(gearState, 3, sound, playController);
		
		playController.updateGearState(gearState);
		
		Gdx.input.setInputProcessor(gearsController);
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();
		
		playController.render();
		
		gearsController.printTempGears((SpriteBatch) playController.renderer.getBatch());
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
