package com.arorashivoy.tank_star.Helper;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


// TODO Check if using it before submitting

public class AnimatedActor extends Image
{
	protected Animation animation = null;
	private float stateTime = 0;

	public AnimatedActor(Animation animation) {
		super((TextureRegion) animation.getKeyFrame(0));
		this.animation = animation;
	}

	@Override
	public void act(float delta)
	{
		stateTime+=delta;
		((TextureRegionDrawable)getDrawable()).setRegion((TextureRegion) animation.getKeyFrame(stateTime, true));
		super.act(delta);
	}
}