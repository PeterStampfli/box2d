package com.mygdx.game.Buttons;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Images.ImageUpdater;
import com.mygdx.game.Pieces.TouchableCollection;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.Resizable;
import com.mygdx.game.utilities.Timer;

/**
 * For making choices, assembles a center button and buttons around it
 * Resize has to define positions, add to resizables
 */

abstract public class BigChoice extends TouchableCollection implements Resizable {
    public Device device;
    public Viewport viewport;
    Timer timer;
    float repeatDelay=1;
    float repeatIntervall=0.5f;

    public int choice=0;                           // going from 0 to numberOfChoices-1
    public int numberOfChoices=0;
    // the buttons (for resizing)
    public ExtensibleSprite nextButton,previousButton,centerButton;
    public ImageUpdater centerImageUpdater;

    /**
     * Create and add to resizables of the device
     * the buttons have to be created later
     *
     * @param viewport Viewport, for resizing
     * @param device Device
     */
    public BigChoice(Viewport viewport,Device device){
        this.viewport=viewport;
        this.device=device;
        device.addResizable(this);
        timer=new Timer();
    }

    // we need a resize(width,height) method with access to viewport and buttons

    /**
     * the action method for the center button
     */
    abstract public void centerAction();

    /**
     * update the center image
     */
    public void updateCenterImage(){
        centerButton.setRegion(centerImageUpdater.updateImage(choice));
    }

    // create the buttons

    /**
     * Create the button for going to next choice
     *
     * @param image TextureRegion with the image, determines size of button
     * @return BigChoice, for chaining
     */
    public BigChoice createNextButton(TextureRegion image){
        Runnable goNext=new Runnable() {
            @Override
            public void run() {
                choice=(choice==numberOfChoices-1)?0:choice+1;
                updateCenterImage();
            }
        };
        nextButton=device.buttonBuilder.repeatingPushButton(goNext,image);
        add(nextButton);
        return  this;
    }

    /**
     * Create the button for going to previous choice
     * starts timer when pushed, goes to next choice if timer not firing
     *
     * @param image TextureRegion with the image, determines size of button
     * @return BigChoice, for chaining
     */
    public BigChoice createPreviousButton(TextureRegion image){
        Runnable goPrevious=new Runnable() {
            @Override
            public void run() {
                choice=(choice==0)?numberOfChoices-1:choice-1;
                updateCenterImage();
            }
        };
        previousButton=device.buttonBuilder.repeatingPushButton(goPrevious,image);
        add(previousButton);
        return this;
    }


// center button is extensibleSprite with buttonextension, change image with setRegion(textureregion)


    public BigChoice createCenterButton(ImageUpdater imageUpdater){
        centerImageUpdater=imageUpdater;
        Runnable doCenter=new Runnable() {
            @Override
            public void run() {
                centerAction();
            }
        };
        centerButton=device.buttonBuilder.pushButton(doCenter,centerImageUpdater.updateImage(0));

        add(centerButton);

        return this;
    }

}
