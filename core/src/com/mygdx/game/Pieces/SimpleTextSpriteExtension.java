package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

/**
 * Created by peter on 4/20/17.
 * extend a sprite to draw a simple short centered text
 */

public class SimpleTextSpriteExtension implements com.mygdx.game.Sprite.SpriteDraw {
    static BitmapFont masterFont;
    public GlyphLayout glyphLayout = new GlyphLayout();
    public BitmapFont font;
    public com.mygdx.game.Sprite.ExtensibleSprite sprite;

    /**
     * set the font for the objects that will be created
     * note that you can use font.setColor()
     * @param bitmapFont
     */
    static public void setFont(BitmapFont bitmapFont){
        masterFont=bitmapFont;
    }

    /**
     * create a simple text extension with given text
     * @param text
     */
    public SimpleTextSpriteExtension(String text){
        font=masterFont;
        setText(text);
    }

    /**
     * create a simple text extension with empty text
     */
    public SimpleTextSpriteExtension(){
        this("");
    }

    /**
     * set the given sprite to this extension
     * sets its spriteDraw and the extension
     * sets this sprite
     * @param sprite
     * @return
     */
    public SimpleTextSpriteExtension applyTo(com.mygdx.game.Sprite.ExtensibleSprite sprite){
        this.sprite=sprite;
        sprite.setDraw(this);
        return this;
    }

    /**
     * set the text of the sprite using its glyphLayout
     *
     * @param text
     */
    public void setText(String text) {
        glyphLayout.setText(font, text);
    }

    /**
     * draw the sprite and then the text
     * @param sprite
     * @param batch
     */
    @Override
    public void draw(com.mygdx.game.Sprite.ExtensibleSprite sprite, Batch batch){
        sprite.superDraw(batch);
        font.draw(batch, glyphLayout, sprite.getWorldOriginX() - glyphLayout.width * 0.5f,
                sprite.getWorldOriginY() + glyphLayout.height * 0.5f);
    }
}

