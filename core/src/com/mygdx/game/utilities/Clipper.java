package com.mygdx.game.utilities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

/**
 * Created by peter on 4/16/17.
 * clipping the sprite batch with scissorstack
 */

public class Clipper {
    SpriteBatch spriteBatch;

    /**
     * give it the spritebatch
     * @param spriteBatch
     */
    public Clipper(SpriteBatch spriteBatch){
        this.spriteBatch=spriteBatch;
    }


    public void start(Camera camera,Rectangle bounds){
        spriteBatch.flush();
        Rectangle scissors=new Rectangle();
        ScissorStack.calculateScissors(camera,spriteBatch.getTransformMatrix(),bounds,scissors);
        ScissorStack.pushScissors(scissors);
    }

    public void end(){
        spriteBatch.flush();
        ScissorStack.popScissors();
    }


}
