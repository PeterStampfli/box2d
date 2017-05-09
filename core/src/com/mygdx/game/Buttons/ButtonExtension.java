package com.mygdx.game.Buttons;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.SpriteDraw;
import com.mygdx.game.Sprite.SpriteTouchBegin;
import com.mygdx.game.Sprite.SpriteTouchEnd;

/**
 * Extension to give button functions to a sprite.
 */

public class ButtonExtension implements SpriteDraw,SpriteTouchBegin,SpriteTouchEnd {
    static final public int LOCKED=0;
    static final public int UP=1;
    static final public int PRESSED=2;

    public ExtensibleSprite sprite;
    public Choice choice;

    public ButtonDraw buttonDraw;
    public ButtonTouchBegin buttonTouchBegin;
    public ButtonTouchEnd buttonTouchEnd;
    public ButtonAct buttonAct;

    public int state;
    SpriteDraw previousDraw;


    public ButtonExtension(ButtonActions buttonActions,ExtensibleSprite sprite){
        state=UP;
        this.sprite=sprite;
        sprite.buttonExtension=this;
        previousDraw=sprite.spriteDraw;
        sprite.setDraw(this);
        sprite.setTouchBegin(this);
        sprite.setTouchEnd(this);
        setButtonDraw(buttonActions.drawTinted);
        setButtonAct(buttonActions.actNull);
        setButtonTouchBegin(buttonActions.touchBeginNull);
        setButtonTouchEnd(buttonActions.touchEndNull);
    }

    /**
     * Set an object that knows how to draw the button and sprite
     *
     * @param buttonDraw ButtonDraw
     */
    public void setButtonDraw(ButtonDraw buttonDraw) {
        this.buttonDraw = buttonDraw;
    }

    /**
     * Set object that has method for touchBegin
     *
     * @param buttonTouchBegin ButtonTouchBegin object
     */
    public void setButtonTouchBegin(ButtonTouchBegin buttonTouchBegin) {
        this.buttonTouchBegin = buttonTouchBegin;
    }

    /**
     * Set object that has method for touchEnd
     *
     * @param buttonTouchEnd ButtonTouchEnd
     */
    public void setButtonTouchEnd(ButtonTouchEnd buttonTouchEnd) {
        this.buttonTouchEnd = buttonTouchEnd;
    }

    /**
     * Set object that has method for what the button should do.
     *
     * @param buttonAct ButtonAct
     */
    public void setButtonAct(ButtonAct buttonAct) {
        this.buttonAct = buttonAct;
    }

    /**
     * Set state of button, especially to lock or unlock button
     *
     * @param state int
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * set that button is up if not locked
     */
    public void setStateUp() {
        if (state!=LOCKED){
            state = UP;
        }
    }

    /**
     * set that button is pressed if not locked
     */
    public void setStatePressed() {
        if (state!=LOCKED){
            state = PRESSED;
        }
    }

    /**
     * Set the tint of the sprite depending on the button state.
     */
    public void tintSprite(){
        switch (state){
            case LOCKED: sprite.setColor(Color.GRAY);
                break;
            case UP: sprite.setColor(Color.WHITE);
                break;
            case PRESSED: sprite.setColor(Color.YELLOW);
                break;
        }
    }

    /**
     * Drawing the sprite: Uses the decorator pattern.
     * First choose a tint,then draw the sprite.
     * Override for something more fancy.
     *
     * @param sprite Extensible Sprite
     * @param batch  Batch
     * @param camera Camera
     */
    @Override
    public void draw(ExtensibleSprite sprite, Batch batch, Camera camera) {
        buttonDraw.draw(this,sprite,batch,camera);
    }

    /**
     * Switch on to pressed, and inverse.
     */
    public void toggleState(){
        state=3-state;
    }

    /**
     * Call the act method for doing what the button should do.
     *
     * @return boolean, true if something changed
     */
    public boolean act(){
        return buttonAct.act(this);
    }

    /**
     * Touch begin on the sprite: Only this action. Thus no decorator.
     * May act on others. Or toggle itself.
     *
     * @param sprite   ExtensibleSprite
     * @param touchPosition
     * @return boolean, true if something changes
     */
    public boolean touchBegin(ExtensibleSprite sprite, Vector2 touchPosition){
        if (state== LOCKED){
            return false;
        }
        return buttonTouchBegin.touchBegin(this);
    }


    @Override
    public boolean touchEnd(ExtensibleSprite sprite, Vector2 position) {
        if (state== LOCKED){
            return false;
        }
        return buttonTouchEnd.touchEnd(this);
    }

    /**
     * Free resources if sprite gets freed.
     */
    public void free(){}
}
