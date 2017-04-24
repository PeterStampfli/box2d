package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.utilities.Clipper;

/**
 * Created by peter on 4/20/17.
 */

public class BigTextExtension extends TextExtension implements SpriteTouchDrag,SpriteScroll {
    public float margin=10;
    public float textShift;                            // shift the text upwards
    public float textShiftMax;

    /**
     * to create we need glyphlayout pool and font
     * @param glyphLayoutPool
     * @param font
     */
    public BigTextExtension(Pool<GlyphLayout> glyphLayoutPool, BitmapFont font, ExtensibleSprite sprite) {
        super(glyphLayoutPool, font, sprite);
    }

    /**
     * overwrite the draw,touchdrag and scroll methods of the sprite
     * @param sprite
     */
    @Override
    public void applyTo(ExtensibleSprite sprite){
        sprite.setDraw(this);
        sprite.setTouchDrag(this);
        sprite.setScroll(this);
    }

    /**
     * set size of margin, access via sprite.textExtension
     * @param margin
     * @return
     */
    public BigTextExtension setMargin(float margin){
        this.margin=margin;
        return this;
    }

    /**
     * set the text of the sprite using its glyphLayout
     * depends on size of sprite
     * @param text
     * @param sprite
     */
    public void setText(String text,ExtensibleSprite sprite) {
        glyphLayout.setText(font, text, font.getColor(),sprite.getWidth()-2*margin, Align.left,true);
        textShift=0;
        textShiftMax=glyphLayout.height-sprite.getHeight()+4*margin;
    }

    /**
     * draw the sprite, and then the text,clipped to the sprite rectangle
     * @param sprite
     * @param batch
     */
    @Override
    public void draw(ExtensibleSprite sprite, Batch batch) {
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
     * @param camera
     * @return
     */
    @Override
    public boolean touchDrag(ExtensibleSprite sprite, Vector2 position, Vector2 deltaPosition, Camera camera) {
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
    public boolean scroll(ExtensibleSprite sprite, Vector2 position, int amount) {
        if(sprite.contains(position)){
            textShift+=0.4f*amount*font.getLineHeight();
            return true;
        }
        return false;
    }
}
