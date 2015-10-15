package jason.smythe.uct.csc2003.screens;

import jason.smythe.uct.csc2003.desktop.fader.SpriteAccessor;
import jason.smythe.uct.csc2003.util.GameSoundController;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Splash implements Screen {
	
	private SpriteBatch batch;
	private Sprite splash;
	private TweenManager tweenManager;
	private GameSoundController sound;
	private Texture masterMech;
	
	public Splash(GameSoundController sound) {
		this.sound = sound;
		
		masterMech = new Texture("img/masterMechanic.png");
	}

	@Override
	public void show() {
		splash = new Sprite(new Texture("img/splash.png"));
		batch = new SpriteBatch();
		tweenManager = new TweenManager();
		
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(splash, SpriteAccessor.ALPHA, 1).target(1).repeatYoyo(1, 2).setCallback(new TweenCallback() {

			@Override
			public void onEvent(int type, BaseTween<?> source) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(sound));				
			}
		}).start(tweenManager);
	
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		batch.begin();
		splash.draw(batch);
		batch.draw(masterMech, 900, 0);
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		
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
		batch.dispose();
		splash.getTexture().dispose();
	}
}
