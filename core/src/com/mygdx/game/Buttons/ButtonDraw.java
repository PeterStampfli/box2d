package com.mygdx.game.Buttons;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.Sprite.ExtensibleSprite;

/**
 * An object that draws the button and sprite.
 */

public interface ButtonDraw {

    public void draw(ButtonExtension buttonExtension, ExtensibleSprite sprite, Batch batch, Camera camera);
}
