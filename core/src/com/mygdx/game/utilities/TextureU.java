package com.mygdx.game.utilities;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * simple methods for textures and textureRegions
 */

public class TextureU {

    /**
     * set texture to linear interpolation
     *
     * @param texture Texture
     */
    public static void linearInterpolation(Texture texture) {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    /**
     * set texture to  nearest neighbor "interpolation"
     *
     * @param texture Texture
     */
    public static void nearestInterpolation(Texture texture) {
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    /**
     * Set the texture of a TextureRegion to linear interpolation.
     *
     * @param textureRegion TextureRegion
     */
    public static void linearInterpolation(TextureRegion textureRegion) {
        linearInterpolation(textureRegion.getTexture());
    }

    /**
     * Set the texture of a TextureRegion to nearest neighbor "interpolation".
     *
     * @param textureRegion TextureRegion
     */
    public static void nearestInterpolation(TextureRegion textureRegion) {
        nearestInterpolation(textureRegion.getTexture());
    }


    /**
     * Create a texture region from a pixmap. Make linear interpolation.
     *
     * @param pixmap Pixmap, dispose the pixmap later
     * @return TextureRegion obtained from pixmap, linear interpolation
     */
    public static TextureRegion textureRegionFromPixmap(Pixmap pixmap){
        TextureRegion result=new TextureRegion(new Texture(pixmap));
        linearInterpolation(result);
        return result;
    }
}
