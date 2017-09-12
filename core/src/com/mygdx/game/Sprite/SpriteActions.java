package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Basic (default) actions for extensible sprites,
 * Defined as instances of anonymous classes that implement the interfaces.
 * Have no data to remember, thus one single object does it for all.
 */

/* reuse the methods of the objects for more complicated actions. Like that:

		physicalSprite.setDraw(new SpriteDraw() {
			int count=0;
			@Override
			public void draw(ExtensibleSprite sprite) {
				count++;
				L.og("hallo "+count);
				SpriteActions.draw.draw(sprite);
			}
		});

    this is more explicit and flexible than using a decorator pattern on extensible sprite
    or extending the sprite class ...
 */

public class SpriteActions {
    /**
     * An object that implements SpriteContains:
     * Does NOT check if the point is inside the sprite. Always returns false.
     * For sprites that do not interact.
     */
    static public SpriteContains containsNull = new SpriteContains() {
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
     */
    static public SpriteContains containsTransRotate = new SpriteContains() {
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
            // un-rotate and un-scale!
            // and shift to put lower left corner at (0,0)
            float unrotatedX = (cosAngle * x + sinAngle * y) / sprite.getScaleX() + sprite.getOriginX();
            float unrotatedY = (-sinAngle * x + cosAngle * y) / sprite.getScaleY() + sprite.getOriginY();
            // limit to texture/pixmap region and check the masterShape, if there is one
            return (unrotatedX >= 0) && (unrotatedX <= sprite.getWidth())
                    && (unrotatedY >= 0) && (unrotatedY <= sprite.getHeight())
                    && (sprite.shape == null || sprite.shape.contains(unrotatedX, unrotatedY));
        }
    };

    /**
     * An object that implements SpriteContains:
     * Check if the point is inside the rectangle of the shape TextureRegion
     * and the Shape2 shape of the sprite. If shape==null, then the shape is ignored.
     * For sprites that do not rotate or scale. Accounts only for sprite translation.
     */
    static public SpriteContains containsTranslate = new SpriteContains() {
        @Override
        public boolean contains(com.mygdx.game.Sprite.ExtensibleSprite sprite, float x, float y) {
            // shift that "origin" is at (0,0)
            x -= sprite.getX();
            y -= sprite.getY();
            // limit to texture/pixmap region and check the masterShape, if there is one
            return (x >= 0) && (x <= sprite.getWidth())
                    && (y >= 0) && (y <= sprite.getHeight())
                    && (sprite.shape == null || sprite.shape.contains(x, y));
        }
    };

    /**
     * An object that implements SpriteDraw:
     * Does not draw anything.
     */
    static public SpriteDraw drawNull = new SpriteDraw() {
        @Override
        public void draw(ExtensibleSprite sprite) {
        }
    };

    /**
     * An object that implements SpriteDraw:
     * Draws the basic sprite without extras.
     */
    static public SpriteDraw draw = new SpriteDraw() {
        @Override
        public void draw(ExtensibleSprite sprite) {
            sprite.draw(sprite.device.spriteBatch);
        }
    };

    /**
     * An object that implements SpriteKeepVisible:
     * Does nothing and returns false.
     */
    static public SpriteKeepVisible keepVisibleNull = new SpriteKeepVisible() {
        @Override
        public void keepVisible(ExtensibleSprite sprite) {
        }
    };

    /**
     * An object that implements SpriteKeepVisible:
     * Makes that the origin of the sprite can be seen by the camera.
     * Shifts the sprite back if it is too far outside.
     */
    static public SpriteKeepVisible keepVisibleOrigin = new SpriteKeepVisible() {
        @Override
        public void keepVisible(ExtensibleSprite sprite) {
            Camera camera=sprite.device.camera;
            float diff = sprite.getWorldOriginX() - camera.position.x;
            float half = 0.5f * camera.viewportWidth;
            if (diff < -half) {
                sprite.setWorldOriginX(camera.position.x - half);
            } else if (diff > half) {
                sprite.setWorldOriginX(camera.position.x + half);
            }
            diff = sprite.getWorldOriginY() - camera.position.y;
            half = 0.5f * camera.viewportHeight;
            if (diff < -half) {
                sprite.setWorldOriginY(camera.position.y - half);
            } else if (diff > half) {
                sprite.setWorldOriginY(camera.position.y + half);
            }
        }
    };

    /**
     * An object that implements SpriteKeepVisible:
     * Makes that the texture region of the sprite can be seen by the camera.
     * Shifts the sprite back if it is too far outside.
     *
     * using a simple estimate for the extension of the texture region around the local center:
     * Maximum distance between (local) Origin and corners
     *
     * if the sprite can rotate, then more precise becomes very expensive
     */
    static public SpriteKeepVisible keepVisibleTexture = new SpriteKeepVisible() {
        @Override
        public void keepVisible(ExtensibleSprite sprite) {
            Camera camera=sprite.device.camera;
            float diff = sprite.getWorldOriginX() - camera.position.x;
            float dx=Math.max(sprite.getOriginX(),sprite.getWidth()-sprite.getOriginX());
            float dy=Math.max(sprite.getOriginY(),sprite.getHeight()-sprite.getOriginY());
            float maxDistance=(float) Math.sqrt(dx*dx+dy*dy);
            float halfFree = 0.5f * camera.viewportWidth-maxDistance;
            if (diff < -halfFree) {
                sprite.setWorldOriginX(camera.position.x - halfFree);
            } else if (diff > halfFree) {
                sprite.setWorldOriginX(camera.position.x + halfFree);
            }
            diff = sprite.getWorldOriginY() - camera.position.y;
            halfFree = 0.5f * camera.viewportHeight-maxDistance;
            if (diff < -halfFree) {
                sprite.setWorldOriginY(camera.position.y - halfFree);
            } else if (diff > halfFree) {
                sprite.setWorldOriginY(camera.position.y + halfFree);
            }
        }
    };

    /**
     * An object that implements SpriteTouchBegin:
     * Does nothing.
     */
    static public SpriteTouchBegin touchBeginNull = new SpriteTouchBegin() {
        @Override
        public void touchBegin(ExtensibleSprite sprite, Vector2 position) {
        }
    };
    /**
     * An object that implements SpriteTouchDrag:
     * Does nothing.
     */
    static public SpriteTouchDrag touchDragNull = new SpriteTouchDrag() {
        @Override
        public void touchDrag(ExtensibleSprite sprite, Vector2 position, Vector2 deltaPosition) {
        }
    };

    /**
     * An object that implements SpriteTouchDrag:
     * Translates the sprite without rotation.
     */
    static public SpriteTouchDrag touchDragTranslate = new SpriteTouchDrag() {
        @Override
        public void touchDrag(ExtensibleSprite sprite, Vector2 position, Vector2 deltaPosition) {
            sprite.translate(deltaPosition.x, deltaPosition.y);
        }
    };

    /**
     * An object that implements SpriteTouchDrag:
     * Translates and rotates the sprite.
     */
    static public SpriteTouchDrag touchDragTransRotate = new SpriteTouchDrag() {
        @Override
        public void touchDrag(ExtensibleSprite sprite, Vector2 touchPosition, Vector2 deltaTouchPosition) {
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
        }
    };

    /**
     * An object that implements SpriteTouchEnd:
     * Does nothing and returns false.
     */
    static public SpriteTouchEnd touchEndNull = new SpriteTouchEnd() {
        @Override
        public void touchEnd(ExtensibleSprite sprite) {
        }
    };
    /**
     * An object that implements SpriteTouchEnd:
     * Returns true if sprite contains the mouse position, but does nothing.
     * This prevents a scroll on sprites lying behind this sprite.
     */
    static public SpriteScroll scrollNull = new SpriteScroll() {
        @Override
        public void scroll(ExtensibleSprite sprite, int amount) {
        }
    };

}