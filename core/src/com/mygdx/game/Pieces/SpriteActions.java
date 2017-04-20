package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.utilities.L;

/**
 * Created by peter on 4/19/17.
 */

public class SpriteActions {
    static public Camera camera;

    /**
     * set the camera for checking visibility
     * @param camera
     */
    static public void setCamera(Camera camera){
        SpriteActions.camera=camera;
    }

    /**
     * set the camera for checking visibility
     * @param viewport
     */
    static public void setCamera(Viewport viewport){
        setCamera(viewport.getCamera());
    }

    // create a single static instance of an anonymous class
    // takes less than defining a named static class and then creating a static instance of the class

    /**
     * contains if the sprite rectangle contains the position and its shape (if exists)
     */
    static public SpriteContains shapeContains=new SpriteContains() {
        @Override
        public boolean contains(ExtensibleSprite sprite, float x, float y) {
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
     * draw the sprite wthout extras
     */
    static public SpriteDraw simpleDraw=new SpriteDraw() {
        @Override
        public void draw(ExtensibleSprite sprite, Batch batch) {
            sprite.superDraw(batch);
        }
    };

    /**
     * keep the sprite visible - here does nothing and thus returns false
     * to do something needs a camera
     */
    static public SpriteKeepVisible nullKeepVisible=new SpriteKeepVisible() {
        @Override
        public boolean keepVisible(ExtensibleSprite sprite) {
            L.og("keep visible");
            return false;
        }
    };

    /**
     * make that the origin of the sprite can be seen by the camera
     * avoid unneeded function calls
     */
    static public SpriteKeepVisible keepOriginVisible=new SpriteKeepVisible() {
        @Override
        public boolean keepVisible(ExtensibleSprite sprite) {
            float diff=sprite.getWorldOriginX()-camera.position.x;
            float half=0.5f*camera.viewportWidth;
            boolean somethingChanged=false;
            if (diff<-half){
                sprite.setWorldOriginX(camera.position.x-half);
                somethingChanged=true;
            }
            else if (diff>half){
                sprite.setWorldOriginX(camera.position.x+half);
                somethingChanged=true;
            }
            diff=sprite.getWorldOriginY()-camera.position.y;
            half=0.5f*camera.viewportHeight;
            if (diff<-half){
                sprite.setWorldOriginY(camera.position.y-half);
                somethingChanged=true;
            }
            else if (diff>half){
                sprite.setWorldOriginY(camera.position.y+half);
                somethingChanged=true;
            }
            return somethingChanged;
        }
    };

    /**
     * default touch begin: do nothing and return false, as nothing has been done
     */
    static public SpriteTouchBegin nullTouchBegin=new SpriteTouchBegin() {
        @Override
        public boolean touchBegin(ExtensibleSprite sprite, Vector2 position) {
            L.og("touchbegin");
            return false;
        }
    };

    /**
     * default touch drag: do nothing and return false, as nothing has been done
     */
    static public SpriteTouchDrag nullTouchDrag=new SpriteTouchDrag() {
        @Override
        public boolean touchDrag(ExtensibleSprite sprite, Vector2 position, Vector2 deltaPosition) {
            L.og("touchdrag");
            return false;
        }
    };

    /**
     * touch drag translation without rotation
     * keep sprite visible
     */
    static public SpriteTouchDrag touchDragTranslate=new SpriteTouchDrag() {
        @Override
        public boolean touchDrag(ExtensibleSprite sprite, Vector2 position, Vector2 deltaPosition) {
            L.og("touchdrag translate");
            sprite.translate(deltaPosition.x,deltaPosition.y);
            sprite.keepVisible();
            return false;
        }
    };

    /**
     * move the sprite by rotation and translation
     * thus we can change the orientation of the sprite
     * keep sprite visible
     */
    static public SpriteTouchDrag touchDragTransRotate=new SpriteTouchDrag() {
        @Override
        public boolean touchDrag(ExtensibleSprite sprite, Vector2 touchPosition, Vector2 deltaTouchPosition) {
            float centerTouchX=touchPosition.x-sprite.getWorldOriginX();
            float centerTouchY=touchPosition.y-sprite.getWorldOriginY();
            float centerTouchLength=Vector2.len(centerTouchX,centerTouchY);
            float centerTouchCrossDeltaTouch=centerTouchX*deltaTouchPosition.y-centerTouchY*deltaTouchPosition.x;
            float deltaAngle=MathUtils.atan2(centerTouchCrossDeltaTouch,centerTouchLength*centerTouchLength);
            deltaAngle*=2*centerTouchLength/(sprite.getWidth()*sprite.getScaleX()+sprite.getHeight()*sprite.getScaleY());
            sprite.setRotation(sprite.getRotation()+MathUtils.radiansToDegrees*deltaAngle);
            //  the rest
            float sinDeltaAngle=MathUtils.sin(deltaAngle);
            float cosDeltaAngle=MathUtils.cos(deltaAngle);
            sprite.translate(deltaTouchPosition.x-((cosDeltaAngle-1)*centerTouchX-sinDeltaAngle*centerTouchY),
                             deltaTouchPosition.y-(sinDeltaAngle*centerTouchX+(cosDeltaAngle-1)*centerTouchY));

            sprite.keepVisible();
            return true;
        }
    };

    /**
     * default touch end: do nothing and return false, as nothing has been done
     */
    static public SpriteTouchEnd nullTouchEnd=new SpriteTouchEnd() {
        @Override
        public boolean touchEnd(ExtensibleSprite sprite, Vector2 position) {
            L.og("touchend");
            return false;
        }
    };

    /**
     * returns true if sprite contains mouse position, but does nothing
     * this prevents scroll on sprites lying behind this sprite
     * after scroll always update image
     */
    static public SpriteScroll nullScroll=new SpriteScroll() {
        @Override
        public boolean scroll(ExtensibleSprite sprite, Vector2 position, int amount) {
            if (sprite.contains(position.x,position.y)){
                L.og("scroll");
                return true;
            }
            return false;
        }
    };



}
