package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Box2d extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	Texture img;

	World world;
	com.mygdx.game.physics.Physics physics;

	Viewport viewport;


	DistanceJoint joint;
	MouseJoint mouseJoint;
	Body body;
	Sprite sprite;
	float pixelsPerMeter=100f;



	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		physics=new com.mygdx.game.physics.Physics(true);

		world=physics.createWorld(0,-10,true);
		viewport=physics.createExtendViewport(10,10);
		//viewport=physics.createFitViewport(10,10);

		shapeRenderer=new ShapeRenderer();


		//bodyDef.gravityScale=-0.1f;
		Body bottom=physics.dynamicBody().position(5,5).build();
		Body top=physics.staticBody().position(9,5).build();

		//body.setLinearDamping(0.2f);


		physics.fixture().boxShape(0.5f,1,0.6f).attachTo(top);
		//physics.fixture().polygonShape(triangle).attachTo(body);
		physics.fixture().circleShape(0.5f).attachTo(bottom);



		DistanceJointDef distanceJointDef=new DistanceJointDef();
		distanceJointDef.length=3;

		//distanceJointDef.frequencyHz=30;
		//distanceJointDef.dampingRatio=1f;
		//distanceJointDef.initialize(top,bottom,new Vector2(0,0),new Vector2(0,0));
		distanceJointDef.bodyB=top;
		distanceJointDef.bodyA=bottom;
		distanceJointDef.length=3;
		distanceJointDef.localAnchorA.set(0,0.2f);
		distanceJointDef.localAnchorB.set(0,0);
		com.mygdx.game.utilities.L.og(distanceJointDef.frequencyHz);
		com.mygdx.game.utilities.L.og(distanceJointDef.dampingRatio);

		//joint=(DistanceJoint)world.createJoint(distanceJointDef);

		Body ground=physics.staticBody().reset().position(5,0.1f).build();
		PolygonShape groundBox=new PolygonShape();
		groundBox.setAsBox(20,0.2f,new Vector2(2f,0.1f),0f);

		physics.fixture().shape(groundBox).attachTo(ground);
		groundBox.dispose();
		com.mygdx.game.utilities.L.og(new MouseJointDef().collideConnected);

		//mouseJoint=physics.mouseJoint().dummyBody(ground).body(bottom).maxForce(12).target(5,4.7f).build();
		physics.distanceJoint().bodyA(top).bodyB(bottom).localAnchorB(0,0.1f).length().build();


		sprite=new Sprite(img);

		sprite.setPosition(0,0);
		//sprite.setSize(1,1);
		sprite.setOrigin(0f,0f);
		sprite.setScale(1/256f);
		//sprite.setRotation(45);

	}

	@Override
	public void resize(int w,int h){

		viewport.update(w, h);


	}
		//L.og(joint.getLength());

	@Override
	public void render () {
		viewport.apply();

		Gdx.gl.glClearColor(0, 0, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();
		//batch.draw(img, 0, 0,0,0,15,10);
		sprite.draw(batch);
		batch.end();
		physics.debugRender();
		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.rect(0.5f,0.5f,9,9);
		shapeRenderer.end();
		//body.setAwake(true);
		//mouseJoint.setTarget(new Vector2(9, 9));
		physics.step(1/60f);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
