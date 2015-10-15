package jason.smythe.uct.csc2003.screens;

import jason.smythe.uct.csc2003.controllers.PlayController;
import jason.smythe.uct.csc2003.desktop.maps.MapsController;
import jason.smythe.uct.csc2003.util.GameSoundController;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class YouLoseScreen implements Screen {

	private Stage stage;
	private Skin skin;
	private TextureAtlas atlas;
	private Table table;
	private TextButton replayButton, mainMenu, exitButton;
	private BitmapFont font; 
	private Label heading;
	private GameSoundController sound;
	private PlayController playController;
	private boolean disposePlayController = false;
	
	public YouLoseScreen(GameSoundController sound, PlayController playController) {
		super();
		this.sound = sound;
		this.playController = playController;
	}

	@Override
	public void show() {
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/black.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 20;
		font = generator.generateFont(parameter);
		generator.dispose();
		
		atlas = new TextureAtlas("ui/buttons.pack");
		skin = new Skin(atlas);

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("out");
		textButtonStyle.down = skin.getDrawable("pressed");
		textButtonStyle.font = font;
		
		heading = new Label("Oh no! The Mech world has been defeated!\nPlease try again.", new LabelStyle(font, Color.WHITE));
//		heading.setFontScale(2);
		
		replayButton = new TextButton("Yes, let me do it!", textButtonStyle);
		replayButton.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				playController.resetPlayer();
				playController.resetEnemies();
				//TODO: make sure that the new player and enemies are properly connected!
				((Game) Gdx.app.getApplicationListener()).setScreen(new GridPlay(playController));
			}
		});
		replayButton.pad(15);
		
		mainMenu = new TextButton("MAIN MENU", textButtonStyle);
		mainMenu.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				disposePlayController = true;
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(sound));
			}
		});
		mainMenu.pad(15);
		
		exitButton = new TextButton("Yes, but let me adjust my strategy!", textButtonStyle);
		exitButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				String oldMap = playController.mapName;
				boolean oldDemo = playController.specialDemo;
				disposePlayController = true;
				((Game) Gdx.app.getApplicationListener()).setScreen(new GearBeltSetup(sound, oldMap, oldDemo));
			}
		});
		exitButton.pad(15);
		
		
		table.add(heading).spaceBottom(100).row();
		table.add(replayButton).spaceBottom(10).row();
		table.add(exitButton).spaceBottom(10).row();
		table.add(mainMenu).spaceBottom(10).row();
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		
		stage.draw();
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
		stage.dispose();
		skin.dispose();
		atlas.dispose();
		font.dispose();
		if(disposePlayController) playController.disposeMe();
	}
}
