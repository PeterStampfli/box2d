package com.mygdx.game.Images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utilities.Basic;

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

    /**
     * Create a transparent mask of given width and height.
     *
     * @param width int, width of the pixmap
     * @param height int, height of the pixmap
     */
    public Mask(int width, int height){
        this.width=width;
        this.height=height;
        alpha=new byte[width*height];
        clear();
    }

    /**
     * Flip the y-axis to compensate for the inversion of pixmaps. y=0 maps to y=height and inversely.
     *
     * @param y float, an y-coordinate
     * @return float, the y-coordinate is flipped
     */
    private float flipY(float y){
        return height-y;
    }

    /**
     * Transform a byte into a positive integer between 0 and 255.
     * Note that a byte is cast as a signed 8 bit integer.
     *
     * @param b byte
     * @return int, between 0 and 255
     */
    private int toPosInt(byte b) {
        int iB=b;
        if (iB<0) iB+=256;
        return iB;
    }

    /**
     * Make the mask transparent.
     *
     * @return this, for chaining
     */
    public Mask clear(){
        for (int index=alpha.length-1;index>=0;index--) {
            alpha[index]=0;
        }
        return this;
    }

    /**
     * Make transparent border bytes. (For smoothing shapes)
     *
     * @return this, for chaining
     */
    public Mask transparentBorder(){
        int index;
        int length=alpha.length;
        for (index=0;index<width;index++){
            alpha[index]=0;
            alpha[length-1-index]=0;
        }
        for (index=0;index<length;index+=width){
            alpha[index]=0;
            alpha[index+width-1]=0;
        }
        return this;
    }

    /**
     * Invert the mask bytes. To draw transparent shapes in opaque regions.
     *
     * @return this, for chaining
     */
    public Mask invert(){
        for (int index=alpha.length-1;index>=0;index--) {
            alpha[index] = (byte) (255-toPosInt(alpha[index]));
        }
        return this;
    }


    /**
     * For drawing, the mask will always be set to the larger value of both the existing mask byte and the
     * new opacity value.
     *
     * @param b   byte, present mask byte
     * @param f   float, opacity to draw, between 0 and 1, 1 and larger give 255
     * @return byte, maximum value
     */
    public byte maxByteFloat(byte b,float f){
        return (byte) MathUtils.clamp(Math.floor(f*256),toPosInt(b),255);
    }

    /**
     * Draw a ring of opaque bits with a smooth border given its center, outer radius and thickness.
     * Draws a disc if the thickness is negative.
     *
     * @param centerX float, x-coordinate of the center
     * @param centerY float, x-coordinate of the center
     * @param thickness float, thickness of the ring, negative values give a simple disc
     * @param outerRadius float, outer radius of the ring
     */
    public void drawRing(float centerX, float centerY, float outerRadius, float thickness){
        float dx,dy2,dx2Plusdy2;
        float innerRadius;
        float d=0.5f;
        float outerRadiusSqPlus=(outerRadius+d)*(outerRadius+d);
        float outerRadiusSqMinus=(outerRadius-d)*(outerRadius-d);
        float outerDenom=1f/ (outerRadiusSqPlus - outerRadiusSqMinus);
        float innerRadiusSqMinus,innerRadiusSqPlus,innerDenom;
        if (thickness>=0) {
            innerRadius=outerRadius-thickness;
            innerRadiusSqPlus = (innerRadius + d) * (innerRadius + d);
            innerRadiusSqMinus = (innerRadius - d) * (innerRadius - d);
            innerDenom = 1f / (innerRadiusSqPlus - innerRadiusSqMinus);
        }
        else {
            innerRadiusSqMinus=-100;
            innerRadiusSqPlus=-90;
            innerDenom=1;
        }
        centerY=flipY(centerY);
        int iMax,iMin,jMax,jMin;
        iMax=Math.min(width-1,MathUtils.ceil(centerX+outerRadius));
        iMin=Math.max(0,MathUtils.floor(centerX-outerRadius-1));
        jMax=Math.min(height-1,MathUtils.ceil(centerY+outerRadius));
        jMin=Math.max(0,MathUtils.floor(centerY-outerRadius-1));
        int i,j,jWidth;
        for (j=jMin;j<=jMax;j++){
            jWidth=j*width;
            dy2=j+0.5f-centerY;
            dy2*=dy2;
            for (i=iMin;i<=iMax;i++){
                dx=i+0.5f-centerX;
                dx2Plusdy2=dy2+dx*dx;
                if (dx2Plusdy2>innerRadiusSqMinus) {
                    if (dx2Plusdy2 < innerRadiusSqPlus) {
                        d = (dx2Plusdy2 - innerRadiusSqMinus) *innerDenom;
                        alpha[i + jWidth] = maxByteFloat(alpha[i + jWidth], d);
                    }
                    else if (dx2Plusdy2 < outerRadiusSqMinus) {
                        alpha[i + jWidth] = (byte) 255;
                    } else if (dx2Plusdy2 < outerRadiusSqPlus) {
                        d = (outerRadiusSqPlus - dx2Plusdy2) *outerDenom;
                        alpha[i + jWidth] = maxByteFloat(alpha[i + jWidth], d);
                    }
                }
            }
        }
    }

    /**
     * Draw a disc of opaque bits with a smooth border given its center and radius.
     *
     * @param centerX float, x-coordinate of the center
     * @param centerY float, x-coordinate of the center
     * @param radius float, radius
     */
    public void drawCircle(float centerX, float centerY, float radius){
        drawRing(centerX,centerY,radius,-10);
    }

    /**
     * Draw a disc of opaque bits with a smooth border given its center and radius.
     *
     * @param center Vector2, center
     * @param radius float, radius
     */
    public void drawCircle(Vector2 center, float radius){
        drawCircle(center.x,center.y,radius);
    }


    /**
     * a line that is part of the border of a convex polygon masterShape
     * going around counterclockwise
     */
    private class Line{
        float pointX,pointY;
        float ex,ey;

        /**
         * create a line with flipped y-values to match the pixmap
         * @param ax
         * @param ay
         * @param bx
         * @param by
         */
        Line(float ax,float ay,float bx,float by) {
            ay=flipY(ay);
            by=flipY(by);
            pointX = ax;
            pointY = ay;
            ex=bx-ax;
            ey=by-ay;
            float normfactor=1.0f/Vector2.len(ex,ey);
            ex*=normfactor;
            ey*=normfactor;
        }

        /**
         * determine the distance of a pixel (i,j) from the line
         * @param i
         * @param j
         * @return
         */
        public float distance(int i,int j){
            float linePosition,d;
            d=ex*(j+0.5f-pointY)-ey*(i+0.5f-pointX);
            return -d+0.5f;    // taking mirroring into acount
        }
    }

    /**
     * draw a convex polygon masterShape. Vertices in counter-clock sense
     * draws the outline with a given thickness inside the border lines
     * (masterShape has to be closed)
     * note that here thickness comes first, it is mainly for diagnstics
     * @param thickness
     * @param coordinates
     */
    public void drawPolygon(float thickness,float... coordinates){
        int length=coordinates.length-2;
        Array<Line> lines=new Array<Line>();
        float xMin=coordinates[length];
        float xMax=xMin;
        float yMin=flipY(coordinates[length+1]);
        float yMax=yMin;
        for (int i=0;i<length;i+=2) {
            lines.add(new Line(coordinates[i],coordinates[i+1],coordinates[i+2],coordinates[i+3]));
            xMin=Math.min(xMin,coordinates[i]);
            xMax=Math.max(xMax,coordinates[i]);
            yMin=Math.min(yMin,flipY(coordinates[i+1]));
            yMax=Math.max(yMax,flipY(coordinates[i+1]));
        }
        lines.add(new Line(coordinates[length],coordinates[length+1],coordinates[0],coordinates[1]));
        // taking into account smooth border of with 0.5 and shift of pixel positions
        int iMin=Math.max(0,MathUtils.floor(xMin-1));
        int iMax=Math.min(width-1,MathUtils.ceil(xMax));
        int jMin=Math.max(0,MathUtils.floor(yMin-1));
        int jMax=Math.min(height-1,MathUtils.ceil(yMax));
        int i,j,jWidth;
        float d;
        length=lines.size;
        for (j=jMin;j<=jMax;j++){
            jWidth=j*width;
            for (i=iMin;i<=iMax;i++){
                d=100000f;
                for (Line line:lines){
                    d=Math.min(d,line.distance(i,j));
                    if (d<0) {
                        break;
                    }
                }
                if (d>0){
                    if (d>thickness-0.5f){
                        d=thickness+0.5f-d;
                    }
                    alpha[i+jWidth]=maxByteFloat(alpha[i+jWidth],d);
                }
            }
        }
    }

    /**
     * fill a convex polygon masterShape. Vertices in counter-clock sense
     * @param coordinates
     */
    public void fillPolygon(float... coordinates){
        drawPolygon(100000,coordinates);
    }

    /**
     * fill a convex polygon masterShape. Vertices in counter-clock sense
     * @param points
     */
    public void fillPolygon(Array<Vector2> points){
        fillPolygon(Basic.toFloats(points));
    }

    /**
     * fill rectangle area (within set limits)
     * @param cornerX
     * @param cornerY
     * @param width >0
     * @param height>0
     */
    public void fillRect(float cornerX,float cornerY,float width,float height){
        fillPolygon(cornerX,cornerY,cornerX+width,cornerY,cornerX+width,cornerY+height,cornerX,cornerY+height);
    }

    /**
     * fill a circle with opaque bits, smooth border
     * the center is a continuous coordinate,
     * (0,0) is at the lower left corner of the lowest leftest pixel
     * center of pixels are integers plus one half
     * @param circle
     */
    public void drawCircle(Circle circle){
        drawCircle(circle.x,circle.y,circle.radius);
    }

    /**
     * fill a convex polygon masterShape. Vertices in counter-clock sense
     * @param polygon
     */
    public void fillPolygon(Polygon polygon){
        fillPolygon(polygon.getVertices());
    }

    /**
     * fill rectangle area (within limits), making it opaque
     * @param rectangle
     */
    public void fillRect(Rectangle rectangle){
        fillRect(Math.round(rectangle.x),Math.round(rectangle.y),
                Math.round(rectangle.width),Math.round(rectangle.height));
    }

    /**
     * fill a Shape2D masterShape on the mask
     * including Shape2DCollection collections
     * @param shape
     */
    public void fill(Shape2D shape){
        if (shape instanceof Polygon){
            fillPolygon((Polygon)shape);
        }
        else if (shape instanceof Circle){
            drawCircle((Circle) shape);
        }
        else if (shape instanceof Rectangle){
            fillRect((Rectangle) shape);
        }
        else if (shape instanceof Shape2DCollection){
            Shape2DCollection shapes=(Shape2DCollection) shape;
            for (Shape2D subShape:shapes.shapes2D){
                fill(subShape);
            }
        }
        else {
            Gdx.app.log(" ******************** mask","unknown masterShape "+shape.getClass());
        }
    }

    /**
     * calculate the center of mass of the mask
     * with respect to lower left corner
     * @return
     */
    public Vector2 getCenter(){
        int surface=0;
        int centerX=0;
        int centerY=0;
        int a;
        int i,j;
        int index=0;
        for (j=0;j<height;j++){
            for (i  = 0; i < width; i++) {
                a = alpha[index];
                index++;
                if (a<0) a+=256;
                surface += a;
                centerX += a * i;
                centerY += a * j;
            }
        }
        surface=Math.max(surface,1);
        return new Vector2((float)centerX/(float)surface,(float)centerY/(float)surface);
    }

    /**
     * combine the mask with another one using "AND" (mathematical min)
     * changes the mask data
     * @return
     */
    public Mask and(Mask mask2){
        for (int index=alpha.length-1;index>=0;index--) {
            alpha[index]=(byte)Math.min(toPosInt(alpha[index]),toPosInt(mask2.alpha[index]));
        }
        return this;
    }

    /**
     * combine the mask with another one using "OR" (mathematical max)
     * changes the mask data
     * @return
     */
    public Mask or(Mask mask2){
        for (int index=alpha.length-1;index>=0;index--) {
            alpha[index]=(byte)Math.max(toPosInt(alpha[index]),toPosInt(mask2.alpha[index]));
        }
        return this;
    }


    /**
     * set the alpha channel of a pixmap
     * the pixmap has to have the same dimensions as the mask
     * @param pixmap
     */
    public void setPixmapAlpha(Pixmap pixmap){
        ByteBuffer pixels=pixmap.getPixels();
        int length=alpha.length;
        int index=3;
        for (int i=0;i<length;i++){
            pixels.put(index,alpha[i]);
            index+=4;
        }
        pixels.rewind();
    }

    /**
     * Create a pixmap with alpha channel of same dimensions as the mask
     * @return
     */
    public Pixmap createPixmap(){
        return new Pixmap(width,height, Pixmap.Format.RGBA8888);
    }

    /**
     * cut a piece of mask dimensions from a pixmap
     * the lower left corner is offset by (offsetX,offsetY)
     * This part is masked and returned as a new pixmap
     * @param input
     * @param offsetX
     * @param offsetY
     * @return
     */
    public Pixmap cutFromPixmap(Pixmap input,int offsetX,int offsetY){
        Pixmap result=createPixmap();
        result.drawPixmap(input,-offsetX,-offsetY);
        setPixmapAlpha(result);
        return result;
    }

    /**
     * for diagnostics create a white texture
     * cut out by the mask
     * @return
     */
    public TextureRegion createTransparentWhiteTextureRegion(){
        Pixmap pixmap=createPixmap();
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        setPixmapAlpha(pixmap);
        Texture result=new Texture(pixmap);
        Basic.linearInterpolation(result);
        pixmap.dispose();
        return new TextureRegion(result);
    }

    /**
     * for diagnostics create a black and white texture
     * white is 255, black is 0
     * @return
     */
    public TextureRegion createBlackWhiteTextureRegion(){
        Pixmap pixmap=createPixmap();
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        ByteBuffer pixels=pixmap.getPixels();
        int length=alpha.length;
        int index=0;
        for (int i=0;i<length;i++){
            pixels.put(index++,alpha[i]);
            pixels.put(index++,alpha[i]);
            pixels.put(index++,alpha[i]);
            pixels.put(index++,(byte) 255);
        }
        pixels.rewind();
        Texture result=new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegion(result);
    }
}


