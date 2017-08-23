package com.mygdx.game.Images;

import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.ShortArray;
import com.mygdx.game.utilities.Device;

/**
 * Triangulates simple nonintersecting polygons. collects the tringles as Shape2D Polygons
 */

public class Triangles extends Shape2DCollection {
    EarClippingTriangulator triangulator;

    /**
     * create with the triangulator of the device ...
     *
     * @param device
     */
    public Triangles(Device device){
        triangulator=device.createTriangulator();
    }


    /*
    public ShortArray computeTriangles(float[] vertices)
See Also:
computeTriangles(float[], int, int)
computeTriangles
public ShortArray computeTriangles(float[] vertices,
                                   int offset,
                                   int count)
Triangulates the given (convex or concave) simple polygon to a list of triangle vertices.
Parameters:
vertices - pairs describing vertices of the polygon, in either clockwise or counterclockwise order.
Returns:
triples of triangle indices in clockwise order. Note the returned array is reused for later calls to the same method.

     */

    /**
     * Dissect a simple polygon defined by vertices into triangle polygons.
     * Add triangles to the collection.
     *
     * @param vertices
     */
    public void addTriangles(float[] vertices){
        ShortArray triangles=triangulator.computeTriangles(vertices);
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
     * @param vertices
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
     * @param polygon
     */
    public void addTriangles(Polygon polygon){
        addTriangles(polygon.getVertices());
    }

    /**
     * Dissect a simple polygon defined by polypoint object into triangle polygons.
     * Add triangles to the collection.
     *
     * @param polypoint
     */
    public void addTriangles(Polypoint polypoint){
        addTriangles(polypoint.coordinates);
    }

    /**
     * Dissect a simple polygon defined by a chain object into triangle polygons.
     * Add triangles to the collection.
     *
     * @param chain
     */
    public void addTriangles(Chain chain){
        addTriangles(chain.coordinates);
    }
}
