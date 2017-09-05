package com.mygdx.game.Buttons;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Sprite.ExtensibleSprite;

/**
 * Basic static button methods
 */

public class ButtonActions {

    /**
     * Button draw: Tint sprite and draw sprite and other extensions.
     * Decorator pattern.
     */
    static public ButtonDraw drawTinted=new ButtonDraw() {
        @Override
        public void draw(ButtonExtension buttonExtension) {
            buttonExtension.tintSprite();
            buttonExtension.doBasicSpriteDraw();
        }
    };

    /**
     * ButtonTouchBegin: does nothing.
     */
    static public ButtonTouchBegin touchBeginNull=new ButtonTouchBegin(){
        @Override
        public void touchBegin(ButtonExtension button){}
    };

    /**
     * ButtonTouchEnd: does nothing.
     */
    static public ButtonTouchEnd touchEndNull=new ButtonTouchEnd(){
        @Override
        public void touchEnd(ButtonExtension button){}
    };

    /**
     * ButtonTouchEnd: does nothing on button, calls action.
     */
    static public ButtonTouchEnd touchEndAct=new ButtonTouchEnd(){
        @Override
        public void touchEnd(ButtonExtension buttonExtension){buttonExtension.act();}
    };

    /**
     * TouchBegin: Make that button appears pressed
     */
    static public ButtonTouchBegin touchBeginPressed=new ButtonTouchBegin() {
        @Override
        public void touchBegin(ButtonExtension buttonExtension) {
            buttonExtension.setStatePressed();
        }
    };

    /**
     * Makes that this button is the only pressed one in a buttonCollection. For selection buttons.
     */
    static public ButtonTouchBegin touchBeginSelect=new ButtonTouchBegin() {
        @Override
        public void touchBegin(ButtonExtension buttonExtension) {
            if (buttonExtension.state!=ButtonExtension.LOCKED) {
                Array<ExtensibleSprite> buttonSprites=buttonExtension.buttonCollection.items;
                ButtonExtension button;
                for (ExtensibleSprite sprite:buttonSprites){
                    button=sprite.buttonExtension;
                    if (button!=null){
                        button.setStateUp();
                    }
                }
                buttonExtension.setStatePressed();
            }
        }
    };

    /**
     * Change the state from up to pressed and inverse. For switching something.
     */
    static public ButtonTouchBegin touchBeginToggle=new ButtonTouchBegin() {
        @Override
        public void touchBegin(ButtonExtension buttonExtension) {
            buttonExtension.toggleState();
        }
    };

    /**
     * button touch end: make action and put button to up. For single event buttons.
     */
    static public ButtonTouchEnd touchEndUpAct =new ButtonTouchEnd() {
        @Override
        public void touchEnd(ButtonExtension buttonExtension) {
            buttonExtension.setStateUp();
            buttonExtension.act();
        }
    };

    /**
     * button act: does nothing
     */
    static public ButtonEffect actNull=new ButtonEffect() {
        @Override
        public void act(ButtonExtension buttonExtension) {
        }
    };
}
