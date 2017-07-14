package com.mygdx.game.Buttons;

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
        public void draw(ButtonExtension buttonExtension) {
            buttonExtension.tintSprite();
            buttonExtension.doBasicSpriteDraw();
        }
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
     * button touch end: make action and put button to up. For single event buttons.
     */
    public ButtonTouchEnd touchEndUp=new ButtonTouchEnd() {
        @Override
        public boolean touchEnd(ButtonExtension buttonExtension) {
            buttonExtension.setStateUp();
            return true;
        }
    };
}
