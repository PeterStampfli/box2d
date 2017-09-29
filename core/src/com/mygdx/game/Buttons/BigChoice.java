package com.mygdx.game.Buttons;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Pieces.TouchableCollection;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.Resizable;

/**
 * For making choices, assembles a center button and buttons around it
 * Resize has to define positions, add to resizables
 */

abstract public class BigChoice extends TouchableCollection implements Resizable {
    public Device device;
    public Viewport viewport;
    public BigChoiceCenter bigChoiceCenter;


    /**
     * Create with center, add center to collection
     *
     * @param viewport Viewport, for resizing
     * @param bigChoiceCenter BigChoiceCenter, the center image and action
     */
    public BigChoice(Viewport viewport,BigChoiceCenter bigChoiceCenter){
        this.device=bigChoiceCenter.device;
        this.viewport=viewport;
        this.bigChoiceCenter=bigChoiceCenter;
        add(bigChoiceCenter);
    }






}
