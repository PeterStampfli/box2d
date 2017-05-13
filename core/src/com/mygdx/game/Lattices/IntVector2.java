package com.mygdx.game.Lattices;

import com.badlogic.gdx.math.MathUtils;

/**
 * Vector with integer components based on Vector2.
 */

public class IntVector2 {

    public final static IntVector2 X = new IntVector2(1, 0);
    public final static IntVector2 Y = new IntVector2(0, 1);
    public final static IntVector2 Zero = new IntVector2(0, 0);

    /** the x-component of this vector **/
    public int x;
    /** the y-component of this vector **/
    public int y;

    /** Constructs a new vector at (0,0) */
    public IntVector2 () {
    }

    /** Constructs a vector with the given components
     * @param x The x-component
     * @param y The y-component */
    public IntVector2 (int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Constructs a vector from the given vector
     * @param v The vector */
    public IntVector2 (IntVector2 v) {
        set(v);
    }

    /** @return a copy of this vector */
    public IntVector2 cpy () {
        return new IntVector2(this);
    }

    /** @return The euclidean length */
    public static float len (int x, int y) {
        return (float)Math.sqrt(x * x + y * y);
    }

    /** @return The euclidean length */
    public float len () {
        return (float)Math.sqrt(x * x + y * y);
    }

    /** @return The squared euclidean length */
    public static float len2 (int x, int y) {
        return x * x + y * y;
    }

    /** @return The squared euclidean length */
    public float len2 () {
        return x * x + y * y;
    }


    /** Sets this vector from the given vector
     * @param v The vector
     * @return This vector for chaining */
    public IntVector2 set (IntVector2 v) {
        x = v.x;
        y = v.y;
        return this;
    }

    /** Sets the components of this vector
     * @param x The x-component
     * @param y The y-component
     * @return This vector for chaining */
    public IntVector2 set (int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /** Subtracts the given vector from this vector.
     * @param v The vector
     * @return This vector for chaining */
    public IntVector2 sub (IntVector2 v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    /** Substracts the other vector from this vector.
     * @param x The x-component of the other vector
     * @param y The y-component of the other vector
     * @return This vector for chaining */
    public IntVector2 sub (int x, int y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    /** Adds the given vector to this vector
     * @param v The vector
     * @return This vector for chaining */
    public IntVector2 add (IntVector2 v) {
        x += v.x;
        y += v.y;
        return this;
    }

    /** Adds the given components to this vector
     * @param x The x-component
     * @param y The y-component
     * @return This vector for chaining */
    public IntVector2 add (int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /** @return The dot product */
    public static int dot (int x1, int y1, int x2, int y2) {
        return x1 * x2 + y1 * y2;
    }

	/** The dot product between this and the other vector */
    public int dot (IntVector2 v) {
        return x * v.x + y * v.y;
    }

    /** @return The dot product */
    public int dot (int ox, int oy) {
        return x * ox + y * oy;
    }

    /** Scales this vector by an integer scalar
     * @param scalar The scalar
     * @return This vector for chaining */
    public IntVector2 scl (int scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    /** Multiplies this vector by integer scalars
     * @return This vector for chaining */
    public IntVector2 scl (int x, int y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    /** Scales this vector by another vector
     * @return This vector for chaining */
    public IntVector2 scl (IntVector2 v) {
        this.x *= v.x;
        this.y *= v.y;
        return this;
    }

    /** First scale a supplied vector, then add it to this vector.
     * @param vec addition vector
     * @param scalar for scaling the addition vector */
    public IntVector2 mulAdd (IntVector2 vec, int scalar) {
        this.x += vec.x * scalar;
        this.y += vec.y * scalar;
        return this;
    }

    /** First scale a supplied vector, then add it to this vector.
     * @param vec addition vector
     * @param mulVec vector by whose values the addition vector will be scaled */
    public IntVector2 mulAdd (IntVector2 vec, IntVector2 mulVec) {
        this.x += vec.x * mulVec.x;
        this.y += vec.y * mulVec.y;
        return this;
    }

     /** @return the distance between this and the other vector */
    public static float dst (int x1, int y1, int x2, int y2) {
        final int x_d = x2 - x1;
        final int y_d = y2 - y1;
        return (float)Math.sqrt(x_d * x_d + y_d * y_d);
    }

    /** @param v The other vector
     * @return the distance between this and the other vector */
    public float dst (IntVector2 v) {
        final int x_d = v.x - x;
        final int y_d = v.y - y;
        return (float)Math.sqrt(x_d * x_d + y_d * y_d);
    }

    /** @param x The x-component of the other vector
     * @param y The y-component of the other vector
     * @return the distance between this and the other vector */
    public float dst (int x, int y) {
        final int x_d = x - this.x;
        final int y_d = y - this.y;
        return (float)Math.sqrt(x_d * x_d + y_d * y_d);
    }

    /** @return the squared distance between this and the other vector */
    public static int dst2 (int x1, int y1, int x2, int y2) {
        final int x_d = x2 - x1;
        final int y_d = y2 - y1;
        return x_d * x_d + y_d * y_d;
    }


    /** @return the squared distance between this and the other vector */
    public int dst2 (IntVector2 v) {
        final int x_d = v.x - x;
        final int y_d = v.y - y;
        return x_d * x_d + y_d * y_d;
    }

    /** @param x The x-component of the other vector
     * @param y The y-component of the other vector
     * @return the squared distance between this and the other vector */
    public int dst2 (int x, int y) {
        final int x_d = x - this.x;
        final int y_d = y - this.y;
        return x_d * x_d + y_d * y_d;
    }

    /** Converts this {@code IntVector2} to a string in the format {@code (x,y)}.
     * @return a string representation of this object. */
    public String toString () {
        return "(" + x + "," + y + ")";
    }

    /** Calculates the 2D cross product between this and the given vector.
     * @param v the other vector
     * @return the cross product */
    public int crs (IntVector2 v) {
        return this.x * v.y - this.y * v.x;
    }

    /** Calculates the 2D cross product between this and the given vector.
     * @param x the x-coordinate of the other vector
     * @param y the y-coordinate of the other vector
     * @return the cross product */
    public int crs (int x, int y) {
        return this.x * y - this.y * x;
    }

    /** @return the angle in degrees of this vector (point) relative to the x-axis. Angles are towards the positive y-axis (typically
     *         counter-clockwise) and between 0 and 360. */
    public float angle () {
        float angle = (float)Math.atan2(y, x) * MathUtils.radiansToDegrees;
        if (angle < 0) angle += 360;
        return angle;
    }

    /** @return the angle in degrees of this vector (point) relative to the given vector. Angles are towards the positive y-axis
     *         (typically counter-clockwise.) between -180 and +180 */
    public float angle (IntVector2 reference) {
        return (float)Math.atan2(crs(reference), dot(reference)) * MathUtils.radiansToDegrees;
    }

    /** @return the angle in radians of this vector (point) relative to the x-axis. Angles are towards the positive y-axis.
     *         (typically counter-clockwise) */
    public float angleRad () {
        return (float)Math.atan2(y, x);
    }

    /** @return the angle in radians of this vector (point) relative to the given vector. Angles are towards the positive y-axis.
     *         (typically counter-clockwise.) */
    public float angleRad (IntVector2 reference) {
        return (float)Math.atan2(crs(reference), dot(reference));
    }


    /** Rotates the IntVector2 by 90 degrees in the specified direction, where >= 0 is counter-clockwise and < 0 is clockwise. */
    public IntVector2 rotate90 (int dir) {
        int x = this.x;
        if (dir >= 0) {
            this.x = -y;
            y = x;
        } else {
            this.x = y;
            y = -x;
        }
        return this;
    }

    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    public boolean equals (Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        IntVector2 other = (IntVector2)obj;
        if (x != other.x) return false;
        if (y != other.y) return false;
        return true;
    }

    /** Compares this vector with the other vector.
     * @return whether the vectors are the same. */
    public boolean equals (IntVector2 other) {
        if (other == null) return false;
        if (x != other.x) return false;
        if (y != other.y) return false;
        return true;
    }

    /** Compares this vector with the other vector.
     * @return whether the vectors are the same. */
    public boolean equals (float x, float y) {
        if (this.x != x) return false;
        if (this.y != y) return false;
        return true;
    }

    /** @return Whether the vector is zero */
    public boolean isZero () {
        return x == 0 && y == 0;
    }

    /** @return true if this vector is in line with the other vector
     * (either in the same or the opposite direction) */
    public boolean isOnLine (IntVector2 other) {
        return (x * other.y - y * other.x)==0;
    }

    /** @return true if this vector is collinear with the other vector. */
    public boolean isCollinear (IntVector2 other) {
        return isOnLine(other) && dot(other) > 0f;
    }

    /** @return true if this vector is opposite collinear with the other vector. */
    public boolean isCollinearOpposite (IntVector2 other) {
        return isOnLine(other) && dot(other) < 0f;
    }

    /** @return Whether this vector is perpendicular with the other vector. True if the dot product is 0. */
    public boolean isPerpendicular (IntVector2 vector) {
        return dot(vector)==0;
    }


    /** @return Whether this vector has similar direction compared to the other vector.
     * True if the dot product is > 0. */
    public boolean hasSameDirection (IntVector2 vector) {
        return dot(vector) > 0;
    }

    /** @return Whether this vector has opposite direction compared to the other vector.
     * True if the normalized dot product is < 0. */
    public boolean hasOppositeDirection (IntVector2 vector) {
        return dot(vector) < 0;
    }

    /** Sets the components of this vector to 0
     * @return This vector for chaining */
    public IntVector2 setZero () {
        this.x = 0;
        this.y = 0;
        return this;
    }
}
