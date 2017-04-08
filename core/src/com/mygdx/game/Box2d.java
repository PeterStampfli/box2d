package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Images.Mask;
import com.mygdx.game.Pieces.Box2DSprite;
import com.mygdx.game.physics.Physics;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.BasicAssets;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.FollowCamera;
import com.mygdx.game.utilities.Viewports;

public class Box2d extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	TextureRegion img;
	Device device;

	World world;
	Physics physics;

	Viewport viewport;
	FollowCamera followCamera;

	DistanceJoint joint;
	MouseJoint mouseJoint;
	Body movingBody;
	Box2DSprite sprite;
	float pixelsPerMeter=100f;
	Body test;

	boolean debug;

	Texture maskImage;
	Texture maskImage2;

	@Override
	public void create () {
		debug=true;
		device=new Device();
		device.createShapeRenderer().createSpriteBatch().setLogging(true);
		batch = device.spriteBatch;
		BasicAssets basicAssets=device.basicAssets;

		img = basicAssets.getTextureRegion("badlogic");
		Basic.linearInterpolation(img);
		Mask mask=new Mask(256,256);
		Circle circle=new Circle(128,128,127);
		mask.fill(circle);

		Pixmap bad=new Pixmap(Gdx.files.internal("badlogic.jpg"));


		img=new TextureRegion(new Texture(mask.cutFromPixmap(bad,0,0)));

		Basic.linearInterpolation(img);

		followCamera=new FollowCamera();

        int viewportSize=10;
		viewport= Viewports.createExtendViewport(viewportSize,viewportSize,followCamera);
		followCamera.setGameWorldSize(200,15).setDebugAllowed(true);
		physics=new Physics(viewport,debug);
		device.disposer.add(physics,"Physics");

		world=physics.createWorld(0,-10,true);
		//viewport=physics.createFitViewport(10,10);

		shapeRenderer=new ShapeRenderer();



		sprite=new Box2DSprite(img);

		sprite.setPosition(0,0);
		sprite.adjustSizeToPixelScale(pixelsPerMeter);

		//bodyDef.gravityScale=-0.1f;
		movingBody=physics.dynamicBody().position(3,5).build(sprite);
		physics.fixture().setScale(1f/pixelsPerMeter).setBody(movingBody).makeShape(circle);

		sprite.initializeSprite(movingBody);

		Body top=physics.staticBody().position(6,10).build();



		physics.fixture().boxShape(0.5f,1,0,0,0.6f).attach(top);
		//physics.fixture().polygonShape(triangle).attach(body);


		Body ground=physics.staticBody().reset().position(5,0.1f).build();
		PolygonShape groundBox=new PolygonShape();
		groundBox.setAsBox(20,0.2f);

		physics.fixture().boxShape(20,0.2f,0,0).attach(ground);
		groundBox.dispose();

		//mouseJoint=physics.mouseJoint().dummyBody(ground).body(bottom).maxForce(12).target(5,4.7f).build();
		physics.distanceJoint().bodyA(top).bodyB(movingBody).localAnchorBIsLocalCenter().length().build();

		physics.start();

		float r=24f;


	}

	@Override
	public void resize(int w,int h){

		viewport.update(w, h);


	}

	@Override
	public void render () {
		if (Basic.timeSinceLastFrameIsSmallerThan(0.03f)) return;
		viewport.apply();

		Basic.clearBackground(Color.BLUE);

		//followCamera.follow(sprite);


		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();
		//batch.draw(img, 0, 0,0,0,15,10);
		//sprite.draw(batch);
		if (debug){
			physics.updateGraphicsData(1);
		}
		sprite.draw(batch);
	//	batch.draw(maskImage,10,0);
		batch.end();
		physics.debugRender();
		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.rect(1f,1f,8,8);
		shapeRenderer.end();
		//mouseJoint.setTarget(new Vector2(9, 9));
		//physics.step(1/60f);
		physics.advance();


		Basic.setContinuousRendering(false);


	}
	
	@Override
	public void dispose () {
		device.dispose();
	}
}
