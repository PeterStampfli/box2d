package com.mygdx.game.Images;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.BasicAssets;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.L;

/**
 * Draw Lines on screen, assuming that drawing the discs is not expensive
 */

public class DrawLines {

    TextureRegion discImage;
    TextureRegion lineImage;
    final private int defaultImageSize=20;
    private float regionSize;

    /**
     * Load images for disc and line, if not present create them.
     *
     * @param device Device with BasicAssets for loading
     * @param discImageName
     * @param lineImageName
     */
    public DrawLines(Device device, String discImageName, String lineImageName){
        BasicAssets basicAssets=device.basicAssets;
        discImage=basicAssets.getTextureRegion(discImageName);
        lineImage=basicAssets.getTextureRegion(lineImageName);
        if (discImage==null){
            L.og("*** creating discImage: "+discImageName);
            discImage=makeDiscImage(defaultImageSize);
            Basic.linearInterpolation(discImage);

        }
        if (lineImage==null){
            L.og("*** creating lineImage: "+lineImageName);
            lineImage=makeLineImage(defaultImageSize);
            Basic.linearInterpolation(lineImage);
        }
        regionSize=discImage.getRegionWidth();
    }

    /**
     * Make an TextureRegion with a white disc surrounded by transparent white pixels
     *
     * @param size int diameter of the disc, region size is size+2
     * @return TextureRegion
     */
    static public TextureRegion makeDiscImage(int size){
        Mask mask=new Mask(size+2,size+2);
        mask.fillCircle(0.5f*size+1,0.5f*size+1,size*0.5f);
        return mask.createTransparentWhiteTextureRegion();
    }

    /**
     * Make a textureRegion as a strip of white pixels, with transparent ends
     * @param size
     * @return
     */
    static public TextureRegion makeLineImage(int size){
        Mask mask=new Mask(1,size+2);
        mask.invert();
        mask.alpha[0]=0;
        mask.alpha[size+1]=0;
        return mask.createTransparentWhiteTextureRegion();
    }

    /**
     * Set the width for lines. The size of the textureRegions has to account for the transparent borders.
     * @param width
     * @return
     */
    public DrawLines setLineWidth(float width){
        float size=discImage.getRegionWidth()-2f;
        regionSize=(size+2f)*width/size;
        return this;
    }

    /**
     * Draw a disc fitting the line width at given position with coordinates (x,y)
     *
     * @param batch
     * @param x
     * @param y
     */
    public void drawDisc(SpriteBatch batch,float x,float y){
        batch.draw(discImage,x-0.5f*regionSize,y-0.5f*regionSize,regionSize,regionSize);
    }

    /**
     * Draw a smooth line between points (x1,y1) and (x2,y2)
     *
     * @param batch
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void drawLine(SpriteBatch batch,float x1,float y1,float x2,float y2){
        float dx=x2-x1;
        float dy=y2-y1;
        float lineLength=(float) Math.sqrt(dx*dx+dy*dy);
        float lineAngle=MathUtils.radiansToDegrees*MathUtils.atan2(dy,dx);
        batch.draw(lineImage,
                x1+0.5f*(dx-lineLength),y1+0.5f*(dy-regionSize),
                0.5f*lineLength,0.5f*regionSize,lineLength,regionSize,
                1,1,lineAngle);
    }


    public void draw(SpriteBatch batch){



    }

}
