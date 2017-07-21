package com.mygdx.game.Buttons;

import com.mygdx.game.Sprite.ExtensibleSprite;

/**
 * Make a button based on an extensibleSprite
 */

public class ButtonBuilder {
    ButtonActions buttonActions;
    ButtonDraw buttonDraw;
    ButtonTouchBegin buttonTouchBegin;
    ButtonTouchEnd buttonTouchEnd;
    ButtonCollection buttonCollection;

    /**
     * Create the buttonBuilder, with default methods for a simple button.
     */
    public ButtonBuilder(){
        buttonActions=new ButtonActions();
        buttonDraw=buttonActions.drawTinted;
        setPressButton();
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
     * set the button collection for new buttons
     *
     * @param buttonCollection
     */
    public ButtonBuilder setButtonCollection(ButtonCollection buttonCollection) {
        this.buttonCollection = buttonCollection;
        return this;
    }

    /**
     * set null button collection. New buttons will not be in any collection.
     *
     */
    public ButtonBuilder setNullButtonCollection() {
        this.buttonCollection = null;
        return this;
    }

    /**
     * Build simple single press buttons.
     *
     * @return ButtonBuilder, for chaining
     */
    public ButtonBuilder setPressButton(){
        setNullButtonCollection();
        buttonTouchBegin=buttonActions.touchBeginPressed;
        buttonTouchEnd=buttonActions.touchEndUp;
        return this;
    }

    /**
     * Build selection buttons that are part of the same collection.
     *
     * @param buttonCollection ButtonCollection, for the interacting buttons
     * @return ButtonBuilder, for chaining
     */
    public ButtonBuilder setSelectionButton(ButtonCollection buttonCollection){
        setButtonCollection(buttonCollection);
        buttonTouchBegin=buttonActions.touchBeginSelect;
        buttonTouchEnd=buttonActions.touchEndNull;
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
        buttonExtension.setButtonDraw(buttonDraw);
        buttonExtension.setButtonTouchBegin(buttonTouchBegin);
        buttonExtension.setButtonTouchEnd(buttonTouchEnd);
        if (buttonCollection!=null){
            buttonCollection.add(sprite);
        }
        return buttonExtension;
    }
}
