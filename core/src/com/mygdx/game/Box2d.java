package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Images.Chain;
import com.mygdx.game.Images.DrawLines;
import com.mygdx.game.Images.Shape2DCollection;
import com.mygdx.game.Matrix.Matrix;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.physics.PhysicalSprite;
import com.mygdx.game.physics.Physics;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.Device;

public class Box2d extends ApplicationAdapter {
	Device device;
	Viewport viewport;
	SpriteBatch spriteBatch;
	TextureRegion img;
	Vector2 vector=new Vector2();
	Screen screen;
	ExtensibleSprite sprite;
	Matrix<ExtensibleSprite> collection;
	Physics physics;
	DrawLines drawLines;
	Shape2DCollection shape2DCollection;
	Body groundBody;
	PhysicalSprite physicalSprite;

	@Override
	public void create () {
		device=new Device().logging();
		physics=new Physics(device,true);
		Physics.PIXELS_PER_METER=100;

		physics.createWorld(0,00,true);

		drawLines=new DrawLines(device,"disc","line",4);
		shape2DCollection=new Shape2DCollection();
		spriteBatch=device.spriteBatch;
		int size=512;
		viewport=device.createExtendViewport(size,size);
		device.setCamera(viewport);
		img=device.basicAssets.getTextureRegion("badlogic");
		groundBody=physics.bodyBuilder.buildStaticBody(null);
		physics.bodyBuilder.setPosition(200,400);
		physics.fixtureBuilder.setFriction(0.01f).setRestitution(1f);
		Body body=physics.bodyBuilder.buildKinematicBody(new Circle(10,10,10));
		TextureRegion circleImage=DrawLines.makeDiscImage(60);
		physics.bodyBuilder.setPosition(300,300);
		physics.physicalSpriteBuilder.setMouseJointMover(true);
		physics.physicalSpriteBuilder.setKinematicTranslate();
		physicalSprite=physics.physicalSpriteBuilder.buildPhysical(circleImage,new Circle(32,32,30));
		physics.start();

		body.setAngularVelocity(1f);
		body.setAngularDamping(0);
		device.touchMover.setPiece(physicalSprite);

	}


	@Override
	public void resize(int w,int h){
		device.resize(w, h);
		Basic.center(viewport);
		shape2DCollection.clear();
		shape2DCollection.add(new Chain(0,150,viewport.getWorldWidth()/2,10,viewport.getWorldWidth(),10));
		physics.fixtureBuilder.clear(groundBody);
		physics.fixtureBuilder.build(groundBody,shape2DCollection);
	}


	@Override
	public void render () {

		device.touchMover.update();
		physics.advance();
		//Basic.setContinuousRendering(false);
		Basic.clearBackground(Color.BLUE);
		viewport.apply();

		Basic.setProjection(spriteBatch,viewport);
		spriteBatch.begin();

		physicalSprite.draw();
		spriteBatch.end();

		physics.debugRender(viewport);

	}
	
	@Override
	public void dispose () {
		device.dispose();
	}
}
