package com.mygdx.game.Images;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.BasicAssets;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.L;

/**
 * Draw Lines on screen, assuming that drawing the discs is not expensive
 */

public class DrawLines {

    Device device;
    TextureRegion discImage;
    TextureRegion lineImage;
    final private int defaultImageSize=20;
    private float sizeFactor;
    Array<Vector2> points=new Array<Vector2>();
    IntArray firstEndPoints=new IntArray();
    IntArray secondEndPoints=new IntArray();


    public DrawLines(Device device, String discImageName, String lineImageName){
        this.device=device;
        BasicAssets basicAssets=device.basicAssets;
        discImage=basicAssets.getTextureRegion(discImageName);
        lineImage=basicAssets.getTextureRegion(lineImageName);


        L.og(discImage);

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
        float size=discImage.getRegionWidth();
        sizeFactor=1+2/size;

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

    static public TextureRegion makeLineImage(int size){
        Mask mask=new Mask(1,size+2);
        mask.invert();
        mask.alpha[0]=0;
        mask.alpha[size+1]=0;
        return mask.createTransparentWhiteTextureRegion();
    }


    public void addLines(float... coordinates){
        points.add(new Vector2(coordinates[0],coordinates[1]));
        int length=coordinates.length;
        for (int i=2;i<length;i+=2){
            points.add(new Vector2(coordinates[i],coordinates[i+1]));
            firstEndPoints.add(points.size-2);
            secondEndPoints.add(points.size-1);
        }
    }

    public void draw(SpriteBatch batch, float thickness){

        float regionSize=thickness*sizeFactor;
        for (Vector2 point:points){
            batch.draw(discImage,point.x-0.5f*regionSize,point.y-0.5f*regionSize,regionSize,regionSize);
        }
        int length=firstEndPoints.size;
        Vector2 firstPoint;
        Vector2 secondPoint;
        float centerX;
        float centerY;
        float lineLength;
        float lineAngle;
        for (int i=0;i<length;i++){
            firstPoint=points.get(firstEndPoints.get(i));
            L.og("*** "+firstPoint.toString());
            secondPoint=points.get(secondEndPoints.get(i));
            L.og(secondPoint.toString());
            lineLength=firstPoint.dst(secondPoint);
            lineAngle= MathUtils.radiansToDegrees*MathUtils.atan2(secondPoint.y-firstPoint.y,
                                                                  secondPoint.x-firstPoint.x);
            L.og(lineLength);
            L.og(lineAngle);
            centerX=0.5f*(secondPoint.x-firstPoint.x);
            centerY=0.5f*(secondPoint.y-firstPoint.y);
            batch.draw(lineImage,
                    firstPoint.x+centerX-0.5f*lineLength,firstPoint.y+centerY-0.5f*regionSize,
                    0.5f*lineLength,0.5f*regionSize,lineLength,regionSize,
                    1,1,lineAngle);
        }

    }

}
