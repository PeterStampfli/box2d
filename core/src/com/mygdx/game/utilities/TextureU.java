package com.mygdx.game.utilities;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by peter on 8/4/17.
 */

public class TextureU {

    /**
     * set texture to linear interpolation
     *
     * @param texture
     */
    public static void linearInterpolation(Texture texture) {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    /**
     * set texture to  nearest neighbor "interpolation"
     *
     * @param texture
     */
    public static void nearestInterpolation(Texture texture) {
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    /**
     * Set the texture of a TextureRegion to linear interpolation.
     *
     * @param textureRegion
     */
    public static void linearInterpolation(TextureRegion textureRegion) {
        linearInterpolation(textureRegion.getTexture());
    }

    /**
     * Set the texture of a TextureRegion to nearest neighbor "interpolation".
     *
     * @param textureRegion
     */
    public static void nearestInterpolation(TextureRegion textureRegion) {
        nearestInterpolation(textureRegion.getTexture());
    }


    /**
     * Create a texture region from a pixmap and dispose the pixmap. Make linear interpolation.
     *
     * @param pixmap will be disposed
     * @return
     */
    public static TextureRegion textureRegionFromPixmap(Pixmap pixmap){
        TextureRegion result=new TextureRegion(new Texture(pixmap));
        pixmap.dispose();
        linearInterpolation(result);
        return result;
    }
}
