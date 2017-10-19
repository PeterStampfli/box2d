package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.SpriteTouchBegin;
import com.mygdx.game.Sprite.SpriteTouchDrag;
import com.mygdx.game.Sprite.SpriteTouchEnd;

/**
 * Moving sprite with a mouse joint and touch.
 * Attention: sets parameters of the JointBuilder to its own values.
 * A stiff joint of frequencyHz=0 crashes. Thus FrequencyHz has to be greater than 0.
 * A large damping is better, thus dampingRatio=1 (critical)
 * Attention: Static bodies have localCenter=0, always.
 */

public class MouseJointMover implements SpriteTouchBegin,SpriteTouchDrag,SpriteTouchEnd {
    private MouseJoint mouseJoint;
    public float maxAcceleration=5000;
    public float frequencyHz=20;
    public float dampingRatio=1;
    private Vector2 target=new Vector2();
    public boolean useStaticBodies=false;

    /**
     * Start move: Create the mouseJoint and set target.
     * Sets parameters of JointBuilder to be safe from side effects.
     *
     * @param sprite   ExtensibleSprite, actually PhysicalSprite
     * @param touchPosition Vector2, the position of touch (in pixels)
     */
    @Override
    public void touchBegin(ExtensibleSprite sprite, Vector2 touchPosition){
        PhysicalSprite physicalSprite=(PhysicalSprite) sprite;
        if (useStaticBodies){
            physicalSprite.body.setType(BodyDef.BodyType.DynamicBody);
        }
        JointBuilder jointBuilder=physicalSprite.physics.jointBuilder;
        jointBuilder.setFrequencyHz(frequencyHz);
        jointBuilder.setDampingRatio(dampingRatio);
        jointBuilder.setMaxAcceleration(maxAcceleration);
        jointBuilder.setCollideConnected(false);
        mouseJoint=jointBuilder.buildMouseJoint(physicalSprite,touchPosition);
    }

    /**
     * Move the sprite, update the mouseJoint target.
     * To keep it from disappearing use a static body with a ChainShape fixture around the screen.
     *
     * @param sprite        ExtensibleSprite, actually PhysicalSprite
     * @param touchPosition Vector2, the average position of touch (in pixels)
     * @param deltaTouchPosition Vector2, the change in the position of touch (in pixels)
     */
    public void touchDrag(ExtensibleSprite sprite, Vector2 touchPosition, Vector2 deltaTouchPosition) {
        target.set(deltaTouchPosition).scl(0.5f).add(touchPosition).scl(1f/Physics.PIXELS_PER_METER);
        mouseJoint.setTarget(target);
    }

    /**
     * End the move: destroy the mouseJoint.
     * @param sprite   ExtensibleSprite
     */
    public void touchEnd(ExtensibleSprite sprite){
        PhysicalSprite physicalSprite=(PhysicalSprite) sprite;
        physicalSprite.physics.world.destroyJoint(mouseJoint);
        mouseJoint=null;
        if (useStaticBodies){
            physicalSprite.body.setType(BodyDef.BodyType.StaticBody);
            physicalSprite.interpolatePositionAngleOfBody(1);
        }
        else {
            physicalSprite.body.setAngularVelocity(0);
            physicalSprite.body.setLinearVelocity(0, 0);
        }
    }
}
