package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Images.DrawLines;
import com.mygdx.game.Images.Shape2DRenderer;
import com.mygdx.game.Pieces.Positionables;
import com.mygdx.game.Pieces.TouchableCollection;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.FileU;
import com.mygdx.game.utilities.L;
import com.mygdx.game.utilities.RenderU;

import java.nio.ByteBuffer;

public class Box2d extends ApplicationAdapter {
	Device device;
	Viewport viewport;
	SpriteBatch spriteBatch;

	ExtensibleSprite sprite;
	TouchableCollection<ExtensibleSprite> collection;
	FloatArray p=new FloatArray();
	Positionables positionables=new Positionables();
	IntArray is=new IntArray();
	Shape2DRenderer shape2DRenderer;
	Polygon polygon=new Polygon();

	@Override
	public void create () {
		device=new Device().logging();
		spriteBatch=device.spriteBatch;
		int size=512;
		viewport=device.createExtendViewport(size,size);
		device.setCamera(viewport);

		TextureRegion circleImage=DrawLines.makeDiscImage(60);
		Circle circleShape=new Circle(32,32,30);
		device.extensibleSpriteBuilder.setTransRotate();

		ExtensibleSprite sprite1=device.extensibleSpriteBuilder.build(circleImage,circleShape);
		sprite1.setPosition(100,100);

		ExtensibleSprite sprite2=device.extensibleSpriteBuilder.build(circleImage,circleShape);
		sprite2.setPosition(200,200);
		sprite2.setColor(Color.FIREBRICK);

		ExtensibleSprite sprite3=device.extensibleSpriteBuilder.build(circleImage,circleShape);
		sprite3.setPosition(300,300);
		sprite3.setColor(Color.CORAL);

		collection=new TouchableCollection<ExtensibleSprite>();
		collection.addLast(sprite1,sprite2,sprite3);
		positionables.addItems(collection);
		device.touchMover.setTouchable(collection);
		p.addAll(100,400,1,200,400,2,300,400,4);
		FileHandle pf=FileU.createLocalFileHandle("p.dat");
		FileU.read(p,pf);
		positionables.setPositionsAngles(p);
		positionables.getPositionsAngles(p);
		L.og(p);
		positionables.getIndices(is,collection);
		pf=FileU.createLocalFileHandle("i.dat");
		FileU.read(is,pf);
		positionables.setIndices(collection,is);
		ByteBuffer byteBuffer=FileU.readByteBuffer(FileU.createLocalFileHandle("dddd.dat"));
		L.og(byteBuffer.asFloatBuffer());
		positionables.setPositionsAngles(byteBuffer);
		L.og(byteBuffer.asIntBuffer());
		positionables.setIndices(collection,byteBuffer);


		shape2DRenderer=new Shape2DRenderer();

	}


	@Override
	public void resize(int w,int h){
		device.resize(w, h);
		RenderU.center(viewport);
	}


	@Override
	public void render () {

		device.touchMover.update();
		RenderU.setContinuousRendering(false);
		RenderU.clearBackground(Color.BLUE);
		viewport.apply();

		RenderU.setProjection(spriteBatch,viewport);
		spriteBatch.begin();
		collection.draw();
		spriteBatch.end();
		shape2DRenderer.begin();
		Circle cc=new Circle(200,200,100);
		shape2DRenderer.draw(cc);

		shape2DRenderer.end();

	}
	
	@Override
	public void dispose () {
		device.dispose();
		FileHandle pf=FileU.createLocalFileHandle("p.dat");
		positionables.getPositionsAngles(p);
		pf.delete();
		FileU.write(p,pf);
		L.og(p);
		positionables.getIndices(is,collection);
		L.og(is);
		pf=FileU.createLocalFileHandle("i.dat");
		pf.delete();
		FileU.write(is,pf);

		pf=FileU.createLocalFileHandle("dddd.dat");
		positionables.getPositionsAngles();
		ByteBuffer byteBuffer=positionables.getIndices(collection);
		L.og(byteBuffer.asIntBuffer());
		byteBuffer.rewind();
		L.og(byteBuffer.asIntBuffer());
		byteBuffer=positionables.getPositionsAngles();
		L.og(positionables.getPositionsAngles().asFloatBuffer());
		pf.delete();
		FileU.write(positionables.getPositionsAngles(),pf);
		FileU.write(positionables.getIndices(collection),pf);
		L.og(FileU.readByteBuffer(pf).asFloatBuffer());
		L.og(FileU.readByteBuffer(pf).asIntBuffer());

	}
}
