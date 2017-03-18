package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Box2d extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	Texture img;

	World world;
	Physics physics;

	Viewport viewport;

	Box2DDebugRenderer debugRenderer;


	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		physics=new Physics(0.1f,true);

		world=physics.createWorld(0,-10,true);
		viewport=physics.createExtendViewport(10,10);
		//viewport=physics.createFitViewport(10,10);

		debugRenderer=new Box2DDebugRenderer();
		shapeRenderer=new ShapeRenderer();


		//bodyDef.gravityScale=-0.1f;
		Body body=physics.createDynamicBody(5,5);
		body.setLinearDamping(0.2f);

		Shape circle=new CircleShape();
		circle.setRadius(0.5f);
		PolygonShape poly=new PolygonShape();
		poly.setAsBox(0.1f,0.9f,new Vector2(1,0),0.2f);
		FixtureDef fixtureDef=physics.getFixtureDef();
		fixtureDef.shape=poly;

		Fixture fixture=body.createFixture(fixtureDef);
		circle.dispose();
		poly.dispose();
		L.og(body.isFixedRotation());



		Body ground=physics.createBody(5,2f, 0.0f,BodyDef.BodyType.StaticBody);
		PolygonShape groundBox=new PolygonShape();
		groundBox.setAsBox(5,0.2f,new Vector2(2f,0.1f),0f);
		fixtureDef.shape=groundBox;
		ground.createFixture(fixtureDef);
		groundBox.dispose();

	}

	@Override
	public void resize(int w,int h){

		viewport.update(w, h);


	}

	@Override
	public void render () {
		viewport.apply();

		Gdx.gl.glClearColor(0, 0, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();
		//batch.draw(img, 0, 0,0,0,15,10);
		batch.end();
		physics.debugRender();
		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.rect(0.5f,0.5f,9,9);
		shapeRenderer.end();
		physics.step(0.015f);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
