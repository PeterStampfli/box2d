package com.mygdx.game.Images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;

/**
 * Created by peter on 4/11/17.
 * drawing the shape2D's and Polypoint with a shape renderer
 * start shaperenderer, set color and type (ShapeType.Line) outside
 */

public class Shape2DRenderer extends ShapeRenderer{
    public float nullRadius;

    /**
     * create with shaperenderer to simplify further work
     * @param nullRadius
     */
    public Shape2DRenderer(float nullRadius){
        this.nullRadius=nullRadius;
    }

    /**
     * create with default null radius
     */
    public Shape2DRenderer(){
        this.nullRadius=2;
    }

    /**
     * set the nullRadius after creation with default
     * @param nullRadius
     */
    public void setNullRadius(float nullRadius){
        this.nullRadius=nullRadius;
    }

    /**
     * draw a point in the 2d plane as a small circle
     * @param x
     * @param y
     */
    public void point(float x,float y){
        circle(x,y,nullRadius);
    }

    /**
     * draws the polygon with original vertices
     * @param polygon
     */
    public void draw(Polygon polygon){
        polygon(polygon.getVertices());
    }

    /**
     * draws the polyline with original vertices
     * @param polyline
     */
    public void draw(Polyline polyline){
        polyline(polyline.getVertices());
    }

    /**
     * draw the points of a polypoint object
     * @param polypoint
     */
    public void draw(Polypoint polypoint){
        for (int i=polypoint.coordinates.size-2;i>=0;i-=2){
            point(polypoint.coordinates.get(i),polypoint.coordinates.get(i+1));
        }
    }

    /**
     * draws the polygon with original vertices
     * @param circle
     */
    public void draw(Circle circle){
        circle(circle.x,circle.y,circle.radius);
    }

    /**
     * draw a rectangle shape
     * @param rectangle
     */
    public void draw(Rectangle rectangle){
        rect(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
    }

    /**
     * draw an edge with ghosts, if they exist
     * @param edge
     */
    public void draw(Edge edge){
        line(edge.aX,edge.aY,edge.bX,edge.bY);
        if (edge.ghostAExists){
            point(edge.ghostAX,edge.ghostAY);
        }
        if (edge.ghostBExists){
            point(edge.ghostBX,edge.ghostBY);
        }
    }


    public void draw(Shape2D shape){
        if (shape instanceof Polygon){
            draw((Polygon)shape);
        }
        else if (shape instanceof Circle){
            draw((Circle) shape);
        }
        else if (shape instanceof Rectangle){
            draw((Rectangle) shape);
        }
        else if (shape instanceof Polypoint){
            draw((Polypoint)shape);
        }
        else if (shape instanceof Shape2DCollection){
            Shape2DCollection shapes=(Shape2DCollection) shape;
            for (Shape2D subShape:shapes.shapes2D){
                draw(subShape);
            }
        }
        else {
            Gdx.app.log(" ******************** draw","unknown shape "+shape.getClass());
        }
    }
}
