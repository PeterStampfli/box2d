package com.mygdx.game.Images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.*;

/**
 * Created by peter on 4/4/17.
 * Static methods to use Shape2D shapes for masks, including collections of shapes
 *
 * a bridge between masks and shapes
 */

public class MaskOfShape2D {

    /**
     * fill a circle with opaque bits, smooth border
     * the center is a continuous coordinate,
     * (0,0) is at the lower left corner of the lowest leftest pixel
     * center of pixels are integers plus one half
     * @param circle
     */
    static public void fillCircle(Circle circle,Mask mask){
        mask.fillCircle(circle.x,circle.y,circle.radius);
    }

    /**
     * fill a convex polygon shape. Vertices in counter-clock sense
     * @param polygon
     */
    static public void fillPolygon(Polygon polygon,Mask mask){
        mask.fillPolygon(polygon.getVertices());
    }

    /**
     * fill rectangle area (within limits), making it opaque
     * @param rectangle
     */
    static public void fillRect(Rectangle rectangle,Mask mask){
        mask.fillRect(Math.round(rectangle.x),Math.round(rectangle.y),
                Math.round(rectangle.width),Math.round(rectangle.height));
    }

    /**
     * draw chain on a mask
     * @param mask
     */
    static public void drawChain(Chain chain,Mask mask){
        mask.drawChain(chain.thickness,chain.coordinates.toArray());
    }

    /**
     * fill a Shape2D shape on the mask
     * including Shapes2D collections
     * @param shape
     * @param mask
     */
    static public void fill(Shape2D shape,Mask mask){
        if (shape instanceof Polygon){
            fillPolygon((Polygon)shape,mask);
        }
        else if (shape instanceof Circle){
            fillCircle((Circle) shape,mask);
        }
        else if (shape instanceof Rectangle){
            fillRect((Rectangle) shape,mask);
        }
        else if (shape instanceof Chain){
            drawChain((Chain)shape,mask);
        }
        else if (shape instanceof Shapes2D){
            Shapes2D shapes=(Shapes2D) shape;
            for (Shape2D subShape:shapes.shapes2D){
                fill(subShape,mask);
            }
        }
        else {
            Gdx.app.log(" ******************** mask","unknown shape "+shape.getClass());
        }
    }

}
