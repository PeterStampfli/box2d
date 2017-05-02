package com.mygdx.game.physics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Sprite.ExtensibleSpriteBuilder;
import com.mygdx.game.utilities.Device;

/**
 * Extension of extensibleSpriteBuilder to make physicalSprites that use a box2D body for moving.
 * The setter methods of the ExtensibleSpriteBuilder can still be used and chained,
 * but they return an extensibleSpriteBuilder. We can use it to build ExtensibleSprites too.
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

    public PhysicalSprite build(TextureRegion textureRegion, Shape2D shape, Body body){
        PhysicalSprite sprite=physics.physicalSpritePool.obtain();
        setup(sprite,textureRegion,shape);
        sprite.body=body;
        body.setUserData(sprite);
        physics.fixtureBuilder.build(body,shape);


        return sprite;
    }
    /*
    public PhysicalSprite buildPhysical(TextureRegion textureRegion,Shape2D shape){

    }*/
}
