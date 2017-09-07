package com.mygdx.game.physics;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Sprite.SpriteActions;
import com.mygdx.game.Sprite.SpriteContains;

/**
 * Basic (default) actions for physical sprites,
 * defined as instances of anonymous classes that implement the interfaces.
 * Includes the actions for extensible sprites.
 */

public class PhysicalSpriteActions extends SpriteActions {

    /**
     * An object that implements SpriteContains especially for physics:
     * Contains a position if one of its body fixtures contains it.
     * Check only fixtures that are not sensors.
     * Does not check the region of the sprite image because the full shapes define the physics.
     * Use only shapes/fixtures that do not go outside the sprite's image.
     */
    static public SpriteContains bodyContains = new SpriteContains() {
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

    static public PhysicalSpritePrepareTimeStep spriteUpdateNull =new PhysicalSpritePrepareTimeStep() {
        @Override
        public void update(PhysicalSprite sprite) {
        }
    };
}
