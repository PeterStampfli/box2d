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

    /**
     * TouchBegin: Make that button appears pressed
     */
    public ButtonTouchBegin touchBeginPressed=new ButtonTouchBegin() {
        @Override
        public boolean touchBegin(ButtonExtension buttonExtension) {
            buttonExtension.setStatePressed();
            return true;
        }
    };

    /**
     * Makes that this button is the only pressed one in a collection. For selection buttons.
     */
    public ButtonTouchBegin touchBeginSelect=new ButtonTouchBegin() {
        @Override
        public boolean touchBegin(ButtonExtension buttonExtension) {
            buttonExtension.collection.select(buttonExtension);
            return true;
        }
    };

    /**
     * Change the state from up to pressed and inverse. For switching something.
     */
    public ButtonTouchBegin touchBeginToggle=new ButtonTouchBegin() {
        @Override
        public boolean touchBegin(ButtonExtension buttonExtension) {
            if (buttonExtension.state==ButtonExtension.UP){
                buttonExtension.setStatePressed();
            }
            else {
                buttonExtension.setStateUp();                   // does not changed if locked
            }
            return true;
        }
    };

    /**
     *TouchEnd: Simply make action, does not change button state
     */
    public ButtonTouchEnd touchEndAct=new ButtonTouchEnd() {
        @Override
        public boolean touchEnd(ButtonExtension buttonExtension) {
            return buttonExtension.act();
        }
    };

    /**
     * button touch end: make action and put button to up. For single event buttons.
     */
    public ButtonTouchEnd touchEndActUp=new ButtonTouchEnd() {
        @Override
        public boolean touchEnd(ButtonExtension buttonExtension) {
            buttonExtension.setStateUp();
            return buttonExtension.act();
        }
    };
}
