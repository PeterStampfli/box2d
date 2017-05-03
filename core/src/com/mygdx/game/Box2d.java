package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Images.Edge;
import com.mygdx.game.Images.Mask;
import com.mygdx.game.Images.Polypoint;
import com.mygdx.game.Images.Shape2DCollection;
import com.mygdx.game.Images.Shape2DRenderer;
import com.mygdx.game.Pieces.TouchMove;
import com.mygdx.game.physics.BodyBuilder;
import com.mygdx.game.physics.FixtureBuilder;
import com.mygdx.game.physics.PhysicalSprite;
import com.mygdx.game.physics.PhysicalSpriteBuilder;
import com.mygdx.game.physics.Physics;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.BasicAssets;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.FollowCamera;
import com.mygdx.game.utilities.L;

public class Box2d extends ApplicationAdapter {
	Shape2DRenderer shapeRenderer;
	Device device;

	Viewport viewport;
	FollowCamera followCamera;

	Pixmap pixmap;
	TextureRegion img;
	TextureRegion textureRegion;

	SpriteBatch spriteBatch;
	Shape2DCollection shape2DCollection;
	TouchMove touchMove;

	PhysicalSprite extensibleSprite;

	Physics physics;
	Edge edge;

	@Override
	public void create () {
		device=new Device();
		device.createShape2DRenderer().createSpriteBatch().setLogging(true).createDefaultBitmapFont();
		BasicAssets basicAssets=device.basicAssets;
		device.bitmapFont.getData().scale(2);
		device.bitmapFont.setColor(Color.BROWN);
		followCamera=new FollowCamera();

		int viewportSize=500;
		viewport= device.createExtendViewport(viewportSize,viewportSize,followCamera);

		shapeRenderer=device.shape2DRenderer;
		spriteBatch=device.spriteBatch;
		shapeRenderer.setNullRadius(5);
		shape2DCollection=new Shape2DCollection();
		//shape2DCollection.addPolygon(10,10,280,10,200,200).addCircle(100,100,50);
		//shape2DCollection.addDotsAndLines(10,false,10,10,250,50,123,40,20,240);
		//shape2DCollection.add();
		Circle circle=new Circle(99,99,80);
		Polypoint dotsAndLines=new Polypoint();
		dotsAndLines.add(30,30,90,10,90,50,30,50);
		Polygon polygon=dotsAndLines.getPolygon();

				Mask mask=new Mask(200,200);

		mask.fill(polygon);
		//mask.invert();

		mask.setSmoothing(2);
		img=mask.createTransparentWhiteTextureRegion();
		Basic.linearInterpolation(img);

		//extensibleSprite.setText("ÄtestfgjÂ");


		String langerText="ein langer text ipsum lorem un noch mehr als das kommt jetz"+
				"mehr ist auch noch drin aber alles hat eine nede";
		physics=new Physics(true);

		physics.createWorld(0,-10,true);

		physics.start();
		BodyBuilder bodyBuilder=physics.bodyBuilder;
		FixtureBuilder fixtureBuilder=physics.fixtureBuilder;

		Body dynamicBody=bodyBuilder.setPosition(200,300).build();
		bodyBuilder.setBodyType(BodyDef.BodyType.StaticBody);
		 edge=new Edge(-100,10,1000,10);
		Body ground=bodyBuilder.setPosition(0,0).build(edge);





		PhysicalSpriteBuilder physicalSpriteBuilder=new PhysicalSpriteBuilder(device,physics);

		extensibleSprite=physicalSpriteBuilder.buildPhysical(img,polygon,dynamicBody);

		extensibleSprite.setColor(Color.FIREBRICK);
		L.og(extensibleSprite.contains(0,0));
		//extensibleSprite.setPhysicalX(00);
		//extensibleSprite.setPhysicalAngle(1.7f);
		touchMove=new TouchMove(extensibleSprite,device.touchReader,viewport);
		touchMove.asInputProcessor();


	}

	@Override
	public void resize(int w,int h){
		device.resize(w, h);
		extensibleSprite.keepVisible(viewport.getCamera());
	}

	@Override
	public void render () {
		//Basic.setContinuousRendering(false);
		//====================================================================================
		//physics.advance();

		viewport.apply();
		Basic.clearBackground(Color.BLUE);

		Vector2 position=device.touchReader.getPosition(viewport);

		touchMove.update();
		spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
		spriteBatch.begin();
		extensibleSprite.draw(spriteBatch, viewport.getCamera());

		spriteBatch.end();


		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
		shapeRenderer.begin();
		//shapeRenderer.draw(shape2DCollection);
		shapeRenderer.rect(0,0,100,300);
		shapeRenderer.draw(edge);
		shapeRenderer.end();

		physics.debugRender(viewport);
	}
	
	@Override
	public void dispose () {
		device.dispose();
	}
}
