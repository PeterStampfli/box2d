package com.mygdx.game.physics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.Sprite.ExtensibleSpriteBuilder;
import com.mygdx.game.Sprite.SpriteActions;
import com.mygdx.game.utilities.Device;

/**
 * Extension of extensibleSpriteBuilder to make physicalSprites that use a box2D body for moving.
 * The setter methods of the ExtensibleSpriteBuilder can still be used and chained,
 * but they return an extensibleSpriteBuilder.
 * Build methods give ExtensibleSprites. BuildPhysical methods give physical sprites.
 * NEEDS the BOX2D EXTENSION.
 */

public class PhysicalSpriteBuilder extends ExtensibleSpriteBuilder {
    Physics physics;
    public PhysicalSpriteActions physicalSpriteActions;
    private MouseJointMover mouseJointMover;

    /**
     * Create the builder with a device that has glyphlayout pool.
     * Set minimal default actions.
     *
     * @param device Device, with pools.
     */
    public PhysicalSpriteBuilder(Device device,Physics physics){
        super(device);
        this.physics=physics;
        physicalSpriteActions=new PhysicalSpriteActions();
        setContains(physicalSpriteActions.bodyContains);
    }

    /**
     * Use the mouseJoint to move the sprite. Use the bodies shapes for contains method.
     * Sets the basic methods for physical sprite.
     *
     * @param useStaticBodies boolean, true for making nonmoving bodies static
     */
    public void setMouseJoint(boolean useStaticBodies){
        if (mouseJointMover==null){
            mouseJointMover=new MouseJointMover();
        }
        mouseJointMover.useStaticBodies=useStaticBodies;
        setContains(physicalSpriteActions.bodyContains);
        setKeepVisible(SpriteActions.keepVisibleNull);
        setDraw(SpriteActions.draw);
        setTouchBegin(mouseJointMover);
        setTouchDrag(mouseJointMover);
        setTouchEnd(mouseJointMover);
        setScroll(SpriteActions.scrollNull);
    }

    /**
     * Get a physical sprite from the sprite pool. Set image and other data.
     * Attach a body and create its fixtures from shape.
     * Set local origin of the sprite from center of mass of the body.
     * Position and angle of sprite and body will be set afterwards.
     * If body has mouseJointMover then make it static.
     *
     * Note: We can't set the position of the origin without knowing the center of
     * mass of the body. Thus we have to create the body before setting its position.
     *
     * @param textureRegion TextureRegion, the sprites image
     * @param shape Shape2D shape for the sprite and the body.
     * @param body may be static, dynamical or kinematic, without fixtures (related to shape)
     * @return
     */
    public PhysicalSprite buildPhysical(TextureRegion textureRegion, Shape2D shape, Body body){
        PhysicalSprite sprite=physics.physicalSpritePool.obtain();
        setup(sprite,textureRegion,shape);                         // ExtensibleSprite
        sprite.body=body;
        sprite.physics=physics;
        body.setUserData(sprite);
        physics.fixtureBuilder.setIsSensor(false).build(body,shape);
        sprite.setLocalOrigin();
        if ((mouseJointMover!=null)&&(masterTouchBegin==mouseJointMover)&&(mouseJointMover.useStaticBodies)){
            body.setType(BodyDef.BodyType.StaticBody);
        }
        return sprite;
    }

    /**
     * Get a physical sprite from the sprite pool. Set image and other data.
     * Create a body with current settings of physics.bodyBuilder.
     * Attach the body to the sprite and create its fixtures from shape.
     * Set local origin of the sprite from center of mass of the body.
     * Position and angle of sprite and body will be set afterwards.
     *
     * Note: We can't set the position of the origin without knowing the center of
     * mass of the body. Thus we have to create the body before setting its position.
     *
     * @param textureRegion TextureRegion, the sprites image
     * @param shape Shape2D shape for the sprite and the body.
     * @return
     */
    public PhysicalSprite buildPhysical(TextureRegion textureRegion, Shape2D shape){
        Body body=physics.bodyBuilder.buildDynamicalBody(null);
        return buildPhysical(textureRegion,shape,body);
    }
}