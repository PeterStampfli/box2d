package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.mygdx.game.Images.ImageUpdater;
import com.mygdx.game.Images.Mask;
import com.mygdx.game.utilities.Device;

/**
 * Created by peter on 10/23/17.
 */

public class ImageUpdaterTest extends ImageUpdater {

    public ImageUpdaterTest(boolean disposeTextures, Device device){
        super(disposeTextures, device);
    }


    @Override
    public TextureRegion createImage(int imageNumber) {


        Circle circle=new Circle(0,0,60);
        Mask mask=Mask.create(circle);
        Color color=Color.WHITE;
        switch (imageNumber){
            case 0: color=Color.WHITE;
                break;
            case 1: color=Color.BROWN;
                break;
            case 2: color=Color.RED;
                break;



        }

        return mask.createColorTextureRegion(color);
    }
}
