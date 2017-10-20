package com.mygdx.game.Buttons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Pieces.TouchableCollection;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.Resizable;

/**
 * For making choices, assembles a center button and buttons around it
 * Resize has to define positions, add to resizables
 */

abstract public class BigChoice extends TouchableCollection implements Resizable {
    public Device device;
    public Viewport viewport;

    int choice=0;                           // going from 0 to numberOfChoices-1
    int numberOfChoices=0;
    // the buttons (for resizing)
    ExtensibleSprite nextButton,previousButton,centerButton;

    /**
     * Create with center, add center to collection
     *
     * @param viewport Viewport, for resizing
     * @param device Device
     */
    public BigChoice(Viewport viewport,Device device){
        this.viewport=viewport;
        this.device=device;
    }


    /**
     * update the center image
     */
    public void updateCenterImage(){


        // update image
    }

    /**
     * update the center image
     */
    public void previousChoice(){
        choice--;
        if (choice<0){
            choice=numberOfChoices;
        }
        // update image
    }

    // create the buttons

    /**
     * Create the button for going to next choice
     *
     * @param image TextureRegion with the image, determines size of button
     */
    public void  createNextButton(TextureRegion image){
        Runnable action=new Runnable() {
            @Override
            public void run() {
                choice=(choice==numberOfChoices-1)?0:choice+1;
                updateCenterImage();
            }
        };
        nextButton=device.buttonBuilder.pushButton(action,image);
        add(nextButton);
    }

    /**
     * Create the button for going to previous choice
     *
     * @param image TextureRegion with the image, determines size of button
     */
    public void  createPreviousButton(TextureRegion image){
        Runnable action=new Runnable() {
            @Override
            public void run() {
                choice=(choice==0)?numberOfChoices-1:choice-1;
                updateCenterImage();
            }
        };
        previousButton=device.buttonBuilder.pushButton(action,image);
        add(nextButton);
    }


// center button is extensibleSprite with buttonextension, change image with setRegion(textureregion)


}
