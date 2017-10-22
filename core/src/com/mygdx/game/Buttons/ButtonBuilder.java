package com.mygdx.game.Buttons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.ExtensibleSpriteBuilder;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.L;

/**
 * Add a button function to an extensibleSprite
 */

public class ButtonBuilder {
    ExtensibleSpriteBuilder extensibleSpriteBuilder;
    Device device;
    ButtonDraw buttonDraw;
    ButtonTouchBegin buttonTouchBegin;
    ButtonTouchEnd buttonTouchEnd;
    boolean makeSelectionButtons;
    float initialDelay=1;
    float timeInterval=0.5f;

    /**
     * Create the buttonBuilder, with default methods for a simple button.
     *
     * @param extensibleSpriteBuilder ExtensibleSpriteBuilder, basis for buttons
     */
    public ButtonBuilder(ExtensibleSpriteBuilder extensibleSpriteBuilder){
        this.extensibleSpriteBuilder=extensibleSpriteBuilder;
        device=extensibleSpriteBuilder.device;
        buttonDraw=ButtonActions.drawTinted;
        setPushButton();
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
     * Build simple single push buttons.
     *
     * @return ButtonBuilder, for chaining
     */
    public ButtonBuilder setPushButton(){
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
     * and a given action
     *
     * @param action Runnable with the run method that does it
     * @param sprite ExtensibleSprite, to make a button out of it
     * @return ButtonExtension
     */
    public ExtensibleSprite build(Runnable action,ExtensibleSprite sprite){
        ButtonExtension buttonExtension=new ButtonExtension(sprite);
        buttonExtension.setButtonDraw(buttonDraw);
        buttonExtension.setButtonTouchBegin(buttonTouchBegin);
        buttonExtension.setButtonTouchEnd(buttonTouchEnd);
        buttonExtension.setButtonAction(action);
        buttonExtension.buttonCollection =extensibleSpriteBuilder.spriteCollection;
        return sprite;
    }

    /**
     * build a button extension and a sprite from an action, a texture region image and a shape
     **
     * @param action Runnable with the run method that does it
     * @param image TextureRegion, image
     * @param shape Shape2D, shape
     * @return ExtensibleSprite, the sprite with button extension
     */
    public ExtensibleSprite build(Runnable action,TextureRegion image, Shape2D shape){
        extensibleSpriteBuilder.setNoMovement();
        return build(action,extensibleSpriteBuilder.build(image, shape));
    }

    /**
     * build a button extension and a sprite from an action, a texture region image
     * The shape is the texture region
     *
     * @param action Runnable with the run method that does it
     * @param image TextureRegion, image
     * @return ExtensibleSprite, the sprite with button extension
     */
    public ExtensibleSprite build(Runnable action,TextureRegion image){
        return build(action,image,null);
    }

    /**
     * build a push button from an action,a texture region image and a shape
     *
     * @param action Runnable with the run method that does it
     * @param image TextureRegion, image
     * @param shape Shape2D, shape
     * @return ExtensibleSprite, the sprite with button extension
     */
    public ExtensibleSprite pushButton(Runnable action,TextureRegion image, Shape2D shape){
        extensibleSpriteBuilder.setNoMovement();
        setPushButton();
        return build(action,extensibleSpriteBuilder.build(image, shape));
    }

    /**
     * build a push button from an action,a texture region image
     * The shape is the texture region
     *
     with given texture region and shape2d shape.
     *
     * @param action Runnable with the run method that does it
     * @param image TextureRegion, image
     * @return ExtensibleSprite, the sprite with button extension
     */
    public ExtensibleSprite pushButton(Runnable action,TextureRegion image){
        return build(action,image,null);
    }



    /**
     * build a repeating push button from an action,a texture region image and a shape
     *
     * @param action Runnable with the run method that does it
     * @param image TextureRegion, image
     * @param shape Shape2D, shape
     * @return ExtensibleSprite, the sprite with button extension
     */
    public ExtensibleSprite repeatingPushButton(final Runnable action, TextureRegion image, Shape2D shape){
        extensibleSpriteBuilder.setNoMovement();
        setPushButton();
        final ExtensibleSprite button= build(new Runnable() {
            @Override
            public void run() {
                if (device.timer.starting){
                    action.run();
                }
                device.timer.stop();
            }
        }, extensibleSpriteBuilder.build(image, shape));
        button.buttonExtension.setButtonTouchBegin(new ButtonTouchBegin() {
            @Override
            public void touchBegin(ButtonExtension buttonExtension) {
                button.buttonExtension.setStatePressed();
                device.timer.repeatForever(action,initialDelay,timeInterval);
            }
        });

        return button;
    }

}
