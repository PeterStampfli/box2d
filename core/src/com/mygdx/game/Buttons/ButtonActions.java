package com.mygdx.game.Buttons;

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
     * TouchBegin: Make that button appears pressed
     */
    static public ButtonTouchBegin touchBeginPressed=new ButtonTouchBegin() {
        @Override
        public void touchBegin(ButtonExtension buttonExtension) {
            buttonExtension.setStatePressed();
        }
    };

    /**
     * Makes that this button is the only pressed one in a collection. For selection buttons.
     */
    static public ButtonTouchBegin touchBeginSelect=new ButtonTouchBegin() {
        @Override
        public void touchBegin(ButtonExtension buttonExtension) {
            buttonExtension.collection.select(buttonExtension);
        }
    };

    /**
     * Change the state from up to pressed and inverse. For switching something.
     */
    static public ButtonTouchBegin touchBeginToggle=new ButtonTouchBegin() {
        @Override
        public void touchBegin(ButtonExtension buttonExtension) {
            if (buttonExtension.state==ButtonExtension.UP){
                buttonExtension.setStatePressed();
            }
            else {
                buttonExtension.setStateUp();                   // does not changed if locked
            }
        }
    };

    /**
     * button touch end: make action and put button to up. For single event buttons.
     */
    static public ButtonTouchEnd touchEndUp=new ButtonTouchEnd() {
        @Override
        public void touchEnd(ButtonExtension buttonExtension) {
            buttonExtension.setStateUp();
            buttonExtension.act();
        }
    };

    /**
     * button act: does nothing
     */
    static public ButtonAct actNull=new ButtonAct() {
        @Override
        public void act(ButtonExtension buttonExtension) {
        }
    };
}
