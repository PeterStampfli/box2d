package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Buttons.ButtonActions;
import com.mygdx.game.Buttons.ButtonBuilder;
import com.mygdx.game.Buttons.ButtonCollection;
import com.mygdx.game.Buttons.ButtonExtension;
import com.mygdx.game.Images.DrawDotsAndLines;
import com.mygdx.game.Images.Edge;
import com.mygdx.game.Images.Mask;
import com.mygdx.game.Images.Polypoint;
import com.mygdx.game.Images.Shape2DCollection;
import com.mygdx.game.Images.Shape2DRenderer;
import com.mygdx.game.Lattice.SquareLattice;
import com.mygdx.game.Pieces.TouchMove;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.SpriteEffect;
import com.mygdx.game.TextSprite.SmallTextExtension;
import com.mygdx.game.physics.BodyBuilder;
import com.mygdx.game.physics.FixtureBuilder;
import com.mygdx.game.physics.JointBuilder;
import com.mygdx.game.physics.PhysicalSprite;
import com.mygdx.game.physics.PhysicalSpriteBuilder;
import com.mygdx.game.physics.Physics;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.BasicAssets;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.FollowCamera;
import com.mygdx.game.utilities.L;

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
		Circle circle=new Circle(99,99,80);
		Polypoint dotsAndLines=new Polypoint();
		dotsAndLines.add(10,10,190,10,90,170,30,150);
		Polygon polygon=dotsAndLines.getPolygon();

		Mask mask=new Mask(200,200);

		mask.fill(polygon);
		//mask.invert();

		mask.setSmoothing(2);
		img=mask.createTransparentWhiteTextureRegion();
		Basic.linearInterpolation(img);

		//extensibleSprite.setText("ÄtestfgjÂ");


		String langerText="ein langer text ipsum lorem un noch mehr als das kommt jetz"+
				"mehr ist auch noch drin aber alles hat eine nede";
		physics=new Physics(true);

		//physics.createWorld(0,-1000,true);
		physics.createWorld(0,-000,true);

		physics.start();
		BodyBuilder bodyBuilder=physics.bodyBuilder;
		FixtureBuilder fixtureBuilder=physics.fixtureBuilder;
		JointBuilder jointBuilder=physics.jointBuilder;

		fixtureBuilder.setFriction(0.7f);
		 edge=new Edge(-1000,10,1000,10);
		Body ground=bodyBuilder.setPosition(0,0).buildStaticBody(edge);

		Body top=bodyBuilder.setPosition(250,350).buildStaticBody(new Circle(0,0,20));

		jointBuilder.setFrequencyHz(2);

		PhysicalSpriteBuilder physicalSpriteBuilder=new PhysicalSpriteBuilder(device,physics);
		physicalSpriteBuilder.setContains(physicalSpriteBuilder.physicalSpriteActions.bodyContains);
		//physicalSpriteBuilder.setMouseJoint(true);


		extensibleSprite=physicalSpriteBuilder.buildPhysical(img,polygon);
		//extensibleSprite2=physicalSpriteBuilder.buildPhysical(img,polygon);

		extensibleSprite.setColor(Color.FIREBRICK);

		extensibleSprite.setWorldOriginAngle(100,300,0.5f);

		new SmallTextExtension(device,device.bitmapFont,extensibleSprite);
		extensibleSprite.setText("Hallo");
		ButtonBuilder buttonBuilder=new ButtonBuilder();
		buttonBuilder.setSelectionButton(buttonCollection);

		ButtonActions buttonActions=new ButtonActions();
		ButtonExtension buttonExtension=buttonBuilder.build(extensibleSprite);
/*
		buttonExtension.setButtonAct(new ButtonAct() {
			@Override
			public boolean act(ButtonExtension buttonExtension) {
				L.og("action red"+buttonExtension.state);
				return false;
			}
		});

*/
		extensibleSprite.addTouchBeginEffect(new SpriteEffect() {
			@Override
			public boolean effect(ExtensibleSprite sprite) {
				L.og("effect");
				return false;
			}
		});

		PhysicalSprite greenSprite=physicalSpriteBuilder.buildPhysical(img,polygon);
		greenSprite.setColor(Color.GREEN);
		greenSprite.setWorldOriginAngle(300,100,0);

		ButtonExtension buttonExtensionGreen=buttonBuilder.build(greenSprite);



		touchMove=new TouchMove(buttonCollection,device.touchReader,viewport);
		touchMove.asInputProcessor();
		L.og((greenSprite.body.getAngularDamping()));
		//   dummyBody=physics.bodyBuilder.reset().setStaticBody().setPosition(100,100).build();

		SquareLattice squareLattice=new SquareLattice(10).setWidthHeight(2,2).setLeftBottom(1,10);

		//L.og(squareLattice.isInside(1,1));
		//L.og(squareLattice.isInside(1f,1f));
		DrawDotsAndLines drawDotsAndLines=new DrawDotsAndLines(device,"disc","line");
	}

	@Override
	public void resize(int w,int h){
		device.resize(w, h);
		extensibleSprite.keepVisible(viewport.getCamera());
	}

	@Override
	public void render () {
		//Basic.setContinuousRendering(false);
		//====================================================================================
		//physics.advance();

		//L.og(extensibleSprite.body.getWorldVector(new Vector2(1,0)).toString());

		//L.og(extensibleSprite.contains(device.touchReader.getPosition(viewport)));

		viewport.apply();
		Basic.clearBackground(Color.BLUE);

		Vector2 position=device.touchReader.getPosition(viewport);

		touchMove.update();
		spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
		spriteBatch.begin();
		//extensibleSprite.draw(spriteBatch, viewport.getCamera());
		//buttonCollection.draw(spriteBatch,viewport.getCamera());
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.draw(DrawDotsAndLines.makeDiscImage(40),150,150,220,220);

		spriteBatch.setColor(Color.FIREBRICK);
		spriteBatch.draw(DrawDotsAndLines.makeLineImage(40),270,150,4,220);
		spriteBatch.end();


		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
		shapeRenderer.begin();
		//shapeRenderer.draw(shape2DCollection);
		shapeRenderer.rect(0,0,100,300);
		shapeRenderer.draw(edge);
		shapeRenderer.end();

		physics.debugRender(viewport);
	}
	
	@Override
	public void dispose () {
		device.dispose();
	}
}
