package com.mygdx.game.Images;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by peter on 4/11/17.
 * drawing the shape2D's and Polypoint with a shape renderer
 * start shaperenderer, set color and type (ShapeType.Line) outside
 */

public class DrawShape2D {
    public ShapeRenderer shapeRenderer;
    public float nullRadius;

    /**
     * create with shaperenderer to simplify further work
     * @param shapeRenderer
     * @param nullRadius
     */
    public DrawShape2D(ShapeRenderer shapeRenderer,float nullRadius){
        this.shapeRenderer=shapeRenderer;
        this.nullRadius=nullRadius;
    }

    /**
     * draws the polygon with original vertices
     * @param polygon
     */
    public void draw(Polygon polygon){
        shapeRenderer.polygon(polygon.getVertices());
    }

    /**
     * draws the polyline with original vertices
     * @param polyline
     */
    public void draw(Polyline polyline){
        shapeRenderer.polyline(polyline.getVertices());
    }

    /**
     * draw the points of a polypoint object
     * @param polypoint
     */
    public void draw(Polypoint polypoint){
        for (int i=polypoint.coordinates.size-2;i>=0;i-=2){
            shapeRenderer.circle(polypoint.coordinates.get(i),polypoint.coordinates.get(i+1),nullRadius);
        }
    }

    /**
     * draws the polygon with original vertices
     * @param circle
     */
    public void draw(Circle circle){
        shapeRenderer.circle(circle.x,circle.y,circle.radius);
    }

    /**
     * draw a rectangle shape
     * @param rectangle
     */
    public void draw(Rectangle rectangle){
        shapeRenderer.rect(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
    }

    /**
     * draw an edge with ghosts, if they exist
     * @param edge
     */
    public void draw(Edge edge){
        shapeRenderer.line(edge.aX,edge.aY,edge.bX,edge.bY);
        if (edge.ghostAExists){
            shapeRenderer.circle(edge.ghostAX,edge.ghostAY,nullRadius);
        }
        if (edge.ghostBExists){
            shapeRenderer.circle(edge.ghostBX,edge.ghostBY,nullRadius);
        }
    }
}
