package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Buttons.ButtonCollection;
import com.mygdx.game.Images.DrawLines;
import com.mygdx.game.Images.Edge;
import com.mygdx.game.Images.Mask;
import com.mygdx.game.Images.Shape2DCollection;
import com.mygdx.game.Images.Shape2DRenderer;
import com.mygdx.game.Lattice.Lattice;
import com.mygdx.game.Lattice.LatticeOfTouchables;
import com.mygdx.game.Lattice.LatticeVector;
import com.mygdx.game.Lattice.RectangularLattice;
import com.mygdx.game.Pieces.TouchMove;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.physics.PhysicalSprite;
import com.mygdx.game.physics.Physics;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.BasicAssets;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.FollowCamera;
import com.mygdx.game.utilities.L;
import com.mygdx.game.utilities.TouchReader;

public class Box2d extends ApplicationAdapter {
	Shape2DRenderer shapeRenderer;
	Device device;
	Vector2 v;
	Viewport viewport;
	FollowCamera followCamera;

	Pixmap pixmap;
	TextureRegion img;
	TextureRegion textureRegion;

	SpriteBatch spriteBatch;
	Shape2DCollection shape2DCollection;
	TouchMove touchMove;

	PhysicalSprite extensibleSprite;

	Physics physics;
	Edge edge;
	MouseJoint mouseJoint;
	ButtonCollection buttonCollection =new ButtonCollection();
	DrawLines drawLines;
	OrthographicCamera camera=new OrthographicCamera();
	TextureRegion square;
	ScreenViewport screenViewport;
	Lattice lattice;
	TouchReader touchReader;
	LatticeOfTouchables<ExtensibleSprite> latticeOfTouchables;

	@Override
	public void create () {
		device=new Device();
		device.createShape2DRenderer().createSpriteBatch().setLogging(true).createDefaultBitmapFont();
		BasicAssets basicAssets=device.basicAssets;
		device.bitmapFont.getData().scale(2);
		device.bitmapFont.setColor(Color.BROWN);
		followCamera=new FollowCamera();
		device.createScreenViewport();
		screenViewport=device.screenViewport;

		int viewportSize=500;
		viewport= device.createExtendViewport(viewportSize,viewportSize,camera);


		shapeRenderer=device.shape2DRenderer;
		spriteBatch=device.spriteBatch;
		shapeRenderer.setNullRadius(5);

		 drawLines=new DrawLines(device,"disc","line",10);
		//drawLines.setLineWidth(20);
		int size=drawLines.discImage.getRegionHeight();
		Mask mask = new Mask(size + 2, size + 2);
		mask.invert();
		mask.transparentBorder();
		mask.invert();
		square= mask.createWhiteTextureRegion();

		img=device.basicAssets.getTextureRegion("badlogic");
		img=DrawLines.makeDiscImage(5);

		lattice=new RectangularLattice(40,50);

		lattice.setLeftBottomCenter(20,20);
		touchReader=device.touchReader;
		ExecuterTest executerTest=new ExecuterTest();
		executerTest.execute("Hallllllo");
		latticeOfTouchables=new LatticeOfTouchables<ExtensibleSprite>(5,20);
		L.og(latticeOfTouchables.items.size);
		latticeOfTouchables.items.set(34,new ExtensibleSprite());
		L.og(latticeOfTouchables.items.size);
		latticeOfTouchables.resize(10,30);
		L.og(latticeOfTouchables.items.size);
	}

	@Override
	public void resize(int w,int h){
		device.resize(w, h);
	}


	@Override
	public void render () {
		Basic.setContinuousRendering(false);



		viewport.apply(true);
		screenViewport.apply(true);
		Basic.clearBackground(Color.WHITE);

		//Vector2 position=device.touchReader.getPosition(viewport);

//		touchMove.update();
		spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
		//spriteBatch.setProjectionMatrix(screenViewport.getCamera().combined);
		spriteBatch.begin();
		spriteBatch.setColor(Color.GRAY);
		//Basic.clearBackground(spriteBatch,viewport,img);

		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
		shapeRenderer.begin();
		//extensibleSprite.draw(spriteBatch, viewport.getCamera());
		//buttonCollection.draw(spriteBatch,viewport.getCamera());
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.setColor(Color.RED);

		LatticeVector a=new LatticeVector(lattice);
		Vector2 b=new Vector2();

		for (int i=0;i<5;i++){
			for (int j=0;j<5;j++) {
				lattice.positionOfAddress(a,i,j);
				spriteBatch.draw(img,a.x-img.getRegionWidth()/2,a.y-img.getRegionHeight()/2);
			}
		}

		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.circle(device.touchReader.getPosition(a,viewport),5);
		shapeRenderer.setColor(Color.BROWN);
		//shapeRenderer.circle(lattice.adjust(device.touchReader.getPosition(a,viewport)),20);
		//spriteBatch.draw(img,0,0);
		shapeRenderer.point(20,20);
		shapeRenderer.setColor(Color.RED);
		device.touchReader.getPosition(a,viewport).addressOfPosition().stepUpRight().positionOfAddress();
		//lattice.stepDown(a);
		shapeRenderer.circle(a,30);

		spriteBatch.end();

		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.draw(new Circle(20,20,2));

		shapeRenderer.end();

		//physics.debugRender(viewport);
	}
	
	@Override
	public void dispose () {
		device.dispose();
	}
}
