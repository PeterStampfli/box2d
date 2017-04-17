package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 4/16/17.
 */

public class TextSprite extends TouchableSprite {
    public GlyphLayout glyphLayout = new GlyphLayout();
    static public BitmapFont bitmapFont;

    /**
     * set the bitmap font to use, do this first !!!!
     *
     * @param bitmapFont
     */
    static public void setBitmapFont(BitmapFont bitmapFont) {
        TextSprite.bitmapFont = bitmapFont;
    }

    /**
     * creates a box2DSprite based on a Texture with same size
     *
     * @param texture
     */
    public TextSprite(Texture texture) {
        super(texture);
        setText("");
    }

    /**
     * Creates a box2DSprite based on a specific TextureRegion,
     * the new sprite's region parameters are a copy
     *
     * @param textureRegion
     */
    public TextSprite(TextureRegion textureRegion) {
        super(textureRegion);
        setText("");
    }

    /**
     * set the text of the sprite using its glyphLayout
     *
     * @param text
     * @return
     */
    public TextSprite setText(String text) {
        glyphLayout.setText(bitmapFont, text);
        return this;
    }

    /**
     * draw the text - easy to override
     * @param batch
     */
    public void drawText(Batch batch){
    bitmapFont.draw(batch, glyphLayout, getWorldOriginX() - glyphLayout.width * 0.5f,
            getWorldOriginY() + glyphLayout.height * 0.5f);
    }

    /**
     * draw the sprite shape and the text (without rotation)
     *
     * @param batch
     */
    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        drawText(batch);
    }

    /**
     * translate without rotation
     * @param touchPosition
     * @param deltaTouchPosition
     * @return
     */
    @Override
    public boolean touchDrag(Vector2 touchPosition, Vector2 deltaTouchPosition) {
        translate(deltaTouchPosition.x, deltaTouchPosition.y);
        return true;
    }
}