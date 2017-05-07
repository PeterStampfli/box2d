package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.SpriteTouchBegin;
import com.mygdx.game.Sprite.SpriteTouchDrag;
import com.mygdx.game.Sprite.SpriteTouchEnd;

/**
 * Moving a sprite with a mouse joint and touch.
 * Can move several sprites, if they have the same (previous) SpriteTouchActions without sprite specific memory.
 * Else create one for each sprite.
 * Uses decorator pattern and assumes that all SpriteTouchAction objects are set and not null.
 * Note that null-actions are simply one object for all and there is no space lost.
 */

public class MouseJointMover implements SpriteTouchBegin,SpriteTouchDrag,SpriteTouchEnd,Disposable {

    private SpriteTouchBegin previousTouchBegin;
    private SpriteTouchEnd previousTouchEnd;
    private SpriteTouchDrag previousTouchDrag;
    private MouseJoint mouseJoint;
    private Vector2 target;


    /**
     * Decorate a sprite.
     *
     * @param sprite ExtensibleSprite or subclass, to decorate
     */
    public MouseJointMover(PhysicalSprite sprite){
        previousTouchBegin=sprite.spriteTouchBegin;
        sprite.setTouchBegin(this);
        previousTouchDrag=sprite.spriteTouchDrag;
        sprite.setTouchDrag(this);
        previousTouchEnd=sprite.spriteTouchEnd;
        sprite.setTouchEnd(this);
        target=new Vector2();
    }

    /**
     * Start move: Create the mouseJoint and set target.
     *
     * @param sprite   ExtensibleSprite, actually PhysicalSprite
     * @param touchPosition Vector2, the position of touch (in pixels)
     * @return boolean, true if something changed
     */
    @Override
    public boolean touchBegin(ExtensibleSprite sprite, Vector2 touchPosition){
        PhysicalSprite physicalSprite=(PhysicalSprite) sprite;
        JointBuilder jointBuilder=physicalSprite.physics.jointBuilder;
        jointBuilder.setBodyAIsDummy().setBodyB(physicalSprite.body).setTarget(touchPosition);
        mouseJoint=jointBuilder.buildMouseJoint();
        return previousTouchBegin.touchBegin(sprite,touchPosition);
    }

    /**
     * Move the sprite, update the mouseJoint target.
     * To keep it from disappearing use a static body with a ChainShape fixture around the screen.
     *
     * @param sprite        ExtensibleSprite, actually PhysicalSprite
     * @param touchPosition Vector2, the average position of touch (in pixels)
     * @param deltaTouchPosition Vector2, the change in the position of touch (in pixels)
     * @return boolean, true if somathing changed
     */
    public boolean touchDrag(ExtensibleSprite sprite, Vector2 touchPosition,Vector2 deltaTouchPosition) {
        target.set(touchPosition.x+0.5f*deltaTouchPosition.x,touchPosition.y+0.5f*deltaTouchPosition.y);
        mouseJoint.setTarget(target);
        previousTouchDrag.touchDrag(sprite,touchPosition,deltaTouchPosition);
        return true;
    }


    public boolean touchEnd(ExtensibleSprite sprite,Vector2 touchPosition){
        PhysicalSprite physicalSprite=(PhysicalSprite) sprite;
        physicalSprite.physics.world.destroyJoint(mouseJoint);
        mouseJoint=null;
        return previousTouchEnd.touchEnd(sprite,touchPosition);

    }



    /**
     * Dispose: nothing to do. MouseJoint destroyed in touchEnd.
     */
    @Override
    public void dispose(){
    }

}
