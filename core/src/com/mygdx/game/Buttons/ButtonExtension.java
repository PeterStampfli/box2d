package com.mygdx.game.Buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Pieces.TouchableCollection;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.SpriteDraw;
import com.mygdx.game.Sprite.SpriteTouchBegin;
import com.mygdx.game.Sprite.SpriteTouchEnd;

/**
 * Extension to give button functions to a sprite. Use buttonBuilder to make buttons.
 * Decorator pattern for sprite draw.
 */

public class ButtonExtension implements SpriteDraw,SpriteTouchBegin,SpriteTouchEnd {
    static final public int LOCKED=0;
    static final public int UP=1;
    static final public int PRESSED=2;
    static public Color COLOR_LOCKED=Color.GRAY;
    static public Color COLOR_UP=Color.WHITE;
    static public Color COLOR_PRESSED=Color.GOLD;

    public ExtensibleSprite sprite;
    public TouchableCollection<ExtensibleSprite> buttonCollection;

    public ButtonDraw buttonDraw;
    public ButtonTouchBegin buttonTouchBegin;
    public ButtonTouchEnd buttonTouchEnd;
    public ButtonEffect buttonEffect;

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
     * set object that has the act-method for the effective action of the button
     *
     * @param buttonEffect ButtonAction object with act method for doing what the button does
     */
    public void setButtonEffect(ButtonEffect buttonEffect) {
        this.buttonEffect = buttonEffect;
    }

    /**
     * Set state of button, especially to lock or unlock button
     * Locked=0,up=1,pressed=2
     *
     * @param state int, for the state
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
     * Switch on to pressed, and inverse.
     */
    public void toggleState(){
        if (state!=LOCKED) {
            state = 3 - state;
        }
    }

    /**
     * Set the tint of the sprite depending on the button state.
     */
    public void tintSprite(){
        switch (state){
            case LOCKED: sprite.setColor(COLOR_LOCKED);
                break;
            case UP: sprite.setColor(COLOR_UP);
                break;
            case PRESSED: sprite.setColor(COLOR_PRESSED);
                break;
        }
    }

    /**
     * Draw the basic sprite without the button extension addons.
     * For the draw-method of the buttonDraw object.
     */
    public void doBasicSpriteDraw(){
        basicSpriteDraw.draw(sprite);
    }

    /**
     * Drawing the sprite: Uses the decorator pattern. And an object with a draw method.
     *  @param sprite Extensible Sprite
     *
     */
    @Override
    public void draw(ExtensibleSprite sprite) {
        buttonDraw.draw(this);
    }

    /**
     * Touch begin on the sprite: Only this action. Thus no decorator.
     * May act on others. Or toggle itself.
     *
     * @param sprite   ExtensibleSprite
     * @param touchPosition Vector2, position of the touch
     */
    @Override
    public void touchBegin(ExtensibleSprite sprite, Vector2 touchPosition){
        if (state!= LOCKED) {
            buttonTouchBegin.touchBegin(this);
        }
    }

    /**
     * Method for calling the buttonAct object that does what the button is supposed to do.
     */
    public void act(){
        buttonEffect.act(this);
    }

    /**
     * Touch end on the sprite: Does nothing if locked,
     * else calls the effective buttonTouchEnd.
     *  @param sprite   ExtensibleSprite
     *
     */
    @Override
    public void touchEnd(ExtensibleSprite sprite) {
        if (state!= LOCKED) {
            buttonTouchEnd.touchEnd(this);
        }
    }

    /**
     * do a button press (for the initial button press of a buttonCollection)
     */
    public void press(){
        if (state!=LOCKED){
            buttonTouchBegin.touchBegin(this);
            buttonTouchEnd.touchEnd(this);
        }
    }

    /**
     * Free resources if sprite gets freed.
     */
    public void free(){}
}
