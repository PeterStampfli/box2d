package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Images.DrawLines;
import com.mygdx.game.Images.Mask;
import com.mygdx.game.Sprite.SpriteActions;
import com.mygdx.game.Sprite.SpriteCollection;
import com.mygdx.game.physics.BorderBarrier;
import com.mygdx.game.physics.PhysicalSprite;
import com.mygdx.game.physics.Physics;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.L;
import com.mygdx.game.utilities.MathU;
import com.mygdx.game.utilities.RenderU;
import com.mygdx.game.utilities.TimeU;
import com.mygdx.game.utilities.Timer;

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
	DrawLines drawLines;
	ForceTest forceTest;
	Timer timer;


	@Override
	public void create () {

		timer=new Timer(new Runnable() {
			@Override
			public void run() {
				L.og("something happens"+ TimeU.getTime());
			}
		});

		timer.repeatForever(1);
		device=new Device().logging();
		spriteBatch=device.spriteBatch;
		int size=512;
		viewport=device.createExtendViewport(size,size);
		device.setCamera(viewport);
		physics=new Physics(device);
		physics.createWorld(200,-100,false);
		borderBarrier=new BorderBarrier(device,viewport,physics);
		Circle circle=new Circle(0,0,30);
		Mask mask=Mask.create(circle);
		textureRegion= mask.createColorTextureRegion();
		device.extensibleSpriteBuilder.setTransRotate();
		device.extensibleSpriteBuilder.setKeepVisible(SpriteActions.keepVisibleOrigin);
		//physics.physicalSpriteBuilder.setKeepVisible(SpriteActions.keepVisibleTexture);
		extensibleSprite=physics.physicalSpriteBuilder.build(textureRegion,circle);
		extensibleSprite.setPosition(100,300);
		spriteCollection=new SpriteCollection(device);
		spriteCollection.add(extensibleSprite);
		spriteCollection.add(device.extensibleSpriteBuilder.build(textureRegion,circle));
		device.touchMover.setTouchable(spriteCollection);

		drawLines=new DrawLines(device,"disc","line",10);
		physics.world.setGravity(new Vector2(0,10));
		physics.createBalance(200);
		forceTest=new ForceTest(viewport,physics);
		physics.start();
		L.og(MathU.mod(0,5));

	}



	@Override
	public void resize(int w,int h){
		device.resize(w, h);
		RenderU.center(viewport);
	}


	@Override
	public void render () {

		timer.update();

		device.touchMover.update();
		physics.balance.update();
		forceTest.update();
		viewport.apply();

		//extensibleSprite.setPosition(device.unproject(position.set(0,0)));
		//RenderU.setContinuousRendering(false);
		physics.advance();
		RenderU.clearBackground(Color.BLUE);

		RenderU.setProjection(spriteBatch,viewport);
		spriteBatch.begin();

		spriteCollection.draw();
		drawLines.draw(borderBarrier.borderShape);

		spriteBatch.end();


	}
	
	@Override
	public void dispose () {
		device.dispose();




	}
}
