package com.mygdx.game;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Buttons.BigChoice;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.L;

/**
 * Created by peter on 10/23/17.
 */

public class BigChoiceTest extends BigChoice {


    public BigChoiceTest(Viewport viewport, Device device){
        super(viewport, device);
        numberOfChoices=3;
    }

    @Override
    public void resize(int width, int height) {
        nextButton.setCenter(400,250);
        previousButton.setCenter(100,250);
        centerButton.setCenter(250,250);
    }

    @Override
    public void centerAction() {
        L.og(" doing this choice: "+choice);
    }
}
