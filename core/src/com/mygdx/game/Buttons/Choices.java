package com.mygdx.game.Buttons;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Sprite.ExtensibleSprite;

/**
 * Make choices with a collection of interacting buttons.
 */

public class Choices {

    Array<ButtonExtension> buttons=new Array<ButtonExtension>();
    ButtonExtension choosenButton;

    /**
     * Add a button(Extension) to the choices. A button can only be in one Choices object.
     *
     * @param button ButtonExtension, add to the choices, set choices of button to this
     */
    public void add(ButtonExtension button){
        buttons.add(button);
        button.choices =this;
    }


    /**
     * Add a button(Extension) of an extensibleSprite to the choices.
     * A button can only be in one Choices object.
     *
     * @param sprite ExtensibleSprite, add to the choices, set choices of buttonExtension to this
     */

    public void add(ExtensibleSprite sprite){
        add(sprite.buttonExtension);
    }

    public void select(int iButton){
        select(buttons.get(iButton));
    }

    /**
     * Select a button.
     */
    public void select(ButtonExtension thisButton){
        for (ButtonExtension button:buttons){
            button.setStateUp();
        }
        thisButton.setStatePressed();
        choosenButton=thisButton;
    }


}
