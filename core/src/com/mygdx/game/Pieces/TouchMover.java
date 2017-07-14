package com.mygdx.game.Pieces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utilities.Device;

/**
 * Poll touch events and transmit to touchable objects. Touchable piece is typically a TouchableCollection.
 */

public class TouchMover extends InputAdapter {
    private Touchable piece;
    public Device device;
    //  touchPosition of touch as average (dragging) and displacement
    public Vector2 touchPosition=new Vector2();
    public Vector2 deltaTouchPosition=new Vector2();
    //  positions of last touch and new touch
    private Vector2 oldTouchPosition=new Vector2();
    private Vector2 newTouchPosition=new Vector2();
    public Vector2 scrollPosition=new Vector2();
    private boolean pieceIsSelected = false;                                 // piece is really selected
    // if there was touch in previous call, we need this because we do polling
    private boolean wasTouching = false;

    /**
     * Create a TouchMove controller with its touch reader
     * @param device with camera and touch reader
     */
    public TouchMover(Device device) {
        this.device=device;
    }

    /**
     * set the touchable piece that will be controlled by the touchMover.
     * Sets wasTouching to false as a reset and because this piece most probably has not been touched at call.
     * Call this method in the show() method of screens.
     *
     * @param piece
     */
    public void setPiece(Touchable piece) {
        this.piece = piece;
        wasTouching=false;
    }

    /**
     * Set the average touch position and change in touch position.
     */
    private void updateTouchPosition() {
        touchPosition.set(oldTouchPosition).add(newTouchPosition).scl(0.5f);
        deltaTouchPosition.set(newTouchPosition).sub(oldTouchPosition);
    }

    /**
     * Poll the touch data. Set touch position and change in touch. Have them defined at all times.
     * At touch down see if a touchable is selected. Put it in front. Apply touch begin, dra and
     * end commands on this object.
     *
     * @return boolean, true if something changed and redraw is needed
     */
    public boolean update() {
        boolean isTouching = device.touchReader.isTouching();
        boolean somethingChanged = false;
        if (!isTouching && !wasTouching) {
            return false;                                     // totally out of touch
        }
        // update touch positions
        if (isTouching && !wasTouching) {                                     // new touch - oldPosition is invalid
            device.touchReader.getPosition(newTouchPosition);
            oldTouchPosition.set(newTouchPosition);
        } else if (isTouching && wasTouching) {                         // continue touching
            oldTouchPosition.set(newTouchPosition);
            device.touchReader.getPosition(newTouchPosition);
        } else {                     // !isTouching&&wasTouching   - end of touch, take last defined positions
            oldTouchPosition.set(newTouchPosition);
        }
        // relevant setPosition is average, and get change in setPosition
        touchPosition.set(oldTouchPosition).add(newTouchPosition).scl(0.5f);
        deltaTouchPosition.set(newTouchPosition).sub(oldTouchPosition);
        // for new touch see if a touchable piece has been selected
        if (isTouching && !wasTouching) {
            pieceIsSelected = piece.contains(touchPosition);
        }
        // act
        if (pieceIsSelected) {
            if (isTouching && !wasTouching) {
                somethingChanged = piece.touchBegin(touchPosition);
            } else if (isTouching && wasTouching) {
                if (!deltaTouchPosition.isZero()) {
                    somethingChanged = piece.touchDrag(touchPosition, deltaTouchPosition, device.camera);
                }
            } else {                     // !isTouching&&wasTouching
                somethingChanged = piece.touchEnd(touchPosition);
                pieceIsSelected = false;
            }
        }
        wasTouching = isTouching;
        return somethingChanged;
    }

    /**
     * Set this as the inputProcessor (or part of multiplexer) to be able to use scroll wheel.
     */
    public void asInputProcessor() {
        Gdx.input.setInputProcessor(this);
    }

    /**
     * Scroll the element that contains the current mouse position.
     * Note: scroll occurs only on PC, where touch (mouse) position is always defined.
     *
     * @param amount int, tells if scrolling up or down
     * @return boolean, true if something changed
     */
    @Override
    public boolean scrolled(int amount) {
        device.touchReader.getPosition(scrollPosition);
        return piece.scroll(scrollPosition, amount);
    }

}
