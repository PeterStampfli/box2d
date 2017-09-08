package com.mygdx.game.Images;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.FloatArray;

/**
 * Translate shapes and shape collections
 */

public class Shape2DTranslate {

    /**
     * Translate a circle.
     *
     * @param circle Circle to translate.
     * @param dx float, translate x-coordinate
     * @param dy float, translate y-coordinate
     */
    static public void circle(Circle circle,float dx,float dy){
        circle.x+=dx;
        circle.y+=dy;
    }

    /**
     * translate a rectangle.
     *
     * @param rectangle Rectangle to translate.
     * @param dx float, translate x-coordinate
     * @param dy float, translate y-coordinate
     */
    static public void rectangle(Rectangle rectangle,float dx,float dy){
        rectangle.x+=dx;
        rectangle.y+=dy;
    }

    /**
     * translate vertices in a float[] array
     *
     * @param coordinates float[], pairs of coordinates to translate
     * @param dx float, translate x-coordinates
     * @param dy float, translate y-coordinates
     */
    static public float[] translate(float[] coordinates, float dx, float dy){
        for (int i=coordinates.length-2;i>=0;i-=2){
            coordinates[i]+=dx;
            coordinates[i+1]+=dy;
        }
        return coordinates;
    }

    /**
     * translate vertices in a floatArray
     *
     * @param coordinates FloatArray, pairs of coordinates to translate
     * @param dx float, translate x-coordinate
     * @param dy float, translate y-coordinate
     */
    static public FloatArray translate(FloatArray coordinates, float dx, float dy){
        for (int i=coordinates.size-2;i>=0;i-=2){
            coordinates.set(i,coordinates.get(i)+dx);
            coordinates.set(i+1,coordinates.get(i+1)+dy);
        }
        return coordinates;
    }

    /**
     * translate local vertices of polygon
     *
     * @param polygon Polygon object to translate
     * @param dx float, translate x-coordinate
     * @param dy float, translate y-coordinate
     */
    static public void polygon(Polygon polygon,float dx,float dy){
        polygon.setVertices(translate(polygon.getVertices(),dx,dy));
    }

    /**
     * translate local vertices of polyline
     *
     * @param polyline Polygon object to translate
     * @param dx float, translate x-coordinate
     * @param dy float, translate y-coordinate
     */
    static public void polyline(Polyline polyline,float dx,float dy){
        polyline.setVertices(translate(polyline.getVertices(),dx,dy));
    }

    /**
     * translate borderPoints object.
     *
     * @param polypoint Polypoint, to translate
     * @param dx float, translate x-coordinate
     * @param dy float, translate y-coordinate
     */
    static public void polypoint(Polypoint polypoint,float dx,float dy){
        translate(polypoint.coordinates,dx,dy);
    }

    /**
     * Translate edge, including ghosts.
     *
     * @param edge Edge
     * @param dx float, translate x-coordinate
     * @param dy float, translate y-coordinate
     */
    static public void edge(Edge edge,float dx,float dy){
        edge.aX+=dx;
        edge.aY+=dy;
        edge.bX+=dx;
        edge.bY+=dy;
        edge.ghostAY+=dx;
        edge.ghostAY+=dy;
        edge.ghostBX+=dx;
        edge.ghostBY+=dy;
    }

    /**
     * translate a borderShape, with ghosts
     *
     * @param chain Chain
     * @param dx float, translate x-coordinate
     * @param dy float, translate y-coordinate
     */
    static public void chain(Chain chain,float dx,float dy){
        translate(chain.coordinates,dx,dy);
        chain.ghostAY+=dx;
        chain.ghostAY+=dy;
        chain.ghostBX+=dx;
        chain.ghostBY+=dy;
    }

    /**
     *   translate a shape2DCollection (includes dots and lines as subclass)
     *
     * @param collection Shape2DCollection to translate
     * @param dx float, translate x-coordinate
     * @param dy float, translate y-coordinate
     */
    static public void collection(Shape2DCollection collection, float dx, float dy){
        for (Shape2D subShape:collection.items){
            shape(subShape,dx,dy);
        }
    }

    /**
     * Translate any Shape2D shape, including collections.
     *
     * @param shape Shape2D to translate
     * @param dx float, translate x-coordinate
     * @param dy float, translate y-coordinate
     */

    static public void shape(Shape2D shape, float dx, float dy){
        if (shape!=null) {
            if (shape instanceof Polygon) {
                polygon((Polygon) shape, dx, dy);
            } else if (shape instanceof Circle) {
                circle((Circle) shape, dx, dy);
            } else if (shape instanceof Rectangle) {
                rectangle((Rectangle) shape, dx, dy);
            } else if (shape instanceof Polypoint) {
                polypoint((Polypoint) shape, dx, dy);
            } else if (shape instanceof Polyline) {
                polyline((Polyline) shape, dx, dy);
            } else if (shape instanceof Edge) {
                edge((Edge) shape, dx, dy);
            } else if (shape instanceof Chain) {
                chain((Chain) shape, dx, dy);
            } else if (shape instanceof Shape2DCollection) {                 // includes subclass DotsAndLines
                collection((Shape2DCollection) shape, dx, dy);
            } else {
                throw new RuntimeException("unknown shape " + shape.getClass());
            }
        }
    }

    /**
     * adjust the shape to have its lowest x- and y-coordinates at given values
     *
     * @param shape Shape2D, its position will be adjusted
     * @param left float, smallest x-coordinate of shape
     * @param bottom float, smallest y-coordinate of shape
     */
    static public void adjustLeftBottom(Shape2D shape,float left,float bottom){
        shape(shape,left-Shape2DLimits.minXShape(shape),bottom-Shape2DLimits.minYShape(shape));
    }
}
