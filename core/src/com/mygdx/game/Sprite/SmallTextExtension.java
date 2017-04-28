package com.mygdx.game.Sprite;

/**
 * Created by peter on 4/22/17.
 */

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.utilities.Device;

/**
 * Created by peter on 4/20/17.
 * extend a sprite to draw a simple short centered text
 */

public class SmallTextExtension extends TextExtension {

    /**
     * Create the extension with a glyphLayout pool and font.
     * Attach to a sprite.
     *
     * @param device Device, device with its glyphLayoutPool
     * @param font   BitmapFont
     * @param sprite ExtensibleSprite, the text will be attached to this sprite
     */

    public SmallTextExtension(Device device, BitmapFont font, ExtensibleSprite sprite) {
        super(device, font, sprite);
    }

    /**
     * Set the text of the sprite using its glyphLayout.
     *
     * @param text   String, the text to show.
     * @param sprite ExtensibleSprite, the text layout depends on the dimensions of the sprite.
     */
    @Override
    public void setText(String text, ExtensibleSprite sprite) {
        glyphLayout.setText(font, text);
    }

    /**
     * Uses the decorator pattern.
     * First draw the sprite. Then draw the text, clipped to the sprite rectangle.
     *
     * @param sprite Extensible Sprite
     * @param batch  Batch
     * @param camera Camera
     */
    @Override
    public void draw(ExtensibleSprite sprite, Batch batch, Camera camera) {
        previousDraw.draw(sprite, batch, camera);
        font.draw(batch, glyphLayout, sprite.getWorldOriginX() - glyphLayout.width * 0.5f,
                sprite.getWorldOriginY() + glyphLayout.height * 0.5f);
    }
}