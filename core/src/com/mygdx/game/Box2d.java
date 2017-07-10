package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Buttons.ButtonCollection;
import com.mygdx.game.Images.DrawLines;
import com.mygdx.game.Images.Edge;
import com.mygdx.game.Images.Mask;
import com.mygdx.game.Images.Shape2DCollection;
import com.mygdx.game.Images.Shape2DRenderer;
import com.mygdx.game.Lattice.Lattice;
import com.mygdx.game.Lattice.RectangularLattice;
import com.mygdx.game.Matrix.Transformation;
import com.mygdx.game.Matrix.ActionIJ;
import com.mygdx.game.Matrix.Matrix;
import com.mygdx.game.Pieces.TouchMove;
import com.mygdx.game.Pieces.Touchable;
import com.mygdx.game.Pieces.TouchableCollection;
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
	Matrix<ExtensibleSprite> matrix;

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
		//img=DrawLines.makeDiscImage(5);

		lattice=new RectangularLattice(40,50);

		lattice.setLeftBottomCenter(20,20);
		touchReader=device.touchReader;

		matrix =new Matrix<ExtensibleSprite>(5,20).resize(3,3);
		L.og(matrix.items.size);
		//latticeOfTouchables.items.set(34,new ExtensibleSprite());
		L.og(matrix.items.size);
	/*	latticeOfTouchables.create(new Creation<ExtensibleSprite>() {
			@Override
			public ExtensibleSprite create() {
				return new ExtensibleSprite();
			}
		});
		*/
		ExtensibleSprite sprite= matrix.get(1,1);
		L.og(sprite);
		matrix.act(new ActionIJ<ExtensibleSprite>() {
			@Override
			public void act( int i, int j,ExtensibleSprite sprite) {
				L.og(i+","+j);
			}
		});
		Matrix<Touchable> touchableMatrix =new Matrix<Touchable>(3,3);
		touchableMatrix.transform(new Transformation<Touchable, ExtensibleSprite>() {
			@Override
			public Touchable transform(ExtensibleSprite sprite1) {
				return null;
			}
		}, matrix);
		L.og(null instanceof ExtensibleSprite);
		Object item=new ExtensibleSprite();
		L.og(item);
		L.og("*"+Basic.convertInstanceOfObject(item,TouchMove.class));
		TouchableCollection touchableCollection =new TouchableCollection(false);
		touchableCollection.items=new Array<ExtensibleSprite>();
	}


	@Override
	public void resize(int w,int h){
		device.resize(w, h);
	}


	@Override
	public void render () {
		Basic.setContinuousRendering(false);
		Basic.clearBackground(Color.BLUE);
		viewport.apply();
		viewport.getCamera().position.set(((ExtendViewport)viewport).getMinWorldWidth()/2,viewport.getWorldHeight()/2,0);
		Basic.setProjection(spriteBatch,viewport);
		spriteBatch.begin();
		spriteBatch.draw(img,0,0);
		spriteBatch.end();


	}
	
	@Override
	public void dispose () {
		device.dispose();
	}
}
