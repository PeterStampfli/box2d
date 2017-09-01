package com.mygdx.game.Images;

import com.badlogic.gdx.graphics.Pixmap;
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
import com.mygdx.game.utilities.BasicAssets;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.L;
import com.mygdx.game.utilities.TextureU;

/**
 * Draw Lines on screen, assuming that drawing the discs is not expensive.
 * Uses positions at time of rendering. Use for items and for arrays of vector2.
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
    int imageWidth;
    float lineWidth;
    float imageSize;
    public float discImageSizeReduction=0.9f;
    public Shape2DRenderer shape2DRenderer;

    /**
     * Load images for disc and line, if not present create them with given width for lines.
     * Actual line width results from image sizes.
     *
     * @param device        Device, with BasicAssets for loading and spriteBatch
     * @param discImageName String, name of the image of the disc
     * @param lineImageName String, name of the image of the line
     * @param width int, width of the line
     */
    public DrawLines(Device device,String discImageName, String lineImageName, int width) {
        imageWidth=width;
        lineWidth=width;
        batch=device.spriteBatch;
        BasicAssets basicAssets = device.basicAssets;
        discImage = basicAssets.getTextureRegion(discImageName);
        lineImage = basicAssets.getTextureRegion(lineImageName);
        if (discImage == null) {
            L.og("*** creating discImage: " + discImageName);
            discImage = makeDiscImage(width);
            TextureU.linearInterpolation(discImage);

        }
        if (lineImage == null) {
            L.og("*** creating lineImage: " + lineImageName);
            lineImage = makeLineImage(width);
            TextureU.linearInterpolation(lineImage);
        }
        imageSize=discImage.getRegionWidth();
        shape2DRenderer=device.shape2DRenderer;
    }

    /**
     * Make an pixmap with a white disc surrounded by transparent white pixels
     *
     * @param size int diameter of the disc, region size is size+4
     * @return Pixmap, for a disc image
     */
    static public Pixmap makeDiscPixmap(int size) {
        Mask mask = Mask.create(new Circle(0,0,size*0.5f));
        return mask.createColorPixmap();
    }

    /**
     * Make an TextureRegion with a white disc surrounded by a border of transparent white pixels.
     * The border is 2 pixels wide
     *
     * @param size int diameter of the disc, region size is size+4
     * @return TextureRegion, with the disc image
     */
    static public TextureRegion makeDiscImage(int size) {
        Pixmap pixmap=makeDiscPixmap(size);
        TextureRegion textureRegion=TextureU.textureRegionFromPixmap(pixmap);
        pixmap.dispose();
        return textureRegion;
    }

    /**
     * Make a pixmap as a strip of white pixels, with additional 2 transparent pixels at each end.
     *
     * @param size int width of the line, region size is size+4
     * @return Pixmap, for the line image
     */
    static public Pixmap makeLinePixmap(int size) {
        Mask mask = new Mask(1, size + 4);
        mask.invert();
        mask.alpha[0] = 0;
        mask.alpha[1] = 0;
        mask.alpha[size + 2] = 0;
        mask.alpha[size + 3] = 0;
        return mask.createColorPixmap();
    }

    /**
     * Make a textureRegion as a strip of white pixels, with transparent ends
     *
     * @param size int width of the line, region size is size+4
     * @return TextureRegion, with the line image
     */
    static public TextureRegion makeLineImage(int size) {
        Pixmap pixmap=makeLinePixmap(size);
        TextureRegion textureRegion=TextureU.textureRegionFromPixmap(pixmap);
        pixmap.dispose();
        return textureRegion;
    }

    /**
     * Set the width for lines. The size of the textureRegions has to account for the transparent borders.
     *
     * @param width float, width of the lines
     * @return this, for chaining
     */
    public DrawLines setLineWidth(float width) {
        lineWidth=width;
        return this;
    }

    /**
     * Draw a disc fitting the line width at given position with coordinates (x,y)
     *  @param x float, x-coordinate of center
     * @param y float, y-coordinate of center
     */
    public void drawDisc(float x, float y) {
        float reducedSize=discImageSizeReduction*imageSize;
        batch.draw(discImage, x - 0.5f * reducedSize, y - 0.5f * reducedSize,
                   reducedSize, reducedSize);
    }

    /**
     * Draw a disc fitting the line width at given Vector2 position
     *
     * @param position Vector2, position of center of disc
     */
    public void drawDisc(Vector2 position) {
        drawDisc(position.x,position.y);
    }

    /**
     * Draw a smooth line between points (x1,y1) and (x2,y2)
     * @param x1  float, x-coordinate of first point
     * @param y1 float, y-coordinate of first point
     * @param x2 float, x-coordinate of second point
     * @param y2 float, y-coordinate of second point
     */
    public void drawLine(float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float lineLength = (float) Math.sqrt(dx * dx + dy * dy);
        float lineAngle =  (float) Math.atan2(dy, dx);
        batch.draw(lineImage,
                x1 + 0.5f * (dx - lineLength), y1 + 0.5f * (dy - imageSize),
                0.5f * lineLength, 0.5f * imageSize, lineLength, imageSize,
                1, 1, MathUtils.radiansToDegrees *lineAngle);
    }

    /**
     * Draw a smooth line between points (x1,y1) and (x2,y2)
     * @param a Vector2, position of first point
     * @param b Vector2, position of second point
     */
    public void drawLine(Vector2 a, Vector2 b) {
        drawLine(a.x,a.y,b.x,b.y);
    }

    /**
     * draw lines and dots for a series of points given as coordinate pairs.
     * Connects first and last point with a line if isLoop.
     *
     * @param isLoop boolean, true if shape should be closed as a loop
     * @param coordinates float... or float[] with coordinate pairs of points
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
     * @param coordinates float... or float[] with coordinate pairs of points
     */
    public void draw(float... coordinates) {
        draw(false, coordinates);
    }

    /**
     * draw lines and dots for a series of points given as coordinate pairs.
     * Connects first and last point with a line if isLoop.
     *
     * @param isLoop boolean, true if shape should be closed as a loop
     * @param coordinates FloatArray with coordinate pairs of points
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
     * @param coordinates FloatArray with coordinate pairs of points
     */
    public void draw(FloatArray coordinates) {
        draw(false, coordinates);
    }

    /**
     * draw lines and dots for a series of points given as Vector2 objects.
     * Connects first and last point with a line if isLoop.
     *
     * @param isLoop boolean, true if shape should be closed as a loop
     * @param points Vector2 ... or Vector2[] array, positions of points
     */
    public void draw(boolean isLoop, Vector2... points) {
        int length = points.length;
        int i;
        for (i = 0; i < length; i++) {
            drawDisc(points[i]);
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
     * @param points Vector2... or Vector2[] array, positions of points
     */
    public void draw(Vector2... points) {
        draw(false,points);
    }

    /**
     * draw lines and dots for a series of points given as Vector2 objects.
     * Connects first and last point with a line if isLoop.
     *
     * @param isLoop boolean, true if shape should be closed as a loop
     * @param points Array of Vector2, positions of points
     */
    public void draw(boolean isLoop, Array<Vector2> points) {
        int length = points.size;
        int i;
        for (i = 0; i < length; i++) {
            drawDisc(points.get(i));
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
     * @param points Array of Vector2, positions of points
     */
    public void draw(Array<Vector2> points) {
        draw(false,points);
    }

    /**
     * draw the lines of a polypoint object
     *
     * @param polypoint Polypoint object
     */
    public void draw(Polypoint polypoint){
        draw(polypoint.isLoop,polypoint.coordinates);
    }

    /**
     * draw the lines of a polyline object. Is not a closed loop.
     *
     * @param polyline Polyline to draw
     */
    public void draw(Polyline polyline){
        draw(false,polyline.getTransformedVertices());
    }

    /**
     * draw the lines of a polygon object. Is a closed loop.
     *
     * @param polygon Polygon
     */
    public void draw(Polygon polygon){
        draw(true,polygon.getTransformedVertices());
    }

    /**
     * draw the lines of a chain object. May be a closed loop or not.
     *
     * @param chain Chain
     */
    public void draw(Chain chain){
        draw(chain.isLoop,chain.coordinates);
    }

    /**
     * draw the line of an edge object.
     *
     * @param edge Edge
     */
    public void draw(Edge edge){
        draw(false,edge.aX, edge.aY, edge.bX, edge.bY);
    }

    /**
     * draw lines and dots for a dotsAndLines object.
     * Dots are drawn as solid discs, lines as simple lines. Width may be different
     *
     * @param dotsAndLines DotsAndLines
     */
    public void draw(DotsAndLines dotsAndLines){
        Circle circle;
        float[] vertices;
        for (Shape2D shape:dotsAndLines.items){
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
            for (Shape2D subShape:shapes.items){
                draw(subShape);
            }
        }
    }

    /**
     * Draw a vertical or horizontal line with square ends.
     * Depending on which coordinate difference is greater.
     *
     * @param x1  float, x-coordinate of first point
     * @param y1 float, y-coordinate of first point
     * @param x2 float, x-coordinate of second point
     * @param y2 float, y-coordinate of second point
     */
    public void drawVerticalHorizontalLine(float x1, float y1,float x2,float y2){
        if (Math.abs(x2-x1)<Math.abs(y2-y1)) {
            batch.draw(lineImage, x1 + imageSize * 0.5f, Math.min(y1, y2) - lineWidth * 0.5f,0,0,
                    Math.abs(y2 - y1) + lineWidth,imageSize,1,1,90);
        }
        else {
            batch.draw(lineImage, Math.min(x1,x2)-lineWidth*0.5f,y1-imageSize*0.5f,
                    Math.abs(x2-x1)+lineWidth,imageSize);
        }
    }

    /**
     * Draw a vertical or horizontal line with square ends.
     * Depending on which coordinate difference is greater.
     *
     * @param a Vector2, first endpoint
     * @param b Vector2, second endpoint
     */
    public void drawVerticalHorizontalLine(Vector2 a,Vector2 b) {
        drawVerticalHorizontalLine(a.x,a.y,b.x,b.y);
    }
}
