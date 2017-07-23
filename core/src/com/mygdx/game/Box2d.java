package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Buttons.ButtonAct;
import com.mygdx.game.Buttons.ButtonBuilder;
import com.mygdx.game.Buttons.ButtonCollection;
import com.mygdx.game.Buttons.ButtonExtension;
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
	ButtonCollection collection;

	@Override
	public void create () {
		device=new Device().logging();
		spriteBatch=device.spriteBatch;
		int size=512;
		viewport=device.createExtendViewport(size,size);
		device.setCamera(viewport);
		img=device.basicAssets.getTextureRegion("badlogic");
		ExtensibleSpriteBuilder extensibleSpriteBuilder=device.extensibleSpriteBuilder;

		extensibleSpriteBuilder.setTranslate();

		collection=new ButtonCollection();

		ButtonBuilder buttonBuilder=device.buttonBuilder.setSelectionButton(collection);
		ExtensibleSprite button=buttonBuilder.build(img);
		ExtensibleSprite button2=buttonBuilder.build(img);
		ExtensibleSprite button3=buttonBuilder.build(img);
		button2.setPosition(256,0);
		button3.setPosition(256,256);

		button.buttonExtension.setButtonAct(new ButtonAct() {
			@Override
			public void act(ButtonExtension buttonExtension) {
				L.og("Hallo "+buttonExtension.state);
			}
		});

		device.touchMover.setPiece(collection);
		button.buttonExtension.press();

	}


	@Override
	public void resize(int w,int h){
		device.resize(w, h);
		Basic.center(viewport);
	}


	@Override
	public void render () {

		device.touchMover.update();
		Basic.setContinuousRendering(false);
		Basic.clearBackground(Color.BLUE);
		viewport.apply();

		Basic.setProjection(spriteBatch,viewport);
		spriteBatch.begin();
		collection.draw();
		spriteBatch.end();


	}
	
	@Override
	public void dispose () {
		device.dispose();
	}
}
