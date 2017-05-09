package com.mygdx.game.physics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Sprite.ExtensibleSpriteBuilder;
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
        setKeepVisible(spriteActions.keepVisibleNull);
        setDraw(spriteActions.drawSuper);
        setTouchBegin(mouseJointMover);
        setTouchDrag(mouseJointMover);
        setTouchEnd(mouseJointMover);
        setScroll(spriteActions.nullScroll);
    }

    /**
     * Set the sprite data. Add the body. Attach fixtures to the body. Set sprite origin.
     * Note that the local origin of the body does not
     *
     * @param sprite ExtensibleSprite, or subtype, to set up.
     * @param textureRegion TextureRegion, image
     * @param shape Shape2D, shape
     * @param body Body, body to add to sprite
     */
    public void setup(PhysicalSprite sprite, TextureRegion textureRegion, Shape2D shape,Body body) {
        setup(sprite,textureRegion,shape);
        sprite.body=body;
        sprite.physics=physics;
        body.setUserData(sprite);
        physics.fixtureBuilder.build(body,shape);
        sprite.setLocalOrigin();
    }

    /**
     * Get a physical sprite from the sprite pool. Set image and other data.
     * Attach a body and create its fixtures from shape.
     * Set local origin of the sprite from center of mass of the body.
     * Position and angle of sprite and body will be set afterwards.
     *
     * Note: We can't set the position of the origin without knowing the center of
     * mass of the body. Thus we have to create the body before setting its position.
     *
     * @param textureRegion TextureRegion, the sprites image
     * @param shape Shape2D shape for the sprite and the body.
     * @param body
     * @return
     */
    public PhysicalSprite buildPhysical(TextureRegion textureRegion, Shape2D shape, Body body){
        PhysicalSprite sprite=physics.physicalSpritePool.obtain();
        setup(sprite,textureRegion,shape,body);
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
        Body body=physics.bodyBuilder.setDynamicalBody().build();
        return buildPhysical(textureRegion,shape,body);
    }
}