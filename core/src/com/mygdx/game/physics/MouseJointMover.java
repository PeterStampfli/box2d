package com.mygdx.game.physics;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.SpriteTouchBegin;
import com.mygdx.game.Sprite.SpriteTouchDrag;
import com.mygdx.game.Sprite.SpriteTouchEnd;

/**
 * Created by peter on 5/7/17.
 */

public class MouseJointMover implements SpriteTouchBegin,SpriteTouchDrag,SpriteTouchEnd,Disposable {

    private SpriteTouchBegin previousTouchBegin;
    private SpriteTouchEnd previousTouchEnd;

    /**
     * Decorate a sprite.
     *
     * @param sprite ExtensibleSprite or subclass, to decorate
     */
    public MouseJointMover(ExtensibleSprite sprite){
        previousTouchBegin=sprite.spriteTouchBegin;
        sprite.setTouchBegin(this);
        sprite.setTouchDrag(this);
        previousTouchEnd=sprite.spriteTouchEnd;
        sprite.setTouchEnd(this);
    }



    public void dispose(){

    }

}
