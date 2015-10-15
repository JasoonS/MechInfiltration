package jason.smythe.uct.csc2003.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class GameSoundController {
	private Music music;
	private boolean gameHasSound;
	
	public GameSoundController(){
		music = Gdx.audio.newMusic(Gdx.files.internal("sound/theme.mp3"));
		
		gameHasSound = true;
		
		music.setVolume((float) 0.25);
		music.play();
		
	  	music.setOnCompletionListener(new Music.OnCompletionListener() {
	     	@Override
	     	public void onCompletion(Music music) {
	        	music.play();
	     	}
	  	});
	}
	
	public void toggleGameSound(){
		if (gameHasSound) {
			music.pause();
		} else {
			music.play();
		}
		gameHasSound = !gameHasSound;
	}
	
	private void playSound(Sound sound){
		if(gameHasSound) sound.play();
	}
}
