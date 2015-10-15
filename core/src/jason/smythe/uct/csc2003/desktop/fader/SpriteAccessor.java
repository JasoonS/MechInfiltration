package jason.smythe.uct.csc2003.desktop.fader;

import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

public class SpriteAccessor implements TweenAccessor <Sprite>{

	// used for gradually fading the spites in, only used in the splash screen so far, to be used in the 'story' section too.
	
	public static final int ALPHA = 0;
	
	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch(tweenType){
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		default:
			return -1;
		}
	}

	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues) {
		switch(tweenType){
		case ALPHA:
			target.setAlpha(newValues[0]);
		default:
			break;
		}
	}

}
