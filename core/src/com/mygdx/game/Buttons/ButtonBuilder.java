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
    ButtonEffect buttonEffect;
    boolean makeSelectionButtons;

    /**
     * Create the buttonBuilder, with default methods for a simple button.
     *
     * @param extensibleSpriteBuilder ExtensibleSpriteBuilder, basis for buttons
     */
    public ButtonBuilder(ExtensibleSpriteBuilder extensibleSpriteBuilder){
        this.extensibleSpriteBuilder=extensibleSpriteBuilder;
        buttonDraw=ButtonActions.drawTinted;
        buttonEffect =ButtonActions.actNull;
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
     * Build selection buttons that are part of the same buttonCollection.
     *
     * @return ButtonBuilder, for chaining
     */
    public ButtonBuilder setSelectionButton(){
        makeSelectionButtons =true;
        buttonTouchBegin=ButtonActions.touchBeginSelect;
        buttonTouchEnd=ButtonActions.touchEndAct;
        return this;
    }

    /**
     * Add a buttonExtension to a sprite with the button methods that have been chosen before
     *
     * @param sprite ExtensibleSprite, to make a button out of it
     * @return ButtonExtension
     */
    public ExtensibleSprite build(ExtensibleSprite sprite){
        ButtonExtension buttonExtension=new ButtonExtension(sprite);
        buttonExtension.setButtonDraw(buttonDraw);
        buttonExtension.setButtonTouchBegin(buttonTouchBegin);
        buttonExtension.setButtonTouchEnd(buttonTouchEnd);
        buttonExtension.setButtonEffect(buttonEffect);
        buttonExtension.buttonCollection =extensibleSpriteBuilder.spriteCollection;
        return sprite;
    }

    /**
     * build a button extension and a sprite from a texture region image and a shape
     *
     with given texture region and shape2d shape.
     *
     * @param image TextureRegion, image
     * @param shape Shape2D, shape
     * @return ExtensibleSprite, the sprite with button extension
     */
    public ExtensibleSprite build(TextureRegion image, Shape2D shape){
        extensibleSpriteBuilder.setNoMovement();
        return build(extensibleSpriteBuilder.build(image, shape));
    }

    /**
     * build a button extension and a sprite from a texture region image without shape
     *
     with given texture region and shape2d shape.
     *
     * @param image TextureRegion, image
     * @return ExtensibleSprite, the sprite with button extension
     */
    public ExtensibleSprite build(TextureRegion image){
        return build(image,null);
    }
}
