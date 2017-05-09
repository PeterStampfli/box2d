package com.mygdx.game.Buttons;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Sprite.ExtensibleSprite;

/**
 * Created by peter on 5/9/17.
 */

public class Choice {

    Array<ButtonExtension> buttons=new Array<ButtonExtension>();
    ButtonExtension choosenButton;

    public void add(ButtonExtension button){
        buttons.add(button);
        button.choice=this;
    }

    public void add(ExtensibleSprite sprite){
        add(sprite.buttonExtension);
    }

    public void chooseFirstButton(){
        choosenButton=buttons.first();
    }

    public void setButtonsUp(){
        for (ButtonExtension button:buttons){
            button.setStateUp();
        }
    }


}
