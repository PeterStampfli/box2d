package com.mygdx.game.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Images.Chain;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.Resizable;

/**
 * A barrier around the visible screen to stop physical sprites.
 */

public class BorderBarrier implements Resizable{
    public Device device;
    public Viewport viewport;
    public Physics physics;
    public Chain borderShape;
    public Body borderBody;

    public BorderBarrier(Device device, Viewport viewport,Physics physics){
        device.addResizable(this);
        this.device=device;
        this.physics=physics;
        this.viewport=viewport;
        borderShape =new Chain();
        borderShape.setIsLoop();
        physics.bodyBuilder.setPosition(0,0);
        borderBody=physics.bodyBuilder.buildStaticBody(null);
    }

    @Override
    public void resize(int width, int height) {
        borderShape.set(0,0,viewport.getWorldWidth(),0,viewport.getWorldWidth(),
                viewport.getWorldHeight(),0,viewport.getWorldHeight());
        physics.fixtureBuilder.destroyFixtures(borderBody);
        physics.fixtureBuilder.build(borderBody,borderShape);
    }


}
