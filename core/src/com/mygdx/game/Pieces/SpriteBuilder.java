package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;

/**
 * Created by peter on 4/19/17.
 * a builder for creating varying sprites on common basis
 */

public class SpriteBuilder {
    // the components
    public TextureRegion image;
    public Shape2D shape;
    public SpriteContains spriteContains = SpriteActions.shapeContains;
    public SpriteDraw spriteDraw=SpriteActions.simpleDraw;
    public SpriteKeepVisible spriteKeepVisible=SpriteActions.nullKeepVisible;
    public SpriteTouchBegin spriteTouchBegin= SpriteActions.nullTouchBegin;
    public SpriteTouchDrag spriteTouchDrag= SpriteActions.nullTouchDrag;
    public SpriteTouchEnd spriteTouchEnd= SpriteActions.nullTouchEnd;
    public SpriteScroll spriteScroll=SpriteActions.nullScroll;

    // set the components

}
