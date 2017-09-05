package com.mygdx.game.Images;

import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.ShortArray;
import com.mygdx.game.utilities.Device;

/**
 * Triangulates simple non-intersecting polygons. collects the triangles as Shape2D Polygons
 */

public class Triangles extends Shape2DCollection {
    EarClippingTriangulator triangulator;

    /**
     * create with the triangulator of the device ...
     *
     * @param device Device, delivers the triangulator
     */
    public Triangles(Device device){
        triangulator=device.createTriangulator();
    }

    /**
     * Dissect a simple polygon defined by vertices into triangle polygons.
     * Add the triangles to the collection.
     *
     * @param vertices float... or float[], contains pairs of coordinates
     */
    public void addTriangles(float... vertices){
        ShortArray triangles=triangulator.computeTriangles(vertices);   // this array will be reused
        int vertex3;
        for (int i=triangles.size-3;i>=0;i-=3){
            float[] triangleVertices=new float[6];
            vertex3=2*triangles.get(i);
            triangleVertices[0]=vertices[vertex3];
            triangleVertices[1]=vertices[vertex3+1];
            vertex3=2*triangles.get(i+2);    // reversed, to get counterclockwise order
            triangleVertices[2]=vertices[vertex3];
            triangleVertices[3]=vertices[vertex3+1];
            vertex3=2*triangles.get(i+1);
            triangleVertices[4]=vertices[vertex3];
            triangleVertices[5]=vertices[vertex3+1];
            add(new Polygon(triangleVertices));
        }
    }

    /**
     * Dissect a simple polygon defined by vertices into triangle polygons.
     * Add triangles to the collection.
     *
     * @param vertices FloatArray, contains pairs of coordinates
     */
    public void addTriangles(FloatArray vertices){
        ShortArray triangles=triangulator.computeTriangles(vertices);
        int vertex3;
        for (int i=triangles.size-3;i>=0;i-=3){
            float[] triangleVertices=new float[6];
            vertex3=2*triangles.get(i);
            triangleVertices[0]=vertices.get(vertex3);
            triangleVertices[1]=vertices.get(vertex3+1);
            vertex3=2*triangles.get(i+2);    // reversed, to get counterclockwise order
            triangleVertices[2]=vertices.get(vertex3);
            triangleVertices[3]=vertices.get(vertex3+1);
            vertex3=2*triangles.get(i+1);
            triangleVertices[4]=vertices.get(vertex3);
            triangleVertices[5]=vertices.get(vertex3+1);
            add(new Polygon(triangleVertices));
        }
    }

    /**
     * Dissect a simple polygon into triangle polygons.
     * Add triangles to the collection.
     *
     * @param polygon Polygon
     */
    public void addTriangles(Polygon polygon){
        addTriangles(polygon.getVertices());
    }

    /**
     * Dissect a simple polygon defined by polypoint object into triangle polygons.
     * Add triangles to the collection.
     *
     * @param polypoint Polypoint
     */
    public void addTriangles(Polypoint polypoint){
        addTriangles(polypoint.coordinates);
    }

    /**
     * Dissect a simple polygon defined by a chain object into triangle polygons.
     * Add triangles to the collection.
     *
     * @param chain Chain
     */
    public void addTriangles(Chain chain){
        addTriangles(chain.coordinates);
    }
}
