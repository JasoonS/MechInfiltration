package jason.smythe.uct.csc2003.screens;

import jason.smythe.uct.csc2003.controllers.PlayController;
import jason.smythe.uct.csc2003.path_finding.AStarNode;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GridPlay implements Screen {

	private PlayController playController;
	SpriteBatch batch;
	Texture background;
	
	public GridPlay(PlayController playController) {
		super();
		this.playController = playController;
	}

	@Override
	public void show() {
		AStarNode.breadCrumbWeightIncrement = 1;
		
		Gdx.input.setInputProcessor(playController.player);
		playController.activateEnemies();
		
		batch = new SpriteBatch();
		background = new Texture("img/gearPlayBackground.png");
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();
		
		playController.render();
		
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
		
	}

	@Override
	public void dispose() {
		playController.disposeMe();
	}
}
