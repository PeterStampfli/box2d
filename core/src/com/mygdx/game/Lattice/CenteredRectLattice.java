package com.mygdx.game.Lattice;

import com.badlogic.gdx.math.Vector2;

/**
 * Centered rectangular lattice with irregular hexagons as cells.
 * And lattice of regular hexagon cells.
 * Address.x gives x-position of the cell
 * Address.y/2 gives y position of cell.
 * For even address.y we have cells at the corner of the rectangular super cell.
 * For odd address.y we have cells at the center of the rectangular super cell.
 */

public class CenteredRectLattice extends Lattice {

    /**
     * Create with given cell size (square lattice)
     *
     * @param size float, width and height of the square lattice cell
     */
    public CenteredRectLattice(float size){
        super(size);
    }

    /**
     * Create with given cell width and height
     *
     * @param width float, of the lattice cell
     * @param height float, of the lattice cell
     */
    public CenteredRectLattice(float width,float height){
        super(width, height);
    }

    @Override
    public Vector2 positionOfAddress(Vector2 vector, int i, int j) {
        vector.x=left+cellWidth*(i+0.5f*(j&1));
        vector.y=bottom+cellHeight*0.5f*j;
        return vector;
    }

    @Override
    public Vector2 addressOfPosition(Vector2 address, float x, float y) {
        x-=left;
        y-=bottom;
        // address of bottom left corner
        int i=Math.round(x/cellWidth);
        int j=Math.round(y/cellHeight);

        // reduce to coordinates around bottom left corner
        x-=i*cellWidth;
        y-=j*cellHeight;
        // two j per unit cell
        j*=2;

        float yRef=0.25f*cellHeight-cellWidth/cellHeight*(Math.abs(x)-0.25f*cellWidth);
        if (x>0){
            if (y>yRef){
                j++;
            }
            else if (y<-yRef){
                j--;
            }
        }
        else {
            if (y>yRef){
                j++;
                i--;
            }
            else if (y<-yRef){
                j--;
                i--;
            }
        }
        address.x=i;
        address.y=j;
        return address;
    }

    /**
     * Make a step upwards in the address.
     *
     * @param address Vector2, to transform
     */
    public void stepUp(Vector2 address) {
        address.y+=2;
    }

    /**
     * Make a step downwards in the address.
     *
     * @param address Vector2, to transform
     */
    public void stepDown(Vector2 address) {
        address.y-=2;
    }

    /**
     * Make a step right in the address.
     *
     * @param address Vector2, to transform
     */
    public void stepRight(Vector2 address) {
        address.x+=2;
    }

    /**
     * Make a step left in the address.
     *
     * @param address Vector2, to transform
     */
    public void stepLeft(Vector2 address) {
        address.x-=2;
    }

    /**
     * Make a step upwards and left in the address.
     *
     * @param address Vector2, to transform
     */
    public void stepUpLeft(Vector2 address) {
        if ((Math.round(address.y)&1)==0){
            address.x--;
        }
        address.y++;
    }

    /**
     * Make a step left and down in the address.
     *
     * @param address Vector2, to transform
     */
    public void stepDownLeft(Vector2 address) {
        if ((Math.round(address.y)&1)==0){
            address.x--;
        }
        address.y--;
    }

    /**
     * Make a step up and right in the address.
     *
     * @param address Vector2, to transform
     */
    public void stepUpRight(Vector2 address) {
        if ((Math.round(address.y)&1)!=0){
            address.x++;
        }
        address.y++;
    }

    /**
     * Make a step down and right in the address.
     *
     * @param address Vector2, to transform
     */
    public void stepDownRight(Vector2 address) {
        if ((Math.round(address.y)&1)!=0){
            address.x++;
        }
        address.y--;
    }
}
