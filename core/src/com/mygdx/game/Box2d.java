package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.ExtensibleSpriteBuilder;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.L;

public class Box2d extends ApplicationAdapter {
	Device device;
	Viewport viewport;
	SpriteBatch spriteBatch;
	TextureRegion img;
	Vector2 vector=new Vector2();
	Screen screen;
	ExtensibleSprite sprite;

	@Override
	public void create () {
		device=new Device().logging();
		spriteBatch=device.spriteBatch;
		int size=500;
		viewport=device.createExtendViewport(size,size);
		device.touchReader.setCamera(viewport);
		img=device.basicAssets.getTextureRegion("badlogic");
		ExtensibleSpriteBuilder extensibleSpriteBuilder=new ExtensibleSpriteBuilder(device);

		sprite=extensibleSpriteBuilder.build(img);

	}


	@Override
	public void resize(int w,int h){
		device.resize(w, h);
		Basic.center(viewport);
	}


	@Override
	public void render () {
		Basic.setContinuousRendering(false);
		Basic.clearBackground(Color.BLUE);
		viewport.apply();

		Basic.setProjection(spriteBatch,viewport);
		spriteBatch.begin();
		//sprite.d
		spriteBatch.end();
		device.touchReader.getPosition(vector);
		L.og(vector);

	}
	
	@Override
	public void dispose () {
		device.dispose();
	}
}
