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
import com.mygdx.game.Pieces.BigTextSprite;
import com.mygdx.game.Pieces.ExtensibleSprite;
import com.mygdx.game.Pieces.TextSprite;
import com.mygdx.game.Pieces.TouchMove;
import com.mygdx.game.Pieces.TouchableSprite;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.BasicAssets;
import com.mygdx.game.utilities.Clipper;
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
	BigTextSprite touchableSprite;
	TouchMove touchMove;

	ExtensibleSprite extensibleSprite;

	@Override
	public void create () {

		device=new Device();
		device.createShape2DRenderer().createSpriteBatch().setLogging(true).createDefaultBitmapFont();
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

		//mask.fill(shape2DCollection);
		mask.invert();

		img=mask.createTransparentWhiteTexture();
		TextSprite.setBitmapFont(device.bitmapFont);

		touchableSprite=new BigTextSprite(img);
		TouchableSprite.setCamera(viewport.getCamera());

		L.og(touchableSprite.getOriginX());
		L.og(touchableSprite.getOriginY());

		extensibleSprite=new ExtensibleSprite(img);
		extensibleSprite.setPosition(200,100);


		touchMove=new TouchMove(extensibleSprite,device.touchReader,viewport);
		touchMove.asInputProcessor();
		TouchableSprite.setCamera(viewport);
		device.bitmapFont.getData().scale(2);
		touchableSprite.setMargin(10).setTextColor(Color.CORAL);
		touchableSprite.setText("tblÄÜagyjtA nbmbmb dfdfdf ggdgd [gggbnbnbn "
		+"blagyjtA nbmbmb dfdfdf ggdgd \nbnbnbn"+"blagyjtA nbmbmb dfdfdf ggdgd [ngdbnbnbn");
		touchableSprite.setY(50);
		Clipper.spriteBatch=device.spriteBatch;
		Clipper.setCamera(viewport);

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
		if (extensibleSprite.contains(position)){
			touchableSprite.setColor(Color.GREEN);
		}
		touchMove.update();
		spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
		spriteBatch.begin();
		touchableSprite.draw(spriteBatch);
		extensibleSprite.draw(spriteBatch);

		spriteBatch.end();


		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		//shapeRenderer.draw(shape2DCollection);
		//shapeRenderer.rect(0,0,100,300);
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		device.dispose();
	}
}
