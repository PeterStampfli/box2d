package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.FollowCamera;
import com.mygdx.game.utilities.Viewports;

public class Box2d extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	Texture img;
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
		img = new Texture("badlogic.jpg");
		Basic.linearInterpolation(img);
		followCamera=new FollowCamera();

        int viewportSize=100;
		viewport= Viewports.createExtendViewport(viewportSize,viewportSize,followCamera);
		followCamera.setGameWorldSize(200,15).setDebugAllowed(true);
		physics=new Physics(viewport,debug);
		device.disposer.add(physics,"Physics");

		world=physics.createWorld(0,-10,true);
		//viewport=physics.createFitViewport(10,10);

		shapeRenderer=new ShapeRenderer();



		sprite=new Box2DSprite(img);

		sprite.setPosition(0,0);
		sprite.adjustSizeToPixelScale(256);

		//bodyDef.gravityScale=-0.1f;
		movingBody=physics.dynamicBody().position(3,5).build(sprite);
		physics.fixture().circleShape(0.5f,0.105f,0.5f).attach(movingBody);

		sprite.initializeSprite(movingBody);

		Body top=physics.staticBody().position(6,10).build();



		physics.fixture().boxShape(0.5f,1,0.6f).attach(top);
		//physics.fixture().polygonShape(triangle).attach(body);


		Body ground=physics.staticBody().reset().position(5,0.1f).build();
		PolygonShape groundBox=new PolygonShape();
		groundBox.setAsBox(20,0.2f);

		physics.fixture().boxShape(20,0.2f).attach(ground);
		groundBox.dispose();

		//mouseJoint=physics.mouseJoint().dummyBody(ground).body(bottom).maxForce(12).target(5,4.7f).build();
		physics.distanceJoint().bodyA(top).bodyB(movingBody).localAnchorBIsLocalCenter().length().build();

		physics.start();

		float r=24f;
		Mask mask=new Mask(50,50);
		mask.fillCircle(25,25,r);
		//mask.drawPolygon(5,15,5,45,20,30,45,5,20);
		//mask.fillPolygon(15,5,20,4,17,10);
mask.invert().transparentBorder();
		maskImage=mask.createBlackWhiteTexture();

		Mask mask2=new Mask(50,50);
		//mask2.betterFillCircle(25,25,r);
		//mask2.fillPolygon(15,5,45,20,30,45,5,20);
		mask2.drawRing(26f,25f,22.5f,10);

		maskImage2=mask2.createBlackWhiteTexture();


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
		batch.draw(maskImage,10,0);
		batch.draw(maskImage2,65,50);
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
		img.dispose();
	}
}
