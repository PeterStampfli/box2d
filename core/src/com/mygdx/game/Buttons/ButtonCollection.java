package com.mygdx.game.Buttons;

import com.mygdx.game.Pieces.Touchable;
import com.mygdx.game.Pieces.TouchableCollection;
import com.mygdx.game.Sprite.ExtensibleSprite;

/**
 * Collect interacting buttons. They are sprites with button extensions.
 */

public class ButtonCollection extends TouchableCollection {

    /**
     * Check if a touchable is an ExtensibleSprite with a ButtonExtension
     *
     * @param touchable Touchable, to check
     * @return boolean, true if it is a button
     */
    boolean isButton(Touchable touchable){
        if (touchable instanceof ExtensibleSprite){
            return ((ExtensibleSprite)touchable).buttonExtension!=null;
        }
        return  false;
    }

    /**
     * Get buttonExtension of an extensibleSprite. Null else.
     *
     * @param touchable Touchable, to check
     * @return ButtonExtension, of a "button", null else
     */
    ButtonExtension getButtonExtension(Touchable touchable){
        if (touchable instanceof ExtensibleSprite){
            return ((ExtensibleSprite)touchable).buttonExtension;
        }
        return null;
    }

    /**
     * Add touchables to the list. For buttons set their ButtonExtension to refernce to this.
     *
     * @param touchables Touchable... or Touchable[]
     */
    @Override
    public void add(Touchable... touchables){
        ButtonExtension buttonExtension;
        for (Touchable touchable : touchables) {
            this.touchables.add(touchable);
            buttonExtension=getButtonExtension(touchable);
            if (buttonExtension!=null){
                buttonExtension.collection=this;
            }
        }
    }

    /**
     * set state of all buttons to up, except if they are locked.
     */
    public void setStatesUp(){
        ButtonExtension buttonExtension;
        for (Touchable touchable : touchables) {
            buttonExtension=getButtonExtension(touchable);
            if (buttonExtension!=null){
                buttonExtension.setStateUp();
            }
        }
    }

    /**
     * Make that all buttons go to up, except locked buttons. The selected button is set to pressed.
     *
     * @param buttonExtension ButtonExtension of the button to select
     */
    public void select(ButtonExtension buttonExtension){
        setStatesUp();
        if (buttonExtension!=null){
            buttonExtension.setStatePressed();
        }
    }

    /**
     * Make that all buttons go to up, except locked buttons. The selected button is set to pressed
     * if the sprite has a ButtonExtension.
     *
     * @param sprite ExtensibleSprite
     */
    public void select(ExtensibleSprite sprite){
        select(sprite.buttonExtension);
    }

}