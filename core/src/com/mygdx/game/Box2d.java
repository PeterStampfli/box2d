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
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.physics.Box2DSprite;
import com.mygdx.game.utilities.Basic;

public class Box2d extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	Texture img;

	World world;
	com.mygdx.game.physics.Physics physics;

	Viewport viewport;


	DistanceJoint joint;
	MouseJoint mouseJoint;
	Body movingBody;
	Box2DSprite sprite;
	float pixelsPerMeter=100f;
	Body test;


	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		physics=new com.mygdx.game.physics.Physics(true);

		world=physics.createWorld(0,-10,true);
		viewport=physics.createExtendViewport(10,10);
		//viewport=physics.createFitViewport(10,10);

		shapeRenderer=new ShapeRenderer();



		sprite=new Box2DSprite(img);

		sprite.setPosition(0,0);
		sprite.adjustSizeToPixelScale(256);

		//bodyDef.gravityScale=-0.1f;
		movingBody=physics.dynamicBody().userData(sprite).position(3,5).build();
		physics.fixture().circleShape(0.5f,0.5f,0.5f).attachTo(movingBody);

		sprite.initializePhysics(movingBody);

		Body top=physics.staticBody().position(9,9).build();



		physics.fixture().boxShape(0.5f,1,0.6f).attachTo(top);
		//physics.fixture().polygonShape(triangle).attachTo(body);


		Body ground=physics.staticBody().reset().position(5,-0.1f).build();
		PolygonShape groundBox=new PolygonShape();
		groundBox.setAsBox(20,0.2f);

		physics.fixture().boxShape(20,0.2f).attachTo(ground);
		groundBox.dispose();
		com.mygdx.game.utilities.L.og(new MouseJointDef().collideConnected);

		//mouseJoint=physics.mouseJoint().dummyBody(ground).body(bottom).maxForce(12).target(5,4.7f).build();
		physics.distanceJoint().bodyA(top).bodyB(movingBody).localAnchorB(0.5f,0.7f).length().build();

		physics.start();



	}

	@Override
	public void resize(int w,int h){

		viewport.update(w, h);


	}

	@Override
	public void render () {
		viewport.apply();

		Basic.clearBackground(Color.BLUE);




		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();
		//batch.draw(img, 0, 0,0,0,15,10);
		//sprite.draw(batch);
		physics.draw(batch);
		batch.end();
		physics.debugRender();
		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.rect(0f,1f,8,8);
		shapeRenderer.end();
		//mouseJoint.setTarget(new Vector2(9, 9));
		//physics.step(1/60f);
		physics.advance();
		/*L.og("test position "+test.getPosition());
		L.og(" angle "+test.getAngle());
		L.og("world center "+test.getWorldCenter());
		L.og("local center "+test.getLocalCenter());*/

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
