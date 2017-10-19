package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.physics.Forces;
import com.mygdx.game.physics.Physics;

/**
 * Created by peter on 9/23/17.
 */

public class ForceTest extends Forces {
    Viewport viewport;

    public ForceTest(Viewport viewport, Physics physics){
        super(physics);
        this.viewport=viewport;
    }

    @Override
    public void calculateAcceleration(Vector2 acceleration, Vector2 position) {

        acceleration.set(viewport.getWorldWidth()/2-position.x,viewport.getWorldHeight()/2-position.y).scl(50);



    }

}
