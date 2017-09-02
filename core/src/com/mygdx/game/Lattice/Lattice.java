package com.mygdx.game.Lattice;

import com.badlogic.gdx.math.Vector2;

/**
 * Defines LatticeVector methods for lattices together with lattice data
 */

public abstract class Lattice {
    public float left;                              // position of lowest left lattice CENTER
    public float bottom;
    public float cellWidth;
    public float cellHeight;
    private Vector2 result=new Vector2();

    /**
     * default constructor ...
     */
    public Lattice() {
    }

    /**
     * Create with given size (square lattice)
     *
     * @param size float, width and height of the square cell
     */
    public Lattice(float size) {
        setCellSize(size);
    }

    /**
     * Create with given size (square lattice)
     *
     * @param width float, of the lattice cell
     * @param height float, of the lattice cell
     */
    public Lattice(float width,float height) {
        setCellSize(width, height);
    }

    /**
     * Set size of a lattice unit
     *
     * @param width float, of the lattice cell
     * @param height float, of the lattice cell
     * @return this,for chaining
     */
    public Lattice setCellSize(float width,float height) {
        this.cellWidth = width;
        this.cellHeight=height;
        return  this;
    }

    /**
     * Set size of a lattice unit
     *
     * @param size float, width and height of the square cell
     * @return this,for chaining
     */
    public Lattice setCellSize(float size) {
        return setCellSize(size,size);
    }

    /**
     * Set position of center of lowest cell at left. With chaining.
     *
     * @param left float, x-coordinate of center of cells at the left
     * @param bottom float, y-coordinate of center of cells at the bottom
     * @return this, for chaining
     */
    public Lattice setLeftBottomCenter(float left, float bottom) {
        this.left = left;
        this.bottom = bottom;
        return this;
    }

    // manipulate LatticeVector objects

    /**
     * Calculate components of address vector with "integer" components
     * fitting given position
     *
     * @param address Vector2, will be determined from position (x,y)
     * @param x float, x-component
     * @param y float, y-component
     * @return the address, for chaining
     */
    abstract public Vector2 addressOfPosition(Vector2 address,float x,float y);

    /**
     * set address vector depending on position
     *
     * @param address Vector2, will be set to the address of cell that contains position
     * @param position Vector2,
     * @return the changed address for chaining
     */
    public Vector2 addressOfPosition(Vector2 address,Vector2 position){
        return addressOfPosition(address,position.x,position.y);
    }

    /**
     * get address depending on position
     *
     * @param x float, x-coordinate
     * @param y float, y-coordinate
     * @return the address for chaining, will be overwritten at next call
     */
    public Vector2 addressOfPosition(float x,float y){
        return addressOfPosition(result,x,y);
    }

    /**
     * get address depending on position
     *
     * @param vector, Vector2 position
     * @return the address for chaining, will be overwritten at next call
     */
    public Vector2 addressOfPosition(Vector2 vector){
        return addressOfPosition(result,vector.x,vector.y);
    }

    /**
     * Calculate components of the position fitting the address.
     * The center of the cell.
     *
     * @param vector will be set to position corresponding to address
     * @param i int, x-component of address
     * @param j int, y-component of address
     * @return Vector2 position for chaining
     */
    abstract public Vector2 positionOfAddress(Vector2 vector,int i,int j);

    /**
     * set position depending on address.
     *
     * @param position will be set to position
     * @return Vector2 position for chaining
     */
    public Vector2 positionOfAddress(Vector2 position,Vector2 address){
        return positionOfAddress(position,Math.round(address.x),Math.round(address.y));
    }

    /**
     * get position depending on address.
     *
     * @param i int, x-component of address
     * @param j int, y-component of address
     * @return Vector2 position for chaining, will be overwritten
     */
    public Vector2 positionOfAddress(int i,int j){
        return positionOfAddress(result,i,j);
    }

    /**
     * get position depending on address.
     *
     * @param address Vector2
     * @return Vector2 position for chaining, will be overwritten at next call
     */
    public Vector2 positionOfAddress(Vector2 address){
        return positionOfAddress(result,Math.round(address.x),Math.round(address.y));
    }

    /**
     * Adjusts position to lattice (centers)
     *
     * @param vector Vector2, position vector to adjust, will change its value to center position
     * @return Vector2, adjusted vector, for chaining
     */
    public Vector2 adjust(Vector2 vector){
        vector.set(positionOfAddress(addressOfPosition(vector)));
        return vector;
    }

    // stepping around, methods that do nothing, to override

    /**
     * Make a step upwards in the address.
     *
     * @param address Vector2, to transform
     */
    public void stepUp(Vector2 address) {
        throw new RuntimeException("Current lattice does not implement stepUp");
    }

    /**
     * Make a step downwards in the address.
     *
     * @param address Vector2, to transform
     */
    public void stepDown(Vector2 address) {
        throw new RuntimeException("Current lattice does not implement stepDown");
    }

    /**
     * Make a step right in the address.
     *
     * @param address Vector2, to transform
     */
    public void stepRight(Vector2 address) {
        throw new RuntimeException("Current lattice does not implement stepRight");
    }

    /**
     * Make a step left in the address.
     *
     * @param address Vector2, to transform
     */
    public void stepLeft(Vector2 address) {
        throw new RuntimeException("Current lattice does not implement stepLeft");
    }

    /**
     * Make a step upwards and left in the address.
     *
     * @param address Vector2, to transform
     */
    public void stepUpLeft(Vector2 address) {
        throw new RuntimeException("Current lattice does not implement stepUpLeft");
    }

    /**
     * Make a step left and down in the address.
     *
     * @param address Vector2, to transform
     */
    public void stepDownLeft(Vector2 address) {
        throw new RuntimeException("Current lattice does not implement stepDownLeft");
    }

    /**
     * Make a step up and right in the address.
     *
     * @param address Vector2, to transform
     */
    public void stepUpRight(Vector2 address) {
        throw new RuntimeException("Current lattice does not implement stepUpRight");
    }

    /**
     * Make a step down and right in the address.
     *
     * @param address Vector2, to transform
     */
    public void stepDownRight(Vector2 address) {
        throw new RuntimeException("Current lattice does not implement stepDownRight");
    }
}
