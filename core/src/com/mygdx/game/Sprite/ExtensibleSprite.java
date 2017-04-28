package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Pieces.Touchable;

/**
 * Created by peter on 4/18/17.
 * uses the built-in pool
 */

public class ExtensibleSprite extends Sprite implements Touchable {

    public Shape2D shape;
    public Pool<ExtensibleSprite> extensibleSpritePool;
    public NullTextExtension textExtension;
    // the composable actions
    public SpriteContains spriteContains;
    public SpriteDraw spriteDraw;
    public SpriteKeepVisible spriteKeepVisible;
    public SpriteTouchBegin spriteTouchBegin;
    public SpriteTouchDrag spriteTouchDrag;
    public SpriteTouchEnd spriteTouchEnd;
    public SpriteScroll spriteScroll;

    /**
     * free, reset and put back in the pool an ExtensibleSprite object.
     * together with reset and set local reference null ???
     * as an interface ???
     */
    public void free(){
        shape=null;
        setTexture(null);
        if (textExtension!=null){
            textExtension.free();
            textExtension=null;
        }
        extensibleSpritePool.free(this);
    }

    /**
     * set the text of the sprite if there is a text extension
     * @param text
     */
    public void setText(String text){
        textExtension.setText(text,this);
    }

    /**
     * set setAngle of sprite
     * @param angle in radians
     */
    public void setAngle(float angle){
        setRotation(angle * MathUtils.radiansToDegrees);
    }

    /**
     * get setAngle of sprite
     * @return  setAngle in radians
     */
    public float getAngle(){
        return getRotation() / MathUtils.radiansToDegrees;
    }

    /**
     * make that the sprite orientation has n discrete steps, 4 for a square lattice
     * @param n
     */
    public void quantizeAngle(int n){
        setRotation(360f/n*Math.round(n*getRotation()/360f));
    }

    /**
     * set the origin (center of rotation and scaling) equal to center of mass of the body
     * In local coordinates. Zero is left bottom corner of the Textureregion.
     * Uses world dimensions. (Not pixel numbers)
     * @param centerX
     * @param centerY
     */
    public void setLocalOrigin(float centerX,float centerY){
        setOrigin(centerX,centerY);
    }

    /**
     * set the origin (center of rotation and scaling) equal to center of mass of the body
     * In local coordinates. Zero is left bottom corner of the Textureregion.
     * Uses world dimensions. (Not pixel numbers)
     * @param center
     */
    public void setLocalOrigin(Vector2 center){
        setLocalOrigin(center.x,center.y);
    }

    /**
     * set the x-coordinate of center of mass (origin)
     * @param x
     */
    public void setWorldOriginX(float x){
        setX(x-getOriginX());
    }

    /**
     * set the y-coordinate of center of mass (origin)
     * @param y
     */
    public void setWorldOriginY(float y){
        setY(y-getOriginY());
    }

    /**
     * set the setPosition of the sprite such that the origin (center of rotation)
     * lies at given world setPosition (of center of mass)
     * @param worldOriginPositionX
     * @param worldOriginPositionY
     */
    public void setWorldOrigin(float worldOriginPositionX,float worldOriginPositionY){
        setPosition(worldOriginPositionX-getOriginX(),worldOriginPositionY-getOriginY());
    }

    /**
     * set the setPosition of the sprite such that the origin (center of rotation)
     * lies at given setPosition of center of mass
     * @param worldOriginPosition
     */
    public void setWorldOrigin(Vector2 worldOriginPosition){
        setWorldOrigin(worldOriginPosition.x,worldOriginPosition.y);
    }

    /**
     * get x coordinate of the sprite center
     * @return
     */
    public float getWorldOriginX(){
        return getX()+getOriginX();
    }

    /**
     * get y coordinate of the sprite center
     * @return
     */
    public float getWorldOriginY(){
        return getY()+getOriginY();
    }

    // the composable actions

    /**
     * set the instance method for contains the point
     * @param spriteContains
     * @return
     */
    public ExtensibleSprite setContains(SpriteContains spriteContains){
        this.spriteContains = spriteContains;
        return this;
    }

    /**
     * contains if sprite contains the point, using both the texture region and the masterShape
     * @param x
     * @param y
     * @return
     */
    @Override
    public boolean contains(float x, float y) {
        return this.spriteContains.contains(this, x, y);
    }

    /**
     * contains if sprite contains the point
     * @param point
     * @return
     */
    @Override
    public boolean contains(Vector2 point) {
        return contains(point.x,point.y);
    }

    // draw the sprite, with extras ???


    /**
     * set the method for sprite draw
     * @param spriteDraw
     * @return
     */
    public ExtensibleSprite setDraw(SpriteDraw spriteDraw){
        this.spriteDraw=spriteDraw;
        return this;
    }

    /**
     * draw the basic batch using the method of the superclass "Sprite"
     * need this as basis for decorations
     * @param batch
     */
    public void superDraw(Batch batch){
        super.draw(batch);
    }

    /**
     * draw the sprite, with decos ? text!
     * @param batch
     * @param camera
     */
    @Override
    public void draw(Batch batch, Camera camera){ spriteDraw.draw(this,batch, camera); }


    // what to do to keep sprite visible

    /**
     * set instance method for keep visible
     * @param spriteKeepVisible
     * @return
     */
    public ExtensibleSprite setKeepVisible(SpriteKeepVisible spriteKeepVisible){
        this.spriteKeepVisible=spriteKeepVisible;
        return this;
    }

    @Override
    public boolean keepVisible(Camera camera){return spriteKeepVisible.keepVisible(this,camera);};

    // what to do at begin of touch


    /**
     * select the draw for touch begin
     * @param spriteTouchBegin
     * @return
     */
    public ExtensibleSprite setTouchBegin(SpriteTouchBegin spriteTouchBegin){
        this.spriteTouchBegin=spriteTouchBegin;
        return this;
    }

    /**
     * make something at begin of touch, return true if something changed
     * @param position
     * @return
     */
    @Override
    public boolean touchBegin(Vector2 position) {
        return spriteTouchBegin.touchBegin(this,position);
    }

    // what to do for touch dragging around

    /**
     * select the draw for touch drag
     * @param spriteTouchDrag
     * @return
     */
    public ExtensibleSprite setTouchDrag(SpriteTouchDrag spriteTouchDrag){
        this.spriteTouchDrag=spriteTouchDrag;
        return this;
    }

    /**
     * touch drag
     * @param position
     * @param deltaPosition
     * @param camera
     * @return
     */
    @Override
    public boolean touchDrag(Vector2 position, Vector2 deltaPosition, Camera camera) {
        return spriteTouchDrag.touchDrag(this,position,deltaPosition, camera);
    }

    // what to do at end of touch

    /**
     * select the draw for touch begin
     * @param spriteTouchEnd
     * @return
     */
    public ExtensibleSprite setTouchEnd(SpriteTouchEnd spriteTouchEnd){
        this.spriteTouchEnd=spriteTouchEnd;
        return this;
    }

    /**
     * make something at end of touch, return true if something changed
     * @param position
     * @return
     */
    @Override
    public boolean touchEnd(Vector2 position) {
        return spriteTouchEnd.touchEnd(this,position);
    }

    // waht to do at scroll event

    /**
     * set the scroll draw
     * @param spriteScroll
     * @return
     */
    public ExtensibleSprite setScroll(SpriteScroll spriteScroll){
        this.spriteScroll=spriteScroll;
        return this;
    }

    /**
     * default touch scroll
     * @param position
     * @param amount
     * @return
     */
    @Override
    public boolean scroll(Vector2 position, int amount) {
        return spriteScroll.scroll(this,position,amount);
    }
}
