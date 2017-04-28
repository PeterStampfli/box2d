package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Pieces.Touchable;

/**
 * A basic sprite that can be extended. Use the ExtensibleSpriteBuilder for creation.
 */

public class ExtensibleSprite extends Sprite implements Touchable {

    public Shape2D shape;
    public Pool<ExtensibleSprite> extensibleSpritePool;
    // a composite extension
    public TextExtension textExtension;
    // the basic actions that can be extended
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
        if (textExtension != null) {
            textExtension.free();
            textExtension = null;
        }
        extensibleSpritePool.free(this);
    }

    /**
     * Set the text of the sprite if there is a text extension.
     *
     * @param text String, the text to set
     */
    public void setText(String text) {
        if (textExtension != null) textExtension.setText(text, this);
    }

    /**
     * get the angle of the sprite
     *
     * @return float, tha angle in radians
     */
    public float getAngle() {
        return getRotation() / MathUtils.radiansToDegrees;
    }

    /**
     * Set the Angle of sprite using radians.
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
     * @param worldOriginPosition
     */
    public void setWorldOrigin(Vector2 worldOriginPosition) {
        setWorldOrigin(worldOriginPosition.x, worldOriginPosition.y);
    }

    /**
     * Get the x-coordinate of the sprite origin (center of rotation and scaling).
     *
     * @return float, x-coordinate
     */
    public float getWorldOriginX() {
        return getX() + getOriginX();
    }

    /**
     * Set the x-coordinate of the sprite origin (center of rotation and scaling).
     *
     * @param x  float, x-coordinate
     */
    public void setWorldOriginX(float x) {
        setX(x - getOriginX());
    }

    /**
     * Get the y-coordinate of the sprite origin (center of rotation and scaling).
     *
     * @return float, y-coordinate
     */
    public float getWorldOriginY() {
        return getY() + getOriginY();
    }

    /**
     * Set the x-coordinate of the sprite origin (center of rotation and scaling).
     *
     * @param y float, y-coordinate
     */
    public void setWorldOriginY(float y) {
        setY(y - getOriginY());
    }

    // the composable actions

    /**
     * Set the object that implements SpriteContains.
     *
     * @param spriteContains SpriteContains, an object that implements SpriteContains
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
     * set the method for sprite draw
     *
     * @param spriteDraw
     * @return
     */
    public ExtensibleSprite setDraw(SpriteDraw spriteDraw) {
        this.spriteDraw = spriteDraw;
        return this;
    }

    /**
     * draw the basic batch using the method of the superclass "Sprite"
     * need this as basis for decorations
     *
     * @param batch
     */
    public void superDraw(Batch batch) {
        super.draw(batch);
    }

    /**
     * draw the sprite, with decos ? text!
     *
     * @param batch
     * @param camera
     */
    @Override
    public void draw(Batch batch, Camera camera) {
        spriteDraw.draw(this, batch, camera);
    }


    // what to do to keep sprite visible

    /**
     * set instance method for keep visible
     *
     * @param spriteKeepVisible
     * @return
     */
    public ExtensibleSprite setKeepVisible(SpriteKeepVisible spriteKeepVisible) {
        this.spriteKeepVisible = spriteKeepVisible;
        return this;
    }

    @Override
    public boolean keepVisible(Camera camera) {
        return spriteKeepVisible.keepVisible(this, camera);
    }

    ;

    // what to do at begin of touch


    /**
     * select the draw for touch begin
     *
     * @param spriteTouchBegin
     * @return
     */
    public ExtensibleSprite setTouchBegin(SpriteTouchBegin spriteTouchBegin) {
        this.spriteTouchBegin = spriteTouchBegin;
        return this;
    }

    /**
     * make something at begin of touch, return true if something changed
     *
     * @param position
     * @return
     */
    @Override
    public boolean touchBegin(Vector2 position) {
        return spriteTouchBegin.touchBegin(this, position);
    }

    // what to do for touch dragging around

    /**
     * select the draw for touch drag
     *
     * @param spriteTouchDrag
     * @return
     */
    public ExtensibleSprite setTouchDrag(SpriteTouchDrag spriteTouchDrag) {
        this.spriteTouchDrag = spriteTouchDrag;
        return this;
    }

    /**
     * touch drag
     *
     * @param position
     * @param deltaPosition
     * @param camera
     * @return
     */
    @Override
    public boolean touchDrag(Vector2 position, Vector2 deltaPosition, Camera camera) {
        return spriteTouchDrag.touchDrag(this, position, deltaPosition, camera);
    }

    // what to do at end of touch

    /**
     * select the draw for touch begin
     *
     * @param spriteTouchEnd
     * @return
     */
    public ExtensibleSprite setTouchEnd(SpriteTouchEnd spriteTouchEnd) {
        this.spriteTouchEnd = spriteTouchEnd;
        return this;
    }

    /**
     * make something at end of touch, return true if something changed
     *
     * @param position
     * @return
     */
    @Override
    public boolean touchEnd(Vector2 position) {
        return spriteTouchEnd.touchEnd(this, position);
    }

    // waht to do at scroll event

    /**
     * set the scroll draw
     *
     * @param spriteScroll
     * @return
     */
    public ExtensibleSprite setScroll(SpriteScroll spriteScroll) {
        this.spriteScroll = spriteScroll;
        return this;
    }

    /**
     * default touch scroll
     *
     * @param position
     * @param amount
     * @return
     */
    @Override
    public boolean scroll(Vector2 position, int amount) {
        return spriteScroll.scroll(this, position, amount);
    }
}
