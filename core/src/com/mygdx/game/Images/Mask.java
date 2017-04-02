package com.mygdx.game.Images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utilities.Basic;

import java.nio.ByteBuffer;

/**
 * Created by peter on 2/26/17.
 * a rectangular region of bytes to use as a mask for pixmaps
 * stored in a single byte array
 * the y-axis is flipped to compensate for the inverted y-axis in pixmaps
 */


public class Mask {
    public byte[] alpha;
    public int width;
    public int height;
    private int iMin, iMax, jMin, jMax;   // outside these limits the mask bytes remain unchanged

    /**
     * create a mask of given width and height,
     * sets all bytes=0 (means transparent),
     * sets limits, such that there will be a transparent border
     * @param width
     * @param height
     */
    public Mask(int width, int height){
        this.width=width;
        this.height=height;
        alpha=new byte[width*height];
        clear();
        setLimits();                                            // transparent border
    }

    /**
     * flip the y-axis to compensate for the inversion of pixmaps
     * images will appear upright. Higher y-values are higher up and rotation sense is unchanged
     * @param y
     * @return
     */
    private int flipY(int y){
        return height-1-y;
    }

    /**
     * flip the y-axis to compensate for the inversion of pixmaps
     * images will appear upright. Higher y-values are higher up and rotation sense is unchanged
     * @param y
     * @return
     */
    private float flipY(float y){
        return height-1-y;
    }

    /**
     * flip the y-axis to compensate for the inversion of pixmaps
     * images will appear upright. Higher y-values are higher up and rotation sense is unchanged
     * @param point
     * @return
     */
    private void flipY(Vector2 point){
        point.y=height-1-point.y;
    }

    /**
     * clear the mask
     * @return
     */
    public Mask clear(){
        int length=alpha.length;
        for (int i=0;i<length;i++){
            alpha[i]=0;
        }
        return this;
    }

    /**
     * set the limits of the active area, with inverted y-axis
     * @param iMin
     * @param iMax
     * @param jMin
     * @param jMax
     */
    public void setLimits(int iMin,int iMax,int jMin,int jMax){
        jMin=flipY(jMin);
        jMax=flipY(jMax);
        this.iMin = MathUtils.clamp(Math.min(iMin,iMax),0,width-1);
        this.iMax = MathUtils.clamp(Math.max(iMin,iMax),0,width-1);
        this.jMin = MathUtils.clamp(Math.min(jMin,jMax),0,height-1);
        this.jMax = MathUtils.clamp(Math.max(jMin,jMax),0,height-1);
    }

    /**
     * set limits, leaving an inactive (transparent) border
     */
    public void setLimits(){
        setLimits(Math.min(1,width-2),Math.max(width-2,1),Math.min(1,height-2),Math.max(1,height-2));
    }

    /**
     * the full mask is active
     */
    public void noLimits(){
        setLimits(0,width-1,0,height-1);
    }

    /**
     * make the border bytes transparent
     */
    public void transparentBorder(){
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
    }

    /**
     * invert the mask bytes within the set limits
     */
    public void invertWithinLimits(){
        int i,j,jWidth;
        for (j=jMin;j<=jMax;j++) {
            jWidth = j * width;
            for (i = iMin; i <= iMax; i++) {
                alpha[i + jWidth] = (byte) (255-alpha[i + jWidth]);
            }
        }
    }

    /**
     * fill rectangle area (within set limits)
     * @param ic
     * @param jc
     * @param rectWidth
     * @param rectHeight
     * @param value
     */
    public void fillRect(int ic,int jc,int rectWidth,int rectHeight,int value){
        int iMx=Math.min(this.iMax,ic+rectWidth-1);
        int iMn=Math.max(this.iMin,ic);
        int jMx=Math.min(this.jMax,flipY(jc));
        int jMn=Math.max(this.jMin,flipY(jc)-rectHeight+1);
        int i,j,jWidth;
        for (j=jMn;j<=jMx;j++) {
            jWidth = j * width;
            for (i = iMn; i <= iMx; i++) {
                alpha[i + jWidth] = (byte) value;
            }
        }
    }

    /**
     * fill rectangle area (within limits), making it opaque
     * @param ic
     * @param jc
     * @param rectWidth
     * @param rectHeight
     */
    public void fillRect(int ic,int jc,int rectWidth,int rectHeight) {
        fillRect(ic, jc, rectWidth, rectHeight,255);
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
     * the mask will always be set to the maximum value in case of superposition
     * @param b   present mask value
     * @param f   opacity, from 0 to 1
     * @return
     */
    public byte maxByteFloat(byte b,float f){
        int iB=b;
        if (iB<0) iB+=256;
        return (byte) Math.max(iB,MathUtils.clamp(Math.floor(f*256),0,255));
    }

    /**
     * fill a circle with opaque bits, smooth border
     * the center is a continuous coordinate,
     * (0,0) is at the lower left corner of the lowest leftest pixel
     * center of pixels are integers plus one half
     * @param centerX
     * @param centerY
     * @param radius
     */

    //   what is the correct position of a pixel (i,j) ????
    //========================================================================
    
    public void fillCircle(float centerX, float centerY, float radius){
        centerY=flipY(centerY);
        int iMax,iMin,jMax,jMin;
        iMax=Math.min(this.iMax,MathUtils.ceil(centerX+radius));
        iMin=Math.max(this.iMin,MathUtils.floor(centerX-radius));
        jMax=Math.min(this.jMax,MathUtils.ceil(centerY+radius));
        jMin=Math.max(this.jMin,MathUtils.floor(centerY-radius));
        int i,j,jWidth;
        float dx,dx2,dy,dy2,dx2Plusdy2;
        float radiusSq=radius*radius;
        float d=0.5f;
        float radiusSqPlus=(radius+d)*(radius+d);
        float radiusSqMinus=(radius-d)*(radius-d);
        for (j=jMin;j<=jMax;j++){
            jWidth=j*width;
            dy=Math.abs(j-centerY);
            dy2=dy*dy;
            for (i=iMin;i<=iMax;i++){
                dx=Math.abs(i-centerX);
                dx2=dx*dx;
                dx2Plusdy2=dy2+dx2;
                if (dx2Plusdy2<radiusSqMinus){
                    alpha[i+jWidth]=(byte) 255;
                }
                else if (dx2Plusdy2<radiusSqPlus){
                    if (dx>dy){
                        d=(float) Math.sqrt(radiusSq-dy2)-dx+0.5f;
                    }
                    else {
                        d=(float) Math.sqrt(radiusSq-dx2)-dy+0.5f;
                    }
                    if (d>0) {
                        alpha[i+jWidth]=maxByteFloat(alpha[i+jWidth],d);
                    }
                }
            }
        }
    }

    /**
     * fill a circle with opaque bits, smooth border
     * the center is a continuous coordinate,
     * (0,0) is at the lower left corner of the lowest leftest pixel
     * center of pixels are integers plus one half
     * @param center
     * @param radius
     */
    public void fillCircle(Vector2 center, float radius){
        fillCircle(center.x,center.y,radius);
    }

    /**
     * fill a circle with opaque bits, smooth border
     * the center is a continuous coordinate,
     * (0,0) is at the lower left corner of the lowest leftest pixel
     * center of pixels are integers plus one half
     * @param circle
     */
    public void fillCircle(Circle circle){
        fillCircle(circle.x,circle.y,circle.radius);
    }

    /**
     * a line that is part of the border of a convex polygon shape
     * going around counterclockwise
     */
    private class Line{
        float pointX,pointY;
        float slope;
        boolean isHorizontal;
        boolean increasing;

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
            isHorizontal = (Math.abs(bx - ax) > Math.abs(by - ay));
            if (isHorizontal){
                increasing = (bx > ax);
                slope = (by - ay) / (bx - ax);
            }
            else {
                increasing=(by > ay);
                slope=(bx-ax)/(by-ay);
            }
        }

        /**
         * determine the distance of a pixel (i,j) from the line
         * @param i
         * @param j
         * @return
         */
        public float distance(int i,int j){
            float linePosition,d;
            if (isHorizontal){
                linePosition=pointY+slope*(i-pointX);
                d=increasing?linePosition-j:j-linePosition;
            }
            else {
                linePosition=pointX+slope*(j-pointY);
                d=increasing?i-linePosition:linePosition-i;
            }
            return d+0.5f;
        }
    }

    public void fillPolygon(float... coordinates){
        int length=coordinates.length-2;
        Array<Line> lines=new Array<Line>();
        for (int i=0;i<length;i+=2) {
            lines.add(new Line(coordinates[i],coordinates[i+1],coordinates[i+2],coordinates[i+3]));
        }
        lines.add(new Line(coordinates[length],coordinates[length+1],coordinates[0],coordinates[1]));
        int i,j,jWidth;
        float d;
        length=lines.size;
        for (j=jMin;j<=jMax;j++){
            jWidth=j*width;
            for (i=iMin;i<=iMax;i++){
                d=1f;
                for (Line line:lines){
                    d=Math.min(d,line.distance(i,j));
                    if (d<0) {
                        break;
                    }
                }
                if (d>0) {
                    alpha[i+jWidth]=maxByteFloat(alpha[i+jWidth],d);
                }
            }
        }

    }

    public void fillPolygon(Array<Vector2> points){
        fillPolygon(Basic.toFloats(points));
    }

    public void fillPolygon(Polygon polygon){
        fillPolygon(polygon.getVertices());
    }

    public void fill(Shape2D shape){
        if (shape instanceof Polygon){
            fillPolygon((Polygon)shape);
        }
        else if (shape.getClass()==Circle.class){
            fillCircle((Circle) shape);
        }
        else if(shape.getClass()==Rectangle.class){
            fillRect((Rectangle) shape);
        }
        else if (shape.getClass()==Shapes2D.class){
            Shapes2D shapes=(Shapes2D) shape;
            for (Shape2D subShape:shapes.shapes2D){
                fill(subShape);
            }
        }
        else {
            Gdx.app.log(" ******************** mask","unknown shape "+shape.getClass());
        }
    }

    public void fillLine(float x1,float y1,float x2,float y2,float halfWidth){
        float length=(float) Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        float ex=(x2-x1)/length*halfWidth;
        float ey=(y2-y1)/length*halfWidth;
        fillPolygon(x1+ey,y1-ex,x2+ey,y2-ex,x2-ey,y2+ex,x1-ey,y1+ex);
    }

    public void fillLine(Vector2 a,Vector2 b,float halfWidth){
        fillLine(a.x,a.y,b.x,b.y,halfWidth);
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
     * for diagnostics create a texture of given color
     * cut out by the mask
     * @return
     */
    public Texture createTransparentWhiteTexture(){
        Pixmap pixmap=createPixmap();
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        setPixmapAlpha(pixmap);
        Texture result=new Texture(pixmap);
        Basic.linearInterpolation(result);
        pixmap.dispose();
        return result;
    }

    /**
     * for diagnostics create a black and white texture
     * @return
     */
    public Texture createBlackWhiteTexture(){
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
        Basic.linearInterpolation(result);
        pixmap.dispose();
        return result;
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


}


