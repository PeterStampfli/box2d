package com.mygdx.game.Images;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.BasicAssets;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.L;

/**
 * Draw Lines on screen, assuming that drawing the discs is not expensive. Uses positions at time of rendering.
 */

public class DrawLines {

    TextureRegion discImage;
    TextureRegion lineImage;
    final private int defaultImageSize = 20;
    private float regionSize;

    /**
     * Load images for disc and line, if not present create them.
     *
     * @param device        Device with BasicAssets for loading
     * @param discImageName
     * @param lineImageName
     */
    public DrawLines(Device device, String discImageName, String lineImageName) {
        BasicAssets basicAssets = device.basicAssets;
        discImage = basicAssets.getTextureRegion(discImageName);
        lineImage = basicAssets.getTextureRegion(lineImageName);
        if (discImage == null) {
            L.og("*** creating discImage: " + discImageName);
            discImage = makeDiscImage(defaultImageSize);
            Basic.linearInterpolation(discImage);

        }
        if (lineImage == null) {
            L.og("*** creating lineImage: " + lineImageName);
            lineImage = makeLineImage(defaultImageSize);
            Basic.linearInterpolation(lineImage);
        }
        regionSize = discImage.getRegionWidth();
    }

    /**
     * Make an TextureRegion with a white disc surrounded by transparent white pixels
     *
     * @param size int diameter of the disc, region size is size+2
     * @return TextureRegion
     */
    static public TextureRegion makeDiscImage(int size) {
        Mask mask = new Mask(size + 2, size + 2);
        mask.fillCircle(0.5f * size + 1, 0.5f * size + 1, size * 0.5f);
        return mask.createTransparentWhiteTextureRegion();
    }

    /**
     * Make a textureRegion as a strip of white pixels, with transparent ends
     *
     * @param size
     * @return
     */
    static public TextureRegion makeLineImage(int size) {
        Mask mask = new Mask(1, size + 2);
        mask.invert();
        mask.alpha[0] = 0;
        mask.alpha[size + 1] = 0;
        return mask.createTransparentWhiteTextureRegion();
    }

    /**
     * Set the width for lines. The size of the textureRegions has to account for the transparent borders.
     *
     * @param width
     * @return
     */
    public DrawLines setLineWidth(float width) {
        float size = discImage.getRegionWidth() - 2f;
        regionSize = (size + 2f) * width / size;
        return this;
    }

    /**
     * Draw a disc fitting the line width at given position with coordinates (x,y)
     *
     * @param batch
     * @param x
     * @param y
     */
    public void drawDisc(SpriteBatch batch, float x, float y) {
        batch.draw(discImage, x - 0.5f * regionSize, y - 0.5f * regionSize, regionSize, regionSize);
    }

    /**
     * Draw a disc fitting the line width at given Vector2 positio
     *
     * @param batch
     * @param position
     */
    public void drawDisc(SpriteBatch batch, Vector2 position) {
        drawDisc(batch,position.x,position.y);
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
    public void drawLine(SpriteBatch batch, float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float lineLength = (float) Math.sqrt(dx * dx + dy * dy);
        float lineAngle = MathUtils.radiansToDegrees * MathUtils.atan2(dy, dx);
        batch.draw(lineImage,
                x1 + 0.5f * (dx - lineLength), y1 + 0.5f * (dy - regionSize),
                0.5f * lineLength, 0.5f * regionSize, lineLength, regionSize,
                1, 1, lineAngle);
    }

    /**
     * Draw a smooth line between points (x1,y1) and (x2,y2)
     *
     * @param batch
     * @param a
     * @param b
     */
    public void drawLine(SpriteBatch batch, Vector2 a,Vector2 b) {
        drawLine(batch,a.x,a.x,b.x,b.y);
    }

    /**
     * draw lines and dots for a series of points given as coordinate pairs.
     * Connects first and last point with a line if isLoop.
     *
     * @param batch
     * @param isLoop
     * @param coordinates
     */
    public void draw(SpriteBatch batch, boolean isLoop, float... coordinates) {
        int length = coordinates.length;
        int i;
        for (i = 0; i < length; i += 2) {
            drawDisc(batch, coordinates[i], coordinates[i + 1]);
        }
        for (i = 2; i < length; i += 2) {
            drawLine(batch, coordinates[i - 2], coordinates[i - 1], coordinates[i], coordinates[i + 1]);
        }
        if (isLoop) {
            drawLine(batch, coordinates[0], coordinates[1], coordinates[length - 2], coordinates[length - 1]);
        }
    }

    /**
     * draw lines and dots for a series of points given as coordinate pairs.
     *
     * @param batch
     * @param coordinates
     */
    public void draw(SpriteBatch batch, float... coordinates) {
        draw(batch, false, coordinates);
    }

    /**
     * draw lines and dots for a series of points given as coordinate pairs.
     * Connects first and last point with a line if isLoop.
     *
     * @param batch
     * @param isLoop
     * @param coordinates
     */
    public void draw(SpriteBatch batch, boolean isLoop, FloatArray coordinates) {
        int length = coordinates.size;
        int i;
        for (i = 0; i < length; i += 2) {
            drawDisc(batch, coordinates.get(i), coordinates.get(i+1));
        }
        for (i = 2; i < length; i += 2) {
            drawLine(batch, coordinates.get(i - 2), coordinates.get(i - 1), coordinates.get(i ), coordinates.get(i +1));
        }
        if (isLoop) {
            drawLine(batch, coordinates.get(0), coordinates.get(1), coordinates.get(length-2), coordinates.get(length-1));
        }
    }

    /**
     * draw lines and dots for a series of points given as coordinate pairs.
     *
     * @param batch
     * @param coordinates
     */
    public void draw(SpriteBatch batch, FloatArray coordinates) {
        draw(batch, false, coordinates);
    }

    /**
     * draw lines and dots for a series of points given as Vector2 objects.
     * Connects first and last point with a line if isLoop.
     *
     * @param batch
     * @param isLoop
     * @param points
     */
    public void draw(SpriteBatch batch, boolean isLoop, Vector2... points) {
        int length = points.length;
        int i;
        for (i = 0; i < length; i++) {
            drawDisc(batch, points[i]);
        }
        for (i = 1; i < length; i++) {
            drawLine(batch, points[i-1], points[i]);
        }
        if (isLoop) {
            drawLine(batch, points[0], points[length-1]);
        }
    }

    /**
     * draw lines and dots for a series of points given as Vector2 objects.
     *
     * @param batch
     * @param points
     */
    public void draw(SpriteBatch batch, Vector2... points) {
        draw(batch,false,points);
    }

    /**
     * draw lines and dots for a series of points given as Vector2 objects.
     * Connects first and last point with a line if isLoop.
     *
     * @param batch
     * @param isLoop
     * @param points
     */
    public void draw(SpriteBatch batch, boolean isLoop, Array<Vector2> points) {
        int length = points.size;
        int i;
        for (i = 0; i < length; i++) {
            drawDisc(batch, points.get(i));
        }
        for (i = 1; i < length; i++) {
            drawLine(batch, points.get(i-1), points.get(i));
        }
        if (isLoop) {
            drawLine(batch, points.get(0), points.get(length-1));
        }
    }

    /**
     * draw lines and dots for a series of points given as Vector2 objects.
     *
     * @param batch
     * @param points
     */
    public void draw(SpriteBatch batch, Array<Vector2> points) {
        draw(batch,false,points);
    }

    /**
     * draw the lines of a polypoint object
     * @param batch
     * @param polypoint
     */
    public void draw(SpriteBatch batch,Polypoint polypoint){
        draw(batch,polypoint.isLoop,polypoint.coordinates);
    }

    /**
     * draw lines and dots for a Shape2DdotsAndLines object.
     *
     * @param batch
     * @param dotsAndLines
     */
    public void draw(SpriteBatch batch,DotsAndLines dotsAndLines){
        Circle circle;
        float[] vertices;
        for (Shape2D shape:dotsAndLines.shapes2D){
            if (shape instanceof Circle){
                circle=(Circle) shape;
                drawDisc(batch,circle.x,circle.y);
            }
            else if (shape instanceof Polygon){
                vertices=((Polygon)shape).getTransformedVertices();
                draw(batch,0.5f*(vertices[0]+vertices[2]),0.5f*(vertices[1]+vertices[3]),
                        0.5f*(vertices[4]+vertices[6]),0.5f*(vertices[5]+vertices[7]));
            }
        }
    }

    /**
     * Draw Shape2D shape DotsAndLines and collections.
     *
     * @param shape Shape2D to draw
     */
    public void draw(Shape2D shape){
        if (shape instanceof DotsAndLines){
            draw((DotsAndLines)shape);
        }
        else if (shape instanceof Shape2DCollection){         // without DotsAndLines
            Shape2DCollection shapes=(Shape2DCollection) shape;
            for (Shape2D subShape:shapes.shapes2D){
                draw(subShape);
            }
        }
    }

}
