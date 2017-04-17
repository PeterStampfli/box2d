package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.utilities.Clipper;

/**
 * Created by peter on 4/17/17.
 * a sprite that shows long texts that can be scrolled and shifted up and down in fixed clipped area
 */

public class BigTextSprite extends TextSprite {
    public float margin=10;
    public float textShift;                            // shift the text upwards
    public float textShiftMax;
    public Color textColor=Color.BLACK;

    /**
     * creates a box2DSprite based on a Texture with same size
     *
     * @param texture
     */
    public BigTextSprite(Texture texture) {
        super(texture);
        setText("");
    }

    /**
     * Creates a box2DSprite based on a specific TextureRegion,
     * the new sprite's region parameters are a copy
     *
     * @param textureRegion
     */
    public BigTextSprite(TextureRegion textureRegion) {
        super(textureRegion);
        setText("");
    }

    /**
     * set size of margin
     * @param margin
     * @return
     */
    public BigTextSprite setMargin(float margin){
        this.margin=margin;
        return this;
    }

    /**
     * set color of text, default=black
     * @param color
     * @return
     */
    public BigTextSprite setTextColor(Color color){
        textColor=color;
        return this;
    }

    /**
     * set the text of the sprite using its glyphLayout
     * @param text
     * @return
     */
    @Override
    public BigTextSprite setText(String text) {
        glyphLayout.setText(bitmapFont, text, textColor,getWidth()-2*margin, Align.left,true);
        textShift=0;
        textShiftMax=glyphLayout.height-getHeight()+4*margin;
        return this;
    }

    /**
     * drawing the text with limited variable offset and cutoff
     * @param batch
     */
    @Override
    public void drawText(Batch batch){
        textShift= MathUtils.clamp(textShift,0,textShiftMax);
        Clipper.start(getX(),getY(),getWidth(),getHeight());
        bitmapFont.draw(batch, glyphLayout, getX()+margin,
                getY()+getHeight()-margin-margin+textShift);
        Clipper.end();
    }

    @Override
    public boolean touchDrag(Vector2 position,Vector2 deltaPosition){
        textShift+=deltaPosition.y;
        return true;
    }

    @Override
    public boolean scroll(Vector2 position,int amount){
        if(contains(position)){
            textShift+=0.4f*amount*bitmapFont.getLineHeight();
            return true;
        }
        return false;
    }
}
