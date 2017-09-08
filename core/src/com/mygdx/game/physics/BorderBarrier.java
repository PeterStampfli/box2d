package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Images.Chain;
import com.mygdx.game.Images.Polypoint;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.L;
import com.mygdx.game.utilities.Resizable;

/**
 * A barrier around the visible screen
 */

public class BorderBarrier implements Resizable{
    public Device device;
    public Viewport viewport;
    public Physics physics;
    public Polypoint borderPoints;
    public Vector2 position=new Vector2();
    public Chain borderShape;
    public Body borderBody;

    public BorderBarrier(Device device, Viewport viewport,Physics physics){
        device.addResizable(this);
        this.device=device;
        this.physics=physics;
        this.viewport=viewport;
        borderPoints =new Polypoint();
        borderPoints.setIsLoop();
        borderShape =new Chain();
        physics.bodyBuilder.setPosition(0,0);
        borderBody=physics.bodyBuilder.buildStaticBody(null);
    }

    @Override
    public void resize(int width, int height) {
        L.og("resize border");
        viewport.apply();
        L.og(viewport);
        L.og(" ww "+viewport.getWorldWidth());//!!!!!!!!!!!!!!!!!!!!!!!¨¨¨
        borderPoints.clear();
        borderPoints.add(device.unproject(position.set(0,0)));
        borderPoints.add(device.unproject(position.set(width,0)));
        borderPoints.add(device.unproject(position.set(width,height)));
        borderPoints.add(device.unproject(position.set(0,height)));

        L.og(borderPoints.coordinates);
    }


}
