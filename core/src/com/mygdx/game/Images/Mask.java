package com.mygdx.game.Images;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utilities.MathU;
import com.mygdx.game.utilities.TextureU;

import java.nio.ByteBuffer;

/**
 * A rectangular region of bytes to use as a mask for pixmaps. Uses a simple byte array.
 * The y-axis is flipped to compensate for the inverted y-axis of pixmaps.
 * A pixel with indices(i,j) lies at (x,y)=(i+1/2,j+1/2).
 * In the resulting textureRegions, the y-axis points upwards.
 */

public class Mask {
    public byte[] alpha;
    public int width;
    public int height;
    public float smoothLengthHalf,smoothFactor;

    /**
     * Create a transparent mask of given width and height.
     *
     * @param width  int, width of the pixmap
     * @param height int, height of the pixmap
     */
    public Mask(int width, int height) {
        this.width = width;
        this.height = height;
        alpha = new byte[width * height];
        setSmoothing(1);
        clear();
    }

    /**
     * Set smoothing. The length of the transition region.
     *
     * @param length float, 0 for no smoothing, 1 for minimal smooth border pixels.
     */
    public void setSmoothing(float length){
        length=Math.max(length, MathU.epsilon);
        smoothLengthHalf=length/2;
        smoothFactor=256/length;
    }

    /**
     * Flip the y-axis to compensate for the inversion of pixmaps. y=0 maps to y=height and inversely.
     *
     * @param y float, an y-coordinate
     * @return float, the y-coordinate is flipped
     */
    private float flipY(float y) {
        return height - y;
    }

    /**
     * Transform a byte into a positive integer between 0 and 255.
     * Note that a byte is cast as a signed 8 bit integer.
     *
     * @param b byte
     * @return int, between 0 and 255
     */
    private int toPosInt(byte b) {
        int iB = b;
        if (iB < 0) iB += 256;
        return iB;
    }

    /**
     * Make the mask transparent.
     *
     * @return this, for chaining
     */
    public Mask clear() {
        for (int index = alpha.length - 1; index >= 0; index--) {
            alpha[index] = 0;
        }
        return this;
    }

    /**
     * Make transparent border bytes. (For smoothing shapes)
     *
     * @return this, for chaining
     */
    public Mask transparentBorder() {
        int index;
        int length = alpha.length;
        for (index = 0; index < width; index++) {
            alpha[index] = 0;
            alpha[length - 1 - index] = 0;
        }
        for (index = 0; index < length; index += width) {
            alpha[index] = 0;
            alpha[index + width - 1] = 0;
        }
        return this;
    }

    /**
     * Invert the mask bytes. To draw transparent shapes in opaque regions.
     *
     * @return this, for chaining
     */
    public Mask invert() {
        for (int index = alpha.length - 1; index >= 0; index--) {
            alpha[index] = (byte) (255 - toPosInt(alpha[index]));
        }
        return this;
    }

    /**
     * For drawing, the mask will always be set to the larger value of both the existing mask byte and the
     * new opacity value.
     *
     * @param b byte, present mask byte
     * @param f float, opacity to draw, between 0 and 1, 1 and larger give 255
     * @return byte, maximum value
     */
    public byte maxByteFloat(byte b, float f) {
        return (byte) MathUtils.clamp(Math.floor(f * 256), toPosInt(b), 255);
    }

    /**
     * Set the opacity of a mask byte. Distance is from the boundary of a shape measured towards the
     * inside. The transition region width is given by setSmoothing(transitionLength).
     * This smooths out the shape. For efficiency check if the distance is in the transition region before calling this.
     *
     * @param index int, index of the byte in the alpha arrray
     * @param distance float, measured from the boundary, positve sign inside, negative outside.
     */
    public void setOpacity(int index,float distance){
        double newOpacity=Math.floor(128+distance*smoothFactor);
        if (newOpacity>=255){                 // in the transition region: fully opaque
            alpha[index]=(byte) 255;
            return;
        }
        if (toPosInt(alpha[index])<newOpacity){   // in the transition region: more opaque than previous
            alpha[index]=(byte) newOpacity;
        }
    }

    /**
     * Draw a disc of opaque bits with a smooth border given its center and radius.
     *
     * @param centerX     float, x-coordinate of the center
     * @param centerY     float, x-coordinate of the center
     * @param radius float, radius of the circle
     */
    public void fillCircle(float centerX, float centerY, float radius) {
        float dx, dy2, dx2Plusdy2;
        float radiusSq=radius*radius;
        float iRadius2=0.5f/radius;
        float outerRadius=radius+smoothLengthHalf;
        float outerRadiusSq=outerRadius*outerRadius;
        float innerRadius=radius-smoothLengthHalf;
        float innerRadiusSq=innerRadius*innerRadius;
        centerY = flipY(centerY);
        int iMax, iMin, jMax, jMin;
        iMax = Math.min(width - 1, MathUtils.ceil(centerX + outerRadius));
        iMin = Math.max(0, MathUtils.floor(centerX - outerRadius - 1));
        jMax = Math.min(height - 1, MathUtils.ceil(centerY + outerRadius));
        jMin = Math.max(0, MathUtils.floor(centerY - outerRadius - 1));
        int i, j, index;
        for (j = jMin; j <= jMax; j++) {
            index = j * width+iMin;
            dy2 = j + 0.5f - centerY;
            dy2 *= dy2;
            for (i = iMin; i <= iMax; i++) {
                dx = i + 0.5f - centerX;
                dx2Plusdy2 = dy2 + dx * dx;
                if (dx2Plusdy2 < innerRadiusSq) {    // d>smallLengthHalf
                    alpha[index]=(byte) 255;
                }
                else if (dx2Plusdy2 < outerRadiusSq) {  // d>-smallLengthHalf
                    setOpacity(index,(radiusSq-dx2Plusdy2)*iRadius2);
                }
                    index++;
            }
        }
    }

    /**
     * Draw a disc of opaque bits with a smooth border given its center and radius.
     *
     * @param center Vector2, center
     * @param radius float, radius
     */
    public void fillCircle(Vector2 center, float radius) {
        fillCircle(center.x, center.y, radius);
    }

    /**
     * A line and how to calculate its distance to a point.
     */
    private class Line {
        float pointX, pointY;
        float ex, ey;

        /**
         * Create a line going through points A and B. The y-coordinate values are flipped
         * to match the flipped y-axis of pixmaps.
         *
         * @param ax float, x-coordinate of point A
         * @param ay float, y-coordinate of point A
         * @param bx float, x-coordinate of point B
         * @param by float, y-coordinate of point B
         */
        Line(float ax, float ay, float bx, float by) {
            ay = flipY(ay);
            by = flipY(by);
            pointX = ax;
            pointY = ay;
            ex = bx - ax;
            ey = by - ay;
            float normfactor = 1.0f / Vector2.len(ex, ey);
            ex *= normfactor;
            ey *= normfactor;
        }

        /**
         * Determine the distance of a pixel (i,j) from the line. The pixel lies at (i+0.5,j+0.5).
         * Distance is positive if point is inside the polygon (Vertices are given counterclockwise
         * and the polygon is convex).
         *
         * @param i int, column index of pixel
         * @param j int, row index of pixels
         * @return float, signed distance, positive if at the left side of the line looking from point A to point B
         */
        public float distance(int i, int j) {
            float linePosition, d;
            d = ex * (j + 0.5f - pointY) - ey * (i + 0.5f - pointX);
            return -d;    // taking the mirroring into account that results from the y-axis flip
        }
    }

    /**
     * Fill a convex polygon shape. Vertices have to be given in counter-clock sense.
     *
     * @param coordinates float.... of float[] of (x,y) coordinate pairs for the vertices
     */
    public void fillPolygon(float... coordinates) {
        int length = coordinates.length - 2;
        Array<Line> lines = new Array<Line>();
        float xMin = coordinates[length];
        float xMax = xMin;
        float yMin = flipY(coordinates[length + 1]);
        float yMax = yMin;
        for (int i = 0; i < length; i += 2) {
            lines.add(new Line(coordinates[i], coordinates[i + 1], coordinates[i + 2], coordinates[i + 3]));
            xMin = Math.min(xMin, coordinates[i]);
            xMax = Math.max(xMax, coordinates[i]);
            yMin = Math.min(yMin, flipY(coordinates[i + 1]));
            yMax = Math.max(yMax, flipY(coordinates[i + 1]));
        }
        lines.add(new Line(coordinates[length], coordinates[length + 1], coordinates[0], coordinates[1]));
        // taking into account smooth border of with 0.5 and shift of pixel positions
        int iMin = Math.max(0, MathUtils.floor(xMin - 1-smoothLengthHalf));
        int iMax = Math.min(width - 1, MathUtils.ceil(xMax+smoothLengthHalf));
        int jMin = Math.max(0, MathUtils.floor(yMin - 1-smoothLengthHalf));
        int jMax = Math.min(height - 1, MathUtils.ceil(yMax+smoothLengthHalf));
        int i, j, index;
        float d;
        for (j = jMin; j <= jMax; j++) {
            index = j * width+iMin;
            for (i = iMin; i <= iMax; i++) {
                d = 100000f;
                for (Line line : lines) {
                    d = Math.min(d, line.distance(i, j));
                    if (d < -smoothLengthHalf) {
                        break;
                    }
                }
                if (d > smoothLengthHalf) {
                    alpha[index]=(byte) 255;
                }
                else if (d>-smoothLengthHalf){
                    setOpacity(index,d);
                }
                index++;
            }
        }
    }

    /**
     * fill a convex polygon masterShape. Vertices in counter-clock sense
     *
     * @param points Array<Vector2>, the vertices
     */
    public void fillPolygon(Array<Vector2> points) {
        fillPolygon(MathU.toFloats(points));
    }

    /**
     * fill rectangle area (within set limits)
     *
     * @param cornerX
     * @param cornerY
     * @param width    >0
     * @param height>0
     */
    public void fillRect(float cornerX, float cornerY, float width, float height) {
        fillPolygon(cornerX, cornerY, cornerX + width, cornerY, cornerX + width, cornerY + height, cornerX, cornerY + height);
    }

    /**
     * Draw a disc of opaque bits with a smooth border given by a Shape2D Circle.
     *
     * @param circle
     */
    public void fillCircle(Circle circle) {
        fillCircle(circle.x, circle.y, circle.radius);
    }

    /**
     * Fill a convex polygon given by a Shape2D Polygon.
     *
     * @param polygon
     */
    public void fillPolygon(Polygon polygon) {
        fillPolygon(polygon.getVertices());
    }

    /**
     * Fill a rectangle, defined by a Shape2D Rectangle.
     *
     * @param rectangle
     */
    public void fillRect(Rectangle rectangle) {
        fillRect(Math.round(rectangle.x), Math.round(rectangle.y),
                Math.round(rectangle.width), Math.round(rectangle.height));
    }

    /**
     * Fill a Shape2D shape.
     * Only Circle, Rectangle, Polygon, Shape2DCollection and DotsAndLines
     * (subclass of Shape2DCollection) shapes. The other Shape2D shapes have no surface.
     *
     * @param shape Shape2D
     */
    public void fill(Shape2D shape) {
        if (shape instanceof Polygon) {
            fillPolygon((Polygon) shape);
        } else if (shape instanceof Circle) {
            fillCircle((Circle) shape);
        } else if (shape instanceof Rectangle) {
            fillRect((Rectangle) shape);
        } else if (shape instanceof Shape2DCollection) {
            Shape2DCollection shapes = (Shape2DCollection) shape;
            for (Shape2D subShape : shapes.shapes2D) {
                fill(subShape);
            }
        }
    }

    /**
     * Calculate the center of mass of the mask with respect to its lower left corner.
     *
     * @return Vector2, position of the center of mass
     */
    public Vector2 getCenter() {
        int surface = 0;
        int centerX = 0;
        int centerY = 0;
        int a;
        int i, j;
        int index = 0;
        for (j = 0; j < height; j++) {
            for (i = 0; i < width; i++) {
                a = alpha[index];
                index++;
                if (a < 0) a += 256;
                surface += a;
                centerX += a * i;
                centerY += a * j;
            }
        }
        surface = Math.max(surface, 1);
        return new Vector2((float) centerX / (float) surface, (float) centerY / (float) surface);
    }

    /**
     * Combine the mask with another one using "AND" (mathematical min).
     * Changes the mask data.
     *
     * @param mask2 Mask, for combination
     */
    public void and(Mask mask2) {
        for (int index = alpha.length - 1; index >= 0; index--) {
            alpha[index] = (byte) Math.min(toPosInt(alpha[index]), toPosInt(mask2.alpha[index]));
        }
    }

    /**
     * Combine the mask with another mask using "OR" (mathematical max).
     * Changes the mask data.
     *
     * @param mask2 Mask, for combination
     */
    public void or(Mask mask2) {
        for (int index = alpha.length - 1; index >= 0; index--) {
            alpha[index] = (byte) Math.max(toPosInt(alpha[index]), toPosInt(mask2.alpha[index]));
        }
    }

    /**
     * Mask the alpha channel of a pixmap.
     * The pixmap has to have the same dimensions as the mask.
     *
     * @param pixmap Pixmap, the mask sets its alpha channel.
     */
    public void setPixmapAlpha(Pixmap pixmap) {
        ByteBuffer pixels = pixmap.getPixels();
        int length = alpha.length;
        int index = 3;
        for (int i = 0; i < length; i++) {
            pixels.put(index, alpha[i]);
            index += 4;
        }
        pixels.rewind();
    }

    /**
     * Create a pixmap with an alpha channel and of the same dimensions as the mask. 8 bits per channel.
     *
     * @return Pixmap
     */
    public Pixmap createPixmap() {
        return new Pixmap(width, height, Pixmap.Format.RGBA8888);
    }

    /**
     * Copy a piece of an input pixmap. The piece has the size as the mask.
     * The lower left corner of the copied region is given by an offset.
     * This part is masked and returned as a new pixmap.
     *
     * @param input   Pixmap
     * @param offsetX float, x-component of offset
     * @param offsetY float, x-component of offset
     * @return Pixmap
     */
    public Pixmap copyFromPixmap(Pixmap input, int offsetX, int offsetY) {
        Pixmap pixmap = createPixmap();
        pixmap.drawPixmap(input, -offsetX, -offsetY);
        setPixmapAlpha(pixmap);
        return pixmap;
    }

    /**
     * Create a white pixmap with transparency resulting from the mask.
     *
     * @return pixmap
     */
    public Pixmap createWhitePixmap() {
        Pixmap pixmap = createPixmap();
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        setPixmapAlpha(pixmap);
        return pixmap;
    }

    /**
     * Create a white textureRegion with transparency resulting from the mask.
     *
     * @return TextureRegion
     */
    public TextureRegion createWhiteTextureRegion() {
        return TextureU.textureRegionFromPixmap(createWhitePixmap());
    }
}


