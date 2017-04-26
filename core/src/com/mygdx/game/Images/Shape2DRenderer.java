package com.mygdx.game.Images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;

/**
 * An extended shapeRenderer that draws Shape2D shapes including collections, Chains,
 * Edges and Polypoint objects. Interfaces the Shape2D objects with corresponding circle,
 * polygon, etc. shapeRenderer commands. Uses preset colors for points, lines and polygons.
 */

public class Shape2DRenderer extends ShapeRenderer{
    public float nullRadius;                               // the circle radius to use for points
    public Color pointColor=Color.RED;
    public Color lineColor=Color.GREEN;
    public Color polygonColor=Color.YELLOW;

    /**
     * Create with special nullRadius.
     *
     * @param nullRadius float, radius for circles marking points
     */
    public Shape2DRenderer(float nullRadius){
        setNullRadius(nullRadius);
    }

    /**
     * Create with default null radius.
     */
    public Shape2DRenderer(){
        setNullRadius(2);
    }

    /**
     * Set the nullRadius.
     *
     * @param nullRadius float, radius for circles marking points
     */
    public void setNullRadius(float nullRadius){
        this.nullRadius=nullRadius;
    }

    /**
     * Begin rendering with ShapeType.line.
     */
    public void begin(){begin(ShapeRenderer.ShapeType.Line);}

    /**
     * Draw a point in the 2d plane as a small circle of nullRadius.
     *
     * @param x float, x-coordinate of point
     * @param y float, y-coordinate of point
     */
    public void point(float x,float y){
        setColor(pointColor);
        circle(x,y,nullRadius);
    }

    /**
     * Draws the polygon with its transformed world vertices (scaled, translated and rotated).
     *
     * @param polygon Polygon object to draw as outline of surface
     */
    public void draw(Polygon polygon){
        setColor(polygonColor);
        polygon(polygon.getTransformedVertices());
    }

    /**
     * Draws the Polyline with its transformed world vertices (scaled, translated and rotated).
     *
     * @param polyline Polyline object to draw as lines.
     */
    public void draw(Polyline polyline){
        setColor(lineColor);
        polyline(polyline.getTransformedVertices());
    }

    /**
     * Draw the points of a polypoint object.
     *
     * @param polypoint Polypoint, to draw as a collection of points
     */
    public void draw(Polypoint polypoint){
        for (int i=polypoint.coordinates.size-2;i>=0;i-=2){
            point(polypoint.coordinates.get(i),polypoint.coordinates.get(i+1));
        }
    }

    /**
     * Draws a circle.
     *
     * @param circle Circle to draw as a line.
     */
    public void draw(Circle circle){
        setColor(lineColor);
        circle(circle.x,circle.y,circle.radius);
    }

    /**
     * Draw a rectangle.
     *
     * @param rectangle Rectangle to draw as outline of a polygon.
     */
    public void draw(Rectangle rectangle){
        setColor(polygonColor);
        rect(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
    }

    /**
     * Draw an edge as a line with ghosts as points, if they exist.
     *
     * @param edge Edge, to draw as a single line with ghost points.
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

    /**
     * Draw an Chain as a polyline with ghosts as points, if they exist.
     * Close the loop if the Chain is a loop.
     *
     * @param chain Chain to draw as a polyline with ghost points.
     */
    public void draw(Chain chain){
        int length=chain.coordinates.length;
        setColor(lineColor);
        for (int i=0;i<length-3;i+=2){
            line(chain.coordinates[i],chain.coordinates[i+1],
                    chain.coordinates[i+2],chain.coordinates[i+3]);
        }
        if (chain.isLoop){
            line(chain.coordinates[0],chain.coordinates[1],
                    chain.coordinates[length-2],chain.coordinates[length-1]);
        }
        if (chain.ghostAExists){
            point(chain.ghostAX,chain.ghostAY);
        }
        if (chain.ghostBExists){
            point(chain.ghostBX,chain.ghostBY);
        }
    }

    /**
     * Draw any Shape2D shape, including collections.
     *
     * @param shape Shape2D to draw
     */
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
        else if (shape instanceof Polyline){
            draw((Polyline) shape);
        }
        else if (shape instanceof Edge){
            draw((Edge) shape);
        }
        else if (shape instanceof Chain){
            draw((Chain) shape);
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
