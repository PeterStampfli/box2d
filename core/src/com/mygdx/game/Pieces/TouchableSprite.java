package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 4/2/17.
 */

public class TouchableSprite extends Sprite implements Touchable {
    public Shape2D shape;

    /**
     * create with a texture (debug) and a shape
     * @param texture
     * @param shape
     */
    public TouchableSprite(Texture texture, Shape2D shape){
        super(texture);
        this.shape=shape;
    }

    /**
     * create with a textureRegion/atlasRegion and a shape
     * @param textureRegion
     * @param shape
     */

    public TouchableSprite(TextureRegion textureRegion, Shape2D shape){
        super(textureRegion);
        this.shape=shape;
    }

    /**
     * create with a texture (debug), without shape (contains==false always)
     * @param texture
     */
    public TouchableSprite(Texture texture){
        super(texture);
    }

    /**
     * create with a textureRegion/atlasRegion, without shape (contains==false always)
     * @param textureRegion
     */

    public TouchableSprite(TextureRegion textureRegion){
        super(textureRegion);
    }


    //  position and scale the sprite

    /**
     * reduce the size of the sprite to world dimensions.
     * Factor is given number of pixels per unit (meter, physics world unit)
     * @param pixelPerUnit
     */
    public void adjustSizeToPixelScale(float pixelPerUnit){
        setSize(getWidth()/pixelPerUnit,getHeight()/pixelPerUnit);
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
     * check if both the texture region and the shape contains a point, take into account rotation and scaling
     * @param x
     * @param y
     * @return
     */
    @Override
    public boolean contains(float x, float y){
        if (shape==null||!getBoundingRectangle().contains(x,y)) return false;
        // shift that "origin" is at (0,0)
        x-=getX()+getOriginX();
        y-=getY()+getOriginY();
        float angleDeg=getRotation();
        float sinAngle= MathUtils.sinDeg(angleDeg);
        float cosAngle=MathUtils.cosDeg(angleDeg);
        // unrotate and unscale!
        // and shift to put lower left corner at (0,0)
        float unrotatedX=(cosAngle*x+sinAngle*y)/getScaleX()+getOriginX();
        float unrotatedY=(-sinAngle*x+cosAngle*y)/getScaleY()+getOriginY();
        // limit to texture/pixmap region and check the shape
        boolean isInside=(unrotatedX>=0)&&(unrotatedX<=getHeight())
                       &&(unrotatedY>=0)&&(unrotatedY<=getHeight())
                       &&shape.contains(unrotatedX,unrotatedY);     // do we have to scale ??
        return isInside;
    }

    /**
     * move the sprite by a simple translation
     * @param delta
     */
    public void translate(Vector2 delta){
        translate(delta.x,delta.y);
    }

    /**
     * move the sprite by rotation and translation
     * thus we can change the orientation of the sprite
     * @param touchPosition
     * @param deltaTouchPosition
     */
    public void transRotate(Vector2 touchPosition,Vector2 deltaTouchPosition){
        float centerTouchX=touchPosition.x-getX()-getOriginX();
        float centerTouchY=touchPosition.y-getY()-getOriginY();
        float centerTouchLength=Vector2.len(centerTouchX,centerTouchY);
        float centerTouchCrossDeltaTouch=centerTouchX*deltaTouchPosition.y-centerTouchY*deltaTouchPosition.x;
        float deltaAngle=MathUtils.atan2(centerTouchCrossDeltaTouch,centerTouchLength*centerTouchLength);
        deltaAngle*=2*centerTouchLength/(getWidth()*getScaleX()+getHeight()*getScaleY());
        setRotation(getRotation()+MathUtils.radiansToDegrees*deltaAngle);
        //  the rest
        float sinDeltaAngle=MathUtils.sin(deltaAngle);
        float cosDeltaAngle=MathUtils.cos(deltaAngle);
        translateX(deltaTouchPosition.x-((cosDeltaAngle-1)*centerTouchX-sinDeltaAngle*centerTouchY));
        translateY(deltaTouchPosition.y-(sinDeltaAngle*centerTouchX+(cosDeltaAngle-1)*centerTouchY));
    }


    @Override
    public boolean contains(Vector2 point) {
        return contains(point.x,point.y);
    }

    @Override
    public boolean touchBegin(Vector2 position) {
        return false;
    }

    @Override
    public boolean touchDrag(Vector2 position, Vector2 deltaPosition) {
        transRotate(position,deltaPosition);
        return true;
    }

    @Override
    public boolean touchEnd() {
        return false;
    }

}
