package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 3/28/17.
 * A sprite that explicitely implements drawable
 * together with functions to set the scale, the angle and the origin for rotation and scaling
 */

public class DrawableSprite extends Sprite implements Drawable {

    public DrawableSprite(Texture texture){
        super(texture);
    }

    public DrawableSprite(TextureRegion textureRegion){
        super(textureRegion);
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
}
