package com.mygdx.game.Buttons;

import com.mygdx.game.Pieces.TouchableCollection;
import com.mygdx.game.Sprite.ExtensibleSprite;

/**
 * Collect interacting buttons. They are sprites with button extensions.
 * All buttons interact. There can be simple sprites without extension too.
 * But not independent buttons.
 */

public class ButtonCollection extends TouchableCollection<ExtensibleSprite> {

    /**
     * Add items to the end of the list. For buttons set their ButtonExtension to reference to this collection.
     *
     * @param sprites
     */
    public void add(ExtensibleSprite... sprites){
        super.addLast(sprites);
        ButtonExtension buttonExtension;
        for (ExtensibleSprite sprite:sprites) {
            buttonExtension=sprite.buttonExtension;
            if (buttonExtension!=null){
                buttonExtension.collection=this;
            }
        }
    }

    /**
     * Remove a given touchable object, using identity. Searches all. Removes multiple occurencies.
     * If it is a button sets its buttonExtension collection to null.
     *
     * @param sprite to remove
     * @return true if something has been removed
     */
    public boolean remove(ExtensibleSprite sprite) {
        boolean success = super.remove(sprite);
        ButtonExtension buttonExtension=sprite.buttonExtension;
        if (buttonExtension!=null){
            buttonExtension.collection=null;
        }
        return success;
    }

    /**
     * set state of all buttons to up, except if they are locked.
     */
    public void setStatesUp(){
        ButtonExtension buttonExtension;
        for (ExtensibleSprite sprite : items) {
            buttonExtension=sprite.buttonExtension;
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
        if (buttonExtension!=null){
            setStatesUp();
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