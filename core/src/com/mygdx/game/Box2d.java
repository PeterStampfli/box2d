package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Box2d extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	World world;
	OrthographicCamera camera;
	Box2DDebugRenderer debugRenderer;

	final float TIME_STEP=1/60f;

	private float accumulator=0f;

	private void doPhysicsStep(float dTime){
		float frameTime=Math.min(dTime,0.25f);
		accumulator+=frameTime;
		while (accumulator>=TIME_STEP){
			// worldmanager???
			world.step(TIME_STEP,6,2);
			accumulator-=TIME_STEP;
		}

	}


	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		Box2D.init();
		Vector2 gravity=new Vector2(0,-10);
		world=new World(gravity,true);
		camera=new OrthographicCamera();
		debugRenderer=new Box2DDebugRenderer();

	}

	@Override
	public void resize(int w,int h){
		camera.setToOrtho(false);
		camera.update();
	}

	@Override
	public void render () {
		world.step(1f/60f,6,2);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img, 0, 0,0,0,100,100);
		batch.end();
		debugRenderer.render(world,camera.combined);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
