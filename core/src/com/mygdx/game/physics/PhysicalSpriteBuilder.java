package com.mygdx.game.physics;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Sprite.ExtensibleSpriteBuilder;
import com.mygdx.game.Sprite.SpriteContains;
import com.mygdx.game.utilities.Device;

/**
 * Extension of extensibleSpriteBuilder to make physicalSprites that use a box2D body for moving.
 * The setter methods of the ExtensibleSpriteBuilder can still be used and chained,
 * but they return an extensibleSpriteBuilder. We can use it to build ExtensibleSprites too.
 */

public class PhysicalSpriteBuilder extends ExtensibleSpriteBuilder {
    Physics physics;

    /**
     * Create the builder with a device that has glyphlayout pool.
     * Set minimal default actions.
     *
     * @param device Device, with pools.
     */
    public PhysicalSpriteBuilder(Device device,Physics physics){
        super(device);
        this.physics=physics;
    }

    /**
     * An object that implements SpriteContains:
     * Contains a position if one of its body fixtures contains it.
     * Check only fixtures that are not sensors.
     * Does not check the region of the sprite image because the full shapes define the physics.
     * Use only shapes/fixtures that do not go outside the sprite's image.
     *
     * 		physicalSpriteBuilder.setContains(physicalSpriteBuilder.containsUsesBody);
     */
    public SpriteContains containsUsesBody = new SpriteContains() {
        @Override
        public boolean contains(com.mygdx.game.Sprite.ExtensibleSprite sprite,  float positionX, float positionY) {
            PhysicalSprite physicalSprite= (PhysicalSprite) sprite;
            positionX/=Physics.PIXELS_PER_METER;
            positionY/=Physics.PIXELS_PER_METER;
            Array<Fixture> fixtures = physicalSprite.body.getFixtureList();
            for (Fixture fixture : fixtures) {
                // if any fixture is not a sensor and it contains the point, then the body contains the point
                if (!fixture.isSensor()&&fixture.testPoint(positionX, positionY)) {
                    return true;
                }
            }
            return false;
        }
    };


    public PhysicalSprite
}
