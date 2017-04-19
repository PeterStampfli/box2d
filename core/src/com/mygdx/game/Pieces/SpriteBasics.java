package com.mygdx.game.Pieces;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utilities.L;

/**
 * Created by peter on 4/19/17.
 */

public class SpriteBasics {

    // create a single static instance of an anonymous class
    // takes less than defining a named static class and then creating a static instance of the class

    /**
     * test if the sprite rectangle contains the position and its shape (if exists)
     */
    static SpriteContains shapeContains=new SpriteContains() {
        @Override
        public boolean test(ExtensibleSprite sprite, float x, float y) {
            if (!sprite.getBoundingRectangle().contains(x,y)) return false;
            // shift that "origin" is at (0,0)
            x-=sprite.getWorldOriginX();
            y-=sprite.getWorldOriginY();
            float angleDeg=sprite.getRotation();
            float sinAngle= MathUtils.sinDeg(angleDeg);
            float cosAngle=MathUtils.cosDeg(angleDeg);
            // unrotate and unscale!
            // and shift to put lower left corner at (0,0)
            float unrotatedX=(cosAngle*x+sinAngle*y)/sprite.getScaleX()+sprite.getOriginX();
            float unrotatedY=(-sinAngle*x+cosAngle*y)/sprite.getScaleY()+sprite.getOriginY();
            // limit to texture/pixmap region and check the shape, if there is one
            boolean isInside=(unrotatedX>=0)&&(unrotatedX<=sprite.getWidth())
                    &&(unrotatedY>=0)&&(unrotatedY<=sprite.getHeight())
                    &&(sprite.shape==null||sprite.shape.contains(unrotatedX,unrotatedY));
            return isInside;
        }
    };

    /**
     * default touch begin: do nothing and return false, as nothing has been done
     */
    static SpriteTouchBegin nullTouchBegin=new SpriteTouchBegin() {
        @Override
        public boolean action(ExtensibleSprite sprite, Vector2 position) {
            L.og("touchbegin");
            return false;
        }
    };

    /**
     * default touch drag: do nothing and return false, as nothing has been done
     */
    static SpriteTouchDrag nullTouchDrag=new SpriteTouchDrag() {
        @Override
        public boolean action(ExtensibleSprite sprite, Vector2 position,Vector2 deltaPosition) {
            L.og("touchdrag");
            return false;
        }
    };

}
