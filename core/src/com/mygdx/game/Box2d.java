package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Images.Shape2DCollection;
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
		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.draw(shape2DCollection);
		shapeRenderer.end();

	}
	
	@Override
	public void dispose () {
		device.dispose();
	}
}
