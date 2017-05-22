package com.mygdx.game.Images;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.utilities.BasicAssets;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.L;

/**
 * Draw dotsAndLines on screen, assuming that drawing the discs is not expensive
 */

public class DrawDotsAndLines {

    Device device;
    TextureRegion discImage;
    TextureRegion lineImage;
    final private int defaultImageSize=20;


    public DrawDotsAndLines(Device device,String discImageName, String lineImageName){
        this.device=device;
        BasicAssets basicAssets=device.basicAssets;
        discImage=basicAssets.getTextureRegion(discImageName);
        lineImage=basicAssets.getTextureRegion(lineImageName);


        L.og(discImage);

        if (discImage==null){
            L.og("*** creating discImage: "+discImageName);
            discImage=makeDiscImage(defaultImageSize);

        }
        if (lineImage==null){
            L.og("*** creating lineImage: "+lineImageName);
            lineImage=makeLineImage(defaultImageSize);

        }
    }

    static public TextureRegion makeDiscImage(int size){
        Mask mask=new Mask(size+2,size+2);
        mask.fillCircle(0.5f*size+1,0.5f*size+1,size*0.5f);
        return mask.createTransparentWhiteTextureRegion();
    }

    static public TextureRegion makeLineImage(int size){
        Mask mask=new Mask(1,size+2);
        mask.invert();
        mask.alpha[0]=0;
        mask.alpha[size+1]=0;
        return mask.createTransparentWhiteTextureRegion();
    }
}
