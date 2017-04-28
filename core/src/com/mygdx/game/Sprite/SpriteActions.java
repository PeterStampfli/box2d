package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Basic (default) actions for extensible sprites,
 * defined as static instances of anonymous classes that implement the interfaces.
 */

public class SpriteActions {

    /**
     * An object that implements SpriteContains:
     * Does NOT check if the point is inside the sprite. Always returns false.
     * For sprites that do not interact.
     */
    static public SpriteContains nullContains = new SpriteContains() {
        @Override
        public boolean contains(com.mygdx.game.Sprite.ExtensibleSprite sprite, float x, float y) {
            return false;
        }
    };

    /**
     * An object that implements SpriteContains:
     * Check if the point is inside the rectangle of the shape TextureRegion
     * and the Shape2 shape of the sprite. If shape==null, then the shape is ignored.
     * Accounts for sprite translation, rotation and scaling.
     *
     * @param sprite ExtensibleSprite
     * @param x      float, x-coordinate of the point
     * @param y      float, y-coordinate of the point
     * @return boolean, true if the sprite contains the point
     */
    static public SpriteContains shapeContains = new SpriteContains() {
        @Override
        public boolean contains(com.mygdx.game.Sprite.ExtensibleSprite sprite, float x, float y) {
            // shift that "origin" is at (0,0)
            float maxOffset = sprite.getWidth() + sprite.getHeight();
            x -= sprite.getWorldOriginX();
            if (Math.abs(x) > maxOffset) {      // very safe, local origin inside sprite rectangle
                return false;
            }
            y -= sprite.getWorldOriginY();
            if (Math.abs(y) > maxOffset) {      // very safe, local origin inside sprite rectangle
                return false;
            }
            float angleDeg = sprite.getRotation();
            float sinAngle = MathUtils.sinDeg(angleDeg);
            float cosAngle = MathUtils.cosDeg(angleDeg);
            // unrotate and unscale!
            // and shift to put lower left corner at (0,0)
            float unrotatedX = (cosAngle * x + sinAngle * y) / sprite.getScaleX() + sprite.getOriginX();
            float unrotatedY = (-sinAngle * x + cosAngle * y) / sprite.getScaleY() + sprite.getOriginY();
            // limit to texture/pixmap region and check the masterShape, if there is one
            boolean isInside = (unrotatedX >= 0) && (unrotatedX <= sprite.getWidth())
                    && (unrotatedY >= 0) && (unrotatedY <= sprite.getHeight())
                    && (sprite.shape == null || sprite.shape.contains(unrotatedX, unrotatedY));
            return isInside;
        }
    };

    /**
     * An object that implements SpriteContains:
     * Check if the point is inside the rectangle of the shape TextureRegion
     * and the Shape2 shape of the sprite. If shape==null, then the shape is ignored.
     * For sprites that do not rotate or scale. Accounts only for sprite translation.
     *
     * @param sprite ExtensibleSprite
     * @param x      float, x-coordinate of the point
     * @param y      float, y-coordinate of the point
     * @return boolean, true if the sprite contains the point
     */
    static public SpriteContains withoutRotationShapeContains = new SpriteContains() {
        @Override
        public boolean contains(com.mygdx.game.Sprite.ExtensibleSprite sprite, float x, float y) {
            // shift that "origin" is at (0,0)
            x -= sprite.getWorldOriginX();
            y -= sprite.getWorldOriginY();
             // limit to texture/pixmap region and check the masterShape, if there is one
            boolean isInside = (x >= 0) && (x <= sprite.getWidth())
                    && (y >= 0) && (y <= sprite.getHeight())
                    && (sprite.shape == null || sprite.shape.contains(x, y));
            return isInside;
        }
    };

    /**
     * An object that implements SpriteDraw:
     * Does not draw anything.
     */
    static public SpriteDraw nullDraw = new SpriteDraw() {
        @Override
        public void draw(ExtensibleSprite sprite, Batch batch, Camera camera) {
        }
    };

    /**
     * An object that implements SpriteDraw:
     * Draws the basic sprite without extras.
     */
    static public SpriteDraw simpleDraw = new SpriteDraw() {
        @Override
        public void draw(ExtensibleSprite sprite, Batch batch, Camera camera) {
            sprite.superDraw(batch);
        }
    };

    /**
     * An object that implements SpriteKeepVisible:
     * Does nothing and returns false.
     */
    static public SpriteKeepVisible nullKeepVisible = new SpriteKeepVisible() {
        @Override
        public boolean keepVisible(ExtensibleSprite sprite, Camera camera) {
            return false;
        }
    };

    /**
     * An object that implements SpriteKeepVisible:
     * Makes that the origin of the sprite can be seen by the camera.
     * Shifts the sprite back if it is too far outside.
     */
    static public SpriteKeepVisible keepOriginVisible = new SpriteKeepVisible() {
        @Override
        public boolean keepVisible(ExtensibleSprite sprite, Camera camera) {
            float diff = sprite.getWorldOriginX() - camera.position.x;
            float half = 0.5f * camera.viewportWidth;
            boolean somethingChanged = false;
            if (diff < -half) {
                sprite.setWorldOriginX(camera.position.x - half);
                somethingChanged = true;
            } else if (diff > half) {
                sprite.setWorldOriginX(camera.position.x + half);
                somethingChanged = true;
            }
            diff = sprite.getWorldOriginY() - camera.position.y;
            half = 0.5f * camera.viewportHeight;
            if (diff < -half) {
                sprite.setWorldOriginY(camera.position.y - half);
                somethingChanged = true;
            } else if (diff > half) {
                sprite.setWorldOriginY(camera.position.y + half);
                somethingChanged = true;
            }
            return somethingChanged;
        }
    };

    /**
     * An object that implements SpriteTouchBegin:
     * Does nothing and returns false.
     */
    static public SpriteTouchBegin nullTouchBegin = new SpriteTouchBegin() {
        @Override
        public boolean touchBegin(ExtensibleSprite sprite, Vector2 position) {
            return false;
        }
    };

    /**
     * An object that implements SpriteTouchDrag:
     * Does nothing and returns false.
     */
    static public SpriteTouchDrag nullTouchDrag = new SpriteTouchDrag() {
        @Override
        public boolean touchDrag(ExtensibleSprite sprite, Vector2 position, Vector2 deltaPosition, Camera camera) {
            return false;
        }
    };

    /**
     * An object that implements SpriteTouchDrag:
     * Translates the sprite without rotation and keeps it visible.
     */
    static public SpriteTouchDrag touchDragTranslate = new SpriteTouchDrag() {
        @Override
        public boolean touchDrag(ExtensibleSprite sprite, Vector2 position, Vector2 deltaPosition, Camera camera) {
            sprite.translate(deltaPosition.x, deltaPosition.y);
            sprite.keepVisible(camera);
            return true;
        }
    };

    /**
     * An object that implements SpriteTouchDrag:
     * Translates and rotates the sprite and keeps it visible.
     */
    static public SpriteTouchDrag touchDragTransRotate = new SpriteTouchDrag() {
        @Override
        public boolean touchDrag(ExtensibleSprite sprite, Vector2 touchPosition, Vector2 deltaTouchPosition, Camera camera) {
            float centerTouchX = touchPosition.x - sprite.getWorldOriginX();
            float centerTouchY = touchPosition.y - sprite.getWorldOriginY();
            float centerTouchLength = Vector2.len(centerTouchX, centerTouchY);
            float centerTouchCrossDeltaTouch = centerTouchX * deltaTouchPosition.y - centerTouchY * deltaTouchPosition.x;
            float deltaAngle = MathUtils.atan2(centerTouchCrossDeltaTouch, centerTouchLength * centerTouchLength);
            deltaAngle *= 2 * centerTouchLength / (sprite.getWidth() * sprite.getScaleX() + sprite.getHeight() * sprite.getScaleY());
            sprite.setRotation(sprite.getRotation() + MathUtils.radiansToDegrees * deltaAngle);
            //  the rest
            float sinDeltaAngle = MathUtils.sin(deltaAngle);
            float cosDeltaAngle = MathUtils.cos(deltaAngle);
            sprite.translate(deltaTouchPosition.x - ((cosDeltaAngle - 1) * centerTouchX - sinDeltaAngle * centerTouchY),
                    deltaTouchPosition.y - (sinDeltaAngle * centerTouchX + (cosDeltaAngle - 1) * centerTouchY));

            sprite.keepVisible(camera);
            return true;
        }
    };

     /**
     * An object that implements SpriteTouchEnd:
     * Does nothing and returns false.
     */
    static public SpriteTouchEnd nullTouchEnd = new SpriteTouchEnd() {
        @Override
        public boolean touchEnd(ExtensibleSprite sprite, Vector2 position) {
            return false;
        }
    };

    /**
     * An object that implements SpriteTouchEnd:
     * Returns true if sprite contains the mouse position, but does nothing.
     * This prevents a scroll on sprites lying behind this sprite.
     */
    static public SpriteScroll nullScroll = new SpriteScroll() {
        @Override
        public boolean scroll(ExtensibleSprite sprite, Vector2 position, int amount) {
            if (sprite.contains(position.x, position.y)) {
                return true;
            }
            return false;
        }
    };
}