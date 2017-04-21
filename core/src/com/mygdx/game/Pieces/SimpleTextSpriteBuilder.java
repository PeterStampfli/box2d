package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.ExtensibleSpriteBuilder;

/**
 * Created by peter on 4/21/17.
 */

public class SimpleTextSpriteBuilder {
    public ExtensibleSpriteBuilder extensibleSpriteBuilder;
    public Pool<SimpleTextSpriteExtension> simpleTextSpriteExtensionPool;
    public BitmapFont masterFont;

    /**
     * create builder with pools
     * @param simpleTextSpriteExtensionPool
     * @param extensibleSpritePool
     */
    public SimpleTextSpriteBuilder(Pool<SimpleTextSpriteExtension> simpleTextSpriteExtensionPool,
                                   Pool<ExtensibleSprite> extensibleSpritePool){
        extensibleSpriteBuilder=new ExtensibleSpriteBuilder(extensibleSpritePool);
        this.simpleTextSpriteExtensionPool=simpleTextSpriteExtensionPool;
    }

    /**
     * set the bitmap masterFont to use in creation of sprites
     * @param masterFont
     * @return
     */
    public SimpleTextSpriteBuilder setMasterFont(BitmapFont masterFont){
        this.masterFont = masterFont;
        return this;
    }

}
