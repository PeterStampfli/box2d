package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.SpriteTouchBegin;
import com.mygdx.game.Sprite.SpriteTouchDrag;
import com.mygdx.game.Sprite.SpriteTouchEnd;

/**
 * Move sprite with touch as kinematic body. Translate only.
 */

public class KinematicTranslate implements SpriteTouchBegin,SpriteTouchDrag,SpriteTouchEnd {
    private Vector2 startBodyPosition=new Vector2();
    private Vector2 startTouchPosition=new Vector2();
    private Vector2 targetBodyPosition=new Vector2();



    @Override
    public boolean touchBegin(ExtensibleSprite sprite, Vector2 position) {
        PhysicalSprite physicalSprite=(PhysicalSprite) sprite;
        startBodyPosition.set(physicalSprite.centerMassCurrentPhysicsTime);
        startTouchPosition.set(position);
        return false;
    }

    @Override
    public boolean touchDrag(ExtensibleSprite sprite, Vector2 position, Vector2 deltaPosition) {
        return false;
    }


    @Override
    public boolean touchEnd(ExtensibleSprite sprite, Vector2 position) {
        return false;
    }

}
