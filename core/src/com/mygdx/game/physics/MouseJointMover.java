package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.SpriteTouchBegin;
import com.mygdx.game.Sprite.SpriteTouchDrag;
import com.mygdx.game.Sprite.SpriteTouchEnd;

/**
 * Moving sprite with a mouse joint and touch.
 * Move methods are basic and set before others in the sprite builder.
 */

public class MouseJointMover implements SpriteTouchBegin,SpriteTouchDrag,SpriteTouchEnd {
    private MouseJoint mouseJoint;
    private Vector2 target=new Vector2();

    /**
     * Start move: Create the mouseJoint and set target.
     *
     * @param sprite   ExtensibleSprite, actually PhysicalSprite
     * @param touchPosition Vector2, the position of touch (in pixels)
     * @return boolean, false, nothing changed
     */
    @Override
    public boolean touchBegin(ExtensibleSprite sprite, Vector2 touchPosition){
        PhysicalSprite physicalSprite=(PhysicalSprite) sprite;
        JointBuilder jointBuilder=physicalSprite.physics.jointBuilder;
        mouseJoint=jointBuilder.buildMouseJoint(physicalSprite,touchPosition);
        return false;
    }

    /**
     * Move the sprite, update the mouseJoint target.
     * To keep it from disappearing use a static body with a ChainShape fixture around the screen.
     *
     * @param sprite        ExtensibleSprite, actually PhysicalSprite
     * @param touchPosition Vector2, the average position of touch (in pixels)
     * @param deltaTouchPosition Vector2, the change in the position of touch (in pixels)
     * @return boolean, true, something changed
     */
    public boolean touchDrag(ExtensibleSprite sprite, Vector2 touchPosition,Vector2 deltaTouchPosition) {
        target.set(deltaTouchPosition).scl(0.5f).add(touchPosition).scl(1f/Physics.PIXELS_PER_METER);
        mouseJoint.setTarget(target);
        return true;
    }

    /**
     * End the move: destroy the mouseJoint.
     * @param sprite   ExtensibleSprite
     * @param touchPosition Vector2, the position of touch (in pixels)
     * @return boolean, false, nothing changed
     */
    public boolean touchEnd(ExtensibleSprite sprite,Vector2 touchPosition){
        PhysicalSprite physicalSprite=(PhysicalSprite) sprite;
        physicalSprite.physics.world.destroyJoint(mouseJoint);
        mouseJoint=null;
        return false;
    }
}
