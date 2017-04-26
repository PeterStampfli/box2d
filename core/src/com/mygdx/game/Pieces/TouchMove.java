package com.mygdx.game.Pieces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.utilities.TouchReader;

/**
 * Poll touch events and transmit to touchable objects.
 */

public class TouchMove extends InputAdapter {

    public Touchable piece;
    public TouchReader touchReader;
    public Camera camera;
    public Vector2 touchPosition;
    public Vector2 deltaTouchPosition;
    //  touchPosition of touch as average (dragging) and displacement
    private Vector2 oldTouchPosition;
    private Vector2 newTouchPosition;
    private boolean pieceIsSelected = false;                                 // piece is really selected
    // if there was touch in previous call, we need this because we do polling
    private boolean wasTouching = false;
    //  positions of last touch and new touch

    /**
     * Create a TouchMove controller.
     *
     * @param piece       Touchable, typically a Touchables collection
     * @param touchReader for reading the touch Position
     * @param camera      Camera, for the touchReader
     */
    public TouchMove(Touchable piece, TouchReader touchReader, Camera camera) {
        this.piece = piece;
        this.touchReader = touchReader;
        this.camera = camera;
        oldTouchPosition = new Vector2();
        newTouchPosition = new Vector2();
        touchPosition = new Vector2();
        deltaTouchPosition = new Vector2();
    }

    /**
     * Create a TouchMove controller.
     *
     * @param piece       Touchable, typically a Touchables collection
     * @param touchReader TouchReader, for reading the touch Position
     * @param viewport    Viewport, with camera for the TouchReader
     */
    public TouchMove(Touchable piece, TouchReader touchReader, Viewport viewport) {
        this(piece, touchReader, viewport.getCamera());
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
        boolean isTouching = touchReader.isTouching();
        boolean somethingChanged = false;
        if (!isTouching && !wasTouching) {
            return false;                                     // totally out of touch
        }
        // update touch positions
        if (isTouching && !wasTouching) {                                     // new touch - oldPosition is invalid
            newTouchPosition.set(touchReader.getPosition(camera));
            oldTouchPosition.set(newTouchPosition);
        } else if (isTouching && wasTouching) {                         // continue touching
            oldTouchPosition.set(newTouchPosition);
            newTouchPosition.set(touchReader.getPosition(camera));
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
                    somethingChanged = piece.touchDrag(touchPosition, deltaTouchPosition, camera);
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
        return piece.scroll(touchReader.getPosition(camera), amount);
    }

}
