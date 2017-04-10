package com.mygdx.game.physics;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utilities.Basic;

/**
 * Created by peter on 4/10/17.
 * static methods to create box2d shapes
 */

public class CreateShapeBox2D {

    /**
     * create scaled circle shape based on graphics world input data
     * @param x
     * @param y
     * @param radius
     * @return
     */
    static public CircleShape circle(float x, float y, float radius) {
        CircleShape circle = new CircleShape();
        circle.setRadius(radius/Physics.PIXELS_PER_METER);
        circle.setPosition(new Vector2(x/Physics.PIXELS_PER_METER, y/Physics.PIXELS_PER_METER));
        return circle;
    }

    /**
     * create scaled circle shape based on graphics world input data
     * @param position
     * @param radius
     * @return
     */
    static public CircleShape circle(Vector2 position, float radius) {
        return circle(position.x, position.y, radius);
    }

    /**
     * create scaled circle shape based on graphics world shape Circle
     * @param circle
     * @return
     */
    static public CircleShape circle(Circle circle) {
        return circle(circle.x, circle.y, circle.radius);
    }

    /**
     * create polygonshape from graphics world vertices
     * @param vertices
     * @return
     */
    static public PolygonShape polygonShape(float[] vertices){
        PolygonShape polygon = new PolygonShape();
        polygon.set(Basic.scaled(vertices,1f/Physics.PIXELS_PER_METER));
        return polygon;
    }

    /**
     * create polygonshape from graphics world vertices
     * @param vertices
     * @return
     */
    static public PolygonShape polygonShape(Array<Vector2> vertices){
        return polygonShape(Basic.toFloats(vertices));
    }

    /**
     * create polygonshape from graphics world polygon
     * @param polygon
     * @return
     */
    static public PolygonShape polygonShape(Polygon polygon){
        return polygonShape(polygon.getVertices());
    }

    /**
     * create a rectangular polygon shape with scaling from graphics to physics
     * @param width  full width
     * @param height  full height
     * @param centerX
     * @param centerY
     * @param angle
     * @return
     */
    static public PolygonShape boxShape(float width, float height, float centerX,float centerY, float angle) {
        PolygonShape polygon = new PolygonShape();
        polygon.setAsBox(0.5f * width/Physics.PIXELS_PER_METER, 0.5f * height/Physics.PIXELS_PER_METER,
                new Vector2(centerX/Physics.PIXELS_PER_METER,centerY/Physics.PIXELS_PER_METER), angle);
        return polygon;
    }

    /**
     * create a rectangular polygon shape,
     * @param width  full width
     * @param height  full height
     * @param center
     * @param angle
     * @return
     */
    static public PolygonShape boxShape(float width, float height, Vector2 center,float angle) {
        return  boxShape(width, height, center.x,center.y, angle);
    }

    /**
     * create a rectangular polygon shape, axis aligned
     * @param width  full width
     * @param height  full height
     * @param centerX
     * @param centerY
     * @return
     */
    static public PolygonShape boxShape(float width, float height, float centerX,float centerY) {
        return  boxShape(width, height, centerX,centerY, 0f);
    }

    /**
     * create a rectangular polygon shape, axis aligned
     * @param width  full width
     * @param height  full height
     * @param center
     * @return
     */
    static public PolygonShape boxShape(float width, float height, Vector2 center) {
        return  boxShape(width, height, center.x,center.y, 0f);
    }

    /**
     * create a rectangular polygon shape, axis aligned, based on shape2 Rectangle
     * @param rectangle
     * @return
     */
    static public PolygonShape boxShape(Rectangle rectangle) {
        return boxShape(rectangle.width,rectangle.height,
                        rectangle.x+0.5f*rectangle.width,rectangle.y+0.5f*rectangle.height);

    }


}
