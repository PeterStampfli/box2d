package com.mygdx.game.Pieces;

import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Sprite.ExtensibleSprite;

/**
 * Created by peter on 4/21/17.
 * combines extensible sprite and text extension
 */

public class SimpleTextSprite{
    public ExtensibleSprite sprite;
    public SimpleTextSpriteExtension textExtension;
    public Pool<SimpleTextSpriteExtension> simpleTextSpriteExtensionPool;

    /**
     * set the text (via the extension)
     * @param text
     * @return
     */
    public SimpleTextSprite setText(String text){
        textExtension.setText(text);
        return this;
    }

    public void free(){
        sprite.free();
        sprite=null;
        setText("");
        textExtension.font=null;
        simpleTextSpriteExtensionPool.free(textExtension);
        textExtension=null;
    }
}
