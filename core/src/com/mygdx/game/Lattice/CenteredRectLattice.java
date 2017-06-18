package com.mygdx.game.Lattice;

import com.badlogic.gdx.math.Vector2;

/**
 * Centered rectangular lattice.
 * Address.x gives x-position of the cell
 * Address.y/2 gives y position of cell. For even address.y we have corner cells.
 * For odd address.y we have center cells.
 *
 */

public class CenteredRectLattice extends Lattice {

    /**
     * Create with given cell size (square lattice)
     *
     * @param size
     */
    public CenteredRectLattice(float size){
        super(size);
    }

    /**
     * Create with given cell width and height
     *
     * @param width
     * @param height
     */
    public CenteredRectLattice(float width,float height){
        super(width, height);
    }

    @Override
    public void positionOfAddress(Vector2 vector, float i, float j) {
        vector.x=left+cellWidth*(i+0.5f*(Math.round(j)&1));
        vector.y=bottom+cellHeight*0.5f*j;
    }

    @Override
    // cutting to a rectangle - rough test
    public boolean positionIsInside(float x,float y) {
        return (x>=left)&&(x<=left+cellWidth* addressWidth)&&(y>=bottom)&&(y<=bottom+0.5f*cellHeight* addressHeight);
    }

    @Override
    public void addressOfPosition(Vector2 address, float x, float y) {
        // using "floor because I am subtracting position of left/lower lattice point,
        // which is at the border of the unit cell
        int iCorner;
        int jCorner;
        int iCenter = (int) Math.floor((x - left) / cellWidth);
        int jCenter = (int) Math.floor((y - bottom) / cellHeight) * 2 + 1; // get indices of center
        // position relative to center
        x -= left + cellWidth * (iCenter + 0.5f);
        y -= bottom + 0.5f * cellHeight * jCenter;
        float len2FromCenter = Vector2.len2(x, y);
        if (x > 0) {
            x -= 0.5f * cellWidth;
            iCorner = iCenter + 1;
        } else {
            x += 0.5f * cellWidth;
            iCorner = iCenter;
        }
        if (y > 0) {
            y -= 0.5f * cellHeight;
            jCorner = jCenter + 1;
        } else {
            y += 0.5f * cellHeight;
            jCorner = jCenter - 1;
        }
        if (len2FromCenter < Vector2.len2(x, y)) {
            address.x = iCenter;
            address.y = iCenter;
        } else {
            address.x = iCorner;
            address.y = jCorner;
        }
    }

    /**
     * Make a step upwards in the address.
     *
     * @param address Vector2, to transform
     * @return this, for chaining
     */    public void stepUp(Vector2 address) {
        address.y+=2;
    }

    /**
     * Make a step downwards in the address.
     *
     * @param address Vector2, to transform
     * @return this, for chaining
     */    public void stepDown(Vector2 address) {
        address.y-=2;
    }

    /**
     * Make a step right in the address.
     *
     * @param address Vector2, to transform
     * @return this, for chaining
     */
    public void stepRight(Vector2 address) {
        address.x+=2;
    }

    /**
     * Make a step left in the address.
     *
     * @param address Vector2, to transform
     * @return this, for chaining
     */
    public void stepLeft(Vector2 address) {
        address.x-=2;
    }

    /**
     * Make a step upwards and left in the address.
     *
     * @param address Vector2, to transform
     * @return this, for chaining
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
     * @return this, for chaining
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
     * @return this, for chaining
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
     * @return this, for chaining
     */
    public void stepDownRight(Vector2 address) {
        if ((Math.round(address.y)&1)!=0){
            address.x++;
        }
        address.y--;
    }
}
