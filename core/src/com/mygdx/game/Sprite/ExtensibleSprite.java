package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Pieces.Touchable;
import com.mygdx.game.utilities.Device;

/**
 * A basic sprite that can be extended. With the strategy pattern for the basic actions.
 * Use the ExtensibleSpriteBuilder for creation.
 */

public class ExtensibleSprite extends Sprite implements Touchable {

    public Shape2D shape;
    public Device device;
    public Array<Object> extensions=new Array<Object>();
    public SpriteContains spriteContains;
    public SpriteDraw spriteDraw;
    public SpriteKeepVisible spriteKeepVisible;
    public SpriteTouchBegin spriteTouchBegin;
    public SpriteTouchDrag spriteTouchDrag;
    public SpriteTouchEnd spriteTouchEnd;
    public SpriteScroll spriteScroll;

    /**
     * Reset the sprite and put it back in the pool. Frees the text extension.
     */
    public void free() {
        shape = null;
        setTexture(null);
        for (Object extension:extensions){
            if (extension instanceof TextExtension){
                device.glyphLayoutPool.free(((TextExtension) extension).glyphLayout);
            }
        }
        device.extensibleSpritePool.free(this);
    }

    /**
     * Add an object to the array of extensions.
     *
     * @param extension Object, the new extension to add
     */
    public void addExtension(Object extension){
        extensions.add(extension);
    }

    /**
     * Set the text of text extensions.
     *
     * @param text String, the text to set
     */
    public void setText(String text) {
        for (Object extension : extensions) {
            if (extension instanceof TextExtension) {
                ((TextExtension) extension).setText(text,this);
            }
        }
    }

    /**
     * Get the angle of the sprite.
     *
     * @return float, the angle in radians
     */
    public float getAngle() {
        return getRotation() / MathUtils.radiansToDegrees;
    }

    /**
     * Set the Angle of the sprite using radians.
     *
     * @param angle float, in radians
     */
    public void setAngle(float angle) {
        setRotation(angle * MathUtils.radiansToDegrees);
    }

    /**
     * Make that the sprite angle is a multiple of 360/n degrees. Use 4 for a square lattice.
     *
     * @param n int, number of different sprite orientations
     */
    public void quantizeAngle(int n) {
        setRotation(360f / n * Math.round(n * getRotation() / 360f));
    }

    /**
     * Set the origin (center of rotation and scaling) in local coordinates without translation.
     * The origin is always the vector from the sprite position to the center of rotation
     * and scaling, independent of rotation and scaling.
     * Zero is left bottom corner of the unrotated, unscaled Textureregion.
     * Uses graphics lengths. The length of a pixel is the unit.
     *
     * @param centerX float, x-coordinate of the origin
     * @param centerY float, y-coordinate of the origin
     */
    public void setLocalOrigin(float centerX, float centerY) {
        setOrigin(centerX, centerY);
    }

    /**
     * Set the origin (center of rotation and scaling) in local coordinates without translation.
     * The origin is always the vector from the sprite position to the center of rotation
     * and scaling, independent of rotation and scaling.
     * Zero is left bottom corner of the unrotated, unscaled Textureregion.
     * Uses graphics lengths. The length of a pixel is the unit.
     *
     * @param center Vector2, position of the origin
     */
    public void setLocalOrigin(Vector2 center) {
        setLocalOrigin(center.x, center.y);
    }

    /**
     * Set the position of the sprite such that the origin (center of rotation and scaling)
     * lies at a given world position.
     *
     * @param worldOriginPositionX float, x-coordinate of the origin
     * @param worldOriginPositionY float, y-coordinate of the origin
     */
    public void setWorldOrigin(float worldOriginPositionX, float worldOriginPositionY) {
        setPosition(worldOriginPositionX - getOriginX(), worldOriginPositionY - getOriginY());
    }

    /**
     * Set the position of the sprite such that the origin (center of rotation and scaling)
     * lies at a given world position.
     *
     * @param worldOriginPosition Vector2, position for the origin
     */
    public void setWorldOrigin(Vector2 worldOriginPosition) {
        setWorldOrigin(worldOriginPosition.x, worldOriginPosition.y);
    }

    /**
     * Get the x-coordinate of the sprite origin (center of rotation and scaling).
     *
     * @return float, x-coordinate of the origin
     */
    public float getWorldOriginX() {
        return getX() + getOriginX();
    }

    /**
     * Set the x-coordinate of the sprite origin (center of rotation and scaling).
     *
     * @param x  float, x-coordinate of the origin
     */
    public void setWorldOriginX(float x) {
        setX(x - getOriginX());
    }

    /**
     * Get the y-coordinate of the sprite origin (center of rotation and scaling).
     *
     * @return float, y-coordinate of the origin
     */
    public float getWorldOriginY() {
        return getY() + getOriginY();
    }

    /**
     * Set the x-coordinate of the sprite origin (center of rotation and scaling).
     *
     * @param y float, y-coordinate of the origin
     */
    public void setWorldOriginY(float y) {
        setY(y - getOriginY());
    }

    // the composable actions

    /**
     * Set the object that implements SpriteContains.
     *
     * @param spriteContains SpriteContains, an object that has a contains method
     */
    public void setContains(SpriteContains spriteContains) {
        this.spriteContains = spriteContains;
    }

    /**
     * Check if the sprite contains a point. Uses the contains method of the SpriteContains object.
     *
     * @param x float, x-coordinate of the point
     * @param y float, x-coordinate of the point
     * @return boolean, the result of the contains-method
     */
    @Override
    public boolean contains(float x, float y) {
        return this.spriteContains.contains(this, x, y);
    }

    /**
     * Check if the sprite contains a point. Uses the contains method of the SpriteContains object.
     *
     * @param point, Vector2, position of the point
     * @return boolean, the result of the contains-method
     */
    @Override
    public boolean contains(Vector2 point) {
        return contains(point.x, point.y);
    }

    /**
     * Set the object that implements SpriteDraw
     *
     * @param spriteDraw SpriteDraw, object with a draw method
     */
    public void setDraw(SpriteDraw spriteDraw) {
        this.spriteDraw = spriteDraw;
    }

    /**
     * A hook to the draw method of the superclass Sprite.
     *
     * @param batch Batch, for drawing
     */
    public void superDraw(Batch batch) {
        super.draw(batch);
    }

    /**
     * Draw the extended sprite with the draw method of the spriteDraw object.
     * Can use the decorator pattern.
     *
     * @param batch Batch, for drawing
     * @param camera Camera, to keep sprite visible.
     */
    @Override
    public void draw(Batch batch, Camera camera) {
        spriteDraw.draw(this, batch, camera);
    }

    /**
     * Set the object implementing SpriteKeepVisible
     *
     * @param spriteKeepVisible SpriteKeepVisible
     */
    public void setKeepVisible(SpriteKeepVisible spriteKeepVisible) {
        this.spriteKeepVisible = spriteKeepVisible;
    }

    /**
     * Keep the sprite visible using the keepVisible method of the spriteKeepVisible object.
     *
     * @param camera Camera, current in use. To decide of sprite is visible and to determine repositioning of the sprite.
     * @return
     */
    @Override
    public boolean keepVisible(Camera camera) {
        return spriteKeepVisible.keepVisible(this, camera);
    }

    /**
     * Set the object that implements SpriteTouchBegin
     *
     * @param spriteTouchBegin SpriteTouchBegin object
     */
    public void setTouchBegin(SpriteTouchBegin spriteTouchBegin) {
        this.spriteTouchBegin = spriteTouchBegin;
    }

    /**
     * Call the touchBegin method of the spriteTouchBegin object.
     *
     * @param position Vector2, position of touch.
     * @return boolean, true if something changed
     */
    @Override
    public boolean touchBegin(Vector2 position) {
        return spriteTouchBegin.touchBegin(this, position);
    }

    /**
     * Set the object implementing SpriteTouchDrag
     *
     * @param spriteTouchDrag SpriteTouchDrag object with touchDrag method
     */
    public void setTouchDrag(SpriteTouchDrag spriteTouchDrag) {
        this.spriteTouchDrag = spriteTouchDrag;
    }

    /**
     * Call the touchDrag method of the spriteTouchDrag object.
     *
     * @param position      Vector2, position of touch.
     * @param deltaPosition Vector2, change in the position of touch.
     * @param camera        Camera current in use. For keeping the sprite visible.
     * @return boolean, true if something changed
     */
    @Override
    public boolean touchDrag(Vector2 position, Vector2 deltaPosition, Camera camera) {
        return spriteTouchDrag.touchDrag(this, position, deltaPosition, camera);
    }

    /**
     * Set the object implementing SpriteTouchEnd.
     *
     * @param spriteTouchEnd SpriteTouchEnd with touchEnd method
     */
    public void setTouchEnd(SpriteTouchEnd spriteTouchEnd) {
        this.spriteTouchEnd = spriteTouchEnd;
    }

    /**
     * Call the touchEnd method of the spriteTouchEnd object.
     *
     * @param position Vector2, position of touch.
     * @return boolean, true if something changed
     */
    @Override
    public boolean touchEnd(Vector2 position) {
        return spriteTouchEnd.touchEnd(this, position);
    }

    /**
     * Set the object that implement SpriteScroll
     *
     * @param spriteScroll SpriteScroll object with a scroll method.
     */
    public void setScroll(SpriteScroll spriteScroll) {
        this.spriteScroll = spriteScroll;
    }


    /**
     * Call the scroll method of the spriteScroll object.
     *
     * @param position Vector2, position of the mouse (pc only).
     * @param amount   int, tells if the wheel turns up or down.
     * @return boolean, true if the sprite contains the mouse position.
     */
    @Override
    public boolean scroll(Vector2 position, int amount) {
        return spriteScroll.scroll(this, position, amount);
    }
}
