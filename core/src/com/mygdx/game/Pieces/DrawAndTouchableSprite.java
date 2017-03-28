package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 3/28/17.
 */

public class DrawAndTouchableSprite extends Sprite implements DrawAndTouchable {

    public DrawAndTouchableSprite(Texture texture){
        super(texture);
    }

    public DrawAndTouchableSprite(TextureRegion textureRegion){
        super(textureRegion);
    }

    // methods to implement drawAndTouchable that do nothing

    // return true if object contians position (aka is selected)
    public boolean contains(Vector2 position){
        return false;
    }

    // begin-touch action, return true if something changed, call requestRendering, this is safer
    public boolean touchBegin(Vector2 position){
        return false;
    }

    // do drag action, return true if something changed
    public boolean touchDrag(Vector2 position,Vector2 deltaPosition){
        return false;
    }

    // end of touch
    public boolean touchEnd(){
        return false;
    }

    //  position and scale the sprite

    /**
     * reduce the size of the sprite to world dimensions.
     * Factor is given number of pixels per meter (world unit)
     * @param pixelPerMeter
     */
    public void adjustSizeToPixelScale(float pixelPerMeter){
        setSize(getWidth()/pixelPerMeter,getHeight()/pixelPerMeter);
    }

    /**
     * set angle of sprite
     * @param angle in radians
     */
    public void setAngle(float angle){
        setRotation(angle * MathUtils.radiansToDegrees);
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


}
