package com.mygdx.game.Buttons;

import com.mygdx.game.Sprite.ExtensibleSprite;

/**
 * Make a button based on an extensibleSprite
 */

public class ButtonBuilder {
    ButtonActions buttonActions;
    ButtonAct buttonAct;
    ButtonDraw buttonDraw;
    ButtonTouchBegin buttonTouchBegin;
    ButtonTouchEnd buttonTouchEnd;
    ButtonCollection buttonCollection;

    /**
     * Create the buttonBuilder, with default methods for a simple button.
     */
    public ButtonBuilder(){
        buttonActions=new ButtonActions();
        buttonAct=buttonActions.actNull;
        buttonDraw=buttonActions.drawTinted;
        setPressButton();
    }

    /**
     * Set the act-method.
     *
     * @param buttonAct ButtonAct, object with act-method
     * @return ButtonBuilder, for chaining
     */
    public ButtonBuilder setAct(ButtonAct buttonAct){
        this.buttonAct=buttonAct;
        return this;
    }

    /**
     * Set the draw-method.
     *
     * @param buttonDraw ButtonDraw, object with draw-method
     * @return ButtonBuilder, for chaining
     */
    public ButtonBuilder setDraw(ButtonDraw buttonDraw){
        this.buttonDraw=buttonDraw;
        return this;
    }

    /**
     * Set the touchBegin-method.
     *
     * @param buttonTouchBegin ButtonTouchBegin, object with touchBegin-method
     * @return ButtonBuilder, for chaining
     */
    public ButtonBuilder setTouchBegin(ButtonTouchBegin buttonTouchBegin){
        this.buttonTouchBegin=buttonTouchBegin;
        return this;
    }

    /**
     * Set the touchEnd-method.
     *
     * @param buttonTouchEnd ButtonTouchEnd, object with touchEnd-method
     * @return ButtonBuilder, for chaining
     */
    public ButtonBuilder setTouchEnd(ButtonTouchEnd buttonTouchEnd){
        this.buttonTouchEnd=buttonTouchEnd;
        return this;
    }

    /**
     * Build simple press buttons.
     *
     * @return ButtonBuilder, for chaining
     */
    public ButtonBuilder setPressButton(){
        buttonTouchBegin=buttonActions.touchBeginPressed;
        buttonTouchEnd=buttonActions.touchEndActUp;
        return this;
    }

    /**
     * Build selection buttons that are part of the same collection.
     *
     * @param buttonCollection ButtonCollection, for the interacting buttons
     * @return ButtonBuilder, for chaining
     */
    public ButtonBuilder setSelectionButton(ButtonCollection buttonCollection){
        this.buttonCollection=buttonCollection;
        buttonTouchBegin=buttonActions.touchBeginSelect;
        buttonTouchEnd=buttonActions.touchEndAct;
        return this;
    }

    /**
     * Add a buttonExtension to a sprite with choosen button methods
     *
     * @param sprite ExtensibleSprite, to make a button out of it
     * @return ButtonExtension
     */
    public ButtonExtension build(ExtensibleSprite sprite){
        ButtonExtension buttonExtension=new ButtonExtension(sprite);
        buttonExtension.setButtonAct(buttonAct);
        buttonExtension.setButtonDraw(buttonDraw);
        buttonExtension.setButtonTouchBegin(buttonTouchBegin);
        buttonExtension.setButtonTouchEnd(buttonTouchEnd);
        if (buttonTouchBegin==buttonActions.touchBeginSelect){
            buttonCollection.add(sprite);
        }
        return buttonExtension;
    }
}
