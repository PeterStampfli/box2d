package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 4/2/17.
 */

public class TouchableShapeSprite extends DrawableSprite implements Touchable {
    public Shape2D shape;

    /**
     * create with a texture (debug) and a shape
     * @param texture
     * @param shape
     */
    public TouchableShapeSprite(Texture texture, Shape2D shape){
        super(texture);
        this.shape=shape;
    }

    /**
     * create with a textureRegion/atlasRegion and a shape
     * @param textureRegion
     * @param shape
     */

    public TouchableShapeSprite(TextureRegion textureRegion, Shape2D shape){
        super(textureRegion);
        this.shape=shape;
    }

    /**
     * check if both the texture region and the shape contains a point, take into account rotation and scaling
     * @param x
     * @param y
     * @return
     */
    @Override
    public boolean contains(float x, float y){
        if (!getBoundingRectangle().contains(x,y)) return false;
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
                       &&shape.contains(unrotatedX,unrotatedY);
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
