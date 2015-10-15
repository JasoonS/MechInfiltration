package jason.smythe.uct.csc2003;

import jason.smythe.uct.csc2003.screens.Splash;
import jason.smythe.uct.csc2003.util.GameSoundController;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class MechInfultration extends Game implements InputProcessor {
	
	GameSoundController sound;
	
	@Override
	public void create () {
		//add sound and start the splash screen (chaining into all the other screens)
		sound = new GameSoundController();
		Gdx.input.setInputProcessor(this);
		
	}

	@Override
	public boolean keyDown(int keycode) {
		System.out.println("KeyDown: " + keycode);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		System.out.println("touching mouse!!!");
		setScreen(new Splash(sound));
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
