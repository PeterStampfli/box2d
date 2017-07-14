package com.mygdx.game.TextSprite;

/**
 * Created by peter on 4/22/17.
 */

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.Sprite.ExtensibleSprite;

/**
 * Created by peter on 4/20/17.
 * extend a sprite to draw a simple short centered text
 */

public class SmallTextExtension extends TextExtension {

    /**
     * Create the extension.
     * Attach to a sprite.
     *  @param font   BitmapFont
     * @param sprite ExtensibleSprite, the text will be attached to this sprite
     */

    public SmallTextExtension(BitmapFont font, ExtensibleSprite sprite) {
        super(font, sprite);
    }

    /**
     * Set the text of the sprite using its glyphLayout.
     *  @param text   String, the text to show.
     *
     */
    @Override
    public void setText(String text) {
        glyphLayout.setText(font, text);
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
        font.draw(sprite.device.spriteBatch, glyphLayout, sprite.getWorldOriginX() - glyphLayout.width * 0.5f,
                sprite.getWorldOriginY() + glyphLayout.height * 0.5f);
    }
}