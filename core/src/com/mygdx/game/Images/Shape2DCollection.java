package com.mygdx.game.Images;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
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
    final private static float epsilon=0.01f;
    public Array<Shape2D> shapes2D;

    /**
     * on creation make the array
     */
    public Shape2DCollection(){
        shapes2D=new Array<Shape2D>();
    }

    /**
     * check if the collection of shapes test a given point
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
     * check if the collection of shapes test a given point
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
        float halfWidth=0.5f*thickness;
        float length=(float) Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        if (length>epsilon) {
            float ex = (x2 - x1) / length * halfWidth;
            float ey = (y2 - y1) / length * halfWidth;
            float[] coordinates = {x1 + ey, y1 - ex, x2 + ey, y2 - ex, x2 - ey, y2 + ex, x1 - ey, y1 + ex};
            addPolygon(coordinates);
        }
        return this;
    }

    /**
     * add a polygon that is a line between two points with a given width/thickness
     * sharp cutoff at end points
     * @param thickness
     * @param a
     * @param b
     */
    public Shape2DCollection addLine(float thickness,Vector2 a,Vector2 b){
        return addLine(thickness,a.x,a.y,b.x,b.y);
    }

    /**
     * add lines of given thickness between points as defined by coordinates
     * line joints and ends are circles
     * joins first and last point if it is a loop
     * @param thickness
     * @param isLoop
     * @param coordinates
     * @return
     */
    public Shape2DCollection addDotsAndLines(float thickness,boolean isLoop, float... coordinates){
        float radius=0.5f*thickness;
        int length=coordinates.length;
        for (int i=0;i<length-1;i+=2){
            addCircle(coordinates[i],coordinates[i+1],radius);
        }
        for (int i=0;i<length-3;i+=2){
            addLine(thickness,coordinates[i],coordinates[i+1],
                    coordinates[i+2],coordinates[i+3]);
        }
        if (isLoop){
            addLine(thickness,coordinates[0],coordinates[1],
                    coordinates[length-2],coordinates[length-1]);
        }
        return this;
    }

    /**
     * add lines of given thickness between points as defined by coordinates
     * line joints and ends are circles
     * no loop
     * @param thickness
     * @param coordinates
     * @return
     */
    public Shape2DCollection addDotsAndLines(float thickness, float... coordinates){
        addDotsAndLines(thickness,false,coordinates);
        return this;
    }

    /**
     * add lines of given thickness between points as defined by coordinates
     * line joints and ends are circles
     * loop is closed if polypoint isLoop=true
     *
     * @param thickness
     * @param polypoint
     * @return
     */
    public Shape2DCollection addDotsAndLines(float thickness,Polypoint polypoint){
        addDotsAndLines(thickness,polypoint.isLoop,polypoint.coordinates.toArray());
        return this;
    }

    /**
     * make lines of given thickness between world points of a polygon
     * may be rotated translated and scaled (around origin)
     * line joints and ends are circles
     * endpoints are joined as for any real polygon
     *
     * @param thickness
     * @param polygon
     * @return
     */
    public Shape2DCollection addDotsAndLines(float thickness,Polygon polygon){
        addDotsAndLines(thickness,true,polygon.getTransformedVertices());
        return this;
    }

    /**
     * make lines of given thickness between world points of a polyline
     * may be rotated translated and scaled (around origin)
     * line joints and ends are circles
     * endpoints are not joined
     *
     * @param thickness
     * @param polyline
     * @return
     */
    public Shape2DCollection addDotsAndLines(float thickness,Polyline polyline){
        addDotsAndLines(thickness,false,polyline.getTransformedVertices());
        return this;
    }

    /**
     * make lines of given thickness between points of a chain
     * line joints and ends are circles
     * ghosts are not included (use this to make a chain substantial
     * @param thickness
     * @param chain
     * @return
     */
    public Shape2DCollection addDotsAndLines(float thickness,Chain chain){
        addDotsAndLines(thickness,chain.isLoop,chain.coordinates);
        return this;
    }

    /**
     * make lines of given thickness between  points of an edge
     * line joints and ends are circles
     * ghosts are not included (use this to make an edge substantial
     * @param thickness
     * @param edge
     * @param
     * @return
     */
    public Shape2DCollection addDotsAndLines(float thickness,Edge edge){
        addDotsAndLines(thickness,false,edge.aX,edge.aY,edge.bX,edge.bY);
        return this;
    }


}
