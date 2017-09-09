package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Images.Mask;
import com.mygdx.game.Sprite.SpriteCollection;
import com.mygdx.game.physics.BorderBarrier;
import com.mygdx.game.physics.PhysicalSprite;
import com.mygdx.game.physics.Physics;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.RenderU;

public class Box2d extends ApplicationAdapter {
	Device device;
	Viewport viewport;
	SpriteBatch spriteBatch;
	Physics physics;
	TextureRegion textureRegion;
	PhysicalSprite extensibleSprite;
	SpriteCollection spriteCollection;
	BorderBarrier borderBarrier;
	Vector2 position=new Vector2();


	@Override
	public void create () {
		device=new Device().logging();
		spriteBatch=device.spriteBatch;
		int size=512;
		viewport=device.createExtendViewport(size,size);
		device.setCamera(viewport);
		physics=new Physics(device,true);
		physics.createWorld(0,-50,true);
		borderBarrier=new BorderBarrier(device,viewport,physics);
		Circle circle=new Circle(0,0,30);
		Mask mask=Mask.create(circle);
		textureRegion= mask.createColorTextureRegion();
		device.extensibleSpriteBuilder.setTransRotate();
		extensibleSprite=physics.physicalSpriteBuilder.build(textureRegion,circle);
		extensibleSprite.setPosition(100,300);
		spriteCollection=new SpriteCollection();
		spriteCollection.add(extensibleSprite);
		spriteCollection.add(device.extensibleSpriteBuilder.build(textureRegion,circle));
		device.touchMover.setTouchable(spriteCollection);

		physics.start();

	}



	@Override
	public void resize(int w,int h){
		device.resize(w, h);
		RenderU.center(viewport);
	}


	@Override
	public void render () {

		device.touchMover.update();
		viewport.apply();

		extensibleSprite.setPosition(device.unproject(position.set(0,0)));
		RenderU.setContinuousRendering(false);
		//physics.advance();
		RenderU.clearBackground(Color.BLUE);

		RenderU.setProjection(spriteBatch,viewport);
		spriteBatch.begin();

		spriteCollection.draw();

		spriteBatch.end();


	}
	
	@Override
	public void dispose () {
		device.dispose();




	}
}
