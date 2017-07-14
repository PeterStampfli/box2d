package com.mygdx.game.Buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.SpriteDraw;
import com.mygdx.game.Sprite.SpriteTouchBegin;
import com.mygdx.game.Sprite.SpriteTouchEnd;

/**
 * Extension to give button functions to a sprite. Without the actual button effect.
 */

public class ButtonExtension implements SpriteDraw,SpriteTouchBegin,SpriteTouchEnd {
    static final public int LOCKED=0;
    static final public int UP=1;
    static final public int PRESSED=2;

    public ExtensibleSprite sprite;
    public ButtonCollection collection;

    public ButtonDraw buttonDraw;
    public ButtonTouchBegin buttonTouchBegin;
    public ButtonTouchEnd buttonTouchEnd;

    public int state;
    SpriteDraw basicSpriteDraw;


    public ButtonExtension(ExtensibleSprite sprite){
        state=UP;
        this.sprite=sprite;
        sprite.buttonExtension=this;
        basicSpriteDraw =sprite.spriteDraw;
        sprite.setDraw(this);
        sprite.setTouchBegin(this);
        sprite.setTouchEnd(this);
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
     * draw the basic sprite without the button extension addons
     */
    public void doBasicSpriteDraw(){
        basicSpriteDraw.draw(sprite);
    }

    /**
     * Drawing the sprite: Uses the decorator pattern.
     * First choose a tint,then draw the sprite.
     * Override for something more fancy.
     *  @param sprite Extensible Sprite
     *
     */
    @Override
    public void draw(ExtensibleSprite sprite) {
        buttonDraw.draw(this);
    }

    /**
     * Switch on to pressed, and inverse.
     */
    public void toggleState(){
        state=3-state;
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
