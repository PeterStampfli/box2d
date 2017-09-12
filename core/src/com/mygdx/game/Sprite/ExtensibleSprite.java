package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Pieces.Drawable;
import com.mygdx.game.Pieces.Touchable;
import com.mygdx.game.utilities.Device;

/**
 * A basic sprite that can be extended. With the strategy pattern for the basic actions.
 * Use the ExtensibleSpriteBuilder for creation.
 */

public class ExtensibleSprite extends Sprite implements Touchable,Drawable,Shape2D,Pool.Poolable {

    public Shape2D shape;
    public Device device;
    public com.mygdx.game.TextSprite.TextExtension textExtension;
    public com.mygdx.game.Buttons.ButtonExtension buttonExtension;
    public SpriteContains spriteContains;
    public SpriteDraw spriteDraw;
    public SpriteKeepVisible spriteKeepVisible;

    public SpriteTouchBegin spriteTouchBegin;
    public SpriteTouchDrag spriteTouchDrag;
    public SpriteTouchEnd spriteTouchEnd;
    public SpriteScroll spriteScroll;

    /**
     * Reset the sprite before putting it back in the pool. Disposes extensions.
     */
    public void reset(){
        if (textExtension!=null){
            textExtension.free();
            textExtension=null;
        }
        if (buttonExtension!=null){
            buttonExtension.free();
            buttonExtension=null;
        }
        shape = null;
        setTexture(null);
        setContains(null);
        setKeepVisible(null);
        setTouchBegin(null);
        setTouchEnd(null);
        setScroll(null);
    }

    /**
     * Put the sprite back to the pool. Calls reset in the process.
     */
    public void free() {
        device.extensibleSpritePool.free(this);
    }

    /**
     * Set the text of a text extension.
     *
     * @param theText String, text to set
     */
    public void setText(String theText){
        textExtension.setText(theText);
    }

    /**
     * set the position of the lower left corner
     *
     * @param x float, coordinate
     * @param y float, coordinate
     */
    public void setPosition(float x,float y){
        super.setPosition(x,y);
    }

    /**
     * set the position of the lower left corner
     *
     * @param position as Vector2
     */
    public void setPosition(Vector2 position){
        super.setPosition(position.x,position.y);
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
        super.setRotation(angle * MathUtils.radiansToDegrees);
    }

    /**
     * Set the position and angle of sprite
     *
     * @param x float, x-coordinate of the position
     * @param y float, y-coordinate of the position
     * @param angle float, angle in radians
     */
    public void setPositionAngle(float x,float y,float angle){
        super.setPosition(x,y);
        super.setRotation(angle * MathUtils.radiansToDegrees);
    }

    /**
     * Set the position and angle of sprite
     *
     * @param position Vector2, position
     * @param angle float, angle in radians
     */
    public void setPositionAngle(Vector2 position,float angle){
        super.setPosition(position.x,position.y);
        super.setRotation(angle * MathUtils.radiansToDegrees);
    }

    /**
     * Make that the sprite angle is a multiple of 360/n degrees. Use 4 for a square lattice.
     *
     * @param n int, number of different sprite orientations
     */
    public void quantizeAngle(int n) {
        super.setRotation(360f / n * Math.round(n * getRotation() / 360f));
    }

    /**
     * Set the origin (center of rotation and scaling) in local coordinates without translation.
     * The origin is always the vector from the sprite position to the center of rotation
     * and scaling, independent of rotation and scaling.
     * Zero is left bottom corner of the unrotated, unscaled TextureRegion.
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
     * Zero is left bottom corner of the unrotated, unscaled TextureRegion.
     * Uses graphics lengths. The length of a pixel is the unit.
     *
     * @param center Vector2, position of the origin
     */
    public void setLocalOrigin(Vector2 center) {
        setOrigin(center.x, center.y);
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
        super.setX(x - getOriginX());
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
        super.setY(y - getOriginY());
    }

    /**
     * get world origin as vector
     *
     * @param vector will be set to world origin
     * @return vector, for chaining
     */
    public Vector2 getWorldOrigin(Vector2 vector){
        vector.set(getWorldOriginX(),getWorldOriginY());
        return vector;
    }

    /**
     * Set the position of the sprite such that the origin (center of rotation and scaling)
     * lies at a given world position.
     *
     * @param worldOriginPositionX float, x-coordinate of the origin
     * @param worldOriginPositionY float, y-coordinate of the origin
     */
    public void setWorldOrigin(float worldOriginPositionX, float worldOriginPositionY) {
        super.setX(worldOriginPositionX - getOriginX());
        super.setY(worldOriginPositionY - getOriginY());
    }

    /**
     * Set the position of the sprite such that the origin (center of rotation and scaling)
     * lies at a given world position.
     *
     * @param worldOriginPosition Vector2, position for the origin
     */
    public void setWorldOrigin(Vector2 worldOriginPosition) {
        super.setX(worldOriginPosition.x - getOriginX());
        super.setY(worldOriginPosition.y - getOriginY());
    }

    // the composable actions: basics
    // set in spriteBuilder


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
     * Draw the extended sprite with the draw method of the spriteDraw object.
     * Can use the decorator pattern.
     *
     */
    @Override
    public void draw() {
        spriteDraw.draw(this);
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
     */
    @Override
    public void keepVisible() {
        spriteKeepVisible.keepVisible(this);
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
     */
    @Override
    public void touchBegin(Vector2 position) {
        spriteTouchBegin.touchBegin(this, position);
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
     * Afterwards call the keepVisible() method to gard against the sprite disappearing
     *
     * @param position      Vector2, position of touch.
     * @param deltaPosition Vector2, change in the position of touch.
     */
    @Override
    public void touchDrag(Vector2 position, Vector2 deltaPosition) {
        spriteTouchDrag.touchDrag(this, position, deltaPosition);
        keepVisible();
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
     */
    @Override
    public void touchEnd() {spriteTouchEnd.touchEnd(this);
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
     */
    @Override
    public void scroll(Vector2 position, int amount) {
        spriteScroll.scroll(this, amount);
    }

    // additional effects as decorations. Add to sprite after it has been built
    // by the spriteBuilder.




    public SpriteDraw getDraw() {
        return spriteDraw;
    }

    public SpriteTouchBegin getTouchBegin() {
        return spriteTouchBegin;
    }

    public SpriteTouchDrag getTouchDrag() {
        return spriteTouchDrag;
    }

    public SpriteTouchEnd getTouchEnd() {
        return spriteTouchEnd;
    }

}
