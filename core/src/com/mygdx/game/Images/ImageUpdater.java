package com.mygdx.game.Images;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.utilities.Device;

/**
 * Update an image (textureRegion), dispose textures if created
 */

public abstract class ImageUpdater implements Disposable{
    boolean disposeTextures;
    boolean imageExists=false;
    Device device;
    int imageNumber=0;
    public TextureRegion image;

    /**
     * create, indicating if textures have to be disposed and with device
     *
     * @param disposeTextures
     * @param device Device, with disposer
     */
    public ImageUpdater(boolean disposeTextures, Device device){
        this.disposeTextures=disposeTextures;
        this.device=device;
        if (disposeTextures) {
            device.disposer.add(this, "ImageUpdater");
        }

    }

    /**
     * Depending on project.
     * Create an image TextureRegion, the textureRegion contains the image
     * May have its own texture.
     *
     * @param imageNumber int, number of the image
     * @return TextureRegion, with the image
     */
    public abstract TextureRegion createImage(int imageNumber);

    /**
     * update and return the image using the abstract createImage method
     * dispose texture of already existing image with own texture
     *
     * @param imageNumber int, number of the image
     * @return TextureRegion, with the image
     */
    public TextureRegion updateImage(int imageNumber){
        if (!imageExists||(this.imageNumber!=imageNumber)){
            dispose();
            image=createImage(imageNumber);
            this.imageNumber=imageNumber;
            imageExists =true;
        }
        return image;
    }

    @Override
    /**
     * the texture will be disposed if the image has its own texture
     */
    public void dispose(){
        if (disposeTextures&& imageExists){
            image.getTexture().dispose();
            imageExists =false;
        }
    }
}
