package com.mygdx.game.physics;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.Images.Chain;
import com.mygdx.game.Images.Edge;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.L;

/**
 * Static methods to create box2d shapes from Shape2D shapes.
 * Shape2D shapes use pixels as unit for length. box2d shapes use meters as length.
 * CALL Box2D.init before using this !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */

public class Box2DShape {

    /**
     * Create box2d CircleShape based on a Shape2D Circle.
     *
     * @param circle Circle (Shape2D)
     * @return CircleShape (box2d)
     */
    static public CircleShape ofShape2D(Circle circle) {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius/Physics.PIXELS_PER_METER);
        circleShape.setPosition(new Vector2(circle.x/Physics.PIXELS_PER_METER, circle.y/Physics.PIXELS_PER_METER));
        return circleShape;
    }

    /**
     * create polygonshape from shape2d polygon, scaled
     * @param polygon
     * @return
     */
    static public PolygonShape ofShape2D(Polygon polygon){
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(Basic.scaled(polygon.getVertices(),1f/Physics.PIXELS_PER_METER));
        return polygonShape;
    }

    /**
     * create a rectangular polygon masterShape with scaling from Shape2D Rectangle
     * @param rectangle
     * @return
     */
    static public PolygonShape ofShape2D(Rectangle rectangle) {
        PolygonShape polygon = new PolygonShape();
        polygon.setAsBox(0.5f * rectangle.width/Physics.PIXELS_PER_METER,
                        0.5f * rectangle.height/Physics.PIXELS_PER_METER,
                        new Vector2((rectangle.x+0.5f*rectangle.width)/Physics.PIXELS_PER_METER,
                                (rectangle.y+0.5f*rectangle.height)/Physics.PIXELS_PER_METER),0);
        return polygon;
    }

    /**
     * create a ChainShape from a Shape2D Chain with scaled dimensions
     * takes into account if it is a loop or chain (with possibly ghosts)
     * @param chain
     * @return
     */
    static public ChainShape ofShape2D(Chain chain){
        ChainShape chainShape=new ChainShape();
        if (chain.isLoop){
            chainShape.createLoop(Basic.scaled(chain.coordinates,1f/Physics.PIXELS_PER_METER));
        }
        else {
            chainShape.createChain(Basic.scaled(chain.coordinates,1f/Physics.PIXELS_PER_METER));
            if (chain.ghostAExists){
                chainShape.setPrevVertex(chain.ghostAX/Physics.PIXELS_PER_METER,
                                         chain.ghostAY/Physics.PIXELS_PER_METER);
            }
            if (chain.ghostBExists){
                chainShape.setNextVertex(chain.ghostBX/Physics.PIXELS_PER_METER,
                                         chain.ghostBY/Physics.PIXELS_PER_METER);
            }
        }
        return chainShape;
    }

    /**
     * create an edgeShape based on a shape2D edge with scaled vertices
     * @param edge
     * @return
     */
    static public EdgeShape ofShape2D(Edge edge){
        EdgeShape edgeShape=new EdgeShape();
        edgeShape.set(edge.aX/Physics.PIXELS_PER_METER,edge.aY/Physics.PIXELS_PER_METER,
                      edge.bX/Physics.PIXELS_PER_METER,edge.bY/Physics.PIXELS_PER_METER);
        edgeShape.setHasVertex0(edge.ghostAExists);
        edgeShape.setHasVertex3(edge.ghostBExists);
        if (edge.ghostAExists){
            edgeShape.setVertex0(edge.ghostAX/Physics.PIXELS_PER_METER,edge.ghostAY/Physics.PIXELS_PER_METER);
        }
        if (edge.ghostBExists){
            edgeShape.setVertex3(edge.ghostBX/Physics.PIXELS_PER_METER,edge.ghostBY/Physics.PIXELS_PER_METER);
        }
        return edgeShape;
    }

    /**
     * create a scaled (Box2D) Shape from a Shape2D masterShape
     * @param shape2D
     * @return
     */
    static public Shape ofShape2D(Shape2D shape2D){
        if (shape2D instanceof Polygon){
            return ofShape2D((Polygon) shape2D);
        }
        if (shape2D instanceof Circle){
            return ofShape2D((Circle) shape2D);
        }
        if (shape2D instanceof Chain){
            return ofShape2D((Chain) shape2D);
        }
        if (shape2D instanceof Edge){
            return ofShape2D((Edge) shape2D);
        }
        L.og("Box2DShape: cannot convert "+shape2D.getClass()+" !!!");
        return null;
    }
}
