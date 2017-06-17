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
        vector.x=left+cellWidth*(i+0.5f*(Math.round(j)&1+1));
        vector.y=bottom+cellHeight*0.5f*(j+1);
    }

    @Override
    // cutting to a rectangle - rough test
    public boolean positionIsInside(float x,float y) {
        return (x>=left)&&(x<=left+cellWidth* addressWidth)&&(y>=bottom)&&(y<=bottom+0.5f*cellHeight* addressHeight);
    }


    @Override
    public void addressOfPosition(Vector2 address, float x, float y) {
        //  using "floor because I am subtracting left/lower border of unit cell
        // get indices of center
        int iCenter=(int) Math.floor((x-left)/cellWidth);
        int jCenter=(int) Math.floor((y-bottom)/cellHeight)*2+1;
        // position relative to center
        x-=(iCenter+0.5f)*cellWidth;
        y-=(jCenter+0.5f)*cellHeight;
        float len2FromCenter
    }
}
