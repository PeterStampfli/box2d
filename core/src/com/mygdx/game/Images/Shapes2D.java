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

public class Shapes2D implements Shape2D {
    public Array<Shape2D> shapes2D;

    /**
     * on creation make the array
     */
    public Shapes2D(){
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
     * add one or more shapes to the collection
     * @param shapes2D
     */
    public void add(Shape2D... shapes2D){
        for (Shape2D shape2D:shapes2D) {
            this.shapes2D.add(shape2D);
        }
    }

    /**
     * Add a shape2D polygon to the collection
     * @param verticesXY
     */
    public void addPolygon(float[] verticesXY){
        add(new Polygon(verticesXY));
    }

    /**
     * Add a shape2D polygon to the collection
     * @param vertices
     */
    public void addPolygon(Array<Vector2> vertices){
        addPolygon(Basic.toFloats(vertices));
    }

    /**
     * add a circle to the collection
     * @param x
     * @param y
     * @param radius
     */
    public void addCircle(float x,float y,float radius){
        add(new Circle(x,y,radius));
    }

    /**
     * add a rectangle to the collection. (x,y) is the lower left corner.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void addRectangle(float x,float y,float width,float height){
        add(new Rectangle(x,y,width,height));
    }
}
