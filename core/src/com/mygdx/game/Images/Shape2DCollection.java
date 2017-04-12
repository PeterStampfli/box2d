package com.mygdx.game.Images;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utilities.Basic;

/**
 * Created by peter on 3/29/17.
 */
//  collect and create shapes to make a new shape

public class Shape2DCollection implements Shape2D {
    public Array<Shape2D> shapes2D;

    /**
     * on creation make the array
     */
    public Shape2DCollection(){
        shapes2D=new Array<Shape2D>();
    }

    /**
     * check if the collection of shapes contains a given point
     * @param x
     * @param y
     * @return
     */
    @Override
    public boolean contains(float x, float y){
        for (Shape2D shape2D: shapes2D){
            if (shape2D.contains(x, y)){
                return true;
            }
        }
        return false;
    }

    /**
     * check if the collection of shapes contains a given point
     * @param point
     * @return
     */
    @Override
    public boolean contains(Vector2 point) {
        return contains(point.x,point.y);
    }

    /**
     * add one shape to the collection (if not null)
     * returns the collection for chaining
     * @param shape2D
     * @return this
     */
    public Shape2DCollection add(Shape2D shape2D){
        if (shape2D!=null){
            this.shapes2D.add(shape2D);
        }
        return this;
    }

    /**
     * add several shapes to the collection
     * returns the collection for chaining
     * @param shapes2D
     * @return this
     */
    public Shape2DCollection add(Shape2D... shapes2D){
        for (Shape2D shape2D:shapes2D) {
            add(shape2D);
        }
        return this;
    }

    /**
     * Add a shape2D polygon to the collection
     * @param verticesXY
     * @return this
     */
    public Shape2DCollection addPolygon(float... verticesXY){
        add(new Polygon(verticesXY));
        return this;
    }

    /**
     * Add a shape2D polygon to the collection
     * @param vertices
     * @return this
     */
    public Shape2DCollection addPolygon(Array<Vector2> vertices){
        addPolygon(Basic.toFloats(vertices));
        return this;
    }

    /**
     * Add a shape2D polygon to the collection based on polypoint
     * @param polypoint
     * @return this
     */
    public Shape2DCollection addPolygon(Polypoint polypoint){
        addPolygon(polypoint.coordinates.toArray());
        return this;
    }

    /**
     * add a circle to the collection
     * @param x
     * @param y
     * @param radius
     * @return this
     */
    public Shape2DCollection addCircle(float x,float y,float radius){
        add(new Circle(x,y,radius));
        return this;
    }

    /**
     * add a circle to the collection
     * @param position
     * @return this
     */
    public Shape2DCollection addCircle(Vector2 position,float radius){
        add(new Circle(position.x,position.y,radius));
        return this;
    }

    /**
     * add a rectangle to the collection. (x,y) is the lower left corner.
     * @param x
     * @param y
     * @param width
     * @param height
     * @return this
     */
    public Shape2DCollection addRectangle(float x,float y,float width,float height){
        add(new Rectangle(x,y,width,height));
        return this;
    }

    /**
     * add a polygon that is a line between two points with a given width/thickness
     * sharp cutoff at end points
     * @param thickness
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public Shape2DCollection addLine(float thickness, float x1, float y1, float x2, float y2){
        return add(Shape2DCreator.line(thickness,x1,y1,x2,y2));
    }

    /**
     * add a polygon that is a line between two points with a given width/thickness
     * sharp cutoff at end points
     * @param thickness
     * @param a
     * @param b
     */
    public Shape2DCollection addLine(float thickness,Vector2 a,Vector2 b){
        return add(Shape2DCreator.line(thickness,a,b));
    }
}
