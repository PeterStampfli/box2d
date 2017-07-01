package com.mygdx.game.Lattice;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 7/1/17.
 */

public class LatticeVector extends Vector2 {
    Lattice lattice;

    /**
     * create a latticeVector with a lattice.
     *
     * @param lattice
     */
    public LatticeVector(Lattice lattice){
        super();
        this.lattice=lattice;
    }

    /**
     * Change lattice vector from address to position
     *
     * @return
     */
    public LatticeVector positionOfAddress(){
        lattice.positionOfAddress(this);
        return this;
    }

    /**
     * Change lattice vector from position to address
     *
     * @return
     */
    public LatticeVector addressOfPosition(){
        lattice.addressOfPosition(this);
        return this;
    }

    /**
     * Adjusts position to lattice (centers)
     *
     * @return
     */
    public LatticeVector adjust(){
        lattice.adjust(this);
        return this;
    }

    // stepping around, methods that do nothing, to override

    /**
     * Make a step upwards in the address.
     */
    public LatticeVector stepUp() {
        lattice.stepUp(this);
        return this;
    }

    /**
     * Make a step downwards in the address.
     */
    public LatticeVector stepDown() {
        lattice.stepDown(this);
        return this;
    }

    /**
     * Make a step right in the address.
     */
    public LatticeVector stepRight() {
        lattice.stepRight(this);
        return this;
    }

    /**
     * Make a step left in the address.
     */
    public LatticeVector stepLeft() {
        lattice.stepLeft(this);
        return this;
    }

    /**
     * Make a step up left in the address.
     */
    public LatticeVector stepUpLeft() {
        lattice.stepUpLeft(this);
        return this;
    }

    /**
     * Make a step down left in the address.
     */
    public LatticeVector stepDownLeft() {
        lattice.stepDownLeft(this);
        return this;
    }

    /**
     * Make a step up right in the address.
     */
    public LatticeVector stepUpRight() {
        lattice.stepUpRight(this);
        return this;
    }

    /**
     * Make a step down right in the address.
     */
    public LatticeVector stepDownRight() {
        lattice.stepDownRight(this);
        return this;
    }
}
