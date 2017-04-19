package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 4/18/17.
 */

public class ExtensibleSprite extends Sprite implements Touchable {
    public Shape2D shape;

    /**
     * create with a texture (debug) a shape and the camera
     * @param texture
     * @param shape
     *
     */
    public ExtensibleSprite(Texture texture, Shape2D shape){
        super(texture);
        this.shape=shape;
    }

    /**
     * create with a textureRegion/atlasRegion and a shape
     * @param textureRegion
     * @param shape
     */

    public ExtensibleSprite(TextureRegion textureRegion, Shape2D shape){
        super(textureRegion);
        this.shape=shape;
    }

    /**
     * create with a texture (debug), without shape (test==false always)
     * @param texture
     */
    public ExtensibleSprite(Texture texture){
        super(texture);
    }

    /**
     * create with a textureRegion/atlasRegion, without shape (test==false always)
     * @param textureRegion
     */
    public ExtensibleSprite(TextureRegion textureRegion){
        super(textureRegion);
    }

    /**
     * set angle of sprite
     * @param angle in radians
     */
    public void setAngle(float angle){
        setRotation(angle * MathUtils.radiansToDegrees);
    }

    /**
     * get angle of sprite
     * @return  angle in radians
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
     * set the position of the sprite such that the origin (center of rotation)
     * lies at given world position (of center of mass)
     * @param worldOriginPositionX
     * @param worldOriginPositionY
     */
    public void setWorldOrigin(float worldOriginPositionX,float worldOriginPositionY){
        setPosition(worldOriginPositionX-getOriginX(),worldOriginPositionY-getOriginY());
    }

    /**
     * set the position of the sprite such that the origin (center of rotation)
     * lies at given position of center of mass
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

    // test if sprite contains a point

    public SpriteContains spriteContains =SpriteBasics.shapeContains;

    /**
     * set the test for shape test the point
     * @param spriteContains
     * @return
     */
    public ExtensibleSprite setSpriteContains(SpriteContains spriteContains){
        this.spriteContains = spriteContains;
        return this;
    }

    /**
     * test if sprite test the point, using both the texture region and the shape
     * @param x
     * @param y
     * @return
     */
    @Override
    public boolean contains(float x, float y) {
        return spriteContains.test(this, x, y);
    }

    /**
     * test if sprite contains the point
     * @param point
     * @return
     */
    @Override
    public boolean contains(Vector2 point) {
        return contains(point.x,point.y);
    }

    // what to do at begin of touch

    SpriteTouchBegin spriteTouchBegin=SpriteBasics.nullTouchBegin;

    /**
     * select the action for touch begin
     * @param spriteTouchBegin
     * @return
     */
    ExtensibleSprite setSpriteTouchBegin(SpriteTouchBegin spriteTouchBegin){
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
        return spriteTouchBegin.action(this,position);
    }

    // what to do for touch dragging around

    SpriteTouchDrag spriteTouchDrag=SpriteBasics.nullTouchDrag;

    /**
     * select the action for touch drag
     * @param spriteTouchDrag
     * @return
     */
    ExtensibleSprite setSpriteTouchDrag(SpriteTouchDrag spriteTouchDrag){
        this.spriteTouchDrag=spriteTouchDrag;
        return this;
    }

    /**
     * touch drag
     * @param position
     * @param deltaPosition
     * @return
     */
    @Override
    public boolean touchDrag(Vector2 position, Vector2 deltaPosition) {
        return spriteTouchDrag.action(this,position,deltaPosition);
    }





    @Override
    public boolean touchEnd(Vector2 position) {
        return false;
    }


    /**
     * default touch scroll
     * @param position
     * @param amount
     * @return
     */
    @Override
    public boolean scroll(Vector2 position, int amount) {
        if (contains(position.x,position.y)){
            return true;
        }
        return false;
    }

    public void keepVisible(){};
}
