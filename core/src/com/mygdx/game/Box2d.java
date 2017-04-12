package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Images.Chain;
import com.mygdx.game.Images.Shape2DCollection;
import com.mygdx.game.Images.Shape2DCreator;
import com.mygdx.game.Images.Shape2DRenderer;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.BasicAssets;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.FollowCamera;
import com.mygdx.game.utilities.Viewports;

public class Box2d extends ApplicationAdapter {
	Shape2DRenderer shapeRenderer;
	Device device;

	Viewport viewport;
	FollowCamera followCamera;

	Pixmap pixmap;
	Texture img;
	PolygonSpriteBatch polygonSpriteBatch;

	@Override
	public void create () {
		device=new Device();
		device.createShape2DRenderer().createSpriteBatch().setLogging(true);
		BasicAssets basicAssets=device.basicAssets;

		followCamera=new FollowCamera();

		int viewportSize=500;
		viewport= Viewports.createExtendViewport(viewportSize,viewportSize,followCamera);

		shapeRenderer=device.shape2DRenderer;
		shapeRenderer.setNullRadius(5);

		polygonSpriteBatch=new PolygonSpriteBatch();
		int size=10;
		pixmap=new Pixmap(size,size, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.CORAL);
		pixmap.fill();
		img=new Texture(pixmap);
	}

	@Override
	public void resize(int w,int h){
		viewport.update(w, h);
	}

	@Override
	public void render () {
		viewport.apply();
		Basic.clearBackground(Color.BLUE);

		Shape2DCollection shape2DCollection=new Shape2DCollection();
		shape2DCollection.addPolygon(10,10,400,10,200,400).addCircle(100,100,50);



		float[] v={10,10,400,10,200,400};
		Polyline polygon=new Polyline(v);
		Chain chain=new Chain(v).addGhostA(10,10).addGhostB(490,490).isLoop();
		Shape2DCollection shape2DCollection1= Shape2DCreator.dotsAndLines(20,true,v);
		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.draw(shape2DCollection1);
		shapeRenderer.end();

		polygonSpriteBatch.setProjectionMatrix(viewport.getCamera().combined);
		polygonSpriteBatch.begin();
		polygonSpriteBatch.draw(img,10,10);
		polygonSpriteBatch.draw(img,new float[]{100,100,200,200,150,300},0,3);
		polygonSpriteBatch.end();
	}
	
	@Override
	public void dispose () {
		device.dispose();
	}
}
