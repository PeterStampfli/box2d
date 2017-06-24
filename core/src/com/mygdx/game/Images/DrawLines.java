package com.mygdx.game.Images;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.BasicAssets;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.L;

/**
 * Draw Lines on screen, assuming that drawing the discs is not expensive.
 * Uses positions at time of rendering. Use for shapes2D and for arrays of vector2.
 *
 * We get smoother lines if lineImage has half the width of the line.
 * Adjust value of discImageSizeReduction to get good line end joins.
 * Smoothing only works if images are blown up.
 *
 * By default lines are white. Use other colors with device.spriteBatch.setColor(...)
 */

public class DrawLines {

    public TextureRegion discImage;
    public TextureRegion lineImage;
    SpriteBatch batch;
    final private int defaultImageSize = 20;
    private float regionSize;
    float width;
    public int discImageSizeReduction=-0;
    public Shape2DRenderer shape2DRenderer;

    /**
     * Load images for disc and line, if not present create them.
     *
     * @param device        Device with BasicAssets for loading and spriteBatch
     * @param discImageName
     * @param lineImageName
     */
    public DrawLines(Device device, String discImageName, String lineImageName) {
        batch=device.spriteBatch;
        BasicAssets basicAssets = device.basicAssets;
        discImage = basicAssets.getTextureRegion(discImageName);
        lineImage = basicAssets.getTextureRegion(lineImageName);
        if (discImage == null) {
            L.og("*** creating discImage: " + discImageName);
            discImage = makeDiscImage(defaultImageSize-discImageSizeReduction);
            //discImage = makeDiscImage(10);
            Basic.linearInterpolation(discImage);

        }
        if (lineImage == null) {
            L.og("*** creating lineImage: " + lineImageName);
            lineImage = makeLineImage(defaultImageSize/2);
         //   Basic.linearInterpolation(lineImage);
        }
        regionSize = discImage.getRegionWidth()+discImageSizeReduction;
        width=defaultImageSize;
        shape2DRenderer=device.shape2DRenderer;
    }

    /**
     * Make an TextureRegion with a white disc surrounded by transparent white pixels
     *
     * @param size int diameter of the disc, region size is size+2
     * @return TextureRegion
     */
    static public TextureRegion makeDiscImage(int size) {
        Mask mask = new Mask(size + 2, size + 2);
        float radius=size*0.5f;
        mask.fillCircle(0.5f * size +1, 0.5f * size +1, radius);
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
        float size = discImage.getRegionWidth() +discImageSizeReduction - 2f;
        regionSize = (size + 2f) * width / size;
        return this;
    }

    /**
     * Draw a disc fitting the line width at given position with coordinates (x,y)
     *  @param x
     * @param y
     */
    public void drawDisc(float x, float y) {
        float reducedSize=regionSize-discImageSizeReduction;
        batch.setColor(Color.FIREBRICK);
        batch.draw(discImage, x - 0.5f * reducedSize, y - 0.5f * reducedSize,
                   reducedSize, reducedSize);
        batch.setColor(Color.YELLOW);
        shape2DRenderer.circle(x,y,reducedSize/2);
    }

    /**
     * Draw a disc fitting the line width at given Vector2 positio
     *
     * @param batch
     * @param position
     */
    public void drawDisc(SpriteBatch batch, Vector2 position) {
        drawDisc(position.x,position.y);
    }

    /**
     * Draw a smooth line between points (x1,y1) and (x2,y2)
     *  @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void drawLine(float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float lineLength = (float) Math.sqrt(dx * dx + dy * dy);
        float lineAngle =  (float) Math.atan2(dy, dx);
        batch.draw(lineImage,
                x1 + 0.5f * (dx - lineLength), y1 + 0.5f * (dy - regionSize),
                0.5f * lineLength, 0.5f * regionSize, lineLength, regionSize,
                1, 1, MathUtils.radiansToDegrees *lineAngle);


    }

    /**
     * Draw a smooth line between points (x1,y1) and (x2,y2)
     *  @param a
     * @param b
     */
    public void drawLine(Vector2 a, Vector2 b) {
        drawLine(a.x,a.x,b.x,b.y);
    }

    /**
     * draw lines and dots for a series of points given as coordinate pairs.
     * Connects first and last point with a line if isLoop.
     *  @param isLoop
     * @param coordinates
     */
    public void draw(boolean isLoop, float... coordinates) {
        int length = coordinates.length;
        int i;
        for (i = 0; i < length; i += 2) {
            drawDisc(coordinates[i], coordinates[i + 1]);
        }
        for (i = 2; i < length; i += 2) {
            drawLine(coordinates[i - 2], coordinates[i - 1], coordinates[i], coordinates[i + 1]);
        }
        if (isLoop) {
            drawLine(coordinates[0], coordinates[1], coordinates[length - 2], coordinates[length - 1]);
        }
    }

    /**
     * draw lines and dots for a series of points given as coordinate pairs.
     *
     * @param coordinates
     */
    public void draw(float... coordinates) {
        draw(false, coordinates);
    }

    /**
     * draw lines and dots for a series of points given as coordinate pairs.
     * Connects first and last point with a line if isLoop.
     *  @param isLoop
     * @param coordinates
     */
    public void draw(boolean isLoop, FloatArray coordinates) {
        int length = coordinates.size;
        int i;
        for (i = 0; i < length; i += 2) {
            drawDisc(coordinates.get(i), coordinates.get(i+1));
        }
        for (i = 2; i < length; i += 2) {
            drawLine(coordinates.get(i - 2), coordinates.get(i - 1), coordinates.get(i ), coordinates.get(i +1));
        }
        if (isLoop) {
            drawLine(coordinates.get(0), coordinates.get(1), coordinates.get(length-2), coordinates.get(length-1));
        }
    }

    /**
     * draw lines and dots for a series of points given as coordinate pairs.
     *
     * @param coordinates
     */
    public void draw(FloatArray coordinates) {
        draw(false, coordinates);
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
            drawLine(points[i-1], points[i]);
        }
        if (isLoop) {
            drawLine(points[0], points[length-1]);
        }
    }

    /**
     * draw lines and dots for a series of points given as Vector2 objects.
     *
     * @param points
     */
    public void draw(Vector2... points) {
        draw(batch,false,points);
    }

    /**
     * draw lines and dots for a series of points given as Vector2 objects.
     * Connects first and last point with a line if isLoop.
     *  @param isLoop
     * @param points
     */
    public void draw(boolean isLoop, Array<Vector2> points) {
        int length = points.size;
        int i;
        for (i = 0; i < length; i++) {
            drawDisc(batch, points.get(i));
        }
        for (i = 1; i < length; i++) {
            drawLine(points.get(i-1), points.get(i));
        }
        if (isLoop) {
            drawLine(points.get(0), points.get(length-1));
        }
    }

    /**
     * draw lines and dots for a series of points given as Vector2 objects.
     *
     * @param points
     */
    public void draw(Array<Vector2> points) {
        draw(false,points);
    }

    /**
     * draw the lines of a polypoint object
     *
     * @param polypoint
     */
    public void draw(Polypoint polypoint){
        draw(polypoint.isLoop,polypoint.coordinates);
    }

    /**
     * draw the lines of a polyline object. Is not a closed loop.
     *
     * @param polyline
     */
    public void draw(Polyline polyline){
        draw(false,polyline.getTransformedVertices());
    }

    /**
     * draw the lines of a polygon object. Is a closed loop.
     *
     * @param polygon
     */
    public void draw(Polygon polygon){
        draw(true,polygon.getTransformedVertices());
    }

    /**
     * draw the lines of a chain object. May be a closed loop or not.
     *
     * @param chain
     */
    public void draw(Chain chain){
        draw(chain.isLoop,chain.coordinates);
    }

    /**
     * draw the line of an edge object.
     *
     * @param edge
     */
    public void draw(Edge edge){
        draw(false,edge.aX, edge.aY, edge.bX, edge.bY);
    }

    /**
     * draw lines and dots for a Shape2DdotsAndLines object.
     *
     * @param dotsAndLines
     */
    public void draw(DotsAndLines dotsAndLines){
        Circle circle;
        float[] vertices;
        for (Shape2D shape:dotsAndLines.shapes2D){
            if (shape instanceof Circle){
                circle=(Circle) shape;
                drawDisc(circle.x,circle.y);
            }
            else if (shape instanceof Polygon){
                vertices=((Polygon)shape).getTransformedVertices();
                draw(0.5f*(vertices[0]+vertices[2]),0.5f*(vertices[1]+vertices[3]),
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
        if (shape instanceof Polygon){
            draw((Polygon)shape);
        }
        else if (shape instanceof Polypoint){
            draw((Polypoint)shape);
        }
        else if (shape instanceof Polyline){
            draw((Polyline) shape);
        }
        else if (shape instanceof Edge){
            draw((Edge) shape);
        }
        else if (shape instanceof Chain){
            draw((Chain) shape);
        }
        else if (shape instanceof DotsAndLines){
            draw((DotsAndLines) shape);
        }
        else if (shape instanceof Shape2DCollection){         // without DotsAndLines
            Shape2DCollection shapes=(Shape2DCollection) shape;
            for (Shape2D subShape:shapes.shapes2D){
                draw(subShape);
            }
        }
    }
}
