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
 * NEEDS the BOX"D EXTENSION.
 */

public class PhysicalSpriteBuilder extends ExtensibleSpriteBuilder {
    Physics physics;
    PhysicalSpriteActions physicalSpriteActions;

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
     * Get a physical sprite from the sprite pool. Set image and other data.
     * Attach a body and create its fixtures from shape.
     * Set local origin of the sprite from center of mass of the body.
     * Position and angle of sprite and body will be set afterwards.
     *
     * @param textureRegion TextureRegion, the sprites image
     * @param shape Shape2D shape for the sprite and the body.
     * @param body
     * @return
     */
    public PhysicalSprite buildPhysical(TextureRegion textureRegion, Shape2D shape, Body body){
        PhysicalSprite sprite=physics.physicalSpritePool.obtain();
        setup(sprite,textureRegion,shape);
        sprite.body=body;
        body.setUserData(sprite);
        physics.fixtureBuilder.build(body,shape);
        sprite.setLocalOrigin();

        //sprite.setPosition(200,100);
        sprite.setAngle(3.141592f/2);

        sprite.setPositionAngleOfBody();
        return sprite;
    }
    /*
    public PhysicalSprite buildPhysical(TextureRegion textureRegion,Shape2D shape){

    }*/
}
