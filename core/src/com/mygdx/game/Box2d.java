package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Images.Mask;
import com.mygdx.game.Images.Shape2DCollection;
import com.mygdx.game.Images.Shape2DRenderer;
import com.mygdx.game.Pieces.TouchMove;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.ExtensibleSpriteBuilder;
import com.mygdx.game.Sprite.SpriteActions;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.BasicAssets;
import com.mygdx.game.utilities.Clipper;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.FollowCamera;

public class Box2d extends ApplicationAdapter {
	Shape2DRenderer shapeRenderer;
	Device device;

	Viewport viewport;
	FollowCamera followCamera;

	Pixmap pixmap;
	TextureRegion img;
	TextureRegion textureRegion;

	SpriteBatch spriteBatch;
	Shape2DCollection shape2DCollection;
	TouchMove touchMove;

	ExtensibleSprite extensibleSprite;

	@Override
	public void create () {

		device=new Device();
		device.createShape2DRenderer().createSpriteBatch().setLogging(true).createDefaultBitmapFont();
		BasicAssets basicAssets=device.basicAssets;
		device.bitmapFont.getData().scale(2);
		device.bitmapFont.setColor(Color.BROWN);

		followCamera=new FollowCamera();

		int viewportSize=500;
		viewport= device.createExtendViewport(viewportSize,viewportSize,followCamera);

		shapeRenderer=device.shape2DRenderer;
		spriteBatch=device.spriteBatch;
		shapeRenderer.setNullRadius(5);
		shape2DCollection=new Shape2DCollection();
		//shape2DCollection.addPolygon(10,10,280,10,200,200).addCircle(100,100,50);
		//shape2DCollection.addDotsAndLines(10,false,10,10,250,50,123,40,20,240);
		//shape2DCollection.add();
		Circle circle=new Circle(150,150,149);
		Mask mask=new Mask(300,300);

		mask.fill(circle);

		img=mask.createTransparentWhiteTextureRegion();

		ExtensibleSpriteBuilder extensibleSpriteBuilder=new ExtensibleSpriteBuilder(device,
				                                                                    device.bitmapFont);

		//extensibleSpriteBuilder.spriteTouchBegin();
		extensibleSpriteBuilder.setTouchDrag(SpriteActions.touchDragTransRotate).setKeepVisible(SpriteActions.keepOriginVisible);
		extensibleSprite=extensibleSpriteBuilder.build(img);

		extensibleSprite.setPosition(40,40);
		//extensibleSprite.setText("ÄtestfgjÂ");

		touchMove=new TouchMove(extensibleSprite,device.touchReader,viewport);
		touchMove.asInputProcessor();
		Clipper.spriteBatch=device.spriteBatch;
		Clipper.setCamera(viewport);
	//	BigTextExtension.setFont(device.bitmapFont);
		String langerText="ein langer text ipsum lorem un noch mehr als das kommt jetz"+
				"mehr ist auch noch drin aber alles hat eine nede";
		//extensibleSprite.setText(langerText);

	}

	@Override
	public void resize(int w,int h){
		device.resize(w, h);
		extensibleSprite.keepVisible(viewport.getCamera());
	}

	@Override
	public void render () {
		viewport.apply();
		Basic.clearBackground(Color.BLUE);

		Vector2 position=device.touchReader.getPosition(viewport);

		touchMove.update();
		spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
		spriteBatch.begin();
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
