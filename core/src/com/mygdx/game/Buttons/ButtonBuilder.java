package com.mygdx.game.Buttons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.ExtensibleSpriteBuilder;

/**
 * Add a button function to an extensibleSprite
 */

public class ButtonBuilder {
    ExtensibleSpriteBuilder extensibleSpriteBuilder;
    ButtonDraw buttonDraw;
    ButtonTouchBegin buttonTouchBegin;
    ButtonTouchEnd buttonTouchEnd;
    ButtonAct buttonAct;
    boolean makeSelectionButtons;

    /**
     * Create the buttonBuilder, with default methods for a simple button.
     */
    public ButtonBuilder(ExtensibleSpriteBuilder extensibleSpriteBuilder){
        this.extensibleSpriteBuilder=extensibleSpriteBuilder;
        buttonDraw=ButtonActions.drawTinted;
        buttonAct=ButtonActions.actNull;
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
     * Build simple single press buttons.
     *
     * @return ButtonBuilder, for chaining
     */
    public ButtonBuilder setPressButton(){
        makeSelectionButtons =false;
        buttonTouchBegin=ButtonActions.touchBeginPressed;
        buttonTouchEnd=ButtonActions.touchEndUpAct;
        return this;
    }

    /**
     * Build simple buttons that change state.
     *
     * @return ButtonBuilder, for chaining
     */
    public ButtonBuilder setToggleButton(){
        makeSelectionButtons =false;
        buttonTouchBegin=ButtonActions.touchBeginToggle;
        buttonTouchEnd=ButtonActions.touchEndAct;
        return this;
    }

    /**
     * Build selection buttons that are part of the same collection.
     *
     * @param buttonCollection ButtonCollection, for the interacting buttons
     * @return ButtonBuilder, for chaining
     */
    public ButtonBuilder setSelectionButton(ButtonCollection buttonCollection){
        makeSelectionButtons =true;
        buttonTouchBegin=ButtonActions.touchBeginSelect;
        buttonTouchEnd=ButtonActions.touchEndAct;
        return this;
    }

    /**
     * Add a buttonExtension to a sprite with choosen button methods
     *
     * @param sprite ExtensibleSprite, to make a button out of it
     * @return ButtonExtension
     */
    public ExtensibleSprite build(ExtensibleSprite sprite){
        ButtonExtension buttonExtension=new ButtonExtension(sprite);
        buttonExtension.setButtonDraw(buttonDraw);
        buttonExtension.setButtonTouchBegin(buttonTouchBegin);
        buttonExtension.setButtonTouchEnd(buttonTouchEnd);
        buttonExtension.setButtonAct(buttonAct);
        if (makeSelectionButtons){
            buttonExtension.collection=extensibleSpriteBuilder.collection;



        }
        return sprite;
    }

    public ExtensibleSprite build(TextureRegion image, Shape2D shape2D){
        extensibleSpriteBuilder.setNoMovement();
        return build(extensibleSpriteBuilder.build(image, shape2D));
    }

    public ExtensibleSprite build(TextureRegion image){
        return build(image,null);
    }
}
