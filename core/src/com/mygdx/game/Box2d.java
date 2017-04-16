package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Images.Mask;
import com.mygdx.game.Images.Shape2DCollection;
import com.mygdx.game.Images.Shape2DRenderer;
import com.mygdx.game.Pieces.TouchMove;
import com.mygdx.game.Pieces.TouchableSprite;
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
	Texture img;

	SpriteBatch spriteBatch;
	Shape2DCollection shape2DCollection;
	TouchableSprite touchableSprite;
	TouchMove touchMove;

	@Override
	public void create () {
		device=new Device();
		device.createShape2DRenderer().createSpriteBatch().setLogging(true);
		BasicAssets basicAssets=device.basicAssets;

		followCamera=new FollowCamera();

		int viewportSize=500;
		viewport= device.createExtendViewport(viewportSize,viewportSize,followCamera);

		shapeRenderer=device.shape2DRenderer;
		spriteBatch=device.spriteBatch;
		shapeRenderer.setNullRadius(5);
		shape2DCollection=new Shape2DCollection();
		//shape2DCollection.addPolygon(10,10,280,10,200,200).addCircle(100,100,50);
		//shape2DCollection.addDotsAndLines(10,false,10,10,250,50,123,40,20,240);
		shape2DCollection.addPolygon(0,0,290,0,150,290);
		Mask mask=new Mask(300,300);

		mask.fill(shape2DCollection);

		img=mask.createTransparentWhiteTexture();

		touchableSprite=new TouchableSprite(img,shape2DCollection);
		TouchableSprite.setCamera(viewport.getCamera());

		L.og(touchableSprite.getOriginX());
		L.og(touchableSprite.getOriginY());

		touchMove=new TouchMove(touchableSprite,device.touchReader,viewport);
		TouchableSprite.setCamera(viewport);
	}

	@Override
	public void resize(int w,int h){
		device.resize(w, h);
		touchableSprite.keepVisible();
	}

	@Override
	public void render () {
		viewport.apply();
		Basic.clearBackground(Color.BLUE);

		Vector2 position=device.touchReader.getPosition(viewport);

		touchableSprite.setColor(Color.ORANGE);
		if (touchableSprite.contains(position)){
			touchableSprite.setColor(Color.GREEN);
		}
		touchMove.update();
		spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
		spriteBatch.begin();
		touchableSprite.draw(spriteBatch);
		spriteBatch.end();


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
