package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.SpriteTouchBegin;
import com.mygdx.game.Sprite.SpriteTouchDrag;
import com.mygdx.game.Sprite.SpriteTouchEnd;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.L;

/**
 * Move sprite with touch as kinematic body. Translate only.
 */

public class KinematicTranslate implements SpriteTouchBegin,SpriteTouchDrag,SpriteTouchEnd {
    private Vector2 bodySubTouchPosition =new Vector2();
    private Vector2 velocity=new Vector2();

    @Override
    public boolean touchBegin(ExtensibleSprite sprite, Vector2 touchPosition) {
        PhysicalSprite physicalSprite=(PhysicalSprite) sprite;
        bodySubTouchPosition.set(physicalSprite.centerMassCurrentPhysicsTime).sub(touchPosition);
        L.og(bodySubTouchPosition);
        return false;
    }

    @Override
    public boolean touchDrag(ExtensibleSprite sprite, Vector2 touchPosition, Vector2 deltaTouchPosition) {
        PhysicalSprite physicalSprite=(PhysicalSprite) sprite;
        velocity.set(touchPosition).add(bodySubTouchPosition).sub(physicalSprite.centerMassCurrentPhysicsTime)
                .scl(1f/(Basic.getTime()-physicalSprite.physics.physicsTime));
L.og("gra "+Basic.getTime());
        L.og(physicalSprite.physics.physicsTime);
        Physics.setVelocity(physicalSprite.body,velocity);
        return false;
    }

    @Override
    public boolean touchEnd(ExtensibleSprite sprite, Vector2 position) {
        PhysicalSprite physicalSprite=(PhysicalSprite) sprite;
        physicalSprite.body.setLinearVelocity(0,0);
        return false;
    }

}
