package com.mygdx.game.TextSprite;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.SpriteScroll;
import com.mygdx.game.Sprite.SpriteTouchDrag;
import com.mygdx.game.utilities.Device;

/**
 * An extension for ExtensibleSprite that shows a large scrollable text.
 */

public class BigTextExtension extends TextExtension implements SpriteTouchDrag, SpriteScroll {
    static private Rectangle scissors = new Rectangle();
    static private Rectangle bounds = new Rectangle();
    public float margin = 10;
    public float textShift;                            // amount to shift the text upwards
    public float textShiftMax;

    /**
     * Create the extension. Attach to a sprite.
     * Overwrites the draw, touchDrag and scroll methods of the sprite.
     *
     * @param device Device, device with its glyphLayoutPool
     * @param font            BitmapFont
     * @param sprite          ExtensibleSprite, the text will be attached to this sprite
     */
    public BigTextExtension(Device device, BitmapFont font, ExtensibleSprite sprite) {
        super(font, sprite);
        sprite.setTouchDrag(this);
        sprite.setScroll(this);
    }

    /**
     * Set the size of the margin, access via sprite.textExtension.
     *
     * @param margin float, new margin around the text.
     * @return this, for chaining
     */
    public BigTextExtension setMargin(float margin) {
        this.margin = margin;
        return this;
    }

    /**
     * Set the text of the sprite using its glyphLayout.
     * Depends on the width of the sprite.
     *  @param text   String, to show in the sprite
     *
     */
    public void setText(String text) {
        glyphLayout.setText(font, text, font.getColor(), sprite.getWidth() - 2 * margin, Align.left, true);
        textShift = 0;
        textShiftMax = glyphLayout.height - sprite.getHeight() + 4 * margin;
    }

    /**
     * Uses the decorator pattern.
     * First draw the sprite. Then draw the text, clipped to the sprite rectangle.
     *  @param sprite Extensible Sprite
     *
     */
    @Override
    public void draw(ExtensibleSprite sprite) {
        doBasicSpriteDraw();
        SpriteBatch batch=sprite.device.spriteBatch;
        textShift = MathUtils.clamp(textShift, 0, textShiftMax);
        bounds.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        batch.flush();
        ScissorStack.calculateScissors(sprite.device.camera, batch.getTransformMatrix(), bounds, scissors);
        ScissorStack.pushScissors(scissors);
        font.draw(batch, glyphLayout, sprite.getX() + margin,
                sprite.getY() + sprite.getHeight() - margin - margin + textShift);
        batch.flush();
        ScissorStack.popScissors();
    }

    /**
     * Touch drag shifts the text up and down.
     *
     * @param sprite        Extensible Sprite
     * @param position      Vector2, position of touch
     * @param deltaPosition Vector2, change in the position of touch
     * @return boolean, true
     */
    @Override
    public boolean touchDrag(ExtensibleSprite sprite, Vector2 position, Vector2 deltaPosition) {
        textShift += deltaPosition.y;
        return true;
    }

    /**
     * scroll the text up and down.
     *
     * @param sprite   ExtensibleSprite
     * @param position Vector2, position of touch
     * @param amount   int=(+/-)1, for the scroll direction
     * @return boolean, true
     */
    @Override
    public boolean scroll(ExtensibleSprite sprite, Vector2 position, int amount) {
        if (sprite.contains(position)) {
            textShift += 0.4f * amount * font.getLineHeight();
            return true;
        }
        return false;
    }
}
