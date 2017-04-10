package com.mygdx.game.physics;

import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by peter on 4/10/17.
 * collection of box2D shapes and collection of collections
 * conversion from Shape2D
 */

public class ShapesBox2D implements Disposable{
    public Array<Shape> shapes;
    public Array<ShapesBox2D>shapeCollections;

    public ShapesBox2D(){
        shapes=new Array<Shape>();
        shapeCollections=new Array<ShapesBox2D>();
    }

    /**
     * dispose the shapes after fixture creation (and clear arrays for reuse and to be safe)
     */
    public void dispose(){
        for (Shape shape:shapes) {
            shape.dispose();
        }
        for (ShapesBox2D shapeCollection:shapeCollections) {
            shapeCollection.dispose();
        }
        shapes.clear();
        shapeCollections.clear();
    }

    /**
     * add a shape to the collection
     * @param shape
     */
    public void add(Shape shape){
        shapes.add(shape);
    }

    /**
     * add a collection of shapes to the collection
     * @param shapesBox2D
     */
    public void add(ShapesBox2D shapesBox2D){
        shapeCollections.add(shapesBox2D);
    }

    //  static methods to create box2D shapes from shapes2D

}
