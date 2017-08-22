package com.mygdx.game.Images;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.FloatArray;

/**
 * Created by peter on 8/22/17.
 */

public class Shape2DTranslate {

    /**
     * Translate a circle.
     *
     * @param circle Circle to translate.
     * @param dx
     * @param dy
     */
    public void circle(Circle circle,float dx,float dy){
        circle.x+=dx;
        circle.y+=dy;
    }

    /**
     * translate a rectangle.
     *
     * @param rectangle Rectangle to translate.
     */
    public void rectangle(Rectangle rectangle,float dx,float dy){
        rectangle.x+=dx;
        rectangle.y+=dy;
    }

    /**
     * translate vertices in a float[] array
     *
     * @param coordinates
     * @param dx
     * @param dy
     */
    static public void vertices(float[] coordinates,float dx,float dy){
        for (int i=coordinates.length-2;i>=0;i-=2){
            coordinates[i]+=dx;
            coordinates[i+1]+=dy;
        }
    }

    /**
     * translate vertices in a floatArray
     *
     * @param coordinates
     * @param dx
     * @param dy
     */
    static public void vertices(FloatArray coordinates, float dx, float dy){
        for (int i=coordinates.size-2;i>=0;i-=2){
            coordinates.set(i,coordinates.get(i)+dx);
            coordinates.set(i+1,coordinates.get(i+1)+dy);
        }
    }

    /**
     * translate local vertices of polygon
     *
     * @param polygon Polygon object to translate
     */
    static public void polygon(Polygon polygon,float dx,float dy){
        float[] coordinates=polygon.getVertices();
        vertices(coordinates,dx,dy);
        polygon.setVertices(coordinates);
    }

    /**
     * Draws the Polyline with its transformed world vertices (scaled, translated and rotated).
     *
     * @param polyline Polyline object to draw as lines.
     */
    public void polyline(Polyline polyline){


    }

    /**
     * Draw the points of a polypoint object.
     *
     * @param polypoint Polypoint, to draw as a collection of points
     */
    public void polypoint(Polypoint polypoint){


    }

    /**
     * Draw an edge as a line with ghosts as points, if they exist.
     *
     * @param edge Edge, to draw as a single line with ghost points.
     */
    public void edge(Edge edge){
       // line(edge.aX,edge.aY,edge.bX,edge.bY);
        if (edge.ghostAExists){
      //      point(edge.ghostAX,edge.ghostAY);
        }
        if (edge.ghostBExists){
       //     point(edge.ghostBX,edge.ghostBY);
        }
    }

    /**
     * Draw an Chain as a polyline with ghosts as points, if they exist.
     * Close the loop if the Chain is a loop.
     *
     * @param chain Chain to draw as a polyline with ghost points.
     */
    public void chain(Chain chain){
        int length=chain.coordinates.length;
       // setColor(lineColor);
        for (int i=0;i<length-3;i+=2){
         //   line(chain.coordinates[i],chain.coordinates[i+1],
                  //  chain.coordinates[i+2],chain.coordinates[i+3]);
        }
        if (chain.isLoop){
          //  line(chain.coordinates[0],chain.coordinates[1],
                 //   chain.coordinates[length-2],chain.coordinates[length-1]);
        }
        if (chain.ghostAExists){
         //   point(chain.ghostAX,chain.ghostAY);
        }
        if (chain.ghostBExists){
         //   point(chain.ghostBX,chain.ghostBY);
        }
    }

    /**
     *     draw a shape2DCollection (including dots and lines)
     *
     * @param collection
     */
    public void shape2DCollection(Shape2DCollection collection){
        for (Shape2D subShape:collection.items){
          //  shape2D(subShape);
        }
    }

    /**
     * Draw any Shape2D shape, including collections.
     *
     * @param shape Shape2D to draw
     */
    /*
    public void shape2D(Shape2D shape){
        if (shape==null){}
        else if (shape instanceof Polygon){
            polygon((Polygon)shape);
        }
        else if (shape instanceof Circle){
            circle((Circle) shape);
        }
        else if (shape instanceof Rectangle){
            rectangle((Rectangle) shape);
        }
        else if (shape instanceof Polypoint){
            polypoint((Polypoint)shape);
        }
        else if (shape instanceof Polyline){
            polyline((Polyline) shape);
        }
        else if (shape instanceof Edge){
            edge((Edge) shape);
        }
        else if (shape instanceof Chain){
            chain((Chain) shape);
        }
        else if (shape instanceof Shape2DCollection){                 // includes subclass DotsAndLines
            shape2DCollection((Shape2DCollection) shape);

        }
        else {
            Gdx.app.log(" ******************** draw","unknown shape "+shape.getClass());
        }
    }
    */
}
