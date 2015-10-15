package jason.smythe.uct.csc2003.screens;

import jason.smythe.uct.csc2003.util.GameSoundController;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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

public class MainMenu implements Screen {

	private Stage stage;
	private Skin skin;
	private TextureAtlas atlas;
	private Table table;
	private TextButton playButton, exitButton, sandBoxButton, toggleSound, levelSelect;
	private BitmapFont font; 
	private Label heading;
	private GameSoundController sound;
	private Texture masterMech;
	
	public MainMenu(GameSoundController sound) {
		this.sound = sound;
		
		masterMech = new Texture("img/masterMechanic.png");
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/black.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 20;
		font = generator.generateFont(parameter);
		generator.dispose();
		
		stage = new Stage();
		
		atlas = new TextureAtlas("ui/buttons.pack");
		skin = new Skin(atlas);

		table = new Table(skin);
		
		heading = new Label("Mech Infiltration", new LabelStyle(font, Color.WHITE));
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		final TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("out");
		textButtonStyle.down = skin.getDrawable("pressed");
		textButtonStyle.font = font;
		
		heading.setFontScale(2);
		
		exitButton = new TextButton("EXIT", textButtonStyle);
		exitButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		exitButton.pad(15);
		
		sandBoxButton = new TextButton("PATH FINDING SAND BOX", textButtonStyle);
		sandBoxButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new PathFindingSandbox(sound, "maps/grid3.tmx", true, textButtonStyle));
			}
		});
		sandBoxButton.pad(15);
		
		levelSelect = new TextButton("SELECT LEVEL", textButtonStyle);
		levelSelect.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu(sound));
			}
		});
		levelSelect.pad(15);
		
		toggleSound = new TextButton("TOGGLE SOUND", textButtonStyle);
		toggleSound.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				sound.toggleGameSound();
			}
		});
		toggleSound.pad(15);
		
		playButton = new TextButton("PLAY", textButtonStyle);
		playButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new GearBeltSetup(sound, "maps/grid1.tmx", false));
			}
		});
		playButton.pad(15);
		
		table.add(heading).spaceBottom(100).row();
		table.add(playButton).spaceBottom(10).row();
		table.add(levelSelect).spaceBottom(10).row();
		table.add(sandBoxButton).spaceBottom(10).row();
		table.add(toggleSound).spaceBottom(10).row();
		table.add(exitButton).spaceBottom(10).row();
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		
		stage.draw();
		
		stage.getBatch().begin();
		stage.getBatch().draw(masterMech, 900, 0);
		stage.getBatch().end();
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
	}

}
