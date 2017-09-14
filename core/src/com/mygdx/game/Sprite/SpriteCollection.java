package com.mygdx.game.Sprite;

import com.mygdx.game.Pieces.TouchableCollection;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.Resizable;

/**
 * A shortcut for TouchableCollection of ExtensibleSprites.
 * Can keep sprites inside screen upon resize
 */

public class SpriteCollection extends TouchableCollection<ExtensibleSprite> implements Resizable{

    /**
     * A sprite collection that does nothing upon resize
     */
    public SpriteCollection(){}

    /**
     * A sprite collection that keeps the sprite on screen with sprites method
     *
     * @param device Device
     */
    public SpriteCollection(Device device){
        device.addResizable(this);
    }

    /**
     * For resize: keep the sprites in this collection visible
     *
     * @param width int, width of screen
     * @param height int, height of screen
     */
    @Override
    public void resize(int width, int height) {
            for (ExtensibleSprite sprite : items) {
                sprite.keepVisible();
            }
    }
}
