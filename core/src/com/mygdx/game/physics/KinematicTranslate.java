package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.SpriteTouchBegin;
import com.mygdx.game.Sprite.SpriteTouchDrag;
import com.mygdx.game.Sprite.SpriteTouchEnd;

/**
 * Move sprite with touch as kinematic body. Translate only.
 */

public class KinematicTranslate
        implements SpriteTouchBegin,SpriteTouchDrag,SpriteTouchEnd,PhysicalSpriteUpdate {
    public Vector2 bodyTargetPosition=new Vector2();
    public boolean moving=false;
    public Vector2 velocity=new Vector2();

    /**
     * determine relative position of touch to body, start motion
     *
     * @param sprite   ExtensibleSprite
     * @param touchPosition Vector2
     */
    @Override
    public void touchBegin(ExtensibleSprite sprite, Vector2 touchPosition) {
        PhysicalSprite physicalSprite=(PhysicalSprite) sprite;
        bodyTargetPosition.set(Physics.getPosition(physicalSprite.body));
        moving=true;
    }

    /**
     * determine the (target) position of body at end of next time steps
     * @param sprite        ExtensibleSprite
     * @param touchPosition Vector2
     * @param deltaTouchPosition Vector2
     */
    @Override
    public void touchDrag(ExtensibleSprite sprite, Vector2 touchPosition, Vector2 deltaTouchPosition) {
        bodyTargetPosition.add(deltaTouchPosition);
    }

    /**
     * stop the body
     *
     * @param sprite   ExtensibleSprite
     * @param position Vector2, position of touch
     */
    @Override
    public void touchEnd(ExtensibleSprite sprite, Vector2 position) {
        PhysicalSprite physicalSprite=(PhysicalSprite) sprite;
        moving=false;
        physicalSprite.body.setLinearVelocity(0,0);
    }

    @Override
    public void update(PhysicalSprite sprite) {
        if (moving) {
            velocity.set(bodyTargetPosition).sub(Physics.getPosition(sprite.body)).scl(1f / Physics.TIME_STEP);
            Physics.setVelocity(sprite.body, velocity);
        }
    }
}
