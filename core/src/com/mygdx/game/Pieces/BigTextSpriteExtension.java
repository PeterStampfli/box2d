package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Sprite.SpriteScroll;
import com.mygdx.game.Sprite.SpriteTouchDrag;
import com.mygdx.game.utilities.Clipper;

/**
 * Created by peter on 4/20/17.
 */

public class BigTextSpriteExtension implements com.mygdx.game.Sprite.SpriteDraw, SpriteTouchDrag,SpriteScroll {
    static BitmapFont masterFont;
    public GlyphLayout glyphLayout = new GlyphLayout();
    public BitmapFont font;
    public com.mygdx.game.Sprite.ExtensibleSprite sprite;
    public float margin=10;
    public float textShift;                            // shift the text upwards
    public float textShiftMax;

    /**
     * set the masterFont for the objects that will be created
     * note that you can use masterFont.setColor()
     * @param bitmapFont
     */
    static public void setFont(BitmapFont bitmapFont){
        masterFont=bitmapFont;
    }

    /**
     * create a big text extension with given text
     * @param text
     */
    public BigTextSpriteExtension(String text, com.mygdx.game.Sprite.ExtensibleSprite sprite){
        this.sprite=sprite;
        sprite.setDraw(this);
        sprite.setTouchDrag(this);
        sprite.setScroll(this);
        font=masterFont;
        setText(text);
    }

    /**
     * create a big text extension with empty text
     */
    public BigTextSpriteExtension(com.mygdx.game.Sprite.ExtensibleSprite sprite){
        this("",sprite);
    }

    /**
     * set size of margin
     * @param margin
     * @return
     */
    public BigTextSpriteExtension setMargin(float margin){
        this.margin=margin;
        return this;
    }

    /**
     * set the text of the sprite using its glyphLayout
     * depends on size of sprite
     * @param text
     * @return
     */
    public BigTextSpriteExtension setText(String text) {
        glyphLayout.setText(font, text, font.getColor(),sprite.getWidth()-2*margin, Align.left,true);
        textShift=0;
        textShiftMax=glyphLayout.height-sprite.getHeight()+4*margin;
        return this;
    }

    /**
     * draw the sprite, and then the text,clipped to the sprite rectangle
     * @param sprite
     * @param batch
     */
    @Override
    public void draw(com.mygdx.game.Sprite.ExtensibleSprite sprite, Batch batch) {
        sprite.superDraw(batch);
        textShift= MathUtils.clamp(textShift,0,textShiftMax);
        Clipper.start(sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight());
        font.draw(batch, glyphLayout, sprite.getX()+margin,
                sprite.getY()+sprite.getHeight()-margin-margin+textShift);
        Clipper.end();
    }

    /**
     * shift the text up and down
     * @param sprite
     * @param position
     * @param deltaPosition
     * @return
     */
    @Override
    public boolean touchDrag(com.mygdx.game.Sprite.ExtensibleSprite sprite, Vector2 position, Vector2 deltaPosition) {
        textShift+=deltaPosition.y;
        return true;
    }

    /**
     * scroll the text up and down
     * @param sprite
     * @param position
     * @param amount
     * @return
     */
    @Override
    public boolean scroll(com.mygdx.game.Sprite.ExtensibleSprite sprite, Vector2 position, int amount) {
        if(sprite.contains(position)){
            textShift+=0.4f*amount*font.getLineHeight();
            return true;
        }
        return false;
    }

}
