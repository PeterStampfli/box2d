package com.mygdx.game.Buttons;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.Sprite.ExtensibleSprite;

/**
 * Basic button methods
 */

public class ButtonActions {

    /**
     * Button draw: Tint sprite and draw sprite and other extensions.
     * Decorator pattern.
     */
    public ButtonDraw drawTinted=new ButtonDraw() {
        @Override
        public void draw(ButtonExtension buttonExtension, ExtensibleSprite sprite, Batch batch, Camera camera) {
            buttonExtension.tintSprite();
            buttonExtension.previousDraw.draw(sprite, batch, camera);
        }
    };

    /**
     * ButtonAct: does nothing.
     */
    public ButtonAct actNull =new ButtonAct(){
        @Override
        public boolean act(ButtonExtension button){return false;}
    };

    /**
     * ButtonTouchBegin: does nothing.
     */
    public ButtonTouchBegin touchBeginNull=new ButtonTouchBegin(){
        @Override
        public boolean touchBegin(ButtonExtension button){return false;}
    };

    /**
     * ButtonTouchEnd: does nothing.
     */
    public ButtonTouchEnd touchEndNull=new ButtonTouchEnd(){
        @Override
        public boolean touchEnd(ButtonExtension button){return false;}
    };
}
